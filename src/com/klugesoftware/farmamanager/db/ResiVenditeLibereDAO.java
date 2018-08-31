package com.klugesoftware.farmamanager.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.klugesoftware.farmamanager.model.ResiProdottiVenditaLibera;
import com.klugesoftware.farmamanager.model.ResiVenditeLibere;


public class ResiVenditeLibereDAO {
	
	private final Logger logger = LogManager.getLogger(ResiVenditeLibereDAO.class.getName());
	
	private static final String SQL_INSERT = "INSERT INTO ResiVenditeLibere (idResoVenditaLibera,numreg,idResoVendita,"
			+ "valoreVenditaLibera,totaleIva,totalePezziResi,totaleScontoProdotto,totaleVenditaScontata,"
			+ "codiceFiscale,dataResoVendita) "
			+ "VALUES (?,?,?,?,?,?,?,?,?,?)";
	
	private static String SQL_UPDATE = "UPDATE ResiVenditeLibere SET numreg = ?,idResoVendita = ?,"
			+ "valoreVenditaLibera = ?,totaleIva = ?,totalePezziResi = ?,totaleScontoProdotto = ?,totaleVenditaScontata = ?,"
			+ "codiceFiscale = ?,dataResoVendita = ? "
			+ "WHERE idResoVenditaLibera = ?";
	
	private static final String SQL_FIND_BY_ID = "SELECT * FROM ResiVenditeLibere WHERE idResoVenditaLibera = ?";
	
	private static final String SQL_FIND_BY_ID_RESO_VENDITA = "SELECT * FROM ResiVenditeLibere WHERE idResoVendita = ?";
	
	private static final String SQL_DELETE_BY_ID = "DELETE FROM ResiVenditeLibere WHERE idResoVenditaLibera = ?";
	
	private static final String SQL_DELETE_BY_ID_RESO_VENDITA = "DELETE FROM ResiVenditeLibere WHERE idResoVendita = ?";
	
	private final static String SQL_TRUNCATE_TABLE = "TRUNCATE ResiVenditeLibere";
	
	private DAOFactory daoFactory;
	
	ResiVenditeLibereDAO (DAOFactory daoFactory){
		this.daoFactory = daoFactory;
	}
	
	private ResiVenditeLibere find(String sql,Object...values){
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ResiVenditeLibere reso = new ResiVenditeLibere();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(conn, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				reso = DAOUtil.mapResiVenditeLibere(resultSet);
			}
			else 
				//throw new SQLException("Non è stata trovato nessun record.");
				logger.warn("Non è stato trovato nessun record con id: "+values[0]);
			}catch(SQLException ex){
				logger.error(ex);
		}finally {
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return reso;
	}
	
	private ArrayList<ResiVenditeLibere> findList(String sql,Object...values){
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<ResiVenditeLibere> elenco = new ArrayList<ResiVenditeLibere>();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(conn, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				elenco.add(DAOUtil.mapResiVenditeLibere(resultSet));
			}
		}catch(SQLException ex){
			logger.error(ex);
		}finally{
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return elenco;
	}

	public ResiVenditeLibere lookUpById(int idResoVenditaLibera) {
		return find(SQL_FIND_BY_ID,idResoVenditaLibera);
	}
	
	public ArrayList<ResiVenditeLibere> lookUpByIdResoVendita(int idResoVendita){
		return findList(SQL_FIND_BY_ID_RESO_VENDITA, idResoVendita);
	}

	public ResiVenditeLibere createResoVenditaLibera(ResiVenditeLibere resoVenditaLibera){
		
		if (resoVenditaLibera.getIdResoVenditaLibera() != null){
			throw new IllegalArgumentException("ResoVenditaLibera già presente!");
			
		}
		
		Object[] values = {
				resoVenditaLibera.getIdResoVenditaLibera(),
				resoVenditaLibera.getNumreg(),
				resoVenditaLibera.getResiVendite().getIdResoVendita(),
				resoVenditaLibera.getValoreVenditaLibera(),
				resoVenditaLibera.getTotaleIva(),
				resoVenditaLibera.getTotalePezziResi(),
				resoVenditaLibera.getTotaleScontoProdotto(),
				resoVenditaLibera.getTotaleVenditaScontata(),
				resoVenditaLibera.getCodiceFiscale(),
				resoVenditaLibera.getDataResoVendita()
		};
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet generetedKey = null;
		try{
			connection = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
			int affectedRows = preparedStatement.executeUpdate();
			if(affectedRows == 0){
				throw new SQLException("La creazione di un nuov ResoVenditaLibera non è andata a buon fine: non è stato creato nessun record nel database!");
			}
			generetedKey = preparedStatement.getGeneratedKeys();
			if(generetedKey.next()){
				resoVenditaLibera.setIdResoVenditaLibera(new Integer(generetedKey.getInt(1)));
			}else{
				throw new SQLException("La creazione di un nuov ResoVenditaLibera non è andata a buon fine: non è stato creato nessun record nel database!");
			}
		}catch(SQLException ex){
			logger.error(ex);
		}finally{
			DAOUtil.close(connection, preparedStatement, generetedKey);
		}
		return resoVenditaLibera;
	}

	public ResiVenditeLibere update(ResiVenditeLibere resoVenditaLibera){
		if(resoVenditaLibera.getIdResoVenditaLibera() == null){
			throw new IllegalArgumentException("Il Reso ha un id nullo: non è stato ancora creato!");
		}
		
		Object[] values = {
				resoVenditaLibera.getNumreg(),
				resoVenditaLibera.getResiVendite().getIdResoVendita(),
				resoVenditaLibera.getValoreVenditaLibera(),
				resoVenditaLibera.getTotaleIva(),
				resoVenditaLibera.getTotalePezziResi(),
				resoVenditaLibera.getTotaleScontoProdotto(),
				resoVenditaLibera.getTotaleVenditaScontata(),
				resoVenditaLibera.getCodiceFiscale(),
				resoVenditaLibera.getDataResoVendita(),
				resoVenditaLibera.getIdResoVenditaLibera(),
		};
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try{
			connection = daoFactory.getConnetcion();
			preparedStatement =  DAOUtil.prepareStatement(connection, SQL_UPDATE, true, values);
			int affectedRows = preparedStatement.executeUpdate();
			if (affectedRows == 0)
				throw new SQLException("La modifica non è andata a buon fine: non è stato modifica nessun record!");
		}catch(SQLException ex){
			logger.error(ex);
		}finally{
			DAOUtil.close(connection, preparedStatement);
		}
		return resoVenditaLibera;
	}
	
	public boolean deleteByIdResoVenditaLibera(int idResoVenditaLibera){
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		boolean ret = false;
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = conn.prepareStatement(SQL_DELETE_BY_ID);
			preparedStatement.setInt(1, idResoVenditaLibera);
			int affestedRows = preparedStatement.executeUpdate();
			if(affestedRows == 0){
				throw new SQLException("La cancellazione non è andata a buon fine!");
			}
			ret = true;
		}catch(SQLException ex){
			logger.error(ex);
		}finally{
			DAOUtil.close(conn, preparedStatement);
		}
		return ret; 
	}
	
	public boolean deleteByIdResoVendita(int idResoVendita){
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		boolean ret = false;
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = conn.prepareStatement(SQL_DELETE_BY_ID_RESO_VENDITA);
			preparedStatement.setInt(1, idResoVendita);
			int affestedRows = preparedStatement.executeUpdate();
			if(affestedRows == 0){
				throw new SQLException("La cancellazione non è andata a buon fine!");
			}
			ret = true;
		}catch(SQLException ex){
			logger.error(ex);
		}finally{
			DAOUtil.close(conn, preparedStatement);
		}
		return ret; 
	}

	public boolean svuotaTable(){
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		boolean ret = false;
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = conn.prepareStatement(SQL_TRUNCATE_TABLE);
			preparedStatement.executeUpdate();
			ret = true;
		}catch(SQLException ex){
			logger.error(ex);
		}finally{
			DAOUtil.close(conn, preparedStatement);
		}
		return ret; 
	}
}

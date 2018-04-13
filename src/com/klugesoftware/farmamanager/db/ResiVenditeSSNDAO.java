package com.klugesoftware.farmamanager.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.klugesoftware.farmamanager.model.ResiVenditeLibere;
import com.klugesoftware.farmamanager.model.ResiVenditeSSN;


public class ResiVenditeSSNDAO {
	private final Logger logger = LogManager.getLogger(ResiVenditeSSNDAO.class.getName());
	private static final String SQL_INSERT = "INSERT INTO ResiVenditeSSN (idResoVenditaSSN,numreg,idResoVendita,"
			+ "valoreVenditaSSN,totaleIva,totalePezziresi,totaleScontoSSN,esenzione,"
			+ "quotaAssistito,quotaRicetta,totaleRicetta,codiceFiscale,dataResoVenditaSSN) "
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
	
	private static final String SQL_UPDATE = "UPDATE ResiVenditeSSN SET numreg = ?,idResoVendita = ?,"
			+ "valoreVenditaSSN = ?,totaleIva = ?,totalePezziresi = ?,totaleScontoSSN = ?,esenzione = ?,"
			+ "quotaAssistito = ?,quotaRicetta = ?,totaleRicetta = ?,codiceFiscale = ?,dataResoVenditaSSN = ? "
			+ "WHERE idResoVenditaSSN = ?";
	
	private static final String SQL_FIND_BY_ID = "SELECT * FROM ResiVenditeSSN WHERE idResoVenditaSSN = ?";
	
	private static final String SQL_FIND_BY_ID_RESO_VENDITA = "SELECT * FROM ResiVenditeSSN WHERE idResoVendita = ?";
	
	private static final String SQL_DELETE_BY_ID = "DELETE FROM ResiVenditeSSN WHERE idResoVenditaSSN = ?";
	
	private static final String SQL_DELETE_BY_ID_RESO_VENDITA = "DELETE FROM ResiVenditeSSN WHERE idResoVendita = ?";
	
	private static final String SQL_TRUNCATE_TABLE = "TRUNCATE ResiVenditeSSN";
	
	private DAOFactory daoFactory;
	
	ResiVenditeSSNDAO(DAOFactory daoFactory){
		this.daoFactory = daoFactory;
	}
	
	private ResiVenditeSSN find(String sql,Object...values){
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ResiVenditeSSN reso = new ResiVenditeSSN();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(conn, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				reso = DAOUtil.mapResiVenditeSSN(resultSet);
			}
			else 
				logger.warn("Non è stato trovato nessun record con id: "+values[0]);
				//throw new SQLException("Non è stata trovato nessun record.");
			}catch(SQLException ex){
				ex.printStackTrace();
		}finally {
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return reso;
	}
	
	private ArrayList<ResiVenditeSSN> findList(String sql,Object...values){
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<ResiVenditeSSN> elenco = new ArrayList<ResiVenditeSSN>();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(conn, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				elenco.add(DAOUtil.mapResiVenditeSSN(resultSet));
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}finally{
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return elenco;
	}

	public ResiVenditeSSN lookUpById(int idResoVenditaSSN) {
		return find(SQL_FIND_BY_ID,idResoVenditaSSN);
	}
	
	public ArrayList<ResiVenditeSSN> lookUpByIdResoVendita(int idResoVendita){
		return findList(SQL_FIND_BY_ID_RESO_VENDITA, idResoVendita);
	}
	
	public ResiVenditeSSN create(ResiVenditeSSN resoVenditaSSN){
		
		if(resoVenditaSSN.getIdResoVenditaSSN() != null){
			throw new IllegalArgumentException("ResoVenditaSSN già presente! ");
		
		}
		
		Object[] values = {
				resoVenditaSSN.getIdResoVenditaSSN(),
				resoVenditaSSN.getNumreg(),
				resoVenditaSSN.getResiVendite().getIdResoVendita(),
				resoVenditaSSN.getValoreVenditaSSN(),
				resoVenditaSSN.getTotaleIva(),
				resoVenditaSSN.getTotalePezziResi(),
				resoVenditaSSN.getTotaleScontoSSN(),
				resoVenditaSSN.getEsenzione(),
				resoVenditaSSN.getQuotaAssistito(),
				resoVenditaSSN.getQuotaRicetta(),
				resoVenditaSSN.getTotaleRicetta(),
				resoVenditaSSN.getCodiceFiscale(),
				resoVenditaSSN.getDataResoVenditaSSN()
		};
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet generetedKey = null;
		try{
			connection = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
			int affectedRows = preparedStatement.executeUpdate();
			if(affectedRows == 0){
				throw new SQLException("La creazione di un nuovo ResoVenditaSSN non è andata a buon fine: non è stato creato nessun record nel database!");
			}
			generetedKey = preparedStatement.getGeneratedKeys();
			if(generetedKey.next()){
				resoVenditaSSN.setIdResoVenditaSSN(new Integer(generetedKey.getInt(1)));
			}else{
				throw new SQLException("La creazione di un nuovo ResoVenditaSSN non è andata a buon fine: non è stato creato nessun record nel database!");
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}finally{
			DAOUtil.close(connection, preparedStatement, generetedKey);
		}
		return resoVenditaSSN;
	}

	public ResiVenditeSSN update(ResiVenditeSSN resoVenditaSSN){
		if(resoVenditaSSN.getIdResoVenditaSSN() == null){
			throw new IllegalArgumentException("Il Reso ha un id nullo: non è stato ancora creato!");
		}
		
		Object[] values = {
				resoVenditaSSN.getNumreg(),
				resoVenditaSSN.getResiVendite().getIdResoVendita(),
				resoVenditaSSN.getValoreVenditaSSN(),
				resoVenditaSSN.getTotaleIva(),
				resoVenditaSSN.getTotalePezziResi(),
				resoVenditaSSN.getTotaleScontoSSN(),
				resoVenditaSSN.getEsenzione(),
				resoVenditaSSN.getQuotaAssistito(),
				resoVenditaSSN.getQuotaRicetta(),
				resoVenditaSSN.getTotaleRicetta(),
				resoVenditaSSN.getCodiceFiscale(),
				resoVenditaSSN.getDataResoVenditaSSN(),
				resoVenditaSSN.getIdResoVenditaSSN(),
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
			ex.printStackTrace();
		}finally{
			DAOUtil.close(connection, preparedStatement);
		}
		return resoVenditaSSN;
	}
	
	public boolean deleteByIdResoVenditaSSN(int idResoVenditaSSN){
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		boolean ret = false;
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = conn.prepareStatement(SQL_DELETE_BY_ID);
			preparedStatement.setInt(1, idResoVenditaSSN);
			int affestedRows = preparedStatement.executeUpdate();
			if(affestedRows == 0){
				throw new SQLException("La cancellazione non è andata a buon fine!");
			}
			ret = true;
		}catch(SQLException ex){
			ex.printStackTrace();
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
			ex.printStackTrace();
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
			ex.printStackTrace();
		}finally{
			DAOUtil.close(conn, preparedStatement);
		}
		return ret; 
	}

}

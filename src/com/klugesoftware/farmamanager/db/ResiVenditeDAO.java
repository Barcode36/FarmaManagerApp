package com.klugesoftware.farmamanager.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.klugesoftware.farmamanager.model.ResiProdottiVenditaSSN;
import com.klugesoftware.farmamanager.model.ResiVendite;
import com.klugesoftware.farmamanager.model.Vendite;

public class ResiVenditeDAO {
	private final Logger logger = LogManager.getLogger(ResiVenditeDAO.class.getName());
	private static final String SQL_INSERT = "INSERT INTO ResiVendite (idResoVendita,numreg,totaleResoVendita,"
			+ "totaleResoVenditaScontata,scontoVendita,totalePezziResi,dataReso) "
			+ "VALUES (?,?,?,?,?,?,?)";
	private static final String SQL_UPDATE = "UPDATE ResiVendite SET numreg = ?,totaleResoVendita = ?,"
			+ "totaleResoVenditaScontata = ?,scontoVendita = ?,totalePezziResi = ?,dataReso = ? "
			+ "WHERE idResoVendita = ?";
	
	private static final String SQL_FIND_BY_MAX_ID = "SELECT * FROM ResiVendite WHERE "
			+ "idResoVendita = (SELECT max(idResoVendita) FROM ResiVendite)";
	
	private static final String SQL_FIND_BY_ID = "SELECT * FROM ResiVendite WHERE idResoVendita = ?";
	
	private static final String SQL_DELETE_BY_ID = "DELETE FROM ResiVendite WHERE idResoVendita = ?";
	
	private static final String SQL_TRUNCATE_TABLE = "TRUNCATE ResiVendite";

	private static String SQL_LOOKUP_BY_DATE = "SELECT * FROM ResiVendite WHERE dataReso BETWEEN ? AND ?";
	
	private DAOFactory daoFactory;
	
	ResiVenditeDAO(DAOFactory daoFactory){
		this.daoFactory = daoFactory;
	}
	
	private ResiVendite find(String sql,Object...values){
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ResiVendite reso = new ResiVendite();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(conn, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				reso = DAOUtil.mapResiVendite(resultSet);
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
	
	private ArrayList<ResiVendite> findList(String sql,Object...values){
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<ResiVendite> elenco = new ArrayList<ResiVendite>();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(conn, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				elenco.add(DAOUtil.mapResiVendite(resultSet));
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}finally{
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return elenco;
	}
	
	public ResiVendite lookUpById(int idResoVendita) {
		return find(SQL_FIND_BY_ID, idResoVendita);
	}
	
	public ResiVendite createResoVendita(ResiVendite resoVendita){
		
		if (resoVendita.getIdResoVendita() != null){
			throw new IllegalArgumentException("ResoVendita già presente!");
			
		}
		
		Object[] values = {
				resoVendita.getIdResoVendita(),
				resoVendita.getNumreg(),
				resoVendita.getTotaleResoVendita(),
				resoVendita.getTotaleResoVenditaScontata(),
				resoVendita.getScontoVendita(),
				resoVendita.getTotalePezziResi(),
				resoVendita.getDataReso()
		};
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet generetedKey = null;
		try{
			connection = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
			int affectedRows = preparedStatement.executeUpdate();
			if(affectedRows == 0){
				throw new SQLException("La creazione di un nuovo ResoVendita non è andata a buon fine: non è stato creato nessun record nel database!");
			}
			generetedKey = preparedStatement.getGeneratedKeys();
			if(generetedKey.next()){
				resoVendita.setIdResoVendita(new Integer(generetedKey.getInt(1)));
			}else{
				throw new SQLException("La creazione di un nuovo ResoVendita non è andata a buon fine: non è stato creato nessun record nel database!");
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}finally{
			DAOUtil.close(connection, preparedStatement, generetedKey);
		}
		return resoVendita;
	}

	public ResiVendite update(ResiVendite resoVendita){
		
		if(resoVendita.getIdResoVendita() == null){
			throw new IllegalArgumentException("Il Reso ha un id nullo: non è stato ancora creato!");
		}
		
		Object[] values = {
				resoVendita.getNumreg(),
				resoVendita.getTotaleResoVendita(),
				resoVendita.getTotaleResoVenditaScontata(),
				resoVendita.getScontoVendita(),
				resoVendita.getTotalePezziResi(),
				resoVendita.getDataReso(),
				resoVendita.getIdResoVendita()
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
		return resoVendita;
	}
	
	public ArrayList<ResiVendite> elencoByDate(Date dateFrom, Date dateTo){
		return findList(SQL_LOOKUP_BY_DATE, dateFrom,dateTo);
	}
	
	public ResiVendite findByMaxId(){
		Connection conn = null;
		Statement stmt = null;
		ResultSet resultSet = null;
		ResiVendite reso = new ResiVendite();
		try{
			conn = daoFactory.getConnetcion();
			stmt = conn.createStatement();
			resultSet = stmt.executeQuery(SQL_FIND_BY_MAX_ID);
			if(resultSet != null) {
				if(resultSet.next())
					reso = DAOUtil.mapResiVendite(resultSet);
			}
			else 
				logger.warn("ResiVenditeDao.findByMaxId: non è stato trovato nessun record");
			}catch(SQLException ex){
				logger.error(ex);
		}finally {
			try{
				resultSet.close();
				conn.close();
			}catch(SQLException ex){
				ex.printStackTrace();
			}
		}
		return reso;
	}
	
	public boolean deleteBetweenDate(Date dateFrom,Date datoTo){
		
		String sqlDeleteResiProdottiLibere = "DELETE FROM ResiProdottiVenditaLibera WHERE idresoVenditaLibera IN  "
										+ " (SELECT idResoVenditaLibera FROM ResiVenditeLibere WHERE dataResoVendita "
										+ " BETWEEN ? AND ?)";
		String sqlDeleteResiProdottiSSN = "DELETE FROM ResiProdottiVenditaSSN WHERE idResoVenditaSSN IN  "
										+ " (SELECT idResoVenditaSSN FROM ResiVenditeSSN WHERE dataResoVenditaSSN "
										+ " BETWEEN ? AND ?)";
		String sqlDeleteResiVenditeLibere = "DELETE FROM ResiVenditeLibere WHERE dataResoVendita BETWEEN ? AND ?";
		String sqlDeleteResiVenditeSSN = "DELETE FROM ResiVenditeSSN WHERE dataResoVenditaSSN BETWEEN ? AND ?";
		String sqlDeleteResiVendite = "DELETE FROM ResiVendite WHERE dataReso BETWEEN ? AND ?";
		ArrayList<String> sqlScript = new ArrayList<String>();
		sqlScript.add(sqlDeleteResiProdottiLibere);
		sqlScript.add(sqlDeleteResiVenditeLibere);
		sqlScript.add(sqlDeleteResiProdottiSSN);
		sqlScript.add(sqlDeleteResiVenditeSSN);
		sqlScript.add(sqlDeleteResiVendite);
		Iterator<String> iter = sqlScript.iterator();
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		boolean ret = false;
		try{
			conn = daoFactory.getConnetcion();
			conn.setAutoCommit(false);
			
			while (iter.hasNext()){
				preparedStatement = conn.prepareStatement(iter.next());
				preparedStatement.setDate(1, new java.sql.Date(dateFrom.getTime()));
				preparedStatement.setDate(2, new java.sql.Date(datoTo.getTime()));
				int affestedRows = preparedStatement.executeUpdate();
				if(affestedRows == 0){
					throw new SQLException("Non è stato cancellato nessun record");
				}
			}
			conn.commit();
			ret = true;
		}catch(SQLException ex){
			ex.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
			preparedStatement = conn.prepareStatement(SQL_DELETE_BY_ID);
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

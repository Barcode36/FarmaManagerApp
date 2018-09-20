package com.klugesoftware.farmamanager.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.klugesoftware.farmamanager.model.Giacenze;

public class GiacenzeDAO {
	
	private static final String SQL_INSERT = "INSERT INTO Giacenze ("
			+ "idGiacenza,minsan,costoUltimoDeivato,dataCostoUltimo,descrizione,giacenza,venditeAnnoInCorso) "
			+ " VALUES (?,?,?,?,?,?,?)";
	private static final String SQL_UPDATE = "UPDATE Giacenze SET "
			+ "minsan = ?,costoUltimoDeivato = ?,dataCostoUltimo = ?,descrizione = ?,giacenza = ?,venditeAnnoInCorso = ? "
			+ "WHERE idGiacenza = ?";
	private static final String SQL_UPDATE_BY_MINSAN = "UPDATE Giacenze SET "
			+ "costoUltimoDeivato = ?,dataCostoUltimo = ?,descrizione = ?,giacenza = ?,venditeAnnoInCorso = ? "
			+ "WHERE minsan = ?";
	private static final String SQL_COUNT_TABLE = "SELECT count(*) FROM Giacenze";
	private static final String SQL_FIND_ALL = "SELECT * FROM Giacenze";
	private static final String SQL_FIND_BY_ID = "SELECT * FROM Giacenze WHERE idGiacenza = ?";
	private static final String SQL_FIND_BY_MINSAN = "SELECT * FROM Giacenze WHERE minsan = ?";
	private static final String SQL_COUNT_BY_MINSAN = "SELECT COUNT(*) FROM Giacenze WHERE minsan = ?";
	private static final String SQL_TRUNCATE = "TRUNCATE TABLE Giacenze";
	private final Logger logger = LogManager.getLogger(GiacenzeDAO.class.getName());
	private DAOFactory daoFactory;
	
	public GiacenzeDAO(DAOFactory daoFactory){
		this.daoFactory = daoFactory;
	}

	private Giacenze find(String sql,Object...values){
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Giacenze giacenza = new Giacenze();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(conn, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			if(resultSet != null) {
				if(resultSet.next()){
					giacenza = DAOUtil.mapGiacenze(resultSet);
				}
			  }
			}catch(SQLException ex){
				logger.error(ex);
		}finally {
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return giacenza;
	}
	
	private ArrayList<Giacenze> findList(String sql,Object...values){
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Giacenze> elenco = new ArrayList<Giacenze>();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(conn, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				while(resultSet.next()){
					elenco.add(DAOUtil.mapGiacenze(resultSet));
				}
			}
		}catch(SQLException ex){
			logger.error(ex);
		}finally{
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return elenco;
	}

	public Giacenze create(Giacenze giacenza){
		
		if(giacenza.getIdGiacenza() != 0){
			throw new IllegalArgumentException("Record già presente! ");
		}
		
		Object[] values = {
				giacenza.getIdGiacenza(),
				giacenza.getMinsan(),
				giacenza.getCostoUltimoDeivato(),
				giacenza.getDataCostoUltimo(),
				giacenza.getDescrizione(),
				giacenza.getGiacenza(),
				giacenza.getVenditeAnnoInCorso()
		};
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet generetedKey = null;
		try{
			connection = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
			int affectedRows = preparedStatement.executeUpdate();
			if(affectedRows == 0){
				throw new SQLException("La creazione di un nuovo record non è andata a buon fine!");
			}
			generetedKey = preparedStatement.getGeneratedKeys();
			if(generetedKey.next()){
				giacenza.setIdGiacenza(new Integer(generetedKey.getInt(1)));
			}else{
				throw new SQLException("La creazione di un nuovo record non è andata a buon fine!");
			}
		}catch(SQLException ex){
			logger.error("GiacenzeDAO.create: I can't create new record...",ex);
		}finally{
			DAOUtil.close(connection, preparedStatement, generetedKey);
		}
		return giacenza;
	}

	public Giacenze findById(int idGiacenza){
		return find(SQL_FIND_BY_ID, idGiacenza);
	}
	
	public Giacenze findByMinsan(String minsan){
		return find(SQL_FIND_BY_MINSAN, minsan);
	}
	
	public int countByMinsan(String minsan){
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int ret = -1;
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(conn, SQL_COUNT_BY_MINSAN, false, minsan);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				ret = resultSet.getInt(1);
			}
			else{ 
				throw new SQLException("Non è stata trovato nessun record.");				
			}
			}catch(SQLException ex){
				logger.error("GiacenzeDAO: I can't find record...",ex);
		}finally {
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return ret;
	}

	public int countTable(){

		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int ret = -1;
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(conn, SQL_COUNT_TABLE, false);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				ret = resultSet.getInt(1);
			}
			else{
				throw new SQLException("Non è stata trovato nessun record.");
			}
		}catch(SQLException ex){
			logger.error("GiacenzeDAO: I can't find record...",ex);
		}finally {
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return ret;
	}
	
	public Giacenze update(Giacenze giacenza){
		
		if (giacenza.getIdGiacenza() == 0)
			throw new IllegalArgumentException("L'oggetto  ha un Id Nullo: non è stata ancora creato.");
		
		Object[] values = {
				giacenza.getMinsan(),
				giacenza.getCostoUltimoDeivato(),
				giacenza.getDataCostoUltimo(),
				giacenza.getDescrizione(),
				giacenza.getGiacenza(),
				giacenza.getVenditeAnnoInCorso(),
				giacenza.getIdGiacenza(),			
			};
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try{
			connection = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(connection, SQL_UPDATE, true, values);
			int affectedRows = preparedStatement.executeUpdate();
			if (affectedRows == 0)
				throw new SQLException("La modifica non è andata a buon fine: non è stato modificato nessun record.");
			
		}catch(SQLException ex){
			logger.error("GiacenzeDAO.update: I can't update record...",ex);
		}finally{
			DAOUtil.close(connection, preparedStatement);
		}		
		return giacenza;
	}

	public Giacenze updateByMinsan(Giacenze giacenza){
		
		if (giacenza.getMinsan().equals(""))
			throw new IllegalArgumentException("L'oggetto  non è stata ancora creato.");
		
		Object[] values = {
				giacenza.getCostoUltimoDeivato(),
				giacenza.getDataCostoUltimo(),
				giacenza.getDescrizione(),
				giacenza.getGiacenza(),
				giacenza.getVenditeAnnoInCorso(),
				giacenza.getMinsan()		
			};
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try{
			connection = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(connection, SQL_UPDATE_BY_MINSAN, true, values);
			int affectedRows = preparedStatement.executeUpdate();
			if (affectedRows == 0)
				throw new SQLException("La modifica non è andata a buon fine: non è stato modificato nessun record.");
			
		}catch(SQLException ex){
			logger.error("GiacenzeDAO.update: I can't update record...",ex);
		}finally{
			DAOUtil.close(connection, preparedStatement);
		}		
		return giacenza;
	}
	
	public void deleteTable(){
		Connection connection = null;
		Statement stmt = null;
		try{
			connection = daoFactory.getConnetcion();
			stmt = connection.createStatement();
			stmt.executeUpdate(SQL_TRUNCATE);	
		}catch(SQLException ex){
			logger.error("GiacenzeDAO.update: I can't update record...",ex);
		}finally{
			try{
				stmt.close();
				connection.close();
			}catch(SQLException ex){
				logger.error("GiacenzeDAO.deleteTable(): I can't close db connection");
			}
		}		
	}
}

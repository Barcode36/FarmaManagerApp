package com.klugesoftware.farmamanager.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.klugesoftware.farmamanager.model.Importazioni;
import com.klugesoftware.farmamanager.IOFunctions.TotaliGeneraliVenditaEstratti;


public class ImportazioniDAO {

	private static final String SQL_INSERT = "INSERT INTO Importazioni ("
			+ "idImportazione,dataImportazione,ultimoNumRegImportato,dataUltimoMovImportato,note) "
			+ " VALUES (?,?,?,?,?)";
	private static final String SQL_UPDATE = "UPDATE Importazioni SET ("
			+ "dataImportazione = ?,ultimoNumRegImportato = ?,dataUltimoMovImportato = ?,note = ?) "
			+ "WHERE idImportazione = ?";
	private static final String SQL_FIND_LAST_INSERT = "SELECT * FROM Importazioni WHERE "
			+ "idImportazione = (SELECT max(idImportazione) FROM Importazioni)";
	private static final String SQL_FIND_ALL = "SELECT * FROM Importazioni";
	private static final String SQL_FIND_BY_ID = "SELECT * FROM Importazioni WHERE idImportazione = ?";

	private final Logger logger = LogManager.getLogger(ImportazioniDAO.class.getName());
	private DAOFactory daoFactory;
	
	public ImportazioniDAO(DAOFactory daoFactory){
		this.daoFactory = daoFactory;
	}
	
	private Importazioni find(String sql,Object...values){
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Importazioni importazione = new Importazioni();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(conn, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				importazione = DAOUtil.mapImportazioni(resultSet);
			}
			else 
				throw new SQLException("Non è stata trovato nessun record.");
			}catch(SQLException ex){
				logger.error("ImportazioniDAO.find: I can't found record");
		}finally {
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return importazione;
	}
	
	private ArrayList<Importazioni> findList(String sql,Object...values){
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Importazioni> elenco = new ArrayList<Importazioni>();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(conn, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				elenco.add(DAOUtil.mapImportazioni(resultSet));
			}
		}catch(SQLException ex){
			logger.error("ImportazioniDAO.findList: I can't found records");
		}finally{
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return elenco;
	}

	public Importazioni create(Importazioni importazione){
		
		if(importazione.getIdImportazione() != null){
			throw new IllegalArgumentException("Prodotto già presente! ");
		}
		
		Object[] values = {
				importazione.getIdImportazione(),
				importazione.getDataImportazione(),
				importazione.getUltimoNumRegImportato(),
				importazione.getDataUltimoMovImportato(),
				importazione.getNote()
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
				importazione.setIdImportazione(new Integer(generetedKey.getInt(1)));
			}else{
				throw new SQLException("La creazione di un nuovo record non è andata a buon fine!");
			}
		}catch(SQLException ex){
			logger.error("ImportazioniDAO: I can't create record...",ex);
		}finally{
			DAOUtil.close(connection, preparedStatement, generetedKey);
		}
		return importazione;
	}

	public Importazioni findUltimoRecordInserito(){
		Connection conn = null;
		Statement stmt = null;
		ResultSet resultSet = null;
		Importazioni importazione = new Importazioni();
		try{
			conn = daoFactory.getConnetcion();
			stmt = conn.createStatement();
			resultSet = stmt.executeQuery(SQL_FIND_LAST_INSERT);
			if(resultSet.next()){
				importazione = DAOUtil.mapImportazioni(resultSet);
			}
			else 
				throw new SQLException("Non è stata trovato nessun record.");
			}catch(SQLException ex){
				logger.error("ImportazioneDAO: Nuovo record importazione...",ex);
		}finally {
			try{
				resultSet.close();
				conn.close();
			}catch(SQLException ex){
				logger.error("ImportazioniDAO: I can't close db connections",ex);
			}
		}
		return importazione;
	}
}

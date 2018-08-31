package com.klugesoftware.farmamanager.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.klugesoftware.farmamanager.model.VenditeLibere;
import com.klugesoftware.farmamanager.model.VenditeSSN;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VenditeSSNDAO {
	private final Logger logger = LogManager.getLogger(VenditeSSNDAO.class.getName());
	private static final String SQL_INSERT = "INSERT INTO VenditeSSN (idVenditaSSN,numreg,idVendita,"
			+ "posizioneInVendita,valoreVenditaSSN,totaleIva,totalePezziVenduti,totaleScontoSSN,esenzione,"
			+ "quotaAssistito,quotaRicetta,totaleRicetta,codiceFiscale,dataVenditaSSN) "
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 

	private static final String SQL_FIND_BY_ID_VENDITA_SSN = "SELECT * FROM VenditeSSN WHERE idVenditaSSN = ?";
	
	private static final String SQL_FIND_BY_ID_VENDITA = "SELECT * FROM VenditeSSN WHERE idVendita = ?";
	
	private DAOFactory daoFactory;
	
	VenditeSSNDAO(DAOFactory daoFactory){
		this.daoFactory = daoFactory;
	}
	
	public VenditeSSN lookupByIdVenditaSSN(int idVenditaSSN){
		return find(SQL_FIND_BY_ID_VENDITA_SSN,idVenditaSSN);
	}
	
	public ArrayList<VenditeSSN> lookupelencoVenditeSSNByIdVendita(int idVendita){
		return findList(SQL_FIND_BY_ID_VENDITA, idVendita);
	}
	
	public VenditeSSN create(VenditeSSN venditaSSN){
		
		if(venditaSSN.getIdVenditaSSN() != null){
			throw new IllegalArgumentException("VenditaSSN già presente! ");
		}
		
		Object[] values = {
				venditaSSN.getIdVenditaSSN(),
				venditaSSN.getNumreg(),
				venditaSSN.getVendita().getIdVendita(),
				venditaSSN.getPosizioneInVendita(),
				venditaSSN.getValoreVenditaSSN(),
				venditaSSN.getTotaleIva(),
				venditaSSN.getTotalePezziVenduti(),
				venditaSSN.getTotaleScontoSSN(),
				venditaSSN.getEsenzione(),
				venditaSSN.getQuotaAssistito(),
				venditaSSN.getQuotaRicetta(),
				venditaSSN.getTotaleRicetta(),
				venditaSSN.getCodiceFiscale(),
				venditaSSN.getDataVenditaSSN()
		};
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet generetedKey = null;
		try{
			connection = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
			int affectedRows = preparedStatement.executeUpdate();
			if(affectedRows == 0){
				throw new SQLException("La creazione di una nuova venditaSSN non è andata a buon fine: non è stato creato nessun record nel database!");
			}
			generetedKey = preparedStatement.getGeneratedKeys();
			if(generetedKey.next()){
				venditaSSN.setIdVenditaSSN(new Integer(generetedKey.getInt(1)));
			}else{
				throw new SQLException("La creazione di una nuova venditaSSN non è andata a buon fine: non è stato creato nessun record nel database!");
			}
		}catch(SQLException ex){
			logger.error(ex);
		}finally{
			DAOUtil.close(connection, preparedStatement, generetedKey);
		}
		return venditaSSN;
	}

	private VenditeSSN find(String sql,Object...values){
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		VenditeSSN vendita = new VenditeSSN();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(conn, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				vendita = DAOUtil.mapVenditaSSN(resultSet);
			}
			else 
				throw new SQLException("Non è stata trovato nessun record.");
			}catch(SQLException ex){
				logger.error(ex);
		}finally {
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return vendita;
	}
	
	private ArrayList<VenditeSSN> findList(String sql,Object...values){
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<VenditeSSN> elenco = new ArrayList<VenditeSSN>();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(conn, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				elenco.add(DAOUtil.mapVenditaSSN(resultSet));
			}
		}catch(SQLException ex){
			logger.error(ex);
		}finally{
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return elenco;
	}


}

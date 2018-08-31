package com.klugesoftware.farmamanager.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.klugesoftware.farmamanager.model.Importazioni;
import com.klugesoftware.farmamanager.model.Vendite;
import com.klugesoftware.farmamanager.model.VenditeLibere;
import com.klugesoftware.farmamanager.utility.DateUtility;


public class VenditeDAO {
	private static final String SQL_INSERT = "INSERT INTO Vendite (idVendita,numreg,"
			+ "totaleVendita,totaleVenditaScontata,scontoVendita,totalePezziVenduti,dataVendita) "
			+ "VALUES (?,?,?,?,?,?,?)";
	
	private static final String SQL_FIND_BY_ID_VENDITA = "SELECT * FROM Vendite WHERE idVendita = ?";
	
	private static final String SQL_FIND_BETWEEN_DATE = "SELECT * FROM Vendite WHERE dataVendita BETWEEN ? AND ?";
	
	private static final String SQL_FIND_BY_MAX_ID = "SELECT * FROM Vendite WHERE "
			+ "idVendita = (SELECT max(idVendita) FROM Vendite)";
	
	private DAOFactory daoFactory;
	private final Logger logger = LogManager.getLogger(VenditeDAO.class.getName());
	VenditeDAO(DAOFactory daoFactory){
		this.daoFactory = daoFactory;
	}
	
	private Vendite find(String sql,Object...values){
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Vendite vendita = new Vendite();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(conn, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				vendita = DAOUtil.mapVendita(resultSet);
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
	
	private ArrayList<Vendite> findList(String sql,Object...values){
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Vendite> elenco = new ArrayList<Vendite>();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(conn, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				elenco.add(DAOUtil.mapVendita(resultSet));
			}
		}catch(SQLException ex){
			logger.error(ex);
		}finally{
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return elenco;
	}

	
	public Vendite lookupByIdVendita(int idVendita){
		return find(SQL_FIND_BY_ID_VENDITA, idVendita);
	}
	
	public ArrayList<Vendite> elencoBetweenDate(Date from,Date to){
		return findList(SQL_FIND_BETWEEN_DATE,from,to);
	}
	
	public Vendite createVendita(Vendite vendita){
		
		if (vendita.getIdVendita() != null){
			throw new IllegalArgumentException("Vendita già presente!");
		}
		
		Object[] values = {
				vendita.getIdVendita(),
				vendita.getNumreg(),
				vendita.getTotaleVendita(),
				vendita.getTotaleVenditaScontata(),
				vendita.getScontoVendita(),
				vendita.getTotalePezziVenduti(),
				vendita.getDataVendita()
		};
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet generetedKey = null;
		try{
			connection = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
			int affectedRows = preparedStatement.executeUpdate();
			if(affectedRows == 0){
				throw new SQLException("La creazione di una nuova Vendita non è andata a buon fine: non è stato creato nessun record nel database!");
			}
			generetedKey = preparedStatement.getGeneratedKeys();
			if(generetedKey.next()){
				vendita.setIdVendita(new Integer(generetedKey.getInt(1)));
			}else{
				throw new SQLException("La creazione di una nuova Vendita non è andata a buon fine: non è stato creato nessun record nel database!");
			}
		}catch(SQLException ex){
			logger.error(ex);
		}finally{
			DAOUtil.close(connection, preparedStatement, generetedKey);
		}
		return vendita;
	}
	
	public Vendite findByMaxId(){
		Connection conn = null;
		Statement stmt = null;
		ResultSet resultSet = null;
		Vendite vendita = new Vendite();
		try{
			conn = daoFactory.getConnetcion();
			stmt = conn.createStatement();
			resultSet = stmt.executeQuery(SQL_FIND_BY_MAX_ID);
			if(resultSet.next()){
				vendita = DAOUtil.mapVendita(resultSet);
			}
			else 
				throw new SQLException("Non è stata trovato nessun record.");
			}catch(SQLException ex){
				logger.error(ex);;
		}finally {
			try{
				resultSet.close();
				conn.close();
			}catch(SQLException ex){
				logger.error(ex);
			}
		}
		return vendita;
	}

	/**
	 * 
	 * @param dateFrom
	 * @param datoTo
	 * @return true se ha cancellato sia i resi che le vendite presenti nell'intervallo di date;
	 * altrimenti return false;
	 */
	public boolean deleteBetweenDate(Date dateFrom,Date datoTo){
		
		String sqlDeleteProdottiLibere = "DELETE FROM ProdottiVenditaLibera WHERE idVenditaLibera IN  "
										+ " (SELECT idVenditaLibera FROM VenditeLibere WHERE dataVendita "
										+ " BETWEEN ? AND ?)";
		String sqlDeleteProdottiSSN = "DELETE FROM ProdottiVenditaSSN WHERE idVenditaSSN IN  "
										+ " (SELECT idVenditaSSN FROM VenditeSSN WHERE dataVenditaSSN "
										+ " BETWEEN ? AND ?)";
		String sqlDeleteVenditeLibere = "DELETE FROM VenditeLibere WHERE dataVendita BETWEEN ? AND ?";
		String sqlDeleteVenditeSSN = "DELETE FROM VenditeSSN WHERE dataVenditaSSN BETWEEN ? AND ?";
		String sqlDeleteVendite = "DELETE FROM Vendite WHERE dataVendita BETWEEN ? AND ?";
		String sqlDeleteResiProdottiLibere = "DELETE FROM ResiProdottiVenditaLibera WHERE idresoVenditaLibera IN  "
											+ " (SELECT idResoVenditaLibera FROM ResiVenditeLibere WHERE dataResoVendita "
											+ " BETWEEN ? AND ?)";
		String sqlDeleteResiProdottiSSN = "DELETE FROM ResiProdottiVenditaSSN WHERE idResoVenditaSSN IN  "
											+ " (SELECT idResoVenditaSSN FROM ResiVenditeSSN WHERE dataResoVenditaSSN "
											+ " BETWEEN ? AND ?)";
		String sqlDeleteResiVenditeLibere = "DELETE FROM ResiVenditeLibere WHERE dataResoVendita BETWEEN ? AND ?";
		String sqlDeleteResiVenditeSSN = "DELETE FROM ResiVenditeSSN WHERE dataResoVenditaSSN BETWEEN ? AND ?";
		String sqlDeleteResiVendite = "DELETE FROM ResiVendite WHERE dataReso BETWEEN ? AND ?";
		
		Calendar tempCal = Calendar.getInstance(Locale.ITALY);
		tempCal.setTime(dateFrom);
		int meseDaCancellare = tempCal.get(Calendar.MONTH) + 1;
		int annoDaCancellare = tempCal.get(Calendar.YEAR);
		String sqlDeleteTotaliGeneraliMensili = "DELETE FROM TotaliGeneraliMensili WHERE mese = "+meseDaCancellare+" AND anno = "+annoDaCancellare;
		
		ArrayList<String> sqlScript = new ArrayList<String>();
		sqlScript.add(sqlDeleteProdottiLibere);
		sqlScript.add(sqlDeleteVenditeLibere);
		sqlScript.add(sqlDeleteProdottiSSN);
		sqlScript.add(sqlDeleteVenditeSSN);
		sqlScript.add(sqlDeleteVendite);
		sqlScript.add(sqlDeleteResiProdottiLibere);
		sqlScript.add(sqlDeleteResiVenditeLibere);
		sqlScript.add(sqlDeleteResiProdottiSSN);
		sqlScript.add(sqlDeleteResiVenditeSSN);
		sqlScript.add(sqlDeleteResiVendite);
		Iterator<String> iter = sqlScript.iterator();
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		boolean ret = false;
		int affestedRows = 0;
		try{
			conn = daoFactory.getConnetcion();
			conn.setAutoCommit(false);
			
			while (iter.hasNext()){
				String sqlTemp = iter.next();
				preparedStatement = conn.prepareStatement(sqlTemp);
				preparedStatement.setDate(1, new java.sql.Date(dateFrom.getTime()));
				preparedStatement.setDate(2, new java.sql.Date(datoTo.getTime()));
				affestedRows = preparedStatement.executeUpdate();
				if(affestedRows == 0){
					logger.warn("Non è stato cancellato nessun record: "+sqlTemp+" date: "+DateUtility.converteDateToGUIStringDDMMYYYY(dateFrom)+"-"+DateUtility.converteDateToGUIStringDDMMYYYY(datoTo));
				}
			}
			preparedStatement = conn.prepareStatement(sqlDeleteTotaliGeneraliMensili);
			affestedRows = preparedStatement.executeUpdate();
			if(affestedRows == 0){
				logger.warn("Non è stato cancellato nessun record: "+sqlDeleteTotaliGeneraliMensili+" data: "+meseDaCancellare+"-"+annoDaCancellare);
			}
			conn.commit();
			ret = true;
		}catch(SQLException ex){
			logger.error(ex);
			try {
				conn.rollback();
			} catch (SQLException e) {
				logger.error(ex);
			}
		}finally{
			DAOUtil.close(conn, preparedStatement);
		}
		return ret; 
	}

}

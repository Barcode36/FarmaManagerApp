package com.klugesoftware.farmamanager.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.klugesoftware.farmamanager.IOFunctions.TotaliGeneraliVenditaEstratti;

public class TotaliGeneraliVenditaEstrattiDAO {
	
	static final String SQL_INSERT = "INSERT INTO TotaliGeneraliMensili ("
			+ "idTotale,totaleVenditeLorde,totaleSconti,totaleVenditeNettoSconti,"
			+ "totaleVenditeNettoScontiEIva,totaleCostiNettoIva,totaleProfitti,"
			+ "margineMedio,ricaricoMedio,totaleVenditeLordeLibere,totaleScontiLibere,"
			+ "totaleVenditeNettoScontiLibere,totaleVenditeNettoScontiEIvaLibere,totaleCostiNettiIvaLibere,"
			+ "totaleProfittiLibere,margineMedioLibere,ricaricoMedioLibere,totaleVenditeLordeSSN,"
			+ "totaleScontiSSN,totaleVenditeNettoScontiSSN,totaleVenditeNettoScontiEIvaSSN,"
			+ "totaleCostiNettiIvaSSN,totaleProfittiSSN,margineMedioSSN,ricaricoMedioSSN,giorniLavorativi,giorniFestivi,"
			+ "mese,anno,costiPresunti,dataUltimoAggiornamento)"
			+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	static final String SQL_UPDATE = "UPDATE TotaliGeneraliMensili SET "
			+ "totaleVenditeLorde = ?,totaleSconti = ?,totaleVenditeNettoSconti = ?,"
			+ "totaleVenditeNettoScontiEIva = ?,totaleCostiNettoIva = ?,totaleProfitti = ?,"
			+ "margineMedio = ?,ricaricoMedio = ?,totaleVenditeLordeLibere = ?,totaleScontiLibere = ?,"
			+ "totaleVenditeNettoScontiLibere = ?,totaleVenditeNettoScontiEIvaLibere = ?,totaleCostiNettiIvaLibere = ?,"
			+ "totaleProfittiLibere = ?,margineMedioLibere = ?,ricaricoMedioLibere = ?,totaleVenditeLordeSSN = ?,"
			+ "totaleScontiSSN = ?,totaleVenditeNettoScontiSSN = ?,totaleVenditeNettoScontiEIvaSSN = ?,"
			+ "totaleCostiNettiIvaSSN = ?,totaleProfittiSSN = ?,margineMedioSSN = ?,ricaricoMedioSSN = ?,"
			+ "giorniLavorativi = ?, giorniFestivi = ?, mese = ?,anno = ?,costiPresunti = ?,dataUltimoAggiornamento = ? WHERE idTotale = ?";
 
	static final String SQL_FIND_BY_ID = "SELECT * FROM TotaliGeneraliMensili WHERE idTotale = ?";
	
	static final String SQL_FIND_BY_MESE_ANNO = "SELECT * FROM TotaliGeneraliMensili WHERE mese = ? AND anno = ? ";
	
	static final String SQL_FIND_COUNT_BY_MESE_ANNO = "SELECT COUNT(*) FROM TotaliGeneraliMensili WHERE mese = ? AND anno = ? ";

	static final String SQL_LIST_ANNI_IMPORTATI = "SELECT DISTINCT anno FROM TotaliGeneraliMensili ORDER BY anno DESC";

	static final String SQL_LIST_MESI_IMPORTATI_BY_ANNO = "SELECT mese FROM TotaliGeneraliMensili WHERE anno = ? ORDER BY mese ASC";

	private final Logger logger = LogManager.getLogger(TotaliGeneraliVenditaEstrattiDAO.class.getName());
	
	private DAOFactory daoFactory;
	
	public TotaliGeneraliVenditaEstrattiDAO(DAOFactory daoFactory){
		this.daoFactory = daoFactory;
	}
	
	private TotaliGeneraliVenditaEstratti find(String sql,Object...values){
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		TotaliGeneraliVenditaEstratti totali = new TotaliGeneraliVenditaEstratti();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(conn, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				if(resultSet.next()){
					totali = DAOUtil.mapTotaliGenerali(resultSet);
				}
			  }
			}catch(SQLException ex){
				logger.error(ex);
		}finally {
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return totali;
	}
	
	private ArrayList<TotaliGeneraliVenditaEstratti> findList(String sql,Object...values){
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<TotaliGeneraliVenditaEstratti> elenco = new ArrayList<TotaliGeneraliVenditaEstratti>();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(conn, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				while(resultSet.next()){
					elenco.add(DAOUtil.mapTotaliGenerali(resultSet));
				}
			}
		}catch(SQLException ex){
			logger.error(ex);
		}finally{
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return elenco;
	}

	private ArrayList<String> listMesiByAnno(String sql, Object...values){
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<String> elenco = new ArrayList<String>();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(conn, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				String ret = "";
				while(resultSet.next()){
					switch (resultSet.getString("mese")){
						case "1": ret = "GENNAIO";break;
						case "2": ret = "FEBBRAIO";break;
						case "3": ret = "MARZO";break;
						case "4": ret = "APRILE";break;
						case "5": ret = "MAGGIO";break;
						case "6": ret = "GIUGNO";break;
						case "7": ret = "LUGLIO";break;
						case "8": ret = "AGOSTO";break;
						case "9": ret = "SETTEMBRE";break;
						case "10": ret = "OTTOBRE";break;
						case "11": ret = "NOVEMBRE";break;
						case "12": ret = "DICEMBRE";break;
					}
					elenco.add(ret);
				}
			}
		}catch(SQLException ex){
			logger.error(ex);
		}finally{
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return elenco;
	}


	private ArrayList<String> listAnniImportati(String sql,Object...values){
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<String> elenco = new ArrayList<String>();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(conn, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				while(resultSet.next()){
					elenco.add(resultSet.getString("anno"));
				}
			}
		}catch(SQLException ex){
			logger.error(ex);
		}finally{
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return elenco;
	}

	public ArrayList<String> listMesiImportatiByAnno(String anno){
		return listMesiByAnno(SQL_LIST_MESI_IMPORTATI_BY_ANNO,anno);
	}

	public TotaliGeneraliVenditaEstratti create(TotaliGeneraliVenditaEstratti totaleGenerale){
		
		if(totaleGenerale.getIdTotale() != null){
			throw new IllegalArgumentException("Prodotto già presente! ");
		}
		
		Object[] values = {
				totaleGenerale.getIdTotale(),
				totaleGenerale.getTotaleVenditeLorde(),
				totaleGenerale.getTotaleSconti(),
				totaleGenerale.getTotaleVenditeNettoSconti(),
				totaleGenerale.getTotaleVenditeNette(),
				totaleGenerale.getTotaleCostiNetti(),
				totaleGenerale.getTotaleProfitti(),
				totaleGenerale.getMargineMedio(),
				totaleGenerale.getRicaricoMedio(),
				totaleGenerale.getTotaleVenditeLordeLibere(),
				totaleGenerale.getTotaleScontiLibere(),
				totaleGenerale.getTotaleVenditeNettoScontiLibere(),
				totaleGenerale.getTotaleVenditeNetteLibere(),
				totaleGenerale.getTotaleCostiNettiLibere(),
				totaleGenerale.getTotaleProfittiLibere(),
				totaleGenerale.getMargineMedioLibere(),
				totaleGenerale.getRicaricoMedioLibere(),
				totaleGenerale.getTotaleVenditeLordeSSN(),
				totaleGenerale.getTotaleScontiSSN(),
				totaleGenerale.getTotaleVenditeNettoScontiSSN(),
				totaleGenerale.getTotaleVenditeNetteSSN(),
				totaleGenerale.getTotaleCostiNettiSSN(),
				totaleGenerale.getTotaleProfittiSSN(),
				totaleGenerale.getMargineMedioSSN(),
				totaleGenerale.getRicaricoMedioSSN(),
				totaleGenerale.getGiorniLavorativi(),
				totaleGenerale.getGiorniFestivi(),
				totaleGenerale.getMese(),
				totaleGenerale.getAnno(),
				totaleGenerale.isCostiPresunti(),
				totaleGenerale.getDataUltimoAggiornamento()
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
				totaleGenerale.setIdTotale(new Integer(generetedKey.getInt(1)));
			}else{
				throw new SQLException("La creazione di un nuovo record non è andata a buon fine!");
			}
		}catch(SQLException ex){
			logger.error("TotaliGeneraliVenditaEtratti.create: I can't create record",ex);
		}finally{
			DAOUtil.close(connection, preparedStatement, generetedKey);
		}
		return totaleGenerale;
	}
	
	public TotaliGeneraliVenditaEstratti update(TotaliGeneraliVenditaEstratti totaleGenerale){
		
		if (totaleGenerale.getIdTotale() == null)
			throw new IllegalArgumentException("Il record ha un Id Nullo: non è stato ancora creata.");
		
		Object[] values = {				
				totaleGenerale.getTotaleVenditeLorde(),
				totaleGenerale.getTotaleSconti(),
				totaleGenerale.getTotaleVenditeNettoSconti(),
				totaleGenerale.getTotaleVenditeNette(),
				totaleGenerale.getTotaleCostiNetti(),
				totaleGenerale.getTotaleProfitti(),
				totaleGenerale.getMargineMedio(),
				totaleGenerale.getRicaricoMedio(),
				totaleGenerale.getTotaleVenditeLordeLibere(),
				totaleGenerale.getTotaleScontiLibere(),
				totaleGenerale.getTotaleVenditeNettoScontiLibere(),
				totaleGenerale.getTotaleVenditeNetteLibere(),
				totaleGenerale.getTotaleCostiNettiLibere(),
				totaleGenerale.getTotaleProfittiLibere(),
				totaleGenerale.getMargineMedioLibere(),
				totaleGenerale.getRicaricoMedioLibere(),
				totaleGenerale.getTotaleVenditeLordeSSN(),
				totaleGenerale.getTotaleScontiSSN(),
				totaleGenerale.getTotaleVenditeNettoScontiSSN(),
				totaleGenerale.getTotaleVenditeNetteSSN(),
				totaleGenerale.getTotaleCostiNettiSSN(),
				totaleGenerale.getTotaleProfittiSSN(),
				totaleGenerale.getMargineMedioSSN(),
				totaleGenerale.getRicaricoMedioSSN(),
				totaleGenerale.getGiorniLavorativi(),
				totaleGenerale.getGiorniFestivi(),
				totaleGenerale.getMese(),
				totaleGenerale.getAnno(),
				totaleGenerale.isCostiPresunti(),
				totaleGenerale.getDataUltimoAggiornamento(),
				totaleGenerale.getIdTotale(),
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
			logger.error("TotaliGeneraliVenditaEtratti.update: I can't update record",ex);
		}finally{
			DAOUtil.close(connection, preparedStatement);
		}		
		return totaleGenerale;
	}

	public ArrayList<String> listAnniImportati(){
		return listAnniImportati(SQL_LIST_ANNI_IMPORTATI);
	}

	public TotaliGeneraliVenditaEstratti findById(int idTotale){
		return find(SQL_FIND_BY_ID, idTotale);
	}
	
	public TotaliGeneraliVenditaEstratti findByDate(int mese,int anno){
		return find(SQL_FIND_BY_MESE_ANNO,mese,anno);
	}
	
	public int findCountByDate(int mese,int anno){
		String sql = SQL_FIND_COUNT_BY_MESE_ANNO;
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int result = -1;
		TotaliGeneraliVenditaEstratti totali = new TotaliGeneraliVenditaEstratti();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(conn, sql, false, mese,anno);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				result = resultSet.getInt(1);			
			}
			else 
				throw new SQLException("Non è stata trovato nessun record.");
			}catch(SQLException ex){
				logger.error("TotaliGeneraliVenditaEtratti.find: I can't find record",ex);
		}finally {
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return result;

	}
}

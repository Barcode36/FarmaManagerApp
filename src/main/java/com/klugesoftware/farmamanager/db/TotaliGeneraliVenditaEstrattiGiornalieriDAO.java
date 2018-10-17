package com.klugesoftware.farmamanager.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.klugesoftware.farmamanager.IOFunctions.TotaliGeneraliVenditaEstrattiGiornalieri;

public class TotaliGeneraliVenditaEstrattiGiornalieriDAO {
	
	static final String SQL_INSERT = "INSERT INTO TotaliGeneraliGiornalieri ("
			+ "idTotale,data,totaleVenditeLorde,totaleSconti,totaleVenditeNettoSconti,"
			+ "totaleVenditeNettoScontiEIva,totaleCostiNettoIva,totaleProfitti,"
			+ "margineMedio,ricaricoMedio,totaleVenditeLordeLibere,totaleScontiLibere,"
			+ "totaleVenditeNettoScontiLibere,totaleVenditeNettoScontiEIvaLibere,totaleCostiNettiIvaLibere,"
			+ "totaleProfittiLibere,margineMedioLibere,ricaricoMedioLibere,totaleVenditeLordeSSN,"
			+ "totaleScontiSSN,totaleVenditeNettoScontiSSN,totaleVenditeNettoScontiEIvaSSN,"
			+ "totaleCostiNettiIvaSSN,totaleProfittiSSN,margineMedioSSN,ricaricoMedioSSN,"
			+ "costiPresunti,dataUltimoAggiornamento)"
			+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	static final String SQL_UPDATE = "UPDATE TotaliGeneraliGiornalieri SET "
			+ "data = ?,totaleVenditeLorde = ?,totaleSconti = ?,totaleVenditeNettoSconti = ?,"
			+ "totaleVenditeNettoScontiEIva = ?,totaleCostiNettoIva = ?,totaleProfitti = ?,"
			+ "margineMedio = ?,ricaricoMedio = ?,totaleVenditeLordeLibere = ?,totaleScontiLibere = ?,"
			+ "totaleVenditeNettoScontiLibere = ?,totaleVenditeNettoScontiEIvaLibere = ?,totaleCostiNettiIvaLibere = ?,"
			+ "totaleProfittiLibere = ?,margineMedioLibere = ?,ricaricoMedioLibere = ?,totaleVenditeLordeSSN = ?,"
			+ "totaleScontiSSN = ?,totaleVenditeNettoScontiSSN = ?,totaleVenditeNettoScontiEIvaSSN = ?,"
			+ "totaleCostiNettiIvaSSN = ?,totaleProfittiSSN = ?,margineMedioSSN = ?,ricaricoMedioSSN = ?,"
			+ "costiPresunti = ?,dataUltimoAggiornamento = ? WHERE idTotale = ?";
 
	static final String SQL_FIND_BY_ID = "SELECT * FROM TotaliGeneraliGiornalieri WHERE idTotale = ?";

	static final String SQL_FIND_BY_MAX_ID = "SELECT * FROM TotaliGeneraliGiornalieri WHERE idTotale = (SELECT max(idTotale) FROM TotaliGeneraliGiornalieri) ";

	static final String SQL_FIND_BY_DATA = "SELECT * FROM TotaliGeneraliGiornalieri WHERE data = ? ";
	
	static final String SQL_FIND_COUNT_BY_DATA = "SELECT COUNT(*) FROM TotaliGeneraliGiornalieri WHERE data = ? ";

	static final String SQL_FIND_BETWEEN_DATE = "SELECT * FROM TotaliGeneraliGiornalieri WHERE data BETWEEN ? AND ?";
	
	private final Logger logger = LogManager.getLogger(TotaliGeneraliVenditaEstrattiGiornalieriDAO.class.getName());
	
	private DAOFactory daoFactory;
	
	public TotaliGeneraliVenditaEstrattiGiornalieriDAO(DAOFactory daoFactory){
		this.daoFactory = daoFactory;
	}
	
	private TotaliGeneraliVenditaEstrattiGiornalieri find(String sql,Object...values){
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		TotaliGeneraliVenditaEstrattiGiornalieri totali = new TotaliGeneraliVenditaEstrattiGiornalieri();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(conn, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				if(resultSet.next()){
					totali = DAOUtil.mapTotaliGeneraliGiornalieri(resultSet);
				}
			  }
			}catch(SQLException ex){
				logger.error(ex);
		}finally {
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return totali;
	}
	
	private ArrayList<TotaliGeneraliVenditaEstrattiGiornalieri> findList(String sql,Object...values){
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<TotaliGeneraliVenditaEstrattiGiornalieri> elenco = new ArrayList<TotaliGeneraliVenditaEstrattiGiornalieri>();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(conn, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				while(resultSet.next()){
					elenco.add(DAOUtil.mapTotaliGeneraliGiornalieri(resultSet));
				}
			}
		}catch(SQLException ex){
			logger.error(ex);
		}finally{
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return elenco;
	}

	public TotaliGeneraliVenditaEstrattiGiornalieri create(TotaliGeneraliVenditaEstrattiGiornalieri totaleGenerale){
		
		if(totaleGenerale.getIdTotale() != null){
			throw new IllegalArgumentException("Prodotto già presente! ");
		}
		
		Object[] values = {
				totaleGenerale.getIdTotale(),
				totaleGenerale.getData(),
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
	
	public TotaliGeneraliVenditaEstrattiGiornalieri update(TotaliGeneraliVenditaEstrattiGiornalieri totaleGenerale){
		
		if (totaleGenerale.getIdTotale() == null)
			throw new IllegalArgumentException("Il record ha un Id Nullo: non è stato ancora creata.");
		
		Object[] values = {				
				totaleGenerale.getData(),
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
			logger.error("TotaliGeneraliVenditaEtrattiGiornalieri.update: I can't update record",ex);
		}finally{
			DAOUtil.close(connection, preparedStatement);
		}		
		return totaleGenerale;
	}


	public TotaliGeneraliVenditaEstrattiGiornalieri findById(int idTotale){
		return find(SQL_FIND_BY_ID, idTotale);
	}

	public TotaliGeneraliVenditaEstrattiGiornalieri findByMaxId(){
		return find(SQL_FIND_BY_MAX_ID);
	}
	
	public TotaliGeneraliVenditaEstrattiGiornalieri findByDate(Date data){
		return find(SQL_FIND_BY_DATA,data);
	}

	public ArrayList<TotaliGeneraliVenditaEstrattiGiornalieri> findBetweenDate(Date dateFrom,Date dateTo){
		return findList(SQL_FIND_BETWEEN_DATE,dateFrom,dateTo);
	}
	
	public int findCountByDate(Date data){
		String sql = SQL_FIND_COUNT_BY_DATA;
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int result = -1;
		TotaliGeneraliVenditaEstrattiGiornalieri totali = new TotaliGeneraliVenditaEstrattiGiornalieri();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(conn, sql, false, data);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				result = resultSet.getInt(1);			
			}
			else 
				throw new SQLException("Non è stata trovato nessun record.");
			}catch(SQLException ex){
				logger.error("TotaliGeneraliVenditaEtrattiGiornalieri.find: I can't find record",ex);
		}finally {
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return result;

	}


}

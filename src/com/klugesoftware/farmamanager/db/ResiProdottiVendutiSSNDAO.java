package com.klugesoftware.farmamanager.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.klugesoftware.farmamanager.model.ProdottiVenditaSSN;
import com.klugesoftware.farmamanager.model.ResiProdottiVenditaLibera;
import com.klugesoftware.farmamanager.model.ResiProdottiVenditaSSN;

public class ResiProdottiVendutiSSNDAO {
	
	private final Logger logger = LogManager.getLogger(ResiProdottiVendutiSSNDAO.class.getName());
	
	private static final String SQL_INSERT = "INSERT INTO ResiProdottiVenditaSSN (idResoProdottoVenditaSSN,numreg,idResoVenditaSSN,minsan,"
			+ "descrizione,prezzoFustello,prezzoPraticato,prezzoRimborso,quotaAssistito,quotaPezzo,quantita,"
			+ "aliquotaIva,costoCompresoIva,costoNettoIva,percentualeScontoSSN,valoreScontoSSNExtra,"
			+ "valoreScontoAifa,valoreScontoSSN,totaleScontoUnitario,prezzoVenditaNetto,profittoUnitario,dataReso) "
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	private static final String SQL_UPDATE = "UPDATE ResiProdottiVenditaSSN SET numreg = ?,idResoVenditaSSN = ?,minsan = ?,"
			+ "descrizione = ?,prezzoFustello = ?,prezzoPraticato = ?,prezzoRimborso = ?,quotaAssistito = ?,quotaPezzo = ?,quantita = ?,"
			+ "aliquotaIva = ?,costoCompresoIva = ?,costoNettoIva = ?,percentualeScontoSSN = ?,valoreScontoSSNExtra = ?,"
			+ "valoreScontoAifa = ?,valoreScontoSSN = ?,totaleScontoUnitario = ?,prezzoVenditaNetto = ?,profittoUnitario = ?,dataReso = ? "
			+ " WHERE idResoProdottoVenditaSSN = ?";

	private static final String SQL_FIND_BY_ID = "SELECT * FROM ResiProdottiVenditaSSN WHERE idResoProdottoVenditaSSN = ?";
	
	private static final String SQL_FIND_BY_ID_RESO_VENDITA_SSN = "SELECT * FROM ResiProdottiVenditaSSN WHERE idResoVenditaSSN = ?";
	
	private static final String SQL_DELETE_BY_ID = "DELETE FROM ResiProdottiVenditaSSN WHERE idResoProdottoVenditaSSN = ?";
	
	private static final String SQL_DELETE_BY_ID_RESO_VENDITA_SSN = "DELETE FROM ResiProdottiVenditaSSN WHERE idResoVenditaSSN = ?";
	
	private static final String SQL_TRUNCATE_TABLE = "TRUNCATE ResiProdottiVenditaSSN";
	
	private DAOFactory daoFactory;
	
	ResiProdottiVendutiSSNDAO(DAOFactory daoFactory){
		this.daoFactory = daoFactory;
	}
	
	private ResiProdottiVenditaSSN find(String sql,Object...values){
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ResiProdottiVenditaSSN reso = new ResiProdottiVenditaSSN();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(conn, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				reso = DAOUtil.mapResiProdottiVenditaSSN(resultSet);
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
	
	private ArrayList<ResiProdottiVenditaSSN> findList(String sql,Object...values){
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<ResiProdottiVenditaSSN> elenco = new ArrayList<ResiProdottiVenditaSSN>();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(conn, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				elenco.add(DAOUtil.mapResiProdottiVenditaSSN(resultSet));
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}finally{
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return elenco;
	}

	public ResiProdottiVenditaSSN lookUpById(int idResoProdottiVenditaSSN) {
		return find(SQL_FIND_BY_ID,idResoProdottiVenditaSSN);
	}
	
	public ArrayList<ResiProdottiVenditaSSN> lookUpByIdResoVenditaSSN(int idResoVenditaSSN){
		return findList(SQL_FIND_BY_ID_RESO_VENDITA_SSN, idResoVenditaSSN);
	}
	
	public ResiProdottiVenditaSSN create(ResiProdottiVenditaSSN resoProdottoVenditaSSN){
		
		if(resoProdottoVenditaSSN.getIdResoProdottoVenditaSSN() != null){
			throw new IllegalArgumentException("ResoProdottoSSN già presente! ");
		}
		
		Object[] values = {
				resoProdottoVenditaSSN.getIdResoProdottoVenditaSSN(),
				resoProdottoVenditaSSN.getNumreg(),
				resoProdottoVenditaSSN.getResiVenditeSsn().getIdResoVenditaSSN(),
				resoProdottoVenditaSSN.getMinsan(),
				resoProdottoVenditaSSN.getDescrizione(),
				resoProdottoVenditaSSN.getPrezzoFustello(),
				resoProdottoVenditaSSN.getPrezzoPraticato(),
				resoProdottoVenditaSSN.getPrezzoRimborso(),
				resoProdottoVenditaSSN.getQuotaAssistito(),
				resoProdottoVenditaSSN.getQuotaPezzo(),
				resoProdottoVenditaSSN.getQuantita(),
				resoProdottoVenditaSSN.getAliquotaIva(),
				resoProdottoVenditaSSN.getCostoCompresoIva(),
				resoProdottoVenditaSSN.getCostoNettoIva(),
				resoProdottoVenditaSSN.getPercentualeScontoSSN(),
				resoProdottoVenditaSSN.getValoreScontoSSNExtra(),
				resoProdottoVenditaSSN.getValoreScontoAifa(),
				resoProdottoVenditaSSN.getValoreScontoSSN(),
				resoProdottoVenditaSSN.getTotaleScontoUnitario(),
				resoProdottoVenditaSSN.getPrezzoVenditaNetto(),
				resoProdottoVenditaSSN.getProfittoUnitario(),
				resoProdottoVenditaSSN.getDataReso()
		};
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet generetedKey = null;
		try{
			connection = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
			int affectedRows = preparedStatement.executeUpdate();
			if(affectedRows == 0){
				throw new SQLException("La creazione di un nuovo reso prodottoSSN  non è andata a buon fine: non è stato creato nessun record nel database!");
			}
			generetedKey = preparedStatement.getGeneratedKeys();
			if(generetedKey.next()){
				resoProdottoVenditaSSN.setIdResoProdottoVenditaSSN(new Integer(generetedKey.getInt(1)));
			}else{
				throw new SQLException("La creazione di un nuovo reso prodottoSSN non è andata a buon fine: non è stato creato nessun record nel database!");
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}finally{
			DAOUtil.close(connection, preparedStatement, generetedKey);
		}
		return resoProdottoVenditaSSN;
	}

	public ResiProdottiVenditaSSN update(ResiProdottiVenditaSSN resoProdottoVenditaSSN){
		if(resoProdottoVenditaSSN.getIdResoProdottoVenditaSSN() == null){
			throw new IllegalArgumentException("Il Reso ha un id nullo: non è stato ancora creato!");
		}
		
		Object[] values = {
				resoProdottoVenditaSSN.getNumreg(),
				resoProdottoVenditaSSN.getResiVenditeSsn().getIdResoVenditaSSN(),
				resoProdottoVenditaSSN.getMinsan(),
				resoProdottoVenditaSSN.getDescrizione(),
				resoProdottoVenditaSSN.getPrezzoFustello(),
				resoProdottoVenditaSSN.getPrezzoPraticato(),
				resoProdottoVenditaSSN.getPrezzoRimborso(),
				resoProdottoVenditaSSN.getQuotaAssistito(),
				resoProdottoVenditaSSN.getQuotaPezzo(),
				resoProdottoVenditaSSN.getQuantita(),
				resoProdottoVenditaSSN.getAliquotaIva(),
				resoProdottoVenditaSSN.getCostoCompresoIva(),
				resoProdottoVenditaSSN.getCostoNettoIva(),
				resoProdottoVenditaSSN.getPercentualeScontoSSN(),
				resoProdottoVenditaSSN.getValoreScontoSSNExtra(),
				resoProdottoVenditaSSN.getValoreScontoAifa(),
				resoProdottoVenditaSSN.getValoreScontoSSN(),
				resoProdottoVenditaSSN.getTotaleScontoUnitario(),
				resoProdottoVenditaSSN.getPrezzoVenditaNetto(),
				resoProdottoVenditaSSN.getProfittoUnitario(),
				resoProdottoVenditaSSN.getDataReso(),
				resoProdottoVenditaSSN.getIdResoProdottoVenditaSSN()
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
		return resoProdottoVenditaSSN;
	}
	
	public boolean deleteByIdResoProdottoVenditaSSN(int idResoProdottoVenditaSSN){
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		boolean ret = false;
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = conn.prepareStatement(SQL_DELETE_BY_ID);
			preparedStatement.setInt(1, idResoProdottoVenditaSSN);
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

	public boolean deleteByIdResoVenditaSSN(int idResoVenditaSSN){
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		boolean ret = false;
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = conn.prepareStatement(SQL_DELETE_BY_ID_RESO_VENDITA_SSN);
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

package com.klugesoftware.farmamanager.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.klugesoftware.farmamanager.model.ProdottiVenditaLibera;
import com.klugesoftware.farmamanager.model.ResiProdottiVenditaLibera;
import com.klugesoftware.farmamanager.utility.DateUtility;

public class ResiProdottiVenditaLiberaDAO {
	private final Logger logger = LogManager.getLogger(ResiProdottiVenditaLiberaDAO.class.getName());
	private static final String SQL_INSERT = "INSERT INTO ResiProdottiVenditaLibera (idResoProdottoVenditaLibera,numreg,"
			+ "idResoVenditaLibera,minsan,descrizione,"
			+ "prezzoVendita,prezzoPraticato,scontoProdotti,scontoPayBack,aliquotaIva,costoCompresoIva,costoNettoIva,"
			+ "quantita,totaleScontoUnitario,prezzoVenditaNetto,profittoUnitario,dataReso) "
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
	
	private static final String SQL_UPDATE = "UPDATE ResiProdottiVenditaLibera SET numreg = ?,"
			+ "idResoVenditaLibera = ?,minsan = ?,descrizione = ?,"
			+ "prezzoVendita = ?,prezzoPraticato = ?,scontoProdotti = ?,scontoPayBack = ?,aliquotaIva = ?,costoCompresoIva = ?,costoNettoIva = ?,"
			+ "quantita = ?,totaleScontoUnitario = ?,prezzoVenditaNetto = ?,profittoUnitario = ?,dataReso = ?"
			+ " WHERE idResoProdottoVenditaLibera = ?";
	
	private static final String SQL_FIND_BY_ID = "SELECT * FROM ResiProdottiVenditaLibera WHERE idResoProdottoVenditaLibera = ?";
	
	private static final String SQL_FIND_BY_ID_RESO_VENDITA_LIBERA = "SELECT * FROM ResiProdottiVenditaLibera WHERE idResoVenditaLibera = ?";
	
	private static final String SQL_DELETE_BY_ID = "DELETE FROM ResiProdottiVenditaLibera WHERE idResoProdottoVenditaLibera = ?";
	
	private static final String SQL_DELETE_BY_ID_RESO_VENDITA_LIBERA = "DELETE FROM ResiProdottiVenditaLibera WHERE idResoVenditaLibera = ?";
	
	private static final String SQL_TRUNCATE_TABLE = "TRUNCATE ResiProdottiVenditaLibera";
	
	private DAOFactory daoFactory;
	
	ResiProdottiVenditaLiberaDAO(DAOFactory daoFactory){
		this.daoFactory = daoFactory;
	}
	
	private ResiProdottiVenditaLibera find(String sql,Object...values){
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ResiProdottiVenditaLibera reso = new ResiProdottiVenditaLibera();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(conn, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				reso = DAOUtil.mapResiProdottiVenditaLibera(resultSet);
			}
			else 
				//throw new SQLException("Non è stata trovato nessun record.");
				logger.warn("Non è stato trovato nessun record.");
			}catch(SQLException ex){
				logger.error(ex);
		}finally {
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return reso;
	}
	
	private ArrayList<ResiProdottiVenditaLibera> findList(String sql,Object...values){
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<ResiProdottiVenditaLibera> elenco = new ArrayList<ResiProdottiVenditaLibera>();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(conn, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				elenco.add(DAOUtil.mapResiProdottiVenditaLibera(resultSet));
			}
		}catch(SQLException ex){
			logger.error(ex);
		}finally{
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return elenco;
	}

	public ResiProdottiVenditaLibera lookUpById(int idResoProdottoVenditaLibera) {
		return find(SQL_FIND_BY_ID,idResoProdottoVenditaLibera);
	}
	
	public ArrayList<ResiProdottiVenditaLibera> lookUpElencoResiProdottiVenditaLibera(int idResoVenditaLibera){
		return findList(SQL_FIND_BY_ID_RESO_VENDITA_LIBERA, idResoVenditaLibera);
	}
	
	public ResiProdottiVenditaLibera create(ResiProdottiVenditaLibera resoProdottoVenditaLibera){
		
		if(resoProdottoVenditaLibera.getIdResoProdottoVenditaLibera() != null){
			throw new IllegalArgumentException("ResoProdotto già presente! ");
		}
		
		Object[] values = {
				resoProdottoVenditaLibera.getIdResoProdottoVenditaLibera(),
				resoProdottoVenditaLibera.getNumreg(),
				resoProdottoVenditaLibera.getResiVenditeLibere().getIdResoVenditaLibera(),
				resoProdottoVenditaLibera.getMinsan(),
				resoProdottoVenditaLibera.getDescrizione(),
				resoProdottoVenditaLibera.getPrezzoVendita(),
				resoProdottoVenditaLibera.getPrezzoPraticato(),
				resoProdottoVenditaLibera.getScontoProdotti(),
				resoProdottoVenditaLibera.getScontoPayBack(),
				resoProdottoVenditaLibera.getAliquotaIva(),
				resoProdottoVenditaLibera.getCostoCompresoIva(),
				resoProdottoVenditaLibera.getCostoNettoIva(),
				resoProdottoVenditaLibera.getQuantita(),
				resoProdottoVenditaLibera.getTotaleScontoUnitario(),
				resoProdottoVenditaLibera.getPrezzoVenditaNetto(),
				resoProdottoVenditaLibera.getProfittoUnitario(),
				resoProdottoVenditaLibera.getDataReso()
		};
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet generetedKey = null;
		try{
			connection = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
			int affectedRows = preparedStatement.executeUpdate();
			if(affectedRows == 0){
				throw new SQLException("La creazione di un nuovo ResoProdotto non è andata a buon fine: non è stato creato nessun record nel database!");
			}
			generetedKey = preparedStatement.getGeneratedKeys();
			if(generetedKey.next()){
				resoProdottoVenditaLibera.setIdResoProdottoVenditaLibera(new Integer(generetedKey.getInt(1)));
			}else{
				throw new SQLException("La creazione di un nuovo ResoProdotto non è andata a buon fine: non è stato creato nessun record nel database!");
			}
		}catch(SQLException ex){
			logger.error(ex);
		}finally{
			DAOUtil.close(connection, preparedStatement, generetedKey);
		}
		return resoProdottoVenditaLibera;
	}
	
	public ResiProdottiVenditaLibera update(ResiProdottiVenditaLibera resoProdottoVenditaLibera){
		if(resoProdottoVenditaLibera.getIdResoProdottoVenditaLibera() == null){
			throw new IllegalArgumentException("Il Reso ha un id nullo: non è stato ancora creato!");
		}
		
		Object[] values = {
				resoProdottoVenditaLibera.getNumreg(),
				resoProdottoVenditaLibera.getResiVenditeLibere().getIdResoVenditaLibera(),
				resoProdottoVenditaLibera.getMinsan(),
				resoProdottoVenditaLibera.getDescrizione(),
				resoProdottoVenditaLibera.getPrezzoVendita(),
				resoProdottoVenditaLibera.getPrezzoPraticato(),
				resoProdottoVenditaLibera.getScontoProdotti(),
				resoProdottoVenditaLibera.getScontoPayBack(),
				resoProdottoVenditaLibera.getAliquotaIva(),
				resoProdottoVenditaLibera.getCostoCompresoIva(),
				resoProdottoVenditaLibera.getCostoNettoIva(),
				resoProdottoVenditaLibera.getQuantita(),
				resoProdottoVenditaLibera.getTotaleScontoUnitario(),
				resoProdottoVenditaLibera.getPrezzoVenditaNetto(),
				resoProdottoVenditaLibera.getProfittoUnitario(),
				resoProdottoVenditaLibera.getDataReso(),
				resoProdottoVenditaLibera.getIdResoProdottoVenditaLibera()
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
		return resoProdottoVenditaLibera;

	}
	
	public boolean deleteByIdResoProdottoVenditaLibera(int idResoProdottoVenditaLibera){
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		boolean ret = false;
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = conn.prepareStatement(SQL_DELETE_BY_ID);
			preparedStatement.setInt(1, idResoProdottoVenditaLibera);
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

	public boolean deleteByIdResoLibera(int idResoVenditaLibera){
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		boolean ret = false;
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = conn.prepareStatement(SQL_DELETE_BY_ID_RESO_VENDITA_LIBERA);
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

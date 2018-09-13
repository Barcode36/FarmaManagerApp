package com.klugesoftware.farmamanager.db;

import java.sql.Connection;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;

import com.klugesoftware.farmamanager.model.ProdottiVenditaLibera;
import com.klugesoftware.farmamanager.model.ResiVendite;


public class ProdottiVenditaLiberaDAO {
	
	private static final String SQL_INSERT = "INSERT INTO ProdottiVenditaLibera (idProdottoVenditaLibera,numreg,"
			+ "idVenditaLibera,posizioneInVendita,minsan,descrizione,prezzoVendita,prezzoPraticato,scontoProdotti,"
			+ "scontoPayBack,aliquotaIva,costoCompresoIva,costoNettoIva,tipoCosto,quantita,scontoVenditaRipartito,"
			+ "totaleScontoUnitario,prezzoVenditaNetto,profittoUnitario,DeGrassi,tipoProdotto,tipoRicetta,"
			+ "percentualeMargineUnitario,percentualeRicaricoUnitario,dataVendita,quantitaReso) "
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
	
	private static final String SQL_UPDATE = "UPDATE ProdottiVenditaLibera  SET numreg = ?,"
			+ "idVenditaLibera = ?,posizioneInVendita = ?,minsan = ?,descrizione = ?,prezzoVendita = ?,prezzoPraticato = ?,scontoProdotti = ?,"
			+ "scontoPayBack = ?,aliquotaIva = ?,costoCompresoIva = ?,costoNettoIva = ?,tipoCosto = ?,quantita = ?,scontoVenditaRipartito = ?,"
			+ "totaleScontoUnitario = ?,prezzoVenditaNetto = ?,profittoUnitario = ?,DeGrassi = ?,tipoProdotto = ?,tipoRicetta = ?,"
			+ "percentualeMargineUnitario = ?,percentualeRicaricoUnitario = ?,dataVendita = ?,quantitaReso = ? "
			+ "WHERE idProdottoVenditaLibera = ?"; 

	private static final String SQL_FIND_BETWEEN_DATE = "SELECT * FROM ProdottiVenditaLibera WHERE dataVendita BETWEEN ? AND ? ";
	
	private static final String SQL_FIND_BY_ID_VENDITA_LIBERA = "SELECT * FROM ProdottiVenditaLibera WHERE idVenditaLibera = ?";
	
	private static final String SQL_FIND_BY_ID_PRODOTTO = "SELECT * FROM ProdottiVenditaLibera WHERE idProdottoVenditaLibera = ?";
	
	private static final String SQL_FIND_PRODOTTO_PER_RESO = "SELECT * FROM ProdottiVenditaLibera "
			+ "WHERE idProdottoVenditaLibera = ("
			+ "SELECT MAX(idProdottoVenditaLibera) FROM ProdottiVenditaLibera "
			+ "WHERE minsan = ? "
			+ "AND dataVendita <= ? "
			+ "AND (quantita-quantitaReso) > 0 )";
	
	private static final String SQL_DELETE_BY_ID = "DELETE FROM ProdottiVenditaLibera WHERE idProdottoVenditaLibera = ?";
	
	private static final String SQL_DELETE_BY_ID_VENDITA_LIBERA = "DELETE FROM ProdottiVenditaLibera WHERE idVenditaLibera = ?";
	
	private static final String SQL_TRUNCATE_TABLE = "TRUNCATE ProdottiVenditaLibera";
	
	private final Logger logger = LogManager.getLogger(ProdottiVenditaLiberaDAO.class.getName());
	
	private DAOFactory daoFactory;
	
	ProdottiVenditaLiberaDAO(DAOFactory daoFactory){
		this.daoFactory = daoFactory;
	}
	
	public ProdottiVenditaLibera create(ProdottiVenditaLibera prodottoVenditaLibera){
		
		if(prodottoVenditaLibera.getIdProdottoVenditaLibera() != null){
			throw new IllegalArgumentException("Prodotto già presente! ");
		}
		
		Object[] values = {
				prodottoVenditaLibera.getIdProdottoVenditaLibera(),
				prodottoVenditaLibera.getNumreg(),
				prodottoVenditaLibera.getVenditaLibera().getIdVenditaLibera(),
				prodottoVenditaLibera.getPosizioneInVendita(),
				prodottoVenditaLibera.getMinsan(),
				prodottoVenditaLibera.getDescrizione(),
				prodottoVenditaLibera.getPrezzoVendita(),
				prodottoVenditaLibera.getPrezzoPraticato(),
				prodottoVenditaLibera.getScontoProdotti(),
				prodottoVenditaLibera.getScontoPayBack(),
				prodottoVenditaLibera.getAliquotaIva(),
				prodottoVenditaLibera.getCostoCompresoIva(),
				prodottoVenditaLibera.getCostoNettoIva(),
				prodottoVenditaLibera.getTipoCosto().toString(),
				prodottoVenditaLibera.getQuantita(),
				prodottoVenditaLibera.getScontoVenditaRipartito(),
				prodottoVenditaLibera.getTotaleScontoUnitario(),
				prodottoVenditaLibera.getPrezzoVenditaNetto(),
				prodottoVenditaLibera.getProfittoUnitario(),
				prodottoVenditaLibera.getDeGrassi(),
				prodottoVenditaLibera.getTipoProdotto(),
				prodottoVenditaLibera.getTipoRicetta(),
				prodottoVenditaLibera.getPercentualeMargineUnitario(),
				prodottoVenditaLibera.getPercentualeRicaricoUnitario(),
				prodottoVenditaLibera.getDataVendita(),
				prodottoVenditaLibera.getQuantitaReso()
		};
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet generetedKey = null;
		try{
			connection = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
			int affectedRows = preparedStatement.executeUpdate();
			if(affectedRows == 0){
				throw new SQLException("La creazione di un nuovo prodotto non è andata a buon fine: non è stato creato nessun record nel database!");
			}
			generetedKey = preparedStatement.getGeneratedKeys();
			if(generetedKey.next()){
				prodottoVenditaLibera.setIdProdottoVenditaLibera(new Integer(generetedKey.getInt(1)));
			}else{
				throw new SQLException("La creazione di un nuovo prodotto non è andata a buon fine: non è stato creato nessun record nel database!");
			}
		}catch(SQLException ex){
			logger.error("NUMREG:"+prodottoVenditaLibera.getNumreg());
			logger.error("Costo Ivato:"+prodottoVenditaLibera.getCostoCompresoIva());
			logger.error("Costo Netto:"+prodottoVenditaLibera.getCostoNettoIva());
			logger.error(ex);
		}finally{
			DAOUtil.close(connection, preparedStatement, generetedKey);
		}
		return prodottoVenditaLibera;
	}
	
	public ProdottiVenditaLibera update(ProdottiVenditaLibera prodottoVenditaLibera){
		
		if(prodottoVenditaLibera.getIdProdottoVenditaLibera() == null){
			throw new IllegalArgumentException("Il Prodotto ha un id nullo: non è stato ancora creato!");
		}
		
		Object[] values = {				
				prodottoVenditaLibera.getNumreg(),
				prodottoVenditaLibera.getVenditaLibera().getIdVenditaLibera(),
				prodottoVenditaLibera.getPosizioneInVendita(),
				prodottoVenditaLibera.getMinsan(),
				prodottoVenditaLibera.getDescrizione(),
				prodottoVenditaLibera.getPrezzoVendita(),
				prodottoVenditaLibera.getPrezzoPraticato(),
				prodottoVenditaLibera.getScontoProdotti(),
				prodottoVenditaLibera.getScontoPayBack(),
				prodottoVenditaLibera.getAliquotaIva(),
				prodottoVenditaLibera.getCostoCompresoIva(),
				prodottoVenditaLibera.getCostoNettoIva(),
				prodottoVenditaLibera.getTipoCosto().toString(),
				prodottoVenditaLibera.getQuantita(),
				prodottoVenditaLibera.getScontoVenditaRipartito(),
				prodottoVenditaLibera.getTotaleScontoUnitario(),
				prodottoVenditaLibera.getPrezzoVenditaNetto(),
				prodottoVenditaLibera.getProfittoUnitario(),
				prodottoVenditaLibera.getDeGrassi(),
				prodottoVenditaLibera.getTipoProdotto(),
				prodottoVenditaLibera.getTipoRicetta(),
				prodottoVenditaLibera.getPercentualeMargineUnitario(),
				prodottoVenditaLibera.getPercentualeRicaricoUnitario(),
				prodottoVenditaLibera.getDataVendita(),
				prodottoVenditaLibera.getQuantitaReso(),
				prodottoVenditaLibera.getIdProdottoVenditaLibera(),
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
		return prodottoVenditaLibera;
	}


	public ProdottiVenditaLibera lookupByIdProdotto(int idProdotto){
		return find(SQL_FIND_BY_ID_PRODOTTO, idProdotto);
	}
	
	public ArrayList<ProdottiVenditaLibera> lookupElencoProdottiByIdVenditaLibera(int idVenditaLibera){
		return findList(SQL_FIND_BY_ID_VENDITA_LIBERA, idVenditaLibera);
	}
	
	/**
	 * Cerca il prodotto venduto più prossimo alla data del reso che abbia quantità-quantitaReso > 0
	 */
	public ProdottiVenditaLibera lookUpProdottoReso(String minsan,Date dataReso) {
		return find(SQL_FIND_PRODOTTO_PER_RESO, minsan,dataReso);
	}
	
	public boolean deleteByIdProdottoLibera(int idProdottoVenditaLibera){
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		boolean ret = false;
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = conn.prepareStatement(SQL_DELETE_BY_ID);
			preparedStatement.setInt(1, idProdottoVenditaLibera);
			int affestedRows = preparedStatement.executeUpdate();
			if(affestedRows == 0){
				logger.error("La cancellazione non è andata a buon fine: nessun record da cancellare");
				return false;
			}
			ret = true;
		}catch(SQLException ex){
			logger.error("La cancellazione non è andata a buon fine!",ex);
		}finally{
			DAOUtil.close(conn, preparedStatement);
		}
		return ret; 
	}

	public boolean deleteByIdvenditaLiberaLibera(int idVenditaLibera){
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		boolean ret = false;
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = conn.prepareStatement(SQL_DELETE_BY_ID_VENDITA_LIBERA);
			preparedStatement.setInt(1, idVenditaLibera);
			int affestedRows = preparedStatement.executeUpdate();
			if(affestedRows == 0){
				logger.error("La cancellazione non è andata a buon fine: nessun record da cancellare");
				return false;
			}
			ret = true;
		}catch(SQLException ex){
			logger.error("La cancellazione non è andata a buon fine!",ex);
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

	
	private ProdottiVenditaLibera find(String sql,Object...values){
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ProdottiVenditaLibera prodotto = new ProdottiVenditaLibera();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(conn, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				if(resultSet.next()){
					prodotto = DAOUtil.mapProdottoVenditaLibera(resultSet); 
					}
				  }
				}catch(SQLException ex){
					logger.error(ex);
			}finally {
				DAOUtil.close(conn, preparedStatement, resultSet);
			}
		return prodotto;
	}
	
	private ArrayList<ProdottiVenditaLibera> findList(String sql,Object...values){
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<ProdottiVenditaLibera> elenco = new ArrayList<ProdottiVenditaLibera>();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(conn, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				while(resultSet.next()){
					elenco.add(DAOUtil.mapProdottoVenditaLibera(resultSet));
				}
			}
		}catch(SQLException ex){
			logger.error(ex);
		}finally{
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return elenco;
	}


}

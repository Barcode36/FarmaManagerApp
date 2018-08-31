package com.klugesoftware.farmamanager.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.klugesoftware.farmamanager.model.ProdottiVenditaLibera;
import com.klugesoftware.farmamanager.model.ProdottiVenditaSSN;

public class ProdottiVenditaSSNDAO {
	private final Logger logger = LogManager.getLogger(ProdottiVenditaSSNDAO.class.getName());
	private static final String SQL_INSERT = "INSERT INTO ProdottiVenditaSSN (idProdottoVenditaSSN,numreg,idVenditaSSN,"
			+ "posizioneInVendita,minsan,descrizione,prezzoFustello,prezzoPraticato,prezzoRimborso,"
			+ "quotaAssistito,quotaPezzo,quantita,codiceAtcGmp,DeGrassi,tipoProdotto,tipoRicetta,aliquotaIva,"
			+ "costoCompresoIva,costoNettoIva,tipoCosto,percentualeScontoSSN,valoreScontoSSNExtra,"
			+ "valoreScontoAifa,valoreScontoSSN,scontoVenditaRipartito,totaleScontoUnitario,prezzoVenditaNetto,"
			+ "profittoUnitario,percentualeMargineUnitario,percentualeRicaricoUnitario,dataVendita,quantitaReso) "
			+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
	
	private static final String SQL_UPDATE = "UPDATE ProdottiVenditaSSN SET numreg = ?,idVenditaSSN = ?,"
			+ "posizioneInVendita = ?,minsan = ?,descrizione = ?,prezzoFustello = ?,prezzoPraticato = ?,prezzoRimborso = ?,"
			+ "quotaAssistito = ?,quotaPezzo = ?,quantita = ?,codiceAtcGmp = ?,DeGrassi = ?,tipoProdotto = ?,tipoRicetta = ?,aliquotaIva = ?,"
			+ "costoCompresoIva = ?,costoNettoIva = ?,tipoCosto = ?,percentualeScontoSSN = ?,valoreScontoSSNExtra = ?,"
			+ "valoreScontoAifa = ?,valoreScontoSSN = ?,scontoVenditaRipartito = ?,totaleScontoUnitario = ?,prezzoVenditaNetto = ?,"
			+ "profittoUnitario = ?,percentualeMargineUnitario = ?,percentualeRicaricoUnitario = ?,dataVendita = ?,quantitaReso = ? "
			+ " WHERE idProdottoVenditaSSN = ?"; 

	private static final String SQL_FIND_BY_ID_PRODOTTO = "SELECT * FROM ProdottiVenditaSSN WHERE idProdottoVenditaSSN = ? ";
	
	private static final String SQL_FIND_BY_ID_VENDITA_SSN = "SELECT * FROM ProdottiVenditaSSN WHERE idVenditaSSN = ? ";
	
	private static final String SQL_FIND_PRODOTTO_PER_RESO = "SELECT * FROM ProdottiVenditaSSN "
			+ "WHERE idProdottoVenditaSSN = ("
			+ "SELECT MAX(idProdottoVenditaSSN) FROM ProdottiVenditaSSN "
			+ "WHERE minsan = ? "
			+ "AND dataVendita < ? "
			+ "AND (quantita-quantitaReso) > 0 )";

	
	private DAOFactory daoFactory;
	
	ProdottiVenditaSSNDAO(DAOFactory daoFactory){
		this.daoFactory = daoFactory;
	}
	
	public ProdottiVenditaSSN create(ProdottiVenditaSSN prodottoVenditaSSN){
		
		
		if(prodottoVenditaSSN.getIdProdottoVenditaSSN() != null){
			throw new IllegalArgumentException("Prodotto già presente! ");
		}
		
		Object[] values = {
				prodottoVenditaSSN.getIdProdottoVenditaSSN(),
				prodottoVenditaSSN.getNumreg(),
				prodottoVenditaSSN.getVenditaSSN().getIdVenditaSSN(),
				prodottoVenditaSSN.getPosizioneInVendita(),
				prodottoVenditaSSN.getMinsan(),
				prodottoVenditaSSN.getDescrizione(),
				prodottoVenditaSSN.getPrezzoFustello(),
				prodottoVenditaSSN.getPrezzoPraticato(),
				prodottoVenditaSSN.getPrezzoRimborso(),
				prodottoVenditaSSN.getQuotaAssistito(),
				prodottoVenditaSSN.getQuotaPezzo(),
				prodottoVenditaSSN.getQuantita(),
				prodottoVenditaSSN.getCodiceAtcGmp(),
				prodottoVenditaSSN.getDeGrassi(),
				prodottoVenditaSSN.getTipoProdotto(),
				prodottoVenditaSSN.getTipoRicetta(),
				prodottoVenditaSSN.getAliquotaIva(),
				prodottoVenditaSSN.getCostoCompresoIva(),
				prodottoVenditaSSN.getCostoNettoIva(),
				prodottoVenditaSSN.getTipoCosto().toString(),
				prodottoVenditaSSN.getPercentualeScontoSSN(),
				prodottoVenditaSSN.getValoreScontoSSNExtra(),
				prodottoVenditaSSN.getValoreScontoAifa(),
				prodottoVenditaSSN.getValoreScontoSSN(),
				prodottoVenditaSSN.getScontoVenditaRipartito(),
				prodottoVenditaSSN.getTotaleScontoUnitario(),
				prodottoVenditaSSN.getPrezzoVenditaNetto(),
				prodottoVenditaSSN.getProfittoUnitario(),
				prodottoVenditaSSN.getPercentualeMargineUnitario(),
				prodottoVenditaSSN.getPercentualeRicaricoUnitario(),
				prodottoVenditaSSN.getDataVendita(),
				prodottoVenditaSSN.getQuantitaReso()
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
				prodottoVenditaSSN.setIdProdottoVenditaSSN(new Integer(generetedKey.getInt(1)));
			}else{
				throw new SQLException("La creazione di un nuovo prodotto non è andata a buon fine: non è stato creato nessun record nel database!");
			}
		}catch(Exception ex){
			logger.error(ex);
		}finally{
			DAOUtil.close(connection, preparedStatement, generetedKey);
		}
		return prodottoVenditaSSN;
	}

	public ProdottiVenditaSSN update(ProdottiVenditaSSN prodottoVenditaSSN){
		
		if(prodottoVenditaSSN.getIdProdottoVenditaSSN() == null){
			throw new IllegalArgumentException("Il Prodotto ha un id nullo: non è stato ancora creato!");
		}
		
		Object[] values = {			
				prodottoVenditaSSN.getNumreg(),
				prodottoVenditaSSN.getVenditaSSN().getIdVenditaSSN(),
				prodottoVenditaSSN.getPosizioneInVendita(),
				prodottoVenditaSSN.getMinsan(),
				prodottoVenditaSSN.getDescrizione(),
				prodottoVenditaSSN.getPrezzoFustello(),
				prodottoVenditaSSN.getPrezzoPraticato(),
				prodottoVenditaSSN.getPrezzoRimborso(),
				prodottoVenditaSSN.getQuotaAssistito(),
				prodottoVenditaSSN.getQuotaPezzo(),
				prodottoVenditaSSN.getQuantita(),
				prodottoVenditaSSN.getCodiceAtcGmp(),
				prodottoVenditaSSN.getDeGrassi(),
				prodottoVenditaSSN.getTipoProdotto(),
				prodottoVenditaSSN.getTipoRicetta(),
				prodottoVenditaSSN.getAliquotaIva(),
				prodottoVenditaSSN.getCostoCompresoIva(),
				prodottoVenditaSSN.getCostoNettoIva(),
				prodottoVenditaSSN.getTipoCosto().toString(),
				prodottoVenditaSSN.getPercentualeScontoSSN(),
				prodottoVenditaSSN.getValoreScontoSSNExtra(),
				prodottoVenditaSSN.getValoreScontoAifa(),
				prodottoVenditaSSN.getValoreScontoSSN(),
				prodottoVenditaSSN.getScontoVenditaRipartito(),
				prodottoVenditaSSN.getTotaleScontoUnitario(),
				prodottoVenditaSSN.getPrezzoVenditaNetto(),
				prodottoVenditaSSN.getProfittoUnitario(),
				prodottoVenditaSSN.getPercentualeMargineUnitario(),
				prodottoVenditaSSN.getPercentualeRicaricoUnitario(),
				prodottoVenditaSSN.getDataVendita(),
				prodottoVenditaSSN.getQuantitaReso(),
				prodottoVenditaSSN.getIdProdottoVenditaSSN(),
		};
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try{
			connection = daoFactory.getConnetcion();
			preparedStatement =  DAOUtil.prepareStatement(connection, SQL_UPDATE, true, values);
			int affectedRows = preparedStatement.executeUpdate();
			if (affectedRows == 0)
				logger.warn("La modifica non è andata a buon fine: non è stato modifica nessun record!");
		}catch(Exception ex){
			logger.error(ex);
		}finally{
			DAOUtil.close(connection, preparedStatement);
		}
		return prodottoVenditaSSN;
	}

	
	public ProdottiVenditaSSN lookupProdottoById(int idProdotto){
		return find(SQL_FIND_BY_ID_PRODOTTO,idProdotto);
	}
	
	public ProdottiVenditaSSN lookupProdottoPerReso(String minsan,Date dataReso) {
		return find(SQL_FIND_PRODOTTO_PER_RESO, minsan,dataReso);
	}
	
	public ArrayList<ProdottiVenditaSSN> lookupElencoByIdVenditaSSN(int idVenditaSSN){
		return findList(SQL_FIND_BY_ID_VENDITA_SSN, idVenditaSSN);
	}
	
	private ProdottiVenditaSSN find(String sql,Object...values){
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ProdottiVenditaSSN prodotto = new ProdottiVenditaSSN();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(conn, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			if(resultSet!=null) {
				if(resultSet.next()){
					prodotto = DAOUtil.mapProdottoVenditaSSN(resultSet);
				}
			  }
			}catch(SQLException ex){
				logger.error(ex);
		}finally {
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return prodotto;
	}

	private ArrayList<ProdottiVenditaSSN> findList(String sql,Object...values){
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<ProdottiVenditaSSN> elenco = new ArrayList<ProdottiVenditaSSN>();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = DAOUtil.prepareStatement(conn, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			if(resultSet != null) {
				while(resultSet.next()){
					elenco.add(DAOUtil.mapProdottoVenditaSSN(resultSet));
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

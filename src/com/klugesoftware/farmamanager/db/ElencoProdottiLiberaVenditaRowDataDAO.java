package com.klugesoftware.farmamanager.db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import com.klugesoftware.farmamanager.DTO.ElencoProdottiLiberaVenditaRowData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ElencoProdottiLiberaVenditaRowDataDAO {

	private final Logger logger = LogManager.getLogger(ElencoProdottiLiberaVenditaRowDataDAO.class.getName());
	private static final String SQL_FIND_BETWEEN_DATE = ""
			+ "SELECT p.idProdottoVenditaLibera,p.dataVendita,p.minsan,p.descrizione,(p.quantita-p.quantitaReso) as quantitaVendute,p.prezzoVendita,"
			+ "p.totaleScontoUnitario,p.prezzoVenditaNetto,p.costoNettoIva,p.percentualeMargineUnitario,"
			+ "p.percentualeRicaricoUnitario,p.profittoUnitario "
			+ "FROM ProdottiVenditaLibera p WHERE p.dataVendita BETWEEN ? AND ?  "
			+ "AND (p.quantita-p.quantitaReso)>0 ";
	
	private static final String SQL_FIND_LIKE_BETWEEN_DATE = SQL_FIND_BETWEEN_DATE + " "
			+ "AND (p.descrizione LIKE ? OR p.minsan LIKE ?)";
	
	private static final String SQL_FIND_BY_PREZZO_VENDITA_MAGGIORE_DI = SQL_FIND_BETWEEN_DATE + " "
			+ "AND (p.prezzoVendita > ?)";

	private static final String SQL_FIND_BY_PREZZO_VENDITA_MINORE_DI = SQL_FIND_BETWEEN_DATE + " "
			+ "AND (p.prezzoVendita < ?)";

	private static final String SQL_FIND_BY_PREZZO_VENDITA_UGUALE_A = SQL_FIND_BETWEEN_DATE + " "
			+ "AND (p.prezzoVendita = ?)";

	private static final String SQL_FIND_BY_PREZZO_VENDITA_NETTO_MAGGIORE_DI = SQL_FIND_BETWEEN_DATE + " "
			+ "AND (p.prezzoVenditaNetto > ?)";

	private static final String SQL_FIND_BY_PREZZO_VENDITA_NETTO_MINORE_DI = SQL_FIND_BETWEEN_DATE + " "
			+ "AND (p.prezzoVenditaNetto < ?)";

	private static final String SQL_FIND_BY_PREZZO_VENDITA_NETTO_UGUALE_A = SQL_FIND_BETWEEN_DATE + " "
			+ "AND (p.prezzoVenditaNetto = ?)";
	
	private static final String SQL_FIND_BY_COSTO_NETTO_MAGGIORE_DI = SQL_FIND_BETWEEN_DATE + " "
			+ "AND (p.costoNettoIva > ?)";

	private static final String SQL_FIND_BY_COSTO_NETTO_MINORE_DI = SQL_FIND_BETWEEN_DATE + " "
			+ "AND (p.costoNettoIva < ?)";

	private static final String SQL_FIND_BY_COSTO_NETTO_UGUALE_A = SQL_FIND_BETWEEN_DATE + " "
			+ "AND (p.costoNettoIva = ?)";
	
	private static final String SQL_FIND_BY_QUANTITA_MAGGIORE_DI = SQL_FIND_BETWEEN_DATE + " "
			+ "AND (p.quantita-p.quantitaReso > ?)";

	private static final String SQL_FIND_BY_QUANTITA_MINORE_DI = SQL_FIND_BETWEEN_DATE + " "
			+ "AND (p.quantita-p.quantitaReso < ?)";

	private static final String SQL_FIND_BY_QUANTITA_UGUALE_A = SQL_FIND_BETWEEN_DATE + " "
			+ "AND (p.quantita-p.quantitaReso = ?)";

	private static final String SQL_FIND_BY_SCONTO_UNITARIO_MAGGIORE_DI = SQL_FIND_BETWEEN_DATE + " "
			+ "AND (p.totaleScontoUnitario > ?)";

	private static final String SQL_FIND_BY_SCONTO_UNITARIO_MINORE_DI = SQL_FIND_BETWEEN_DATE + " "
			+ "AND (p.totaleScontoUnitario < ?)";

	private static final String SQL_FIND_BY_SCONTO_UNITARIO_UGUALE_A = SQL_FIND_BETWEEN_DATE + " "
			+ "AND (p.totaleScontoUnitario = ?)";
	
	private static final String SQL_FIND_BY_MARGINE_UNITARIO_MAGGIORE_DI = SQL_FIND_BETWEEN_DATE + " "
			+ "AND (p.percentualeMargineUnitario > ?)";

	private static final String SQL_FIND_BY_MARGINE_UNITARIO_MINORE_DI = SQL_FIND_BETWEEN_DATE + " "
			+ "AND (p.percentualeMargineUnitario < ?)";

	private static final String SQL_FIND_BY_MARGINE_UNITARIO_UGUALE_A = SQL_FIND_BETWEEN_DATE + " "
			+ "AND (p.percentualeMargineUnitario = ?)";
	
	private static final String SQL_FIND_BY_RICARICO_UNITARIO_MAGGIORE_DI = SQL_FIND_BETWEEN_DATE + " "
			+ "AND (p.percentualeRicaricoUnitario > ?)";

	private static final String SQL_FIND_BY_RICARICO_UNITARIO_MINORE_DI = SQL_FIND_BETWEEN_DATE + " "
			+ "AND (p.percentualeRicaricoUnitario < ?)";

	private static final String SQL_FIND_BY_RICARICO_UNITARIO_UGUALE_A = SQL_FIND_BETWEEN_DATE + " "
			+ "AND (p.percentualeRicaricoUnitario = ?)";
	
	private static final String SQL_FIND_BY_PROFITTO_UNITARIO_MAGGIORE_DI = SQL_FIND_BETWEEN_DATE + " "
			+ "AND (p.profittoUnitario > ?)";

	private static final String SQL_FIND_BY_PROFITTO_UNITARIO_MINORE_DI = SQL_FIND_BETWEEN_DATE + " "
			+ "AND (p.profittoUnitario < ?)";

	private static final String SQL_FIND_BY_PROFITTO_UNITARIO_UGUALE_A = SQL_FIND_BETWEEN_DATE + " "
			+ "AND (p.profittoUnitario = ?)";
	

	private DAOFactory daoFactory;
	
	public ElencoProdottiLiberaVenditaRowDataDAO(DAOFactory daoFactory){
		this.daoFactory = daoFactory;
	}
	
	public ArrayList<ElencoProdottiLiberaVenditaRowData> elencoBetweenDate(Date dateFrom,Date dateTo){
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<ElencoProdottiLiberaVenditaRowData> elenco = new ArrayList<ElencoProdottiLiberaVenditaRowData>();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = conn.prepareStatement(SQL_FIND_BETWEEN_DATE);
			preparedStatement.setDate(1, new java.sql.Date(dateFrom.getTime()));
			preparedStatement.setDate(2, new java.sql.Date(dateTo.getTime()));
			resultSet = preparedStatement.executeQuery();
			if(resultSet == null){
				JOptionPane.showMessageDialog(null, "La ricerca ha restituito un insieme vuoto.", "Search Between Date", JOptionPane.INFORMATION_MESSAGE);
			}
			else{
				while(resultSet.next()){
					elenco.add(DAOUtil.mapElencoProdottiLiberaVenditaRowData(resultSet));
				}
			}
		}catch(SQLException ex){
			logger.error(ex.getMessage());
		}finally{
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return elenco;

	}
	
	public ArrayList<ElencoProdottiLiberaVenditaRowData> elencoLikeBetweenDate(Date dateFrom,Date dateTo,String search){
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<ElencoProdottiLiberaVenditaRowData> elenco = new ArrayList<ElencoProdottiLiberaVenditaRowData>();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = conn.prepareStatement(SQL_FIND_LIKE_BETWEEN_DATE);
			preparedStatement.setDate(1, new java.sql.Date(dateFrom.getTime()));
			preparedStatement.setDate(2, new java.sql.Date(dateTo.getTime()));
			preparedStatement.setString(3, "%"+search+"%");
			preparedStatement.setString(4, "%"+search+"%");
			resultSet = preparedStatement.executeQuery();
			if(resultSet == null){
				JOptionPane.showMessageDialog(null, "La ricerca ha restituito un insieme vuoto.", "Search Between Date", JOptionPane.INFORMATION_MESSAGE);
			}
			else{
				while(resultSet.next()){
					elenco.add(DAOUtil.mapElencoProdottiLiberaVenditaRowData(resultSet));
				}
			}
		}catch(SQLException ex){
			logger.error(ex.getMessage());
		}finally{
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return elenco;
	}
	
	public ArrayList<ElencoProdottiLiberaVenditaRowData> elencoFiltroPrezzoVendita(Date dateFrom,Date dateTo, BigDecimal prezzoVendita, String segno){
		String query = "";
		switch(segno){
		case "MAGGIORE": query = SQL_FIND_BY_PREZZO_VENDITA_MAGGIORE_DI;break;
		case "MINORE": query = SQL_FIND_BY_PREZZO_VENDITA_MINORE_DI;break;
		case "UGUALE": query = SQL_FIND_BY_PREZZO_VENDITA_UGUALE_A;break;
		}
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<ElencoProdottiLiberaVenditaRowData> elenco = new ArrayList<ElencoProdottiLiberaVenditaRowData>();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setDate(1, new java.sql.Date(dateFrom.getTime()));
			preparedStatement.setDate(2, new java.sql.Date(dateTo.getTime()));
			preparedStatement.setBigDecimal(3, prezzoVendita);

			resultSet = preparedStatement.executeQuery();
			if(resultSet == null){
				JOptionPane.showMessageDialog(null, "La ricerca ha restituito un insieme vuoto.", "Search Between Date and Filtro", JOptionPane.INFORMATION_MESSAGE);
			}
			else{
				while(resultSet.next()){
					elenco.add(DAOUtil.mapElencoProdottiLiberaVenditaRowData(resultSet));
				}
			}
		}catch(SQLException ex){
			logger.error(ex.getMessage());
		}finally{
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return elenco;		
	}
	
	public ArrayList<ElencoProdottiLiberaVenditaRowData> elencoFiltroPrezzoVenditaNetto(Date dateFrom,Date dateTo, BigDecimal prezzoVenditaNetto, String segno){
		String query = "";
		switch(segno){
		case "MAGGIORE": query = SQL_FIND_BY_PREZZO_VENDITA_NETTO_MAGGIORE_DI;break;
		case "MINORE": query = SQL_FIND_BY_PREZZO_VENDITA_NETTO_MINORE_DI;break;
		case "UGUALE": query = SQL_FIND_BY_PREZZO_VENDITA_NETTO_UGUALE_A;break;
		}
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<ElencoProdottiLiberaVenditaRowData> elenco = new ArrayList<ElencoProdottiLiberaVenditaRowData>();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setDate(1, new java.sql.Date(dateFrom.getTime()));
			preparedStatement.setDate(2, new java.sql.Date(dateTo.getTime()));
			preparedStatement.setBigDecimal(3, prezzoVenditaNetto);

			resultSet = preparedStatement.executeQuery();
			if(resultSet == null){
				JOptionPane.showMessageDialog(null, "La ricerca ha restituito un insieme vuoto.", "Search Between Date and Filtro", JOptionPane.INFORMATION_MESSAGE);
			}
			else{
				while(resultSet.next()){
					elenco.add(DAOUtil.mapElencoProdottiLiberaVenditaRowData(resultSet));
				}
			}
		}catch(SQLException ex){
			logger.error(ex.getMessage());
		}finally{
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return elenco;		
	}

	public ArrayList<ElencoProdottiLiberaVenditaRowData> elencoFiltroCostoNetto(Date dateFrom,Date dateTo, BigDecimal costoNetto, String segno){
		String query = "";
		switch(segno){
		case "MAGGIORE": query = SQL_FIND_BY_COSTO_NETTO_MAGGIORE_DI;break;
		case "MINORE": query = SQL_FIND_BY_COSTO_NETTO_MINORE_DI;break;
		case "UGUALE": query = SQL_FIND_BY_COSTO_NETTO_UGUALE_A;break;
		}
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<ElencoProdottiLiberaVenditaRowData> elenco = new ArrayList<ElencoProdottiLiberaVenditaRowData>();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setDate(1, new java.sql.Date(dateFrom.getTime()));
			preparedStatement.setDate(2, new java.sql.Date(dateTo.getTime()));
			preparedStatement.setBigDecimal(3, costoNetto);

			resultSet = preparedStatement.executeQuery();
			if(resultSet == null){
				JOptionPane.showMessageDialog(null, "La ricerca ha restituito un insieme vuoto.", "Search Between Date and Filtro", JOptionPane.INFORMATION_MESSAGE);
			}
			else{
				while(resultSet.next()){
					elenco.add(DAOUtil.mapElencoProdottiLiberaVenditaRowData(resultSet));
				}
			}
		}catch(SQLException ex){
			logger.error(ex.getMessage());
		}finally{
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return elenco;		
	}

	public ArrayList<ElencoProdottiLiberaVenditaRowData> elencoFiltroQuantita(Date dateFrom,Date dateTo, int quantita, String segno){
		String query = "";
		switch(segno){
		case "MAGGIORE": query = SQL_FIND_BY_QUANTITA_MAGGIORE_DI;break;
		case "MINORE": query = SQL_FIND_BY_QUANTITA_MINORE_DI;break;
		case "UGUALE": query = SQL_FIND_BY_QUANTITA_UGUALE_A;break;
		}
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<ElencoProdottiLiberaVenditaRowData> elenco = new ArrayList<ElencoProdottiLiberaVenditaRowData>();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setDate(1, new java.sql.Date(dateFrom.getTime()));
			preparedStatement.setDate(2, new java.sql.Date(dateTo.getTime()));
			preparedStatement.setInt(3, quantita);

			resultSet = preparedStatement.executeQuery();
			if(resultSet == null){
				JOptionPane.showMessageDialog(null, "La ricerca ha restituito un insieme vuoto.", "Search Between Date and Filtro", JOptionPane.INFORMATION_MESSAGE);
			}
			else{
				while(resultSet.next()){
					elenco.add(DAOUtil.mapElencoProdottiLiberaVenditaRowData(resultSet));
				}
			}
		}catch(SQLException ex){
			logger.error(ex.getMessage());
		}finally{
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return elenco;		
	}

	public ArrayList<ElencoProdottiLiberaVenditaRowData> elencoFiltroScontoUnitario(Date dateFrom,Date dateTo, BigDecimal scontoUnitario, String segno){
		String query = "";
		switch(segno){
		case "MAGGIORE": query = SQL_FIND_BY_SCONTO_UNITARIO_MAGGIORE_DI;break;
		case "MINORE": query = SQL_FIND_BY_SCONTO_UNITARIO_MINORE_DI;break;
		case "UGUALE": query = SQL_FIND_BY_SCONTO_UNITARIO_UGUALE_A;break;
		}
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<ElencoProdottiLiberaVenditaRowData> elenco = new ArrayList<ElencoProdottiLiberaVenditaRowData>();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setDate(1, new java.sql.Date(dateFrom.getTime()));
			preparedStatement.setDate(2, new java.sql.Date(dateTo.getTime()));
			preparedStatement.setBigDecimal(3, scontoUnitario);

			resultSet = preparedStatement.executeQuery();
			if(resultSet == null){
				JOptionPane.showMessageDialog(null, "La ricerca ha restituito un insieme vuoto.", "Search Between Date and Filtro", JOptionPane.INFORMATION_MESSAGE);
			}
			else{
				while(resultSet.next()){
					elenco.add(DAOUtil.mapElencoProdottiLiberaVenditaRowData(resultSet));
				}
			}
		}catch(SQLException ex){
			logger.error(ex.getMessage());
		}finally{
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return elenco;		
	}

	public ArrayList<ElencoProdottiLiberaVenditaRowData> elencoFiltroMargineUnitario(Date dateFrom,Date dateTo, BigDecimal margineUnitario, String segno){
		String query = "";
		switch(segno){
		case "MAGGIORE": query = SQL_FIND_BY_MARGINE_UNITARIO_MAGGIORE_DI;break;
		case "MINORE": query = SQL_FIND_BY_MARGINE_UNITARIO_MINORE_DI;break;
		case "UGUALE": query = SQL_FIND_BY_MARGINE_UNITARIO_UGUALE_A;break;
		}
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<ElencoProdottiLiberaVenditaRowData> elenco = new ArrayList<ElencoProdottiLiberaVenditaRowData>();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setDate(1, new java.sql.Date(dateFrom.getTime()));
			preparedStatement.setDate(2, new java.sql.Date(dateTo.getTime()));
			preparedStatement.setBigDecimal(3, margineUnitario);

			resultSet = preparedStatement.executeQuery();
			if(resultSet == null){
				JOptionPane.showMessageDialog(null, "La ricerca ha restituito un insieme vuoto.", "Search Between Date and Filtro", JOptionPane.INFORMATION_MESSAGE);
			}
			else{
				while(resultSet.next()){
					elenco.add(DAOUtil.mapElencoProdottiLiberaVenditaRowData(resultSet));
				}
			}
		}catch(SQLException ex){
			logger.error(ex.getMessage());
		}finally{
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return elenco;		
	}

	public ArrayList<ElencoProdottiLiberaVenditaRowData> elencoFiltroRicaricoUnitario(Date dateFrom,Date dateTo, BigDecimal ricaricoUnitario, String segno){
		String query = "";
		switch(segno){
		case "MAGGIORE": query = SQL_FIND_BY_RICARICO_UNITARIO_MAGGIORE_DI;break;
		case "MINORE": query = SQL_FIND_BY_RICARICO_UNITARIO_MINORE_DI;break;
		case "UGUALE": query = SQL_FIND_BY_RICARICO_UNITARIO_UGUALE_A;break;
		}
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<ElencoProdottiLiberaVenditaRowData> elenco = new ArrayList<ElencoProdottiLiberaVenditaRowData>();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setDate(1, new java.sql.Date(dateFrom.getTime()));
			preparedStatement.setDate(2, new java.sql.Date(dateTo.getTime()));
			preparedStatement.setBigDecimal(3, ricaricoUnitario);

			resultSet = preparedStatement.executeQuery();
			if(resultSet == null){
				JOptionPane.showMessageDialog(null, "La ricerca ha restituito un insieme vuoto.", "Search Between Date and Filtro", JOptionPane.INFORMATION_MESSAGE);
			}
			else{
				while(resultSet.next()){
					elenco.add(DAOUtil.mapElencoProdottiLiberaVenditaRowData(resultSet));
				}
			}
		}catch(SQLException ex){
			logger.error(ex.getMessage());
		}finally{
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return elenco;		
	}

	public ArrayList<ElencoProdottiLiberaVenditaRowData> elencoFiltroProfittoUnitario(Date dateFrom,Date dateTo, BigDecimal profittoUnitario, String segno){
		String query = "";
		switch(segno){
		case "MAGGIORE": query = SQL_FIND_BY_PROFITTO_UNITARIO_MAGGIORE_DI;break;
		case "MINORE": query = SQL_FIND_BY_PROFITTO_UNITARIO_MINORE_DI;break;
		case "UGUALE": query = SQL_FIND_BY_PROFITTO_UNITARIO_UGUALE_A;break;
		}
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<ElencoProdottiLiberaVenditaRowData> elenco = new ArrayList<ElencoProdottiLiberaVenditaRowData>();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setDate(1, new java.sql.Date(dateFrom.getTime()));
			preparedStatement.setDate(2, new java.sql.Date(dateTo.getTime()));
			preparedStatement.setBigDecimal(3, profittoUnitario);

			resultSet = preparedStatement.executeQuery();
			if(resultSet == null){
				JOptionPane.showMessageDialog(null, "La ricerca ha restituito un insieme vuoto.", "Search Between Date and Filtro", JOptionPane.INFORMATION_MESSAGE);
			}
			else{
				while(resultSet.next()){
					elenco.add(DAOUtil.mapElencoProdottiLiberaVenditaRowData(resultSet));
				}
			}
		}catch(SQLException ex){
			logger.error(ex.getMessage());
		}finally{
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return elenco;		
	}

}

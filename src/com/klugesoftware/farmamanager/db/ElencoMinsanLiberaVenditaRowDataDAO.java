package com.klugesoftware.farmamanager.db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

import com.klugesoftware.farmamanager.DTO.ElencoProdottiLiberaVenditaRowData;
import com.klugesoftware.farmamanager.DTO.ElencoMinsanLiberaVenditaRowData;

public class ElencoMinsanLiberaVenditaRowDataDAO {
	private static final String SQL_FIND_BETWEEN_DATE = "SELECT "
			+ "minsan,"
			+ "descrizione,"
			+ "sum(quantita-quantitaReso) as quantitaTotale,"
			+ "sum(prezzoVendita*(quantita-quantitaReso))/sum(quantita-quantitaReso) as prezzoVenditaMedio,"
			+ "sum(totaleScontoUnitario*(quantita-quantitaReso))/sum(quantita-quantitaReso) as scontoMedio,"
			+ "sum(costoNettoIva*(quantita-quantitaReso))/sum(quantita-quantitaReso) as costoMedio,"
			+ "sum(prezzoVenditaNetto*(quantita-quantitaReso))/sum((quantita-quantitaReso)) as prezzoVenditaNettoMedio, "
			+ "sum(prezzoVenditaNetto*(quantita-quantitaReso))/sum(quantita-quantitaReso)-sum(costoNettoIva*(quantita-quantitaReso))/sum((quantita-quantitaReso)) as profittoMedio, "
			+ "(sum(prezzoVenditaNetto*(quantita-quantitaReso))/sum(quantita-quantitaReso)-sum(costoNettoIva*(quantita-quantitaReso))/sum((quantita-quantitaReso)))/( sum(costoNettoIva*(quantita-quantitaReso))/sum(quantita-quantitaReso) )*100 as ricaricoMedio,"
			+ "(sum(prezzoVenditaNetto*(quantita-quantitaReso))/sum(quantita-quantitaReso)-sum(costoNettoIva*(quantita-quantitaReso))/sum((quantita-quantitaReso)))/(sum(prezzoVenditaNetto*(quantita-quantitaReso))/sum(quantita-quantitaReso))*100 as margineMedio "
			+ "FROM ProdottiVenditaLibera WHERE dataVendita BETWEEN ? AND ?  "
			+ "AND (quantita-quantitaReso) > 0 "
			+ "GROUP BY minsan ";
			
	
	private static final String SQL_FIND_LIKE_BETWEEN_DATE = SQL_FIND_BETWEEN_DATE + " "
			+ "HAVING (descrizione LIKE ? OR minsan LIKE ?)";
		
	private static final String SQL_FIND_BY_QUANTITA_MAGGIORE_DI = SQL_FIND_BETWEEN_DATE + " "
			+ "HAVING (quantitaTotale > ?)";

	private static final String SQL_FIND_BY_QUANTITA_MINORE_DI = SQL_FIND_BETWEEN_DATE + " "
			+ "HAVING (quantitaTotale < ?)";

	private static final String SQL_FIND_BY_QUANTITA_UGUALE_A = SQL_FIND_BETWEEN_DATE + " "
			+ "HAVING (quantitaTotale = ?)";
	
	
	private static final String SQL_FIND_BY_PREZZO_VENDITA_MEDIO_MAGGIORE_DI = SQL_FIND_BETWEEN_DATE + " "
			+ "HAVING (prezzoVenditaMedio > ?)";

	private static final String SQL_FIND_BY_PREZZO_VENDITA_MEDIO_MINORE_DI = SQL_FIND_BETWEEN_DATE + " "
			+ "HAVING (prezzoVenditaMedio < ?)";

	private static final String SQL_FIND_BY_PREZZO_VENDITA_MEDIO_UGUALE_A = SQL_FIND_BETWEEN_DATE + " "
			+ "HAVING (prezzoVenditaMedio = ?)";

	private static final String SQL_FIND_BY_PREZZO_VENDITA_MEDIO_NETTO_MAGGIORE_DI = SQL_FIND_BETWEEN_DATE + " "
			+ "HAVING (prezzoVenditaNettoMedio > ?)";

	private static final String SQL_FIND_BY_PREZZO_VENDITA_MEDIO_NETTO_MINORE_DI = SQL_FIND_BETWEEN_DATE + " "
			+ "HAVING (prezzoVenditaNettoMedio < ?)";

	private static final String SQL_FIND_BY_PREZZO_VENDITA_MEDIO_NETTO_UGUALE_A = SQL_FIND_BETWEEN_DATE + " "
			+ "HAVING (prezzoVenditaNettoMedio = ?)";
	
	private static final String SQL_FIND_BY_COSTO_NETTO_MEDIO_MAGGIORE_DI = SQL_FIND_BETWEEN_DATE + " "
			+ "HAVING (costoMedio > ?)";

	private static final String SQL_FIND_BY_COSTO_NETTO_MEDIO_MINORE_DI = SQL_FIND_BETWEEN_DATE + " "
			+ "HAVING (costoMedio < ?)";

	private static final String SQL_FIND_BY_COSTO_NETTO_MEDIO_UGUALE_A = SQL_FIND_BETWEEN_DATE + " "
			+ "HAVING (costoMedio = ?)";
	
	private static final String SQL_FIND_BY_SCONTO_MEDIO_MAGGIORE_DI = SQL_FIND_BETWEEN_DATE + " "
			+ "HAVING (scontoMedio > ?)";

	private static final String SQL_FIND_BY_SCONTO_MEDIO_MINORE_DI = SQL_FIND_BETWEEN_DATE + " "
			+ "HAVING (scontoMedio < ?)";

	private static final String SQL_FIND_BY_SCONTO_MEDIO_UGUALE_A = SQL_FIND_BETWEEN_DATE + " "
			+ "HAVING (scontoMedio = ?)";
	
	private static final String SQL_FIND_BY_MARGINE_MEDIO_MAGGIORE_DI = SQL_FIND_BETWEEN_DATE + " "
			+ "HAVING (margineMedio > ?)";

	private static final String SQL_FIND_BY_MARGINE_MEDIO_MINORE_DI = SQL_FIND_BETWEEN_DATE + " "
			+ "HAVING (margineMedio < ?)";

	private static final String SQL_FIND_BY_MARGINE_MEDIO_UGUALE_A = SQL_FIND_BETWEEN_DATE + " "
			+ "HAVING (margineMedio = ?)";
	
	private static final String SQL_FIND_BY_RICARICO_MEDIO_MAGGIORE_DI = SQL_FIND_BETWEEN_DATE + " "
			+ "HAVING (ricaricoMedio > ?)";

	private static final String SQL_FIND_BY_RICARICO_MEDIO_MINORE_DI = SQL_FIND_BETWEEN_DATE + " "
			+ "HAVING (ricaricoMedio < ?)";

	private static final String SQL_FIND_BY_RICARICO_MEDIO_UGUALE_A = SQL_FIND_BETWEEN_DATE + " "
			+ "HAVING (ricaricoMedio = ?)";
	
	private static final String SQL_FIND_BY_PROFITTO_MEDIO_MAGGIORE_DI = SQL_FIND_BETWEEN_DATE + " "
			+ "HAVING (profittoMedio > ?)";

	private static final String SQL_FIND_BY_PROFITTO_MEDIO_MINORE_DI = SQL_FIND_BETWEEN_DATE + " "
			+ "HAVING (profittoMedio < ?)";

	private static final String SQL_FIND_BY_PROFITTO_MEDIO_UGUALE_A = SQL_FIND_BETWEEN_DATE + " "
			+ "HAVING (profittoMedio = ?)";


	private DAOFactory daoFactory;
	
	public ElencoMinsanLiberaVenditaRowDataDAO(DAOFactory daoFactory){
		this.daoFactory = daoFactory;
	}
	
	public ArrayList<ElencoMinsanLiberaVenditaRowData> elencoBetweenDate(Date dateFrom,Date dateTo){
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<ElencoMinsanLiberaVenditaRowData> elenco = new ArrayList<ElencoMinsanLiberaVenditaRowData>();
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
					elenco.add(DAOUtil.mapElencoMinsanLiberaVenditaRowData(resultSet));
				}
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}finally{
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return elenco;

	}
	
	public ArrayList<ElencoMinsanLiberaVenditaRowData> elencoLikeBetweenDate(Date dateFrom,Date dateTo,String search){
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<ElencoMinsanLiberaVenditaRowData> elenco = new ArrayList<ElencoMinsanLiberaVenditaRowData>();
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
					elenco.add(DAOUtil.mapElencoMinsanLiberaVenditaRowData(resultSet));
				}
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}finally{
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return elenco;
	}

	public ArrayList<ElencoMinsanLiberaVenditaRowData> elencoFiltroPrezzoVenditaMedio(Date dateFrom,Date dateTo, BigDecimal prezzoVendita, String segno){
		String query = "";
		switch(segno){
		case "MAGGIORE": query = SQL_FIND_BY_PREZZO_VENDITA_MEDIO_MAGGIORE_DI;break;
		case "MINORE": query = SQL_FIND_BY_PREZZO_VENDITA_MEDIO_MINORE_DI;break;
		case "UGUALE": query = SQL_FIND_BY_PREZZO_VENDITA_MEDIO_UGUALE_A;break;
		}
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<ElencoMinsanLiberaVenditaRowData> elenco = new ArrayList<ElencoMinsanLiberaVenditaRowData>();
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
					elenco.add(DAOUtil.mapElencoMinsanLiberaVenditaRowData(resultSet));
				}
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}finally{
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return elenco;		
	}
	
	public ArrayList<ElencoMinsanLiberaVenditaRowData> elencoFiltroPrezzoVenditaNettoMedio(Date dateFrom,Date dateTo, BigDecimal prezzoVenditaNetto, String segno){
		String query = "";
		switch(segno){
		case "MAGGIORE": query = SQL_FIND_BY_PREZZO_VENDITA_MEDIO_NETTO_MAGGIORE_DI;break;
		case "MINORE": query = SQL_FIND_BY_PREZZO_VENDITA_MEDIO_NETTO_MINORE_DI;break;
		case "UGUALE": query = SQL_FIND_BY_PREZZO_VENDITA_MEDIO_NETTO_UGUALE_A;break;
		}
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<ElencoMinsanLiberaVenditaRowData> elenco = new ArrayList<ElencoMinsanLiberaVenditaRowData>();
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
					elenco.add(DAOUtil.mapElencoMinsanLiberaVenditaRowData(resultSet));
				}
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}finally{
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return elenco;		
	}

	public ArrayList<ElencoMinsanLiberaVenditaRowData> elencoFiltroCostoNettoMedio(Date dateFrom,Date dateTo, BigDecimal costoNetto, String segno){
		String query = "";
		switch(segno){
		case "MAGGIORE": query = SQL_FIND_BY_COSTO_NETTO_MEDIO_MAGGIORE_DI;break;
		case "MINORE": query = SQL_FIND_BY_COSTO_NETTO_MEDIO_MINORE_DI;break;
		case "UGUALE": query = SQL_FIND_BY_COSTO_NETTO_MEDIO_UGUALE_A;break;
		}
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<ElencoMinsanLiberaVenditaRowData> elenco = new ArrayList<ElencoMinsanLiberaVenditaRowData>();
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
					elenco.add(DAOUtil.mapElencoMinsanLiberaVenditaRowData(resultSet));
				}
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}finally{
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return elenco;		
	}

	public ArrayList<ElencoMinsanLiberaVenditaRowData> elencoFiltroQuantita(Date dateFrom,Date dateTo, int quantita, String segno){
		String query = "";
		switch(segno){
		case "MAGGIORE": query = SQL_FIND_BY_QUANTITA_MAGGIORE_DI;break;
		case "MINORE": query = SQL_FIND_BY_QUANTITA_MINORE_DI;break;
		case "UGUALE": query = SQL_FIND_BY_QUANTITA_UGUALE_A;break;
		}
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<ElencoMinsanLiberaVenditaRowData> elenco = new ArrayList<ElencoMinsanLiberaVenditaRowData>();
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
					elenco.add(DAOUtil.mapElencoMinsanLiberaVenditaRowData(resultSet));
				}
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}finally{
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return elenco;		
	}

	public ArrayList<ElencoMinsanLiberaVenditaRowData> elencoFiltroScontoMedio(Date dateFrom,Date dateTo, BigDecimal scontoMedio, String segno){
		String query = "";
		switch(segno){
		case "MAGGIORE": query = SQL_FIND_BY_SCONTO_MEDIO_MAGGIORE_DI;break;
		case "MINORE": query = SQL_FIND_BY_SCONTO_MEDIO_MINORE_DI;break;
		case "UGUALE": query = SQL_FIND_BY_SCONTO_MEDIO_UGUALE_A;break;
		}
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<ElencoMinsanLiberaVenditaRowData> elenco = new ArrayList<ElencoMinsanLiberaVenditaRowData>();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setDate(1, new java.sql.Date(dateFrom.getTime()));
			preparedStatement.setDate(2, new java.sql.Date(dateTo.getTime()));
			preparedStatement.setBigDecimal(3, scontoMedio);

			resultSet = preparedStatement.executeQuery();
			if(resultSet == null){
				JOptionPane.showMessageDialog(null, "La ricerca ha restituito un insieme vuoto.", "Search Between Date and Filtro", JOptionPane.INFORMATION_MESSAGE);
			}
			else{
				while(resultSet.next()){
					elenco.add(DAOUtil.mapElencoMinsanLiberaVenditaRowData(resultSet));
				}
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}finally{
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return elenco;		
	}

	public ArrayList<ElencoMinsanLiberaVenditaRowData> elencoFiltroMargineMedio(Date dateFrom,Date dateTo, BigDecimal margineMedio, String segno){
		String query = "";
		switch(segno){
		case "MAGGIORE": query = SQL_FIND_BY_MARGINE_MEDIO_MAGGIORE_DI;break;
		case "MINORE": query = SQL_FIND_BY_MARGINE_MEDIO_MINORE_DI;break;
		case "UGUALE": query = SQL_FIND_BY_MARGINE_MEDIO_UGUALE_A;break;
		}
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<ElencoMinsanLiberaVenditaRowData> elenco = new ArrayList<ElencoMinsanLiberaVenditaRowData>();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setDate(1, new java.sql.Date(dateFrom.getTime()));
			preparedStatement.setDate(2, new java.sql.Date(dateTo.getTime()));
			preparedStatement.setBigDecimal(3, margineMedio);

			resultSet = preparedStatement.executeQuery();
			if(resultSet == null){
				JOptionPane.showMessageDialog(null, "La ricerca ha restituito un insieme vuoto.", "Search Between Date and Filtro", JOptionPane.INFORMATION_MESSAGE);
			}
			else{
				while(resultSet.next()){
					elenco.add(DAOUtil.mapElencoMinsanLiberaVenditaRowData(resultSet));
				}
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}finally{
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return elenco;		
	}

	public ArrayList<ElencoMinsanLiberaVenditaRowData> elencoFiltroRicaricoMedio(Date dateFrom,Date dateTo, BigDecimal ricaricoMedio, String segno){
		String query = "";
		switch(segno){
		case "MAGGIORE": query = SQL_FIND_BY_RICARICO_MEDIO_MAGGIORE_DI;break;
		case "MINORE": query = SQL_FIND_BY_RICARICO_MEDIO_MINORE_DI;break;
		case "UGUALE": query = SQL_FIND_BY_RICARICO_MEDIO_UGUALE_A;break;
		}
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<ElencoMinsanLiberaVenditaRowData> elenco = new ArrayList<ElencoMinsanLiberaVenditaRowData>();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setDate(1, new java.sql.Date(dateFrom.getTime()));
			preparedStatement.setDate(2, new java.sql.Date(dateTo.getTime()));
			preparedStatement.setBigDecimal(3, ricaricoMedio);

			resultSet = preparedStatement.executeQuery();
			if(resultSet == null){
				JOptionPane.showMessageDialog(null, "La ricerca ha restituito un insieme vuoto.", "Search Between Date and Filtro", JOptionPane.INFORMATION_MESSAGE);
			}
			else{
				while(resultSet.next()){
					elenco.add(DAOUtil.mapElencoMinsanLiberaVenditaRowData(resultSet));
				}
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}finally{
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return elenco;		
	}

	public ArrayList<ElencoMinsanLiberaVenditaRowData> elencoFiltroProfittoMedio(Date dateFrom,Date dateTo, BigDecimal profittoMedio, String segno){
		String query = "";
		switch(segno){
		case "MAGGIORE": query = SQL_FIND_BY_PROFITTO_MEDIO_MAGGIORE_DI;break;
		case "MINORE": query = SQL_FIND_BY_PROFITTO_MEDIO_MINORE_DI;break;
		case "UGUALE": query = SQL_FIND_BY_PROFITTO_MEDIO_UGUALE_A;break;
		}
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<ElencoMinsanLiberaVenditaRowData> elenco = new ArrayList<ElencoMinsanLiberaVenditaRowData>();
		try{
			conn = daoFactory.getConnetcion();
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setDate(1, new java.sql.Date(dateFrom.getTime()));
			preparedStatement.setDate(2, new java.sql.Date(dateTo.getTime()));
			preparedStatement.setBigDecimal(3, profittoMedio);

			resultSet = preparedStatement.executeQuery();
			if(resultSet == null){
				JOptionPane.showMessageDialog(null, "La ricerca ha restituito un insieme vuoto.", "Search Between Date and Filtro", JOptionPane.INFORMATION_MESSAGE);
			}
			else{
				while(resultSet.next()){
					elenco.add(DAOUtil.mapElencoMinsanLiberaVenditaRowData(resultSet));
				}
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}finally{
			DAOUtil.close(conn, preparedStatement, resultSet);
		}
		return elenco;		
	}

}

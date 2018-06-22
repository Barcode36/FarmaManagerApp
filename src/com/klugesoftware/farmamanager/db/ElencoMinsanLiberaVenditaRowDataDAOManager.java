package com.klugesoftware.farmamanager.db;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import com.klugesoftware.farmamanager.DTO.ElencoMinsanLiberaVenditaRowData;

public class ElencoMinsanLiberaVenditaRowDataDAOManager {

	public static ArrayList<ElencoMinsanLiberaVenditaRowData> lookUpElencoMinsanProdottoVenditaLiberaBetweenDate(Date dateFrom, Date dateTo){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ElencoMinsanLiberaVenditaRowDataDAO elencoMinsanDAO = daoFactory.getElencoMinsanLiberaVenditaDAO();
		return elencoMinsanDAO.elencoBetweenDate(dateFrom, dateTo);
	}
	
	public static ArrayList<ElencoMinsanLiberaVenditaRowData> lookUpElencoMinsanProdottoVenditaLiberaLikeBetweenDate(Date dateFrom, Date dateTo,String search){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ElencoMinsanLiberaVenditaRowDataDAO elencoMinsanDAO = daoFactory.getElencoMinsanLiberaVenditaDAO();
		return elencoMinsanDAO.elencoLikeBetweenDate(dateFrom, dateTo, search);
	}
	
	public static ArrayList<ElencoMinsanLiberaVenditaRowData> lookUpElencoMinsanfiltroPrezzoVenditaMedio(Date dateFrom, Date dateTo,BigDecimal prezzo, String segno){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ElencoMinsanLiberaVenditaRowDataDAO elencoMinsanDAO = daoFactory.getElencoMinsanLiberaVenditaDAO();
		return elencoMinsanDAO.elencoFiltroPrezzoVenditaMedio(dateFrom, dateTo, prezzo, segno);
	}

	public static ArrayList<ElencoMinsanLiberaVenditaRowData> lookUpElencoMinsanfiltroPrezzoVenditaNettoMedio(Date dateFrom, Date dateTo,BigDecimal prezzo, String segno){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ElencoMinsanLiberaVenditaRowDataDAO elencoMinsanDAO = daoFactory.getElencoMinsanLiberaVenditaDAO();
		return elencoMinsanDAO.elencoFiltroPrezzoVenditaNettoMedio(dateFrom, dateTo, prezzo, segno);
	}

	public static ArrayList<ElencoMinsanLiberaVenditaRowData> lookUpElencoMinsanfiltroCostoMedio(Date dateFrom, Date dateTo,BigDecimal costo, String segno){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ElencoMinsanLiberaVenditaRowDataDAO elencoMinsanDAO = daoFactory.getElencoMinsanLiberaVenditaDAO();
		return elencoMinsanDAO.elencoFiltroCostoNettoMedio(dateFrom, dateTo, costo, segno);
	}
	
	public static ArrayList<ElencoMinsanLiberaVenditaRowData> lookUpElencoMinsanfiltroScontoMedio(Date dateFrom, Date dateTo,BigDecimal sconto, String segno){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ElencoMinsanLiberaVenditaRowDataDAO elencoMinsanDAO = daoFactory.getElencoMinsanLiberaVenditaDAO();
		return elencoMinsanDAO.elencoFiltroScontoMedio(dateFrom, dateTo, sconto, segno);
	}

	public static ArrayList<ElencoMinsanLiberaVenditaRowData> lookUpElencoMinsanfiltroQuantitaTotale(Date dateFrom, Date dateTo,Integer quantita, String segno){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ElencoMinsanLiberaVenditaRowDataDAO elencoMinsanDAO = daoFactory.getElencoMinsanLiberaVenditaDAO();
		return elencoMinsanDAO.elencoFiltroQuantita(dateFrom, dateTo, quantita, segno);
	}
	
	public static ArrayList<ElencoMinsanLiberaVenditaRowData> lookUpElencoMinsanfiltroProfittoMedio(Date dateFrom, Date dateTo,BigDecimal profitto, String segno){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ElencoMinsanLiberaVenditaRowDataDAO elencoMinsanDAO = daoFactory.getElencoMinsanLiberaVenditaDAO();
		return elencoMinsanDAO.elencoFiltroProfittoMedio(dateFrom, dateTo, profitto, segno);
	}
	
	public static ArrayList<ElencoMinsanLiberaVenditaRowData> lookUpElencoMinsanfiltroMargineMedio(Date dateFrom, Date dateTo,BigDecimal margine, String segno){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ElencoMinsanLiberaVenditaRowDataDAO elencoMinsanDAO = daoFactory.getElencoMinsanLiberaVenditaDAO();
		return elencoMinsanDAO.elencoFiltroMargineMedio(dateFrom, dateTo, margine, segno);
	}
	
	public static ArrayList<ElencoMinsanLiberaVenditaRowData> lookUpElencoMinsanfiltroRicaricoMedio(Date dateFrom, Date dateTo,BigDecimal ricarico, String segno){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ElencoMinsanLiberaVenditaRowDataDAO elencoMinsanDAO = daoFactory.getElencoMinsanLiberaVenditaDAO();
		return elencoMinsanDAO.elencoFiltroRicaricoMedio(dateFrom, dateTo, ricarico, segno);
	}

	public static ArrayList<ElencoMinsanLiberaVenditaRowData> lookUpElencoMinsanBetweenDateOrderByQuantDescLimit10(Date dateFrom, Date dateTo){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ElencoMinsanLiberaVenditaRowDataDAO elencoMinsanDAO = daoFactory.getElencoMinsanLiberaVenditaDAO();
		return elencoMinsanDAO.elencoBetweenDateOrderByQuantLimit10(dateFrom,dateTo);
	}

	public static ArrayList<ElencoMinsanLiberaVenditaRowData> lookUpElencoMinsanBetweenDateOrderByProfittoDescLimit5(Date dateFrom, Date dateTo){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ElencoMinsanLiberaVenditaRowDataDAO elencoMinsanDAO = daoFactory.getElencoMinsanLiberaVenditaDAO();
		return elencoMinsanDAO.elencoBetweenDateOrderByProdttoDescLimit5(dateFrom,dateTo);
	}

	public static ArrayList<ElencoMinsanLiberaVenditaRowData> lookUpElencoMinsanBetweenDateOrderByProfittoAscLimit5(Date dateFrom, Date dateTo){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ElencoMinsanLiberaVenditaRowDataDAO elencoMinsanDAO = daoFactory.getElencoMinsanLiberaVenditaDAO();
		return elencoMinsanDAO.elencoBetweenDateOrderByProdttoAscLimit5(dateFrom,dateTo);
	}

}

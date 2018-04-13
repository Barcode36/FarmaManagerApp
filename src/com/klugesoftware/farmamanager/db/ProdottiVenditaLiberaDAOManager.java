package com.klugesoftware.farmamanager.db;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import com.klugesoftware.farmamanager.model.ProdottiVenditaLibera;
import com.klugesoftware.farmamanager.DTO.ElencoProdottiLiberaVenditaRowData;

public class ProdottiVenditaLiberaDAOManager {

	public static ArrayList<ElencoProdottiLiberaVenditaRowData> elencoBetweenDate(Date dataFrom,Date dataTo){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ElencoProdottiLiberaVenditaRowDataDAO elencoDAO = daoFactory.getElencoProdottiLiberaVenditaDAO();
		return elencoDAO.elencoBetweenDate(dataFrom, dataTo);
	}
	
	public static ArrayList<ElencoProdottiLiberaVenditaRowData> elencoLikeBetweenDate(Date dataFrom,Date dataTo,String search){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ElencoProdottiLiberaVenditaRowDataDAO elencoDAO = daoFactory.getElencoProdottiLiberaVenditaDAO();
		return elencoDAO.elencoLikeBetweenDate(dataFrom, dataTo, search);
	}
	
	public static ArrayList<ElencoProdottiLiberaVenditaRowData> elencoFiltroPrezzoVendita(Date dataFrom,Date dataTo,BigDecimal prezzoVendita, String segno){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ElencoProdottiLiberaVenditaRowDataDAO elencoDAO = daoFactory.getElencoProdottiLiberaVenditaDAO();
		return elencoDAO.elencoFiltroPrezzoVendita(dataFrom, dataTo, prezzoVendita, segno);		
	}
	
	public static ArrayList<ElencoProdottiLiberaVenditaRowData> elencoFiltroPrezzoVenditaNetto(Date dataFrom,Date dataTo,BigDecimal prezzoVenditaNetto, String segno){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ElencoProdottiLiberaVenditaRowDataDAO elencoDAO = daoFactory.getElencoProdottiLiberaVenditaDAO();
		return elencoDAO.elencoFiltroPrezzoVenditaNetto(dataFrom, dataTo, prezzoVenditaNetto, segno);		
	}

	public static ArrayList<ElencoProdottiLiberaVenditaRowData> elencoFiltroCostoNetto(Date dataFrom,Date dataTo,BigDecimal costoNetto, String segno){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ElencoProdottiLiberaVenditaRowDataDAO elencoDAO = daoFactory.getElencoProdottiLiberaVenditaDAO();
		return elencoDAO.elencoFiltroCostoNetto(dataFrom, dataTo, costoNetto, segno);		
	}

	public static ArrayList<ElencoProdottiLiberaVenditaRowData> elencoFiltroQuantita(Date dataFrom,Date dataTo,int quantita, String segno){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ElencoProdottiLiberaVenditaRowDataDAO elencoDAO = daoFactory.getElencoProdottiLiberaVenditaDAO();
		return elencoDAO.elencoFiltroQuantita(dataFrom, dataTo, quantita, segno);		
	}

	public static ArrayList<ElencoProdottiLiberaVenditaRowData> elencoFiltroScontoUnitario(Date dataFrom,Date dataTo,BigDecimal scontoUnitario, String segno){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ElencoProdottiLiberaVenditaRowDataDAO elencoDAO = daoFactory.getElencoProdottiLiberaVenditaDAO();
		return elencoDAO.elencoFiltroScontoUnitario(dataFrom, dataTo, scontoUnitario, segno);		
	}

	public static ArrayList<ElencoProdottiLiberaVenditaRowData> elencoFiltroMargineUnitario(Date dataFrom,Date dataTo,BigDecimal margineUnitario, String segno){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ElencoProdottiLiberaVenditaRowDataDAO elencoDAO = daoFactory.getElencoProdottiLiberaVenditaDAO();
		return elencoDAO.elencoFiltroMargineUnitario(dataFrom, dataTo, margineUnitario, segno);		
	}

	public static ArrayList<ElencoProdottiLiberaVenditaRowData> elencoFiltroRicaricoUnitario(Date dataFrom,Date dataTo,BigDecimal ricaricoUnitario, String segno){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ElencoProdottiLiberaVenditaRowDataDAO elencoDAO = daoFactory.getElencoProdottiLiberaVenditaDAO();
		return elencoDAO.elencoFiltroRicaricoUnitario(dataFrom, dataTo, ricaricoUnitario, segno);		
	}
	
	public static ArrayList<ElencoProdottiLiberaVenditaRowData> elencoFiltroProfittoUnitario(Date dataFrom,Date dataTo,BigDecimal profittoUnitario, String segno){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ElencoProdottiLiberaVenditaRowDataDAO elencoDAO = daoFactory.getElencoProdottiLiberaVenditaDAO();
		return elencoDAO.elencoFiltroProfittoUnitario(dataFrom, dataTo, profittoUnitario, segno);		
	}

	public static ProdottiVenditaLibera lookupProdottoById(int idProdottoVenditaLibera){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ProdottiVenditaLiberaDAO prodottoDao = daoFactory.getProdottoVenditaLiberaDAO();
		return prodottoDao.lookupByIdProdotto(idProdottoVenditaLibera);	
	}
	
	public static ArrayList<ProdottiVenditaLibera> elencoByidVenditaLibera(int idVenditaLibera){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ProdottiVenditaLiberaDAO prodottoDao = daoFactory.getProdottoVenditaLiberaDAO();
		return prodottoDao.lookupElencoProdottiByIdVenditaLibera(idVenditaLibera);
	}
	
	public static ProdottiVenditaLibera modifica(ProdottiVenditaLibera prodotto){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ProdottiVenditaLiberaDAO prodottoDao = daoFactory.getProdottoVenditaLiberaDAO();
		return prodottoDao.update(prodotto);
	}
	
	public static ProdottiVenditaLibera insert(ProdottiVenditaLibera prodotto){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ProdottiVenditaLiberaDAO prodottoDao = daoFactory.getProdottoVenditaLiberaDAO();
		return prodottoDao.create(prodotto);
	}

	public static  boolean deleteByIdProdottoVenditaLibera(int idProdotto) {
		DAOFactory daoFactory = DAOFactory.getInstance();
		ProdottiVenditaLiberaDAO prodottoDao = daoFactory.getProdottoVenditaLiberaDAO();
		return prodottoDao.deleteByIdProdottoLibera(idProdotto);		
	}
	
	public static  boolean deleteByIdVenditaLibera(int idVenditaLlibera) {
		DAOFactory daoFactory = DAOFactory.getInstance();
		ProdottiVenditaLiberaDAO prodottoDao = daoFactory.getProdottoVenditaLiberaDAO();
		return prodottoDao.deleteByIdvenditaLiberaLibera(idVenditaLlibera);		
	}
	
	public static boolean svuotaTabella() {
		DAOFactory daoFactory = DAOFactory.getInstance();
		ProdottiVenditaLiberaDAO prodottoDao = daoFactory.getProdottoVenditaLiberaDAO();
		return prodottoDao.svuotaTable();				
	}
	
}

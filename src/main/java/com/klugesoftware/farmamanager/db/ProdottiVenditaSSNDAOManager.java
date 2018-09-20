package com.klugesoftware.farmamanager.db;

import java.util.ArrayList;
import java.util.Date;

import com.klugesoftware.farmamanager.model.ProdottiVenditaSSN;

public class ProdottiVenditaSSNDAOManager {
	
	public static ProdottiVenditaSSN lookupProdottoById(int idProdotto){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ProdottiVenditaSSNDAO prodottoDao = daoFactory.getProdottoVenditaSSNDAO();
		return prodottoDao.lookupProdottoById(idProdotto);
	}
	
	public static ArrayList<ProdottiVenditaSSN> lookupProdottiByIdVenditaSSN(int idVenditaSSN){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ProdottiVenditaSSNDAO prodottoDao = daoFactory.getProdottoVenditaSSNDAO();
		return prodottoDao.lookupElencoByIdVenditaSSN(idVenditaSSN);
	}
	
	public static ProdottiVenditaSSN modifica(ProdottiVenditaSSN prodotto){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ProdottiVenditaSSNDAO prodottoDao = daoFactory.getProdottoVenditaSSNDAO();
		return prodottoDao.update(prodotto);
	}
	
	public static ProdottiVenditaSSN lookupProdottoPerReso(String minsan, Date dataReso){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ProdottiVenditaSSNDAO prodottoDao = daoFactory.getProdottoVenditaSSNDAO();
		return prodottoDao.lookupProdottoPerReso(minsan, dataReso);
	}	

}

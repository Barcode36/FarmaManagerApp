package com.klugesoftware.farmamanager.db;

import java.util.ArrayList;

import com.klugesoftware.farmamanager.model.ResiProdottiVenditaLibera;

public class ResiProdottiVenditaLiberaDAOManager {
	
	public static ResiProdottiVenditaLibera lookUpByIdResoProdottoVenditaLibera(int idResoProdottoVenditaLibera){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiProdottiVenditaLiberaDAO resoProdottoVenditaLiberaDAO = daoFactory.getResoProdottoVenditaLiberaDAO();
		return resoProdottoVenditaLiberaDAO.lookUpById(idResoProdottoVenditaLibera);
	}
	
	public static ArrayList<ResiProdottiVenditaLibera> lookUpElencoResiProdottiByIdResoVenditaLibera(int idResoVenditaLibera){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiProdottiVenditaLiberaDAO resoProdottoVenditaLiberaDAO = daoFactory.getResoProdottoVenditaLiberaDAO();
		return resoProdottoVenditaLiberaDAO.lookUpElencoResiProdottiVenditaLibera(idResoVenditaLibera);		
	}
	
	public static ResiProdottiVenditaLibera create(ResiProdottiVenditaLibera reso){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiProdottiVenditaLiberaDAO resoProdottoVenditaLiberaDAO = daoFactory.getResoProdottoVenditaLiberaDAO();
		return resoProdottoVenditaLiberaDAO.create(reso);
	}

	public static ResiProdottiVenditaLibera update(ResiProdottiVenditaLibera reso){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiProdottiVenditaLiberaDAO resoProdottoVenditaLiberaDAO = daoFactory.getResoProdottoVenditaLiberaDAO();
		return resoProdottoVenditaLiberaDAO.update(reso);
	}

	public static boolean deleteByIdResoProdottiVenditaLibera(int idResoProdottoVenditaLibera){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiProdottiVenditaLiberaDAO resoProdottoVenditaLiberaDAO = daoFactory.getResoProdottoVenditaLiberaDAO();
		return resoProdottoVenditaLiberaDAO.deleteByIdResoProdottoVenditaLibera(idResoProdottoVenditaLibera);
	}

	public static boolean deleteByIdResoVenditaLibera(int idResoVenditaLibera){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiProdottiVenditaLiberaDAO resoProdottoVenditaLiberaDAO = daoFactory.getResoProdottoVenditaLiberaDAO();
		return resoProdottoVenditaLiberaDAO.deleteByIdResoLibera(idResoVenditaLibera);
	}
	
	public static boolean svuotaTabella(){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiProdottiVenditaLiberaDAO resoProdottoVenditaLiberaDAO = daoFactory.getResoProdottoVenditaLiberaDAO();
		return resoProdottoVenditaLiberaDAO.svuotaTable();
	}

}

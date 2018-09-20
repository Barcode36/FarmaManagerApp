package com.klugesoftware.farmamanager.db;

import com.klugesoftware.farmamanager.model.ResiVendite;

/**
 * Classe usata per i test unitari
 */
public class ResiVenditeDAOManager {
	
	public static ResiVendite inserimentoResoVendita(ResiVendite resoVendita){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiVenditeDAO resoVenditaDAO = daoFactory.getResiVenditeDAO();
		return resoVenditaDAO.createResoVendita(resoVendita);
	}

	public static ResiVendite updateResoVendita(ResiVendite resoVendita){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiVenditeDAO resoVenditaDAO = daoFactory.getResiVenditeDAO();
		return resoVenditaDAO.update(resoVendita);
	}
	
	public static ResiVendite findByIdResoVendita(int idResoVendita){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiVenditeDAO resoVenditaDAO = daoFactory.getResiVenditeDAO();
		return resoVenditaDAO.lookUpById(idResoVendita);
	}
	
	public static boolean deleteByIdResoVendita(int idResoVendita){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiVenditeDAO resoVenditaDAO = daoFactory.getResiVenditeDAO();
		return resoVenditaDAO.deleteByIdResoVendita(idResoVendita);
	}
	
	public static boolean svuotaTabella(){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiVenditeDAO resoVenditaDAO = daoFactory.getResiVenditeDAO();
		return resoVenditaDAO.svuotaTable();
	}

}

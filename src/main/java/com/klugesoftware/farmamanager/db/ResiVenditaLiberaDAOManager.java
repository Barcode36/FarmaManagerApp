package com.klugesoftware.farmamanager.db;

import java.util.ArrayList;

import com.klugesoftware.farmamanager.model.ResiVenditeLibere;

public class ResiVenditaLiberaDAOManager {

	public static ResiVenditeLibere create(ResiVenditeLibere reso){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiVenditeLibereDAO resoVenditaLiberaDAO = daoFactory.getResoVenditaLiberaDAO();
		return resoVenditaLiberaDAO.createResoVenditaLibera(reso);
	}
	
	public static ResiVenditeLibere update(ResiVenditeLibere reso){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiVenditeLibereDAO resoVenditaLiberaDAO = daoFactory.getResoVenditaLiberaDAO();
		return resoVenditaLiberaDAO.update(reso);
	}

	public static ResiVenditeLibere findByIdResoVenditaLibera(int idResVenditaLibera){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiVenditeLibereDAO resoVenditaLiberaDAO = daoFactory.getResoVenditaLiberaDAO();
		return resoVenditaLiberaDAO.lookUpById(idResVenditaLibera);
	}
	
	public static ArrayList<ResiVenditeLibere> findByIdResoVendita(int idResoVendita){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiVenditeLibereDAO resoVenditaLiberaDAO = daoFactory.getResoVenditaLiberaDAO();
		return resoVenditaLiberaDAO.lookUpByIdResoVendita(idResoVendita);
	}
	
	public static boolean deleteByIdResoVenditaLibera(int idResoVenditaLibera){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiVenditeLibereDAO resoVenditaLiberaDAO = daoFactory.getResoVenditaLiberaDAO();
		return resoVenditaLiberaDAO.deleteByIdResoVenditaLibera(idResoVenditaLibera);
	}

	public static boolean deleteByIdResoVendita(int idResoVendita){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiVenditeLibereDAO resoVenditaLiberaDAO = daoFactory.getResoVenditaLiberaDAO();
		return resoVenditaLiberaDAO.deleteByIdResoVendita(idResoVendita);
	}
	
	public static boolean svuotaTabella(){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiVenditeLibereDAO resoVenditaLiberaDAO = daoFactory.getResoVenditaLiberaDAO();
		return resoVenditaLiberaDAO.svuotaTable();
	}


}

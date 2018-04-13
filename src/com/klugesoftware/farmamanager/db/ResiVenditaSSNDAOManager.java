package com.klugesoftware.farmamanager.db;

import java.util.ArrayList;

import com.klugesoftware.farmamanager.model.ResiVendite;
import com.klugesoftware.farmamanager.model.ResiVenditeLibere;
import com.klugesoftware.farmamanager.model.ResiVenditeSSN;

public class ResiVenditaSSNDAOManager {

	public static ResiVenditeSSN create(ResiVenditeSSN resoVenditaSSN){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiVenditeSSNDAO resoVenditeSSNDAO = daoFactory.getResoVenditaSSNDAO();
		return resoVenditeSSNDAO.create(resoVenditaSSN);
	}

	public static ResiVenditeSSN update(ResiVenditeSSN resoVenditaSSN){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiVenditeSSNDAO resoVenditeSSNDAO = daoFactory.getResoVenditaSSNDAO();
		return resoVenditeSSNDAO.update(resoVenditaSSN);
	}
	
	public static ResiVenditeSSN findByIdResoVenditaSSN(int idRdesoVenditaSSN){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiVenditeSSNDAO resoVenditeSSNDAO = daoFactory.getResoVenditaSSNDAO();
		return resoVenditeSSNDAO.lookUpById(idRdesoVenditaSSN);
	}

	public static ArrayList<ResiVenditeSSN> findByIdResoVendita(int idResoVendita){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiVenditeSSNDAO resoVenditeSSNDAO = daoFactory.getResoVenditaSSNDAO();
		return resoVenditeSSNDAO.lookUpByIdResoVendita(idResoVendita);
	}
	
	public static boolean deleteByIdResoVenditaSSN(int idResoVenditaSSN){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiVenditeSSNDAO resoVenditeSSNDAO = daoFactory.getResoVenditaSSNDAO();
		return resoVenditeSSNDAO.deleteByIdResoVenditaSSN(idResoVenditaSSN);
	}
	
	public static boolean deleteByIdResoVendita(int idResoVendita){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiVenditeSSNDAO resoVenditeSSNDAO = daoFactory.getResoVenditaSSNDAO();
		return resoVenditeSSNDAO.deleteByIdResoVendita(idResoVendita);
	}
	
	public static boolean svuotaTabella() {
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiVenditeSSNDAO resoVenditeSSNDAO = daoFactory.getResoVenditaSSNDAO();
		return resoVenditeSSNDAO.svuotaTable();		
	}

}

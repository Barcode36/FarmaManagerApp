package com.klugesoftware.farmamanager.db;

import java.util.ArrayList;
import com.klugesoftware.farmamanager.model.VenditeSSN;

public class VenditeSSNDAOManager {

	public static VenditeSSN lookupByIdVenditaSSN(int idVenditaSSN){
		DAOFactory daoFactory = DAOFactory.getInstance();
		VenditeSSNDAO venditaDAO = daoFactory.getVenditASSNDAO();
		return venditaDAO.lookupByIdVenditaSSN(idVenditaSSN);
	}

	public static ArrayList<VenditeSSN> lookupElencoVenditeSSNByIdVenditaGenerale(int idVenditaGenerale){
		DAOFactory daoFactory = DAOFactory.getInstance();
		VenditeSSNDAO venditaDAO = daoFactory.getVenditASSNDAO();
		return venditaDAO.lookupelencoVenditeSSNByIdVendita(idVenditaGenerale);
	}

}

package com.klugesoftware.farmamanager.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.klugesoftware.farmamanager.model.ProdottiVenditaLibera;
import com.klugesoftware.farmamanager.model.Vendite;
import com.klugesoftware.farmamanager.model.VenditeLibere;

public class VenditeLibereDAOMAnager {

	public static VenditeLibere lookupByIdVenditaLibera(int idVenditaLibera){
		DAOFactory daoFactory = DAOFactory.getInstance();
		VenditeLibereDAO venditaLiberaDao = daoFactory.getVenditaLiberaDAO();
		return venditaLiberaDao.lookupById(idVenditaLibera);
	}
	
	public static ArrayList<VenditeLibere> lookupElencoVenditeLibereByIdVenditaGenerale(int idVenditaGenerale){
		DAOFactory daoFactory = DAOFactory.getInstance();
		VenditeLibereDAO venditaLiberaDao = daoFactory.getVenditaLiberaDAO();
		return venditaLiberaDao.lookupElencoVenditeLibereByIdVenditaGenerale(idVenditaGenerale);		
	}
	
	public static ArrayList<VenditeLibere> lookupElencoVenditeLibereBetweenDate(Date from, Date to){
		DAOFactory daoFactory = DAOFactory.getInstance();
		VenditeLibereDAO venditaLiberaDao = daoFactory.getVenditaLiberaDAO();
		ArrayList<VenditeLibere> elencoVenditeLibere = venditaLiberaDao.elencoByIntervalloDate(from, to);
		if (elencoVenditeLibere != null) {
			Iterator<VenditeLibere> iterVenditeLibere = elencoVenditeLibere.iterator();
			VenditeLibere venditaLibera;
			ArrayList<ProdottiVenditaLibera> elencoProdottiVenditaLibera;
			while (iterVenditeLibere.hasNext()) {
				venditaLibera = iterVenditeLibere.next();
				elencoProdottiVenditaLibera = ProdottiVenditaLiberaDAOManager.elencoByidVenditaLibera(venditaLibera.getIdVenditaLibera());
				venditaLibera.setProdottiVenduti(elencoProdottiVenditaLibera);
			}
		}
		
		return elencoVenditeLibere;
	}

}

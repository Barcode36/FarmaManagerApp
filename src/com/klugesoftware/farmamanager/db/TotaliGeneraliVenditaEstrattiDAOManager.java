package com.klugesoftware.farmamanager.db;

import java.util.ArrayList;
import java.util.Date;

import com.klugesoftware.farmamanager.model.ResiVendite;
import com.klugesoftware.farmamanager.model.Vendite;
import com.klugesoftware.farmamanager.IOFunctions.TotaliGeneraliResiVenditaEstratti;
import com.klugesoftware.farmamanager.IOFunctions.TotaliGeneraliVenditaEstratti;
import com.klugesoftware.farmamanager.DTO.ElencoProdottiLiberaVenditaRowData;
import com.klugesoftware.farmamanager.utility.DateUtility;

public class TotaliGeneraliVenditaEstrattiDAOManager {

	public static TotaliGeneraliVenditaEstratti insert(TotaliGeneraliVenditaEstratti totaleGenerale){
		DAOFactory daoFactory = DAOFactory.getInstance();
		TotaliGeneraliVenditaEstrattiDAO totaliGeneraliDAO = daoFactory.getTotaliGeneraliVenditaEstrattiDAO();
		return totaliGeneraliDAO.create(totaleGenerale);
	}
	
	public static TotaliGeneraliVenditaEstratti findById(int idTotale){
		DAOFactory daoFactory = DAOFactory.getInstance();
		TotaliGeneraliVenditaEstrattiDAO totaliGeneraliDAO = daoFactory.getTotaliGeneraliVenditaEstrattiDAO();
		return totaliGeneraliDAO.findById(idTotale);		
	}
	
	public static TotaliGeneraliVenditaEstratti findByDate(int mese,int anno){
		DAOFactory daoFactory = DAOFactory.getInstance();
		TotaliGeneraliVenditaEstrattiDAO totaliGeneraliDAO = daoFactory.getTotaliGeneraliVenditaEstrattiDAO();
		return totaliGeneraliDAO.findByDate(mese, anno);		
	}
 
	public static TotaliGeneraliVenditaEstratti update(TotaliGeneraliVenditaEstratti totaleGenerale){
		DAOFactory daoFactory = DAOFactory.getInstance();
		TotaliGeneraliVenditaEstrattiDAO totaliGeneraliDAO = daoFactory.getTotaliGeneraliVenditaEstrattiDAO();
		return totaliGeneraliDAO.update(totaleGenerale);
	}
	
	public static TotaliGeneraliVenditaEstratti aggiornaTotaliGenerali(Vendite vendita){
		DAOFactory daoFactory = DAOFactory.getInstance();
		TotaliGeneraliVenditaEstrattiDAO totaliGeneraliDAO = daoFactory.getTotaliGeneraliVenditaEstrattiDAO();
		TotaliGeneraliVenditaEstratti totaleGenerale = findByDate(DateUtility.getMese(vendita.getDataVendita()), DateUtility.getAnno(vendita.getDataVendita())); 
		if (totaleGenerale.getIdTotale() != null){
			totaleGenerale.aggiornaTotaliGenerali(vendita);
			update(totaleGenerale);
		}else{
			totaleGenerale = new TotaliGeneraliVenditaEstratti();
			totaleGenerale.aggiornaTotaliGenerali(vendita);
			insert(totaleGenerale);
		}
		return totaleGenerale;
	}
	
	public static TotaliGeneraliVenditaEstratti aggiornaTotaliGenerali(ResiVendite resoVendita){
		TotaliGeneraliResiVenditaEstratti totaleGeneraleReso = new TotaliGeneraliResiVenditaEstratti();
		totaleGeneraleReso.aggiornaTotaliGeneraliResiEstratti(resoVendita);
		TotaliGeneraliVenditaEstratti totaleGenerale = findByDate(DateUtility.getMese(resoVendita.getDataReso()), DateUtility.getAnno(resoVendita.getDataReso())); 
		if (totaleGenerale.getIdTotale() != null){
			totaleGenerale.sottraiResi(totaleGeneraleReso);
			update(totaleGenerale);
		}else{
			totaleGenerale = new TotaliGeneraliVenditaEstratti();
			totaleGenerale.sottraiResi(totaleGeneraleReso);
			// inserimento Date
			totaleGenerale.setMese(DateUtility.getMese(resoVendita.getDataReso()));
			totaleGenerale.setAnno(DateUtility.getAnno(resoVendita.getDataReso()));
			totaleGenerale.setDataUltimoAggiornamento(DateUtility.getDataOdierna());
			insert(totaleGenerale);
		}
		return totaleGenerale;
	}
}

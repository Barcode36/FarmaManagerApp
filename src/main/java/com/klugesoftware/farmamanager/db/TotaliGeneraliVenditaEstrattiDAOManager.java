package com.klugesoftware.farmamanager.db;

import com.klugesoftware.farmamanager.IOFunctions.ImportazioneVenditeFromDBF;
import com.klugesoftware.farmamanager.model.ResiVendite;
import com.klugesoftware.farmamanager.model.Vendite;
import com.klugesoftware.farmamanager.IOFunctions.TotaliGeneraliResiVenditaEstratti;
import com.klugesoftware.farmamanager.IOFunctions.TotaliGeneraliVenditaEstratti;
import com.klugesoftware.farmamanager.utility.DateUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;
import java.util.Locale;

public class TotaliGeneraliVenditaEstrattiDAOManager {

	private static final Logger logger = LogManager.getLogger(TotaliGeneraliVenditaEstrattiDAOManager.class.getName());

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
			Calendar tempCal = Calendar.getInstance(Locale.ITALY);
			tempCal.setTime(vendita.getDataVendita());
			logger.info("Importazione movimenti del mese di: "+tempCal.getDisplayName(Calendar.MONTH,Calendar.LONG,Locale.ITALY)+" "+tempCal.get(Calendar.YEAR));
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

package com.klugesoftware.farmamanager.db;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.klugesoftware.farmamanager.model.ResiVendite;
import com.klugesoftware.farmamanager.model.Vendite;
import com.klugesoftware.farmamanager.IOFunctions.TotaliGeneraliResiVenditaEstratti;
import com.klugesoftware.farmamanager.IOFunctions.TotaliGeneraliVenditaEstrattiGiornalieri;
import com.klugesoftware.farmamanager.utility.DateUtility;

public class TotaliGeneraliVenditaEstrattiGiornalieriDAOManager {
	public static TotaliGeneraliVenditaEstrattiGiornalieri insert(TotaliGeneraliVenditaEstrattiGiornalieri totaleGenerale){
		DAOFactory daoFactory = DAOFactory.getInstance();
		TotaliGeneraliVenditaEstrattiGiornalieriDAO totaliGeneraliDAO = daoFactory.getTotaliGeneraliVenditaEstrattiGiornalieriDAO();
		return totaliGeneraliDAO.create(totaleGenerale);
	}
	
	public static TotaliGeneraliVenditaEstrattiGiornalieri findById(int idTotale){
		DAOFactory daoFactory = DAOFactory.getInstance();
		TotaliGeneraliVenditaEstrattiGiornalieriDAO totaliGeneraliDAO = daoFactory.getTotaliGeneraliVenditaEstrattiGiornalieriDAO();
		return totaliGeneraliDAO.findById(idTotale);		
	}
	
	public static TotaliGeneraliVenditaEstrattiGiornalieri findByDate(Date data){
		DAOFactory daoFactory = DAOFactory.getInstance();
		TotaliGeneraliVenditaEstrattiGiornalieriDAO totaliGeneraliDAO = daoFactory.getTotaliGeneraliVenditaEstrattiGiornalieriDAO();
		return totaliGeneraliDAO.findByDate(data);		
	}

	public static ArrayList<TotaliGeneraliVenditaEstrattiGiornalieri> findbetweenDate(Date dataFrom, Date dateTo){
		DAOFactory daoFactory = DAOFactory.getInstance();
		TotaliGeneraliVenditaEstrattiGiornalieriDAO totaliGeneraliDAO = daoFactory.getTotaliGeneraliVenditaEstrattiGiornalieriDAO();
		return totaliGeneraliDAO.findBetweenDate(dataFrom,dateTo);
	}
 
	public static TotaliGeneraliVenditaEstrattiGiornalieri update(TotaliGeneraliVenditaEstrattiGiornalieri totaleGenerale){
		DAOFactory daoFactory = DAOFactory.getInstance();
		TotaliGeneraliVenditaEstrattiGiornalieriDAO totaliGeneraliDAO = daoFactory.getTotaliGeneraliVenditaEstrattiGiornalieriDAO();
		return totaliGeneraliDAO.update(totaleGenerale);
	}
	
	public static TotaliGeneraliVenditaEstrattiGiornalieri aggiornaTotaliGenerali(Vendite vendita){
		DAOFactory daoFactory = DAOFactory.getInstance();
		TotaliGeneraliVenditaEstrattiGiornalieriDAO totaliGeneraliDAO = daoFactory.getTotaliGeneraliVenditaEstrattiGiornalieriDAO();
		TotaliGeneraliVenditaEstrattiGiornalieri totaleGenerale = findByDate(vendita.getDataVendita()); 
		if (totaleGenerale.getIdTotale() != null){
			totaleGenerale.aggiornaTotaliGenerali(vendita);
			update(totaleGenerale);
		}else{
			aggiungeGiorniNonLavorativi(vendita.getDataVendita());
			totaleGenerale = new TotaliGeneraliVenditaEstrattiGiornalieri();
			totaleGenerale.aggiornaTotaliGenerali(vendita);
			insert(totaleGenerale);
		}
		return totaleGenerale;
	}
	
	public static TotaliGeneraliVenditaEstrattiGiornalieri aggiornaTotaliGenerali(ResiVendite resoVendita){
		TotaliGeneraliResiVenditaEstratti totaleGeneraleReso = new TotaliGeneraliResiVenditaEstratti();
		totaleGeneraleReso.aggiornaTotaliGeneraliResiEstratti(resoVendita);
		TotaliGeneraliVenditaEstrattiGiornalieri totaleGenerale = findByDate(resoVendita.getDataReso()); 
		if (totaleGenerale.getIdTotale() != null){
			totaleGenerale.sottraiResi(totaleGeneraleReso);
			update(totaleGenerale);
		}else{
			aggiungeGiorniNonLavorativi(resoVendita.getDataReso());
			totaleGenerale = new TotaliGeneraliVenditaEstrattiGiornalieri();
			totaleGenerale.sottraiResi(totaleGeneraleReso);
			// inserimento Date
			totaleGenerale.setData(resoVendita.getDataReso());
			totaleGenerale.setDataUltimoAggiornamento(DateUtility.getDataOdierna());
			insert(totaleGenerale);
		}
		return totaleGenerale;
	}

	private static void aggiungeGiorniNonLavorativi(Date dataOperazione){
		//lookup ultimo record in TotaliGeneraliGirnalieri
		TotaliGeneraliVenditaEstrattiGiornalieri lastTotale = lastRecord();
		if (lastTotale.getIdTotale() != null){
			Calendar dataOp = Calendar.getInstance(Locale.ITALY);
			Calendar dataUltTot = Calendar.getInstance(Locale.ITALY);
			dataOp.setTime(dataOperazione);
			dataUltTot.setTime(lastTotale.getData());
			dataUltTot.add(Calendar.DAY_OF_MONTH,1);
			while(dataUltTot.compareTo(dataOp) != 0){
				TotaliGeneraliVenditaEstrattiGiornalieri totaleGiornoNonLavorrativo = new TotaliGeneraliVenditaEstrattiGiornalieri();
				totaleGiornoNonLavorrativo.setData(dataUltTot.getTime());
				insert(totaleGiornoNonLavorrativo);
				dataUltTot.add(Calendar.DAY_OF_MONTH,1);
			}
		}
	}

	public static TotaliGeneraliVenditaEstrattiGiornalieri lastRecord(){
		DAOFactory daoFactory = DAOFactory.getInstance();
		TotaliGeneraliVenditaEstrattiGiornalieriDAO totaliGeneraliDAO = daoFactory.getTotaliGeneraliVenditaEstrattiGiornalieriDAO();
		return totaliGeneraliDAO.findByMaxId();
	}

}

package com.klugesoftware.farmamanager.db;

import java.util.ArrayList;
import java.util.Date;

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
			totaleGenerale = new TotaliGeneraliVenditaEstrattiGiornalieri();
			totaleGenerale.sottraiResi(totaleGeneraleReso);
			// inserimento Date
			totaleGenerale.setData(resoVendita.getDataReso());
			totaleGenerale.setDataUltimoAggiornamento(DateUtility.getDataOdierna());
			insert(totaleGenerale);
		}
		return totaleGenerale;
	}

}

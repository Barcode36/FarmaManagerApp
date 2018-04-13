package com.klugesoftware.farmamanager.db;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.klugesoftware.farmamanager.model.CustomRoundingAndScaling;
import com.klugesoftware.farmamanager.model.ProdottiVenditaLibera;
import com.klugesoftware.farmamanager.model.ProdottiVenditaSSN;
import com.klugesoftware.farmamanager.model.ResiProdottiVenditaLibera;
import com.klugesoftware.farmamanager.model.ResiProdottiVenditaSSN;
import com.klugesoftware.farmamanager.model.ResiVendite;
import com.klugesoftware.farmamanager.model.ResiVenditeLibere;
import com.klugesoftware.farmamanager.model.ResiVenditeSSN;
import com.klugesoftware.farmamanager.model.Vendite;
import com.klugesoftware.farmamanager.model.VenditeLibere;
import com.klugesoftware.farmamanager.model.VenditeSSN;
import com.klugesoftware.farmamanager.IOFunctions.TotaliGeneraliResiVenditaEstratti;


public class ResiDAOManager {

	public static ResiVendite inserimentoResoVenditaGenerale(ResiVendite resoVendita){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiVenditeDAO resoVenditaDAO = daoFactory.getResiVenditeDAO();
		return resoVenditaDAO.createResoVendita(resoVendita);
	}
	
	public static ResiVenditeLibere inserimentoResoVenditaLibera(ResiVenditeLibere resoVenditaLibera){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiVenditeLibereDAO resoVenditaLiberaDAO = daoFactory.getResoVenditaLiberaDAO();
		return resoVenditaLiberaDAO.createResoVenditaLibera(resoVenditaLibera);
	}	
	
	public static ResiVenditeSSN inserimentoResoVenditaSSN(ResiVenditeSSN resoVenditaSSN){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiVenditeSSNDAO resoVenditaSSNDAO = daoFactory.getResoVenditaSSNDAO();
		return resoVenditaSSNDAO.create(resoVenditaSSN);
	}
	
	public static ResiProdottiVenditaLibera inserimentoResoProdottiVenditaLibera(ResiProdottiVenditaLibera resoProdottoVenditaLibera){
		return ResiProdottiVenditaLiberaDAOManager.create(resoProdottoVenditaLibera);
	}
	
	public static ResiProdottiVenditaSSN inserimentoResoProdottoVenditaSSN(ResiProdottiVenditaSSN resoProdottoVenditaSSN){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiProdottiVendutiSSNDAO resoProdottoVenditaSSNDAO = daoFactory.getResoProdottoVenditaSSNDAO();
		return resoProdottoVenditaSSNDAO.create(resoProdottoVenditaSSN);
	}
	
	public static ResiVendite inserimentoResoVenditaComposto(ResiVendite resoVendite){
		resoVendite = inserimentoResoVenditaGenerale(resoVendite);
		
		Collection<ResiVenditeLibere> elencoResiVenditeLibere = resoVendite.getResiVenditeLiberes();
		Iterator<ResiVenditeLibere> iterResiVenditeLibere;
		if (elencoResiVenditeLibere != null){
			iterResiVenditeLibere = elencoResiVenditeLibere.iterator();
			ResiVenditeLibere resoVenditaLibera;
			while(iterResiVenditeLibere.hasNext()){
				resoVenditaLibera = iterResiVenditeLibere.next();
				resoVenditaLibera = inserimentoResoVenditaLibera(resoVenditaLibera);
				Collection<ResiProdottiVenditaLibera> elencoResiLibera = resoVenditaLibera.getResiProdottiVenditaLiberas();
				Iterator<ResiProdottiVenditaLibera> iterResiProdottiLibera = elencoResiLibera.iterator();
				ResiProdottiVenditaLibera resoProdottoLibera;
				while(iterResiProdottiLibera.hasNext()){
					resoProdottoLibera = iterResiProdottiLibera.next();
					resoProdottoLibera = inserimentoResoProdottiVenditaLibera(resoProdottoLibera);		
				}
			}
		}
		
		Collection<ResiVenditeSSN> elencoResiVenditeSSN = resoVendite.getResiVenditeSsns();
		Iterator<ResiVenditeSSN> iterResiVenditeSSN;
		if(elencoResiVenditeSSN != null){
			iterResiVenditeSSN = elencoResiVenditeSSN.iterator();
			ResiVenditeSSN resoVenditaSSN;
			while(iterResiVenditeSSN.hasNext()){
				resoVenditaSSN = iterResiVenditeSSN.next();
				resoVenditaSSN = inserimentoResoVenditaSSN(resoVenditaSSN);
				Collection<ResiProdottiVenditaSSN> elencoResiProdottiSSN = resoVenditaSSN.getResiProdottiVenditaSsns();
				Iterator<ResiProdottiVenditaSSN> iterResiProdottiSSN = elencoResiProdottiSSN.iterator();
				ResiProdottiVenditaSSN resoProdottoSSN;
				while(iterResiProdottiSSN.hasNext()){
					resoProdottoSSN = iterResiProdottiSSN.next();
					resoProdottoSSN = inserimentoResoProdottoVenditaSSN(resoProdottoSSN);
				}
			}
		}
		
		return resoVendite;
	}
	
	static public ResiVendite lookupByMaxId(){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiVenditeDAO resoVenditaDAO = daoFactory.getResiVenditeDAO();
		return resoVenditaDAO.findByMaxId();
	}
	
	static public ResiVendite updateResoVendita(ResiVendite reso) {
		
		//Reso Vendita Generale
		//controllo l'IdResoVendita; se è NULL allora faccio una INSERT altrimenti un'UPDATE
		if(reso.getIdResoVendita() != null)
			reso = ResiVenditeDAOManager.updateResoVendita(reso);
		else 
			reso = ResiVenditeDAOManager.inserimentoResoVendita(reso);
		
		//Resi Vendite SSN
		Collection<ResiVenditeSSN> elencoResiVenditeSSN = reso.getResiVenditeSsns();
		Iterator<ResiVenditeSSN> iterResiVenditeSSN; 
		if (elencoResiVenditeSSN != null) {
			iterResiVenditeSSN = elencoResiVenditeSSN.iterator();
			ResiVenditeSSN resoVenditaSSN;
			while(iterResiVenditeSSN.hasNext()) {
				resoVenditaSSN = iterResiVenditeSSN.next();
				//controllo l'IdResoVenditaSSn; se è NULL allora faccio una INSERT altrimenti un'UPDATE
				if(resoVenditaSSN.getIdResoVenditaSSN() != null)
					resoVenditaSSN = ResiVenditaSSNDAOManager.update(resoVenditaSSN);
				else
					resoVenditaSSN = ResiVenditaSSNDAOManager.create(resoVenditaSSN);
				Collection<ResiProdottiVenditaSSN> elencoResiProdottiSSN = resoVenditaSSN.getResiProdottiVenditaSsns();
				Iterator<ResiProdottiVenditaSSN> iterResiProdottiSSN;
				if(elencoResiProdottiSSN != null) {
					iterResiProdottiSSN = elencoResiProdottiSSN.iterator();
					ResiProdottiVenditaSSN resoProdottoSSN;
					while (iterResiProdottiSSN.hasNext()) {
						resoProdottoSSN = iterResiProdottiSSN.next();
						//controllo l'idResoProdottoSSN; se è NULL allora devo fare una insert altrimenti update
						if(resoProdottoSSN.getIdResoProdottoVenditaSSN() != null)
							resoProdottoSSN = ResiProdottiVendutiSSNDAOManager.update(resoProdottoSSN);
						else
							resoProdottoSSN = ResiProdottiVendutiSSNDAOManager.create(resoProdottoSSN);
						//aggiornaQuantitaResoProdotto(resoProdottoSSN);
					}
				}				
			}
		}
		
		//Resi Vendite Libere
		Collection<ResiVenditeLibere> elencoResiVenditeLibere = reso.getResiVenditeLiberes();
		Iterator<ResiVenditeLibere> iterResiVenditeLibere;
		if (elencoResiVenditeLibere != null) {
			iterResiVenditeLibere = elencoResiVenditeLibere.iterator();
			ResiVenditeLibere resoVenditaLibera;
			while(iterResiVenditeLibere.hasNext()) {
				resoVenditaLibera = iterResiVenditeLibere.next();
				Collection<ResiProdottiVenditaLibera> elencoResiProdottiLibera = resoVenditaLibera.getResiProdottiVenditaLiberas();
				Iterator<ResiProdottiVenditaLibera> iterResiProdottiLibera;
				if(elencoResiProdottiLibera != null) {
					iterResiProdottiLibera = elencoResiProdottiLibera.iterator();
					//controllo l'IdResoVenditaLibera; se è NULL allora faccio una INSERT altrimenti un'UPDATE
					if(resoVenditaLibera.getIdResoVenditaLibera() != null) 
						resoVenditaLibera = ResiVenditaLiberaDAOManager.update(resoVenditaLibera);
					else
						resoVenditaLibera = ResiVenditaLiberaDAOManager.create(resoVenditaLibera);
					ResiProdottiVenditaLibera resoProdottoLibera;
					while(iterResiProdottiLibera.hasNext()) {
						resoProdottoLibera = iterResiProdottiLibera.next();
						//controllo l'idResoProdottoLibera; se è NULL allora devo fare una insert altrimenti update
						if(resoProdottoLibera.getIdResoProdottoVenditaLibera() != null)
							resoProdottoLibera = ResiProdottiVenditaLiberaDAOManager.update(resoProdottoLibera);
						else
							resoProdottoLibera = ResiProdottiVenditaLiberaDAOManager.create(resoProdottoLibera);
						aggiornaQuantitaResoProdotto(resoProdottoLibera);
					}
				}
				
			}
		}
				
		return reso;
	}
	
	public static ArrayList<ResiVendite> lookupByDate(Date dateFrom, Date dateTo){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ResiVenditeDAO resoVenditaDAO = daoFactory.getResiVenditeDAO();
		return resoVenditaDAO.elencoByDate(dateFrom, dateTo);
	}
	
	public static ArrayList<ResiVendite> elencoResiVenditeComposti(Date dateFrom, Date dateTo){
		
		ArrayList<ResiVendite> elencoResiVenditeGenerali = ResiDAOManager.lookupByDate(dateFrom, dateTo);
		Iterator<ResiVendite> iterResiVenditeGenerali = elencoResiVenditeGenerali.iterator();
		ResiVendite resoVendita;
		while(iterResiVenditeGenerali.hasNext()){
			resoVendita = iterResiVenditeGenerali.next();
			ArrayList<ResiVenditeLibere> elencoResiVenditeLibere = ResiVenditaLiberaDAOManager.findByIdResoVendita(resoVendita.getIdResoVendita());
			ArrayList<ResiVenditeSSN> elencoResiVenditeSSN = ResiVenditaSSNDAOManager.findByIdResoVendita(resoVendita.getIdResoVendita());
			resoVendita.setResiVenditeLiberes(elencoResiVenditeLibere);
			resoVendita.setResiVenditeSsns(elencoResiVenditeSSN);
			
			Iterator<ResiVenditeLibere> iterResiVenditeLibere = elencoResiVenditeLibere.iterator();
			ResiVenditeLibere resoVenditaLibera;
			while(iterResiVenditeLibere.hasNext()){
				resoVenditaLibera = iterResiVenditeLibere.next();
				resoVenditaLibera.setResiVendite(resoVendita);
				ArrayList<ResiProdottiVenditaLibera> elencoResiProdottiLibera = ResiProdottiVenditaLiberaDAOManager.lookUpElencoResiProdottiByIdResoVenditaLibera(resoVenditaLibera.getIdResoVenditaLibera());
				resoVenditaLibera.setResiProdottiVenditaLiberas(elencoResiProdottiLibera);
				Iterator<ResiProdottiVenditaLibera> iterResiProdottiVenditaLibera = elencoResiProdottiLibera.iterator();
				ResiProdottiVenditaLibera resoProdottoVenditaLibera;
				while (iterResiProdottiVenditaLibera.hasNext()){
					resoProdottoVenditaLibera = iterResiProdottiVenditaLibera.next();
					resoProdottoVenditaLibera.setResiVenditeLibere(resoVenditaLibera);
				}
			}
			
			Iterator<ResiVenditeSSN> iterResiVenditeSSN = elencoResiVenditeSSN.iterator();
			ResiVenditeSSN resoVenditaSSN;
			while (iterResiVenditeSSN.hasNext()){
				resoVenditaSSN = iterResiVenditeSSN.next();
				resoVenditaSSN.setResiVendite(resoVendita);
				ArrayList<ResiProdottiVenditaSSN> elencoResiProdottiVenditaSSN = ResiProdottiVendutiSSNDAOManager.findByIdResoVenditaSSN(resoVenditaSSN.getIdResoVenditaSSN());
				resoVenditaSSN.setResiProdottiVenditaSsns(elencoResiProdottiVenditaSSN);
				Iterator<ResiProdottiVenditaSSN> iterResiProdottiVenditaSSN = elencoResiProdottiVenditaSSN.iterator();
				ResiProdottiVenditaSSN resoProdottoVenditaSSN;
				while (iterResiProdottiVenditaSSN.hasNext()){
					resoProdottoVenditaSSN = iterResiProdottiVenditaSSN.next();
					resoProdottoVenditaSSN.setResiVenditeSsn(resoVenditaSSN);
				}
			}
		}
		return elencoResiVenditeGenerali;

		
	}
	
	public static TotaliGeneraliResiVenditaEstratti estrazioneTotaliResiVendita(Date from, Date to) {
		TotaliGeneraliResiVenditaEstratti totaliGeneraliResiEstratti = new TotaliGeneraliResiVenditaEstratti();
		ArrayList<ResiVendite> elencoResiVenditeGenerali = elencoResiVenditeComposti(from, to);
		if (elencoResiVenditeGenerali.isEmpty())
			return null;
		
		double totaleResiLordoVendite = 0;
		double totaleResiLordoVenditeSSN = 0;
		double totaleResiLordoVenditeLibere = 0;
		double totaleResiNettoVendite = 0;
		double totaleResiNettoVenditeLibere = 0;
		double totaleResiNettoVenditeSSN = 0;
		double totaleResiScontiVenditaGenerale = 0;
		double totaleResiScontiVenditaLibera = 0;
		double totaleResiScontiSSNVenditeSSN = 0;
		double totaleResiNettoCosti = 0;
		double totaleResiNettoCostiVenditeLibere = 0;
		double totaleResiNettoCostiVenditeSSN = 0;
		double totaleResiProfitti = 0;
		double totaleResiProfittiVenditeLibere = 0;
		double totaleResiProfittiVenditeSSN = 0;
		int totaleResiPezziVenduti = 0;
		int totaleResiPezziVendutiLibere = 0;
		int totaleResiPezziVendutiSSN = 0;
		Iterator<ResiVendite> iter = elencoResiVenditeGenerali.iterator();
		ResiVendite resoVendita;
		while(iter.hasNext()){
			resoVendita = iter.next();
			totaleResiLordoVendite = totaleResiLordoVendite + resoVendita.getTotaleResoVendita().doubleValue();
			totaleResiPezziVenduti = totaleResiPezziVenduti + resoVendita.getTotalePezziResi();
			Collection<ResiVenditeLibere> resiVenditeLibere = resoVendita.getResiVenditeLiberes();
			Iterator<ResiVenditeLibere> iterResiVenditeLibere = resiVenditeLibere.iterator();
			ResiVenditeLibere resoVenditaLibera;
			while(iterResiVenditeLibere.hasNext()){
				resoVenditaLibera = iterResiVenditeLibere.next();
				totaleResiScontiVenditaLibera = totaleResiScontiVenditaLibera + resoVenditaLibera.getTotaleScontoProdotto().doubleValue();
				totaleResiScontiVenditaGenerale = totaleResiScontiVenditaGenerale + resoVenditaLibera.getTotaleScontoProdotto().doubleValue();
				totaleResiLordoVenditeLibere = totaleResiLordoVenditeLibere + resoVenditaLibera.getValoreVenditaLibera().doubleValue();
				totaleResiPezziVendutiLibere = totaleResiPezziVendutiLibere + resoVenditaLibera.getTotalePezziResi();
				Collection<ResiProdottiVenditaLibera> elencoresiProdottiLiberaVendita = resoVenditaLibera.getResiProdottiVenditaLiberas();
				Iterator<ResiProdottiVenditaLibera> iterResiProdottiLiberaVendita = elencoresiProdottiLiberaVendita.iterator();
				ResiProdottiVenditaLibera resoProdottiLiberaVendita;
				while(iterResiProdottiLiberaVendita.hasNext()){
					resoProdottiLiberaVendita = iterResiProdottiLiberaVendita.next();
					totaleResiNettoVenditeLibere = totaleResiNettoVenditeLibere + resoProdottiLiberaVendita.getPrezzoVenditaNetto().doubleValue()*resoProdottiLiberaVendita.getQuantita();
					totaleResiNettoVendite = totaleResiNettoVendite + resoProdottiLiberaVendita.getPrezzoVenditaNetto().doubleValue()*resoProdottiLiberaVendita.getQuantita();
					totaleResiProfittiVenditeLibere = totaleResiProfittiVenditeLibere + resoProdottiLiberaVendita.getProfittoUnitario().doubleValue()*resoProdottiLiberaVendita.getQuantita();
					totaleResiProfitti = totaleResiProfitti + resoProdottiLiberaVendita.getProfittoUnitario().doubleValue()*resoProdottiLiberaVendita.getQuantita();
					totaleResiNettoCostiVenditeLibere = totaleResiNettoCostiVenditeLibere + resoProdottiLiberaVendita.getCostoNettoIva().doubleValue() * resoProdottiLiberaVendita.getQuantita();
					totaleResiNettoCosti = totaleResiNettoCosti + resoProdottiLiberaVendita.getCostoNettoIva().doubleValue() * resoProdottiLiberaVendita.getQuantita();
				}
			}
			
			Collection<ResiVenditeSSN> 	resiVenditeSSN = resoVendita.getResiVenditeSsns();
			Iterator<ResiVenditeSSN> iterResiVenditeSSN = resiVenditeSSN.iterator();
			ResiVenditeSSN resoVenditaSSN;
			while(iterResiVenditeSSN.hasNext()){
				resoVenditaSSN = iterResiVenditeSSN.next();
				totaleResiScontiSSNVenditeSSN = totaleResiScontiSSNVenditeSSN + resoVenditaSSN.getTotaleScontoSSN().doubleValue();
				totaleResiScontiVenditaGenerale = totaleResiScontiVenditaGenerale + resoVenditaSSN.getTotaleScontoSSN().doubleValue();;
				totaleResiPezziVendutiSSN = totaleResiPezziVendutiSSN + resoVenditaSSN.getTotalePezziResi();
				totaleResiLordoVenditeSSN = totaleResiLordoVenditeSSN + resoVenditaSSN.getValoreVenditaSSN().doubleValue();
				Collection<ResiProdottiVenditaSSN> elencoResiProdottiSSN = resoVenditaSSN.getResiProdottiVenditaSsns();
				Iterator<ResiProdottiVenditaSSN> iterResiProdottiSSN = elencoResiProdottiSSN.iterator();
				ResiProdottiVenditaSSN resoProdottoSSN;
				while (iterResiProdottiSSN.hasNext()){
					resoProdottoSSN = iterResiProdottiSSN.next();
					totaleResiNettoVendite = totaleResiNettoVendite + resoProdottoSSN.getPrezzoVenditaNetto().doubleValue() * resoProdottoSSN.getQuantita();
					totaleResiNettoVenditeSSN = totaleResiNettoVenditeSSN + resoProdottoSSN.getPrezzoVenditaNetto().doubleValue() * resoProdottoSSN.getQuantita();
					totaleResiProfitti = totaleResiProfitti + resoProdottoSSN.getProfittoUnitario().doubleValue()*resoProdottoSSN.getQuantita();
					totaleResiProfittiVenditeSSN = totaleResiProfittiVenditeSSN + resoProdottoSSN.getProfittoUnitario().doubleValue()*resoProdottoSSN.getQuantita();
					totaleResiNettoCosti = totaleResiNettoCosti + resoProdottoSSN.getCostoNettoIva().doubleValue() * resoProdottoSSN.getQuantita();
					totaleResiNettoCostiVenditeSSN = totaleResiNettoCostiVenditeSSN + resoProdottoSSN.getCostoNettoIva().doubleValue() * resoProdottoSSN.getQuantita();
				}
			}
		}
		
		totaliGeneraliResiEstratti.setTotaleResiProfitti(new BigDecimal(totaleResiProfitti).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		totaliGeneraliResiEstratti.setTotaleResiProfittiLibere(new BigDecimal(totaleResiProfittiVenditeLibere).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		totaliGeneraliResiEstratti.setTotaleResiProfittiSSN(new BigDecimal(totaleResiProfittiVenditeSSN).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		
		totaliGeneraliResiEstratti.setTotaleResiCostiNetti(new BigDecimal(totaleResiNettoCosti).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		totaliGeneraliResiEstratti.setTotaleResiCostiNettiLibere(new BigDecimal(totaleResiNettoCostiVenditeLibere).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		totaliGeneraliResiEstratti.setTotaleResiCostiNettiSSN(new BigDecimal(totaleResiNettoCostiVenditeSSN).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		
		totaliGeneraliResiEstratti.setTotaleResiSconti(new BigDecimal(totaleResiScontiVenditaGenerale).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		totaliGeneraliResiEstratti.setTotaleResiScontiLibere(new BigDecimal(totaleResiScontiVenditaLibera).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		totaliGeneraliResiEstratti.setTotaleResiScontiSSN(new BigDecimal(totaleResiScontiSSNVenditeSSN).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		
		totaliGeneraliResiEstratti.setTotaleResiVenditeLorde(new BigDecimal(totaleResiLordoVendite).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		totaliGeneraliResiEstratti.setTotaleResiVenditeLordeLibere(new BigDecimal(totaleResiLordoVenditeLibere).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		totaliGeneraliResiEstratti.setTotaleResiVenditeLordeSSN(new BigDecimal(totaleResiLordoVenditeSSN).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		
		totaliGeneraliResiEstratti.setTotaleResiVenditeNette(new BigDecimal(totaleResiNettoVendite).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		totaliGeneraliResiEstratti.setTotaleResiVenditeNetteLibere(new BigDecimal(totaleResiNettoVenditeLibere).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		totaliGeneraliResiEstratti.setTotaleResiVenditeNetteSSN(new BigDecimal(totaleResiNettoVenditeSSN).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));

		return totaliGeneraliResiEstratti;

	}
	
	/**
	 * Riporta le quantità reso nella colonna quantitaReso del record Prodotti* 
	 * @param Reso
	 */
	private static void aggiornaQuantitaResoProdotto(Object reso) {
		ProdottiVenditaLibera prodottoVenditaLibera;
		ProdottiVenditaSSN prodottoVenditaSSN;
		DAOFactory daoFactory = DAOFactory.getInstance();
		ProdottiVenditaLiberaDAO prodottoVenditaLiberaDAO;
		ProdottiVenditaSSNDAO prodottoVenditaSSNDAO;
		ResiProdottiVenditaLibera resoVenditaLibera;
		ResiProdottiVenditaSSN resoVenditaSSN;
		boolean exit = true;
		int capacitaReso = 0;
		int quantitaReso = 0;
		if(reso instanceof ResiProdottiVenditaLibera) {
			resoVenditaLibera = (ResiProdottiVenditaLibera) reso;
			prodottoVenditaLiberaDAO = daoFactory.getProdottoVenditaLiberaDAO();
			quantitaReso = resoVenditaLibera.getQuantita();
			do {
				prodottoVenditaLibera  = prodottoVenditaLiberaDAO.lookUpProdottoReso(resoVenditaLibera.getMinsan(), resoVenditaLibera.getDataReso());
				if (prodottoVenditaLibera.getIdProdottoVenditaLibera() == null)
					exit = true;
				else {
					capacitaReso = prodottoVenditaLibera.getQuantita().intValue() - prodottoVenditaLibera.getQuantitaReso().intValue();
					if (capacitaReso >= quantitaReso) {
						prodottoVenditaLibera.setQuantitaReso(prodottoVenditaLibera.getQuantitaReso().intValue()+quantitaReso);
						prodottoVenditaLibera = ProdottiVenditaLiberaDAOManager.modifica(prodottoVenditaLibera);
						exit = true;
					}
					else {
						prodottoVenditaLibera.setQuantitaReso(prodottoVenditaLibera.getQuantitaReso().intValue()+capacitaReso);
						prodottoVenditaLibera = ProdottiVenditaLiberaDAOManager.modifica(prodottoVenditaLibera);
						quantitaReso = quantitaReso - capacitaReso;
						exit = false;
					}
				}
			}while(!exit);
		}
		
		if (reso instanceof ResiProdottiVenditaSSN) {
			resoVenditaSSN   = (ResiProdottiVenditaSSN) reso;
			prodottoVenditaSSNDAO = daoFactory.getProdottoVenditaSSNDAO();
			quantitaReso = resoVenditaSSN.getQuantita();
			do {
				prodottoVenditaSSN = ProdottiVenditaSSNDAOManager.lookupProdottoPerReso(resoVenditaSSN.getMinsan(), resoVenditaSSN.getDataReso());
				if (prodottoVenditaSSN.getIdProdottoVenditaSSN() == null)
					exit = true;
				else {
					capacitaReso = prodottoVenditaSSN.getQuantita().intValue()-prodottoVenditaSSN.getQuantitaReso().intValue();
					if (capacitaReso >= quantitaReso) {
						prodottoVenditaSSN.setQuantitaReso(prodottoVenditaSSN.getQuantitaReso().intValue()+quantitaReso);
						prodottoVenditaSSN = ProdottiVenditaSSNDAOManager.modifica(prodottoVenditaSSN);
						exit = true;
					}
					else {
						prodottoVenditaSSN.setQuantitaReso(prodottoVenditaSSN.getQuantitaReso()+capacitaReso);
						prodottoVenditaSSN = ProdottiVenditaSSNDAOManager.modifica(prodottoVenditaSSN);
						quantitaReso = quantitaReso - capacitaReso;
						exit = false;
					}
				}
			}while(!exit);
		}
	}
}

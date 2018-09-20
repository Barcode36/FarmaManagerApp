package com.klugesoftware.farmamanager.db;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import com.klugesoftware.farmamanager.model.CustomRoundingAndScaling;
import com.klugesoftware.farmamanager.model.ProdottiVenditaLibera;
import com.klugesoftware.farmamanager.model.ProdottiVenditaSSN;
import com.klugesoftware.farmamanager.model.TipoCosto;
import com.klugesoftware.farmamanager.model.Vendite;
import com.klugesoftware.farmamanager.model.VenditeLibere;
import com.klugesoftware.farmamanager.model.VenditeSSN;
import com.klugesoftware.farmamanager.IOFunctions.TotaliGeneraliVenditaEstratti;

public class VenditeDAOManager {

	public static Vendite inserimentoVendita(Vendite vendita){
		
		DAOFactory daoFactory = DAOFactory.getInstance();
		VenditeDAO venditaDAO = daoFactory.getVenditaDAO();
		return venditaDAO.createVendita(vendita);
	}
	
	public static VenditeLibere inserimentoVenditaLibera(VenditeLibere venditaLibera){
		DAOFactory daoFactory = DAOFactory.getInstance();
		VenditeLibereDAO venditaLiberaDAO = daoFactory.getVenditaLiberaDAO();
		return venditaLiberaDAO.createVenditaLibera(venditaLibera);
	}
	
	public static ProdottiVenditaLibera inserimentoProdottoVenditaLibera(ProdottiVenditaLibera prodottoVenditaLibera){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ProdottiVenditaLiberaDAO prodottoDAO = daoFactory.getProdottoVenditaLiberaDAO();
		return prodottoDAO.create(prodottoVenditaLibera);
	}
	
	public static VenditeSSN inserimentoVenditaSSN(VenditeSSN venditaSSN){
		DAOFactory daoFactory = DAOFactory.getInstance();
		VenditeSSNDAO venditaSSNDAO = daoFactory.getVenditASSNDAO();
		return venditaSSNDAO.create(venditaSSN);
	}
	
	public static ProdottiVenditaSSN inserimentoProdottiVenditaSSN(ProdottiVenditaSSN prodottiVenditaSSN){
		DAOFactory daoFactory = DAOFactory.getInstance();
		ProdottiVenditaSSNDAO prodottiVenditaSSNDAO = daoFactory.getProdottoVenditaSSNDAO();
		return  prodottiVenditaSSNDAO.create(prodottiVenditaSSN);
	}
	/**
	 * inserimento delle venditelibere ed ssn, oltre i relativi prodottivenduti, 
	 * alla stessa venditaGenerale
	 */
	public static Vendite inserimentoVenditaComposta(Vendite venditaGenerale){
		venditaGenerale = inserimentoVendita(venditaGenerale);
		
		Collection<VenditeLibere> elencoVenditeLibere = venditaGenerale.getVenditeLibere();
		Iterator<VenditeLibere> iterVenditeLibere;
		if (elencoVenditeLibere != null){
			iterVenditeLibere = elencoVenditeLibere.iterator();
			VenditeLibere venditaLibera;
			while (iterVenditeLibere.hasNext()){
				venditaLibera = iterVenditeLibere.next();
				venditaLibera = inserimentoVenditaLibera(venditaLibera);
				Collection<ProdottiVenditaLibera> elencoProdottiLibera = venditaLibera.getProdottiVenduti();
				Iterator<ProdottiVenditaLibera> iterProdottiLibera = elencoProdottiLibera.iterator();
				ProdottiVenditaLibera prodottoVenditaLibera;
				while(iterProdottiLibera.hasNext()){
					prodottoVenditaLibera = iterProdottiLibera.next();
					prodottoVenditaLibera = inserimentoProdottoVenditaLibera(prodottoVenditaLibera);
				}
			}
		}
		
		Collection<VenditeSSN> elencoVenditeSSN = venditaGenerale.getVenditeSSN();
		Iterator<VenditeSSN> iterVenditeSSN;
		if (elencoVenditeSSN != null){
			iterVenditeSSN = elencoVenditeSSN.iterator();
			VenditeSSN venditaSSN;
			while(iterVenditeSSN.hasNext()){
				venditaSSN = iterVenditeSSN.next();
				venditaSSN = inserimentoVenditaSSN(venditaSSN);
				Collection<ProdottiVenditaSSN> elencoProdottiSSN = venditaSSN.getProdottiSsnVenduti();
				Iterator<ProdottiVenditaSSN> iterProdottiSSN = elencoProdottiSSN.iterator();
				ProdottiVenditaSSN prodottoVenditaSSN;
				while(iterProdottiSSN.hasNext()){
					prodottoVenditaSSN = iterProdottiSSN.next();
					prodottoVenditaSSN = inserimentoProdottiVenditaSSN(prodottoVenditaSSN);
				}
			}
		}
		
		return venditaGenerale;
	}
	
	public static Vendite lookupVenditaById(int idVendita){
		DAOFactory daoFactory = DAOFactory.getInstance();
		VenditeDAO venditaDAO = daoFactory.getVenditaDAO();
		return venditaDAO.lookupByIdVendita(idVendita);		
	}
	
	public static ArrayList<Vendite> lookupVenditeBetweenDate(Date fromDate,Date toDate){
		DAOFactory daoFactory = DAOFactory.getInstance();
		VenditeDAO venditaDAO = daoFactory.getVenditaDAO();
		return venditaDAO.elencoBetweenDate(fromDate, toDate);
	}
	
	public static ArrayList<Vendite> lookupVenditeWithVenditeLibereSSnEProdotti(Date fromDate,Date toDate){
		ArrayList<Vendite> elencoVenditeGenerali = VenditeDAOManager.lookupVenditeBetweenDate(fromDate, toDate);
		Iterator<Vendite> iterVenditeGenerali = elencoVenditeGenerali.iterator();
		Vendite vendita;
		while(iterVenditeGenerali.hasNext()){
			vendita = iterVenditeGenerali.next();
			ArrayList<VenditeLibere> elencoVenditeLibere = VenditeLibereDAOMAnager.lookupElencoVenditeLibereByIdVenditaGenerale(vendita.getIdVendita());
			ArrayList<VenditeSSN> elencoVenditeSSN = VenditeSSNDAOManager.lookupElencoVenditeSSNByIdVenditaGenerale(vendita.getIdVendita());
			vendita.setVenditeLibere(elencoVenditeLibere);
			vendita.setVenditeSSN(elencoVenditeSSN);
			
			Iterator<VenditeLibere> iterVenditeLibere = elencoVenditeLibere.iterator();
			VenditeLibere venditaLibera;
			while(iterVenditeLibere.hasNext()){
				venditaLibera = iterVenditeLibere.next();
				venditaLibera.setVendita(vendita);
				ArrayList<ProdottiVenditaLibera> elencoProdottiLibera = ProdottiVenditaLiberaDAOManager.elencoByidVenditaLibera(venditaLibera.getIdVenditaLibera());
				venditaLibera.setProdottiVenduti(elencoProdottiLibera);
				Iterator<ProdottiVenditaLibera> iterProdottiVenditaLibera = elencoProdottiLibera.iterator();
				ProdottiVenditaLibera prodottoVenditaLibera;
				while (iterProdottiVenditaLibera.hasNext()){
					prodottoVenditaLibera = iterProdottiVenditaLibera.next();
					prodottoVenditaLibera.setVenditaLibera(venditaLibera);
				}
			}
			
			Iterator<VenditeSSN> iterVenditeSSN = elencoVenditeSSN.iterator();
			VenditeSSN venditaSSN;
			while (iterVenditeSSN.hasNext()){
				venditaSSN = iterVenditeSSN.next();
				venditaSSN.setVendita(vendita);
				ArrayList<ProdottiVenditaSSN> elencoProdottiVenditaSSN = ProdottiVenditaSSNDAOManager.lookupProdottiByIdVenditaSSN(venditaSSN.getIdVenditaSSN());
				venditaSSN.setProdottiSsnVenduti(elencoProdottiVenditaSSN);
				Iterator<ProdottiVenditaSSN> iterProdottiVenditaSSN = elencoProdottiVenditaSSN.iterator();
				ProdottiVenditaSSN prodottoVenditaSSN;
				while (iterProdottiVenditaSSN.hasNext()){
					prodottoVenditaSSN = iterProdottiVenditaSSN.next();
					prodottoVenditaSSN.setVenditaSSN(venditaSSN);
				}
			}
		}
		return elencoVenditeGenerali;
	}
	
	public static Vendite lookupVenditaByMaxId(){
		DAOFactory daoFactory = DAOFactory.getInstance();
		VenditeDAO venditaDAO = daoFactory.getVenditaDAO();
		return venditaDAO.findByMaxId();
	}
	
	public static boolean deleteVenditeBetweenDate(Date dateFrom,Date dateTo){
		DAOFactory daoFactory = DAOFactory.getInstance();
		VenditeDAO venditaDAO = daoFactory.getVenditaDAO();
		ResiVenditeDAO resoDao = daoFactory.getResiVenditeDAO();
		boolean ret = venditaDAO.deleteBetweenDate(dateFrom, dateTo);
		boolean ret2 = resoDao.deleteBetweenDate(dateFrom,dateTo);
		ImportazioniDAOManager.insertLogImportazione("post-delete");

		return ret;
	}
	
	public static TotaliGeneraliVenditaEstratti totaliDatiVendita(Date from, Date to) {
	
		ArrayList<Vendite>  elencoVendite = lookupVenditeWithVenditeLibereSSnEProdotti(from, to);
		
		if (elencoVendite.isEmpty())
			return null;

		boolean costiPresunti = false;
		double totaleLordoVendite = 0;
		double totaleLordoVenditeSSN = 0;
		double totaleLordoVenditeLibere = 0;
		double totaleNettoVendite = 0;
		double totaleNettoVenditeLibere = 0;
		double totaleNettoVenditeSSN = 0;
		double totaleScontiVenditaGenerale = 0;
		double totaleScontiVenditaLibera = 0;
		double totaleScontiSSNVenditeSSN = 0;
		double totaleNettoCosti = 0;
		double totaleNettoCostiVenditeLibere = 0;
		double totaleNettoCostiVenditeSSN = 0;
		double totaleProfitti = 0;
		double totaleProfittiVenditeLibere = 0;
		double totaleProfittiVenditeSSN = 0;
		int totalePezziVenduti = 0;
		int totalePezziVendutiLibere = 0;
		int totalePezziVendutiSSN = 0;
		Iterator<Vendite> iter = elencoVendite.iterator();
		Vendite vendita;
		while(iter.hasNext()){
			vendita = iter.next();
			totaleLordoVendite = totaleLordoVendite + vendita.getTotaleVendita().doubleValue();
			totalePezziVenduti = totalePezziVenduti + vendita.getTotalePezziVenduti();
			Collection<VenditeLibere> venditeLibere = vendita.getVenditeLibere();
			Iterator<VenditeLibere> iterVenditeLibere = venditeLibere.iterator();
			VenditeLibere venditaLibera;
			while(iterVenditeLibere.hasNext()){
				venditaLibera = iterVenditeLibere.next();
				totaleScontiVenditaLibera = totaleScontiVenditaLibera + venditaLibera.getTotaleScontoProdotto().doubleValue();
				totaleScontiVenditaGenerale = totaleScontiVenditaGenerale + venditaLibera.getTotaleScontoProdotto().doubleValue();
				totaleLordoVenditeLibere = totaleLordoVenditeLibere + venditaLibera.getValoreVenditaLibera().doubleValue();
				totalePezziVendutiLibere = totalePezziVendutiLibere + venditaLibera.getTotalePezziVenduti();
				Collection<ProdottiVenditaLibera> elencoProdottiLiberaVendita = venditaLibera.getProdottiVenduti();
				Iterator<ProdottiVenditaLibera> iterProdottiLiberaVendita = elencoProdottiLiberaVendita.iterator();
				ProdottiVenditaLibera prodottoLiberaVendita;
				while (iterProdottiLiberaVendita.hasNext()){
					prodottoLiberaVendita = iterProdottiLiberaVendita.next();
					if (prodottoLiberaVendita.getCostoCompresoIva().doubleValue() != 0){
						totaleNettoVenditeLibere = totaleNettoVenditeLibere + prodottoLiberaVendita.getPrezzoVenditaNetto().doubleValue() * prodottoLiberaVendita.getQuantita();
						totaleNettoVendite = totaleNettoVendite + prodottoLiberaVendita.getPrezzoVenditaNetto().doubleValue() * prodottoLiberaVendita.getQuantita();
	                    totaleProfittiVenditeLibere = totaleProfittiVenditeLibere + prodottoLiberaVendita.getProfittoUnitario().doubleValue() * prodottoLiberaVendita.getQuantita();
						totaleProfitti = totaleProfitti + prodottoLiberaVendita.getProfittoUnitario().doubleValue() * prodottoLiberaVendita.getQuantita();
						double costoDeivato = prodottoLiberaVendita.getCostoCompresoIva().doubleValue()/(1+((double)prodottoLiberaVendita.getAliquotaIva())/100);
						totaleNettoCostiVenditeLibere = totaleNettoCostiVenditeLibere + (prodottoLiberaVendita.getCostoNettoIva().doubleValue()*prodottoLiberaVendita.getQuantita());
						totaleNettoCosti = totaleNettoCosti + (prodottoLiberaVendita.getCostoNettoIva().doubleValue()*prodottoLiberaVendita.getQuantita());
						if (!costiPresunti)	
							if (prodottoLiberaVendita.getTipoCosto().equals(TipoCosto.PRESUNTO))
								costiPresunti = true;
					}
				}
			}
			
			Collection<VenditeSSN> venditeSSN = vendita.getVenditeSSN();
			Iterator<VenditeSSN> iterVenditeSSN = venditeSSN.iterator();
			VenditeSSN venditaSSN;
			while(iterVenditeSSN.hasNext()){
				venditaSSN = iterVenditeSSN.next();
				totaleScontiSSNVenditeSSN = totaleScontiSSNVenditeSSN + venditaSSN.getTotaleScontoSSN().doubleValue();
				totaleScontiVenditaGenerale = totaleScontiVenditaGenerale + venditaSSN.getTotaleScontoSSN().doubleValue();
				totalePezziVendutiSSN = totalePezziVendutiSSN + venditaSSN.getTotalePezziVenduti();
				totaleLordoVenditeSSN = totaleLordoVenditeSSN + venditaSSN.getValoreVenditaSSN().doubleValue();
				Collection<ProdottiVenditaSSN> prodottiSSNVenduti = venditaSSN.getProdottiSsnVenduti();
				Iterator<ProdottiVenditaSSN> iterProdottiSSN = prodottiSSNVenduti.iterator();
				ProdottiVenditaSSN prodottoSSN;
				while (iterProdottiSSN.hasNext()){
					prodottoSSN = iterProdottiSSN.next();
					if (prodottoSSN.getCostoCompresoIva().doubleValue() != 0){
						totaleNettoVendite = totaleNettoVendite + prodottoSSN.getPrezzoVenditaNetto().doubleValue()*prodottoSSN.getQuantita();
						totaleNettoVenditeSSN = totaleNettoVenditeSSN + prodottoSSN.getPrezzoVenditaNetto().doubleValue()*prodottoSSN.getQuantita();
						totaleProfitti = totaleProfitti + prodottoSSN.getProfittoUnitario().doubleValue()*prodottoSSN.getQuantita();
						totaleProfittiVenditeSSN = totaleProfittiVenditeSSN + prodottoSSN.getProfittoUnitario().doubleValue()*prodottoSSN.getQuantita();
						double costoDeivato2 = prodottoSSN.getCostoCompresoIva().doubleValue()/(1+((double)prodottoSSN.getAliquotaIva())/100);
						//totaleNettoCosti = totaleNettoCosti + costoDeivato2*prodottoSSN.getQuantita();
						totaleNettoCosti = totaleNettoCosti + (prodottoSSN.getCostoNettoIva().doubleValue() * prodottoSSN.getQuantita());
						totaleNettoCostiVenditeSSN = totaleNettoCostiVenditeSSN + (prodottoSSN.getCostoNettoIva().doubleValue() * prodottoSSN.getQuantita());
						if (!costiPresunti)	
							if (prodottoSSN.getTipoCosto().equals(TipoCosto.PRESUNTO))
								costiPresunti = true;
					}
				}
			}
		}
			
		TotaliGeneraliVenditaEstratti totaliGeneraliVendita = new TotaliGeneraliVenditaEstratti();
		
		totaliGeneraliVendita.setCostiPresunti(costiPresunti);
		
		totaliGeneraliVendita.setTotaleProfitti(new BigDecimal(totaleProfitti).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		totaliGeneraliVendita.setTotaleProfittiLibere(new BigDecimal(totaleProfittiVenditeLibere).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		totaliGeneraliVendita.setTotaleProfittiSSN(new BigDecimal(totaleProfittiVenditeSSN).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		
		totaliGeneraliVendita.setTotaleCostiNetti(new BigDecimal(totaleNettoCosti).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		totaliGeneraliVendita.setTotaleCostiNettiLibere(new BigDecimal(totaleNettoCostiVenditeLibere).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		totaliGeneraliVendita.setTotaleCostiNettiSSN(new BigDecimal(totaleNettoCostiVenditeSSN).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		
		totaliGeneraliVendita.setTotaleSconti(new BigDecimal(totaleScontiVenditaGenerale).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		totaliGeneraliVendita.setTotaleScontiSSN(new BigDecimal(totaleScontiSSNVenditeSSN).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		totaliGeneraliVendita.setTotaleScontiLibere(new BigDecimal(totaleScontiVenditaLibera).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		
		
		totaliGeneraliVendita.setTotaleVenditeLorde(new BigDecimal(totaleLordoVendite).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		totaliGeneraliVendita.setTotaleVenditeLordeLibere(new BigDecimal(totaleLordoVenditeLibere).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		totaliGeneraliVendita.setTotaleVenditeLordeSSN(new BigDecimal(totaleLordoVenditeSSN).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		
		totaliGeneraliVendita.setTotaleVenditeNette(new BigDecimal(totaleNettoVendite).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		totaliGeneraliVendita.setTotaleVenditeNetteLibere(new BigDecimal(totaleNettoVenditeLibere).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		totaliGeneraliVendita.setTotaleVenditeNetteSSN(new BigDecimal(totaleNettoVenditeSSN).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		
		double tempMargine = (totaliGeneraliVendita.getTotaleProfitti().doubleValue()/totaliGeneraliVendita.getTotaleVenditeNette().doubleValue())*100;
		double tempMargineLibere = (totaliGeneraliVendita.getTotaleProfittiLibere().doubleValue()/totaliGeneraliVendita.getTotaleVenditeNetteLibere().doubleValue())*100;
		double tempMargineSSN = (totaliGeneraliVendita.getTotaleProfittiSSN().doubleValue()/totaliGeneraliVendita.getTotaleVenditeNetteSSN().doubleValue())*100;	
		totaliGeneraliVendita.setMargineMedio(new BigDecimal(tempMargine).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		totaliGeneraliVendita.setMargineMedioLibere(new BigDecimal(tempMargineLibere).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		totaliGeneraliVendita.setMargineMedioSSN(new BigDecimal(tempMargineSSN).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		
		double tempRicarico = (totaliGeneraliVendita.getTotaleProfitti().doubleValue()/totaliGeneraliVendita.getTotaleCostiNetti().doubleValue())*100;
		double tempRicaricoLibere = (totaliGeneraliVendita.getTotaleProfittiLibere().doubleValue()/totaliGeneraliVendita.getTotaleCostiNettiLibere().doubleValue())*100;
		double tempRicaricoSSN = (totaliGeneraliVendita.getTotaleProfittiSSN().doubleValue()/totaliGeneraliVendita.getTotaleCostiNettiSSN().doubleValue())*100;
		totaliGeneraliVendita.setRicaricoMedio(new BigDecimal(tempRicarico).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		totaliGeneraliVendita.setRicaricoMedioLibere(new BigDecimal(tempRicaricoLibere).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		totaliGeneraliVendita.setRicaricoMedioSSN(new BigDecimal(tempRicaricoSSN).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		
		return totaliGeneraliVendita;

	}
}

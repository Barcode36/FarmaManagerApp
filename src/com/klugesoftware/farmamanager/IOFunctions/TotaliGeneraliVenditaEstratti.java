package com.klugesoftware.farmamanager.IOFunctions;

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
import com.klugesoftware.farmamanager.utility.DateUtility;

public class TotaliGeneraliVenditaEstratti {
	
	private Integer	idTotale;
	private BigDecimal totaleVenditeLorde;
	private BigDecimal totaleVenditeLordeLibere;
	private BigDecimal totaleVenditeLordeSSN;
	private BigDecimal totaleVenditeNette;
	private BigDecimal totaleVenditeNetteLibere;
	private BigDecimal totaleVenditeNetteSSN;
	private BigDecimal totaleSconti;
	private BigDecimal totaleScontiSSN;
	private BigDecimal totaleScontiLibere;
	private BigDecimal totaleVenditeNettoSconti;
	private BigDecimal totaleVenditeNettoScontiLibere;
	private BigDecimal totaleVenditeNettoScontiSSN;
	private BigDecimal totaleCostiNetti;
	private BigDecimal totaleCostiNettiLibere;
	private BigDecimal totaleCostiNettiSSN;
	private BigDecimal totaleProfitti;
	private BigDecimal totaleProfittiLibere;
	private BigDecimal totaleProfittiSSN;
	private BigDecimal margineMedio;
	private BigDecimal margineMedioLibere;
	private BigDecimal margineMedioSSN;
	private BigDecimal ricaricoMedio;
	private BigDecimal ricaricoMedioLibere;
	private BigDecimal ricaricoMedioSSN;
	private boolean costiPresunti;
	private int mese;
	private int anno;
	private Date dataUltimoAggiornamento;

	public TotaliGeneraliVenditaEstratti(){
		totaleVenditeLorde = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleVenditeLordeLibere = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleVenditeLordeSSN = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleVenditeNette = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleVenditeNetteLibere = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleVenditeNetteSSN = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleVenditeNettoSconti = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleVenditeNettoScontiLibere = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleVenditeNettoScontiSSN = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleSconti = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleScontiSSN = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleScontiLibere = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleCostiNetti = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleCostiNettiLibere = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleCostiNettiSSN = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleProfitti = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleProfittiLibere = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleProfittiSSN = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		margineMedio = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		margineMedioLibere = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		margineMedioSSN = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		ricaricoMedio = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		ricaricoMedioLibere = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		ricaricoMedioSSN = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		costiPresunti = false;
	}

	public TotaliGeneraliVenditaEstratti(
			Integer idTotale,
			BigDecimal totaleVenditeLorde,
			BigDecimal totaleVenditeLordeLibere,
			BigDecimal totaleVenditeLordeSSN,
			BigDecimal totaleVenditeNette,
			BigDecimal totaleVenditeNetteLibere,
			BigDecimal totaleVenditeNetteSSN,
			BigDecimal totaleVenditeNettoSconti,
			BigDecimal totaleVenditeNettoScontiLibere,
			BigDecimal totaleVenditeNettoScontiSSN,
			BigDecimal totaleSconti,
			BigDecimal totaleScontiSSN,
			BigDecimal totaleScontiLibere,
			BigDecimal totaleCostiNetti,
			BigDecimal totaleCostiNettiLibere,
			BigDecimal totaleCostiNettiSSN,
			BigDecimal totaleProfitti,
			BigDecimal totaleProfittiLibere,
			BigDecimal totaleProfittiSSN,
			BigDecimal margineMedio,
			BigDecimal margineMedioLibere,
			BigDecimal margineMedioSSN,
			BigDecimal ricaricoMedio,
			BigDecimal ricaricoMedioLibere,
			BigDecimal ricaricoMedioSSN,
			boolean costiPresunti,
			int mese,
			int anno,
			Date dataUltimoAggiornamento
			){
		this.idTotale = idTotale;
		this.totaleVenditeLorde = totaleVenditeLorde;
		this.totaleVenditeLordeLibere = totaleVenditeLordeLibere;
		this.totaleVenditeLordeSSN = totaleVenditeLordeSSN;
		this.totaleVenditeNette = totaleVenditeNette;
		this.totaleVenditeNetteLibere = totaleVenditeNetteLibere;
		this.totaleVenditeNetteSSN = totaleVenditeNetteSSN;
		this.totaleVenditeNettoSconti = totaleVenditeNettoSconti;
		this.totaleVenditeNettoScontiLibere = totaleVenditeNettoScontiLibere;
		this.totaleVenditeNettoScontiSSN = totaleVenditeNettoScontiSSN;
		this.totaleSconti = totaleSconti;
		this.totaleScontiSSN = totaleScontiSSN;
		this.totaleScontiLibere = totaleScontiLibere;
		this.totaleCostiNetti = totaleCostiNetti;
		this.totaleCostiNettiLibere = totaleCostiNettiLibere;
		this.totaleCostiNettiSSN = totaleCostiNettiSSN;
		this.totaleProfitti = totaleProfitti;
		this.totaleProfittiLibere = totaleProfittiLibere;
		this.totaleProfittiSSN = totaleProfittiSSN;
		this.margineMedio = margineMedio;
		this.margineMedioLibere = margineMedioLibere;
		this.margineMedioSSN = margineMedioSSN;
		this.ricaricoMedio = ricaricoMedio;
		this.ricaricoMedioLibere = ricaricoMedioLibere;
		this.ricaricoMedioSSN = ricaricoMedioSSN;		
		this.costiPresunti = costiPresunti;
		this.mese = mese;
		this.anno = anno;
		this.dataUltimoAggiornamento = dataUltimoAggiornamento;
	}
	
	
	public Integer getIdTotale() {
		return idTotale;
	}

	public void setIdTotale(Integer idTotale) {
		this.idTotale = idTotale;
	}

	public boolean isCostiPresunti() {
		return costiPresunti;
	}

	public void setCostiPresunti(boolean costiPresunti) {
		this.costiPresunti = costiPresunti;
	}

	public BigDecimal getTotaleVenditeLorde() {
		return totaleVenditeLorde;
	}

	public void setTotaleVenditeLorde(BigDecimal totaleVenditeLorde) {
		this.totaleVenditeLorde = (this.totaleVenditeLorde.add(totaleVenditeLorde)).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());;
	}

	public BigDecimal getTotaleVenditeNette() {
		return totaleVenditeNette;
	}

	public void setTotaleVenditeNette(BigDecimal totaleVenditeNette) {
		this.totaleVenditeNette = (this.totaleVenditeNette.add(totaleVenditeNette)).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());;
	}

	public BigDecimal getTotaleSconti() {
		return totaleSconti;
	}

	public void setTotaleSconti(BigDecimal totaleSconti) {
		this.totaleSconti = (this.totaleSconti.add(totaleSconti)).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());;
	}

	public BigDecimal getTotaleScontiSSN() {
		return totaleScontiSSN;
	}

	public void setTotaleScontiSSN(BigDecimal totaleScontiSSN) {
		this.totaleScontiSSN = (this.totaleScontiSSN.add(totaleScontiSSN)).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());;
	}

	public BigDecimal getTotaleCostiNetti() {
		return totaleCostiNetti;
	}

	public void setTotaleCostiNetti(BigDecimal totaleCostiNetti) {
		this.totaleCostiNetti = (this.totaleCostiNetti.add(totaleCostiNetti)).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());;
	}

	public BigDecimal getTotaleProfitti() {
		return totaleProfitti;
	}

	public void setTotaleProfitti(BigDecimal totaleProfitti) {
		this.totaleProfitti = (this.totaleProfitti.add(totaleProfitti)).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
	}

	public BigDecimal getMargineMedio() {
		return margineMedio;
	}

	public void setMargineMedio(BigDecimal margineMedio) {
		this.margineMedio = margineMedio;
	}

	public BigDecimal getRicaricoMedio() {
		return ricaricoMedio;
	}

	public void setRicaricoMedio(BigDecimal ricaricoMedio) {
		this.ricaricoMedio = ricaricoMedio;
	}

	public BigDecimal getTotaleVenditeLordeLibere() {
		return totaleVenditeLordeLibere;
	}

	public void setTotaleVenditeLordeLibere(BigDecimal totaleVenditeLordeLibere) {
		this.totaleVenditeLordeLibere = (this.totaleVenditeLordeLibere.add(totaleVenditeLordeLibere)).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());;
	}

	public BigDecimal getTotaleVenditeLordeSSN() {
		return totaleVenditeLordeSSN;
	}

	public void setTotaleVenditeLordeSSN(BigDecimal totaleVenditeLordeSSN) {
		this.totaleVenditeLordeSSN = (this.totaleVenditeLordeSSN.add(totaleVenditeLordeSSN)).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());;
	}

	public BigDecimal getTotaleVenditeNetteLibere() {
		return totaleVenditeNetteLibere;
	}

	public void setTotaleVenditeNetteLibere(BigDecimal totaleVenditeNetteLibere) {
		this.totaleVenditeNetteLibere = (this.totaleVenditeNetteLibere.add(totaleVenditeNetteLibere)).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());;
	}

	public BigDecimal getTotaleVenditeNetteSSN() {
		return totaleVenditeNetteSSN;
	}

	public void setTotaleVenditeNetteSSN(BigDecimal totaleVenditeNetteSSN) {
		this.totaleVenditeNetteSSN = (this.totaleVenditeNetteSSN.add(totaleVenditeNetteSSN)).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());;
	}

	public BigDecimal getTotaleScontiLibere() {
		return totaleScontiLibere;
	}

	public void setTotaleScontiLibere(BigDecimal totaleScontiLibere) {
		this.totaleScontiLibere = (this.totaleScontiLibere.add(totaleScontiLibere)).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());;
	}

	public BigDecimal getTotaleCostiNettiLibere() {
		return totaleCostiNettiLibere;
	}

	public void setTotaleCostiNettiLibere(BigDecimal totaleCostiNettiLibere) {
		this.totaleCostiNettiLibere = (this.totaleCostiNettiLibere.add(totaleCostiNettiLibere)).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());;
	}

	public BigDecimal getTotaleCostiNettiSSN() {
		return totaleCostiNettiSSN;
	}

	public void setTotaleCostiNettiSSN(BigDecimal totaleCostiNettiSSN) {
		this.totaleCostiNettiSSN = (this.totaleCostiNettiSSN.add(totaleCostiNettiSSN)).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());;
	}

	public BigDecimal getTotaleProfittiLibere() {
		return totaleProfittiLibere;
	}

	public void setTotaleProfittiLibere(BigDecimal totaleProfittiLibere) {
		this.totaleProfittiLibere = (this.totaleProfittiLibere.add(totaleProfittiLibere)).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());;
	}

	public BigDecimal getTotaleProfittiSSN() {
		return totaleProfittiSSN;
	}

	public void setTotaleProfittiSSN(BigDecimal totaleProfittiSSN) {
		this.totaleProfittiSSN = (this.totaleProfittiSSN.add(totaleProfittiSSN)).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());;
	}

	public BigDecimal getMargineMedioLibere() {
		return margineMedioLibere;
	}

	public void setMargineMedioLibere(BigDecimal margineMedioLibere) {
		this.margineMedioLibere = margineMedioLibere;
	}

	public BigDecimal getMargineMedioSSN() {
		return margineMedioSSN;
	}

	public void setMargineMedioSSN(BigDecimal margineMedioSSN) {
		this.margineMedioSSN = margineMedioSSN;
	}

	public BigDecimal getRicaricoMedioLibere() {
		return ricaricoMedioLibere;
	}

	public void setRicaricoMedioLibere(BigDecimal ricaricoMedioLibere) {
		this.ricaricoMedioLibere = ricaricoMedioLibere;
	}

	public BigDecimal getRicaricoMedioSSN() {
		return ricaricoMedioSSN;
	}

	public void setRicaricoMedioSSN(BigDecimal ricaricoMedioSSN) {
		this.ricaricoMedioSSN = ricaricoMedioSSN;
	}
	
	public void sottraiResi(TotaliGeneraliResiVenditaEstratti resi){
		if (resi != null){
			totaleVenditeLorde = totaleVenditeLorde.add(resi.getTotaleResiVenditeLorde().multiply(new BigDecimal(-1))); 
			totaleVenditeLordeLibere = totaleVenditeLordeLibere.add(resi.getTotaleResiVenditeLordeLibere().multiply(new BigDecimal(-1)));
			totaleVenditeLordeSSN = totaleVenditeLordeSSN.add(resi.getTotaleResiVenditeLordeSSN().multiply(new BigDecimal(-1)));
			totaleVenditeNette = totaleVenditeNette.add(resi.getTotaleResiVenditeNette().multiply(new BigDecimal(-1)));
			totaleVenditeNetteLibere = totaleVenditeNetteLibere.add(resi.getTotaleResiVenditeNetteLibere().multiply(new BigDecimal(-1)));
			totaleVenditeNetteSSN = totaleVenditeNetteSSN.add(resi.getTotaleResiVenditeNetteSSN().multiply(new BigDecimal(-1)));
			totaleSconti = totaleSconti.add(resi.getTotaleResiSconti().multiply(new BigDecimal(-1)));
			totaleScontiSSN = totaleScontiSSN.add(resi.getTotaleResiScontiSSN().multiply(new BigDecimal(-1)));
			totaleScontiLibere = totaleScontiLibere.add(resi.getTotaleResiScontiLibere().multiply(new BigDecimal(-1)));
			totaleCostiNetti = totaleCostiNetti.add(resi.getTotaleResiCostiNetti().multiply(new BigDecimal(-1)));
			totaleCostiNettiLibere = totaleCostiNettiLibere.add(resi.getTotaleResiCostiNettiLibere().multiply(new BigDecimal(-1)));
			totaleCostiNettiSSN = totaleCostiNettiSSN.add(resi.getTotaleResiCostiNettiSSN().multiply(new BigDecimal(-1)));
			totaleProfitti = totaleProfitti.add(resi.getTotaleResiProfitti().multiply(new BigDecimal(-1)));
			totaleProfittiLibere = totaleProfittiLibere.add(resi.getTotaleResiProfittiLibere().multiply(new BigDecimal(-1)));
			totaleProfittiSSN = totaleProfittiSSN.add(resi.getTotaleResiProfittiSSN().multiply(new BigDecimal(-1)));
			totaleVenditeNettoSconti = totaleVenditeLorde.add(totaleSconti.multiply(new BigDecimal(-1)));
			totaleVenditeNettoScontiLibere = totaleVenditeLordeLibere.add(totaleScontiLibere.multiply(new BigDecimal(-1)));
			totaleVenditeNettoScontiSSN = totaleVenditeLordeSSN.add(totaleScontiSSN.multiply(new BigDecimal(-1)));
		}
	}
	
	public void aggiornaTotaliGenerali(Vendite vendita){
		ArrayList<Vendite> elenco = new ArrayList<Vendite>();
		elenco.add(vendita);
		aggiornaTotali(elenco);
	}
	
	private void aggiornaTotali(ArrayList<Vendite> elencoVendite){
		
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
		Vendite vendita = new Vendite();
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
		
		//TotaliGeneraliVenditaEstratti totaliGeneraliVendita = new TotaliGeneraliVenditaEstratti();
		
		if(!isCostiPresunti() && costiPresunti)
			this.setCostiPresunti(costiPresunti);
		
		this.setTotaleProfitti(new BigDecimal(totaleProfitti).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		this.setTotaleProfittiLibere(new BigDecimal(totaleProfittiVenditeLibere).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		this.setTotaleProfittiSSN(new BigDecimal(totaleProfittiVenditeSSN).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		
		this.setTotaleCostiNetti(new BigDecimal(totaleNettoCosti).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		this.setTotaleCostiNettiLibere(new BigDecimal(totaleNettoCostiVenditeLibere).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		this.setTotaleCostiNettiSSN(new BigDecimal(totaleNettoCostiVenditeSSN).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		
		this.setTotaleSconti(new BigDecimal(totaleScontiVenditaGenerale).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		this.setTotaleScontiSSN(new BigDecimal(totaleScontiSSNVenditeSSN).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		this.setTotaleScontiLibere(new BigDecimal(totaleScontiVenditaLibera).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		
		
		this.setTotaleVenditeLorde(new BigDecimal(totaleLordoVendite).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		this.setTotaleVenditeLordeLibere(new BigDecimal(totaleLordoVenditeLibere).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		this.setTotaleVenditeLordeSSN(new BigDecimal(totaleLordoVenditeSSN).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		
		this.setTotaleVenditeNette(new BigDecimal(totaleNettoVendite).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		this.setTotaleVenditeNetteLibere(new BigDecimal(totaleNettoVenditeLibere).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		this.setTotaleVenditeNetteSSN(new BigDecimal(totaleNettoVenditeSSN).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		
		double temp1 = totaleVenditeLorde.doubleValue() - totaleSconti.doubleValue();
		double temp2 = totaleVenditeLordeLibere.doubleValue() - totaleScontiLibere.doubleValue();
		double temp3 = totaleVenditeLordeSSN.doubleValue() - totaleScontiSSN.doubleValue();
		this.setTotaleVenditeNettoSconti(new BigDecimal(temp1).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		this.setTotaleVenditeNettoScontiLibere(new BigDecimal(temp2).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		this.setTotaleVenditeNettoScontiSSN(new BigDecimal(temp3).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		
		double tempMargine = 0;
		double tempMargineLibere = 0;
		double tempMargineSSN = 0;
		if (this.getTotaleProfitti().doubleValue() > 0 && this.getTotaleVenditeNette().doubleValue() > 0)
			tempMargine = (this.getTotaleProfitti().doubleValue()/this.getTotaleVenditeNette().doubleValue())*100;
		if (this.getTotaleProfittiLibere().doubleValue() > 0 && this.getTotaleVenditeNetteLibere().doubleValue() > 0)
			tempMargineLibere = (this.getTotaleProfittiLibere().doubleValue()/this.getTotaleVenditeNetteLibere().doubleValue())*100;
		if (this.getTotaleProfittiSSN().doubleValue() > 0 && this.getTotaleVenditeNetteSSN().doubleValue() > 0)
			tempMargineSSN = (this.getTotaleProfittiSSN().doubleValue()/this.getTotaleVenditeNetteSSN().doubleValue())*100;	
		this.setMargineMedio(new BigDecimal(tempMargine).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		this.setMargineMedioLibere(new BigDecimal(tempMargineLibere).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		this.setMargineMedioSSN(new BigDecimal(tempMargineSSN).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		
		double tempRicarico = 0;
		double tempRicaricoLibere = 0;
		double tempRicaricoSSN = 0;
		if (this.getTotaleProfitti().doubleValue() > 0 && this.getTotaleCostiNetti().doubleValue() > 0)
			tempRicarico = (this.getTotaleProfitti().doubleValue()/this.getTotaleCostiNetti().doubleValue())*100;
		if (this.getTotaleProfittiLibere().doubleValue() > 0 && this.getTotaleCostiNettiLibere().doubleValue() > 0)
			tempRicaricoLibere = (this.getTotaleProfittiLibere().doubleValue()/this.getTotaleCostiNettiLibere().doubleValue())*100;
		if (this.getTotaleProfittiSSN().doubleValue() > 0 && this.getTotaleCostiNettiSSN().doubleValue() > 0)
			tempRicaricoSSN = (this.getTotaleProfittiSSN().doubleValue()/this.getTotaleCostiNettiSSN().doubleValue())*100;
		this.setRicaricoMedio(new BigDecimal(tempRicarico).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		this.setRicaricoMedioLibere(new BigDecimal(tempRicaricoLibere).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		this.setRicaricoMedioSSN(new BigDecimal(tempRicaricoSSN).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		
		// inserimento Date
		this.setMese(DateUtility.getMese(vendita.getDataVendita()));
		this.setAnno(DateUtility.getAnno(vendita.getDataVendita()));
		this.setDataUltimoAggiornamento(DateUtility.getDataOdierna());
	}

	public int getMese() {
		return mese;
	}

	public void setMese(int mese) {
		this.mese = mese;
	}

	public int getAnno() {
		return anno;
	}

	public void setAnno(int anno) {
		this.anno = anno;
	}

	public Date getDataUltimoAggiornamento() {
		return dataUltimoAggiornamento;
	}

	public void setDataUltimoAggiornamento(Date dataUltimoAggiornamento) {
		this.dataUltimoAggiornamento = dataUltimoAggiornamento;
	}

	public BigDecimal getTotaleVenditeNettoSconti() {
		return totaleVenditeNettoSconti;
	}

	public void setTotaleVenditeNettoSconti(BigDecimal totaleVenditeNettoSconti) {
		this.totaleVenditeNettoSconti = totaleVenditeNettoSconti;
	}

	public BigDecimal getTotaleVenditeNettoScontiLibere() {
		return totaleVenditeNettoScontiLibere;
	}

	public void setTotaleVenditeNettoScontiLibere(BigDecimal totaleVenditeNettoScontiLibere) {
		this.totaleVenditeNettoScontiLibere = totaleVenditeNettoScontiLibere;
	}

	public BigDecimal getTotaleVenditeNettoScontiSSN() {
		return totaleVenditeNettoScontiSSN;
	}

	public void setTotaleVenditeNettoScontiSSN(BigDecimal totaleVenditeNettoScontiSSN) {
		this.totaleVenditeNettoScontiSSN = totaleVenditeNettoScontiSSN;
	}
}

package com.klugesoftware.farmamanager.IOFunctions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.klugesoftware.farmamanager.model.CustomRoundingAndScaling;
import com.klugesoftware.farmamanager.model.ResiProdottiVenditaLibera;
import com.klugesoftware.farmamanager.model.ResiProdottiVenditaSSN;
import com.klugesoftware.farmamanager.model.ResiVendite;
import com.klugesoftware.farmamanager.model.ResiVenditeLibere;
import com.klugesoftware.farmamanager.model.ResiVenditeSSN;

public class TotaliGeneraliResiVenditaEstratti {
	
	private BigDecimal totaleResiVenditeLorde;
	private BigDecimal totaleResiVenditeLordeLibere;
	private BigDecimal totaleResiVenditeLordeSSN;
	private BigDecimal totaleResiVenditeNette;
	private BigDecimal totaleResiVenditeNetteLibere;
	private BigDecimal totaleResiVenditeNetteSSN;
	private BigDecimal totaleResiSconti;
	private BigDecimal totaleResiScontiSSN;
	private BigDecimal totaleResiScontiLibere;
	private BigDecimal totaleResiCostiNetti;
	private BigDecimal totaleResiCostiNettiLibere;
	private BigDecimal totaleResiCostiNettiSSN;
	private BigDecimal totaleResiProfitti;
	private BigDecimal totaleResiProfittiLibere;
	private BigDecimal totaleResiProfittiSSN;

	public TotaliGeneraliResiVenditaEstratti(){

		totaleResiVenditeLorde = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleResiVenditeLordeLibere = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleResiVenditeLordeSSN = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleResiVenditeNette = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleResiVenditeNetteLibere = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleResiVenditeNetteSSN = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleResiSconti = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleResiScontiSSN = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleResiScontiLibere = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleResiCostiNetti = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleResiCostiNettiLibere = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleResiCostiNettiSSN = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleResiProfitti = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleResiProfittiLibere = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleResiProfittiSSN = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());	
	}

	public BigDecimal getTotaleResiVenditeLorde() {
		return totaleResiVenditeLorde;
	}

	public void setTotaleResiVenditeLorde(BigDecimal totaleResiVenditeLorde) {
		this.totaleResiVenditeLorde = (this.totaleResiVenditeLorde.add(totaleResiVenditeLorde)).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());;
	}

	public BigDecimal getTotaleResiVenditeLordeLibere() {
		return totaleResiVenditeLordeLibere;
	}

	public void setTotaleResiVenditeLordeLibere(BigDecimal totaleResiVenditeLordeLibere) {
		this.totaleResiVenditeLordeLibere = (this.totaleResiVenditeLordeLibere.add(totaleResiVenditeLordeLibere)).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());;
	}

	public BigDecimal getTotaleResiVenditeLordeSSN() {
		return totaleResiVenditeLordeSSN;
	}

	public void setTotaleResiVenditeLordeSSN(BigDecimal totaleResiVenditeLordeSSN) {
		this.totaleResiVenditeLordeSSN = (this.totaleResiVenditeLordeSSN.add(totaleResiVenditeLordeSSN)).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());;
	}

	public BigDecimal getTotaleResiVenditeNette() {
		return totaleResiVenditeNette;
	}

	public void setTotaleResiVenditeNette(BigDecimal totaleResiVenditeNette) {
		this.totaleResiVenditeNette = (this.totaleResiVenditeNette.add(totaleResiVenditeNette)).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());;
	}

	public BigDecimal getTotaleResiVenditeNetteLibere() {
		return totaleResiVenditeNetteLibere;
	}

	public void setTotaleResiVenditeNetteLibere(BigDecimal totaleResiVenditeNetteLibere) {
		this.totaleResiVenditeNetteLibere = (this.totaleResiVenditeNetteLibere.add(totaleResiVenditeNetteLibere)).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());;
	}

	public BigDecimal getTotaleResiVenditeNetteSSN() {
		return totaleResiVenditeNetteSSN;
	}

	public void setTotaleResiVenditeNetteSSN(BigDecimal totaleResiVenditeNetteSSN) {
		this.totaleResiVenditeNetteSSN = (this.totaleResiVenditeNetteSSN.add(totaleResiVenditeNetteSSN)).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());;
	}

	public BigDecimal getTotaleResiSconti() {
		return totaleResiSconti;
	}

	public void setTotaleResiSconti(BigDecimal totaleResiSconti) {
		this.totaleResiSconti = (this.totaleResiSconti.add(totaleResiSconti)).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());;
	}

	public BigDecimal getTotaleResiScontiSSN() {
		return totaleResiScontiSSN;
	}

	public void setTotaleResiScontiSSN(BigDecimal totaleResiScontiSSN) {
		this.totaleResiScontiSSN = (this.totaleResiScontiSSN.add(totaleResiScontiSSN)).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());;
	}

	public BigDecimal getTotaleResiScontiLibere() {
		return totaleResiScontiLibere;
	}

	public void setTotaleResiScontiLibere(BigDecimal totaleResiScontiLibere) {
		this.totaleResiScontiLibere = (this.totaleResiScontiLibere.add(totaleResiScontiLibere)).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());;
	}

	public BigDecimal getTotaleResiCostiNetti() {
		return totaleResiCostiNetti;
	}

	public void setTotaleResiCostiNetti(BigDecimal totaleResiCostiNetti) {
		this.totaleResiCostiNetti = (this.totaleResiCostiNetti.add(totaleResiCostiNetti)).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());;
	}

	public BigDecimal getTotaleResiCostiNettiLibere() {
		return totaleResiCostiNettiLibere;
	}

	public void setTotaleResiCostiNettiLibere(BigDecimal totaleResiCostiNettiLibere) {
		this.totaleResiCostiNettiLibere = (this.totaleResiCostiNettiLibere.add(totaleResiCostiNettiLibere)).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());;
	}

	public BigDecimal getTotaleResiCostiNettiSSN() {
		return totaleResiCostiNettiSSN;
	}

	public void setTotaleResiCostiNettiSSN(BigDecimal totaleResiCostiNettiSSN) {
		this.totaleResiCostiNettiSSN = (this.totaleResiCostiNettiSSN.add(totaleResiCostiNettiSSN)).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());;
	}

	public BigDecimal getTotaleResiProfitti() {
		return totaleResiProfitti;
	}

	public void setTotaleResiProfitti(BigDecimal totaleResiProfitti) {
		this.totaleResiProfitti = (this.totaleResiProfitti.add(totaleResiProfitti)).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());;
	}

	public BigDecimal getTotaleResiProfittiLibere() {
		return totaleResiProfittiLibere;
	}

	public void setTotaleResiProfittiLibere(BigDecimal totaleResiProfittiLibere) {
		this.totaleResiProfittiLibere = (this.totaleResiProfittiLibere.add(totaleResiProfittiLibere)).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());;
	}

	public BigDecimal getTotaleResiProfittiSSN() {
		return totaleResiProfittiSSN;
	}

	public void setTotaleResiProfittiSSN(BigDecimal totaleResiProfittiSSN) {
		this.totaleResiProfittiSSN = (this.totaleResiProfittiSSN.add(totaleResiProfittiSSN)).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());;
	}
	
	public void aggiornaTotaliGeneraliResiEstratti(ResiVendite resoVenditaGenerale){
		
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
		ArrayList<ResiVendite> results = new ArrayList<ResiVendite>();
		results.add(resoVenditaGenerale);
		Iterator<ResiVendite> iter = results.iterator();
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
		
		this.setTotaleResiProfitti(new BigDecimal(totaleResiProfitti).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		this.setTotaleResiProfittiLibere(new BigDecimal(totaleResiProfittiVenditeLibere).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		this.setTotaleResiProfittiSSN(new BigDecimal(totaleResiProfittiVenditeSSN).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		
		this.setTotaleResiCostiNetti(new BigDecimal(totaleResiNettoCosti).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		this.setTotaleResiCostiNettiLibere(new BigDecimal(totaleResiNettoCostiVenditeLibere).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		this.setTotaleResiCostiNettiSSN(new BigDecimal(totaleResiNettoCostiVenditeSSN).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		
		this.setTotaleResiSconti(new BigDecimal(totaleResiScontiVenditaGenerale).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		this.setTotaleResiScontiLibere(new BigDecimal(totaleResiScontiVenditaLibera).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		this.setTotaleResiScontiSSN(new BigDecimal(totaleResiScontiSSNVenditeSSN).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		
		this.setTotaleResiVenditeLorde(new BigDecimal(totaleResiLordoVendite).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		this.setTotaleResiVenditeLordeLibere(new BigDecimal(totaleResiLordoVenditeLibere).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		this.setTotaleResiVenditeLordeSSN(new BigDecimal(totaleResiLordoVenditeSSN).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		
		this.setTotaleResiVenditeNette(new BigDecimal(totaleResiNettoVendite).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		this.setTotaleResiVenditeNetteLibere(new BigDecimal(totaleResiNettoVenditeLibere).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
		this.setTotaleResiVenditeNetteSSN(new BigDecimal(totaleResiNettoVenditeSSN).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));

	}
}

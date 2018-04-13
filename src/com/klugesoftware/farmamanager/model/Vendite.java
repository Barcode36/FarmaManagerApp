package com.klugesoftware.farmamanager.model;

import java.io.Serializable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


/**
 * The class for the Vendite database table.
 * 
 */
public class Vendite implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idVendita;
	
	private Integer numreg;

	private Date dataVendita;

	private BigDecimal totaleVendita;

	private BigDecimal totaleVenditaScontata;
	
	private BigDecimal scontoVendita;
	
	private Integer totalePezziVenduti;
	
	private Collection<VenditeSSN> venditeSSN;

	private Collection<VenditeLibere> venditeLibere;
	
	public Vendite() {
		totaleVendita = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleVenditaScontata = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		scontoVendita = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totalePezziVenduti = new Integer(0);
		venditeLibere = new ArrayList<VenditeLibere>();
		venditeSSN = new ArrayList<VenditeSSN>();
	}
	
	public Vendite(
			Integer idVendita,
			Integer numreg,
			Date dataVendita,
			BigDecimal totaleVendita,
			BigDecimal totaleVenditaScontata,
			BigDecimal scontoVendita,
			Integer totalePezziVenduti
			){
		this.idVendita = idVendita;
		this.numreg = numreg;
		this.dataVendita = dataVendita;
		this.totaleVendita = totaleVendita;
		this.totaleVenditaScontata = totaleVenditaScontata;
		this.scontoVendita = scontoVendita;
		this.totalePezziVenduti = totalePezziVenduti;		
	}

	public Integer getIdVendita() {
		return this.idVendita;
	}

	public void setIdVendita(Integer idVendita) {
		this.idVendita = idVendita;
	}

	public Integer getNumreg() {
		return numreg;
	}

	public void setNumreg(Integer numreg) {
		this.numreg = numreg;
	}

	public Date getDataVendita() {
		return this.dataVendita;
	}

	public void setDataVendita(Date dataVendita) {
		this.dataVendita = dataVendita;
	}

	public BigDecimal getTotaleVendita() {
		return this.totaleVendita;
	}

	public void setTotaleVendita(BigDecimal totaleVendita) {
		this.totaleVendita = totaleVendita;
	}

	public BigDecimal getTotaleVenditaScontata() {
		return this.totaleVenditaScontata;
	}

	public void setTotaleVenditaScontata(BigDecimal totaleVenditaScontata) {
		this.totaleVenditaScontata = totaleVenditaScontata;
	}

	public BigDecimal getScontoVendita() {
		return scontoVendita;
	}

	public void setScontoVendita(BigDecimal scontoVendita) {
		this.scontoVendita = scontoVendita;
	}

	public Integer getTotalePezziVenduti() {
		return totalePezziVenduti;
	}

	public void setTotalePezziVenduti(Integer totalePezziVenduti) {
		this.totalePezziVenduti = totalePezziVenduti;
	}

	public Collection<VenditeSSN> getVenditeSSN() {
		return venditeSSN;
	}

	public void setVenditeSSN(Collection<VenditeSSN> venditeSSN) {
		this.venditeSSN = venditeSSN;
	}

	public Collection<VenditeLibere> getVenditeLibere() {
		return venditeLibere;
	}

	public void setVenditeLibere(Collection<VenditeLibere> venditeLibere) {
		this.venditeLibere = venditeLibere;
	}
	
	public void addVenditaSSN(VenditeSSN vendita){
		if (venditeSSN == null)
			venditeSSN = new ArrayList<VenditeSSN>();
		venditeSSN.add(vendita);
	}
	
	public void addVenditaLibera(VenditeLibere vendita){
		if (venditeLibere == null)
			venditeLibere = new ArrayList<VenditeLibere>();
		venditeLibere.add(vendita);
	}

}
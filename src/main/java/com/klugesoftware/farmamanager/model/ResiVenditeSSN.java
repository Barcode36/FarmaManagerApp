package com.klugesoftware.farmamanager.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The class for the ResiVenditeSSN database table.
 * 
 */
public class ResiVenditeSSN implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idResoVenditaSSN;
	
	/**
	 * campo RIC del file DBF
	 */
	private String campoRicReso;
	
	private String codiceFiscale;

	private Date dataResoVenditaSSN;

	private String esenzione;

	private Integer numreg;

	private BigDecimal quotaAssistito;

	private BigDecimal quotaRicetta;

	private BigDecimal totaleIva;

	private Integer totalePezziResi;

	private BigDecimal totaleRicetta;

	private BigDecimal totaleScontoSSN;

	private BigDecimal valoreVenditaSSN;

	private ResiVendite resiVendite;

	private List<ResiProdottiVenditaSSN> resiProdottiVenditaSsns;

	public ResiVenditeSSN() {
		campoRicReso = "00";
		quotaAssistito = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		quotaRicetta = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleIva = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleRicetta = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleScontoSSN = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		valoreVenditaSSN = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		resiProdottiVenditaSsns = new ArrayList<ResiProdottiVenditaSSN>();
		totalePezziResi = new Integer(0);
		numreg = new Integer(0);
	}
	
	public ResiVenditeSSN(
			Integer idResoVenditaSSN,			
			String codiceFiscale,
			Date dataResoVenditaSSN,
			String esenzione,
			Integer numreg,
			BigDecimal quotaAssistito,
			BigDecimal quotaRicetta,
			BigDecimal totaleIva,
			Integer totalePezziResi,
			BigDecimal totaleRicetta,
			BigDecimal totaleScontoSSN,
			BigDecimal valoreVenditaSSN,
			ResiVendite resiVendite
			) {
		this.campoRicReso = "00";
		this.idResoVenditaSSN = idResoVenditaSSN;			
		this.codiceFiscale = codiceFiscale;
		this.dataResoVenditaSSN = dataResoVenditaSSN;
		this.esenzione = esenzione;
		this.numreg = numreg;
		this.quotaAssistito = quotaAssistito;
		this.quotaRicetta = quotaRicetta;
		this.totaleIva = totaleIva;
		this.totalePezziResi = totalePezziResi;
		this.totaleRicetta = totaleRicetta;
		this.totaleScontoSSN = totaleScontoSSN;
		this.valoreVenditaSSN = valoreVenditaSSN;
		this.resiVendite = resiVendite;		
	}

	public Integer getIdResoVenditaSSN() {
		return this.idResoVenditaSSN;
	}

	public void setIdResoVenditaSSN(Integer idResoVenditaSSN) {
		this.idResoVenditaSSN = idResoVenditaSSN;
	}

	public String getCampoRicReso() {
		return campoRicReso;
	}

	public void setCampoRicReso(String campoRicReso) {
		this.campoRicReso = campoRicReso;
	}

	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public Date getDataResoVenditaSSN() {
		return this.dataResoVenditaSSN;
	}

	public void setDataResoVenditaSSN(Date dataResoVenditaSSN) {
		this.dataResoVenditaSSN = dataResoVenditaSSN;
	}

	public String getEsenzione() {
		return this.esenzione;
	}

	public void setEsenzione(String esenzione) {
		this.esenzione = esenzione;
	}

	public Integer getNumreg() {
		return this.numreg;
	}

	public void setNumreg(Integer numreg) {
		this.numreg = numreg;
	}

	public BigDecimal getQuotaAssistito() {
		return this.quotaAssistito;
	}

	public void setQuotaAssistito(BigDecimal quotaAssistito) {
		this.quotaAssistito = quotaAssistito;
	}

	public BigDecimal getQuotaRicetta() {
		return this.quotaRicetta;
	}

	public void setQuotaRicetta(BigDecimal quotaRicetta) {
		this.quotaRicetta = quotaRicetta;
	}

	public BigDecimal getTotaleIva() {
		return this.totaleIva;
	}

	public void setTotaleIva(BigDecimal totaleIva) {
		this.totaleIva = totaleIva;
	}

	public Integer getTotalePezziResi() {
		return this.totalePezziResi;
	}

	public void setTotalePezziResi(Integer totalePezziResi) {
		this.totalePezziResi = totalePezziResi;
	}

	public BigDecimal getTotaleRicetta() {
		return this.totaleRicetta;
	}

	public void setTotaleRicetta(BigDecimal totaleRicetta) {
		this.totaleRicetta = totaleRicetta;
	}

	public BigDecimal getTotaleScontoSSN() {
		return this.totaleScontoSSN;
	}

	public void setTotaleScontoSSN(BigDecimal totaleScontoSSN) {
		this.totaleScontoSSN = totaleScontoSSN;
	}

	public BigDecimal getValoreVenditaSSN() {
		return this.valoreVenditaSSN;
	}

	public void setValoreVenditaSSN(BigDecimal valoreVenditaSSN) {
		this.valoreVenditaSSN = valoreVenditaSSN;
	}

	public ResiVendite getResiVendite() {
		return this.resiVendite;
	}

	public void setResiVendite(ResiVendite resiVendite) {
		this.resiVendite = resiVendite;
	}

	public List<ResiProdottiVenditaSSN> getResiProdottiVenditaSsns() {
		return this.resiProdottiVenditaSsns;
	}

	public void setResiProdottiVenditaSsns(List<ResiProdottiVenditaSSN> resiProdottiVenditaSsns) {
		this.resiProdottiVenditaSsns = resiProdottiVenditaSsns;
	}

	public ResiProdottiVenditaSSN addResiProdottiVenditaSsn(ResiProdottiVenditaSSN resiProdottiVenditaSsn) {
		getResiProdottiVenditaSsns().add(resiProdottiVenditaSsn);
		resiProdottiVenditaSsn.setResiVenditeSsn(this);

		return resiProdottiVenditaSsn;
	}

	public ResiProdottiVenditaSSN removeResiProdottiVenditaSsn(ResiProdottiVenditaSSN resiProdottiVenditaSsn) {
		getResiProdottiVenditaSsns().remove(resiProdottiVenditaSsn);
		resiProdottiVenditaSsn.setResiVenditeSsn(null);

		return resiProdottiVenditaSsn;
	}

}
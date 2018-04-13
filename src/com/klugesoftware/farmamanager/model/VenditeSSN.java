package com.klugesoftware.farmamanager.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


/**
 * The class for the VenditeSSN database table.
 * 
 */
public class VenditeSSN implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idVenditaSSN;
	
	private Integer numreg;

	private Date dataVenditaSSN;
	
	private String codiceFiscale;

	private String esenzione;

	private Vendite vendita;
	
	private Collection<ProdottiVenditaSSN> prodottiSsnVenduti;

	private Integer posizioneInVendita;
	
	private BigDecimal totaleIva;
	
	private Integer totalePezziVenduti;
	
	private BigDecimal quotaAssistito;

	private BigDecimal quotaRicetta;

	private BigDecimal totaleRicetta;

	private BigDecimal totaleScontoSSN;

	private BigDecimal valoreVenditaSSN;

	public VenditeSSN() {
		 prodottiSsnVenduti = new ArrayList<ProdottiVenditaSSN>();
		 totaleIva = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		 quotaAssistito = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		 quotaRicetta = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		 totaleRicetta = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		 totaleScontoSSN = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		 valoreVenditaSSN = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		 totalePezziVenduti = new Integer(0);
	}
	
	public VenditeSSN(
			Integer idVenditaSSN,
			Integer numreg,
			Date dataVenditaSSN,			
			String codiceFiscale,
			String esenzione,
			Integer posizioneInVendita,			
			BigDecimal totaleIva,
			Integer totalePezziVenduti,
			BigDecimal quotaAssistito,
			BigDecimal quotaRicetta,
			BigDecimal totaleRicetta,
			BigDecimal totaleScontoSSN,
			BigDecimal valoreVenditaSSN
			){
		this.idVenditaSSN = idVenditaSSN;
		this.numreg = numreg;
		this.dataVenditaSSN = dataVenditaSSN;			
		this.codiceFiscale = codiceFiscale;
		this.esenzione = esenzione;
		this.posizioneInVendita = posizioneInVendita;			
		this.totaleIva = totaleIva;
		this.totalePezziVenduti = totalePezziVenduti;
		this.quotaAssistito = quotaAssistito;
		this.quotaRicetta = quotaRicetta;
		this.totaleRicetta = totaleRicetta;
		this.totaleScontoSSN = totaleScontoSSN;
		this.valoreVenditaSSN = valoreVenditaSSN;
	}

	public Integer getIdVenditaSSN() {
		return this.idVenditaSSN;
	}

	public void setIdVenditaSSN(Integer idVenditaSSN) {
		this.idVenditaSSN = idVenditaSSN;
	}

	public Integer getNumreg() {
		return numreg;
	}

	public void setNumreg(Integer numreg) {
		this.numreg = numreg;
	}

	public Date getDataVenditaSSN() {
		return this.dataVenditaSSN;
	}

	public void setDataVenditaSSN(Date dataVenditaSSN) {
		this.dataVenditaSSN = dataVenditaSSN;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getEsenzione() {
		return this.esenzione;
	}

	public void setEsenzione(String esenzione) {
		this.esenzione = esenzione;
	}

	

	public BigDecimal getQuotaAssistito() {
		return quotaAssistito;
	}

	public void setQuotaAssistito(BigDecimal quotaAssistito) {
		this.quotaAssistito = quotaAssistito;
	}

	public BigDecimal getTotaleIva() {
		return this.totaleIva;
	}

	public Integer getTotalePezziVenduti() {
		return totalePezziVenduti;
	}

	public void setTotalePezziVenduti(Integer totalePezziVenduti) {
		this.totalePezziVenduti = totalePezziVenduti;
	}

	public void setTotaleIva(BigDecimal totaleIva) {
		this.totaleIva = totaleIva;
	}

	public Vendite getVendita() {
		return vendita;
	}

	public void setVendita(Vendite vendita) {
		this.vendita = vendita;
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

	public Collection<ProdottiVenditaSSN> getProdottiSsnVenduti() {
		return prodottiSsnVenduti;
	}

	public void setProdottiSsnVenduti(
			Collection<ProdottiVenditaSSN> prodottiSsnVenduti) {
		this.prodottiSsnVenduti = prodottiSsnVenduti;
	}
	
	public void addProdottoSsnVenduto(ProdottiVenditaSSN prodotto){
		prodottiSsnVenduti.add(prodotto);
	}

	public Integer getPosizioneInVendita() {
		return posizioneInVendita;
	}

	public void setPosizioneInVendita(Integer posizioneInVendita) {
		this.posizioneInVendita = posizioneInVendita;
	}

	public BigDecimal getQuotaRicetta() {
		return quotaRicetta;
	}

	public void setQuotaRicetta(BigDecimal quotaRicetta) {
		this.quotaRicetta = quotaRicetta;
	}

	public BigDecimal getValoreVenditaSSN() {
		return valoreVenditaSSN;
	}

	public void setValoreVenditaSSN(BigDecimal valoreVenditaSSN) {
		this.valoreVenditaSSN = valoreVenditaSSN;
	}

}
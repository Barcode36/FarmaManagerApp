package com.klugesoftware.farmamanager.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


/**
 * The class for the VenditeLibere database table.
 * 
 */
public class VenditeLibere implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idVenditaLibera;
	
	private Integer numreg;

	private String codiceFiscale;

	private Date dataVendita;

	private Vendite vendita;

	private Collection<ProdottiVenditaLibera> prodottiVenduti;
	
	private Integer posizioneInVendita;

	private BigDecimal totaleIva;
	
	private Integer totalePezziVenduti;

	private BigDecimal totaleScontoProdotto;

	private BigDecimal valoreVenditaLibera;

	private BigDecimal totaleVenditaScontata;

	public VenditeLibere() {
		prodottiVenduti = new ArrayList<ProdottiVenditaLibera>();
		totaleIva = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleScontoProdotto = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleVenditaScontata = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		valoreVenditaLibera = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totalePezziVenduti = new Integer(0);
	}
	
	public VenditeLibere(
		Integer idVenditaLibera,
		Integer numreg,
		String codiceFiscale,
		Date dataVendita,
		Integer posizioneInVendita,
		BigDecimal totaleIva,
		Integer totalePezziVenduti,
		BigDecimal totaleScontoProdotto,
		BigDecimal valoreVenditaLibera,
		BigDecimal totaleVenditaScontata
										){
		this.idVenditaLibera = idVenditaLibera;
		this.numreg = numreg;
		this.codiceFiscale = codiceFiscale;
		this.dataVendita = dataVendita;
		this.posizioneInVendita = posizioneInVendita;
		this.totaleIva = totaleIva;
		this.totalePezziVenduti = totalePezziVenduti;
		this.totaleScontoProdotto = totaleScontoProdotto;
		this.valoreVenditaLibera = valoreVenditaLibera;
		this.totaleVenditaScontata = totaleVenditaScontata;
	}

	public Integer getIdVenditaLibera() {
		return this.idVenditaLibera;
	}

	public void setIdVenditaLibera(Integer idVenditaLibera) {
		this.idVenditaLibera = idVenditaLibera;
	}

	public Integer getNumreg() {
		return numreg;
	}

	public void setNumreg(Integer numreg) {
		this.numreg = numreg;
	}

	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public Date getDataVendita() {
		return this.dataVendita;
	}

	public void setDataVendita(Date dataVendita) {
		this.dataVendita = dataVendita;
	}

	

	public Vendite getVendita() {
		return vendita;
	}

	public void setVendita(Vendite vendita) {
		this.vendita = vendita;
	}

	public BigDecimal getTotaleIva() {
		return this.totaleIva;
	}

	public void setTotaleIva(BigDecimal totaleIva) {
		this.totaleIva = totaleIva;
	}

	public Integer getTotalePezziVenduti() {
		return totalePezziVenduti;
	}

	public void setTotalePezziVenduti(Integer totalePezziVenduti) {
		this.totalePezziVenduti = totalePezziVenduti;
	}

	public BigDecimal getTotaleScontoProdotto() {
		return this.totaleScontoProdotto;
	}

	public void setTotaleScontoProdotto(BigDecimal totaleScontoProdotto) {
		this.totaleScontoProdotto = totaleScontoProdotto;
	}

	public BigDecimal getTotaleVenditaScontata() {
		return this.totaleVenditaScontata;
	}

	public void setTotaleVenditaScontata(BigDecimal totaleVenditaScontata) {
		this.totaleVenditaScontata = totaleVenditaScontata;
	}

	public Collection<ProdottiVenditaLibera> getProdottiVenduti() {
		return prodottiVenduti;
	}

	public void setProdottiVenduti(Collection<ProdottiVenditaLibera> prodottiVenduti) {
		this.prodottiVenduti = prodottiVenduti;
	}
	
	public void addProdottoVenduto(ProdottiVenditaLibera prodotto){
		prodottiVenduti.add(prodotto);
	}

	public Integer getPosizioneInVendita() {
		return posizioneInVendita;
	}

	public void setPosizioneInVendita(Integer posizioneInVendita) {
		this.posizioneInVendita = posizioneInVendita;
	}

	public BigDecimal getValoreVenditaLibera() {
		return valoreVenditaLibera;
	}

	public void setValoreVenditaLibera(BigDecimal valoreVenditaLibera) {
		this.valoreVenditaLibera = valoreVenditaLibera;
	}

}
package com.klugesoftware.farmamanager.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The class for the ResiVenditeLibere database table.
 * 
 */
public class ResiVenditeLibere implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idResoVenditaLibera;

	/**
	 * campo RIC della tabella DBF
	 */
	private String campoRicReso;
	
	private String codiceFiscale;

	private Date dataResoVendita;

	private Integer numreg;

	private BigDecimal totaleIva;

	private Integer totalePezziResi;

	private BigDecimal totaleScontoProdotto;

	private BigDecimal totaleVenditaScontata;

	private BigDecimal valoreVenditaLibera;

	private ResiVendite resiVendite;

	private List<ResiProdottiVenditaLibera> resiProdottiVenditaLiberas;

	public ResiVenditeLibere() {
		campoRicReso = "00";
		totaleIva = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleScontoProdotto = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleVenditaScontata = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		valoreVenditaLibera = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		resiProdottiVenditaLiberas  = new ArrayList<ResiProdottiVenditaLibera>();
		totalePezziResi = new Integer(0);
		numreg = new Integer(0);
	}
	
	public ResiVenditeLibere(
			Integer idResoVenditaLibera,
			String codiceFiscale,
			Date dataResoVendita,
			Integer numreg,
			BigDecimal totaleIva,
			Integer totalePezziResi,
			BigDecimal totaleScontoProdotto,
			BigDecimal totaleVenditaScontata,
			BigDecimal valoreVenditaLibera,
			ResiVendite resiVendite
			) {
		this.idResoVenditaLibera = idResoVenditaLibera;
		this.campoRicReso = "00";
		this.codiceFiscale = codiceFiscale;
		this.dataResoVendita = dataResoVendita;
		this.numreg = numreg;
		this.totaleIva  = totaleIva;
		this.totalePezziResi = totalePezziResi;
		this.totaleScontoProdotto = totaleScontoProdotto;
		this.totaleVenditaScontata = totaleVenditaScontata;
		this.valoreVenditaLibera = valoreVenditaLibera;
		this.resiVendite = resiVendite;
	}

	public Integer getIdResoVenditaLibera() {
		return this.idResoVenditaLibera;
	}

	public void setIdResoVenditaLibera(Integer idResoVenditaLibera) {
		this.idResoVenditaLibera = idResoVenditaLibera;
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

	public Date getDataResoVendita() {
		return this.dataResoVendita;
	}

	public void setDataResoVendita(Date dataResoVendita) {
		this.dataResoVendita = dataResoVendita;
	}

	public Integer getNumreg() {
		return this.numreg;
	}

	public void setNumreg(Integer numreg) {
		this.numreg = numreg;
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

	public BigDecimal getValoreVenditaLibera() {
		return this.valoreVenditaLibera;
	}

	public void setValoreVenditaLibera(BigDecimal valoreVenditaLibera) {
		this.valoreVenditaLibera = valoreVenditaLibera;
	}

	public ResiVendite getResiVendite() {
		return this.resiVendite;
	}

	public void setResiVendite(ResiVendite resiVendite) {
		this.resiVendite = resiVendite;
	}

	public List<ResiProdottiVenditaLibera> getResiProdottiVenditaLiberas() {
		return this.resiProdottiVenditaLiberas;
	}

	public void setResiProdottiVenditaLiberas(List<ResiProdottiVenditaLibera> resiProdottiVenditaLiberas) {
		this.resiProdottiVenditaLiberas = resiProdottiVenditaLiberas;
	}

	public ResiProdottiVenditaLibera addResiProdottiVenditaLibera(ResiProdottiVenditaLibera resiProdottiVenditaLibera) {
		getResiProdottiVenditaLiberas().add(resiProdottiVenditaLibera);
		resiProdottiVenditaLibera.setResiVenditeLibere(this);

		return resiProdottiVenditaLibera;
	}

	public ResiProdottiVenditaLibera removeResiProdottiVenditaLibera(ResiProdottiVenditaLibera resiProdottiVenditaLibera) {
		getResiProdottiVenditaLiberas().remove(resiProdottiVenditaLibera);
		resiProdottiVenditaLibera.setResiVenditeLibere(null);

		return resiProdottiVenditaLibera;
	}

}
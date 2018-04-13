package com.klugesoftware.farmamanager.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The  class for the ResiProdottiVenditaLibera database table.
 * 
 */
public class ResiProdottiVenditaLibera implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idResoProdottoVenditaLibera;

	private Integer aliquotaIva;

	private BigDecimal costoCompresoIva;

	private BigDecimal costoNettoIva;

	private String descrizione;

	private String minsan;

	private Integer numreg;

	private BigDecimal prezzoPraticato;

	private BigDecimal prezzoVendita;

	private BigDecimal prezzoVenditaNetto;

	private BigDecimal profittoUnitario;

	private Integer quantita;

	private BigDecimal scontoPayBack;

	private BigDecimal scontoProdotti;

	private BigDecimal totaleScontoUnitario;

	private ResiVenditeLibere resiVenditeLibere;
	
	private Date dataReso;

	public ResiProdottiVenditaLibera() {
		costoCompresoIva = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		costoNettoIva = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		prezzoPraticato = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		prezzoVendita = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		prezzoVenditaNetto = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		profittoUnitario = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		scontoPayBack = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		scontoProdotti = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleScontoUnitario = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		quantita = new Integer(0);
		numreg = new Integer(0);
	}
	
	public ResiProdottiVenditaLibera(	
			Integer idResoProdottoVenditaLibera,
			Integer aliquotaIva,
			BigDecimal costoCompresoIva,
			BigDecimal costoNettoIva,
			String descrizione,
			String minsan,
			Integer numreg,
			BigDecimal prezzoPraticato,
			BigDecimal prezzoVendita,
			BigDecimal prezzoVenditaNetto,
			BigDecimal profittoUnitario,
			Integer quantita,
			BigDecimal scontoPayBack,
			BigDecimal scontoProdotti,
			BigDecimal totaleScontoUnitario,
			ResiVenditeLibere resoVenditaLibera,
			Date dataReso
			) {
		this.idResoProdottoVenditaLibera = idResoProdottoVenditaLibera; 
		this.aliquotaIva = aliquotaIva;
		this.costoCompresoIva = costoCompresoIva;
		this.costoNettoIva = costoNettoIva; 
		this.descrizione = descrizione;
		this.minsan = minsan; 
		this.numreg = numreg;
		this.prezzoPraticato = prezzoPraticato;
		this.prezzoVendita = prezzoVendita;
		this.prezzoVenditaNetto = prezzoVenditaNetto;
		this.profittoUnitario = profittoUnitario;
		this.quantita = quantita;
		this.scontoPayBack = scontoPayBack;
		this.scontoProdotti = scontoProdotti;
		this.totaleScontoUnitario = totaleScontoUnitario;
		this.resiVenditeLibere = resoVenditaLibera;
		this.dataReso = dataReso;
	}

	public Integer getIdResoProdottoVenditaLibera() {
		return this.idResoProdottoVenditaLibera;
	}

	public void setIdResoProdottoVenditaLibera(Integer idResoProdottoVenditaLibera) {
		this.idResoProdottoVenditaLibera = idResoProdottoVenditaLibera;
	}

	public Integer getAliquotaIva() {
		return this.aliquotaIva;
	}

	public void setAliquotaIva(Integer aliquotaIva) {
		this.aliquotaIva = aliquotaIva;
	}

	public BigDecimal getCostoCompresoIva() {
		return this.costoCompresoIva;
	}

	public void setCostoCompresoIva(BigDecimal costoCompresoIva) {
		this.costoCompresoIva = costoCompresoIva;
	}

	public BigDecimal getCostoNettoIva() {
		return this.costoNettoIva;
	}

	public void setCostoNettoIva(BigDecimal costoNettoIva) {
		this.costoNettoIva = costoNettoIva;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getMinsan() {
		return this.minsan;
	}

	public void setMinsan(String minsan) {
		this.minsan = minsan;
	}

	public Integer getNumreg() {
		return this.numreg;
	}

	public void setNumreg(Integer numreg) {
		this.numreg = numreg;
	}

	public BigDecimal getPrezzoPraticato() {
		return this.prezzoPraticato;
	}

	public void setPrezzoPraticato(BigDecimal prezzoPraticato) {
		this.prezzoPraticato = prezzoPraticato;
	}

	public BigDecimal getPrezzoVendita() {
		return this.prezzoVendita;
	}

	public void setPrezzoVendita(BigDecimal prezzoVendita) {
		this.prezzoVendita = prezzoVendita;
	}

	public BigDecimal getPrezzoVenditaNetto() {
		return this.prezzoVenditaNetto;
	}

	public void setPrezzoVenditaNetto(BigDecimal prezzoVenditaNetto) {
		this.prezzoVenditaNetto = prezzoVenditaNetto;
	}

	public BigDecimal getProfittoUnitario() {
		return this.profittoUnitario;
	}

	public void setProfittoUnitario(BigDecimal profittoUnitario) {
		this.profittoUnitario = profittoUnitario;
	}

	public Integer getQuantita() {
		return this.quantita;
	}

	public void setQuantita(Integer quantita) {
		this.quantita = quantita;
	}

	public BigDecimal getScontoPayBack() {
		return this.scontoPayBack;
	}

	public void setScontoPayBack(BigDecimal scontoPayBack) {
		this.scontoPayBack = scontoPayBack;
	}

	public BigDecimal getScontoProdotti() {
		return this.scontoProdotti;
	}

	public void setScontoProdotti(BigDecimal scontoProdotti) {
		this.scontoProdotti = scontoProdotti;
	}

	public BigDecimal getTotaleScontoUnitario() {
		return this.totaleScontoUnitario;
	}

	public void setTotaleScontoUnitario(BigDecimal totaleScontoUnitario) {
		this.totaleScontoUnitario = totaleScontoUnitario;
	}

	public ResiVenditeLibere getResiVenditeLibere() {
		return this.resiVenditeLibere;
	}

	public void setResiVenditeLibere(ResiVenditeLibere resiVenditeLibere) {
		this.resiVenditeLibere = resiVenditeLibere;
	}

	public Date getDataReso() {
		return dataReso;
	}

	public void setDataReso(Date dataReso) {
		this.dataReso = dataReso;
	}

}
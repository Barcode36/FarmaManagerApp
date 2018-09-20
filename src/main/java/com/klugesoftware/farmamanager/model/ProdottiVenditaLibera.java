
package com.klugesoftware.farmamanager.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The  class for the ProdottiVenditaLibera database table.
 * 
 */
public class ProdottiVenditaLibera implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idProdottoVenditaLibera;
	
	private int numreg;

	private Integer aliquotaIva;

	private BigDecimal costoCompresoIva;
	
	private BigDecimal costoNettoIva;
	
	private TipoCosto tipoCosto;

	private String deGrassi;

	private String descrizione;

	private VenditeLibere venditaLibera;

	private Integer posizioneInVendita;
	
	private String minsan;

	private BigDecimal prezzoVendita;
	
	private BigDecimal prezzoPraticato;

	private Integer quantita;

	private BigDecimal scontoProdotti;
	
	private BigDecimal scontoPayBack;
	
	private BigDecimal scontoVenditaRipartito;
	
	private BigDecimal totaleScontoUnitario;
	
	private BigDecimal prezzoVenditaNetto;
	
	private BigDecimal profittoUnitario;
	
	private BigDecimal percentualeMargineUnitario;
	
	private BigDecimal percentualeRicaricoUnitario;

	private String tipoProdotto;

	private String tipoRicetta;
	
	private Date dataVendita;
	
	private Integer quantitaReso;

	public ProdottiVenditaLibera() {
		costoCompresoIva = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		costoNettoIva = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		prezzoVendita = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		prezzoPraticato = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		scontoProdotti = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleScontoUnitario = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		scontoPayBack = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		scontoVenditaRipartito = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		prezzoVenditaNetto = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		profittoUnitario = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		percentualeMargineUnitario = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		percentualeRicaricoUnitario = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		quantita = new Integer(0);
		quantitaReso = new Integer(0);
	}

	public ProdottiVenditaLibera(
									Integer idProdottoVenditaLibera,
									int numreg,
									Integer aliquotaIva,
									BigDecimal costoCompresivoIva,
									BigDecimal costoNettoIva,
									TipoCosto tipoCosto,
									String deGrassi,
									String descrizione,
									Integer posizioneVendita,
									String minsan,
									BigDecimal prezzoVendita,
									BigDecimal prezzoPraticato,
									Integer quantita,
									BigDecimal scontoProdotti,
									BigDecimal scontoPayBack,
									BigDecimal scontoVenditaRipartito,
									BigDecimal totaleScontoUnitario,
									BigDecimal prezzoVenditaNetto,
									BigDecimal profittoUnitario,
									BigDecimal percentualeMargineUnitario,
									BigDecimal percentualeRicaricoUnitario,
									String tipoProdotto,
									String tipoRicetta,
									Date dataVendita,
									Integer quantitaReso,
									VenditeLibere venditaLibera){
		this.idProdottoVenditaLibera = idProdottoVenditaLibera;
		this.numreg = numreg;
		this.aliquotaIva = aliquotaIva;
		this.costoCompresoIva = costoCompresivoIva;
		this.costoNettoIva = costoNettoIva;
		this.tipoCosto = tipoCosto;
		this.deGrassi = deGrassi;
		this.descrizione = descrizione;
		this.posizioneInVendita = posizioneVendita;
		this.minsan = minsan;
		this.prezzoVendita = prezzoVendita;
		this.prezzoPraticato = prezzoPraticato;
		this.quantita = quantita;
		this.scontoProdotti = scontoProdotti;
		this.scontoPayBack = scontoPayBack;
		this.scontoVenditaRipartito = scontoVenditaRipartito;
		this.totaleScontoUnitario = totaleScontoUnitario;
		this.prezzoVenditaNetto = prezzoVenditaNetto;
		this.profittoUnitario = profittoUnitario;
		this.percentualeMargineUnitario = percentualeMargineUnitario;
		this.percentualeRicaricoUnitario = percentualeRicaricoUnitario;
		this.tipoProdotto = tipoProdotto;
		this.tipoRicetta = tipoRicetta;
		this.dataVendita = dataVendita;
		this.quantitaReso = quantitaReso;
		this.venditaLibera = venditaLibera;
	}
	
	public Integer getIdProdottoVenditaLibera() {
		return this.idProdottoVenditaLibera;
	}

	public void setIdProdottoVenditaLibera(Integer idProdottoVenditaLibera) {
		this.idProdottoVenditaLibera = idProdottoVenditaLibera;
	}

	public Integer getNumreg() {
		return numreg;
	}

	public void setNumreg(Integer numreg) {
		this.numreg = numreg;
	}

	public Integer getAliquotaIva() {
		return this.aliquotaIva;
	}

	public void setAliquotaIva(Integer aliquotaIva) {
		this.aliquotaIva = aliquotaIva;
	}

	public BigDecimal getCostoNettoIva() {
		return costoNettoIva;
	}

	public void setCostoNettoIva(BigDecimal costoNettoIva) {
		this.costoNettoIva = costoNettoIva;
	}

	public BigDecimal getCostoCompresoIva() {
		return costoCompresoIva;
	}

	public void setCostoCompresoIva(BigDecimal costoCompresoIva) {
		this.costoCompresoIva = costoCompresoIva;
	}

	public TipoCosto getTipoCosto() {
		return tipoCosto;
	}

	public void setTipoCosto(TipoCosto tipoCosto) {
		this.tipoCosto = tipoCosto;
	}

	public String getDeGrassi() {
		return this.deGrassi;
	}

	public void setDeGrassi(String deGrassi) {
		this.deGrassi = deGrassi;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public VenditeLibere getVenditaLibera() {
		return venditaLibera;
	}

	public void setVenditaLibera(VenditeLibere venditaLibera) {
		this.venditaLibera = venditaLibera;
	}

	public String getMinsan() {
		return this.minsan;
	}

	public void setMinsan(String minsan) {
		this.minsan = minsan;
	}

	public BigDecimal getPrezzoVendita() {
		return this.prezzoVendita;
	}

	public void setPrezzoVendita(BigDecimal prezzoVendita) {
		this.prezzoVendita = prezzoVendita;
	}

	public BigDecimal getPrezzoPraticato() {
		return prezzoPraticato;
	}

	public void setPrezzoPraticato(BigDecimal prezzoPraticato) {
		this.prezzoPraticato = prezzoPraticato;
	}

	public Integer getQuantita() {
		return this.quantita;
	}

	public void setQuantita(Integer quantita) {
		this.quantita = quantita;
	}

	public BigDecimal getScontoProdotti() {
		return scontoProdotti;
	}

	public void setScontoProdotti(BigDecimal scontoProdotti) {
		this.scontoProdotti = scontoProdotti;
	}

	public BigDecimal getTotaleScontoUnitario() {
		return totaleScontoUnitario;
	}

	public void setTotaleScontoUnitario(BigDecimal totaleScontoUnitario) {
		this.totaleScontoUnitario = totaleScontoUnitario;
	}

	public BigDecimal getScontoPayBack() {
		return scontoPayBack;
	}

	public void setScontoPayBack(BigDecimal scontoPayBack) {
		this.scontoPayBack = scontoPayBack;
	}

	public BigDecimal getScontoVenditaRipartito() {
		return scontoVenditaRipartito;
	}

	public void setScontoVenditaRipartito(BigDecimal scontoVenditaRipartito) {
		this.scontoVenditaRipartito = scontoVenditaRipartito;
	}

	public BigDecimal getPrezzoVenditaNetto() {
		return prezzoVenditaNetto;
	}

	public void setPrezzoVenditaNetto(BigDecimal prezzoVenditaNetto) {
		this.prezzoVenditaNetto = prezzoVenditaNetto;
	}

	public BigDecimal getProfittoUnitario() {
		return profittoUnitario;
	}

	public void setProfittoUnitario(BigDecimal profittoUnitario) {
		this.profittoUnitario = profittoUnitario;
	}

	public BigDecimal getPercentualeMargineUnitario() {
		return percentualeMargineUnitario;
	}

	public void setPercentualeMargineUnitario(BigDecimal percentualeMargineUnitario) {
		this.percentualeMargineUnitario = percentualeMargineUnitario;
	}

	public BigDecimal getPercentualeRicaricoUnitario() {
		return percentualeRicaricoUnitario;
	}

	public void setPercentualeRicaricoUnitario(
			BigDecimal percentualeRicaricoUnitario) {
		this.percentualeRicaricoUnitario = percentualeRicaricoUnitario;
	}

	public String getTipoProdotto() {
		return this.tipoProdotto;
	}

	public void setTipoProdotto(String tipoProdotto) {
		this.tipoProdotto = tipoProdotto;
	}

	public String getTipoRicetta() {
		return this.tipoRicetta;
	}

	public void setTipoRicetta(String tipoRicetta) {
		this.tipoRicetta = tipoRicetta;
	}

	public Integer getPosizioneInVendita() {
		return posizioneInVendita;
	}

	public void setPosizioneInVendita(Integer posizioneInVendita) {
		this.posizioneInVendita = posizioneInVendita;
	}

	public Date getDataVendita() {
		return dataVendita;
	}

	public void setDataVendita(Date dataVendita) {
		this.dataVendita = dataVendita;
	}

	public Integer getQuantitaReso() {
		return quantitaReso;
	}

	public void setQuantitaReso(Integer quantitaReso) {
		this.quantitaReso = quantitaReso;
	}

}
package com.klugesoftware.farmamanager.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The class for the ResiProdottiVenditaSSN database table.
 * 
 */
public class ResiProdottiVenditaSSN implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idResoProdottoVenditaSSN;

	private Integer aliquotaIva;

	private BigDecimal costoCompresoIva;

	private BigDecimal costoNettoIva;

	private String descrizione;

	private String minsan;

	private Integer numreg;

	private BigDecimal percentualeScontoSSN;

	private BigDecimal prezzoFustello;

	private BigDecimal prezzoPraticato;

	private BigDecimal prezzoRimborso;

	private BigDecimal prezzoVenditaNetto;

	private BigDecimal profittoUnitario;

	private Integer quantita;

	private BigDecimal quotaAssistito;

	private BigDecimal quotaPezzo;

	private BigDecimal totaleScontoUnitario;

	private BigDecimal valoreScontoAifa;

	private BigDecimal valoreScontoSSN;

	private BigDecimal valoreScontoSSNExtra;

	private ResiVenditeSSN resiVenditeSsn;
	
	private Date dataReso;

	public ResiProdottiVenditaSSN() {
		costoCompresoIva = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		costoNettoIva = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		percentualeScontoSSN = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		prezzoFustello = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		prezzoPraticato = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		prezzoRimborso = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		prezzoVenditaNetto = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		profittoUnitario = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		quotaAssistito = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		quotaPezzo = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleScontoUnitario = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		valoreScontoAifa = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		valoreScontoSSN = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		valoreScontoSSNExtra = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		quantita = new Integer(0);
		numreg = new Integer(0);
	}

	public ResiProdottiVenditaSSN(
			Integer idResoProdottoVenditaSSN,
			Integer aliquotaIva,
			BigDecimal costoCompresoIva,
			BigDecimal costoNettoIva,
			String descrizione,
			String minsan,
			Integer numreg,
			BigDecimal percentualeScontoSSN,
			BigDecimal prezzoFustello,
			BigDecimal prezzoPraticato,
			BigDecimal prezzoRimborso,
			BigDecimal prezzoVenditaNetto,
			BigDecimal profittoUnitario,
			Integer quantita,
			BigDecimal quotaAssistito,
			BigDecimal quotaPezzo,
			BigDecimal totaleScontoUnitario,
			BigDecimal valoreScontoAifa,
			BigDecimal valoreScontoSSN,
			BigDecimal valoreScontoSSNExtra,
			ResiVenditeSSN resoVenditeSsn,
			Date dataReso
			) {
		this.idResoProdottoVenditaSSN = idResoProdottoVenditaSSN;
		this.aliquotaIva = aliquotaIva; 
		this.costoCompresoIva = costoCompresoIva;
		this.costoNettoIva = costoNettoIva;
		this.descrizione = descrizione;
		this.minsan = minsan;
		this.numreg = numreg;
		this.percentualeScontoSSN = percentualeScontoSSN;
		this.prezzoFustello = prezzoFustello; 
		this.prezzoPraticato = prezzoPraticato;
		this.prezzoRimborso = prezzoRimborso; 
		this.prezzoVenditaNetto = prezzoVenditaNetto;
		this.profittoUnitario = profittoUnitario;
		this.quantita = quantita;
		this.quotaAssistito = quotaAssistito;
		this.quotaPezzo = quotaPezzo;
		this.totaleScontoUnitario = totaleScontoUnitario;
		this.valoreScontoAifa = valoreScontoAifa;
		this.valoreScontoSSN = valoreScontoSSN;
		this.valoreScontoSSNExtra = valoreScontoSSNExtra;
		this.resiVenditeSsn = resoVenditeSsn;
		this.dataReso = dataReso;
	}
	public Integer getIdResoProdottoVenditaSSN() {
		return this.idResoProdottoVenditaSSN;
	}

	public void setIdResoProdottoVenditaSSN(Integer idResoProdottoVenditaSSN) {
		this.idResoProdottoVenditaSSN = idResoProdottoVenditaSSN;
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

	public BigDecimal getPercentualeScontoSSN() {
		return this.percentualeScontoSSN;
	}

	public void setPercentualeScontoSSN(BigDecimal percentualeScontoSSN) {
		this.percentualeScontoSSN = percentualeScontoSSN;
	}

	public BigDecimal getPrezzoFustello() {
		return this.prezzoFustello;
	}

	public void setPrezzoFustello(BigDecimal prezzoFustello) {
		this.prezzoFustello = prezzoFustello;
	}

	public BigDecimal getPrezzoPraticato() {
		return this.prezzoPraticato;
	}

	public void setPrezzoPraticato(BigDecimal prezzoPraticato) {
		this.prezzoPraticato = prezzoPraticato;
	}

	public BigDecimal getPrezzoRimborso() {
		return this.prezzoRimborso;
	}

	public void setPrezzoRimborso(BigDecimal prezzoRimborso) {
		this.prezzoRimborso = prezzoRimborso;
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

	public BigDecimal getQuotaAssistito() {
		return this.quotaAssistito;
	}

	public void setQuotaAssistito(BigDecimal quotaAssistito) {
		this.quotaAssistito = quotaAssistito;
	}

	public BigDecimal getQuotaPezzo() {
		return this.quotaPezzo;
	}

	public void setQuotaPezzo(BigDecimal quotaPezzo) {
		this.quotaPezzo = quotaPezzo;
	}

	public BigDecimal getTotaleScontoUnitario() {
		return this.totaleScontoUnitario;
	}

	public void setTotaleScontoUnitario(BigDecimal totaleScontoUnitario) {
		this.totaleScontoUnitario = totaleScontoUnitario;
	}

	public BigDecimal getValoreScontoAifa() {
		return this.valoreScontoAifa;
	}

	public void setValoreScontoAifa(BigDecimal valoreScontoAifa) {
		this.valoreScontoAifa = valoreScontoAifa;
	}

	public BigDecimal getValoreScontoSSN() {
		return this.valoreScontoSSN;
	}

	public void setValoreScontoSSN(BigDecimal valoreScontoSSN) {
		this.valoreScontoSSN = valoreScontoSSN;
	}

	public BigDecimal getValoreScontoSSNExtra() {
		return this.valoreScontoSSNExtra;
	}

	public void setValoreScontoSSNExtra(BigDecimal valoreScontoSSNExtra) {
		this.valoreScontoSSNExtra = valoreScontoSSNExtra;
	}

	public ResiVenditeSSN getResiVenditeSsn() {
		return this.resiVenditeSsn;
	}

	public void setResiVenditeSsn(ResiVenditeSSN resiVenditeSsn) {
		this.resiVenditeSsn = resiVenditeSsn;
	}

	public Date getDataReso() {
		return dataReso;
	}

	public void setDataReso(Date dataReso) {
		this.dataReso = dataReso;
	}

}
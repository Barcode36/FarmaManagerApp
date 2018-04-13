package com.klugesoftware.farmamanager.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The class for the ProdottiVenditaSSN database table.
 * 
 */
public class ProdottiVenditaSSN implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idProdottoVenditaSSN;
	
	private Integer numreg;

	private Integer aliquotaIva;

	private String codiceAtcGmp;

	private BigDecimal costoCompresoIva;
	
	private BigDecimal costoNettoIva;
	
	private TipoCosto tipoCosto;

	private String deGrassi;

	private String descrizione;

	private VenditeSSN venditaSSN;

	private Integer posizioneInVendita;
	
	private String minsan;

	private BigDecimal percentualeScontoSSN;
	
	private BigDecimal valoreScontoSSNExtra;
	
	private BigDecimal valoreScontoAifa;

	private BigDecimal prezzoFustello;
	
	private BigDecimal prezzoPraticato;

	private BigDecimal prezzoRimborso;

	private Integer quantita;

	private BigDecimal quotaAssistito;

	private BigDecimal quotaPezzo;

	private BigDecimal valoreScontoSSN;
	
	private BigDecimal scontoVenditaRipartito;
	
	/**
	 * totaleScontoUnitario comprende la somma dello scontoSSN,scontoPayBack e scontoExtraSSN
	 */
	private BigDecimal totaleScontoUnitario;
	
	/**
	 * E' dato dal (Prezzo di Fustello decurtato degli scontiSSN) al netto dell'iva
	 */
	private BigDecimal prezzoVenditaNetto;
	
	/**
	 * Dato dal prezzoVenditaNetto - costoNettoIva
	 */
	private BigDecimal profittoUnitario;
	
	/**
	 * percentualeMargineUnitario = (profittoUnitario/prezzoVenditaNetto)*100
	 */
	private BigDecimal percentualeMargineUnitario;
	
	/**
	 * percentualeRicaricoUnitario = (profittoUnitario/costoNetto)*100 
	 */
	private BigDecimal percentualeRicaricoUnitario;

	private String tipoProdotto;

	private String tipoRicetta;
	
	private Date dataVendita;
	
	private Integer quantitaReso;

	public ProdottiVenditaSSN() {
		costoCompresoIva = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		costoNettoIva = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		percentualeScontoSSN = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		valoreScontoSSNExtra = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		valoreScontoAifa = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		prezzoFustello = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		prezzoRimborso = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		quotaAssistito = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		quotaPezzo = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		valoreScontoSSN = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		scontoVenditaRipartito = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleScontoUnitario = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		prezzoVenditaNetto = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		profittoUnitario = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		percentualeMargineUnitario = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		percentualeRicaricoUnitario = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		quantita = new Integer(0);
		quantitaReso = new Integer(0);
	}
	
	public ProdottiVenditaSSN(
			Integer idProdottoVenditaSSN,
			Integer numreg,
			Integer aliquotaIva,
			String codiceAtcGmp,
			BigDecimal costoCompresoIva,
			BigDecimal costoNettoIva,
			TipoCosto tipoCosto,
			String deGrassi,
			String descrizione,
			Integer posizioneInVendita,
			String minsan,
			BigDecimal percentualeScontoSSN,
			BigDecimal valoreScontoSSNExtra,
			BigDecimal valoreScontoAifa,
			BigDecimal prezzoFustello,			
			BigDecimal prezzoPraticato,
			BigDecimal prezzoRimborso,
			Integer quantita,
			BigDecimal quotaAssistito,
			BigDecimal quotaPezzo,
			BigDecimal valoreScontoSSN,
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
			VenditeSSN venditaSSN
			){
		this.idProdottoVenditaSSN = idProdottoVenditaSSN;
		this.numreg = numreg;
		this.aliquotaIva = aliquotaIva;
		this.codiceAtcGmp = codiceAtcGmp;
		this.costoCompresoIva = costoCompresoIva;
		this.costoNettoIva = costoNettoIva;
		this.tipoCosto = tipoCosto;
		this.deGrassi = deGrassi;
		this.descrizione = descrizione;
		this.posizioneInVendita = posizioneInVendita;
		this.minsan = minsan;
		this.percentualeScontoSSN = percentualeScontoSSN;
		this.valoreScontoSSNExtra = valoreScontoSSNExtra;
		this.valoreScontoAifa = valoreScontoAifa;
		this.prezzoFustello = prezzoFustello;			
		this.prezzoPraticato = prezzoPraticato;
		this.prezzoRimborso = prezzoRimborso;
		this.quantita = quantita;
		this.quotaAssistito = quotaAssistito;
		this.quotaPezzo = quotaPezzo;
		this.valoreScontoSSN = valoreScontoSSN;
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
		this.venditaSSN = venditaSSN;
	}

	public Integer getIdProdottoVenditaSSN() {
		return this.idProdottoVenditaSSN;
	}

	public void setIdProdottoVenditaSSN(Integer idProdottoVenditaSSN) {
		this.idProdottoVenditaSSN = idProdottoVenditaSSN;
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

	public String getCodiceAtcGmp() {
		return this.codiceAtcGmp;
	}

	public void setCodiceAtcGmp(String codiceAtcGmp) {
		this.codiceAtcGmp = codiceAtcGmp;
	}

	public BigDecimal getCostoCompresoIva() {
		return costoCompresoIva;
	}

	public void setCostoCompresoIva(BigDecimal costoCompresoIva) {
		this.costoCompresoIva = costoCompresoIva;
	}

	public BigDecimal getCostoNettoIva() {
		return costoNettoIva;
	}

	public void setCostoNettoIva(BigDecimal costoNettoIva) {
		this.costoNettoIva = costoNettoIva;
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

	public VenditeSSN getVenditaSSN() {
		return venditaSSN;
	}

	public void setVenditaSSN(VenditeSSN venditaSSN) {
		this.venditaSSN = venditaSSN;
	}

	public String getMinsan() {
		return this.minsan;
	}

	public void setMinsan(String minsan) {
		this.minsan = minsan;
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
		return prezzoPraticato;
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

	public BigDecimal getValoreScontoSSNExtra() {
		return valoreScontoSSNExtra;
	}

	public void setValoreScontoSSNExtra(BigDecimal valoreScontoSSNExtra) {
		this.valoreScontoSSNExtra = valoreScontoSSNExtra;
	}

	public BigDecimal getValoreScontoAifa() {
		return valoreScontoAifa;
	}

	public void setValoreScontoAifa(BigDecimal valoreScontoAifa) {
		this.valoreScontoAifa = valoreScontoAifa;
	}

	public BigDecimal getValoreScontoSSN() {
		return valoreScontoSSN;
	}

	public void setValoreScontoSSN(BigDecimal valoreScontoSSN) {
		this.valoreScontoSSN = valoreScontoSSN;
	}

	public BigDecimal getScontoVenditaRipartito() {
		return scontoVenditaRipartito;
	}

	public void setScontoVenditaRipartito(BigDecimal scontoVenditaRipartito) {
		this.scontoVenditaRipartito = scontoVenditaRipartito;
	}

	/**
	 * totaleScontoUnitario comprende la somma dello scontoSSN,scontoPayBack e scontoExtraSSN
	 */
	public BigDecimal getTotaleScontoUnitario() {
		return totaleScontoUnitario;
	}

	/**
	 * totaleScontoUnitario comprende la somma dello scontoSSN,scontoPayBack e scontoExtraSSN
	 */
	public void setTotaleScontoUnitario(BigDecimal totaleScontoUnitario) {
		this.totaleScontoUnitario = totaleScontoUnitario;
	}

	/**
	 * E' dato dal (Prezzo di Fustello decurtato degli scontiSSN) al netto dell'iva
	 */
	public BigDecimal getPrezzoVenditaNetto() {
		return prezzoVenditaNetto;
	}

	/**
	 * E' dato dal (Prezzo di Fustello decurtato degli scontiSSN) al netto dell'iva
	 */
	public void setPrezzoVenditaNetto(BigDecimal prezzoVenditaNetto) {
		this.prezzoVenditaNetto = prezzoVenditaNetto;
	}

	
	
	public BigDecimal getQuotaPezzo() {
		return quotaPezzo;
	}

	public void setQuotaPezzo(BigDecimal quotaPezzo) {
		this.quotaPezzo = quotaPezzo;
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
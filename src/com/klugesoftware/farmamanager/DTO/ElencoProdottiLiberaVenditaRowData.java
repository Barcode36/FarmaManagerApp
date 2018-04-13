package com.klugesoftware.farmamanager.DTO;

import java.math.BigDecimal;

public class ElencoProdottiLiberaVenditaRowData {
	private Integer idProdotto;
	private String data;
	private String minsan;
	private String descrizione;
	private Integer quantita;
	private BigDecimal prezzoVendita;
	private BigDecimal scontoUnitario;
	private BigDecimal prezzoVenditaNettoIvaESconti;
	private BigDecimal costoUnitarioSenzaIva;
	private BigDecimal margineUnitario;
	private BigDecimal ricaricoUnitario;
	private BigDecimal profittoUnitario;
	
	public ElencoProdottiLiberaVenditaRowData(){}
	
	public ElencoProdottiLiberaVenditaRowData(	Integer idProdotto,
										String data,
										String minsan,
										String descrizione,
										Integer quantita,
										BigDecimal prezzoVendita,
										BigDecimal scontoUnitario,
										BigDecimal prezzoVenditaNettoIvaESconti,
										BigDecimal costoUnitarioSenzaIva,
										BigDecimal margineUnitario,
										BigDecimal ricaricoUnitario,
										BigDecimal profittoUnitario){
		this.idProdotto = idProdotto;
		this.data = data;
		this.minsan = minsan;
		this.descrizione = descrizione;
		this.quantita = quantita;
		this.prezzoVendita = prezzoVendita;
		this.scontoUnitario = scontoUnitario;
		this.prezzoVenditaNettoIvaESconti = prezzoVenditaNettoIvaESconti;
		this.costoUnitarioSenzaIva = costoUnitarioSenzaIva;
		this.margineUnitario = margineUnitario;
		this.ricaricoUnitario = ricaricoUnitario;
		this.profittoUnitario = profittoUnitario;
	}

	public Integer getIdProdotto() {
		return idProdotto;
	}

	public void setIdProdotto(Integer idProdotto) {
		this.idProdotto = idProdotto;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getMinsan() {
		return minsan;
	}

	public void setMinsan(String minsan) {
		this.minsan = minsan;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Integer getQuantita() {
		return quantita;
	}

	public void setQuantita(Integer quantita) {
		this.quantita = quantita;
	}

	public BigDecimal getPrezzoVendita() {
		return prezzoVendita;
	}

	public void setPrezzoVendita(BigDecimal prezzoVendita) {
		this.prezzoVendita = prezzoVendita;
	}

	public BigDecimal getScontoUnitario() {
		return scontoUnitario;
	}

	public void setScontoUnitario(BigDecimal scontoUnitario) {
		this.scontoUnitario = scontoUnitario;
	}

	public BigDecimal getPrezzoVenditaNettoIvaESconti() {
		return prezzoVenditaNettoIvaESconti;
	}

	public void setPrezzoVenditaNettoIvaESconti(BigDecimal prezzoVenditaNettoIvaESconti) {
		this.prezzoVenditaNettoIvaESconti = prezzoVenditaNettoIvaESconti;
	}

	public BigDecimal getCostoUnitarioSenzaIva() {
		return costoUnitarioSenzaIva;
	}

	public void setCostoUnitarioSenzaIva(BigDecimal costoUnitarioSenzaIva) {
		this.costoUnitarioSenzaIva = costoUnitarioSenzaIva;
	}

	public BigDecimal getMargineUnitario() {
		return margineUnitario;
	}

	public void setMargineUnitario(BigDecimal margineUnitario) {
		this.margineUnitario = margineUnitario;
	}

	public BigDecimal getRicaricoUnitario() {
		return ricaricoUnitario;
	}

	public void setRicaricoUnitario(BigDecimal ricaricoUnitario) {
		this.ricaricoUnitario = ricaricoUnitario;
	}

	public BigDecimal getProfittoUnitario() {
		return profittoUnitario;
	}

	public void setProfittoUnitario(BigDecimal profittoUnitario) {
		this.profittoUnitario = profittoUnitario;
	}

}

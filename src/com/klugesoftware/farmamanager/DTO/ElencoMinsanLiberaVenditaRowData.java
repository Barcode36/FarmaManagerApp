package com.klugesoftware.farmamanager.DTO;

import java.math.BigDecimal;
import java.util.Date;

public class ElencoMinsanLiberaVenditaRowData {
	
	private String minsan;
	private String descrizione;
	private Integer quantitaTotale;
	private BigDecimal prezzoVenditaMedio;
	private BigDecimal prezzoNettoMedio;
	private BigDecimal scontoMedio;
	private BigDecimal costoMedio;
	private BigDecimal margineMedio;
	private BigDecimal ricaricoMedio; 
	private BigDecimal profittoMedio;
	
	public ElencoMinsanLiberaVenditaRowData() {
		
	}
	
	public ElencoMinsanLiberaVenditaRowData(
			String minsan,
			String descrizione,
			Integer quantitaTotale,
			BigDecimal prezzoVenditaMedio,
			BigDecimal prezzoNettoMedio,
			BigDecimal scontoMedio,
			BigDecimal costoMedio,
			BigDecimal margineMedio,
			BigDecimal ricaricoMedio, 
			BigDecimal profittoMedio		
			) {
		this.minsan = minsan;
		this.descrizione = descrizione;
		this.quantitaTotale = quantitaTotale;
		this.prezzoVenditaMedio = prezzoVenditaMedio;
		this.prezzoNettoMedio = prezzoNettoMedio;
		this.scontoMedio = scontoMedio;
		this.costoMedio = costoMedio;
		this.margineMedio = margineMedio;
		this.ricaricoMedio = ricaricoMedio;
		this.profittoMedio = profittoMedio;
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

	public Integer getQuantitaTotale() {
		return quantitaTotale;
	}
	public void setQuantitaTotale(Integer quantitaTotale) {
		this.quantitaTotale = quantitaTotale;
	}
	public BigDecimal getPrezzoVenditaMedio() {
		return prezzoVenditaMedio;
	}
	public void setPrezzoVenditaMedio(BigDecimal prezzoVenditaMedio) {
		this.prezzoVenditaMedio = prezzoVenditaMedio;
	}
	public BigDecimal getPrezzoNettoMedio() {
		return prezzoNettoMedio;
	}
	public void setPrezzoNettoMedio(BigDecimal prezzoNettoMedio) {
		this.prezzoNettoMedio = prezzoNettoMedio;
	}
	public BigDecimal getScontoMedio() {
		return scontoMedio;
	}
	public void setScontoMedio(BigDecimal scontoMedio) {
		this.scontoMedio = scontoMedio;
	}
	public BigDecimal getCostoMedio() {
		return costoMedio;
	}
	public void setCostoMedio(BigDecimal costoMedio) {
		this.costoMedio = costoMedio;
	}
	public BigDecimal getMargineMedio() {
		return margineMedio;
	}
	public void setMargineMedio(BigDecimal margineMedio) {
		this.margineMedio = margineMedio;
	}
	public BigDecimal getRicaricoMedio() {
		return ricaricoMedio;
	}
	public void setRicaricoMedio(BigDecimal ricaricoMedio) {
		this.ricaricoMedio = ricaricoMedio;
	}
	public BigDecimal getProfittoMedio() {
		return profittoMedio;
	}
	public void setProfittoMedio(BigDecimal profittoMedio) {
		this.profittoMedio = profittoMedio;
	}

}

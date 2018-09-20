package com.klugesoftware.farmamanager.DTO;

import java.math.BigDecimal;
import java.util.Date;

public class ElencoTotaliGiornalieriRowData {
	
	private String data;
	private BigDecimal totaleVenditeLorde;
	private BigDecimal totaleProfitti;
	private BigDecimal totaleVenditeLordeSSN;
	private BigDecimal totaleProfittiSSN;
	private BigDecimal totaleVenditeLordeLibere;
	private BigDecimal totaleProfittiLibere;
	
	public ElencoTotaliGiornalieriRowData() {
		
	}

	public ElencoTotaliGiornalieriRowData(String data) {
		this.data = data;
		this.totaleVenditeLorde = new BigDecimal(0);
		this.totaleProfitti = new BigDecimal(0);
		this.totaleVenditeLordeSSN = new BigDecimal(0);
		this.totaleProfittiSSN = new BigDecimal(0);
		this.totaleVenditeLordeLibere = new BigDecimal(0);
		this.totaleProfittiLibere = new BigDecimal(0);
		
	}
	
	public ElencoTotaliGiornalieriRowData(
			String data,
			BigDecimal totaleVenditeLorde,
			BigDecimal totaleProfitti,
			BigDecimal totaleVenditeLordeSSN,
			BigDecimal totaleProfittiSSN,
			BigDecimal totaleVenditeLordeLibere,
			BigDecimal totaleProfittiLibere
			) {
		this.data = data;
		this.totaleVenditeLorde = totaleVenditeLorde;
		this.totaleProfitti = totaleProfitti;
		this.totaleVenditeLordeSSN = totaleVenditeLordeSSN;
		this.totaleProfittiSSN = totaleProfittiSSN;
		this.totaleVenditeLordeLibere = totaleVenditeLordeLibere;
		this.totaleProfittiLibere = totaleProfittiLibere;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public BigDecimal getTotaleVenditeLorde() {
		return totaleVenditeLorde;
	}

	public void setTotaleVenditeLorde(BigDecimal totaleVenditeLorde) {
		this.totaleVenditeLorde = totaleVenditeLorde;
	}

	public BigDecimal getTotaleProfitti() {
		return totaleProfitti;
	}

	public void setTotaleProfitti(BigDecimal totaleProfitti) {
		this.totaleProfitti = totaleProfitti;
	}

	public BigDecimal getTotaleVenditeLordeSSN() {
		return totaleVenditeLordeSSN;
	}

	public void setTotaleVenditeLordeSSN(BigDecimal totaleVenditeLordeSSN) {
		this.totaleVenditeLordeSSN = totaleVenditeLordeSSN;
	}

	public BigDecimal getTotaleProfittiSSN() {
		return totaleProfittiSSN;
	}

	public void setTotaleProfittiSSN(BigDecimal totaleProfittiSSN) {
		this.totaleProfittiSSN = totaleProfittiSSN;
	}

	public BigDecimal getTotaleVenditeLordeLibere() {
		return totaleVenditeLordeLibere;
	}

	public void setTotaleVenditeLordeLibere(BigDecimal totaleVenditeLordeLibere) {
		this.totaleVenditeLordeLibere = totaleVenditeLordeLibere;
	}

	public BigDecimal getTotaleProfittiLibere() {
		return totaleProfittiLibere;
	}

	public void setTotaleProfittiLibere(BigDecimal totaleProfittiLibere) {
		this.totaleProfittiLibere = totaleProfittiLibere;
	}
	
	

}

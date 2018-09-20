package com.klugesoftware.farmamanager.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;


/**
 * The  class for the Giacenze database table.
 * 
 */
public class Giacenze implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idGiacenza;

	private BigDecimal costoUltimoDeivato;

	private Date dataCostoUltimo;

	private String minsan;
	
	private String descrizione;
	
	private int giacenza;
	
	private int venditeAnnoInCorso;

	public Giacenze() {
		costoUltimoDeivato = new BigDecimal(0).setScale(2,RoundingMode.HALF_DOWN);
	}

	public Giacenze(
			int idGiacenza,
			BigDecimal costoUltimoDeivato,
			Date dataCostoUltimo,
			String minsan,			
			String descrizione,			
			int giacenza,			
			int venditeAnnoInCorso
			){
		this.idGiacenza = idGiacenza;
		this.costoUltimoDeivato = costoUltimoDeivato;
		this.dataCostoUltimo = dataCostoUltimo; 
		this.minsan = minsan;			
		this.descrizione = descrizione;			
		this.giacenza = giacenza;			
		this.venditeAnnoInCorso = venditeAnnoInCorso;		
	}
	public int getIdGiacenza() {
		return this.idGiacenza;
	}

	public void setIdGiacenza(int idGiacenza) {
		this.idGiacenza = idGiacenza;
	}

	public BigDecimal getCostoUltimoDeivato() {
		return this.costoUltimoDeivato;
	}

	public void setCostoUltimoDeivato(BigDecimal costoUltimo) {
		this.costoUltimoDeivato = costoUltimo;
	}

	public Date getDataCostoUltimo() {
		return this.dataCostoUltimo;
	}

	public void setDataCostoUltimo(Date dataCostoUltimo) {
		this.dataCostoUltimo = dataCostoUltimo;
	}

	public String getMinsan() {
		return this.minsan;
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

	public int getGiacenza() {
		return giacenza;
	}

	public void setGiacenza(int giacenza) {
		this.giacenza = giacenza;
	}

	public int getVenditeAnnoInCorso() {
		return venditeAnnoInCorso;
	}

	public void setVenditeAnnoInCorso(int venditeAnnoInCorso) {
		this.venditeAnnoInCorso = venditeAnnoInCorso;
	}

}
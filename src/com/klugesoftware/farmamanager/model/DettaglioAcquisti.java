package com.klugesoftware.farmamanager.model;

import java.io.Serializable;

import java.math.BigDecimal;


/**
 * The class for the DettaglioAcquisti database table.
 * 
 */
public class DettaglioAcquisti implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idDettaglioAcquisti;

	private int aliquotaIva;

	private BigDecimal costoNettoIva;

	private String descrizione;

	private Acquisti acquisto;

	private String minsan;

	private int quantita;
	
	private int numReg;

	public DettaglioAcquisti() {
	}

	public int getIdDettaglioAcquisti() {
		return this.idDettaglioAcquisti;
	}

	public void setIdDettaglioAcquisti(int idDettaglioAcquisti) {
		this.idDettaglioAcquisti = idDettaglioAcquisti;
	}

	public int getAliquotaIva() {
		return this.aliquotaIva;
	}

	public void setAliquotaIva(int aliquotaIva) {
		this.aliquotaIva = aliquotaIva;
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

	public Acquisti getAcquisto() {
		return acquisto;
	}

	public void setAcquisto(Acquisti acquisto) {
		this.acquisto = acquisto;
	}

	public String getMinsan() {
		return this.minsan;
	}

	public void setMinsan(String minsan) {
		this.minsan = minsan;
	}

	public int getQuantita() {
		return this.quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	public int getNumReg() {
		return numReg;
	}

	public void setNumReg(int numReg) {
		this.numReg = numReg;
	}

}
package com.klugesoftware.farmamanager.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


/**
 * The  class for the Acquisti database table.
 * 
 */
public class Acquisti implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idAcquisto;

	private String causale;

	private Date data;

	private String ditta;

	private String numDocumento;

	private int numReg;
	
	private Collection<DettaglioAcquisti> dettaglioAcquisti;


	public Acquisti() {
		dettaglioAcquisti = new ArrayList<DettaglioAcquisti>();
	}

	public int getIdAcquisto() {
		return this.idAcquisto;
	}

	public void setIdAcquisto(int idAcquisto) {
		this.idAcquisto = idAcquisto;
	}

	public String getCausale() {
		return this.causale;
	}

	public void setCausale(String causale) {
		this.causale = causale;
	}

	public Date getData() {
		return this.data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDitta() {
		return this.ditta;
	}

	public void setDitta(String ditta) {
		this.ditta = ditta;
	}

	public String getNumDocumento() {
		return this.numDocumento;
	}

	public void setNumDocumento(String numDocumento) {
		this.numDocumento = numDocumento;
	}

	public int getNumReg() {
		return this.numReg;
	}

	public void setNumReg(int numReg) {
		this.numReg = numReg;
	}
	
	public void addDettaglioAcquisti(DettaglioAcquisti dettaglioAcquisto){
		dettaglioAcquisti.add(dettaglioAcquisto);
	}
	
	public void removeDettaglioAcquisti(DettaglioAcquisti dettaglioAcquisto){
		dettaglioAcquisti.remove(dettaglioAcquisto);
	}
}
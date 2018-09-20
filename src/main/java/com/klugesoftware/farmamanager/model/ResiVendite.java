package com.klugesoftware.farmamanager.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The class for the ResiVendite database table.
 * 
 */
public class ResiVendite implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idResoVendita;

	private Date dataReso;

	private Integer numreg;

	private BigDecimal scontoVendita;

	private Integer totalePezziResi;

	private BigDecimal totaleResoVendita;

	private BigDecimal totaleResoVenditaScontata;

	private List<ResiVenditeLibere> resiVenditeLiberes;

	private List<ResiVenditeSSN> resiVenditeSsns;

	public ResiVendite() {
		scontoVendita = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleResoVendita = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		totaleResoVenditaScontata = new BigDecimal(0).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
		resiVenditeLiberes = new ArrayList<ResiVenditeLibere>();
		resiVenditeSsns = new ArrayList<ResiVenditeSSN>();
		totalePezziResi = new Integer(0);
		numreg = new Integer(0);
	}
	
	public ResiVendite(
			Integer idResoVendita,
			Date dataReso,
			Integer numreg,
			BigDecimal scontoVendita,
			Integer totalePezziResi,
			BigDecimal totaleResoVendita,
			BigDecimal totaleResoVenditaScontata
			){
		this.idResoVendita = idResoVendita;
		this.dataReso= dataReso;
		this.numreg = numreg;
		this.scontoVendita = scontoVendita;
		this.totalePezziResi = totalePezziResi;
		this.totaleResoVendita = totaleResoVendita;
		this.totaleResoVenditaScontata = totaleResoVenditaScontata;
	}

	public Integer getIdResoVendita() {
		return this.idResoVendita;
	}

	public void setIdResoVendita(Integer idResoVendita) {
		this.idResoVendita = idResoVendita;
	}

	public Date getDataReso() {
		return this.dataReso;
	}

	public void setDataReso(Date dataReso) {
		this.dataReso = dataReso;
	}

	public Integer getNumreg() {
		return this.numreg;
	}

	public void setNumreg(Integer numreg) {
		this.numreg = numreg;
	}

	public BigDecimal getScontoVendita() {
		return this.scontoVendita;
	}

	public void setScontoVendita(BigDecimal scontoVendita) {
		this.scontoVendita = scontoVendita;
	}

	public Integer getTotalePezziResi() {
		return this.totalePezziResi;
	}

	public void setTotalePezziResi(Integer totalePezziResi) {
		this.totalePezziResi = totalePezziResi;
	}

	public BigDecimal getTotaleResoVendita() {
		return this.totaleResoVendita;
	}

	public void setTotaleResoVendita(BigDecimal totaleResoVendita) {
		this.totaleResoVendita = totaleResoVendita;
	}

	public BigDecimal getTotaleResoVenditaScontata() {
		return this.totaleResoVenditaScontata;
	}

	public void setTotaleResoVenditaScontata(BigDecimal totaleResoVenditaScontata) {
		this.totaleResoVenditaScontata = totaleResoVenditaScontata;
	}

	public List<ResiVenditeLibere> getResiVenditeLiberes() {
		return this.resiVenditeLiberes;
	}

	public void setResiVenditeLiberes(List<ResiVenditeLibere> resiVenditeLiberes) {
		this.resiVenditeLiberes = resiVenditeLiberes;
	}

	public ResiVenditeLibere addResiVenditeLibere(ResiVenditeLibere resiVenditeLibere) {
		getResiVenditeLiberes().add(resiVenditeLibere);
		resiVenditeLibere.setResiVendite(this);

		return resiVenditeLibere;
	}

	public ResiVenditeLibere removeResiVenditeLibere(ResiVenditeLibere resiVenditeLibere) {
		getResiVenditeLiberes().remove(resiVenditeLibere);
		resiVenditeLibere.setResiVendite(null);

		return resiVenditeLibere;
	}

	public List<ResiVenditeSSN> getResiVenditeSsns() {
		return this.resiVenditeSsns;
	}

	public void setResiVenditeSsns(List<ResiVenditeSSN> resiVenditeSsns) {
		this.resiVenditeSsns = resiVenditeSsns;
	}

	public ResiVenditeSSN addResiVenditeSsn(ResiVenditeSSN resiVenditeSsn) {
		getResiVenditeSsns().add(resiVenditeSsn);
		resiVenditeSsn.setResiVendite(this);

		return resiVenditeSsn;
	}

	public ResiVenditeSSN removeResiVenditeSsn(ResiVenditeSSN resiVenditeSsn) {
		getResiVenditeSsns().remove(resiVenditeSsn);
		resiVenditeSsn.setResiVendite(null);

		return resiVenditeSsn;
	}

}
package com.klugesoftware.farmamanager.model;

import java.util.Date;

public class Importazioni {
	
	private Integer idImportazione;
	private Date dataImportazione;
	private Integer ultimoNumRegImportato;
	private Date dataUltimoMovImportato;
	private String note;
	
	public Importazioni(){
		
	}
	
	public Importazioni(
			Integer idImportazione,
			Date dataImportazione,
			Integer ultimoNumRegImportato,
			Date dataUltimoMovImportato,
			String note
			){
		this.idImportazione = idImportazione;
		this.dataImportazione =  dataImportazione;
		this.ultimoNumRegImportato = ultimoNumRegImportato;
		this.dataUltimoMovImportato = dataUltimoMovImportato;
		this.note = note;	
	}
	
	public Integer getIdImportazione() {
		return idImportazione;
	}
	public void setIdImportazione(Integer idImportazione) {
		this.idImportazione = idImportazione;
	}
	public Date getDataImportazione() {
		return dataImportazione;
	}
	public void setDataImportazione(Date dataImportazione) {
		this.dataImportazione = dataImportazione;
	}
	public Integer getUltimoNumRegImportato() {
		return ultimoNumRegImportato;
	}
	public void setUltimoNumRegImportato(Integer ultimoNumRegImportato) {
		this.ultimoNumRegImportato = ultimoNumRegImportato;
	}
	public Date getDataUltimoMovImportato() {
		return dataUltimoMovImportato;
	}
	public void setDataUltimoMovImportato(Date dataUltimoMovImportato) {
		this.dataUltimoMovImportato = dataUltimoMovImportato;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

}

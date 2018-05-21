package com.klugesoftware.farmamanager.DTO;

import com.klugesoftware.farmamanager.model.CustomRoundingAndScaling;

import java.math.BigDecimal;

public class ConfrontoTotaliVenditeRowData {

    private String colDescrizione;
    private BigDecimal totaleLibere;
    private BigDecimal totaleLiberePrec;

    /**
     * formato diffPercLibere: es. 32,50%
     */
    private String diffPercLibere;
    private BigDecimal totaleSSN;
    private BigDecimal totaleSSNPrecedente;

    /**
     * formato diffPercSSN: es. 32,50%
     */
    private String diffPercSSN;
    private BigDecimal totale;
    private BigDecimal totalePrecedente;

    /**
     * formato diffPercTotale: es. 32,50%
     */
    private String diffPercTotale;

    public ConfrontoTotaliVenditeRowData(String colDescrizione, BigDecimal totaleLibere, BigDecimal totaleLiberePrec,  String diffPercLibere, BigDecimal totaleSSN, BigDecimal totaleSSNPrecedente, String diffPercSSN, BigDecimal totale, BigDecimal totalePrecedente, String diffPercTotale) {
        this.colDescrizione = colDescrizione;
        this.totaleLibere = totaleLibere;
        this.totaleLiberePrec = totaleLiberePrec;
        this.diffPercLibere = diffPercLibere;
        this.totaleSSN = totaleSSN;
        this.totaleSSNPrecedente = totaleSSNPrecedente;
        this.diffPercSSN = diffPercSSN;
        this.totale = totale;
        this.totalePrecedente = totalePrecedente;
        this.diffPercTotale = diffPercTotale;
    }

    public ConfrontoTotaliVenditeRowData(){}

    public String getColDescrizione() {
        return colDescrizione;
    }

    public void setColDescrizione(String colDescrizione) {
        this.colDescrizione = colDescrizione;
    }

    public BigDecimal getTotaleLibere() {
        return totaleLibere;
    }

    public void setTotaleLibere(BigDecimal totaleLibere) {
        this.totaleLibere = totaleLibere;
    }

    public BigDecimal getTotaleLiberePrec() {
        return totaleLiberePrec;
    }

    public void setTotaleLiberePrec(BigDecimal totaleLiberePrec) {
        this.totaleLiberePrec = totaleLiberePrec;
    }

    /**
     * formato diffPercLibere: es. 32,50%
     */
    public String getDiffPercLibere() {
        return diffPercLibere;
    }

    /**
     * formato diffPercLibere: es. 32,50%
     */
    public void setDiffPercLibere(String diffPercLibere) {
        this.diffPercLibere = diffPercLibere;
    }

    public void setDiffPercLibere(BigDecimal valoreAttuale, BigDecimal valorePrecedente){
        diffPercLibere = diffPercToString(valoreAttuale,valorePrecedente);
    }

    public BigDecimal getTotaleSSN() {
        return totaleSSN;
    }

    public void setTotaleSSN(BigDecimal totaleSSN) {
        this.totaleSSN = totaleSSN;
    }

    public BigDecimal getTotaleSSNPrecedente() {
        return totaleSSNPrecedente;
    }

    public void setTotaleSSNPrecedente(BigDecimal totaleSSNPrecedente) {
        this.totaleSSNPrecedente = totaleSSNPrecedente;
    }

    /**
     * formato diffPercSSN: es. 32,50%
     */
    public String getDiffPercSSN() {
        return diffPercSSN;
    }

    /**
     * formato diffPercSSN: es. 32,50%
     */
    public void setDiffPercSSN(String diffPercSSN) {
        this.diffPercSSN = diffPercSSN;
    }

    /**
     * formato diffPercSSN: es. 32,50%
     */
    public void setDiffPercSSN(BigDecimal valoreAttuale, BigDecimal valorePrecedente) {
        this.diffPercSSN = diffPercToString(valoreAttuale,valorePrecedente);
    }

    public BigDecimal getTotale() {
        return totale;
    }

    public void setTotale(BigDecimal totale) {
        this.totale = totale;
    }

    public BigDecimal getTotalePrecedente() {
        return totalePrecedente;
    }

    public void setTotalePrecedente(BigDecimal totalePrecedente) {
        this.totalePrecedente = totalePrecedente;
    }

    /**
     * formato diffPercTotale: es. 32,50%
     */
    public String getDiffPercTotale() {
        return diffPercTotale;
    }

    /**
     * formato diffPercTotale: es. 32,50%
     */
    public void setDiffPercTotale(String diffPercTotale) {
        this.diffPercTotale = diffPercTotale;
    }

    /**
     * formato diffPercTotale: es. 32,50%
     */
    public void setDiffPercTotale(BigDecimal valoreAttuale, BigDecimal valorePrecedente) {
        this.diffPercTotale = diffPercToString(valoreAttuale,valorePrecedente);
    }

    private String diffPercToString(BigDecimal valoreAttuale,BigDecimal valorePrecedente){
        if(valoreAttuale.doubleValue() > 0 && valorePrecedente.doubleValue() > 0) {
            double temp = ((valoreAttuale.doubleValue() / valorePrecedente.doubleValue()) * 100) - 100;
            BigDecimal temp2 = new BigDecimal(temp).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode());
            return (temp2.toString().replace(".", ",") + " %");
        }else
            return "";
    }
}

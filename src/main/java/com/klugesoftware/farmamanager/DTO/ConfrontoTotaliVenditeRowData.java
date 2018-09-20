package com.klugesoftware.farmamanager.DTO;

import com.klugesoftware.farmamanager.model.CustomRoundingAndScaling;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

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

    private NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.ITALY);
    private DecimalFormat df = (DecimalFormat)nf;


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

    private String converteInPercentuale(BigDecimal temp){
        String ret = temp.toString().replace(".",",");
        return ret+" %";
    }

    public String getTotaleLibere() {
        if (colDescrizione.equals("Margine") || colDescrizione.equals("Ricarico")){
            return converteInPercentuale(totaleLibere);
        }
        else{
            return df.format(totaleLibere);
        }

    }

    public void setTotaleLibere(BigDecimal totaleLibere) {
        this.totaleLibere = totaleLibere;
    }

    public String getTotaleLiberePrec() {
        if (colDescrizione.equals("Margine") || colDescrizione.equals("Ricarico")){
            return converteInPercentuale(totaleLiberePrec);
        }
        else{
            return df.format(totaleLiberePrec);
        }
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

    public String getTotaleSSN() {
        if (colDescrizione.equals("Margine") || colDescrizione.equals("Ricarico")){
            return converteInPercentuale(totaleSSN);
        }
        else{
            return df.format(totaleSSN);
        }
    }

    public void setTotaleSSN(BigDecimal totaleSSN) {
        this.totaleSSN = totaleSSN;
    }

    public String getTotaleSSNPrecedente() {
        if (colDescrizione.equals("Margine") || colDescrizione.equals("Ricarico")){
            return converteInPercentuale(totaleSSNPrecedente);
        }
        else{
            return df.format(totaleSSNPrecedente);
        }
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

    public String getTotale() {
        if (colDescrizione.equals("Margine") || colDescrizione.equals("Ricarico")){
            return converteInPercentuale(totale);
        }
        else{
            return df.format(totale);
        }
    }

    public void setTotale(BigDecimal totale) {
        this.totale = totale;
    }

    public String getTotalePrecedente() {
        if (colDescrizione.equals("Margine") || colDescrizione.equals("Ricarico")){
            return converteInPercentuale(totalePrecedente);
        }
        else{
            return df.format(totalePrecedente);
        }
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
            if (temp2.doubleValue() > 0)
                return ("+"+temp2.toString().replace(".", ",") + " %");
            else
                return (temp2.toString().replace(".", ",") + " %");
        }else
            return "";
    }

    public BigDecimal getValTotaleLibere(){
        return totaleLibere;
    }

    public BigDecimal getValTotaleLiberePrecedenti(){
        return totaleLiberePrec;
    }

    public BigDecimal getValTotaleSSN(){
        return totaleSSN;
    }

    public BigDecimal getValTotaleSSNPrecedente(){
        return totaleSSNPrecedente;
    }

    public BigDecimal getValTotale(){
        return totale;
    }

    public BigDecimal getValTotalePrecedente(){
        return totalePrecedente;
    }

}

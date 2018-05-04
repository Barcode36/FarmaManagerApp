package com.klugesoftware.farmamanager.DTO;

import java.math.BigDecimal;

public class DettaglioTotaliVenditeRowData {

    private String descrizione;
    private BigDecimal totaleVenditeLibere;
    private BigDecimal totaleVenditeSSN;
    private BigDecimal totaleVendite;

    public DettaglioTotaliVenditeRowData(String descrizione,BigDecimal totaleVendite, BigDecimal totaleVenditeLibere, BigDecimal totaleVenditeSSN){
        this.descrizione = descrizione;
        this.totaleVendite = totaleVendite;
        this.totaleVenditeLibere = totaleVenditeLibere;
        this.totaleVenditeSSN = totaleVenditeSSN;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public BigDecimal getTotaleVenditeLibere() {
        return totaleVenditeLibere;
    }

    public void setTotaleVenditeLibere(BigDecimal totaleVenditeLibere) {
        this.totaleVenditeLibere = totaleVenditeLibere;
    }

    public BigDecimal getTotaleVenditeSSN() {
        return totaleVenditeSSN;
    }

    public void setTotaleVenditeSSN(BigDecimal totaleVenditeSSN) {
        this.totaleVenditeSSN = totaleVenditeSSN;
    }

    public BigDecimal getTotaleVendite() {
        return totaleVendite;
    }

    public void setTotaleVendite(BigDecimal totaleVendite) {
        this.totaleVendite = totaleVendite;
    }
}

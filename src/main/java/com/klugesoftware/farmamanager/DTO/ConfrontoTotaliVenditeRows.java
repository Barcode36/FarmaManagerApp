package com.klugesoftware.farmamanager.DTO;

import com.klugesoftware.farmamanager.IOFunctions.TotaliGeneraliVenditaEstratti;
import com.klugesoftware.farmamanager.IOFunctions.TotaliGeneraliVenditaEstrattiGiornalieri;
import com.klugesoftware.farmamanager.db.TotaliGeneraliVenditaEstrattiGiornalieriDAOManager;
import com.klugesoftware.farmamanager.model.CustomRoundingAndScaling;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 * Questa classe è l'elenco delle righe( ConfrontoTotaliVenditeRowData)
 * della TabellaTotali della Scene ConfrontoTotaliVendita
 */
public class ConfrontoTotaliVenditeRows {

    private ArrayList<ConfrontoTotaliVenditeRowData> rows;
    private Date dateFrom;
    private Date dateTo;
    private Date dateFromPrec;
    private Date dateToPrec;
    private Integer giorniLavoratiPrec;
    private Integer giorniLavorati;

    public ConfrontoTotaliVenditeRows(Date dateFrom, Date dateTo, Date dateFromPrec, Date dateToPrec) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.dateFromPrec = dateFromPrec;
        this.dateToPrec = dateToPrec;

        rows = new ArrayList<ConfrontoTotaliVenditeRowData>();
        makeRows();
    }

    // crea l'elenco delle righe
    private void makeRows(){
        TotaliGeneraliVenditaEstratti totaliGenerali = new TotaliGeneraliVenditaEstratti();
        ArrayList<TotaliGeneraliVenditaEstrattiGiornalieri> elencoTotaliGiornalieri = TotaliGeneraliVenditaEstrattiGiornalieriDAOManager.findbetweenDate(dateFrom,dateTo);
        if(elencoTotaliGiornalieri.isEmpty()){
            rows = null;
            return;
        }

        totaliGenerali.addElencoTotaliGeneraliVenditaEstrattiGiornalieri(elencoTotaliGiornalieri);

        TotaliGeneraliVenditaEstratti totaliGeneraliPrecedenti = new TotaliGeneraliVenditaEstratti();
        elencoTotaliGiornalieri.clear();
        elencoTotaliGiornalieri = TotaliGeneraliVenditaEstrattiGiornalieriDAOManager.findbetweenDate(dateFromPrec,dateToPrec);
        if(elencoTotaliGiornalieri.isEmpty()) {
            rows = null;
            return;
        }

        totaliGeneraliPrecedenti.addElencoTotaliGeneraliVenditaEstrattiGiornalieri(elencoTotaliGiornalieri);

        giorniLavorati = totaliGenerali.getGiorniLavorativi();

        giorniLavoratiPrec = totaliGeneraliPrecedenti.getGiorniLavorativi();

        String[] descrizioni = {
                "Totale Vendite\nLorde",
                "Totale Sconti",
                "Totale Vendite\nal netto di sconti",
                "Totale Vendite\nal netto di iva\n e sconti",
                "Totale Costi\nal netto di iva",
                "Totale Profitti",
                "Margine",
                "Ricarico",
        };

        //Valori Vendita Lordi
        ConfrontoTotaliVenditeRowData row1 = new ConfrontoTotaliVenditeRowData();
        row1.setColDescrizione(descrizioni[0]);
        row1.setTotaleLibere(totaliGenerali.getTotaleVenditeLordeLibere());
        row1.setTotaleLiberePrec(totaliGeneraliPrecedenti.getTotaleVenditeLordeLibere());
        row1.setDiffPercLibere(totaliGenerali.getTotaleVenditeLordeLibere(),totaliGeneraliPrecedenti.getTotaleVenditeLordeLibere());
        row1.setTotaleSSN(totaliGenerali.getTotaleVenditeLordeSSN());
        row1.setTotaleSSNPrecedente(totaliGeneraliPrecedenti.getTotaleVenditeLordeSSN());
        row1.setDiffPercSSN(totaliGenerali.getTotaleVenditeLordeSSN(),totaliGeneraliPrecedenti.getTotaleVenditeLordeSSN());
        row1.setTotale(totaliGenerali.getTotaleVenditeLorde());
        row1.setTotalePrecedente(totaliGeneraliPrecedenti.getTotaleVenditeLorde());
        row1.setDiffPercTotale(totaliGenerali.getTotaleVenditeLorde(),totaliGeneraliPrecedenti.getTotaleVenditeLorde());
        rows.add(row1);

        //Valori Sconti
        ConfrontoTotaliVenditeRowData row2 = new ConfrontoTotaliVenditeRowData();
        row2.setColDescrizione(descrizioni[1]);
        row2.setTotaleLibere(totaliGenerali.getTotaleScontiLibere());
        row2.setTotaleLiberePrec(totaliGeneraliPrecedenti.getTotaleScontiLibere());
        row2.setDiffPercLibere(totaliGenerali.getTotaleScontiLibere(),totaliGeneraliPrecedenti.getTotaleScontiLibere());
        row2.setTotaleSSN(totaliGenerali.getTotaleScontiSSN());
        row2.setTotaleSSNPrecedente(totaliGeneraliPrecedenti.getTotaleScontiSSN());
        row2.setDiffPercSSN(totaliGenerali.getTotaleScontiSSN(),totaliGeneraliPrecedenti.getTotaleScontiSSN());
        row2.setTotale(totaliGenerali.getTotaleSconti());
        row2.setTotalePrecedente(totaliGeneraliPrecedenti.getTotaleSconti());
        row2.setDiffPercTotale(totaliGenerali.getTotaleSconti(),totaliGeneraliPrecedenti.getTotaleSconti());
        rows.add(row2);

        //Valori Vendite Netto Sconti
        ConfrontoTotaliVenditeRowData row3 = new ConfrontoTotaliVenditeRowData();
        row3.setColDescrizione(descrizioni[2]);
        row3.setTotaleLibere(totaliGenerali.getTotaleVenditeNettoScontiLibere());
        row3.setTotaleLiberePrec(totaliGeneraliPrecedenti.getTotaleVenditeNettoScontiLibere());
        row3.setDiffPercLibere(totaliGenerali.getTotaleVenditeNettoScontiLibere(),totaliGeneraliPrecedenti.getTotaleVenditeNettoScontiLibere());
        row3.setTotaleSSN(totaliGenerali.getTotaleVenditeNettoScontiSSN());
        row3.setTotaleSSNPrecedente(totaliGeneraliPrecedenti.getTotaleVenditeNettoScontiSSN());
        row3.setDiffPercSSN(totaliGenerali.getTotaleVenditeNettoScontiSSN(),totaliGeneraliPrecedenti.getTotaleVenditeNettoScontiSSN());
        row3.setTotale(totaliGenerali.getTotaleVenditeNettoSconti());
        row3.setTotalePrecedente(totaliGeneraliPrecedenti.getTotaleVenditeNettoSconti());
        row3.setDiffPercTotale(totaliGenerali.getTotaleVenditeNettoSconti(),totaliGeneraliPrecedenti.getTotaleVenditeNettoSconti());
        rows.add(row3);

        //Valori Vendite Netto Iva e Sconti
        ConfrontoTotaliVenditeRowData row4 = new ConfrontoTotaliVenditeRowData();
        row4.setColDescrizione(descrizioni[3]);
        row4.setTotaleLibere(totaliGenerali.getTotaleVenditeNetteLibere());
        row4.setTotaleLiberePrec(totaliGeneraliPrecedenti.getTotaleVenditeNetteLibere());
        row4.setDiffPercLibere(totaliGenerali.getTotaleVenditeNetteLibere(),totaliGeneraliPrecedenti.getTotaleVenditeNetteLibere());
        row4.setTotaleSSN(totaliGenerali.getTotaleVenditeNetteSSN());
        row4.setTotaleSSNPrecedente(totaliGeneraliPrecedenti.getTotaleVenditeNetteSSN());
        row4.setDiffPercSSN(totaliGenerali.getTotaleVenditeNetteSSN(),totaliGeneraliPrecedenti.getTotaleVenditeNetteSSN());
        row4.setTotale(totaliGenerali.getTotaleVenditeNette());
        row4.setTotalePrecedente(totaliGeneraliPrecedenti.getTotaleVenditeNette());
        row4.setDiffPercTotale(totaliGenerali.getTotaleVenditeNette(),totaliGeneraliPrecedenti.getTotaleVenditeNette());
        rows.add(row4);

        //Valori Costi
        ConfrontoTotaliVenditeRowData row5 = new ConfrontoTotaliVenditeRowData();
        row5.setColDescrizione(descrizioni[4]);
        row5.setTotaleLibere(totaliGenerali.getTotaleCostiNettiLibere());
        row5.setTotaleLiberePrec(totaliGeneraliPrecedenti.getTotaleCostiNettiLibere());
        row5.setDiffPercLibere(totaliGenerali.getTotaleCostiNettiLibere(),totaliGeneraliPrecedenti.getTotaleCostiNettiLibere());
        row5.setTotaleSSN(totaliGenerali.getTotaleCostiNettiSSN());
        row5.setTotaleSSNPrecedente(totaliGeneraliPrecedenti.getTotaleCostiNettiSSN());
        row5.setDiffPercSSN(totaliGenerali.getTotaleCostiNettiSSN(),totaliGeneraliPrecedenti.getTotaleCostiNettiSSN());
        row5.setTotale(totaliGenerali.getTotaleCostiNetti());
        row5.setTotalePrecedente(totaliGeneraliPrecedenti.getTotaleCostiNetti());
        row5.setDiffPercTotale(totaliGenerali.getTotaleCostiNetti(),totaliGeneraliPrecedenti.getTotaleCostiNetti());
        rows.add(row5);

        //Valore Profitti
        ConfrontoTotaliVenditeRowData row6 = new ConfrontoTotaliVenditeRowData();
        row6.setColDescrizione(descrizioni[5]);
        row6.setTotaleLibere(totaliGenerali.getTotaleProfittiLibere());
        row6.setTotaleLiberePrec(totaliGeneraliPrecedenti.getTotaleProfittiLibere());
        row6.setDiffPercLibere(totaliGenerali.getTotaleProfittiLibere(),totaliGeneraliPrecedenti.getTotaleProfittiLibere());
        row6.setTotaleSSN(totaliGenerali.getTotaleProfittiSSN());
        row6.setTotaleSSNPrecedente(totaliGeneraliPrecedenti.getTotaleProfittiSSN());
        row6.setDiffPercSSN(totaliGenerali.getTotaleProfittiSSN(),totaliGeneraliPrecedenti.getTotaleProfittiSSN());
        row6.setTotale(totaliGenerali.getTotaleProfitti());
        row6.setTotalePrecedente(totaliGeneraliPrecedenti.getTotaleProfitti());
        row6.setDiffPercTotale(totaliGenerali.getTotaleProfitti(),totaliGeneraliPrecedenti.getTotaleProfitti());
        rows.add(row6);

        //Valori Margine
        ConfrontoTotaliVenditeRowData row7 = new ConfrontoTotaliVenditeRowData();
        row7.setColDescrizione(descrizioni[6]);

        double temp = 0;

        //margine vendite libere
        if (totaliGenerali.getTotaleProfittiLibere().doubleValue() > 0 && totaliGenerali.getTotaleVenditeNetteLibere().doubleValue() > 0) {
            temp = totaliGenerali.getTotaleProfittiLibere().doubleValue() / totaliGenerali.getTotaleVenditeNetteLibere().doubleValue() * 100;
        }else
            temp = 0;
        row7.setTotaleLibere(new BigDecimal(temp).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));

        if (totaliGeneraliPrecedenti.getTotaleProfittiLibere().doubleValue() > 0 && totaliGeneraliPrecedenti.getTotaleVenditeNetteLibere().doubleValue() > 0) {
            temp = totaliGeneraliPrecedenti.getTotaleProfittiLibere().doubleValue() / totaliGeneraliPrecedenti.getTotaleVenditeNetteLibere().doubleValue() * 100;
        }else
            temp = 0;
        row7.setTotaleLiberePrec(new BigDecimal(temp).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
        //row7.setDiffPercLibere(row7.getTotaleLibere(),row7.getTotaleLiberePrec());

        //margine vendite SSN
        if (totaliGenerali.getTotaleProfittiSSN().doubleValue() > 0 && totaliGenerali.getTotaleVenditeNetteSSN().doubleValue() > 0) {
            temp = totaliGenerali.getTotaleProfittiSSN().doubleValue() / totaliGenerali.getTotaleVenditeNetteSSN().doubleValue() * 100;
        }else
            temp = 0;
        row7.setTotaleSSN(new BigDecimal(temp).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
        if (totaliGeneraliPrecedenti.getTotaleProfittiSSN().doubleValue() > 0 && totaliGeneraliPrecedenti.getTotaleVenditeNetteSSN().doubleValue() > 0) {
            temp = totaliGeneraliPrecedenti.getTotaleProfittiSSN().doubleValue() / totaliGeneraliPrecedenti.getTotaleVenditeNetteSSN().doubleValue() * 100;
        }else
            temp = 0;
        row7.setTotaleSSNPrecedente(new BigDecimal(temp).setScale(CustomRoundingAndScaling.getScaleValue(),CustomRoundingAndScaling.getRoundingMode()));
        //row7.setDiffPercSSN(row7.getTotaleSSN(),row7.getTotaleSSNPrecedente());

        //margine vendite totali
        if(totaliGenerali.getTotaleProfitti().doubleValue() > 0 && totaliGenerali.getTotaleVenditeNette().doubleValue() > 0) {
            temp = totaliGenerali.getTotaleProfitti().doubleValue() / totaliGenerali.getTotaleVenditeNette().doubleValue() * 100;
        }else
            temp = 0;
        row7.setTotale(new BigDecimal(temp).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
        if(totaliGeneraliPrecedenti.getTotaleProfitti().doubleValue() > 0 && totaliGeneraliPrecedenti.getTotaleVenditeNette().doubleValue() > 0) {
            temp = totaliGeneraliPrecedenti.getTotaleProfitti().doubleValue() / totaliGeneraliPrecedenti.getTotaleVenditeNette().doubleValue() * 100;
        }else
            temp = 0;
        row7.setTotalePrecedente(new BigDecimal(temp).setScale(CustomRoundingAndScaling.getScaleValue(),CustomRoundingAndScaling.getRoundingMode()));
        //row7.setDiffPercTotale(row7.getTotale(),row7.getTotalePrecedente());

        rows.add(row7);

        //Valori Ricarico
        ConfrontoTotaliVenditeRowData row8 = new ConfrontoTotaliVenditeRowData();
        row8.setColDescrizione(descrizioni[7]);

        //ricarico vendite libere
        if (totaliGenerali.getTotaleProfittiLibere().doubleValue() > 0 && totaliGenerali.getTotaleCostiNettiLibere().doubleValue() > 0) {
            temp = totaliGenerali.getTotaleProfittiLibere().doubleValue() / totaliGenerali.getTotaleCostiNettiLibere().doubleValue() * 100;
        }else
            temp = 0;
        row8.setTotaleLibere(new BigDecimal(temp).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
        if (totaliGeneraliPrecedenti.getTotaleProfittiLibere().doubleValue() > 0 && totaliGeneraliPrecedenti.getTotaleCostiNettiLibere().doubleValue() > 0) {
            temp = totaliGeneraliPrecedenti.getTotaleProfittiLibere().doubleValue() / totaliGeneraliPrecedenti.getTotaleCostiNettiLibere().doubleValue() * 100;
        }else
            temp = 0;
        row8.setTotaleLiberePrec(new BigDecimal(temp).setScale(CustomRoundingAndScaling.getScaleValue(),CustomRoundingAndScaling.getRoundingMode()));
        //row8.setDiffPercLibere(row8.getTotaleLibere(),row8.getTotaleLiberePrec());

        //ricarico vendite ssn
        if (totaliGenerali.getTotaleProfittiSSN().doubleValue() > 0 && totaliGenerali.getTotaleCostiNettiSSN().doubleValue() > 0) {
            temp = totaliGenerali.getTotaleProfittiSSN().doubleValue() / totaliGenerali.getTotaleCostiNettiSSN().doubleValue() * 100;
        }else
            temp = 0;
        row8.setTotaleSSN(new BigDecimal(temp).setScale(CustomRoundingAndScaling.getScaleValue(),CustomRoundingAndScaling.getRoundingMode()));
        if (totaliGeneraliPrecedenti.getTotaleProfittiSSN().doubleValue() > 0 && totaliGeneraliPrecedenti.getTotaleCostiNettiSSN().doubleValue() > 0) {
            temp = totaliGeneraliPrecedenti.getTotaleProfittiSSN().doubleValue() / totaliGeneraliPrecedenti.getTotaleCostiNettiSSN().doubleValue() * 100;
        }
        row8.setTotaleSSNPrecedente(new BigDecimal(temp).setScale(CustomRoundingAndScaling.getScaleValue(),CustomRoundingAndScaling.getRoundingMode()));
        //row8.setDiffPercSSN(row8.getTotaleSSN(),row8.getTotaleSSNPrecedente());

        //ricarico vendite totali
        if (totaliGenerali.getTotaleProfitti().doubleValue() > 0 && totaliGenerali.getTotaleCostiNetti().doubleValue() > 0) {
            temp = totaliGenerali.getTotaleProfitti().doubleValue() / totaliGenerali.getTotaleCostiNetti().doubleValue() * 100;
        }else
            temp = 0;
        row8.setTotale(new BigDecimal(temp).setScale(CustomRoundingAndScaling.getScaleValue(), CustomRoundingAndScaling.getRoundingMode()));
        if (totaliGeneraliPrecedenti.getTotaleProfitti().doubleValue() > 0 && totaliGeneraliPrecedenti.getTotaleCostiNetti().doubleValue() > 0) {
            temp = totaliGeneraliPrecedenti.getTotaleProfitti().doubleValue()/totaliGeneraliPrecedenti.getTotaleCostiNetti().doubleValue()*100;
        }else
            temp = 0;
        row8.setTotalePrecedente(new BigDecimal(temp).setScale(CustomRoundingAndScaling.getScaleValue(),CustomRoundingAndScaling.getRoundingMode()));
        //row8.setDiffPercTotale(row8.getTotale(),row8.getTotalePrecedente());

        rows.add(row8);

    }

    public ArrayList<ConfrontoTotaliVenditeRowData> getRows(){
        return rows;
    }

    public Integer getGiorniLavorati(){
        return giorniLavorati;
    }

    public Integer getGiorniLavoratiPrec(){
        return giorniLavoratiPrec;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public Date getDateFromPrec() {
        return dateFromPrec;
    }

    public Date getDateToPrec() {
        return dateToPrec;
    }



}

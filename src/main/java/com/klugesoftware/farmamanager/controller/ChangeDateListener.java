package com.klugesoftware.farmamanager.controller;

import com.klugesoftware.farmamanager.utility.DateUtility;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.DatePicker;

import java.util.Date;

public class ChangeDateListener implements EventHandler<ActionEvent> {

    private ElencoMinsanController elencoMinsanController;


    public ChangeDateListener(ElencoMinsanController elencoMinsanController){
        this.elencoMinsanController = elencoMinsanController;
    }

    @Override
    public void handle(ActionEvent event) {
        Date dateFrom = elencoMinsanController.getDateFrom();
        Date dateTo = elencoMinsanController.getDateTo();

        if (DateUtility.getMese(dateFrom)==DateUtility.getMese(dateTo) && (dateFrom.before(dateTo) || dateFrom.equals(dateTo))) {
            elencoMinsanController.aggiornaTable(elencoMinsanController.getDateFrom(), elencoMinsanController.getDateTo());
        }
        else{
            DatePicker dataClicked  = (DatePicker)event.getSource();
            if(dataClicked.getUserData().equals("dataFrom")){
                elencoMinsanController.aggiornaTable(elencoMinsanController.getDateFrom(), DateUtility.ultimoGiornoDelMeseCorrente(elencoMinsanController.getDateFrom()));
            }else{
                if(dataClicked.getUserData().equals("dataTo")) {
                    elencoMinsanController.aggiornaTable(DateUtility.primoGiornoDelMeseCorrente(elencoMinsanController.getDateTo()), elencoMinsanController.getDateTo());
                }
            }
        }
    }
}

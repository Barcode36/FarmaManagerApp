package com.klugesoftware.farmamanager.controller;

import com.klugesoftware.farmamanager.DTO.ElencoTotaliGiornalieriRowData;
import com.klugesoftware.farmamanager.db.ElencoTotaliGiornalieriRowDataManager;
import com.klugesoftware.farmamanager.utility.DateUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;

import java.net.URL;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class SituazioneVenditeEProfittiLibereController implements Initializable {

    @FXML private DatePicker txtFldDataFrom;
    @FXML private DatePicker txtFldDataTo;
    @FXML private RadioButton rdtBtnVistaSettimanale;
    @FXML private RadioButton rdtBtnVistaMensile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void clickedDataFrom(ActionEvent event){

        Calendar myCal = Calendar.getInstance(Locale.ITALY);
        Date fromDate = DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataFrom.getEditor().getText());
        Date toDate = DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataTo.getEditor().getText());
        myCal.setTime(fromDate);
        if (rdtBtnVistaSettimanale.isSelected()){
            myCal.add(Calendar.DAY_OF_YEAR,6);
            toDate = myCal.getTime();
        }else
        if (rdtBtnVistaMensile.isSelected()){
            myCal.set(Calendar.DAY_OF_MONTH,myCal.getActualMaximum(Calendar.DAY_OF_MONTH));
            toDate = myCal.getTime();
        }
        ObservableList<ElencoTotaliGiornalieriRowData> elencoRighe = FXCollections.observableArrayList(ElencoTotaliGiornalieriRowDataManager.lookUpElencoTotaliGiornalieriBetweenDate((fromDate),(toDate)));
        if(!elencoRighe.isEmpty()){
            txtFldDataTo.getEditor().setText(DateUtility.converteDateToGUIStringDDMMYYYY(toDate));
            txtFldDataTo.setValue(LocalDate.of(myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH)+1,myCal.get(Calendar.DAY_OF_MONTH)));
            aggiornaTable(elencoRighe,fromDate,toDate);
        }else{
            //TODO: alert mancanza di movimenti
        }
    }

    @FXML
    private void clickedDataTo(ActionEvent event){

        Calendar myCal = Calendar.getInstance(Locale.ITALY);
        Date fromDate = DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataFrom.getEditor().getText());
        Date toDate = DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataTo.getEditor().getText());
        myCal.setTime(toDate);
        if (rdtBtnVistaSettimanale.isSelected()){
            myCal.set(Calendar.DAY_OF_WEEK,myCal.getActualMinimum(Calendar.DAY_OF_WEEK));
            fromDate = myCal.getTime();
        }else
        if (rdtBtnVistaMensile.isSelected()){
            myCal.set(Calendar.DAY_OF_MONTH,myCal.getActualMinimum(Calendar.DAY_OF_MONTH));
            fromDate = myCal.getTime();
        }
        ObservableList<ElencoTotaliGiornalieriRowData> elencoRighe = FXCollections.observableArrayList(ElencoTotaliGiornalieriRowDataManager.lookUpElencoTotaliGiornalieriBetweenDate((fromDate),(toDate)));
        if(!elencoRighe.isEmpty()){
            txtFldDataFrom.getEditor().setText(DateUtility.converteDateToGUIStringDDMMYYYY(fromDate));
            txtFldDataFrom.setValue(LocalDate.of(myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH)+1,myCal.get(Calendar.DAY_OF_MONTH)));
            aggiornaTable(elencoRighe,fromDate,toDate);
        }else{
            //TODO: alert mancanza di movimenti
        }


    }

    @FXML
    private void listenerEsciButton(ActionEvent event){
        System.exit(0);
    }

    @FXML
    private void clickedMese(ActionEvent event) {
        try {
            Calendar myCal = Calendar.getInstance(Locale.ITALY);
            Date fromDate = DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataFrom.getEditor().getText());
            Date toDate = DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataTo.getEditor().getText());
            myCal.setTime(fromDate);
            myCal.set(myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH),1);
            fromDate = myCal.getTime();
            myCal.set(Calendar.DAY_OF_MONTH,myCal.getActualMaximum(Calendar.DAY_OF_MONTH));
            toDate = myCal.getTime();
            aggiornaTableAndScene(fromDate,toDate,false);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void clickedSettimana(ActionEvent event) {
        try {
            Calendar myCal = Calendar.getInstance(Locale.ITALY);
            Date fromDate = DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataFrom.getEditor().getText());
            Date toDate = DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataTo.getEditor().getText());
            myCal.setTime(fromDate);
            myCal.set(myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH),1);
            fromDate = myCal.getTime();
            myCal.add(Calendar.DAY_OF_YEAR,6);
            toDate = myCal.getTime();
            aggiornaTableAndScene(fromDate,toDate,true);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}

package com.klugesoftware.farmamanager.controller;

import com.klugesoftware.farmamanager.utility.DateUtility;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
/**
 * Questa classe si occupa di intercettare gli eventi dei
 * - DatePicker dataFrome e dataTo
 * - RadioButton rdbtVistaMese/Settimana
 *
 * Tramite l'uso di setDataUser viene scelto il tipo ti source dell'evento;
 * di seguito i dataUser:
 * - per i DatePicker:
 *              - dataFrom
 *              - dataTo
 * - per i RadioButton:
 *              - vistaMensile
 *              - vistaSettimanale
 *
 */
public class ChangeDateAndViewListener implements EventHandler<ActionEvent> {

    private VenditeEProfittiController controller;
    public ChangeDateAndViewListener(VenditeEProfittiController controller){
        this.controller = controller;
    }

    @Override
    public void handle(ActionEvent event) {

        if(event.getSource() instanceof  DatePicker){
            DatePicker dateControl = (DatePicker)event.getSource();
            if(dateControl.getUserData().equals("dataFrom")){
                setDataFrom();
            }else{
                setDataTo();
            }
        }else
            if (event.getSource() instanceof RadioButton){
                RadioButton rdbt = (RadioButton)event.getSource();
                if (rdbt.getUserData().equals("vistaMensile")){
                    setVistaMensile();
                }else{
                    setVistaSettimanale();
                }

            }

    }

    private void setDataFrom(){
        Calendar myCal = Calendar.getInstance(Locale.ITALY);
        Date fromDate = controller.getDateFrom();
        Date toDate = controller.getDateTo();
        myCal.setTime(fromDate);
        if (controller.getRdbtVistaSettimanale().isSelected()){
            myCal.add(Calendar.DAY_OF_YEAR,6);
            toDate = myCal.getTime();
        }else
        if (controller.getRdbtVistaMensile().isSelected()){
            myCal.set(Calendar.DAY_OF_MONTH,myCal.getActualMaximum(Calendar.DAY_OF_MONTH));
            toDate = myCal.getTime();
        }
        controller.aggiornaTable(fromDate,toDate);
    }

    private void setDataTo(){
        Calendar myCal = Calendar.getInstance(Locale.ITALY);
        Date fromDate = controller.getDateFrom();
        Date toDate = controller.getDateTo();
        myCal.setTime(toDate);
        if (controller.getRdbtVistaSettimanale().isSelected()){
            myCal.set(Calendar.DAY_OF_WEEK,myCal.getActualMinimum(Calendar.DAY_OF_WEEK));
            fromDate = myCal.getTime();
        }else
        if (controller.getRdbtVistaMensile().isSelected()){
            myCal.set(Calendar.DAY_OF_MONTH,myCal.getActualMinimum(Calendar.DAY_OF_MONTH));
            fromDate = myCal.getTime();
        }
        controller.aggiornaTable(fromDate,toDate);
    }

    private void setVistaMensile(){
        try {
            Calendar myCal = Calendar.getInstance(Locale.ITALY);
            Date fromDate = controller.getDateFrom();
            Date toDate = controller.getDateTo();
            myCal.setTime(fromDate);
            myCal.set(myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH),1);
            fromDate = myCal.getTime();
            myCal.set(Calendar.DAY_OF_MONTH,myCal.getActualMaximum(Calendar.DAY_OF_MONTH));
            toDate = myCal.getTime();
            controller.aggiornaTableAndScene(fromDate,toDate,false);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private void setVistaSettimanale(){
        try {
            Calendar myCal = Calendar.getInstance(Locale.ITALY);
            Date fromDate = controller.getDateFrom();
            Date toDate = controller.getDateTo();
            myCal.setTime(fromDate);
            myCal.set(myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH),1);
            fromDate = myCal.getTime();
            myCal.add(Calendar.DAY_OF_YEAR,6);
            toDate = myCal.getTime();
            controller.aggiornaTableAndScene(fromDate,toDate,true);

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}

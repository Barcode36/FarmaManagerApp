package com.klugesoftware.farmamanager.controller;

import com.klugesoftware.farmamanager.DTO.ElencoTotaliGiornalieriRowData;
import com.klugesoftware.farmamanager.db.ElencoTotaliGiornalieriRowDataManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ChangePeriodListener implements EventHandler<ActionEvent> {
    private PeriodToShow period;
    private PeriodDirection periodDirection;
    private VenditeEProfittiController controller;

    ChangePeriodListener(PeriodToShow period, PeriodDirection periodDirection,VenditeEProfittiController controller){
        this.period = period;
        this.periodDirection = periodDirection;
        this.controller = controller;
    }

    @Override
    public void handle(ActionEvent event) {
        Calendar myCal = Calendar.getInstance(Locale.ITALY);
        Date fromDate = controller.getDateFrom();
        Date toDate = controller.getDateTo();
        myCal.setTime(fromDate);
        switch(period){
            case SETTIMANA:
                switch(periodDirection){
                    case BACK:
                        myCal.add(Calendar.DAY_OF_YEAR,-7);
                        fromDate = myCal.getTime();
                        myCal.add(Calendar.DAY_OF_YEAR,6);
                        toDate = myCal.getTime();
                        break;
                    case FORWARD:
                        myCal.add(Calendar.DAY_OF_YEAR,7);
                        fromDate = myCal.getTime();
                        myCal.add(Calendar.DAY_OF_YEAR,6);
                        toDate = myCal.getTime();
                        break;
                    case CURRENT:
                        break;
                }
                break;
            case MESE:
                switch (periodDirection){
                    case BACK:
                        myCal.set(myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH),1);
                        myCal.add(Calendar.MONTH,-1);
                        fromDate = myCal.getTime();
                        myCal.set(Calendar.DAY_OF_MONTH,myCal.getActualMaximum(Calendar.DAY_OF_MONTH));
                        toDate = myCal.getTime();
                        break;
                    case FORWARD:
                        myCal.set(myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH),1);
                        myCal.add(Calendar.MONTH,1);
                        fromDate = myCal.getTime();
                        myCal.set(Calendar.DAY_OF_MONTH,myCal.getActualMaximum(Calendar.DAY_OF_MONTH));
                        toDate = myCal.getTime();
                        break;
                    case CURRENT:
                        break;
                }

        }

        controller.aggiornaTable(fromDate,toDate);
    }

}

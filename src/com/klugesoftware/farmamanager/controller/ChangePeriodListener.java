package com.klugesoftware.farmamanager.controller;

import com.klugesoftware.farmamanager.utility.DateUtility;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Questa classe si occupa di intercettare gli eventi legati ai Button di direzione:
 * - direzione Back decrementa la settimana oppure il mese
 * - direzione Forward incrementa la settimana oppure il mese.
 *
 * In fase di initialize del Controller si instanzia un oggetto per ogni button
 * cioè un listener per il buttonBack ed uno per il listener per il buttonForward.
 */
public class ChangePeriodListener implements EventHandler<ActionEvent> {
    private PeriodToShow period;
    private PeriodDirection periodDirection;
    private VenditeEProfittiController controller;

    ChangePeriodListener(PeriodToShow period, PeriodDirection periodDirection, VenditeEProfittiController controller){
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

    public PeriodToShow getPeriod() {
        return period;
    }

    public PeriodDirection getPeriodDirection() {
        return periodDirection;
    }

    public void setPeriodDirection(PeriodDirection periodDirection) {
        this.periodDirection = periodDirection;
    }

    public void setPeriod(PeriodToShow period) {
        this.period = period;
    }

}

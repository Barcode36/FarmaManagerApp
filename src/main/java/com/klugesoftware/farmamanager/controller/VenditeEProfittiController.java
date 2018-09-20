package com.klugesoftware.farmamanager.controller;

import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;

import java.util.Date;

abstract public class VenditeEProfittiController {

    abstract public Date getDateFrom();
    abstract public Date getDateTo();
    abstract public RadioButton getRdbtVistaMensile();
    abstract public RadioButton getRdbtVistaSettimanale();
    abstract public void aggiornaTableAndScene(Date dateFrom,Date dateTo, boolean vistaSettimanale);
    abstract public void aggiornaTable(Date dateFrom,Date dateTo);
}

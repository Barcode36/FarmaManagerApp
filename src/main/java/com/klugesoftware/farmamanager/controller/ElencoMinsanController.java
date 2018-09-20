package com.klugesoftware.farmamanager.controller;

import javafx.scene.control.DatePicker;

import java.util.Date;

abstract public class ElencoMinsanController {

    abstract  public Date getDateFrom();
    abstract  public Date getDateTo();
    abstract  public void aggiornaTable(Date dateFrom,Date dateTo);
}

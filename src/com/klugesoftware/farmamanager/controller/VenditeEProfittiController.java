package com.klugesoftware.farmamanager.controller;

import java.util.Date;

abstract public class VenditeEProfittiController {

    abstract public Date getDateFrom();
    abstract public Date getDateTo();
    abstract public void aggiornaTableAndScene(Date dateFrom,Date dateTo, boolean vistaSettimanale);
    abstract public void aggiornaTable(Date dateFrom,Date dateTo);
}

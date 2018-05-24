package com.klugesoftware.farmamanager.controller;

import com.klugesoftware.farmamanager.DTO.ConfrontoTotaliVenditeRowData;
import com.klugesoftware.farmamanager.DTO.ConfrontoTotaliVenditeRows;
import com.klugesoftware.farmamanager.utility.DateUtility;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class ConfrontoTotaliVenditeGraficoController implements Initializable{

    @FXML private BarChart<String,Number> graficoConfronto;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void initGrafico(ConfrontoTotaliVenditeRows confrontoTotaliVenditeRows){


        String periodoAttuale = "periodo attuale: "+DateUtility.converteDateToGUIStringDDMMYYYY(confrontoTotaliVenditeRows.getDateFrom())+"-"+DateUtility.converteDateToGUIStringDDMMYYYY(confrontoTotaliVenditeRows.getDateTo());
        String periodoPrecedente = "periodo precedente: "+DateUtility.converteDateToGUIStringDDMMYYYY(confrontoTotaliVenditeRows.getDateFromPrec())+"-"+DateUtility.converteDateToGUIStringDDMMYYYY(confrontoTotaliVenditeRows.getDateToPrec());
        String profittoLibere = "ProfittoLibere";
        String margineLibere = "MargineLibere";
        String ricaricoLibere = "RicaricoLibere";
        String profittoSSN = "ProfittoSSN";
        String margineSSN = "MargineSSN";
        String ricaricoSSN = "RicaricoSSN";
        String profittoTotale = "ProfittoTotale";
        String margineTotale = "MargineTotale";
        String ricaricoTotale = "RicaricoTotale";



        ArrayList<ConfrontoTotaliVenditeRowData> rowsData = confrontoTotaliVenditeRows.getRows();
        XYChart.Series seriesPeriodoAttuale = new XYChart.Series();
        seriesPeriodoAttuale.setName(periodoAttuale);
        seriesPeriodoAttuale.getData().add(new XYChart.Data<>(profittoLibere,rowsData.get(5).getTotaleLibere()));
        //seriesPeriodoAttuale.getData().add(new XYChart.Data<>(margineLibere,rowsData.get(6).getTotaleLibere()));
        //seriesPeriodoAttuale.getData().add(new XYChart.Data<>(ricaricoLibere,rowsData.get(7).getTotaleLibere()));
        seriesPeriodoAttuale.getData().add(new XYChart.Data<>(profittoSSN,rowsData.get(5).getTotaleSSN()));
        //seriesPeriodoAttuale.getData().add(new XYChart.Data<>(margineSSN,rowsData.get(6).getTotaleSSN()));
        //seriesPeriodoAttuale.getData().add(new XYChart.Data<>(ricaricoSSN,rowsData.get(7).getTotaleSSN()));
        seriesPeriodoAttuale.getData().add(new XYChart.Data<>(profittoTotale,rowsData.get(5).getTotale()));
        //seriesPeriodoAttuale.getData().add(new XYChart.Data<>(margineTotale,rowsData.get(6).getTotale()));
        //seriesPeriodoAttuale.getData().add(new XYChart.Data<>(ricaricoTotale,rowsData.get(7).getTotale()));


        XYChart.Series seriesPeriodoPrecedente = new XYChart.Series();
        seriesPeriodoPrecedente.setName(periodoPrecedente);
        seriesPeriodoPrecedente.getData().add(new XYChart.Data<>(profittoLibere,rowsData.get(5).getTotaleLiberePrec()));
        //seriesPeriodoPrecedente.getData().add(new XYChart.Data<>(margineLibere,rowsData.get(6).getTotaleLiberePrec()));
        //seriesPeriodoPrecedente.getData().add(new XYChart.Data<>(ricaricoLibere,rowsData.get(7).getTotaleLiberePrec()));
        seriesPeriodoPrecedente.getData().add(new XYChart.Data<>(profittoSSN,rowsData.get(5).getTotaleSSNPrecedente()));
        //seriesPeriodoPrecedente.getData().add(new XYChart.Data<>(margineSSN,rowsData.get(6).getTotaleSSNPrecedente()));
        //seriesPeriodoPrecedente.getData().add(new XYChart.Data<>(ricaricoSSN,rowsData.get(7).getTotaleSSNPrecedente()));
        seriesPeriodoPrecedente.getData().add(new XYChart.Data<>(profittoTotale,rowsData.get(5).getTotalePrecedente()));
        //seriesPeriodoPrecedente.getData().add(new XYChart.Data<>(margineTotale,rowsData.get(6).getTotalePrecedente()));
        //seriesPeriodoPrecedente.getData().add(new XYChart.Data<>(ricaricoTotale,rowsData.get(7).getTotalePrecedente()));

        graficoConfronto.getData().addAll(seriesPeriodoPrecedente,seriesPeriodoAttuale);


    }

}

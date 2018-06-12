package com.klugesoftware.farmamanager.controller;

import com.klugesoftware.farmamanager.DTO.DettaglioTotaliVenditeRowData;
import com.klugesoftware.farmamanager.IOFunctions.EstrazioneDatiGeneraliVendite;
import com.klugesoftware.farmamanager.IOFunctions.TotaliGeneraliVenditaEstratti;
import com.klugesoftware.farmamanager.IOFunctions.TotaliGeneraliVenditaEstrattiGiornalieri;
import com.klugesoftware.farmamanager.db.TotaliGeneraliVenditaEstrattiGiornalieriDAOManager;
import com.klugesoftware.farmamanager.utility.DateUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.*;


public class DettaglioVenditeEProfittiController extends VenditeEProfittiController implements Initializable {

    @FXML private DatePicker txtFldDataFrom;
    @FXML private DatePicker txtFldDataTo;
    @FXML private Button btnBack;
    @FXML private Button btnForward;
    @FXML private Label lblPeriodo;
    @FXML private Label lblTitle;
    @FXML private RadioButton rdtBtnVistaSettimanale;
    @FXML private RadioButton rdtBtnVistaMensile;
    @FXML private TableView<DettaglioTotaliVenditeRowData> tableDettaglioTotali;
    @FXML private TableColumn<DettaglioTotaliVenditeRowData,String> colDescrizione;
    @FXML private TableColumn<DettaglioTotaliVenditeRowData,BigDecimal> colTotaliLibere;
    @FXML private TableColumn<DettaglioTotaliVenditeRowData,BigDecimal> colTotaliSSN;
    @FXML private TableColumn<DettaglioTotaliVenditeRowData,BigDecimal> colTotale;
    @FXML private PieChart graficoComposizioneProfitto;
    private ChangePeriodListener changePeriodListenerBack;
    private ChangePeriodListener changePeriodListenerNext;
    private ChangeDateAndViewListener changeDateListener;



    @Override
    public void initialize(URL location, ResourceBundle resources) {


        changeDateListener = new ChangeDateAndViewListener(this);
        rdtBtnVistaMensile.setUserData("vistaMensile");
        rdtBtnVistaSettimanale.setUserData("vistaSettimanale");
        rdtBtnVistaSettimanale.setOnAction(changeDateListener);
        rdtBtnVistaMensile.setOnAction(changeDateListener);

        txtFldDataFrom.setUserData("dataFrom");
        txtFldDataTo.setUserData("dataTo");

        txtFldDataFrom.setOnAction(changeDateListener);
        txtFldDataTo.setOnAction(changeDateListener);

        changePeriodListenerBack = new ChangePeriodListener(PeriodToShow.SETTIMANA,PeriodDirection.BACK,this);
        changePeriodListenerNext = new ChangePeriodListener(PeriodToShow.SETTIMANA,PeriodDirection.FORWARD,this);
        btnBack.setOnAction(changePeriodListenerBack);
        btnForward.setOnAction(changePeriodListenerNext);


        colDescrizione.setCellValueFactory(new PropertyValueFactory<DettaglioTotaliVenditeRowData,String>("descrizione"));
        colTotaliLibere.setCellValueFactory(new PropertyValueFactory<DettaglioTotaliVenditeRowData,BigDecimal>("totaleVenditeLibere"));
        colTotaliSSN.setCellValueFactory(new PropertyValueFactory<DettaglioTotaliVenditeRowData,BigDecimal>("totaleVenditeSSN"));
        colTotale.setCellValueFactory(new PropertyValueFactory<DettaglioTotaliVenditeRowData,BigDecimal>("totaleVendite"));

        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.ITALY);
        DecimalFormat df = (DecimalFormat)nf;

        colTotaliLibere.setCellFactory(new Callback<TableColumn<DettaglioTotaliVenditeRowData, BigDecimal>, TableCell<DettaglioTotaliVenditeRowData, BigDecimal>>() {
            @Override
            public TableCell<DettaglioTotaliVenditeRowData, BigDecimal> call(TableColumn<DettaglioTotaliVenditeRowData, BigDecimal> param) {
                return new TableCell<DettaglioTotaliVenditeRowData, BigDecimal>(){
                    @Override
                    public void updateItem(BigDecimal item,boolean empty){
                        super.updateItem(item, empty);
                        if (item == null || empty){
                            setText(null);
                        }else{
                            if(item.doubleValue() > 0)
                                setText(nf.format(item));
                            else
                            {
                                item = item.multiply(new BigDecimal(-1));
                                String temp = item.toString().replace(".",",");
                                setText(temp + " %");
                            }
                        }
                    }
                };
            }
        });


        colTotaliSSN.setCellFactory(new Callback<TableColumn<DettaglioTotaliVenditeRowData, BigDecimal>, TableCell<DettaglioTotaliVenditeRowData, BigDecimal>>() {
            @Override
            public TableCell<DettaglioTotaliVenditeRowData, BigDecimal> call(TableColumn<DettaglioTotaliVenditeRowData, BigDecimal> param) {
                return new TableCell<DettaglioTotaliVenditeRowData, BigDecimal>(){
                    @Override
                    public void updateItem(BigDecimal item,boolean empty){
                        super.updateItem(item, empty);
                        if (item == null || empty){
                            setText(null);
                        }else{
                            if(item.doubleValue() > 0)
                                setText(nf.format(item));
                            else
                            {
                                item = item.multiply(new BigDecimal(-1));
                                String temp = item.toString().replace(".",",");
                                setText(temp + " %");
                            }
                        }
                    }
                };
            }
        });

        colTotale.setCellFactory(new Callback<TableColumn<DettaglioTotaliVenditeRowData, BigDecimal>, TableCell<DettaglioTotaliVenditeRowData, BigDecimal>>() {
            @Override
            public TableCell<DettaglioTotaliVenditeRowData, BigDecimal> call(TableColumn<DettaglioTotaliVenditeRowData, BigDecimal> param) {
                return new TableCell<DettaglioTotaliVenditeRowData, BigDecimal>(){
                    @Override
                    public void updateItem(BigDecimal item,boolean empty){
                        super.updateItem(item, empty);
                        if (item == null || empty){
                            setText(null);
                        }else{
                            if(item.doubleValue() > 0)
                                setText(nf.format(item));
                            else {
                                item = item.multiply(new BigDecimal(-1));
                                String temp = item.toString().replace(".",",");
                                setText(temp + " %");
                            }
                        }
                    }
                };
            }
        });

    }

    private void initTable(Date dateFrom,Date dateTo){

        /* Codice che estrae i Totali dai singoli movimenti dei prodotti

        TotaliGeneraliVenditaEstratti totaliGenerali = new TotaliGeneraliVenditaEstratti();
        EstrazioneDatiGeneraliVendite estrazioneDati = new EstrazioneDatiGeneraliVendite();
        totaliGenerali = estrazioneDati.estraiDatiTotali(dateFrom,dateTo);
        */

        TotaliGeneraliVenditaEstratti totaliGenerali = new TotaliGeneraliVenditaEstratti();
        ArrayList<TotaliGeneraliVenditaEstrattiGiornalieri> elencoTotaliGiornalieri = TotaliGeneraliVenditaEstrattiGiornalieriDAOManager.findbetweenDate(dateFrom,dateTo);
        totaliGenerali.addElencoTotaliGeneraliVenditaEstrattiGiornalieri(elencoTotaliGiornalieri);


        //TODO: messaggio se movimenti mancanti....

        ObservableList<DettaglioTotaliVenditeRowData> data = FXCollections.observableArrayList(
                new DettaglioTotaliVenditeRowData("Totale Vendite\n Lorde\n",totaliGenerali.getTotaleVenditeLorde(),totaliGenerali.getTotaleVenditeLordeLibere(),totaliGenerali.getTotaleVenditeLordeSSN()),
                new DettaglioTotaliVenditeRowData("Totale Sconti\n",totaliGenerali.getTotaleSconti(),totaliGenerali.getTotaleScontiLibere(),totaliGenerali.getTotaleScontiSSN()),
                new DettaglioTotaliVenditeRowData("Totale Vendite\n al netto degli\n sconti\n",totaliGenerali.getTotaleVenditeNettoSconti(),totaliGenerali.getTotaleVenditeNettoScontiLibere(),totaliGenerali.getTotaleVenditeNettoScontiSSN()),
                new DettaglioTotaliVenditeRowData("Totale Vendite\n netto iva e sconti\n",totaliGenerali.getTotaleVenditeNette(),totaliGenerali.getTotaleVenditeNetteLibere(),totaliGenerali.getTotaleVenditeNetteSSN()),
                new DettaglioTotaliVenditeRowData("Totale Costi\n al netto iva\n",totaliGenerali.getTotaleCostiNetti(),totaliGenerali.getTotaleCostiNettiLibere(),totaliGenerali.getTotaleCostiNettiSSN()),
                new DettaglioTotaliVenditeRowData("Totale Profitti\n",totaliGenerali.getTotaleProfitti(),totaliGenerali.getTotaleProfittiLibere(),totaliGenerali.getTotaleProfittiSSN()),
                new DettaglioTotaliVenditeRowData("Margine\n",totaliGenerali.getMargineMedio().multiply(new BigDecimal(-1)),totaliGenerali.getMargineMedioLibere().multiply(new BigDecimal(-1)),totaliGenerali.getMargineMedioSSN().multiply(new BigDecimal(-1))),
                new DettaglioTotaliVenditeRowData("Ricarico\n",totaliGenerali.getRicaricoMedio().multiply(new BigDecimal(-1)),totaliGenerali.getRicaricoMedioLibere().multiply(new BigDecimal(-1)),totaliGenerali.getRicaricoMedioSSN().multiply(new BigDecimal(-1)))
        );

        tableDettaglioTotali.getItems().clear();
        tableDettaglioTotali.getItems().setAll(data);
        tableDettaglioTotali.refresh();

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Profitto Vendite SSN", totaliGenerali.getTotaleProfittiSSN().doubleValue()),
                        new PieChart.Data("Profitto Vendite Libere", totaliGenerali.getTotaleProfittiLibere().doubleValue())
                        );
        graficoComposizioneProfitto.getData().clear();
        graficoComposizioneProfitto.setData(pieChartData);

        //TODO: terminare la visualizzazione delle percentali all'interno del grafico a torta...
        Label caption = new Label("");
        caption.setTextFill(Color.DARKORANGE);
        caption.setStyle("-fx-font: 24 arial;");

        for (PieChart.Data dataTemp : graficoComposizioneProfitto.getData()) {
            dataTemp.getNode().addEventHandler(MouseEvent.ANY,
                    new EventHandler<MouseEvent>() {
                        @Override public void handle(MouseEvent e) {
                            caption.setTranslateX(e.getSceneX());
                            caption.setTranslateY(e.getSceneY());
                            caption.setText(String.valueOf(dataTemp.getPieValue()) + "%");
                        }
                    });
        }

    }

    @FXML
    private void listenerEsciButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/SituazioneVenditeEProfitti.fxml"));
        Parent parent = (Parent)fxmlLoader.load();
        SituazioneVenditeEProfittiController controller = fxmlLoader.getController();
        boolean vistaSettimanale = true;
        if (rdtBtnVistaMensile.isSelected())
            vistaSettimanale = false;
        controller.aggiornaTableAndScene(DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataFrom.getEditor().getText()),DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataTo.getEditor().getText()),vistaSettimanale);
        Scene scene = new Scene(parent);
        Stage app_stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(scene);
        app_stage.show();
    }


    /*
    aggiorna i dati della tableview in fuzione dell'intervallo di date; setta il giusto valore ai campi DatePicker, inoltre
    setta il giusto valore dei RadioButton, Label atc..in funzione del periodo di riferimento( Settimanale o Mensile)
     */
    @Override
    public void aggiornaTableAndScene(Date fromDate,Date toDate,boolean vistaSettimanale){
        Calendar myCal = Calendar.getInstance(Locale.ITALY);
        myCal.setTime(fromDate);
        txtFldDataFrom.setValue(LocalDate.of(myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH)+1,myCal.get(Calendar.DAY_OF_MONTH)));
        myCal.setTime(toDate);
        txtFldDataTo.setValue(LocalDate.of(myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH)+1,myCal.get(Calendar.DAY_OF_MONTH)));

        txtFldDataFrom.getEditor().setText(DateUtility.converteDateToGUIStringDDMMYYYY(fromDate));
        txtFldDataTo.getEditor().setText(DateUtility.converteDateToGUIStringDDMMYYYY(toDate));

        initTable(fromDate,toDate);
        if (vistaSettimanale){
            changePeriodListenerBack.setPeriod(PeriodToShow.SETTIMANA);
            changePeriodListenerNext.setPeriod(PeriodToShow.SETTIMANA);
            rdtBtnVistaSettimanale.setSelected(true);
            lblPeriodo.setText(" Settimana");
        }else{
            changePeriodListenerBack.setPeriod(PeriodToShow.MESE);
            changePeriodListenerNext.setPeriod(PeriodToShow.MESE);
            lblPeriodo.setText("    Mese  ");
            rdtBtnVistaMensile.setSelected(true);
        }
    }

    @Override
    public void aggiornaTable(Date dateFrom, Date dateTo) {

        Calendar myCal = Calendar.getInstance(Locale.ITALY);
        myCal.setTime(dateFrom);
        txtFldDataFrom.setOnAction(null);
        txtFldDataTo.setOnAction(null);
        txtFldDataFrom.setValue(LocalDate.of(myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH)+1,myCal.get(Calendar.DAY_OF_MONTH)));
        myCal.setTime(dateTo);
        txtFldDataTo.setValue(LocalDate.of(myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH)+1,myCal.get(Calendar.DAY_OF_MONTH)));

        txtFldDataFrom.getEditor().setText(DateUtility.converteDateToGUIStringDDMMYYYY(dateFrom));
        txtFldDataTo.getEditor().setText(DateUtility.converteDateToGUIStringDDMMYYYY(dateTo));
        txtFldDataFrom.setOnAction(changeDateListener);
        txtFldDataTo.setOnAction(changeDateListener);

        initTable(dateFrom,dateTo);

        //FIXME: capire perchè è necessario "eliminare" listener e poi "riaggiugnerlo"...


    }


    public Date getDateFrom(){
        return DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataFrom.getEditor().getText());
    }

    public Date getDateTo(){
        return DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataTo.getEditor().getText());
    }

    public RadioButton getRdbtVistaMensile(){
        return rdtBtnVistaMensile;
    }
    public RadioButton getRdbtVistaSettimanale(){
        return  rdtBtnVistaSettimanale;
    }


    @FXML
    private void confrontaPeriodoClicked(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/ConfrontoTotaliVendite.fxml"));
        Parent parent = (Parent)fxmlLoader.load();
        ConfrontoTotaliVenditeController controller = fxmlLoader.getController();
        controller.setIntervalloMensile(getDateFrom(),getDateTo(),true);
        Scene scene = new Scene(parent);
        Stage app_stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(scene);
        app_stage.show();

    }

    @FXML
    private void analisiLibereClicked(ActionEvent event){

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/SituazioneVenditeEProfittiLibere.fxml"));
            Parent parent = (Parent) fxmlLoader.load();
            SituazioneVenditeEProfittiLibereController controller = fxmlLoader.getController();
            Scene scene = new Scene(parent);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.hide();
            app_stage.setScene(scene);
            app_stage.show();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}

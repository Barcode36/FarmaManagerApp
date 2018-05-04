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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.*;


public class DettaglioVenditeEProfittiController implements Initializable {

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



    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
                            setText(nf.format(item));
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
                            setText(nf.format(item));
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
                            setText(nf.format(item));
                        }
                    }
                };
            }
        });

       // initTable(DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataFrom.getEditor().getText()),DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataTo.getEditor().getText()));

    }

    private void initTable(Date dateFrom,Date dateTo){

        TotaliGeneraliVenditaEstratti totaliGenerali = new TotaliGeneraliVenditaEstratti();
        EstrazioneDatiGeneraliVendite estrazioneDati = new EstrazioneDatiGeneraliVendite();
        totaliGenerali = estrazioneDati.estraiDatiTotali(dateFrom,dateTo);

        /*
        TotaliGeneraliVenditaEstratti totaliGenerali = new TotaliGeneraliVenditaEstratti();
        ArrayList<TotaliGeneraliVenditaEstrattiGiornalieri> elencoTotaliGiornalieri = TotaliGeneraliVenditaEstrattiGiornalieriDAOManager.findbetweenDate(dateFrom,dateTo);
        totaliGenerali.addElencoTotaliGeneraliVenditaEstrattiGiornalieri(elencoTotaliGiornalieri);
        */

        ObservableList<DettaglioTotaliVenditeRowData> data = FXCollections.observableArrayList(
                new DettaglioTotaliVenditeRowData("Totale Vendite Lorde",totaliGenerali.getTotaleVenditeLorde(),totaliGenerali.getTotaleVenditeLordeLibere(),totaliGenerali.getTotaleVenditeLordeSSN()),
                new DettaglioTotaliVenditeRowData("Totale Sconti",totaliGenerali.getTotaleSconti(),totaliGenerali.getTotaleScontiLibere(),totaliGenerali.getTotaleScontiSSN()),
                new DettaglioTotaliVenditeRowData("Totale Vendite al netto degli sconti",totaliGenerali.getTotaleVenditeNettoSconti(),totaliGenerali.getTotaleVenditeNettoScontiLibere(),totaliGenerali.getTotaleVenditeNettoScontiSSN()),
                new DettaglioTotaliVenditeRowData("Totale Vendite netto iva e sconti",totaliGenerali.getTotaleVenditeNette(),totaliGenerali.getTotaleVenditeNetteLibere(),totaliGenerali.getTotaleVenditeNetteSSN()),
                new DettaglioTotaliVenditeRowData("Totale Costi al netto iva",totaliGenerali.getTotaleCostiNetti(),totaliGenerali.getTotaleCostiNettiLibere(),totaliGenerali.getTotaleCostiNettiSSN()),
                new DettaglioTotaliVenditeRowData("Totale Profitti",totaliGenerali.getTotaleProfitti(),totaliGenerali.getTotaleProfittiLibere(),totaliGenerali.getTotaleProfittiSSN()),
                new DettaglioTotaliVenditeRowData("Margine",totaliGenerali.getMargineMedio(),totaliGenerali.getMargineMedioLibere(),totaliGenerali.getMargineMedioSSN()),
                new DettaglioTotaliVenditeRowData("Ricarico",totaliGenerali.getRicaricoMedio(),totaliGenerali.getRicaricoMedioLibere(),totaliGenerali.getRicaricoMedioSSN())
        );

        tableDettaglioTotali.getItems().clear();
        tableDettaglioTotali.getItems().setAll(data);
        tableDettaglioTotali.refresh();

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

    @FXML
    private void clickedMese(ActionEvent event){

    }

    @FXML
    private void clickedSettimana(ActionEvent event){

    }

    /*
    aggiorna i dati della tableview in fuzione dell'intervallo di date; setta il giusto valore ai campi DatePicker, inoltre
    setta il giusto valore dei RadioButton, Label atc..in funzione del periodo di riferimento( Settimanale o Mensile)
     */
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
            rdtBtnVistaSettimanale.setSelected(true);
            lblPeriodo.setText(" Settimana");
            lblTitle.setText("Situazione Vendite e Profitti Settimanale");
        }else{
            lblPeriodo.setText("    Mese  ");
            lblTitle.setText("Situazione Vendite e Profitti Mensile");
            rdtBtnVistaMensile.setSelected(true);
        }
    }

}

package com.klugesoftware.farmamanager.controller;

import com.klugesoftware.farmamanager.DTO.ElencoTotaliGiornalieriRowData;
import com.klugesoftware.farmamanager.db.ElencoTotaliGiornalieriRowDataManager;
import com.klugesoftware.farmamanager.db.ImportazioniDAOManager;
import com.klugesoftware.farmamanager.model.Importazioni;
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
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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


public class SituazioneVenditeEProfittiController implements Initializable {

    @FXML private TableView<ElencoTotaliGiornalieriRowData> tableVenditeEProfittiTotali;
    @FXML private TableColumn<ElencoTotaliGiornalieriRowData,String> colData;
    @FXML private TableColumn<ElencoTotaliGiornalieriRowData,BigDecimal> colTotaleVendite;
    @FXML private TableColumn<ElencoTotaliGiornalieriRowData,BigDecimal> colTotaleProfitti;
    @FXML private AreaChart<String,Number> graficoVenditeEProfitti;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;
    @FXML private DatePicker txtFldDataFrom;
    @FXML private DatePicker txtFldDataTo;
    @FXML private Label lblTotVendite;
    @FXML private Label lblTotProfitti;
    @FXML private Button btnBack;
    @FXML private Button btnForward;
    @FXML private Label lblPeriodo;
    @FXML private Label lblTitle;
    @FXML private RadioButton rdtBtnVistaSettimanale;
    @FXML private RadioButton rdtBtnVistaMensile;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnBack.setOnAction(new ChangePeriodListener(PeriodToShow.SETTIMANA,PeriodDirection.BACK));
        btnForward.setOnAction(new ChangePeriodListener(PeriodToShow.SETTIMANA,PeriodDirection.FORWARD));
        colData.setCellValueFactory(new PropertyValueFactory<ElencoTotaliGiornalieriRowData,String>("data"));
        colTotaleVendite.setCellValueFactory(new PropertyValueFactory<ElencoTotaliGiornalieriRowData,BigDecimal>("totaleVenditeLorde"));
        colTotaleProfitti.setCellValueFactory(new PropertyValueFactory<ElencoTotaliGiornalieriRowData,BigDecimal>("totaleProfitti"));

        colData.setCellFactory(e ->{ return new TableCell<ElencoTotaliGiornalieriRowData,String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(DateUtility.converteGUIStringDDMMYYYYToNameDayOfWeek(item));
                }
            }
        };
        });

        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.ITALY);
        DecimalFormat df = (DecimalFormat)nf;

        colTotaleProfitti.setCellFactory(new Callback<TableColumn<ElencoTotaliGiornalieriRowData, BigDecimal>, TableCell<ElencoTotaliGiornalieriRowData, BigDecimal>>() {
            @Override
            public TableCell<ElencoTotaliGiornalieriRowData, BigDecimal> call(TableColumn<ElencoTotaliGiornalieriRowData, BigDecimal> e) {
                return new TableCell<ElencoTotaliGiornalieriRowData, BigDecimal>() {
                    @Override
                    public void updateItem(BigDecimal item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(df.format(item));
                        }
                    }
                };
            }
        });

        colTotaleVendite.setCellFactory(new Callback<TableColumn<ElencoTotaliGiornalieriRowData, BigDecimal>, TableCell<ElencoTotaliGiornalieriRowData, BigDecimal>>() {
            @Override
            public TableCell<ElencoTotaliGiornalieriRowData, BigDecimal> call(TableColumn<ElencoTotaliGiornalieriRowData, BigDecimal> e) {
                return new TableCell<ElencoTotaliGiornalieriRowData, BigDecimal>() {
                    @Override
                    public void updateItem(BigDecimal item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(nf.format(item));
                        }
                    }
                };
            }
        });

        ObservableList<ElencoTotaliGiornalieriRowData> elencoRighe = itemList();
        tableVenditeEProfittiTotali.getItems().setAll(elencoRighe);

        aggiornaGrafico(graficoVenditeEProfitti,elencoRighe);

    }

    private void aggiornaGrafico(AreaChart<String,Number> graficoVenditeEProfitti,ObservableList<ElencoTotaliGiornalieriRowData> elencoRighe){

        xAxis.setLabel("Giorno");
        yAxis.setLabel("Valore (in €)");
        List<String> xLabels = new ArrayList<String>();

        XYChart.Series seriesProfitti = new XYChart.Series();
        seriesProfitti.setName("Profitti");
        XYChart.Series seriesVendite = new XYChart.Series();
        seriesVendite.setName("Vendite");
        Iterator<ElencoTotaliGiornalieriRowData> iter = elencoRighe.iterator();
        ElencoTotaliGiornalieriRowData totale;
        String toDay;
        BigDecimal totVendite = new BigDecimal(0);
        BigDecimal totProfitti = new BigDecimal(0);
        while(iter.hasNext()){
            totale = (ElencoTotaliGiornalieriRowData)iter.next();
            toDay = Integer.toString(DateUtility.getGiornoDelMese(DateUtility.converteGUIStringDDMMYYYYToDate(totale.getData())));
            xLabels.add(toDay);
            totVendite = totVendite.add(totale.getTotaleVenditeLorde());
            totProfitti = totProfitti.add(totale.getTotaleProfitti());
            seriesProfitti.getData().add(new XYChart.Data(toDay,totale.getTotaleProfitti()));
            seriesVendite.getData().add(new XYChart.Data(toDay,totale.getTotaleVenditeLorde()));
        }
        ObservableList<String> xxLabels = FXCollections.observableArrayList(xLabels);
        xAxis.getCategories().clear();
        xAxis.setCategories(xxLabels);
        graficoVenditeEProfitti.getData().clear();
        graficoVenditeEProfitti.getData().addAll(seriesProfitti,seriesVendite);

        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.ITALY);
        DecimalFormat df = (DecimalFormat)nf;
        lblTotVendite.setText(df.format(totVendite.doubleValue()));
        lblTotProfitti.setText(df.format(totProfitti.doubleValue()));
    }

    private ObservableList<ElencoTotaliGiornalieriRowData> itemList(){

        Date toDate;
        Date fromDate;

        Importazioni importazione = ImportazioniDAOManager.findUltimoInsert();
        toDate = importazione.getDataUltimoMovImportato();
        Calendar myCal = Calendar.getInstance(Locale.ITALY);
        myCal.setTime(toDate);

        int diff = 0;
        if (myCal.getFirstDayOfWeek() < myCal.get(Calendar.DAY_OF_WEEK))
            diff = myCal.get(Calendar.DAY_OF_WEEK) - myCal.getFirstDayOfWeek();

        if (myCal.get(Calendar.DAY_OF_WEEK) == 1) {
            myCal.add(Calendar.DAY_OF_YEAR, -6);
            fromDate = myCal.getTime();
        } else {
            if (diff > 0)
                diff = diff * (-1);
            myCal.add(Calendar.DAY_OF_YEAR, diff);
            fromDate = myCal.getTime();
        }


        txtFldDataFrom.getEditor().setText(DateUtility.converteDateToGUIStringDDMMYYYY(fromDate));
        txtFldDataTo.getEditor().setText(DateUtility.converteDateToGUIStringDDMMYYYY(toDate));

        myCal.setTime(fromDate);
        txtFldDataFrom.setValue(LocalDate.of(myCal.get(Calendar.YEAR), myCal.get(Calendar.MONTH) + 1, myCal.get(Calendar.DAY_OF_MONTH)));
        myCal.setTime(toDate);
        txtFldDataTo.setValue(LocalDate.of(myCal.get(Calendar.YEAR), myCal.get(Calendar.MONTH) + 1, myCal.get(Calendar.DAY_OF_MONTH)));


        return FXCollections.observableArrayList(ElencoTotaliGiornalieriRowDataManager.lookUpElencoTotaliGiornalieriBetweenDate((fromDate),(toDate)));

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
            ObservableList<ElencoTotaliGiornalieriRowData> elencoRighe = FXCollections.observableArrayList(ElencoTotaliGiornalieriRowDataManager.lookUpElencoTotaliGiornalieriBetweenDate((fromDate),(toDate)));
            if (!elencoRighe.isEmpty())
                aggiornaTable(elencoRighe,fromDate,toDate);
            btnBack.setOnAction(new ChangePeriodListener(PeriodToShow.MESE,PeriodDirection.BACK));
            btnForward.setOnAction(new ChangePeriodListener(PeriodToShow.MESE,PeriodDirection.FORWARD));
            lblPeriodo.setText("    Mese  ");
            lblTitle.setText("Situazione Vendite e Profitti Mensile");
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
            ObservableList<ElencoTotaliGiornalieriRowData> elencoRighe = FXCollections.observableArrayList(ElencoTotaliGiornalieriRowDataManager.lookUpElencoTotaliGiornalieriBetweenDate((fromDate),(toDate)));
            if (!elencoRighe.isEmpty())
                aggiornaTable(elencoRighe,fromDate,toDate);
            btnBack.setOnAction(new ChangePeriodListener(PeriodToShow.SETTIMANA,PeriodDirection.BACK));
            btnForward.setOnAction(new ChangePeriodListener(PeriodToShow.SETTIMANA,PeriodDirection.FORWARD));
            lblPeriodo.setText(" Settimana");
            lblTitle.setText("Situazione Vendite e Profitti Settimanale");

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void DettaglioVenditeClicked(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/DettagliVenditeEProfitti.fxml"));
        Parent parent = (Parent)fxmlLoader.load();
        DettaglioVenditeEProfittiController dettaglioController = fxmlLoader.getController();
        boolean vistaSettimanale = true;
        if (rdtBtnVistaMensile.isSelected())
            vistaSettimanale = false;
        dettaglioController.aggiornaTableAndScene(DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataFrom.getEditor().getText()),DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataTo.getEditor().getText()),vistaSettimanale);
        Scene scene = new Scene(parent);
        Stage app_stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(scene);
        app_stage.show();
    }
    
    class ChangePeriodListener implements EventHandler<ActionEvent>{

        private PeriodToShow period;
        private PeriodDirection periodDirection;

        ChangePeriodListener(PeriodToShow period, PeriodDirection periodDirection){
            this.period = period;
            this.periodDirection = periodDirection;
        }

        @Override
        public void handle(ActionEvent event) {
            Calendar myCal = Calendar.getInstance(Locale.ITALY);
            Date fromDate = DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataFrom.getEditor().getText());
            Date toDate = DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataTo.getEditor().getText());
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
            ObservableList<ElencoTotaliGiornalieriRowData> elencoRighe = FXCollections.observableArrayList(ElencoTotaliGiornalieriRowDataManager.lookUpElencoTotaliGiornalieriBetweenDate((fromDate),(toDate)));
            if(!elencoRighe.isEmpty()) {
                aggiornaTable(elencoRighe,fromDate,toDate);
            }else{
                //TODO: Alert per mancanza di movimenti
            }
        }
    }

    /*
    aggiorna il contenuto della tableview in funzione dell'intervallo di date; inoltre aggirna  anche il valore dei
    campi date picker, rispettando al corrispondenza fra il contenuto della tabella ed il valore dei campi datePicker
     */
    private void aggiornaTable(ObservableList<ElencoTotaliGiornalieriRowData> elencoRighe,Date fromDate,Date toDate){
        Calendar myCal = Calendar.getInstance(Locale.ITALY);
        myCal.setTime(fromDate);
        txtFldDataFrom.setValue(LocalDate.of(myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH)+1,myCal.get(Calendar.DAY_OF_MONTH)));
        myCal.setTime(toDate);
        txtFldDataTo.setValue(LocalDate.of(myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH)+1,myCal.get(Calendar.DAY_OF_MONTH)));

        txtFldDataFrom.getEditor().setText(DateUtility.converteDateToGUIStringDDMMYYYY(fromDate));
        txtFldDataTo.getEditor().setText(DateUtility.converteDateToGUIStringDDMMYYYY(toDate));
        tableVenditeEProfittiTotali.getItems().clear();
        tableVenditeEProfittiTotali.getItems().setAll(elencoRighe);
        tableVenditeEProfittiTotali.refresh();
        aggiornaGrafico(graficoVenditeEProfitti, elencoRighe);

    }

    /*
    aggiorna il periodo di riferimento della tabella e le varie label in funzione del periodo di riferimento( settimana o mese)
     */
    public void aggiornaTableAndScene(Date dateFrom, Date dateTo, boolean vistaSettimanale){
        ObservableList<ElencoTotaliGiornalieriRowData> elencoRighe = FXCollections.observableArrayList(ElencoTotaliGiornalieriRowDataManager.lookUpElencoTotaliGiornalieriBetweenDate((dateFrom),(dateTo)));
        aggiornaTable(elencoRighe,dateFrom,dateTo);
        if (vistaSettimanale){
            btnBack.setOnAction(new ChangePeriodListener(PeriodToShow.SETTIMANA,PeriodDirection.BACK));
            btnForward.setOnAction(new ChangePeriodListener(PeriodToShow.SETTIMANA,PeriodDirection.FORWARD));
            lblPeriodo.setText(" Settimana");
            lblTitle.setText("Situazione Vendite e Profitti Settimanale");
            rdtBtnVistaSettimanale.setSelected(true);
        }else{
            btnBack.setOnAction(new ChangePeriodListener(PeriodToShow.MESE,PeriodDirection.BACK));
            btnForward.setOnAction(new ChangePeriodListener(PeriodToShow.MESE,PeriodDirection.FORWARD));
            lblPeriodo.setText("    Mese  ");
            lblTitle.setText("Situazione Vendite e Profitti Mensile");
            rdtBtnVistaMensile.setSelected(true);
        }

    }

    enum PeriodToShow {
        MESE,
        SETTIMANA
    }

    enum PeriodDirection{
        BACK,
        FORWARD,
        CURRENT
    }

    public DatePicker getTxtFldDataFrom() {
        return txtFldDataFrom;
    }

    public DatePicker getTxtFldDataTo(){
        return txtFldDataTo;
    }

    public RadioButton getRdtBtnVistaSettimanale(){
        return rdtBtnVistaSettimanale;
    }

    public RadioButton getRdtBtnVistaMensile(){
        return rdtBtnVistaMensile;
    }
}

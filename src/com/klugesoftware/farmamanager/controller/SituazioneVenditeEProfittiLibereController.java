package com.klugesoftware.farmamanager.controller;

import com.klugesoftware.farmamanager.DTO.ElencoTotaliGiornalieriRowData;
import com.klugesoftware.farmamanager.db.ElencoTotaliGiornalieriRowDataManager;
import com.klugesoftware.farmamanager.db.ImportazioniDAOManager;
import com.klugesoftware.farmamanager.model.Importazioni;
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
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

public class SituazioneVenditeEProfittiLibereController extends VenditeEProfittiController implements Initializable {

    private final Logger logger = LogManager.getLogger(SituazioneVenditeEProfittiLibereController.class.getName());
    @FXML private BorderPane mainPanel;
    @FXML private AreaChart<String,Number> graficoVenditeEProfittiLibere;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;
    @FXML private Label lblTotVendite;
    @FXML private Label lblTotProfitti;
    @FXML private Label lblTitleTotProfitti;
    @FXML private Label lblTitleTotVendite;
    @FXML private TableView<ElencoTotaliGiornalieriRowData> tableVenditeEProfittiLibere;
    @FXML private TableColumn<ElencoTotaliGiornalieriRowData,String> colData;
    @FXML private TableColumn<ElencoTotaliGiornalieriRowData,BigDecimal> colTotaleVenditeLibere;
    @FXML private TableColumn<ElencoTotaliGiornalieriRowData,BigDecimal> colTotaleProfittiLibere;
    @FXML private TableColumn colDettagliMovimenti;
    @FXML private Label lblTitle;
    @FXML private Label lblPeriodo;
    @FXML private DatePicker txtFldDataFrom;
    @FXML private DatePicker txtFldDataTo;
    @FXML private RadioButton rdtBtnVistaSettimanale;
    @FXML private RadioButton rdtBtnVistaMensile;
    @FXML private Button btnBack;
    @FXML private Button btnForward;
    @FXML private Button btnElencoMinsan;
    private ChangePeriodListener changePeriodListenerBack;
    private ChangePeriodListener changePeriodListenerForward;
    private ChangeDateAndViewListener changeDateAndViewListener;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        changePeriodListenerBack = new ChangePeriodListener(PeriodToShow.SETTIMANA,PeriodDirection.BACK,this);
        changePeriodListenerForward = new ChangePeriodListener(PeriodToShow.SETTIMANA,PeriodDirection.FORWARD,this);
        changeDateAndViewListener = new ChangeDateAndViewListener(this);

        btnBack.setOnAction(changePeriodListenerBack);
        btnForward.setOnAction(changePeriodListenerForward);

        txtFldDataFrom.setUserData("dataFrom");
        txtFldDataTo.setUserData("dataTo");
        rdtBtnVistaMensile.setUserData("vistaMensile");
        rdtBtnVistaSettimanale.setUserData("vistaSettimanale");

        txtFldDataFrom.setOnAction(changeDateAndViewListener);
        txtFldDataTo.setOnAction(changeDateAndViewListener);
        rdtBtnVistaSettimanale.setOnAction(changeDateAndViewListener);
        rdtBtnVistaMensile.setOnAction(changeDateAndViewListener);

        colData.setCellValueFactory(new PropertyValueFactory<ElencoTotaliGiornalieriRowData,String>("data"));
        colTotaleVenditeLibere.setCellValueFactory(new PropertyValueFactory<ElencoTotaliGiornalieriRowData,BigDecimal>("totaleVenditeLordeLibere"));
        colTotaleProfittiLibere.setCellValueFactory(new PropertyValueFactory<ElencoTotaliGiornalieriRowData,BigDecimal>("totaleProfittiLibere"));

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

        colTotaleProfittiLibere.setCellFactory(new Callback<TableColumn<ElencoTotaliGiornalieriRowData, BigDecimal>, TableCell<ElencoTotaliGiornalieriRowData, BigDecimal>>() {
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

        colTotaleVenditeLibere.setCellFactory(new Callback<TableColumn<ElencoTotaliGiornalieriRowData, BigDecimal>, TableCell<ElencoTotaliGiornalieriRowData, BigDecimal>>() {
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

        colDettagliMovimenti.setCellFactory(ActionButtonTableCell.<ElencoTotaliGiornalieriRowData>forTableColumn("", (ElencoTotaliGiornalieriRowData p) -> {
            goToDettagli(DateUtility.converteGUIStringDDMMYYYYToDate(p.getData()));
            return p;
        }));

        ObservableList<ElencoTotaliGiornalieriRowData> elencoRighe = itemList();
        tableVenditeEProfittiLibere.getItems().setAll(elencoRighe);
        aggiornaGrafico(graficoVenditeEProfittiLibere,elencoRighe);
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

    private void goToDettagli(Date data){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/ElencoMinsanVenditeLibere.fxml"));
            Parent parent = (Parent) fxmlLoader.load();
            ElencoMinsanVenditeLibereController controller = fxmlLoader.getController();
            controller.aggiornaTable(data,data);
            Scene scene = new Scene(parent);
            Stage app_stage = (Stage) ( mainPanel.getScene().getWindow());
            app_stage.hide();
            app_stage.setScene(scene);
            app_stage.show();
        }catch(Exception ex){
            logger.error(ex.getMessage());
        }
    }

    private void aggiornaGrafico(AreaChart<String,Number> graficoVenditeEProfittiLibere,ObservableList<ElencoTotaliGiornalieriRowData> elencoRighe){

        xAxis.setLabel("Giorno");
        yAxis.setLabel("Valore (in €)");
        List<String> xLabels = new ArrayList<String>();

        XYChart.Series seriesProfitti = new XYChart.Series();
        seriesProfitti.setName("Profitti Vendite Libere");
        XYChart.Series seriesVendite = new XYChart.Series();
        seriesVendite.setName("Vendite Libere");
        Iterator<ElencoTotaliGiornalieriRowData> iter = elencoRighe.iterator();
        ElencoTotaliGiornalieriRowData totale;
        String toDay;
        BigDecimal totVendite = new BigDecimal(0);
        BigDecimal totProfitti = new BigDecimal(0);
        while(iter.hasNext()){
            totale = (ElencoTotaliGiornalieriRowData)iter.next();
            toDay = Integer.toString(DateUtility.getGiornoDelMese(DateUtility.converteGUIStringDDMMYYYYToDate(totale.getData())));
            xLabels.add(toDay);
            totVendite = totVendite.add(totale.getTotaleVenditeLordeLibere());
            totProfitti = totProfitti.add(totale.getTotaleProfittiLibere());
            seriesProfitti.getData().add(new XYChart.Data(toDay,totale.getTotaleProfittiLibere()));
            seriesVendite.getData().add(new XYChart.Data(toDay,totale.getTotaleVenditeLordeLibere()));
        }
        ObservableList<String> xxLabels = FXCollections.observableArrayList(xLabels);
        xAxis.getCategories().clear();
        xAxis.setCategories(xxLabels);
        graficoVenditeEProfittiLibere.getData().clear();
        graficoVenditeEProfittiLibere.getData().addAll(seriesProfitti,seriesVendite);

        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.ITALY);
        DecimalFormat df = (DecimalFormat)nf;
        lblTotVendite.setText(df.format(totVendite.doubleValue()));
        lblTotProfitti.setText(df.format(totProfitti.doubleValue()));
    }


    @Override
    public Date getDateFrom() {
        return DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataFrom.getEditor().getText());
    }

    @Override
    public Date getDateTo() {
        return DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataTo.getEditor().getText());
    }

    @Override
    public RadioButton getRdbtVistaMensile() {
        return rdtBtnVistaMensile;
    }

    @Override
    public RadioButton getRdbtVistaSettimanale() {
        return rdtBtnVistaSettimanale;
    }

    @Override
    public void aggiornaTableAndScene(Date dateFrom, Date dateTo, boolean vistaSettimanale) {

        ObservableList<ElencoTotaliGiornalieriRowData> elencoRighe = FXCollections.observableArrayList(ElencoTotaliGiornalieriRowDataManager.lookUpElencoTotaliGiornalieriBetweenDate((dateFrom),(dateTo)));
        aggiornaTable(elencoRighe,dateFrom,dateTo);
        if (vistaSettimanale){
            changePeriodListenerBack.setPeriod(PeriodToShow.SETTIMANA);
            changePeriodListenerForward.setPeriod(PeriodToShow.SETTIMANA);
            lblPeriodo.setText(" Settimana");
            rdtBtnVistaSettimanale.setSelected(true);
        }else{
            changePeriodListenerBack.setPeriod(PeriodToShow.MESE);
            changePeriodListenerForward.setPeriod(PeriodToShow.MESE);
            lblPeriodo.setText("    Mese  ");
            rdtBtnVistaMensile.setSelected(true);
        }

    }

    @Override
    public void aggiornaTable(Date dateFrom, Date dateTo) {
        ObservableList<ElencoTotaliGiornalieriRowData> elencoRighe = FXCollections.observableArrayList(ElencoTotaliGiornalieriRowDataManager.lookUpElencoTotaliGiornalieriBetweenDate((dateFrom),(dateTo)));
        if(!elencoRighe.isEmpty()) {
            //TODO: messsaggio se movimenti mancanti....
            aggiornaTable(elencoRighe, dateFrom, dateTo);
        }
    }

    private void aggiornaTable(ObservableList<ElencoTotaliGiornalieriRowData> elencoRighe,Date dateFrom, Date dateTo){
        Calendar myCal = Calendar.getInstance(Locale.ITALY);
        myCal.setTime(dateFrom);
        txtFldDataFrom.setValue(LocalDate.of(myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH)+1,myCal.get(Calendar.DAY_OF_MONTH)));
        myCal.setTime(dateTo);
        txtFldDataTo.setValue(LocalDate.of(myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH)+1,myCal.get(Calendar.DAY_OF_MONTH)));

        txtFldDataFrom.getEditor().setText(DateUtility.converteDateToGUIStringDDMMYYYY(dateFrom));
        txtFldDataTo.getEditor().setText(DateUtility.converteDateToGUIStringDDMMYYYY(dateTo));
        tableVenditeEProfittiLibere.getItems().clear();
        tableVenditeEProfittiLibere.getItems().setAll(elencoRighe);
        tableVenditeEProfittiLibere.refresh();
        aggiornaGrafico(graficoVenditeEProfittiLibere, elencoRighe);

    }

    @FXML
    private void buttonElencoMinsanListener(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/ElencoMinsanVenditeLibere.fxml"));
            Parent parent = (Parent) fxmlLoader.load();
            ElencoMinsanVenditeLibereController controller = fxmlLoader.getController();
            controller.aggiornaTable(getDateFrom(),getDateTo());
            Scene scene = new Scene(parent);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.hide();
            app_stage.setScene(scene);
            app_stage.show();
        }catch(Exception ex){
            logger.error(ex.getMessage());
        }
    }

    @FXML
    private void listenerEsciButton(ActionEvent event){

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/HomeAnalisiDati.fxml"));
            Parent parent = (Parent) fxmlLoader.load();
            HomeAnalisiDatiController controller = fxmlLoader.getController();
            Scene scene = new Scene(parent);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.hide();
            app_stage.setScene(scene);
            app_stage.show();
        }catch(Exception ex){
            logger.error(ex.getMessage());
        }
    }

    @FXML
    private void dettagliEConfrontoClicked(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/ConfrontoTotaliVenditeLibere.fxml"));
            Parent parent = (Parent)fxmlLoader.load();
            ConfrontoTotaliVenditeLibereController controller = fxmlLoader.getController();
            controller.setIntervalloMensile(getDateFrom(),getDateTo(),true);
            Scene scene = new Scene(parent);
            Stage app_stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            app_stage.hide();
            app_stage.setScene(scene);
            app_stage.show();
        }catch(Exception ex){
            logger.error(ex.getMessage());
        }

    }
    
}

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

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public class SituazioneVenditeEProfittiMensileController implements Initializable {

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
    @FXML private RadioButton rdtBtnVistaMensile;
    @FXML private RadioButton rdtBtnVistaSettimanale;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        Importazioni importazione = ImportazioniDAOManager.findUltimoInsert();
        Date myDate = importazione.getDataUltimoMovImportato();
        Calendar myCal = Calendar.getInstance(Locale.ITALY);
        myCal.setTime(myDate);
        myCal.set(myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH),1);
        Date fromDate = myCal.getTime();
        myCal.set(myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH),myCal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date toDate = myCal.getTime();

        txtFldDataFrom.getEditor().setText(DateUtility.converteDateToGUIStringDDMMYYYY(fromDate));
        txtFldDataTo.getEditor().setText(DateUtility.converteDateToGUIStringDDMMYYYY(toDate));

        return FXCollections.observableArrayList(ElencoTotaliGiornalieriRowDataManager.lookUpElencoTotaliGiornalieriBetweenDate((fromDate),(toDate)));

    }

    @FXML
    private void listenerMeseBack(ActionEvent event){

        Date lastDate = DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataFrom.getEditor().getText());
        Calendar myCal = Calendar.getInstance(Locale.ITALY);
        myCal.setTime(lastDate);
        myCal.set(myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH),1);
        myCal.add(Calendar.MONTH,-1);
        Date fromDate = myCal.getTime();
        myCal.set(Calendar.DAY_OF_MONTH,myCal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date toDate = myCal.getTime();

        ObservableList<ElencoTotaliGiornalieriRowData> elencoRighe = FXCollections.observableArrayList(ElencoTotaliGiornalieriRowDataManager.lookUpElencoTotaliGiornalieriBetweenDate((fromDate),(toDate)));
        if(!elencoRighe.isEmpty()) {
            txtFldDataFrom.getEditor().setText(DateUtility.converteDateToGUIStringDDMMYYYY(fromDate));
            txtFldDataTo.getEditor().setText(DateUtility.converteDateToGUIStringDDMMYYYY(toDate));
            tableVenditeEProfittiTotali.getItems().clear();
            tableVenditeEProfittiTotali.getItems().setAll(elencoRighe);
            tableVenditeEProfittiTotali.refresh();
            aggiornaGrafico(graficoVenditeEProfitti, elencoRighe);
        }else{
            //TODO: alert per mancanza moviementi !!!
        }
    }

    @FXML
    private void listenerMeseForward(ActionEvent event){

        Date lastDate = DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataFrom.getEditor().getText());
        Calendar myCal = Calendar.getInstance(Locale.ITALY);
        myCal.setTime(lastDate);
        myCal.set(myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH),1);
        myCal.add(Calendar.MONTH,1);
        Date fromDate = myCal.getTime();
        myCal.set(Calendar.DAY_OF_MONTH,myCal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date toDate = myCal.getTime();

        ObservableList<ElencoTotaliGiornalieriRowData> elencoRighe = FXCollections.observableArrayList(ElencoTotaliGiornalieriRowDataManager.lookUpElencoTotaliGiornalieriBetweenDate((fromDate),(toDate)));
        if (!elencoRighe.isEmpty()) {
            txtFldDataFrom.getEditor().setText(DateUtility.converteDateToGUIStringDDMMYYYY(fromDate));
            txtFldDataTo.getEditor().setText(DateUtility.converteDateToGUIStringDDMMYYYY(toDate));
            tableVenditeEProfittiTotali.getItems().clear();
            tableVenditeEProfittiTotali.getItems().setAll(elencoRighe);
            tableVenditeEProfittiTotali.refresh();
            aggiornaGrafico(graficoVenditeEProfitti, elencoRighe);
        }else{
            //TODO: ALERT per mancanza movimenti !!!
        }
    }

    @FXML
    private void clickedSettimana(ActionEvent event) {
        try {
            rdtBtnVistaSettimanale = (RadioButton) event.getSource();
            rdtBtnVistaSettimanale.setSelected(true);
            rdtBtnVistaMensile.setSelected(false);
            Stage stage = (Stage) rdtBtnVistaSettimanale.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("../view/SituazioneVenditeEProfittiSettimanale.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void listenerEsciButton(ActionEvent event){
        System.exit(0);
    }

}
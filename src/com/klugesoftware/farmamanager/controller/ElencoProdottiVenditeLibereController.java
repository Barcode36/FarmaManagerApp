package com.klugesoftware.farmamanager.controller;


import com.klugesoftware.farmamanager.DTO.ElencoProdottiLiberaVenditaRowData;
import com.klugesoftware.farmamanager.db.*;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.*;

public class ElencoProdottiVenditeLibereController extends ElencoMinsanController implements Initializable {

    @FXML private TableView<ElencoProdottiLiberaVenditaRowData> tableElencoProdotti;
    @FXML private TableColumn<ElencoProdottiLiberaVenditaRowData,String> colData;
    @FXML private TableColumn<ElencoProdottiLiberaVenditaRowData,String> colMinsan;
    @FXML private TableColumn<ElencoProdottiLiberaVenditaRowData,String> colDescrizione;
    @FXML private TableColumn<ElencoProdottiLiberaVenditaRowData,Integer> colQuantita;
    @FXML private TableColumn<ElencoProdottiLiberaVenditaRowData,BigDecimal> colPrezzoVendita;
    @FXML private TableColumn<ElencoProdottiLiberaVenditaRowData,BigDecimal> colScontoUnitario;
    @FXML private TableColumn<ElencoProdottiLiberaVenditaRowData,BigDecimal> colPrezzoVenditaNetto;
    @FXML private TableColumn<ElencoProdottiLiberaVenditaRowData,BigDecimal> colCostoUnitario;
    @FXML private TableColumn<ElencoProdottiLiberaVenditaRowData,BigDecimal> colMargineUnitario;
    @FXML private TableColumn<ElencoProdottiLiberaVenditaRowData,BigDecimal> colRicaricoUnitario;
    @FXML private TableColumn<ElencoProdottiLiberaVenditaRowData,BigDecimal> colProfittoUnitario;
    @FXML private DatePicker txtFldDataFrom;
    @FXML private DatePicker txtFldDataTo;
    @FXML private TextField txtSearch;
    private ChangeDateListener changeDateListener;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        changeDateListener = new ChangeDateListener(this);
        txtFldDataFrom.setUserData("dataFrom");
        txtFldDataTo.setUserData("dataTo");
        txtFldDataFrom.setOnAction(changeDateListener);
        txtFldDataTo.setOnAction(changeDateListener);


        colData.setCellValueFactory(new PropertyValueFactory<ElencoProdottiLiberaVenditaRowData,String>("data"));
        colMinsan.setCellValueFactory(new PropertyValueFactory<ElencoProdottiLiberaVenditaRowData,String>("minsan"));
        colDescrizione.setCellValueFactory(new PropertyValueFactory<ElencoProdottiLiberaVenditaRowData,String>("descrizione"));
        colQuantita.setCellValueFactory(new PropertyValueFactory<ElencoProdottiLiberaVenditaRowData,Integer>("quantita"));
        colPrezzoVendita.setCellValueFactory(new PropertyValueFactory<ElencoProdottiLiberaVenditaRowData,BigDecimal>("prezzoVendita"));
        colScontoUnitario.setCellValueFactory(new PropertyValueFactory<ElencoProdottiLiberaVenditaRowData,BigDecimal>("scontoUnitario"));
        colPrezzoVenditaNetto.setCellValueFactory(new PropertyValueFactory<ElencoProdottiLiberaVenditaRowData,BigDecimal>("prezzoVenditaNettoIvaESconti"));
        colCostoUnitario.setCellValueFactory(new PropertyValueFactory<ElencoProdottiLiberaVenditaRowData,BigDecimal>("costoUnitarioSenzaIva"));
        colMargineUnitario.setCellValueFactory(new PropertyValueFactory<ElencoProdottiLiberaVenditaRowData,BigDecimal>("margineUnitario"));
        colRicaricoUnitario.setCellValueFactory(new PropertyValueFactory<ElencoProdottiLiberaVenditaRowData,BigDecimal>("ricaricoUnitario"));
        colProfittoUnitario.setCellValueFactory(new PropertyValueFactory<ElencoProdottiLiberaVenditaRowData,BigDecimal>("profittoUnitario"));


        colData.setCellFactory(new Callback<TableColumn<ElencoProdottiLiberaVenditaRowData, String>, TableCell<ElencoProdottiLiberaVenditaRowData, String>>() {
            @Override
            public TableCell<ElencoProdottiLiberaVenditaRowData, String> call(TableColumn<ElencoProdottiLiberaVenditaRowData, String> item) {
                return new TableCell<ElencoProdottiLiberaVenditaRowData,String>(){
                    @Override
                    public void updateItem(String item, boolean empty){
                        if (item == null || empty){
                            setText(null);
                        }else {
                            setText(DateUtility.converteGUIStringDDMMYYYYToNameDayOfWeek(item));
                        }
                    }
                };
            }
        });

        colMinsan.setCellFactory(new Callback<TableColumn<ElencoProdottiLiberaVenditaRowData, String>, TableCell<ElencoProdottiLiberaVenditaRowData, String>>() {
            @Override
            public TableCell<ElencoProdottiLiberaVenditaRowData, String> call(TableColumn<ElencoProdottiLiberaVenditaRowData, String> param) {
                return new TableCell<ElencoProdottiLiberaVenditaRowData,String>(){
                    @Override
                    public void updateItem(String item, boolean empty){
                        if (item == null || empty){
                            setText(null);
                        }else {
                            setText(item);
                        }
                    }
                };
            }
        });


        colDescrizione.setCellFactory(new Callback<TableColumn<ElencoProdottiLiberaVenditaRowData, String>, TableCell<ElencoProdottiLiberaVenditaRowData, String>>() {
            @Override
            public TableCell<ElencoProdottiLiberaVenditaRowData, String> call(TableColumn<ElencoProdottiLiberaVenditaRowData, String> param) {
                return new TableCell<ElencoProdottiLiberaVenditaRowData,String>(){
                    @Override
                    public void updateItem(String item, boolean empty){
                        if (item == null || empty){
                            setText(null);
                        }else {
                            setText(item);
                        }
                    }
                };
            }
        });

        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.ITALY);
        DecimalFormat df = (DecimalFormat)nf;

        colQuantita.setCellFactory(new Callback<TableColumn<ElencoProdottiLiberaVenditaRowData, Integer>, TableCell<ElencoProdottiLiberaVenditaRowData, Integer>>() {
            @Override
            public TableCell<ElencoProdottiLiberaVenditaRowData, Integer> call(TableColumn<ElencoProdottiLiberaVenditaRowData, Integer> param) {
                return new TableCell<ElencoProdottiLiberaVenditaRowData, Integer>(){
                    @Override
                    public void updateItem(Integer item, boolean empty){
                        if (item == null || empty){
                            setText(null);
                        }else{
                            setText(item.toString());
                        }
                    }
                };
            }
        });

        colCostoUnitario.setCellFactory(new Callback<TableColumn<ElencoProdottiLiberaVenditaRowData, BigDecimal>, TableCell<ElencoProdottiLiberaVenditaRowData, BigDecimal>>() {
            @Override
            public TableCell<ElencoProdottiLiberaVenditaRowData, BigDecimal> call(TableColumn<ElencoProdottiLiberaVenditaRowData, BigDecimal> param) {
                return new TableCell<ElencoProdottiLiberaVenditaRowData, BigDecimal>(){
                    @Override
                    public void updateItem(BigDecimal item, boolean empty){
                        if(item == null || empty){
                            setText(null);
                        }else{
                            setText(nf.format(item));
                        }
                    }
                };
            }
        });

        colPrezzoVendita.setCellFactory(new Callback<TableColumn<ElencoProdottiLiberaVenditaRowData, BigDecimal>, TableCell<ElencoProdottiLiberaVenditaRowData, BigDecimal>>() {
            @Override
            public TableCell<ElencoProdottiLiberaVenditaRowData, BigDecimal> call(TableColumn<ElencoProdottiLiberaVenditaRowData, BigDecimal> param) {
                return new TableCell<ElencoProdottiLiberaVenditaRowData, BigDecimal>(){
                    @Override
                    public void updateItem(BigDecimal item, boolean empty){
                        if(item == null || empty){
                            setText(null);
                        }else{
                            setText(nf.format(item));
                        }
                    }
                };
            }
        });

        colPrezzoVenditaNetto.setCellFactory(new Callback<TableColumn<ElencoProdottiLiberaVenditaRowData, BigDecimal>, TableCell<ElencoProdottiLiberaVenditaRowData, BigDecimal>>() {
            @Override
            public TableCell<ElencoProdottiLiberaVenditaRowData, BigDecimal> call(TableColumn<ElencoProdottiLiberaVenditaRowData, BigDecimal> param) {
                return new TableCell<ElencoProdottiLiberaVenditaRowData, BigDecimal>(){
                    @Override
                    public void updateItem(BigDecimal item, boolean empty){
                        if(item == null || empty){
                            setText(null);
                        }else{
                            setText(nf.format(item));
                        }
                    }
                };
            }
        });

        colScontoUnitario.setCellFactory(new Callback<TableColumn<ElencoProdottiLiberaVenditaRowData, BigDecimal>, TableCell<ElencoProdottiLiberaVenditaRowData, BigDecimal>>() {
            @Override
            public TableCell<ElencoProdottiLiberaVenditaRowData, BigDecimal> call(TableColumn<ElencoProdottiLiberaVenditaRowData, BigDecimal> param) {
                return new TableCell<ElencoProdottiLiberaVenditaRowData, BigDecimal>(){
                    @Override
                    public void updateItem(BigDecimal item, boolean empty){
                        if(item == null || empty){
                            setText(null);
                        }else{
                            setText(nf.format(item));
                        }
                    }
                };
            }
        });

        colProfittoUnitario.setCellFactory(new Callback<TableColumn<ElencoProdottiLiberaVenditaRowData, BigDecimal>, TableCell<ElencoProdottiLiberaVenditaRowData, BigDecimal>>() {
            @Override
            public TableCell<ElencoProdottiLiberaVenditaRowData, BigDecimal> call(TableColumn<ElencoProdottiLiberaVenditaRowData, BigDecimal> param) {
                return new TableCell<ElencoProdottiLiberaVenditaRowData, BigDecimal>(){
                    @Override
                    public void updateItem(BigDecimal item, boolean empty){
                        if(item == null || empty){
                            setText(null);
                        }else{
                            setText(nf.format(item));
                        }
                    }
                };
            }
        });

        colRicaricoUnitario.setCellFactory(new Callback<TableColumn<ElencoProdottiLiberaVenditaRowData, BigDecimal>, TableCell<ElencoProdottiLiberaVenditaRowData, BigDecimal>>() {
            @Override
            public TableCell<ElencoProdottiLiberaVenditaRowData, BigDecimal> call(TableColumn<ElencoProdottiLiberaVenditaRowData, BigDecimal> param) {
                return new TableCell<ElencoProdottiLiberaVenditaRowData, BigDecimal>(){
                    @Override
                    public void updateItem(BigDecimal item, boolean empty){
                        if(item == null || empty){
                            setText(null);
                        }else{
                            String temp = item.toString().replace(".",",");
                            setText(temp+" %");
                        }
                    }
                };
            }
        });

        colMargineUnitario.setCellFactory(new Callback<TableColumn<ElencoProdottiLiberaVenditaRowData, BigDecimal>, TableCell<ElencoProdottiLiberaVenditaRowData, BigDecimal>>() {
            @Override
            public TableCell<ElencoProdottiLiberaVenditaRowData, BigDecimal> call(TableColumn<ElencoProdottiLiberaVenditaRowData, BigDecimal> param) {
                return new TableCell<ElencoProdottiLiberaVenditaRowData, BigDecimal>(){
                    @Override
                    public void updateItem(BigDecimal item, boolean empty){
                        if(item == null || empty){
                            setText(null);
                        }else{
                            String temp = item.toString().replace(".",",");
                            setText(temp+" %");
                        }
                    }
                };
            }
        });

        ObservableList<ElencoProdottiLiberaVenditaRowData> elencoRighe = itemList();
        tableElencoProdotti.getItems().setAll(elencoRighe);

    }

    private ObservableList<ElencoProdottiLiberaVenditaRowData> itemList(){

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



        return FXCollections.observableArrayList(ProdottiVenditaLiberaDAOManager.elencoBetweenDate(fromDate,toDate));

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
    public void aggiornaTable(Date dateFrom, Date dateTo) {
        txtFldDataFrom.setOnAction(null);
        txtFldDataTo.setOnAction(null);
        Calendar myCal = Calendar.getInstance(Locale.ITALY);
        myCal.setTime(dateFrom);
        txtFldDataFrom.setValue(LocalDate.of(myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH)+1,myCal.get(Calendar.DAY_OF_MONTH)));
        txtFldDataFrom.getEditor().setText(DateUtility.converteDateToGUIStringDDMMYYYY(dateFrom));
        myCal.setTime(dateTo);
        txtFldDataTo.setValue(LocalDate.of(myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH)+1,myCal.get(Calendar.DAY_OF_MONTH)));
        txtFldDataTo.getEditor().setText(DateUtility.converteDateToGUIStringDDMMYYYY(dateTo));
        txtFldDataFrom.setOnAction(changeDateListener);
        txtFldDataTo.setOnAction(changeDateListener);
        ArrayList<ElencoProdottiLiberaVenditaRowData> elenco = ProdottiVenditaLiberaDAOManager.elencoBetweenDate(dateFrom,dateTo);
        tableElencoProdotti.getItems().clear();
        tableElencoProdotti.getItems().setAll(FXCollections.observableArrayList(elenco));
        tableElencoProdotti.refresh();

    }

    @FXML
    private void listenerEsciButton(ActionEvent event){
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

    @FXML
    private void cercaClicked(ActionEvent event){
        Date dateFrom = DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataFrom.getEditor().getText());
        Date dateTo = DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataTo.getEditor().getText());
        tableElencoProdotti.getItems().clear();
        tableElencoProdotti.getItems().setAll(FXCollections.observableArrayList(ProdottiVenditaLiberaDAOManager.elencoLikeBetweenDate(dateFrom,dateTo,txtSearch.getText())));
        tableElencoProdotti.refresh();
    }

    @FXML
    private void goBack(ActionEvent event){
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
            ex.printStackTrace();
        }

    }

}

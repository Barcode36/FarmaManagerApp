package com.klugesoftware.farmamanager.controller;

import com.klugesoftware.farmamanager.DTO.ElencoMinsanLiberaVenditaRowData;
import com.klugesoftware.farmamanager.db.ElencoMinsanLiberaVenditaRowDataDAOManager;
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
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

public class ElencoDettagliatoMinsanVenditeLibereController extends ElencoMinsanController implements Initializable {

    private final Logger logger = LogManager.getLogger(ElencoDettagliatoMinsanVenditeLibereController.class.getName());
    @FXML private TableView<ElencoMinsanLiberaVenditaRowData> tableElencoMinsan;
    @FXML private TableColumn<ElencoMinsanLiberaVenditaRowData,String> colMinsan;
    @FXML private TableColumn<ElencoMinsanLiberaVenditaRowData,String> colDescrizione;
    @FXML private TableColumn<ElencoMinsanLiberaVenditaRowData,Integer> colQuantita;
    @FXML private TableColumn<ElencoMinsanLiberaVenditaRowData,BigDecimal> colPrezzoVenditaMedio;
    @FXML private TableColumn<ElencoMinsanLiberaVenditaRowData,BigDecimal> colScontoMedio;
    @FXML private TableColumn<ElencoMinsanLiberaVenditaRowData,BigDecimal> colCostoMedio;
    @FXML private TableColumn<ElencoMinsanLiberaVenditaRowData,BigDecimal> colMargineMedio;
    @FXML private TableColumn<ElencoMinsanLiberaVenditaRowData,BigDecimal> colRicaricoMedio;
    @FXML private TableColumn<ElencoMinsanLiberaVenditaRowData,BigDecimal> colProfittoMedio;
    @FXML private TextField txtSearch;
    @FXML private DatePicker txtFldDataFrom;
    @FXML private DatePicker txtFldDataTo;
          private ChangeDateListener changeDateListener;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        changeDateListener = new ChangeDateListener(this);
        txtFldDataFrom.setUserData("dataFrom");
        txtFldDataTo.setUserData("dataTo");
        txtFldDataFrom.setOnAction(changeDateListener);
        txtFldDataTo.setOnAction(changeDateListener);

        colMinsan.setCellValueFactory(new PropertyValueFactory<ElencoMinsanLiberaVenditaRowData,String>("minsan"));
        colDescrizione.setCellValueFactory(new PropertyValueFactory<ElencoMinsanLiberaVenditaRowData,String>("descrizione"));
        colQuantita.setCellValueFactory(new PropertyValueFactory<ElencoMinsanLiberaVenditaRowData,Integer>("quantitaTotale"));
        colPrezzoVenditaMedio.setCellValueFactory(new PropertyValueFactory<ElencoMinsanLiberaVenditaRowData,BigDecimal>("prezzoVenditaMedio"));
        colScontoMedio.setCellValueFactory(new PropertyValueFactory<ElencoMinsanLiberaVenditaRowData,BigDecimal>("scontoMedio"));
        colCostoMedio.setCellValueFactory(new PropertyValueFactory<ElencoMinsanLiberaVenditaRowData,BigDecimal>("costoMedio"));
        colMargineMedio.setCellValueFactory(new PropertyValueFactory<ElencoMinsanLiberaVenditaRowData,BigDecimal>("margineMedio"));
        colRicaricoMedio.setCellValueFactory(new PropertyValueFactory<ElencoMinsanLiberaVenditaRowData,BigDecimal>("ricaricoMedio"));
        colProfittoMedio.setCellValueFactory(new PropertyValueFactory<ElencoMinsanLiberaVenditaRowData,BigDecimal>("profittoMedio"));


        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.ITALY);
        DecimalFormat df = (DecimalFormat)nf;


        colPrezzoVenditaMedio.setCellFactory(new Callback<TableColumn<ElencoMinsanLiberaVenditaRowData, BigDecimal>, TableCell<ElencoMinsanLiberaVenditaRowData, BigDecimal>>() {
            @Override
            public TableCell<ElencoMinsanLiberaVenditaRowData, BigDecimal> call(TableColumn<ElencoMinsanLiberaVenditaRowData, BigDecimal> param) {
                return new TableCell<ElencoMinsanLiberaVenditaRowData,BigDecimal>(){
                  @Override
                  public void updateItem(BigDecimal item,boolean empty){
                      if (item == null || empty) {
                          setText(null);
                      } else {
                          setText(df.format(item));
                      }
                  }
                };
            }
        });

        colScontoMedio.setCellFactory(new Callback<TableColumn<ElencoMinsanLiberaVenditaRowData, BigDecimal>, TableCell<ElencoMinsanLiberaVenditaRowData, BigDecimal>>() {
            @Override
            public TableCell<ElencoMinsanLiberaVenditaRowData, BigDecimal> call(TableColumn<ElencoMinsanLiberaVenditaRowData, BigDecimal> param) {
                return new TableCell<ElencoMinsanLiberaVenditaRowData,BigDecimal>(){
                    @Override
                    public void updateItem(BigDecimal item,boolean empty){
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(df.format(item));
                        }
                    }
                };
            }
        });

        colCostoMedio.setCellFactory(new Callback<TableColumn<ElencoMinsanLiberaVenditaRowData, BigDecimal>, TableCell<ElencoMinsanLiberaVenditaRowData, BigDecimal>>() {
            @Override
            public TableCell<ElencoMinsanLiberaVenditaRowData, BigDecimal> call(TableColumn<ElencoMinsanLiberaVenditaRowData, BigDecimal> param) {
                return new TableCell<ElencoMinsanLiberaVenditaRowData,BigDecimal>(){
                    @Override
                    public void updateItem(BigDecimal item,boolean empty){
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(df.format(item));
                        }
                    }
                };
            }
        });


        colMargineMedio.setCellFactory(new Callback<TableColumn<ElencoMinsanLiberaVenditaRowData, BigDecimal>, TableCell<ElencoMinsanLiberaVenditaRowData, BigDecimal>>() {
            @Override
            public TableCell<ElencoMinsanLiberaVenditaRowData, BigDecimal> call(TableColumn<ElencoMinsanLiberaVenditaRowData, BigDecimal> param) {
                return new TableCell<ElencoMinsanLiberaVenditaRowData,BigDecimal>(){
                    @Override
                    public void updateItem(BigDecimal item,boolean empty){
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            String temp = item.toString().replace(".",",");
                            setText(temp.toString()+" %");

                        }
                    }
                };
            }
        });

        colRicaricoMedio.setCellFactory(new Callback<TableColumn<ElencoMinsanLiberaVenditaRowData, BigDecimal>, TableCell<ElencoMinsanLiberaVenditaRowData, BigDecimal>>() {
            @Override
            public TableCell<ElencoMinsanLiberaVenditaRowData, BigDecimal> call(TableColumn<ElencoMinsanLiberaVenditaRowData, BigDecimal> param) {
                return new TableCell<ElencoMinsanLiberaVenditaRowData,BigDecimal>(){
                    @Override
                    public void updateItem(BigDecimal item,boolean empty){
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            String temp = item.toString().replace(".",",");
                            setText(temp.toString()+" %");
                        }
                    }
                };
            }
        });

        colProfittoMedio.setCellFactory(new Callback<TableColumn<ElencoMinsanLiberaVenditaRowData, BigDecimal>, TableCell<ElencoMinsanLiberaVenditaRowData, BigDecimal>>() {
            @Override
            public TableCell<ElencoMinsanLiberaVenditaRowData, BigDecimal> call(TableColumn<ElencoMinsanLiberaVenditaRowData, BigDecimal> param) {
                return new TableCell<ElencoMinsanLiberaVenditaRowData,BigDecimal>(){
                    @Override
                    public void updateItem(BigDecimal item,boolean empty){
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(df.format(item));
                        }
                    }
                };
            }
        });

        ObservableList<ElencoMinsanLiberaVenditaRowData> elencoRighe = itemList();
        tableElencoMinsan.getItems().setAll(elencoRighe);
    }

    private ObservableList<ElencoMinsanLiberaVenditaRowData> itemList(){
        Date toDate;
        Date fromDate;

        Importazioni importazione = ImportazioniDAOManager.findUltimoInsert();
        toDate = importazione.getDataUltimoMovImportato();
        Calendar myCal = Calendar.getInstance(Locale.ITALY);
        myCal.setTime(toDate);

        fromDate = DateUtility.primoGiornoDelMeseCorrente(toDate);

        txtFldDataFrom.setOnAction(null);
        txtFldDataTo.setOnAction(null);

        txtFldDataFrom.getEditor().setText(DateUtility.converteDateToGUIStringDDMMYYYY(fromDate));
        txtFldDataTo.getEditor().setText(DateUtility.converteDateToGUIStringDDMMYYYY(toDate));

        myCal.setTime(fromDate);
        txtFldDataFrom.setValue(LocalDate.of(myCal.get(Calendar.YEAR), myCal.get(Calendar.MONTH) + 1, myCal.get(Calendar.DAY_OF_MONTH)));
        myCal.setTime(toDate);
        txtFldDataTo.setValue(LocalDate.of(myCal.get(Calendar.YEAR), myCal.get(Calendar.MONTH) + 1, myCal.get(Calendar.DAY_OF_MONTH)));

        txtFldDataFrom.setOnAction(changeDateListener);
        txtFldDataTo.setOnAction(changeDateListener);
        ArrayList<ElencoMinsanLiberaVenditaRowData> elenco = ElencoMinsanLiberaVenditaRowDataDAOManager.lookUpElencoMinsanProdottoVenditaLiberaBetweenDate(fromDate,toDate);

        return FXCollections.observableArrayList(elenco);

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
            logger.error(ex.getMessage());
        }
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
        ArrayList<ElencoMinsanLiberaVenditaRowData> elenco = ElencoMinsanLiberaVenditaRowDataDAOManager.lookUpElencoMinsanProdottoVenditaLiberaBetweenDate(dateFrom,dateTo);
        tableElencoMinsan.getItems().clear();
        tableElencoMinsan.getItems().setAll(FXCollections.observableArrayList(elenco));
        tableElencoMinsan.refresh();
    }

    @FXML
    private void cercaClicked(ActionEvent event){
        Date dateFrom = DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataFrom.getEditor().getText());
        Date dateTo = DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataTo.getEditor().getText());
        tableElencoMinsan.getItems().clear();
        tableElencoMinsan.getItems().setAll(FXCollections.observableArrayList(ElencoMinsanLiberaVenditaRowDataDAOManager.lookUpElencoMinsanProdottoVenditaLiberaLikeBetweenDate(dateFrom,dateTo,txtSearch.getText())));
        tableElencoMinsan.refresh();
    }

    @FXML
    private void elencoProdottiClicked(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/ElencoProdottiVenditeLibere.fxml"));
            Parent parent = (Parent) fxmlLoader.load();
            ElencoProdottiVenditeLibereController controller = fxmlLoader.getController();
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
    private void goBackClicked(ActionEvent event){
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
}



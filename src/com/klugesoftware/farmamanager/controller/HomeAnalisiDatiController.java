package com.klugesoftware.farmamanager.controller;

import com.klugesoftware.farmamanager.DTO.ElencoMinsanLiberaVenditaRowData;
import com.klugesoftware.farmamanager.DTO.ElencoTotaliGiornalieriRowData;
import com.klugesoftware.farmamanager.db.ElencoMinsanLiberaVenditaRowDataDAOManager;
import com.klugesoftware.farmamanager.db.ImportazioniDAOManager;
import com.klugesoftware.farmamanager.model.Importazioni;
import com.klugesoftware.farmamanager.utility.DateUtility;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.*;

public class HomeAnalisiDatiController extends ElencoMinsanController implements Initializable {


    @FXML private TableView<ElencoMinsanLiberaVenditaRowData> tableQuantita;
    @FXML private TableColumn<ElencoMinsanLiberaVenditaRowData,String> colMinsan1;
    @FXML private TableColumn<ElencoMinsanLiberaVenditaRowData,String> colDescrizione1;
    @FXML private TableColumn<ElencoMinsanLiberaVenditaRowData,Integer> colQuantita1;
    @FXML private TableColumn<ElencoMinsanLiberaVenditaRowData,BigDecimal> colPrezzoVendita1;
    @FXML private TableView<ElencoMinsanLiberaVenditaRowData> tableHiProfitti;
    @FXML private TableColumn<ElencoMinsanLiberaVenditaRowData,String> colMinsan2;
    @FXML private TableColumn<ElencoMinsanLiberaVenditaRowData,String> colDescrizione2;
    @FXML private TableColumn<ElencoMinsanLiberaVenditaRowData,Integer> colQuantita2;
    @FXML private TableColumn<ElencoMinsanLiberaVenditaRowData,BigDecimal> colPrezzoVendita2;
    @FXML private TableColumn<ElencoMinsanLiberaVenditaRowData,BigDecimal> colMargineMedio2;
    @FXML private TableColumn<ElencoMinsanLiberaVenditaRowData,BigDecimal> colRicaricoMedio2;
    @FXML private TableColumn<ElencoMinsanLiberaVenditaRowData,BigDecimal> colProfittoMedio2;
    @FXML private TableView<ElencoMinsanLiberaVenditaRowData> tableLowProfitti;
    @FXML private TableColumn<ElencoMinsanLiberaVenditaRowData,String> colMinsan3;
    @FXML private TableColumn<ElencoMinsanLiberaVenditaRowData,String> colDescrizione3;
    @FXML private TableColumn<ElencoMinsanLiberaVenditaRowData,Integer> colQuantita3;
    @FXML private TableColumn<ElencoMinsanLiberaVenditaRowData,BigDecimal> colPrezzoVendita3;
    @FXML private TableColumn<ElencoMinsanLiberaVenditaRowData,BigDecimal> colMargineMedio3;
    @FXML private TableColumn<ElencoMinsanLiberaVenditaRowData,BigDecimal> colRicaricoMedio3;
    @FXML private TableColumn<ElencoMinsanLiberaVenditaRowData,BigDecimal> colProfittoMedio3;
    @FXML private DatePicker txtFldDataTo;
    @FXML private DatePicker txtFldDataFrom;
          private ChangeDateListener changeDateListener;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        changeDateListener = new ChangeDateListener(this);
        txtFldDataFrom.setUserData("dataFrom");
        txtFldDataTo.setUserData("dataTo");
        txtFldDataFrom.setOnAction(changeDateListener);
        txtFldDataTo.setOnAction(changeDateListener);

        colMinsan1.setCellValueFactory(new PropertyValueFactory<ElencoMinsanLiberaVenditaRowData,String>("minsan"));
        colDescrizione1.setCellValueFactory(new PropertyValueFactory<ElencoMinsanLiberaVenditaRowData,String>("descrizione"));
        colQuantita1.setCellValueFactory(new PropertyValueFactory<ElencoMinsanLiberaVenditaRowData,Integer>("quantitaTotale"));
        colPrezzoVendita1.setCellValueFactory(new PropertyValueFactory<ElencoMinsanLiberaVenditaRowData,BigDecimal>("prezzoVenditaMedio"));

        colMinsan2.setCellValueFactory(new PropertyValueFactory<ElencoMinsanLiberaVenditaRowData,String>("minsan"));
        colDescrizione2.setCellValueFactory(new PropertyValueFactory<ElencoMinsanLiberaVenditaRowData,String>("descrizione"));
        colQuantita2.setCellValueFactory(new PropertyValueFactory<ElencoMinsanLiberaVenditaRowData,Integer>("quantitaTotale"));
        colPrezzoVendita2.setCellValueFactory(new PropertyValueFactory<ElencoMinsanLiberaVenditaRowData,BigDecimal>("prezzoVenditaMedio"));
        colMargineMedio2.setCellValueFactory(new PropertyValueFactory<ElencoMinsanLiberaVenditaRowData,BigDecimal>("margineMedio"));
        colRicaricoMedio2.setCellValueFactory(new PropertyValueFactory<ElencoMinsanLiberaVenditaRowData,BigDecimal>("ricaricoMedio"));
        colProfittoMedio2.setCellValueFactory(new PropertyValueFactory<ElencoMinsanLiberaVenditaRowData,BigDecimal>("profittoMedio"));

        colMinsan3.setCellValueFactory(new PropertyValueFactory<ElencoMinsanLiberaVenditaRowData,String>("minsan"));
        colDescrizione3.setCellValueFactory(new PropertyValueFactory<ElencoMinsanLiberaVenditaRowData,String>("descrizione"));
        colQuantita3.setCellValueFactory(new PropertyValueFactory<ElencoMinsanLiberaVenditaRowData,Integer>("quantitaTotale"));
        colPrezzoVendita3.setCellValueFactory(new PropertyValueFactory<ElencoMinsanLiberaVenditaRowData,BigDecimal>("prezzoVenditaMedio"));
        colMargineMedio3.setCellValueFactory(new PropertyValueFactory<ElencoMinsanLiberaVenditaRowData,BigDecimal>("margineMedio"));
        colRicaricoMedio3.setCellValueFactory(new PropertyValueFactory<ElencoMinsanLiberaVenditaRowData,BigDecimal>("ricaricoMedio"));
        colProfittoMedio3.setCellValueFactory(new PropertyValueFactory<ElencoMinsanLiberaVenditaRowData,BigDecimal>("profittoMedio"));

        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.ITALY);
        DecimalFormat df = (DecimalFormat)nf;

        colPrezzoVendita1.setCellFactory(new Callback<TableColumn<ElencoMinsanLiberaVenditaRowData, BigDecimal>, TableCell<ElencoMinsanLiberaVenditaRowData, BigDecimal>>() {
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

        colPrezzoVendita2.setCellFactory(new Callback<TableColumn<ElencoMinsanLiberaVenditaRowData, BigDecimal>, TableCell<ElencoMinsanLiberaVenditaRowData, BigDecimal>>() {
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


        colMargineMedio2.setCellFactory(new Callback<TableColumn<ElencoMinsanLiberaVenditaRowData, BigDecimal>, TableCell<ElencoMinsanLiberaVenditaRowData, BigDecimal>>() {
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

        colRicaricoMedio2.setCellFactory(new Callback<TableColumn<ElencoMinsanLiberaVenditaRowData, BigDecimal>, TableCell<ElencoMinsanLiberaVenditaRowData, BigDecimal>>() {
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

        colProfittoMedio2.setCellFactory(new Callback<TableColumn<ElencoMinsanLiberaVenditaRowData, BigDecimal>, TableCell<ElencoMinsanLiberaVenditaRowData, BigDecimal>>() {
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

        colPrezzoVendita3.setCellFactory(new Callback<TableColumn<ElencoMinsanLiberaVenditaRowData, BigDecimal>, TableCell<ElencoMinsanLiberaVenditaRowData, BigDecimal>>() {
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


        colMargineMedio3.setCellFactory(new Callback<TableColumn<ElencoMinsanLiberaVenditaRowData, BigDecimal>, TableCell<ElencoMinsanLiberaVenditaRowData, BigDecimal>>() {
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

        colRicaricoMedio3.setCellFactory(new Callback<TableColumn<ElencoMinsanLiberaVenditaRowData, BigDecimal>, TableCell<ElencoMinsanLiberaVenditaRowData, BigDecimal>>() {
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

        colProfittoMedio3.setCellFactory(new Callback<TableColumn<ElencoMinsanLiberaVenditaRowData, BigDecimal>, TableCell<ElencoMinsanLiberaVenditaRowData, BigDecimal>>() {
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

        itemList(tableQuantita,tableHiProfitti,tableLowProfitti);

    }


    private void itemList(TableView tableQuantita,TableView tableHiProfitti, TableView tableLowProfitti){
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
        ArrayList<ElencoMinsanLiberaVenditaRowData> elencoTableQuantita = ElencoMinsanLiberaVenditaRowDataDAOManager.lookUpElencoMinsanBetweenDateOrderByQuantDescLimit10(fromDate,toDate);
        ArrayList<ElencoMinsanLiberaVenditaRowData> elencoTableHiProfitto = ElencoMinsanLiberaVenditaRowDataDAOManager.lookUpElencoMinsanBetweenDateOrderByProfittoDescLimit5(fromDate,toDate);
        ArrayList<ElencoMinsanLiberaVenditaRowData> elencotableLowProfitto = ElencoMinsanLiberaVenditaRowDataDAOManager.lookUpElencoMinsanBetweenDateOrderByProfittoAscLimit5(fromDate,toDate);

        tableQuantita.getItems().setAll(FXCollections.observableArrayList(elencoTableQuantita));
        tableHiProfitti.getItems().setAll(FXCollections.observableArrayList(elencoTableHiProfitto));
        tableLowProfitti.getItems().setAll(FXCollections.observableArrayList(elencotableLowProfitto));
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

    }

}

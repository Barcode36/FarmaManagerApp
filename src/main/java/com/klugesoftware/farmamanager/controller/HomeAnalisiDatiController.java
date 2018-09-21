package com.klugesoftware.farmamanager.controller;

import com.klugesoftware.farmamanager.DTO.ElencoMinsanLiberaVenditaRowData;
import com.klugesoftware.farmamanager.db.ElencoMinsanLiberaVenditaRowDataDAOManager;
import com.klugesoftware.farmamanager.db.ImportazioniDAOManager;
import com.klugesoftware.farmamanager.model.Importazioni;
import com.klugesoftware.farmamanager.utility.DateUtility;
import javafx.collections.FXCollections;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.*;

public class HomeAnalisiDatiController extends VenditeEProfittiController implements Initializable {


    private final Logger logger = LogManager.getLogger(HomeAnalisiDatiController.class.getName());
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
    @FXML private RadioButton rdtBtnVistaSettimanale;
    @FXML private RadioButton rdtBtnVistaMensile;
    @FXML private Label lblPeriodo;
    @FXML private Button btnBack;
    @FXML private Button btnForward;
    @FXML private Button settingsButton;
    @FXML private Button btnAggMov;
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

        colQuantita1.setCellFactory(new Callback<TableColumn<ElencoMinsanLiberaVenditaRowData, Integer>, TableCell<ElencoMinsanLiberaVenditaRowData, Integer>>() {
            @Override
            public TableCell<ElencoMinsanLiberaVenditaRowData, Integer> call(TableColumn<ElencoMinsanLiberaVenditaRowData, Integer> param) {
                return new TableCell<ElencoMinsanLiberaVenditaRowData,Integer>(){
                    @Override
                    public void updateItem(Integer item,boolean empty){
                        if (item == null || empty) {
                            setText(null);
                            this.getStyleClass().remove("clnProfitto");
                        } else {
                            setText(df.format(item));
                            this.getStyleClass().add("clnProfitto");
                        }
                    }
                };
            }
        });


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
                            this.getStyleClass().add("clnProfitto");
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
                            this.getStyleClass().add("clnProfitto");
                        }
                    }
                };
            }
        });

        itemList(tableQuantita,tableHiProfitti,tableLowProfitti);
        aggiornaMovimenti();
    }

    private void itemList(TableView tableQuantita,TableView tableHiProfitti, TableView tableLowProfitti){
        Date toDate;
        Date fromDate;

        Importazioni importazione = ImportazioniDAOManager.findUltimoInsert();
        if (importazione.getIdImportazione() != null) {
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

            txtFldDataFrom.setOnAction(changeDateAndViewListener);
            txtFldDataTo.setOnAction(changeDateAndViewListener);

            aggiornaTableAndScene(fromDate, toDate, false);
        }
    }

    private void aggiornaMovimenti(){
        btnAggMov.setVisible(false);
        Importazioni importazioni = ImportazioniDAOManager.findUltimoInsert();
        if (importazioni.getIdImportazione()!= null) {
            Calendar dateTo = Calendar.getInstance(Locale.ITALY);
            Calendar dateFrom = Calendar.getInstance(Locale.ITALY);
            dateFrom.setTime(importazioni.getDataUltimoMovImportato());
            dateTo.set(Calendar.DAY_OF_MONTH, dateTo.get(Calendar.DAY_OF_MONTH) - 1);
            if (dateFrom.before(dateTo)) {
                btnAggMov.setVisible(true);
            }
        }
    }

    @FXML
    private void aggMovimentiClicked(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conferma Importazione");
        alert.setHeaderText("L'ultimo movimento importato risale al "+DateUtility.converteDateToGUIStringDDMMYYYY(ImportazioniDAOManager.findUltimoInsert().getDataUltimoMovImportato()));
        alert.setContentText("Vuoi aggiornare i movimenti?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/klugesoftware/farmamanager/view/Settings.fxml"));
                Parent parent = (Parent)fxmlLoader.load();
                SettingsController controller = fxmlLoader.getController();
                controller.fireButton();
                Scene scene = new Scene(parent);
                Stage app_stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                app_stage.setScene(scene);
                app_stage.show();
            }catch(Exception ex){
                logger.error(ex.getMessage());
            }
        } else {
            ;
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
    public RadioButton getRdbtVistaMensile() {
        return rdtBtnVistaMensile;
    }

    @Override
    public RadioButton getRdbtVistaSettimanale() {
        return rdtBtnVistaSettimanale;
    }

    @Override
    public void aggiornaTableAndScene(Date dateFrom, Date dateTo, boolean vistaSettimanale) {
        aggiornaTable(dateFrom,dateTo);
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

        txtFldDataFrom.setOnAction(null);
        txtFldDataTo.setOnAction(null);
        Calendar myCal = Calendar.getInstance(Locale.ITALY);
        myCal.setTime(dateFrom);
        txtFldDataFrom.setValue(LocalDate.of(myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH)+1,myCal.get(Calendar.DAY_OF_MONTH)));
        txtFldDataFrom.getEditor().setText(DateUtility.converteDateToGUIStringDDMMYYYY(dateFrom));
        myCal.setTime(dateTo);
        txtFldDataTo.setValue(LocalDate.of(myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH)+1,myCal.get(Calendar.DAY_OF_MONTH)));
        txtFldDataTo.getEditor().setText(DateUtility.converteDateToGUIStringDDMMYYYY(dateTo));
        txtFldDataFrom.setOnAction(changeDateAndViewListener);
        txtFldDataTo.setOnAction(changeDateAndViewListener);


        ArrayList<ElencoMinsanLiberaVenditaRowData> elencoTableQuantita = ElencoMinsanLiberaVenditaRowDataDAOManager.lookUpElencoMinsanBetweenDateOrderByQuantDescLimit10(dateFrom,dateTo);
        ArrayList<ElencoMinsanLiberaVenditaRowData> elencoTableHiProfitto = ElencoMinsanLiberaVenditaRowDataDAOManager.lookUpElencoMinsanBetweenDateOrderByProfittoDescLimit5(dateFrom,dateTo);
        ArrayList<ElencoMinsanLiberaVenditaRowData> elencotableLowProfitto = ElencoMinsanLiberaVenditaRowDataDAOManager.lookUpElencoMinsanBetweenDateOrderByProfittoAscLimit5(dateFrom,dateTo);

        tableQuantita.getItems().setAll(FXCollections.observableArrayList(elencoTableQuantita));
        tableHiProfitti.getItems().setAll(FXCollections.observableArrayList(elencoTableHiProfitto));
        tableLowProfitti.getItems().setAll(FXCollections.observableArrayList(elencotableLowProfitto));

    }

    @FXML
    private void esciClicked(ActionEvent event){
        System.exit(0);
    }

    @FXML
    private void venditeLibereClicked(ActionEvent event){
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/klugesoftware/farmamanager/view/SituazioneVenditeEProfittiLibere.fxml"));
            Parent parent = (Parent)fxmlLoader.load();
            SituazioneVenditeEProfittiLibereController controller = fxmlLoader.getController();
            Scene scene = new Scene(parent);
            Stage app_stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            app_stage.setScene(scene);
            app_stage.show();
        }catch(Exception ex){
            logger.error(ex.getMessage());
        }

    }

    @FXML
    private void venditeTotaliClicked(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/klugesoftware/farmamanager/view/SituazioneVenditeEProfitti.fxml"));
            Parent parent = (Parent)fxmlLoader.load();
            SituazioneVenditeEProfittiController controller = fxmlLoader.getController();
            Scene scene = new Scene(parent);
            Stage app_stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            app_stage.setScene(scene);
            app_stage.show();
        }catch(Exception ex){
            logger.error(ex);
        }

    }

    @FXML
    private void settingsClicked(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/klugesoftware/farmamanager/view/Settings.fxml"));
            Parent parent = (Parent)fxmlLoader.load();
            SettingsController controller = fxmlLoader.getController();
            Scene scene = new Scene(parent);
            Stage app_stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            app_stage.setScene(scene);
            app_stage.show();
        }catch(Exception ex){
            logger.error(ex.getMessage());
        }
    }
}

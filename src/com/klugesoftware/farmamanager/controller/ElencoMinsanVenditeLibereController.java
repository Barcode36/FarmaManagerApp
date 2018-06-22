package com.klugesoftware.farmamanager.controller;

import com.klugesoftware.farmamanager.DTO.ElencoMinsanLiberaVenditaRowData;
import com.klugesoftware.farmamanager.DTO.ElencoTotaliGiornalieriRowData;
import com.klugesoftware.farmamanager.db.ElencoMinsanLiberaVenditaRowDataDAOManager;
import com.klugesoftware.farmamanager.db.ElencoTotaliGiornalieriRowDataManager;
import com.klugesoftware.farmamanager.db.ImportazioniDAOManager;
import com.klugesoftware.farmamanager.model.CustomRoundingAndScaling;
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

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.*;

public class ElencoMinsanVenditeLibereController extends ElencoMinsanController implements Initializable {

    @FXML private TableView<ElencoMinsanLiberaVenditaRowData> tableElencoMinsan;
    @FXML private TableColumn<ElencoMinsanLiberaVenditaRowData,String> colMinsan;
    @FXML private TableColumn<ElencoMinsanLiberaVenditaRowData,String> colDescrizione;
    @FXML private TableColumn<ElencoMinsanLiberaVenditaRowData,Integer> colQuantita;
    @FXML private TableColumn<ElencoMinsanLiberaVenditaRowData,BigDecimal> colPrezzoVenditaMedio;
    @FXML private TableColumn<ElencoMinsanLiberaVenditaRowData,BigDecimal> colMargineMedio;
    @FXML private TableColumn<ElencoMinsanLiberaVenditaRowData,BigDecimal> colRicaricoMedio;
    @FXML private TableColumn<ElencoMinsanLiberaVenditaRowData,BigDecimal> colProfittoMedio;
    @FXML private TextField txtSearch;
    @FXML private DatePicker txtFldDataFrom;
    @FXML private DatePicker txtFldDataTo;
          private ChangeDateListener changeDateListener;
    @FXML private PieChart graficoComposizioneRicarico;


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
        aggiornaGrafico(elenco);
        return FXCollections.observableArrayList(elenco);

    }

    private void aggiornaGrafico(ArrayList<ElencoMinsanLiberaVenditaRowData> elencoMinsanLiberaVenditaRowData){
        ElencoMinsanLiberaVenditaRowData rowData;
        Iterator<ElencoMinsanLiberaVenditaRowData> iter = elencoMinsanLiberaVenditaRowData.iterator();

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        double fascia1 = 0;
        double fascia2 = 10;
        double fascia3 = 20;
        double fascia4 = 30;
        double valFascia1=0;
        double valFascia2=0;
        double valFascia3=0;
        double valFascia4=0;
        double valFascia5=0;
        double ricarico;

        while(iter.hasNext()){
            rowData = iter.next();
            ricarico = rowData.getRicaricoMedio().doubleValue();
            if(ricarico <= fascia1)
                valFascia1++;
            else
                if(ricarico <= fascia2)
                    valFascia2++;
                else
                    if(ricarico <= fascia3)
                        valFascia3++;
                    else
                        if (ricarico <= fascia4)
                            valFascia4++;
                        else
                            valFascia5++;
        }

        pieChartData.add(new PieChart.Data("minore di 0%",valFascia1));
        pieChartData.add(new PieChart.Data("da 0 a 10 % ",valFascia2));
        pieChartData.add(new PieChart.Data("da 10 a 20 % ",valFascia3));
        pieChartData.add(new PieChart.Data("da 20 a 30 % ",valFascia4));
        pieChartData.add(new PieChart.Data("maggiore di 30 % ",valFascia5));
        graficoComposizioneRicarico.setTitle("Composizione Ricarico\ndal: "+txtFldDataFrom.getEditor().getText()+"\nal: "+txtFldDataTo.getEditor().getText());
        graficoComposizioneRicarico.getData().clear();
        graficoComposizioneRicarico.setData(pieChartData);

    }

    @FXML
    private void listenerEsciButton(javafx.event.ActionEvent event){

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
        aggiornaGrafico(elenco);
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
            ex.printStackTrace();
        }
    }

    @FXML
    private void dettagliClicked(ActionEvent event){

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/ElencoDettagliatoMinsanVenditeLibere.fxml"));
            Parent parent = (Parent) fxmlLoader.load();
            ElencoDettagliatoMinsanVenditeLibereController controller = fxmlLoader.getController();
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

    @FXML
    private void goBackClicked(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/SituazioneVenditeEProfittiLibere.fxml"));
            Parent parent = (Parent) fxmlLoader.load();
            SituazioneVenditeEProfittiLibereController controller = fxmlLoader.getController();
            controller.aggiornaTableAndScene(getDateFrom(),getDateTo(),false);
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



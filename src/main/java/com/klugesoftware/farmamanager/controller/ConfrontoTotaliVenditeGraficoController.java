package com.klugesoftware.farmamanager.controller;

import com.klugesoftware.farmamanager.DTO.ConfrontoTotaliVenditeRowData;
import com.klugesoftware.farmamanager.DTO.ConfrontoTotaliVenditeRows;
import com.klugesoftware.farmamanager.db.ImportazioniDAOManager;
import com.klugesoftware.farmamanager.model.Importazioni;
import com.klugesoftware.farmamanager.utility.DateUtility;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class ConfrontoTotaliVenditeGraficoController implements Initializable{

    private final Logger logger = LogManager.getLogger(ConfrontoTotaliVenditeGraficoController.class.getName());
    @FXML private BarChart<String,Number> graficoConfronto;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;
    @FXML private DatePicker txtFldDataFrom;
    @FXML private DatePicker txtFldDataTo;
    @FXML private RadioButton rdtBtnAnnoPrecedente;
    @FXML private RadioButton rdtBtnMesePrecedente;
    @FXML private ToggleGroup periodoDiConfronto;
    @FXML private ComboBox<String> comboMeseDaConfrontare;
    @FXML private TextArea txtAreaPeriodiConfrontati;
    private final String annoPrecedente = "annoPrecedente";
    private final String mesePrecedente = "mesePrecedente";
    private String periodoAttuale;
    private String periodoPrecedente;
    private String testoArea;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rdtBtnAnnoPrecedente.setUserData(annoPrecedente);
        rdtBtnMesePrecedente.setUserData(mesePrecedente);
        periodoDiConfronto.selectedToggleProperty().addListener(new ListenerCambioPeriodoConfronto());
        ArrayList<String> mesiComboBox = new ArrayList<String>();
        mesiComboBox.add("seleziona il mese da analizzare");
        mesiComboBox.add("GENNAIO");
        mesiComboBox.add("FEBBRAIO");
        mesiComboBox.add("MARZO");
        mesiComboBox.add("APRILE");
        mesiComboBox.add("MAGGIO");
        mesiComboBox.add("GIUGNO");
        mesiComboBox.add("LUGLIO");
        mesiComboBox.add("AGOSTO");
        mesiComboBox.add("SETTEMBRE");
        mesiComboBox.add("OTTOBRE");
        mesiComboBox.add("NOVEMBRE");
        mesiComboBox.add("DICEMBRE");

        comboMeseDaConfrontare.setItems(FXCollections.observableList(mesiComboBox));

    }

    public void init(boolean annoPrecedente, int meseDaConfrontare, ConfrontoTotaliVenditeRows rows){

        Date dateFrom = rows.getDateFrom();
        Date dateTo = rows.getDateTo();

        Calendar myCal = Calendar.getInstance(Locale.ITALY);
        myCal.setTime(dateFrom);
        txtFldDataFrom.setValue(LocalDate.of(myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH)+1,myCal.get(Calendar.DAY_OF_MONTH)));
        myCal.setTime(dateTo);
        txtFldDataTo.setValue(LocalDate.of(myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH)+1,myCal.get(Calendar.DAY_OF_MONTH)));
        txtFldDataFrom.getEditor().setText(DateUtility.converteDateToGUIStringDDMMYYYY(dateFrom));
        txtFldDataTo.getEditor().setText(DateUtility.converteDateToGUIStringDDMMYYYY(dateTo));

        periodoAttuale = DateUtility.converteDateToGUIStringDDMMYYYY(dateFrom)+"-"+DateUtility.converteDateToGUIStringDDMMYYYY(dateTo);
        periodoPrecedente = DateUtility.converteDateToGUIStringDDMMYYYY(rows.getDateFromPrec())+"-"+DateUtility.converteDateToGUIStringDDMMYYYY(rows.getDateToPrec());
        testoArea = "Periodi confrontati:"+"\n\nprecedente: "+periodoPrecedente+"\n\nattuale:         "+periodoAttuale;
        txtAreaPeriodiConfrontati.setText(testoArea);

        comboMeseDaConfrontare.getSelectionModel().select(meseDaConfrontare);

        if (annoPrecedente)
            rdtBtnAnnoPrecedente.setSelected(true);
        else
            rdtBtnMesePrecedente.setSelected(true);

        initGrafico(rows);

    }

    private void initData(Date dateFrom, Date dateTo, Date dateFromPrec, Date dateToPrec){
        ConfrontoTotaliVenditeRows rows = new ConfrontoTotaliVenditeRows(dateFrom,dateTo,dateFromPrec,dateToPrec);
        ObservableList<ConfrontoTotaliVenditeRowData> data = FXCollections.observableList(rows.getRows());

        periodoAttuale = DateUtility.converteDateToGUIStringDDMMYYYY(dateFrom)+"-"+DateUtility.converteDateToGUIStringDDMMYYYY(dateTo);
        periodoPrecedente = DateUtility.converteDateToGUIStringDDMMYYYY(dateFromPrec)+"-"+DateUtility.converteDateToGUIStringDDMMYYYY(dateToPrec);
        testoArea = "Periodi confrontati:"+"\n\nprecedente: "+periodoPrecedente+"\n\nattuale:         "+periodoAttuale;
        txtAreaPeriodiConfrontati.setText(testoArea);

        initGrafico(rows);
    }

    private void initGrafico(ConfrontoTotaliVenditeRows confrontoTotaliVenditeRows){


        String periodoAttuale = "periodo attuale: "+DateUtility.converteDateToGUIStringDDMMYYYY(confrontoTotaliVenditeRows.getDateFrom())+"-"+DateUtility.converteDateToGUIStringDDMMYYYY(confrontoTotaliVenditeRows.getDateTo());
        String periodoPrecedente = "periodo precedente: "+DateUtility.converteDateToGUIStringDDMMYYYY(confrontoTotaliVenditeRows.getDateFromPrec())+"-"+DateUtility.converteDateToGUIStringDDMMYYYY(confrontoTotaliVenditeRows.getDateToPrec());
        String profittoLibere = "ProfittoLibere";
        String venditeLibere = "VenditeLibere";
        String scontiLibere = "ScontiLibere";
        String costiLibere = "CostiLibere";
        String profittoSSN = "ProfittoSSN";
        String venditeSSN = "VenditeSSN";
        String scontiSSN = "ScontiSSN";
        String costiSSN = "CostiSSN";
        String profittoTotale = "ProfittoTotale";
        String venditeTotali = "VenditeTotali";
        String scontiTotali = "ScontiTotali";
        String costiTotali = "CostiTotali";



        ArrayList<ConfrontoTotaliVenditeRowData> rowsData = confrontoTotaliVenditeRows.getRows();
        XYChart.Series seriesPeriodoAttuale = new XYChart.Series();
        seriesPeriodoAttuale.setName(periodoAttuale);
        seriesPeriodoAttuale.getData().add(new XYChart.Data<>(venditeLibere,rowsData.get(0).getValTotaleLibere()));
        seriesPeriodoAttuale.getData().add(new XYChart.Data<>(scontiLibere,rowsData.get(1).getValTotaleLibere()));
        seriesPeriodoAttuale.getData().add(new XYChart.Data<>(costiLibere,rowsData.get(4).getValTotaleLibere()));
        seriesPeriodoAttuale.getData().add(new XYChart.Data<>(profittoLibere,rowsData.get(5).getValTotaleLibere()));

        seriesPeriodoAttuale.getData().add(new XYChart.Data<>(venditeSSN,rowsData.get(0).getValTotaleSSN()));
        seriesPeriodoAttuale.getData().add(new XYChart.Data<>(scontiSSN,rowsData.get(1).getValTotaleSSN()));
        seriesPeriodoAttuale.getData().add(new XYChart.Data<>(costiSSN,rowsData.get(4).getValTotaleSSN()));
        seriesPeriodoAttuale.getData().add(new XYChart.Data<>(profittoSSN,rowsData.get(5).getValTotaleSSN()));

        seriesPeriodoAttuale.getData().add(new XYChart.Data<>(venditeTotali,rowsData.get(0).getValTotale()));
        seriesPeriodoAttuale.getData().add(new XYChart.Data<>(scontiTotali,rowsData.get(1).getValTotaleSSN()));
        seriesPeriodoAttuale.getData().add(new XYChart.Data<>(costiTotali,rowsData.get(4).getValTotale()));
        seriesPeriodoAttuale.getData().add(new XYChart.Data<>(profittoTotale,rowsData.get(5).getValTotale()));


        XYChart.Series seriesPeriodoPrecedente = new XYChart.Series();
        seriesPeriodoPrecedente.setName(periodoPrecedente);

        seriesPeriodoPrecedente.getData().add(new XYChart.Data<>(venditeLibere,rowsData.get(0).getValTotaleLiberePrecedenti()));
        seriesPeriodoPrecedente.getData().add(new XYChart.Data<>(scontiLibere,rowsData.get(1).getValTotaleLiberePrecedenti()));
        seriesPeriodoPrecedente.getData().add(new XYChart.Data<>(costiLibere,rowsData.get(4).getValTotaleLiberePrecedenti()));
        seriesPeriodoPrecedente.getData().add(new XYChart.Data<>(profittoLibere,rowsData.get(5).getValTotaleLiberePrecedenti()));

        seriesPeriodoPrecedente.getData().add(new XYChart.Data<>(venditeSSN,rowsData.get(0).getValTotaleSSNPrecedente()));
        seriesPeriodoPrecedente.getData().add(new XYChart.Data<>(scontiSSN,rowsData.get(1).getValTotaleSSNPrecedente()));
        seriesPeriodoPrecedente.getData().add(new XYChart.Data<>(costiSSN,rowsData.get(4).getValTotaleSSNPrecedente()));
        seriesPeriodoPrecedente.getData().add(new XYChart.Data<>(profittoSSN,rowsData.get(5).getValTotaleSSNPrecedente()));

        seriesPeriodoPrecedente.getData().add(new XYChart.Data<>(venditeTotali,rowsData.get(0).getValTotalePrecedente()));
        seriesPeriodoPrecedente.getData().add(new XYChart.Data<>(scontiTotali,rowsData.get(1).getValTotalePrecedente()));
        seriesPeriodoPrecedente.getData().add(new XYChart.Data<>(costiTotali,rowsData.get(4).getValTotalePrecedente()));
        seriesPeriodoPrecedente.getData().add(new XYChart.Data<>(profittoTotale,rowsData.get(5).getValTotalePrecedente()));

        graficoConfronto.getData().clear();
        graficoConfronto.getData().addAll(seriesPeriodoPrecedente,seriesPeriodoAttuale);

    }

    /**
     *
     * @param dateFrom
     * @param dateTo
     * @param annoPrecedente
     *
     * Setting dei campi dataFrom e DateTo;
     * se annoPrecedente = true allora inizializzo la tabella( del confronto dei Totali)
     * con i totali riferiti ai seguenti intervalli:
     *      - periodo attuale: dateFrom-dateTo
     *      - periodo precedente: dateFrom-dateTo ANNO PRECEDENTE
     *
     * se annoPrecedente = false allora il periodo precedente si riferisce al MESE PRECEDENTE.
     */
    public void setIntervalloMensile(Date dateFrom, Date dateTo,boolean annoPrecedente){

        //set the date, set the comboBox and radio button; at init I must set the monthly period VS monthly period of the year before

        Date firstDay = DateUtility.primoGiornoDelMeseCorrente(dateFrom);
        Date lastDay = DateUtility.ultimoGiornoDelMeseCorrente(dateTo);

        Importazioni importazione = new Importazioni();
        importazione = ImportazioniDAOManager.findUltimoInsert();
        Date lastUpdate = importazione.getDataUltimoMovImportato();

        if(lastUpdate.before(lastDay))
            lastDay = lastUpdate;

        Calendar myCal = Calendar.getInstance(Locale.ITALY);
        myCal.setTime(firstDay);
        txtFldDataFrom.setValue(LocalDate.of(myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH)+1,myCal.get(Calendar.DAY_OF_MONTH)));
        myCal.setTime(lastDay);
        txtFldDataTo.setValue(LocalDate.of(myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH)+1,myCal.get(Calendar.DAY_OF_MONTH)));
        txtFldDataFrom.getEditor().setText(DateUtility.converteDateToGUIStringDDMMYYYY(firstDay));
        txtFldDataTo.getEditor().setText(DateUtility.converteDateToGUIStringDDMMYYYY(lastDay));


        int meseSelezionato = myCal.get(Calendar.MONTH)+1;
        comboMeseDaConfrontare.getSelectionModel().select(meseSelezionato);

        if (annoPrecedente)
            rdtBtnAnnoPrecedente.setSelected(true);
        else
            rdtBtnMesePrecedente.setSelected(true);

        Date[] datesBefore = DateUtility.datesBefore(firstDay,lastDay,annoPrecedente);

        initData(firstDay,lastDay,datesBefore[0],datesBefore[1]);
    }

    /**
     *
     * @param event
     * Listener clickDataFrom: setto il valore del txtDataTo com ultimo del mese del giorno
     * selezionato in txtDataFrom e aggiorno i valori
     */
    @FXML
    private void clickedDataFrom(ActionEvent event){

        //TODO: gestire il caso in cui la dataFrom è posteriore all'ultima data importazione: avvertendo l'utente con DialogBox?
        Date firstDay = DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataFrom.getEditor().getText());
        Date lastDay = DateUtility.ultimoGiornoDelMeseCorrente(firstDay);

        Importazioni importazione = new Importazioni();
        importazione = ImportazioniDAOManager.findUltimoInsert();
        Date lastUpdate = importazione.getDataUltimoMovImportato();

        if(lastUpdate.before(lastDay))
            lastDay = lastUpdate;


        Calendar myCal = Calendar.getInstance(Locale.ITALY);
        myCal.setTime(firstDay);
        txtFldDataFrom.setValue(LocalDate.of(myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH)+1,myCal.get(Calendar.DAY_OF_MONTH)));
        myCal.setTime(lastDay);
        txtFldDataTo.setValue(LocalDate.of(myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH)+1,myCal.get(Calendar.DAY_OF_MONTH)));
        txtFldDataFrom.getEditor().setText(DateUtility.converteDateToGUIStringDDMMYYYY(firstDay));
        txtFldDataTo.getEditor().setText(DateUtility.converteDateToGUIStringDDMMYYYY(lastDay));

        int meseSelezionato = myCal.get(Calendar.MONTH)+1;
        comboMeseDaConfrontare.getSelectionModel().select(meseSelezionato);

        Date[] datesBefore;
        if (rdtBtnAnnoPrecedente.isSelected())
            datesBefore = DateUtility.datesBefore(firstDay,lastDay,true);
        else
            datesBefore = DateUtility.datesBefore(firstDay,lastDay,false);

        initData(firstDay,lastDay,datesBefore[0],datesBefore[1]);

    }

    @FXML
    private void clickedDataTo(ActionEvent event){
        Date firstDay = DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataFrom.getEditor().getText());
        Date lastDay = DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataTo.getEditor().getText());

        Importazioni importazione = new Importazioni();
        importazione = ImportazioniDAOManager.findUltimoInsert();
        Date lastUpdate = importazione.getDataUltimoMovImportato();

        if(lastUpdate.before(lastDay))
            lastDay = lastUpdate;


        Calendar myCal = Calendar.getInstance(Locale.ITALY);
        myCal.setTime(firstDay);
        txtFldDataFrom.setValue(LocalDate.of(myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH)+1,myCal.get(Calendar.DAY_OF_MONTH)));
        myCal.setTime(lastDay);
        txtFldDataTo.setValue(LocalDate.of(myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH)+1,myCal.get(Calendar.DAY_OF_MONTH)));
        txtFldDataFrom.getEditor().setText(DateUtility.converteDateToGUIStringDDMMYYYY(firstDay));
        txtFldDataTo.getEditor().setText(DateUtility.converteDateToGUIStringDDMMYYYY(lastDay));

        int meseSelezionato = myCal.get(Calendar.MONTH)+1;
        comboMeseDaConfrontare.getSelectionModel().select(meseSelezionato);

        Date[] datesBefore;
        if (rdtBtnAnnoPrecedente.isSelected())
            datesBefore = DateUtility.datesBefore(firstDay,lastDay,true);
        else
            datesBefore = DateUtility.datesBefore(firstDay,lastDay,false);

        initData(firstDay,lastDay,datesBefore[0],datesBefore[1]);

    }

    @FXML
    private void clickedMeseDaConfrontare(ActionEvent event){

        int meseSelezionato = comboMeseDaConfrontare.getSelectionModel().getSelectedIndex();
        if (meseSelezionato == 0) return;

        Calendar myCal = Calendar.getInstance(Locale.ITALY);
        myCal.set(Calendar.DAY_OF_MONTH,1);
        myCal.set(Calendar.MONTH,meseSelezionato-1);
        txtFldDataFrom.setValue(LocalDate.of(myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH)+1,myCal.get(Calendar.DAY_OF_MONTH)));

    }


    @FXML
    private void goBackClicked(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/klugesoftware/farmamanager/view/ConfrontoTotaliVendite.fxml"));
            Parent parent = (Parent) fxmlLoader.load();
            ConfrontoTotaliVenditeController controller = fxmlLoader.getController();
            if(rdtBtnAnnoPrecedente.isSelected())
                controller.setIntervalloMensile(DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataFrom.getEditor().getText()),DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataTo.getEditor().getText()),true);
            else
                controller.setIntervalloMensile(DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataFrom.getEditor().getText()),DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataTo.getEditor().getText()),false);
            Scene scene = new Scene(parent);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(scene);
            app_stage.show();
        }catch(Exception ex){
            logger.error(ex.getMessage());
        }
    }

    class ListenerCambioPeriodoConfronto implements ChangeListener<Toggle> {

        ListenerCambioPeriodoConfronto() {
        }

        @Override
        public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
            if(periodoDiConfronto.getSelectedToggle() != null){
                txtFldDataFrom.fireEvent(new ActionEvent());
            }
        }
    }
}

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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;



public class ConfrontoTotaliVenditeController implements Initializable {

    private final Logger logger = LogManager.getLogger(ConfrontoTotaliVenditeController.class.getName());
    private final String annoPrecedente = "annoPrecedente";
    private final String mesePrecedente = "mesePrecedente";
    @FXML private TableColumn<ConfrontoTotaliVenditeRowData,String> colDescrizione;
    @FXML private TableColumn<ConfrontoTotaliVenditeRowData,BigDecimal> colTotaleLibere;
    @FXML private TableColumn<ConfrontoTotaliVenditeRowData,BigDecimal> colTotaleLiberePrecedente;
    @FXML private TableColumn<ConfrontoTotaliVenditeRowData,String> colDiffPercLibere;
    @FXML private TableColumn<ConfrontoTotaliVenditeRowData,BigDecimal> colTotaliSSN;
    @FXML private TableColumn<ConfrontoTotaliVenditeRowData,BigDecimal> colTotaliSSNPrecedente;
    @FXML private TableColumn<ConfrontoTotaliVenditeRowData,String> colDiffPercSSN;
    @FXML private TableColumn<ConfrontoTotaliVenditeRowData,BigDecimal> colTotaliVendite;
    @FXML private TableColumn<ConfrontoTotaliVenditeRowData,BigDecimal> colTotaliVenditePrecedente;
    @FXML private TableColumn<ConfrontoTotaliVenditeRowData,String> colDiffPercTotali;
    @FXML private TableView<ConfrontoTotaliVenditeRowData> tabellaTotali;
    @FXML private DatePicker txtFldDataFrom;
    @FXML private DatePicker txtFldDataTo;
    @FXML private RadioButton rdtBtnAnnoPrecedente;
    @FXML private RadioButton rdtBtnMesePrecedente;
    @FXML private ToggleGroup periodoDiConfronto;
    @FXML private ComboBox<String> comboMeseDaConfrontare;
    @FXML private TextArea txtAreaPeriodiConfrontati;
    private String periodoAttuale;
    private String periodoPrecedente;
    private String testoArea;
    private ConfrontoTotaliVenditeRows rows;

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

        colDescrizione.setCellValueFactory(new PropertyValueFactory<ConfrontoTotaliVenditeRowData,String>("colDescrizione"));
        colTotaleLibere.setCellValueFactory(new PropertyValueFactory<ConfrontoTotaliVenditeRowData,BigDecimal>("totaleLibere"));
        colTotaleLiberePrecedente.setCellValueFactory(new PropertyValueFactory<ConfrontoTotaliVenditeRowData,BigDecimal>("totaleLiberePrec"));
        colDiffPercLibere.setCellValueFactory(new PropertyValueFactory<ConfrontoTotaliVenditeRowData,String>("diffPercLibere"));
        colTotaliSSN.setCellValueFactory(new PropertyValueFactory<ConfrontoTotaliVenditeRowData,BigDecimal>("totaleSSN"));
        colTotaliSSNPrecedente.setCellValueFactory(new PropertyValueFactory<ConfrontoTotaliVenditeRowData,BigDecimal>("totaleSSNPrecedente"));
        colDiffPercSSN.setCellValueFactory(new PropertyValueFactory<ConfrontoTotaliVenditeRowData,String>("diffPercSSN"));
        colTotaliVendite.setCellValueFactory(new PropertyValueFactory<ConfrontoTotaliVenditeRowData,BigDecimal>("totale"));
        colTotaliVenditePrecedente.setCellValueFactory(new PropertyValueFactory<ConfrontoTotaliVenditeRowData,BigDecimal>("totalePrecedente"));
        colDiffPercTotali.setCellValueFactory(new PropertyValueFactory<ConfrontoTotaliVenditeRowData,String>("diffPercTotale"));

        colDiffPercLibere.setCellFactory(new Callback<TableColumn<ConfrontoTotaliVenditeRowData, String>, TableCell<ConfrontoTotaliVenditeRowData, String>>() {
            @Override
            public TableCell<ConfrontoTotaliVenditeRowData, String> call(TableColumn<ConfrontoTotaliVenditeRowData, String> param) {
                return new TableCell<ConfrontoTotaliVenditeRowData,String>(){
                    @Override
                    public void updateItem(String item,boolean empty){
                        if(item != null)
                            if (item.contains("+")) {
                                setText(item.toString());
                                this.getStyleClass().add("confrontoTableUp");
                            }else {
                                setText(item.toString());
                                this.getStyleClass().add("confrontoTableDown");
                            }
                    }
                };
            }
        });
        colDiffPercSSN.setCellFactory(new Callback<TableColumn<ConfrontoTotaliVenditeRowData, String>, TableCell<ConfrontoTotaliVenditeRowData, String>>() {
            @Override
            public TableCell<ConfrontoTotaliVenditeRowData, String> call(TableColumn<ConfrontoTotaliVenditeRowData, String> param) {
                return new TableCell<ConfrontoTotaliVenditeRowData,String>(){
                    @Override
                    public void updateItem(String item,boolean empty){
                        if(item != null)
                            if (item.contains("+")) {
                                setText(item.toString());
                                this.getStyleClass().add("confrontoTableUp");
                            }else {
                                setText(item.toString());
                                this.getStyleClass().add("confrontoTableDown");
                            }
                    }
                };
            }
        });

        colDiffPercTotali.setCellFactory(new Callback<TableColumn<ConfrontoTotaliVenditeRowData, String>, TableCell<ConfrontoTotaliVenditeRowData, String>>() {
            @Override
            public TableCell<ConfrontoTotaliVenditeRowData, String> call(TableColumn<ConfrontoTotaliVenditeRowData, String> param) {
                return new TableCell<ConfrontoTotaliVenditeRowData,String>(){
                    @Override
                    public void updateItem(String item,boolean empty){
                        if(item != null)
                            if (item.contains("+")) {
                                setText(item.toString());
                                this.getStyleClass().add("confrontoTableUp");
                            }else {
                                setText(item.toString());
                                this.getStyleClass().add("confrontoTableDown");
                            }
                    }
                };
            }
        });

    }

    private void intiTabella(Date dateFrom, Date dateTo, Date dateFromPrec, Date dateToPrec){

        rows = new ConfrontoTotaliVenditeRows(dateFrom,dateTo,dateFromPrec,dateToPrec);
        ObservableList<ConfrontoTotaliVenditeRowData> data = FXCollections.observableList(rows.getRows());

        periodoAttuale = DateUtility.converteDateToGUIStringDDMMYYYY(dateFrom)+"-"+DateUtility.converteDateToGUIStringDDMMYYYY(dateTo);
        periodoPrecedente = DateUtility.converteDateToGUIStringDDMMYYYY(dateFromPrec)+"-"+DateUtility.converteDateToGUIStringDDMMYYYY(dateToPrec);
        testoArea = "Periodi confrontati:"+"\n\nprecedente: "+periodoPrecedente+"\n\nattuale:         "+periodoAttuale;
        txtAreaPeriodiConfrontati.setText(testoArea);

        tabellaTotali.getItems().clear();
        tabellaTotali.getItems().setAll(data);
        tabellaTotali.refresh();

    }

    /**
     *
     * @param dateFrom
     * @param dateTo
     * @param annoPrecedente
     *
     * Setting dei campi dataFrom e DateTo;
     * se annoPrecedente = true allora inizializzo la tabella( del confronto dei Totali)
     * con i totali riferiti ai seguentni intervalli:
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

        intiTabella(firstDay,lastDay,datesBefore[0],datesBefore[1]);
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

        intiTabella(firstDay,lastDay,datesBefore[0],datesBefore[1]);

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

        intiTabella(firstDay,lastDay,datesBefore[0],datesBefore[1]);

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
    private void listenerEsciButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/klugesoftware/farmamanager/view/SituazioneVenditeEProfitti.fxml"));
        Parent parent = (Parent)fxmlLoader.load();
        SituazioneVenditeEProfittiController controller = fxmlLoader.getController();
        boolean vistaSettimanale = false;
        controller.aggiornaTableAndScene(DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataFrom.getEditor().getText()),DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataTo.getEditor().getText()),vistaSettimanale);
        Scene scene = new Scene(parent);
        Stage app_stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        app_stage.setScene(scene);
        app_stage.show();
    }

    @FXML
    private void clickedGrafico(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/klugesoftware/farmamanager/view/ConfrontoTotaliVenditeGrafico.fxml"));
        Parent parent = (Parent)fxmlLoader.load();
        ConfrontoTotaliVenditeGraficoController controller = fxmlLoader.getController();
        boolean anno = false;
        if (rdtBtnAnnoPrecedente.isSelected())
            anno = true;
        controller.init(anno,comboMeseDaConfrontare.getSelectionModel().getSelectedIndex(),rows);
        Scene scene = new Scene(parent);
        //Stage this_app_stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Stage app_stage = new Stage();
        app_stage.setScene(scene);
        app_stage.show();
    }

    @FXML
    private void clickedVenditeLibere(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/klugesoftware/farmamanager/view/SituazioneVenditeEProfittiLibere.fxml"));
            Parent parent = (Parent) fxmlLoader.load();
            SituazioneVenditeEProfittiLibereController controller = fxmlLoader.getController();
            Scene scene = new Scene(parent);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(scene);
            app_stage.show();
        }catch(Exception ex){
            logger.error(ex.getMessage());
        }
    }

    @FXML
    private void goBackClicked(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/klugesoftware/farmamanager/view/DettagliVenditeEProfitti.fxml"));
            Parent parent = (Parent) fxmlLoader.load();
            DettaglioVenditeEProfittiController controller = fxmlLoader.getController();
            controller.aggiornaTableAndScene(DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataFrom.getEditor().getText()),DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataTo.getEditor().getText()),false);
            Scene scene = new Scene(parent);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(scene);
            app_stage.show();
        }catch(Exception ex){
            logger.error(ex.getMessage());
        }

    }

    class ListenerCambioPeriodoConfronto implements ChangeListener<Toggle>{

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

package com.klugesoftware.farmamanager.controller;


import com.klugesoftware.farmamanager.DTO.ConfrontoTotaliVenditeRowData;
import com.klugesoftware.farmamanager.DTO.ConfrontoTotaliVenditeRows;
import com.klugesoftware.farmamanager.db.ImportazioniDAOManager;
import com.klugesoftware.farmamanager.db.TotaliGeneraliVenditaEstrattiDAOManager;
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
import javafx.scene.layout.Pane;
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
    @FXML private RadioButton rdbtIntervalloDate;
    @FXML private RadioButton rdbtAnnoMese;
    @FXML private ToggleGroup metodoFiltroDate;
    @FXML private Pane pnlIntervalloDate;
    @FXML private ComboBox<String> comboMeseDaConfrontare;
    @FXML private ComboBox<String> comboAnnoDaConfrontare;
    @FXML private TextArea txtAreaPeriodiConfrontati;
    @FXML private Label lblNumDayLavoratiDiff;
    @FXML private Label lblNumDayLavoratiPrec;
    @FXML private Label lblNumDayLavoratiAttuale;

    private String periodoAttuale;
    private String periodoPrecedente;
    private String testoArea;
    private ConfrontoTotaliVenditeRows rows;
    private boolean confrontabile = true;
    private Map<String,List<String>> mapAnnoEmeseImportati;
    private boolean init = false;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        rdtBtnAnnoPrecedente.setUserData(annoPrecedente);
        rdtBtnMesePrecedente.setUserData(mesePrecedente);
        periodoDiConfronto.selectedToggleProperty().addListener(new ListenerCambioPeriodoConfronto());

        mapAnnoEmeseImportati = new HashMap<>();
        ArrayList<String> listAnniImportati = TotaliGeneraliVenditaEstrattiDAOManager.listAnniImportati();
        for(String anno : listAnniImportati){
            mapAnnoEmeseImportati.put(anno,TotaliGeneraliVenditaEstrattiDAOManager.listMesiImportatiByAnno(anno));
        }
        metodoFiltroDate.selectedToggleProperty().addListener(new ListenerCambioFiltroDate());
        
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

        //listener datepicker
        /*
        txtFldDataFrom.valueProperty().addListener((ov,oldValue,newValue)->{
            Date dateFrom = DateUtility.converteLocalDateToDate(newValue);
            Date datoTo = DateUtility.converteGUIStringDDMMYYYYToDate(DateUtility.fineMese(dateFrom));
            txtFldDataTo.setValue(DateUtility.converteDateToLocalDate(datoTo));
            clickedDataFrom(newValue);
        });

        txtFldDataTo.valueProperty().addListener((ov,oldValue,newValue)->{
            if(newValue.isAfter(txtFldDataFrom.getValue())){
                System.out.println("hello");
                clickedDataTo();
            }else{
                ;
            }

        });

         */
        ListenerDatePicker listenerDatePicker = new ListenerDatePicker();
        txtFldDataFrom.valueProperty().addListener(listenerDatePicker);
        txtFldDataTo.valueProperty().addListener(listenerDatePicker);
    }

    /**
     *
     * @return true se ci sono valori relativi al periodo di riferimento e quindi si può fare un confronto,
     * altrimenti ritorna false e non si apro la gui relativa al confronto.
     */
    public boolean getConfrontabile(){
        return confrontabile;
    }

    private void intiTabella(Date dateFrom, Date dateTo, Date dateFromPrec, Date dateToPrec){

        rows = new ConfrontoTotaliVenditeRows(dateFrom,dateTo,dateFromPrec,dateToPrec);
        if (rows.getRows() != null) {
            ObservableList<ConfrontoTotaliVenditeRowData> data = FXCollections.observableList(rows.getRows());

            periodoAttuale = DateUtility.converteDateToGUIStringDDMMYYYY(dateFrom) + "-" + DateUtility.converteDateToGUIStringDDMMYYYY(dateTo);
            periodoPrecedente = DateUtility.converteDateToGUIStringDDMMYYYY(dateFromPrec) + "-" + DateUtility.converteDateToGUIStringDDMMYYYY(dateToPrec);
            testoArea = "Periodi confrontati:" + "\n\nattuale:         " + periodoAttuale + "\n\nprecedente: " + periodoPrecedente ;
            txtAreaPeriodiConfrontati.setText(testoArea);

            tabellaTotali.getItems().clear();
            tabellaTotali.getItems().setAll(data);
            tabellaTotali.refresh();

            //valorizzo lblGiorni Lavorativi
            lblNumDayLavoratiAttuale.setText(rows.getGiorniLavorati().toString());
            lblNumDayLavoratiPrec.setText(rows.getGiorniLavoratiPrec().toString());
            int diff = rows.getGiorniLavorati().intValue() - rows.getGiorniLavoratiPrec().intValue();
            lblNumDayLavoratiDiff.setText(Integer.toString(diff));
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confronto Totali Vendita");
            alert.setHeaderText(testoArea);
            alert.setContentText("Sono presenti dei valori a zero NON confrontabili!");
            Optional<ButtonType> result = alert.showAndWait();
            confrontabile = false;
        }

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

        this.init = true;

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
        //txtFldDataFrom.getEditor().setText(DateUtility.converteDateToGUIStringDDMMYYYY(firstDay));
        //txtFldDataTo.getEditor().setText(DateUtility.converteDateToGUIStringDDMMYYYY(lastDay));


        if (annoPrecedente)
            rdtBtnAnnoPrecedente.setSelected(true);
        else
            rdtBtnMesePrecedente.setSelected(true);

        Date[] datesBefore = DateUtility.datesBefore(firstDay,lastDay,annoPrecedente);

        intiTabella(firstDay,lastDay,datesBefore[0],datesBefore[1]);
    }


    /**
     *
     *
     * Listener clickDataFrom: setto il valore del txtDataTo com ultimo del mese del giorno
     * selezionato in txtDataFrom e aggiorno i valori
     */

    private void clickedDataFrom(LocalDate dateFrom){

        if(rdbtIntervalloDate.isSelected()) {
            Date firstDay = DateUtility.converteLocalDateToDate(dateFrom);
            //Date lastDay = DateUtility.converteGUIStringDDMMYYYYToDate(DateUtility.fineMese(firstDay));
            Date lastDay = DateUtility.converteLocalDateToDate(txtFldDataTo.getValue());
            Importazioni importazione = new Importazioni();
            importazione = ImportazioniDAOManager.findUltimoInsert();
            Date lastUpdate = importazione.getDataUltimoMovImportato();

            if (lastUpdate.before(lastDay))
                lastDay = lastUpdate;


            Date[] datesBefore;
            if (rdtBtnAnnoPrecedente.isSelected())
                datesBefore = DateUtility.datesBefore(firstDay, lastDay, true);
            else
                datesBefore = DateUtility.datesBefore(firstDay, lastDay, false);

            intiTabella(firstDay, lastDay, datesBefore[0], datesBefore[1]);
        }

    }


    private void clickedDataTo(LocalDate newValue){
        if(rdbtIntervalloDate.isSelected()) {
            //Date firstDay = DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataFrom.getEditor().getText());
            //Date lastDay = DateUtility.converteGUIStringDDMMYYYYToDate(txtFldDataTo.getEditor().getText());

            Date firstDay = DateUtility.converteLocalDateToDate(txtFldDataFrom.getValue());
            Date lastDay = DateUtility.converteLocalDateToDate(newValue);

            Importazioni importazione = new Importazioni();
            importazione = ImportazioniDAOManager.findUltimoInsert();
            Date lastUpdate = importazione.getDataUltimoMovImportato();

            if (lastUpdate.before(lastDay))
                lastDay = lastUpdate;

            /*
            Calendar myCal = Calendar.getInstance(Locale.ITALY);
            myCal.setTime(firstDay);
            txtFldDataFrom.setValue(LocalDate.of(myCal.get(Calendar.YEAR), myCal.get(Calendar.MONTH) + 1, myCal.get(Calendar.DAY_OF_MONTH)));
            myCal.setTime(lastDay);
            txtFldDataTo.setValue(LocalDate.of(myCal.get(Calendar.YEAR), myCal.get(Calendar.MONTH) + 1, myCal.get(Calendar.DAY_OF_MONTH)));
            txtFldDataFrom.getEditor().setText(DateUtility.converteDateToGUIStringDDMMYYYY(firstDay));
            txtFldDataTo.getEditor().setText(DateUtility.converteDateToGUIStringDDMMYYYY(lastDay));
            */
            Date[] datesBefore;
            if (rdtBtnAnnoPrecedente.isSelected())
                datesBefore = DateUtility.datesBefore(firstDay, lastDay, true);
            else
                datesBefore = DateUtility.datesBefore(firstDay, lastDay, false);

            intiTabella(firstDay, lastDay, datesBefore[0], datesBefore[1]);
        }
    }

    @FXML
    private void clickedMeseDaConfrontare(ActionEvent event){
        if(rdbtAnnoMese.isSelected()) {
            int meseSelezionato = comboMeseDaConfrontare.getSelectionModel().getSelectedIndex();
            String anno = comboAnnoDaConfrontare.getSelectionModel().getSelectedItem();
            String mese = comboMeseDaConfrontare.getSelectionModel().getSelectedItem();
            Calendar myCal = Calendar.getInstance(Locale.ITALY);
            myCal.set(Calendar.DAY_OF_MONTH, 1);
            myCal.set(Calendar.MONTH, meseSelezionato);
            myCal.set(Calendar.YEAR, Integer.parseInt(anno));
            txtFldDataFrom.setValue(LocalDate.of(myCal.get(Calendar.YEAR), myCal.get(Calendar.MONTH) + 1, myCal.get(Calendar.DAY_OF_MONTH)));
            Date dateFrom = DateUtility.converteLocalDateToDate(txtFldDataFrom.getValue());
            txtFldDataTo.setValue(DateUtility.converteDateToLocalDate(DateUtility.ultimoGiornoDelMeseCorrente(dateFrom)));
            Date firstDay = DateUtility.converteLocalDateToDate(LocalDate.of(myCal.get(Calendar.YEAR), myCal.get(Calendar.MONTH) + 1, myCal.get(Calendar.DAY_OF_MONTH)));
            Date lastDay = DateUtility.converteGUIStringDDMMYYYYToDate(DateUtility.fineMese(firstDay));
            Date[] datesBefore;
            if (rdtBtnAnnoPrecedente.isSelected())
                datesBefore = DateUtility.datesBefore(firstDay, lastDay, true);
            else
                datesBefore = DateUtility.datesBefore(firstDay, lastDay, false);

            intiTabella(firstDay, lastDay, datesBefore[0], datesBefore[1]);
        }
    }

    @FXML
    private void annoClicked(ActionEvent event){
        if(rdbtAnnoMese.isSelected())
            comboMeseDaConfrontare.setItems(FXCollections.observableArrayList(mapAnnoEmeseImportati.get(comboAnnoDaConfrontare.getSelectionModel().getSelectedItem())));
    }

    @FXML
    private void listenerEsciButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/klugesoftware/farmamanager/view/SituazioneVenditeEProfitti.fxml"));
        Parent parent = (Parent) fxmlLoader.load();
        SituazioneVenditeEProfittiController controller = fxmlLoader.getController();
        Scene scene = new Scene(parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
        Stage app_stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        app_stage.setScene(scene);
        app_stage.show();
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
                if(rdbtAnnoMese.isSelected())
                    clickedMeseDaConfrontare(new ActionEvent());
                else
                    clickedDataFrom(txtFldDataFrom.getValue());
        }
    }

    class ListenerCambioFiltroDate implements ChangeListener<Toggle>{

        @Override
        public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
            if(rdbtAnnoMese.isSelected()){
                comboAnnoDaConfrontare.setItems(FXCollections.observableArrayList(mapAnnoEmeseImportati.keySet()));
                comboAnnoDaConfrontare.setDisable(false);
                comboMeseDaConfrontare.setDisable(false);
                pnlIntervalloDate.setDisable(true);
            }else{
                comboMeseDaConfrontare.setItems(FXCollections.observableArrayList("seleziona mese "));
                comboAnnoDaConfrontare.setItems(FXCollections.observableArrayList("seleziona anno "));
                pnlIntervalloDate.setDisable(false);
                comboAnnoDaConfrontare.setDisable(true);
                comboMeseDaConfrontare.setDisable(true);
            }
        }
    }

    class ListenerDatePicker implements ChangeListener<LocalDate>{


        @Override
        public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
            if (rdbtIntervalloDate.isSelected()) {
                if (init) {
                    init = false;
                } else {
                    Importazioni importazione = new Importazioni();
                    importazione = ImportazioniDAOManager.findUltimoInsert();
                    Date lastUpdate = importazione.getDataUltimoMovImportato();
                    if (newValue.isAfter(DateUtility.converteDateToLocalDate(lastUpdate))) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Congruenza date");
                        alert.setContentText("E' stata selezionata una data in cui non sono presenti movimenti!!!");
                        Optional<ButtonType> result = alert.showAndWait();
                        newValue = oldValue;
                        return;
                    }


                    if (observable.getValue().equals(txtFldDataFrom.getValue())) {
                        if (newValue.isBefore(txtFldDataTo.getValue())) {
                            clickedDataFrom(newValue);
                        } else {
                            Date fineMese = DateUtility.ultimoGiornoDelMeseCorrente(DateUtility.converteLocalDateToDate(newValue));
                            txtFldDataTo.setValue(DateUtility.converteDateToLocalDate(fineMese));
                          }
                        } else{
                            if (observable.getValue().equals(txtFldDataTo.getValue())) {
                                if (newValue.isAfter(txtFldDataFrom.getValue())) {
                                    clickedDataTo(newValue);
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setTitle("Congruenza date");
                                    alert.setContentText("La data è precedente alla data iniziale!!!\nImporto come data finale FINE MESE");
                                    Optional<ButtonType> result = alert.showAndWait();
                                    Date firstDate = DateUtility.converteLocalDateToDate(txtFldDataFrom.getValue());
                                    Date lastDate = DateUtility.ultimoGiornoDelMeseCorrente(firstDate);
                                    txtFldDataTo.setValue(DateUtility.converteDateToLocalDate(lastDate));
                                }
                            }
                        }
                }
            }
        }

    }

}

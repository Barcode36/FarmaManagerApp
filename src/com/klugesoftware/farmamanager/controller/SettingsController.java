package com.klugesoftware.farmamanager.controller;

import com.klugesoftware.farmamanager.IOFunctions.DeleteGiacenze;
import com.klugesoftware.farmamanager.IOFunctions.DeleteMovimenti;
import com.klugesoftware.farmamanager.IOFunctions.ImportazioneGiacenzeFromDBF;
import com.klugesoftware.farmamanager.IOFunctions.ImportazioneVenditeFromDBF;
import com.klugesoftware.farmamanager.db.GiacenzeDAOManager;
import com.klugesoftware.farmamanager.db.ImportazioniDAOManager;
import com.klugesoftware.farmamanager.model.Importazioni;
import com.klugesoftware.farmamanager.utility.DateUtility;
import com.klugesoftware.farmamanager.utility.GetDBFFileName;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.net.URL;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SettingsController implements Initializable {

    @FXML private ProgressIndicator progressIndicator;
    @FXML private ProgressBar progressBar;
    @FXML private ListView<String> elencoMessaggi;
    @FXML private Button btnImportaMov;
    @FXML private Button btnCancellaImportazione;
    @FXML private CheckBox importaGiacenze;
    @FXML private RadioButton importaAnnoCorrente;
    @FXML private RadioButton importaAnnoPrev;
    @FXML private RadioButton importaBetweenDate;
    @FXML private RadioButton aggiornaMovimenti;
    @FXML private DatePicker fldDateFrom;
    @FXML private DatePicker fldDateTo;
    @FXML private DatePicker cancDateFrom;
    @FXML private DatePicker cancDateTo;
    @FXML private Pane warnGiacPanel;
    @FXML private Pane warnMovPanel;
    @FXML private Pane cancPane;
    @FXML private Pane importaPane;
    @FXML private Button btnAbilita;
    @FXML private Button btnImportIniziale;
    @FXML private Button btnCancMov;
    @FXML private CheckBox rdbtCancGiacenze;
    @FXML private RadioButton rdbtCancMovCorrente;
    @FXML private RadioButton rdbtCancMovPrev;
    @FXML private RadioButton rdbtCancBetweenDate;

    private boolean cancellazioneAbilitata = false;
    private final Logger logger = LogManager.getLogger(SettingsController.class.getName());
    private ImportazioneVenditeFromDBF taskMovimenti;
    private ImportazioneGiacenzeFromDBF taskGiacenze;
    private ObservableList<String> listaMessagi = FXCollections.observableArrayList();
    private Date dateFrom;
    private Date dateTo;
    private String movimentiFileName;
    private ExecutorService executor;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        elencoMessaggi.setItems(listaMessagi);
        executor = Executors.newFixedThreadPool(1);


        cancDateFrom.valueProperty().addListener((ov,oldValue,newValue)->{
            cancDateFrom.setValue(newValue.with(TemporalAdjusters.firstDayOfMonth()));
            cancDateTo.setValue(newValue.with(TemporalAdjusters.lastDayOfMonth()));
        });

        cancDateTo.valueProperty().addListener((ov,oldValue,newValue)->{
            cancDateTo.setValue(newValue.with(TemporalAdjusters.lastDayOfMonth()));
        });

        /*controllo che la Tabella Giacenze nn sia vuota, nel qual caso abilito l'opzione Importazione Giacenze*/
        int countGiac = GiacenzeDAOManager.countGiacenze();
        if(countGiac == 0) {
            importaGiacenze.setSelected(true);
            warnGiacPanel.setVisible(true);
        }else
            warnGiacPanel.setVisible(false);
        btnCancellaImportazione.setVisible(false);
        warnMovPanel.setVisible(false);
        cancPane.setDisable(true);
        statoImportazioni();
    }

    private void statoImportazioni(){
        Importazioni importazioni = ImportazioniDAOManager.findUltimoInsert();
        if (importazioni.getIdImportazione() == null){
            warnMovPanel.setVisible(true);
            aggiornaMovimenti.setDisable(true);
            importaAnnoCorrente.setSelected(true);
        }
    }

    @FXML
    private void listenerEsciButton(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/HomeAnalisiDati.fxml"));
            Parent parent = (Parent)fxmlLoader.load();
            HomeAnalisiDatiController controller = fxmlLoader.getController();
            Scene scene = new Scene(parent);
            Stage app_stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            app_stage.hide();
            app_stage.setScene(scene);
            app_stage.show();
        }catch(Exception ex){
            logger.error(ex);
        }
    }

    @FXML
    private void importaClicked(ActionEvent event){
        // decido in base  ai radioButton l'intervallo  dei movimenti da importare


        //TODO: controllare che sia selezionato almeno un'opzione
        GetDBFFileName dbfFileName = new GetDBFFileName();
        if (importaAnnoCorrente.isSelected()) {
            movimentiFileName = dbfFileName.getMovimentiFileNameAnnoCorrente();
            dateFrom = DateUtility.primoGiornoAnnoCorrente();
            dateTo = DateUtility.ultimoGiornoAnnoCorrente();
        }
        else
            if (importaAnnoPrev.isSelected()) {
                movimentiFileName = dbfFileName.getMovimentiFileNameAnnoPrecedente();
                dateFrom = DateUtility.primoGiornoAnnoPrecedente();
                dateTo = DateUtility.ultimoGiornoAnnoPrecedente();
            }
            else
                if (importaBetweenDate.isSelected()){
                    dateFrom = DateUtility.converteGUIStringDDMMYYYYToDate(fldDateFrom.getEditor().getText());
                    dateTo = DateUtility.converteGUIStringDDMMYYYYToDate(fldDateTo.getEditor().getText());
                    movimentiFileName = dbfFileName.getMovimentiFileName(dateFrom);
                }
                else
                    if (aggiornaMovimenti.isSelected()){
                        Importazioni importazione = ImportazioniDAOManager.findUltimoInsert();
                        dateFrom = DateUtility.aggiungeGiorniAData(1,importazione.getDataUltimoMovImportato());
                        dateTo = DateUtility.sottraeGiorniADataOdierna(1);
                        movimentiFileName = dbfFileName.getMovimentiFileName(dateFrom);
                    }
        runTask();
    }

    private void runTask(){

        btnCancellaImportazione.setVisible(true);
        warnMovPanel.setVisible(false);
        warnGiacPanel.setVisible(false);
        progressIndicator.setProgress(0);
        progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);

        if (importaGiacenze.isSelected()) {
            taskGiacenze = new ImportazioneGiacenzeFromDBF();
            taskGiacenze.messageProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    listaMessagi.add(taskGiacenze.getMessage());
                    elencoMessaggi.scrollTo(elencoMessaggi.getItems().size());
                }
            });

            taskGiacenze.setOnRunning((succeededEvent) -> {
                btnImportaMov.setDisable(true);
                btnCancellaImportazione.setDisable(false);
            });

            taskGiacenze.setOnSucceeded((succeededEvent) -> {
                btnCancellaImportazione.setDisable(true);
            });

            taskGiacenze.setOnCancelled((eventCancelled) -> {
            });
        }

        taskMovimenti = new ImportazioneVenditeFromDBF(DateUtility.converteDateToGUIStringDDMMYYYY(dateFrom),DateUtility.converteDateToGUIStringDDMMYYYY(dateTo),movimentiFileName);

        taskMovimenti.messageProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null ){
                listaMessagi.add(taskMovimenti.getMessage());
                elencoMessaggi.scrollTo(elencoMessaggi.getItems().size());
            }

        });

        taskMovimenti.setOnRunning((succeededEvent)->{
            btnImportaMov.setDisable(true);
            btnCancellaImportazione.setDisable(false);
        });

        taskMovimenti.setOnSucceeded((succeededEvent)->{
            progressIndicator.setProgress(1);
            btnImportaMov.setDisable(false);
            btnCancellaImportazione.setDisable(true);
        });

        if (importaGiacenze.isSelected()) {
            executor.execute(taskGiacenze);
            executor.execute(taskMovimenti);
        }else
            executor.execute(taskMovimenti);

    }

    @FXML
    private void cancellaImportazione(ActionEvent event){

       if (importaGiacenze.isSelected()){
           taskGiacenze.cancel();
       }
       taskMovimenti.cancel();
       btnCancellaImportazione.setVisible(false);
       rollBack();
    }

    private void rollBack(){

        progressIndicator.setProgress(0);
        progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);

        DeleteGiacenze taskGiac = new DeleteGiacenze();
        if (importaGiacenze.isSelected() || rdbtCancGiacenze.isSelected()) {
            taskGiac.messageProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    listaMessagi.add(taskGiac.getMessage());
                    elencoMessaggi.scrollTo(elencoMessaggi.getItems().size());
                }

            });

            taskGiac.setOnRunning((succeededEvent) -> {
                btnImportaMov.setDisable(true);
                btnCancellaImportazione.setDisable(true);
            });

            taskGiac.setOnSucceeded((succeededEvent) -> {
                progressIndicator.setProgress(1);
                btnImportaMov.setDisable(false);
                btnCancellaImportazione.setDisable(true);
            });
        }

        DeleteMovimenti taskMov = new DeleteMovimenti(dateFrom,dateTo);

        taskMov.messageProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null ){
                listaMessagi.add(taskMov.getMessage());
                elencoMessaggi.scrollTo(elencoMessaggi.getItems().size());
            }

        });

        taskMov.setOnRunning((succeededEvent)->{
            btnImportaMov.setDisable(true);
            btnCancellaImportazione.setDisable(true);
        });

        taskMov.setOnSucceeded((succeededEvent)->{
            progressIndicator.setProgress(1);
            btnImportaMov.setDisable(false);
            btnCancellaImportazione.setDisable(true);
        });

        if(importaGiacenze.isSelected() || rdbtCancGiacenze.isSelected()){
            executor.execute(taskGiac);
            executor.execute(taskMov);
        }
        else
            executor.execute(taskMov);

    }

    @FXML
    private void abilitaClicked(ActionEvent event){
        if(!cancellazioneAbilitata){
            btnAbilita.setText("Abilita\nImportazione");
            cancellazioneAbilitata = true;
            btnImportaMov.setDisable(true);
            btnImportIniziale.setDisable(true);
            btnCancMov.setDisable(false);
            importaPane.setDisable(true);
            cancPane.setDisable(false);
        }else{
            btnAbilita.setText("Abilita\nCancellazione");
            cancellazioneAbilitata = false;
            btnImportaMov.setDisable(false);
            btnImportIniziale.setDisable(false);
            btnCancMov.setDisable(true);
            importaPane.setDisable(false);
            cancPane.setDisable(true);
        }
    }

    @FXML
    private void cancMovClicked(ActionEvent event){
        if (rdbtCancMovCorrente.isSelected()) {
            dateFrom = DateUtility.primoGiornoAnnoCorrente();
            dateTo = DateUtility.ultimoGiornoAnnoCorrente();
        }
        else
        if (rdbtCancMovPrev.isSelected()) {
            dateFrom = DateUtility.primoGiornoAnnoPrecedente();
            dateTo = DateUtility.ultimoGiornoAnnoPrecedente();
        }
        else
        if (rdbtCancBetweenDate.isSelected()){
            dateFrom = DateUtility.converteGUIStringDDMMYYYYToDate(cancDateFrom.getEditor().getText());
            dateTo = DateUtility.converteGUIStringDDMMYYYYToDate(cancDateTo.getEditor().getText());
        }
        rollBack();
    }

}

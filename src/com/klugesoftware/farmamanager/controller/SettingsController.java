package com.klugesoftware.farmamanager.controller;

import com.klugesoftware.farmamanager.IOFunctions.ImportazioneGiacenzeFromDBF;
import com.klugesoftware.farmamanager.IOFunctions.ImportazioneVenditeFromDBF;
import com.klugesoftware.farmamanager.utility.DateUtility;
import com.klugesoftware.farmamanager.utility.GetDBFFileName;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
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
    @FXML private DatePicker fldDateFrom;
    @FXML private DatePicker fldDateTo;

    private boolean flagGiacenze = false;
    private ImportazioneVenditeFromDBF taskMovimenti;
    private ImportazioneGiacenzeFromDBF taskGiacenze;
    private ObservableList<String> listaMessagi = FXCollections.observableArrayList();
    private Date dateFrom;
    private Date dateTo;
    private String movimentiFileName;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        elencoMessaggi.setItems(listaMessagi);
    }

    @FXML
    private void listenerEsciButton(ActionEvent event){
        System.exit(0);
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

        if (importaGiacenze.isSelected()){
           runTaskGiacenze();
        }else{
            runTaskMovimenti();
        }

    }

    private void runTaskMovimenti(){

        taskMovimenti = new ImportazioneVenditeFromDBF(DateUtility.converteDateToGUIStringDDMMYYYY(dateFrom),DateUtility.converteDateToGUIStringDDMMYYYY(dateTo),movimentiFileName);
        System.out.println(movimentiFileName);
        System.out.println(DateUtility.converteDateToGUIStringDDMMYYYY(dateFrom));
        System.out.println(DateUtility.converteDateToGUIStringDDMMYYYY(dateTo));
        //progressIndicator.progressProperty().bind(task.progressProperty());
        //progressBar.progressProperty().bind(task.progressProperty());
        progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
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

        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.execute(taskMovimenti);
        executor.shutdown();
    }

    private void runTaskGiacenze(){

        taskGiacenze = new ImportazioneGiacenzeFromDBF();

        //progressIndicator.progressProperty().bind(task.progressProperty());
        //progressBar.progressProperty().bind(task.progressProperty());
        progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        taskGiacenze.messageProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null ){
                listaMessagi.add(taskGiacenze.getMessage());
                elencoMessaggi.scrollTo(elencoMessaggi.getItems().size());
            }

        });

        taskGiacenze.setOnRunning((succeededEvent)->{
            btnImportaMov.setDisable(true);
            btnCancellaImportazione.setDisable(false);
            flagGiacenze = true;
        });


        taskGiacenze.setOnSucceeded((succeededEvent)->{
            flagGiacenze = false;
            runTaskMovimenti();
            progressIndicator.setProgress(1);
            btnImportaMov.setDisable(false);
            btnCancellaImportazione.setDisable(true);
        });

        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.execute(taskGiacenze);
        executor.shutdown();
    }

    @FXML
    private void cancellaImportazione(ActionEvent event){

        if(flagGiacenze){
            if (taskGiacenze != null){
                taskGiacenze.cancel();
                flagGiacenze = false;
            }
        }else{
            if(taskMovimenti != null){
                taskMovimenti.cancel();
            }
        }

        progressIndicator.setProgress(0);
        btnCancellaImportazione.setDisable(true);
        btnImportaMov.setDisable(false);
    }
}

package com.klugesoftware.farmamanager.controller;

import com.klugesoftware.farmamanager.IOFunctions.*;
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

import java.io.File;
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
    @FXML private TextArea txtAreaInfoImportazione;

    private boolean importazioneIniziale = false;
    private boolean cancellazioneAbilitata = false;
    private final Logger logger = LogManager.getLogger(SettingsController.class.getName());
    private ImportazioneVenditeFromDBF taskMovimenti;
    private ImportazioneGiacenzeFromDBF taskGiacenze;
    private ImportazioneGiacenzeFromDBF taskGiacenzeTemp;
    private ImportazioneVenditeFromDBF taskMovAnnoPrevTemp;
    private ImportazioneVenditeFromDBF taskMovAnnoCorrenteTemp;
    private InizializzaArchivi taskInizializzaArchivi;
    private DeleteGiacenze taskGiac;
    private DeleteMovimenti taskMov;
    private ObservableList<String> listaMessagi = FXCollections.observableArrayList();
    private Date dateFrom;
    private Date dateTo;
    private String movimentiFileName;
    private ExecutorService executor;
    private final String pathFileDBF  = "./resources/dbf/";
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

        btnImportaMov.setOnAction(event -> {
            importaClicked();
        });

        statoImportazioni();
    }

    private void statoImportazioni(){
        Importazioni importazioni = ImportazioniDAOManager.findUltimoInsert();
        if (importazioni.getIdImportazione() == null){
            warnMovPanel.setVisible(true);
            aggiornaMovimenti.setDisable(true);
            importaAnnoCorrente.setSelected(true);
            txtAreaInfoImportazione.setText("Archivi vuoti: seleziona un'opzione");
        }else{
            txtAreaInfoImportazione.setText("Archivi movimenti aggiornati al "+DateUtility.converteDateToGUIStringDDMMYYYY(importazioni.getDataUltimoMovImportato()));
        }
    }


    @FXML
    private void listenerEsciButton(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/klugesoftware/farmamanager/view/HomeAnalisiDati.fxml"));
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

    public void fireButton(){
        btnImportaMov.fire();
    }

    private boolean existFileMovimentiDbf(){
        File fileMovDbf = new File(pathFileDBF+movimentiFileName);
        if(!fileMovDbf.exists()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Importazione non possibile.");
            alert.setContentText("Non ho trovato il file " + movimentiFileName + " !");
            alert.showAndWait();
            return false;
        }else
            return true;
    }

    private boolean existFileGiacenzeDbf(){
        GetDBFFileName getDbffileName = new GetDBFFileName();
        File fileGiacDbf = new File(pathFileDBF+getDbffileName.getGiacenzeFileNameAnnoCorrente());
        if(!fileGiacDbf.exists()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Importazione giacenze non possibile.");
            alert.setContentText("Non ho trovato il file: "+getDbffileName.getGiacenzeFileNameAnnoCorrente()+"!");
            alert.showAndWait();
            return false;
        }else
            return true;
    }

    private void importaClicked(){

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
        if (existFileMovimentiDbf()) {
            runTask();

            if (importaGiacenze.isSelected() && existFileGiacenzeDbf()) {
                executor.execute(taskGiacenze);
                executor.execute(taskMovimenti);
            } else
                executor.execute(taskMovimenti);
        }

    }

    private void runTask(){

            txtAreaInfoImportazione.setText("sto importando i movimenti da " + DateUtility.converteDateToGUIStringDDMMYYYY(dateFrom) + " " +
                    " a " + DateUtility.converteDateToGUIStringDDMMYYYY(dateTo));
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

            }

            taskMovimenti = new ImportazioneVenditeFromDBF(DateUtility.converteDateToGUIStringDDMMYYYY(dateFrom), DateUtility.converteDateToGUIStringDDMMYYYY(dateTo), movimentiFileName, false);

            taskMovimenti.messageProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    listaMessagi.add(taskMovimenti.getMessage());
                    elencoMessaggi.scrollTo(elencoMessaggi.getItems().size());
                }

            });

            taskMovimenti.setOnRunning((succeededEvent) -> {
                btnImportaMov.setDisable(true);
                btnCancellaImportazione.setDisable(false);
            });

            taskMovimenti.setOnSucceeded((succeededEvent) -> {
                progressIndicator.setProgress(1);
                btnImportaMov.setDisable(false);
                btnCancellaImportazione.setDisable(false);
                btnCancellaImportazione.setVisible(false);
            });
        }

    @FXML
    private void cancellaImportazione(ActionEvent event){
        txtAreaInfoImportazione.setText("Cancellazione archivi");
        InizializzaArchivi taskIni = new InizializzaArchivi();
        if (importazioneIniziale){
            importazioneIniziale=false;
            taskGiacenzeTemp.cancel();
            taskMovAnnoPrevTemp.cancel();
            taskMovAnnoCorrenteTemp.cancel();
            btnImportaMov.setDisable(false);
            btnCancellaImportazione.setVisible(false);
            taskIni.messageProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    listaMessagi.add(taskIni.getMessage());
                    elencoMessaggi.scrollTo(elencoMessaggi.getItems().size());
                }
            });
            taskIni.setOnSucceeded((succeededEvent) -> {
                progressIndicator.setProgress(1);
                btnImportaMov.setDisable(false);
                btnCancMov.setDisable(true);
                btnAbilita.setDisable(false);
            });
            executor.execute(taskIni);
        }else {

            if (importaGiacenze.isSelected()) {
                taskGiacenze.cancel();
            }
            taskMovimenti.cancel();
            btnCancellaImportazione.setVisible(false);
            rollBack();
            if (importaGiacenze.isSelected() || rdbtCancGiacenze.isSelected()) {
                executor.execute(taskGiac);
                executor.execute(taskMov);
            } else
                executor.execute(taskMov);
        }
    }

    private void rollBack(){

        txtAreaInfoImportazione.setText("sto cancellando i movimenti da "+DateUtility.converteDateToGUIStringDDMMYYYY(dateFrom)+" " +
                " a "+DateUtility.converteDateToGUIStringDDMMYYYY(dateTo));

        progressIndicator.setProgress(0);
        progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);

        taskGiac = new DeleteGiacenze();
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
            });
        }

        taskMov = new DeleteMovimenti(dateFrom,dateTo);

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
        if(importaGiacenze.isSelected() || rdbtCancGiacenze.isSelected()){
            executor.execute(taskGiac);
            executor.execute(taskMov);
        }
        else
            executor.execute(taskMov);
    }

    @FXML
    private void importInizialeClicked(ActionEvent event){

        txtAreaInfoImportazione.setText("sto importando i moviementi da "+DateUtility.converteDateToGUIStringDDMMYYYY(DateUtility.primoGiornoAnnoPrecedente())+" " +
                " a "+DateUtility.converteDateToGUIStringDDMMYYYY(DateUtility.ultimoGiornoAnnoCorrente()));

        btnCancellaImportazione.setVisible(true);
        btnCancellaImportazione.setDisable(false);
        importazioneIniziale = true;
        btnImportaMov.setDisable(true);
        btnAbilita.setDisable(true);
        importaGiacenze.setSelected(true);
        importaAnnoPrev.setSelected(true);

        progressIndicator.setProgress(0);
        progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);

        taskInizializzaArchivi = new InizializzaArchivi();
        taskInizializzaArchivi.messageProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                listaMessagi.add(taskInizializzaArchivi.getMessage());
                elencoMessaggi.scrollTo(elencoMessaggi.getItems().size());
            }
        });

        taskGiacenzeTemp = new ImportazioneGiacenzeFromDBF();
        taskGiacenzeTemp.messageProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                listaMessagi.add(taskGiacenzeTemp.getMessage());
                elencoMessaggi.scrollTo(elencoMessaggi.getItems().size());
            }
        });

        GetDBFFileName dbfFileName = new GetDBFFileName();

        String movimentiFileNameTemp = dbfFileName.getMovimentiFileNameAnnoPrecedente();
        String dateFromTemp = DateUtility.converteDateToGUIStringDDMMYYYY(DateUtility.primoGiornoAnnoPrecedente());
        String dateToTemp = DateUtility.converteDateToGUIStringDDMMYYYY(DateUtility.ultimoGiornoAnnoPrecedente());

        taskMovAnnoPrevTemp = new ImportazioneVenditeFromDBF(dateFromTemp,dateToTemp,movimentiFileNameTemp,true);
        taskMovAnnoPrevTemp.messageProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                listaMessagi.add(taskMovAnnoPrevTemp.getMessage());
                elencoMessaggi.scrollTo(elencoMessaggi.getItems().size());
            }
        });
        taskMovAnnoPrevTemp.setOnSucceeded((succeededEvent) -> {
            importaAnnoCorrente.setSelected(true);
        });


        movimentiFileNameTemp = dbfFileName.getMovimentiFileNameAnnoCorrente();
        dateFromTemp = DateUtility.converteDateToGUIStringDDMMYYYY(DateUtility.primoGiornoAnnoCorrente());
        dateToTemp = DateUtility.converteDateToGUIStringDDMMYYYY(DateUtility.ultimoGiornoAnnoCorrente());

        taskMovAnnoCorrenteTemp = new ImportazioneVenditeFromDBF(dateFromTemp,dateToTemp,movimentiFileNameTemp,true);
        taskMovAnnoCorrenteTemp.messageProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                listaMessagi.add(taskMovAnnoCorrenteTemp.getMessage());
                elencoMessaggi.scrollTo(elencoMessaggi.getItems().size());
            }
        });

        taskMovAnnoCorrenteTemp.setOnSucceeded((succeededEvent) -> {
            progressIndicator.setProgress(1);
            btnImportaMov.setDisable(false);
            btnCancellaImportazione.setDisable(true);
            importazioneIniziale=false;
        });

        executor.execute(taskInizializzaArchivi);
        executor.execute(taskGiacenzeTemp);
        executor.execute(taskMovAnnoPrevTemp);
        executor.execute(taskMovAnnoCorrenteTemp);
        }


}

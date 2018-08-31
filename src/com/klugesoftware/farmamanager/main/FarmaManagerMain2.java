package com.klugesoftware.farmamanager.main;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class FarmaManagerMain2 extends Application{

    @FXML private RadioButton rdtBtnVistaMensile;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("../view/Settings.fxml"));
        Scene sceneSituazioneVenditeEProfitti = new Scene(root);

        primaryStage.setScene(sceneSituazioneVenditeEProfitti);
        primaryStage.show();

    }

    public static void main(String[] args){
        Application.launch(args);
    }
}

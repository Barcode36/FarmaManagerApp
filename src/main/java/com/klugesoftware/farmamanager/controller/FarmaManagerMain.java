package com.klugesoftware.farmamanager.controller;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;



public class FarmaManagerMain extends Application{

    @FXML private RadioButton rdtBtnVistaMensile;

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("/com/klugesoftware/farmamanager/view/HomeAnalisiDati.fxml"));
        Scene sceneSituazioneVenditeEProfitti = new Scene(root);
        primaryStage.setScene(sceneSituazioneVenditeEProfitti);
        primaryStage.show();

    }

    public static void main(String[] args){
        Application.launch(args);
    }
}

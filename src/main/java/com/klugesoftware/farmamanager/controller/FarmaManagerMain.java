package com.klugesoftware.farmamanager.controller;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;



public class FarmaManagerMain extends Application{


    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("/com/klugesoftware/farmamanager/view/SituazioneVenditeEProfitti.fxml"));
        Scene sceneSituazioneVenditeEProfitti = new Scene(root);
        primaryStage.setScene(sceneSituazioneVenditeEProfitti);

        /*
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        //set Stage boundaries to visible bounds of the main screen
        primaryStage.setX(primaryScreenBounds.getMinX());
        primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setWidth(primaryScreenBounds.getWidth());
        primaryStage.setHeight(primaryScreenBounds.getHeight());
        */
        primaryStage.show();

    }

    public static void main(String[] args){
        Application.launch(args);
    }
}

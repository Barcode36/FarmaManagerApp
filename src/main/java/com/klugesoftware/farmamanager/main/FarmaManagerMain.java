package com.klugesoftware.farmamanager.main;
import com.klugesoftware.FarmaManagerUpdating.ftp.FTPConnector;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.util.Properties;


public class FarmaManagerMain extends Application{

    private Logger logger = LogManager.getLogger(FarmaManagerMain.class.getName());

    @Override
    public void start(Stage primaryStage) throws Exception {

        //controllo se ci sono aggiornamenti da scaricare
        try {
            FTPConnector ftpClient = new FTPConnector();
            boolean ret = ftpClient.isEmptyFolderUpdate();
            String nomeFile = "./resources/config/configFtp.properties";
            Properties properties = new Properties();
            properties.load(new FileInputStream(nomeFile));
            if(ret)
                properties.setProperty("updating","false");
            else
                properties.setProperty("updating","true");
            properties.store(new FileOutputStream(nomeFile),null);

        }catch(IOException ex){
            logger.error(ex.getMessage());
        }
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

package com.klugesoftware.FarmaManagerUpdating.main;

import com.klugesoftware.farmamanager.controller.SettingsController;
import com.klugesoftware.farmamanager.utility.GetDBFFileName;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

public class UpdaterMovimentiNotVisibleMain extends Application {

    public static boolean visible = true;

    @Override
    public void start(Stage primaryStage) throws Exception{

        //copio Movimenti e Giacenze DBF in resources/DBF
        Properties properties = new Properties();
        properties.load(new FileInputStream("./resources/config/config.properties"));
        String fileFrom = properties.getProperty("pathCopyFileFrom")+(new GetDBFFileName()).getGiacenzeFileNameAnnoCorrente();
        String fileTo = properties.getProperty("pathCopyFileTo")+(new GetDBFFileName()).getGiacenzeFileNameAnnoCorrente();
        FileUtils.copyFile(new File(fileFrom),new File(fileTo));
        fileFrom = properties.getProperty("pathCopyFileFrom")+(new GetDBFFileName()).getMovimentiFileNameAnnoCorrente();
        fileTo = properties.getProperty("pathCopyFileTo")+(new GetDBFFileName()).getMovimentiFileNameAnnoCorrente();
        FileUtils.copyFile(new File(fileFrom),new File(fileTo));


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/klugesoftware/farmamanager/view/Settings.fxml"));
        BorderPane root = (BorderPane) fxmlLoader.load();
        Scene scene = new Scene((root));
        primaryStage.setScene(scene);
        SettingsController controller = fxmlLoader.getController();

        controller.fireAggiornaMovimentiButton();
    }

    public static void main(String[] args){
        Application.launch(args);
    }

}

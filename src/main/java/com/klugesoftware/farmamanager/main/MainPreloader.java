package com.klugesoftware.farmamanager.main;

import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

public class MainPreloader extends Preloader {

    private static final double WIDTH = 400;
    private static final double HEIGHT = 400;

    private Stage preloaderStage;
    private Scene scene;

    private Label lblProgress;
    private ProgressBar progressBar;

    public MainPreloader() {
    }

    @Override
    public void init() throws Exception {

        // If preloader has complex UI it's initialization can be done in MainPreloader#init
        Platform.runLater(() -> {
            try {
                String msg= "";
                File jarFile = null;
                File wd = new File(".");
                File[] dirfiles = wd.listFiles();
                for (File f : dirfiles) {
                    if (f.isFile()) {
                        if (f.getName().contains("FarmaManagerApp") && f.getName().contains("jar")) {
                            jarFile = f;
                        }
                    }
                }
                if(jarFile != null) {
                    JarInputStream jarStream = new JarInputStream(new FileInputStream(jarFile.getName()));
                    Manifest mf = jarStream.getManifest();
                    Attributes attr = mf.getMainAttributes();
                    msg = "versione " + attr.getValue("jarVersion");
                }
                lblProgress = new Label("0%");
                progressBar = new ProgressBar();
                progressBar.setProgress(ProgressIndicator.USE_COMPUTED_SIZE);
                Label title = new Label("Sto caricando l'applicazione...");
                VBox vbox = new VBox(title,lblProgress,progressBar);
                vbox.setAlignment(Pos.CENTER);
                vbox.setSpacing(5);

                GridPane gridPane = new GridPane();
                gridPane.setStyle("-fx-background-color: rgb(201, 201, 201);");
                Label invoiceVersion = new Label("FarmaManager "+msg);
                Label cifarmaLabel = new Label("Cifarma Srl");
                cifarmaLabel.setStyle("fx-font-family: Calibri;");
                cifarmaLabel.setStyle("-fx-font-size: 10;");
                invoiceVersion.setStyle("fx-font-family: Calibri;");
                invoiceVersion.setStyle("-fx-font-size:10;");
                gridPane.add(invoiceVersion,0,0);
                gridPane.add(cifarmaLabel,1,0);
                ColumnConstraints col0 = new ColumnConstraints();
                ColumnConstraints col1 = new ColumnConstraints();
                col1.setHalignment(HPos.RIGHT);
                col0.setHgrow(Priority.ALWAYS);
                gridPane.getColumnConstraints().addAll(col0,col1);
                GridPane.setMargin(cifarmaLabel,new Insets(5,10,5,10));
                GridPane.setMargin(invoiceVersion,new Insets(5,10,5,10));
                BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("/com/klugesoftware/farmamanager/view/Preloader.fxml"));
                root.setCenter(vbox);
                root.setBottom(gridPane);
                scene = new Scene(root);
            }catch(IOException ex){
                ex.printStackTrace();
            }
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //progressBar.setProgress(ProgressIndicator.USE_COMPUTED_SIZE);
        this.preloaderStage = primaryStage;

        // Set preloader scene and show stage.
        preloaderStage.setScene(scene);
        preloaderStage.show();
    }

    @Override
    public void handleApplicationNotification(PreloaderNotification info) {
        // Handle application notification in this point (see MyApplication#init).
        if (info instanceof ProgressNotification) {
            lblProgress.setText(((ProgressNotification) info).getProgress() + "%");
            progressBar.setProgress(((ProgressNotification) info).getProgress()/100);
        }
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification info) {
        // Handle state change notifications.
        StateChangeNotification.Type type = info.getType();
        switch (type) {
            case BEFORE_LOAD:
                break;
            case BEFORE_INIT:
                break;
            case BEFORE_START:
                preloaderStage.hide();
                break;
        }
    }
}

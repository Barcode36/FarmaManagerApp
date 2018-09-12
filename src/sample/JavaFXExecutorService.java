package sample;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @web http://java-buddy.blogspot.com/
 */
public class JavaFXExecutorService extends Application {

    ExecutorService executorService = null;

    @Override
    public void start(Stage primaryStage) {

        CountDownLatch countDownLatch1 = new CountDownLatch(5);
        CountThread countThread1 = new CountThread("A", countDownLatch1, 5);
        ProgressBar progressBar1 = new ProgressBar();
        progressBar1.progressProperty().bind(countThread1.processProperty);

        CountDownLatch countDownLatch2 = new CountDownLatch(10);
        CountThread countThread2 = new CountThread("B", countDownLatch2, 10);
        ProgressBar progressBar2 = new ProgressBar();
        progressBar2.progressProperty().bind(countThread2.processProperty);

        CountDownLatch countDownLatch3 = new CountDownLatch(5);
        CountThread countThread3 = new CountThread("C", countDownLatch3, 5);
        ProgressBar progressBar3 = new ProgressBar();
        progressBar3.progressProperty().bind(countThread3.processProperty);

        CountDownLatch countDownLatch4 = new CountDownLatch(5);
        CountThread countThread4 = new CountThread("D", countDownLatch4, 5);
        ProgressBar progressBar4 = new ProgressBar();
        progressBar4.progressProperty().bind(countThread4.processProperty);

        CountDownLatch countDownLatch5 = new CountDownLatch(5);
        CountThread countThread5 = new CountThread("E", countDownLatch5, 5);
        ProgressBar progressBar5 = new ProgressBar();
        progressBar5.progressProperty().bind(countThread5.processProperty);

        Button btnStart = new Button();
        btnStart.setText("Start");
        btnStart.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Start");
                executorService = Executors.newFixedThreadPool(2);
                executorService.execute(countThread1);
                executorService.execute(countThread2);
                executorService.execute(countThread3);
                executorService.execute(countThread4);
                executorService.execute(countThread5);
            }
        });

        Button btnShutdown = new Button();
        btnShutdown.setText("Shut Down");
        btnShutdown.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if(executorService != null){
                    System.out.println("Shut Down");
                    executorService.shutdown();
                }
            }
        });

        Button btnShutdownNow = new Button();
        btnShutdownNow.setText("Shut Down Now");
        btnShutdownNow.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if(executorService != null){
                    System.out.println("Shut Down Now");
                    executorService.shutdownNow();
                }
            }
        });

        VBox vBox = new VBox();
        vBox.getChildren().addAll(btnStart, btnShutdown,
                btnShutdownNow, progressBar1, progressBar2,
                progressBar3, progressBar4, progressBar5);

        StackPane root = new StackPane();
        root.getChildren().add(vBox);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("java-buddy.blogspot.com");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

class CountThread implements Runnable {

    int num_of_count = 5;
    CountDownLatch counter;
    String name;

    DoubleProperty processProperty;

    CountThread(String n, CountDownLatch c, int num) {
        name = n;
        counter = c;
        num_of_count = num;
        processProperty = new SimpleDoubleProperty(num_of_count);
    }

    @Override
    public void run() {

        for (int i = 0; i < num_of_count; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(CountThread.class.getName()).log(Level.SEVERE, null, ex);
            }

            processProperty.set((double)(counter.getCount())/(double)num_of_count);
            System.out.println(name + " : " + counter.getCount());
            counter.countDown();

        }
    }

}

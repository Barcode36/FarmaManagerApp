package sample;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class TableMCVE extends Application {
    @Override
    public void start(Stage stage) {

        ObservableList<ObservableList<String>> tableData = FXCollections.observableArrayList();
        tableData.add(FXCollections.observableArrayList("Row1Col1", "Row1Col2"));
        tableData.add(FXCollections.observableArrayList("Row2Col1", "Row2Col2"));
        tableData.add(FXCollections.observableArrayList("Row3Col1", "Row3Col2"));

        TableView<ObservableList<String>> table = new TableView<ObservableList<String>>();

        TableColumn<ObservableList<String>, String> col1 = new TableColumn<ObservableList<String>, String>("Col1");
        col1.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().get(0)));

        // Set the cell factory of the column with a custom TableCell to modify its behavior.
        col1.setCellFactory(e -> new TableCell<ObservableList<String>, String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                // Always invoke super constructor.
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item);

                    // If index is two we set the background color explicitly.
                    if (getIndex() == 2) {
                        this.setStyle("-fx-background-color: green;");
                    }
                }
            }
        });

        TableColumn<ObservableList<String>, String> col2 = new TableColumn<ObservableList<String>, String>("Col2");
        col2.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().get(1)));

        // Set the cell factory of the column with a custom TableCell to modify its behavior.
        col2.setCellFactory(e -> new TableCell<ObservableList<String>, String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                // Always invoke super constructor.
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item);

                    // If index is zero we set the background color explicitly.
                    if (getIndex() == 0) {
                        this.setStyle("-fx-background-color: blue;");
                    }
                }
            }
        });

        table.getColumns().addAll(col1, col2);
        table.getItems().addAll(tableData);

        stage.setScene(new Scene(table));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}


package sample;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public class TableController {

    private final ObservableList<Item> items = FXCollections.observableArrayList();

    @FXML
    private void loadItems() {
        items.setAll(createItems());
    }

    private List<Item> createItems() {
        return IntStream.rangeClosed(1, 100)
                .mapToObj(i -> "Item "+i)
                .map(Item::new)
                .collect(Collectors.toList());
    }

    public ObservableList<Item> getItems() {
        return items ;
    }


    public static class Item {
        private final StringProperty name = new SimpleStringProperty();

        public Item(String name) {
            setName(name);
        }

        public final StringProperty nameProperty() {
            return this.name;
        }

        public final java.lang.String getName() {
            return this.nameProperty().get();
        }

        public final void setName(final java.lang.String name) {
            this.nameProperty().set(name);
        }

    }
}

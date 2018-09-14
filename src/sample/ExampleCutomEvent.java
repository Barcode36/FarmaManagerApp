package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ExampleCutomEvent extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button btn = new Button("Say 'Hello World'");
        btn.setOnAction((ActionEvent event) -> {
            btn.fireEvent(new CustomEvent1(42));
            btn.fireEvent(new CustomEvent2("Hello World"));
        });

        btn.addEventHandler(CustomEvent.CUSTOM_EVENT_TYPE, new MyCustomEventHandler() {

            @Override
            public void onEvent1(int param0) {
                System.out.println("integer parameter: " + param0);
            }

            @Override
            public void onEvent2(String param0) {
                System.out.println("string parameter: "+param0);
            }
        });
    }
}

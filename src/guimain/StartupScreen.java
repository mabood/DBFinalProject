package guimain;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StartupScreen {
    private Stage splash;
    private Label startupTitle;
    private Label message;

    public StartupScreen(Stage initStage) {
        splash = initStage;
        splash.initStyle(StageStyle.UNDECORATED);
        startupTitle = new Label("Beer DB");
        message = new Label("Loading...");
    }

    public Stage getStage() {
        return splash;
    }

    public boolean display() {
        splash.setTitle("Add a Bar");
        splash.setMinWidth(600);
        splash.setMinHeight(400);

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 20, 10));
        layout.setVgap(30);
        layout.setAlignment(Pos.CENTER);

        GridPane.setConstraints(startupTitle, 0, 0);
        GridPane.setConstraints(message, 0, 1);

        layout.getChildren().addAll(startupTitle, message);

        Scene startup = new Scene(layout);
        splash.setScene(startup);

        splash.show();
        return true;
    }

    public void close() {
        splash.hide();
    }
}

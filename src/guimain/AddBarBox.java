package guimain;

import BeerDB.Bar;
import BeerDB.BeardyBee;
import BeerDB.Brewery;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddBarBox {
    private boolean changed;
    private Label barNameLabel;
    private Label barBlankError;
    private Label barExistsError;
    private Label locationLabel;
    private Label locationError;

    private TextField barNameField;
    private TextField locationField;


    public AddBarBox() {
        changed = false;
        barNameLabel = new Label("Bar Name:");
        barBlankError = new Label("Bar name cannot be blank!");
        barExistsError = new Label("Bar already exists!");
        barBlankError.setVisible(false);
        barExistsError.setVisible(false);
        barNameField = new TextField();
        barNameField.setMinWidth(200);

        locationLabel = new Label("Bar Location:");
        locationError = new Label("Location cannot be blank!");
        locationError.setVisible(false);
        locationField = new TextField();
        locationField.setMinWidth(200);
    }

    public void display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add a Bar");
        window.setMinWidth(500);

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 20, 10));
        layout.setVgap(10);

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 0, 10,0));
        pane.setVgap(4);
        pane.setHgap(10);

        Label instructions = new Label("Enter the following Bar information:");

        GridPane.setConstraints(barNameLabel, 0, 0);
        GridPane.setConstraints(barNameField, 1, 0);
        GridPane.setConstraints(barBlankError, 1, 1);
        GridPane.setConstraints(barExistsError, 1, 1);
        GridPane.setConstraints(locationLabel, 0, 2);
        GridPane.setConstraints(locationField, 1, 2);
        GridPane.setConstraints(locationError, 1, 3);

        pane.getChildren().addAll(
                barNameLabel, barNameField, barBlankError, barExistsError,
                locationLabel, locationField, locationError);

        pane.setAlignment(Pos.CENTER);

        HBox buttons = new HBox(10);
        //Create two buttons
        Button yesButton = new Button("Add Bar");
        Button noButton = new Button("Cancel");


        //Clicking will set answer and close window
        yesButton.setOnAction(e -> {
            if (onSubmitClick()) {
                window.close();
            }
        });
        noButton.setOnAction(e -> {
            window.close();
        });

        buttons.getChildren().addAll(yesButton, noButton);
        buttons.setAlignment(Pos.CENTER);

        GridPane.setConstraints(instructions, 0, 1);
        GridPane.setConstraints(pane, 0, 2);
        GridPane.setConstraints(buttons, 0, 3);

        layout.getChildren().addAll(instructions, pane, buttons);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    private Bar validateFields() {
        Bar bar = null;
        String barName = barNameField.getCharacters().toString();
        String location = locationField.getCharacters().toString();
        boolean validated = true;

        if (barName.length() == 0) {
            validated = false;
            barBlankError.setVisible(true);
            barExistsError.setVisible(false);
        }
        else if (BeardyBee.barExists(barName + location)) {
            validated = false;
            barBlankError.setVisible(false);
            barExistsError.setVisible(true);
        }
        else {
            barBlankError.setVisible(true);
            barBlankError.setVisible(false);
        }

        if (location.length() == 0) {
            validated = false;
            locationError.setVisible(true);
        }
        else {
            locationError.setVisible(false);
        }

        //after all checks, if validated then create brewery
        if (validated) {
            bar = new Bar(barName, location);
        }

        return bar;
    }

    private boolean onSubmitClick() {
        Bar toAdd = validateFields();
        if (toAdd != null) {
            BeardyBee.insertBar(toAdd);
            changed = true;
            return true;
        }
        else {
            return false;
        }
    }

    public boolean changesMade() {
        return changed;
    }
}

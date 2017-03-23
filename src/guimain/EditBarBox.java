package guimain;

import BeerDB.Bar;
import BeerDB.BeardyBee;
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

public class EditBarBox {
    private boolean changed;
    private Bar toEdit;

    private Label barNameLabel;
    private Label barBlankError;
    private Label barExistsError;
    private Label locationLabel;
    private Label locationError;
    private Label modifiedError;

    private TextField barNameField;
    private TextField locationField;


    public EditBarBox(Bar toEdit) {
        this.toEdit = toEdit;
        changed = false;
        barNameLabel = new Label("Bar Name:");
        barBlankError = new Label("Bar name cannot be blank!");
        barExistsError = new Label("Bar already exists!");
        barBlankError.setVisible(false);
        barExistsError.setVisible(false);
        barNameField = new TextField();
        barNameField.setText(toEdit.getBarName());
        barNameField.setMinWidth(200);

        locationLabel = new Label("Bar Location:");
        locationError = new Label("Location cannot be blank!");
        locationError.setVisible(false);
        locationField = new TextField();
        locationField.setText(toEdit.getBarLocation());
        locationField.setMinWidth(200);

        modifiedError = new Label("No changes detected!");
        modifiedError.setVisible(false);
    }

    public void display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Edit a Bar");
        window.setMinWidth(500);

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 20, 10));
        layout.setVgap(10);

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 0, 10,0));
        pane.setVgap(4);
        pane.setHgap(10);

        Label instructions = new Label("Edit the following Bar information:");

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

        HBox errorMessage = new HBox(10);
        errorMessage.setAlignment(Pos.CENTER);
        errorMessage.getChildren().add(modifiedError);
        HBox buttons = new HBox(10);
        //Create two buttons
        Button yesButton = new Button("Edit Bar");
        Button noButton = new Button("Cancel");


        //Clicking will set answer and close window
        yesButton.setOnAction(e -> {
            if (onSubmitClick(window)) {
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
        GridPane.setConstraints(errorMessage, 0, 3);
        GridPane.setConstraints(buttons, 0, 4);

        layout.getChildren().addAll(instructions, pane, errorMessage, buttons);
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

    private boolean onSubmitClick(Stage window) {
        Bar updated = validateFields();
        if (updated != null && !toEdit.compareFields(updated)) {
            System.out.println("first if");
            if (BeardyBee.barExists(updated.getBarName(), updated.getBarLocation())) {
                barBlankError.setVisible(false);
                barExistsError.setVisible(true);
                return false;
            }
            else {
                toEdit.setBarName(updated.getBarName());
                toEdit.setBarLocation(updated.getBarLocation());
                BeardyBee.updateBar(toEdit);
                changed = true;
                return true;
            }

        }
        else if (updated != null && toEdit.compareFields(updated)) {
            System.out.println("here");
            modifiedError.setVisible(true);
            return false;
        }
        else {
            System.out.println("3rd here");
            modifiedError.setVisible(false);
            return false;
        }
    }

    public boolean changesMade() {
        return changed;
    }
}

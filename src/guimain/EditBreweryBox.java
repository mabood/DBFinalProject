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

public class EditBreweryBox {
    private Label breweryNameLabel;
    private Label breweryBlankError;
    private Label locationLabel;
    private Label locationError;

    private TextField breweryNameField;
    private TextField locationField;


    public EditBreweryBox(Brewery toEdit) {
        breweryNameLabel = new Label("Brewery Name:");
        breweryBlankError = new Label("Brewery name cannot be blank!");
        breweryBlankError.setVisible(false);
        breweryNameField = new TextField();
        breweryNameField.setText(toEdit.getBreweryName());
        breweryNameField.setMinWidth(200);

        locationLabel = new Label("Brewery Location:");
        locationError = new Label("Location cannot be blank!");
        locationError.setVisible(false);
        locationField = new TextField();
        locationField.setText(toEdit.getBreweryLocation());
        locationField.setMinWidth(200);
    }

    public void display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Edit Brewery Entry");
        window.setMinWidth(500);

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 20, 10));
        layout.setVgap(10);

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 0, 10,0));
        pane.setVgap(4);
        pane.setHgap(10);

        Label instructions = new Label("Edit the following Brewery information:");

        GridPane.setConstraints(breweryNameLabel, 0, 0);
        GridPane.setConstraints(breweryNameField, 1, 0);
        GridPane.setConstraints(breweryBlankError, 1, 1);
        GridPane.setConstraints(locationLabel, 0, 2);
        GridPane.setConstraints(locationField, 1, 2);
        GridPane.setConstraints(locationError, 1, 3);

        pane.getChildren().addAll(
                breweryNameLabel, breweryNameField, breweryBlankError,
                locationLabel, locationField, locationError);

        pane.setAlignment(Pos.CENTER);

        HBox buttons = new HBox(10);
        //Create two buttons
        Button yesButton = new Button("Update Brewery");
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

    private Brewery validateFields() {
        Brewery brewery = null;
        String breweryName = breweryNameField.getCharacters().toString();
        String location = locationField.getCharacters().toString();
        boolean validated = true;

        if (breweryName.length() == 0) {
            validated = false;
            breweryBlankError.setVisible(true);
        }
        else {
            breweryBlankError.setVisible(false);
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
            brewery = new Brewery(breweryName, location);
        }

        return brewery;
    }

    private boolean onSubmitClick() {
        Brewery toUpdate = validateFields();
        if (toUpdate != null) {
            //BeardyBee.updateBrewery(toAdd);
            System.out.println(toUpdate.toString());
            return true;
        }
        else {
            return false;
        }
    }
}

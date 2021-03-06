package guimain;

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
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddBreweryBox {
    private boolean changed;
    private Label breweryNameLabel;
    private Label breweryBlankError;
    private Label breweryExistsError;
    private Label locationLabel;
    private Label locationError;
    private Label imgLabel;

    private TextField breweryNameField;
    private TextField locationField;
    private TextField imgField;


    public AddBreweryBox() {
        changed = false;
        breweryNameLabel = new Label("Brewery Name:");
        breweryBlankError = new Label("Brewery name cannot be blank!");
        breweryExistsError = new Label("Brewery already exists!");
        breweryBlankError.setVisible(false);
        breweryExistsError.setVisible(false);
        breweryBlankError.getStyleClass().add("error");
        breweryExistsError.getStyleClass().add("error");
        breweryNameField = new TextField();
        breweryNameField.setMinWidth(200);

        locationLabel = new Label("Brewery Location:");
        locationError = new Label("Location cannot be blank!");
        locationError.setVisible(false);
        locationError.getStyleClass().add("error");
        locationField = new TextField();
        locationField.setMinWidth(200);

        imgLabel = new Label("Brewery Image URL:");
        imgField = new TextField();
        imgField.setMinWidth(200);
    }

    public void display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add a Brewery");
        window.setMinWidth(500);

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 20, 10));
        layout.setVgap(10);

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 0, 10, 0));
        pane.setVgap(4);
        pane.setHgap(10);

        Label instructions = new Label("Enter the following Brewery information:");

        GridPane.setConstraints(breweryNameLabel, 0, 0);
        GridPane.setConstraints(breweryNameField, 1, 0);
        GridPane.setConstraints(breweryBlankError, 1, 1);
        GridPane.setConstraints(breweryExistsError, 1, 1);
        GridPane.setConstraints(locationLabel, 0, 2);
        GridPane.setConstraints(locationField, 1, 2);
        GridPane.setConstraints(locationError, 1, 3);
        GridPane.setConstraints(imgLabel, 0, 4);
        GridPane.setConstraints(imgField, 1, 4);

        pane.getChildren().addAll(
                breweryNameLabel, breweryNameField, breweryBlankError, breweryExistsError,
                locationLabel, locationField, locationError,
                imgLabel, imgField);

        pane.setAlignment(Pos.CENTER);

        HBox buttons = new HBox(10);
        //Create two buttons
        Button yesButton = new Button("Add Brewery");
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
        GridPane.setConstraints(buttons, 0, 3);

        layout.getChildren().addAll(instructions, pane, buttons);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        scene.getStylesheets().add("guimain/stylesheets/Hornet.css");
        window.setScene(scene);
        window.showAndWait();
    }

    private Brewery validateFields() {
        Brewery brewery = null;
        String breweryName = breweryNameField.getCharacters().toString();
        String location = locationField.getCharacters().toString();
        String imgUrl;
        boolean validated = true;

        if (breweryName.length() == 0) {
            validated = false;
            breweryBlankError.setVisible(true);
            breweryExistsError.setVisible(false);
        }
        else if (BeardyBee.breweryExists(breweryName)) {
            validated = false;
            breweryBlankError.setVisible(false);
            breweryExistsError.setVisible(true);
        }
        else {
            breweryBlankError.setVisible(false);
            breweryExistsError.setVisible(false);
        }

        if (location.length() == 0) {
            validated = false;
            locationError.setVisible(true);
        }
        else {
            locationError.setVisible(false);
        }

        imgUrl = imgField.getCharacters().toString();
        if (!imgUrl.contains("http://") && !imgUrl.contains("https://")) {
            imgUrl = null;
        }

        //after all checks, if validated then create brewery
        if (validated) {
            brewery = new Brewery(breweryName, location);
            brewery.setBreweryImgUrl(imgUrl);
        }

        return brewery;
    }

    private boolean onSubmitClick(Stage window) {
        Brewery toAdd = validateFields();
        if (toAdd != null) {

            BeardyBee.insertBrewery(toAdd);
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

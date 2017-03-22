package guimain;

import BeerDB.BeardyBee;
import BeerDB.Beer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditBeerBox {
    private boolean changed;
    private Beer toEdit;

    private Label beerNameLabel;
    private Label beerNameError;
    private Label breweryNameLabel;
    private Label breweryError;
    private Label abvLabel;
    private Label abvError;
    private Label ibuLabel;
    private Label ibuError;
    private Label imgLabel;
    private Label modifiedError;

    private ComboBox<String> breweryDropdown;
    private TextField beerNameField;
    private TextField abvField;
    private TextField ibuField;
    private TextField imgField;

    public EditBeerBox(Beer toEdit) {
        this.toEdit = toEdit;
        changed = false;
        beerNameLabel = new Label("Beer Name:");
        beerNameError = new Label("Beer Name cannot be blank!");
        beerNameError.setVisible(false);
        beerNameField = new TextField();
        beerNameField.setText(toEdit.getBeerName());
        beerNameField.setMinWidth(200);

        breweryNameLabel = new Label("Brewery Name:");
        breweryError = new Label("Brewery cannot be blank!");
        breweryError.setVisible(false);
        breweryDropdown = new ComboBox<>(buildDropdown());
        breweryDropdown.setValue(toEdit.getBreweryName());
        breweryDropdown.setMinWidth(200);

        abvLabel = new Label("Alcohol By Volume:");
        abvError = new Label("ABV value must be between 0 and 100!");
        abvError.setVisible(false);
        abvField = new TextField();
        abvField.setText(String.valueOf(toEdit.getBeerAbv()));
        abvField.setMaxWidth(60);

        ibuLabel = new Label("IBU score:");
        ibuError = new Label("IBU value must be between 0 and 100!");
        ibuError.setVisible(false);
        ibuField = new TextField();
        ibuField.setText(String.valueOf(toEdit.getBeerIbu()));
        ibuField.setMaxWidth(60);

        imgLabel = new Label("Beer Image URL:");
        imgField = new TextField();
        if (toEdit.getBeerImgUrl() != null) {
            imgField.setText(toEdit.getBeerImgUrl());
        }
        imgField.setMinWidth(200);

        modifiedError = new Label("No changes detected!");
        modifiedError.setVisible(false);
    }

    public ObservableList<String> buildDropdown() {
        ObservableList<String> options = FXCollections.observableArrayList();
        for (String name : BeardyBee.getBreweryNames()) {
            options.add(name);
        }
        return options;
    }

    public void display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Edit Beer Entry");
        window.setMinWidth(500);

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 20, 10));
        layout.setVgap(10);

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 0, 10, 0));
        pane.setVgap(4);
        pane.setHgap(10);

        Label instructions = new Label("Edit the following Beer information:");

        GridPane.setConstraints(beerNameLabel, 0, 0);
        GridPane.setConstraints(beerNameField, 1, 0);
        GridPane.setConstraints(beerNameError, 1, 1);
        GridPane.setConstraints(breweryNameLabel, 0, 2);
        GridPane.setConstraints(breweryDropdown, 1, 2);
        GridPane.setConstraints(breweryError, 1, 3);
        GridPane.setConstraints(abvLabel, 0, 4);
        GridPane.setConstraints(abvField, 1, 4);
        GridPane.setConstraints(abvError, 1, 5);
        GridPane.setConstraints(ibuLabel, 0, 6);
        GridPane.setConstraints(ibuField, 1, 6);
        GridPane.setConstraints(ibuError, 1, 7);
        GridPane.setConstraints(imgLabel, 0, 8);
        GridPane.setConstraints(imgField, 1, 8);

        pane.getChildren().addAll(
                beerNameLabel, beerNameField, beerNameError,
                breweryNameLabel, breweryDropdown, breweryError,
                abvLabel, abvField, abvError,
                ibuLabel, ibuField, ibuError,
                imgLabel, imgField);

        pane.setAlignment(Pos.CENTER);

        HBox errorMessage = new HBox(10);
        errorMessage.setAlignment(Pos.CENTER);
        errorMessage.getChildren().add(modifiedError);
        HBox buttons = new HBox(10);
        //Create two buttons
        Button yesButton = new Button("Update Beer");
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

    private Beer validateFields() {
        Beer updated = null;
        String beerName = beerNameField.getCharacters().toString();
        String breweryName = breweryDropdown.getValue();
        Double abv = 0.0;
        int ibu = 0;
        String imgUrl;
        boolean validated = true;

        if (beerName.length() == 0) {
            validated = false;
            beerNameError.setVisible(true);
        }
        else {
            beerNameError.setVisible(false);
        }

        if (breweryName == null) {
            validated = false;
            breweryError.setVisible(true);
        }
        else {
            breweryError.setVisible(false);
        }

        try {
            abv = Double.parseDouble(abvField.getCharacters().toString());
            abvError.setVisible(false);
        }
        catch (Exception e) {
            abvError.setVisible(true);
            validated = false;
        }

        try {
            ibu = Integer.parseInt(ibuField.getCharacters().toString());
            ibuError.setVisible(false);
        }
        catch (Exception e) {
            ibuError.setVisible(true);
            validated = false;
        }

        imgUrl = imgField.getCharacters().toString();

        if (!imgUrl.contains("http://") && !imgUrl.contains("https://")) {
            imgUrl = null;
        }

        //if after all the checks, validated is still true, update Beer object
        if (validated) {
            updated = new Beer(beerName, breweryName, abv, ibu);
            updated.setBeerImgUrl(imgUrl);
        }

        return updated;
    }

    private boolean onSubmitClick(Stage window) {
        Beer updated = validateFields();
        if (updated != null && !toEdit.equals(updated)) {
            modifiedError.setVisible(false);
            toEdit.setBeerName(updated.getBeerName());
            toEdit.setBreweryName(updated.getBreweryName());
            toEdit.setBeerAbv(updated.getBeerAbv());
            toEdit.setBeerIbu(updated.getBeerIbu());
            toEdit.setBeerImgUrl(updated.getBeerImgUrl());
            BeardyBee.updateBeer(toEdit);
            changed = true;
            return true;
        }
        else if (updated != null && toEdit.equals(updated)) {
            modifiedError.setVisible(true);
            return false;
        }
        else {
            modifiedError.setVisible(false);
            return false;
        }
    }

    public boolean changesMade() {
        return changed;
    }
}

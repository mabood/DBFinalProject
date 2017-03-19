package guimain;

import BeerDB.BeardyBee;
import BeerDB.Beer;
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

public class AddBeerBox {
    private boolean changed;
    private Label beerNameLabel;
    private Label beerNameError;
    private Label breweryNameLabel;
    private Label breweryError;
    private Label abvLabel;
    private Label abvError;
    private Label ibuLabel;
    private Label ibuError;
    private Label imgLabel;

    private TextField beerNameField;
    private TextField breweryField;
    private TextField abvField;
    private TextField ibuField;
    private TextField imgField;

    public AddBeerBox() {
        changed = true;
        beerNameLabel = new Label("Beer Name:");
        beerNameError = new Label("Beer Name cannot be blank!");
        beerNameError.setVisible(false);
        beerNameField = new TextField();
        beerNameField.setMinWidth(200);

        breweryNameLabel = new Label("Brewery Name:");
        breweryError = new Label("Brewery not found! Add it to the Breweries table.");
        breweryError.setVisible(false);
        breweryField = new TextField();
        breweryField.setMinWidth(200);

        abvLabel = new Label("Alcohol By Volume:");
        abvError = new Label("ABV value must be between 0 and 100!");
        abvError.setVisible(false);
        abvField = new TextField();
        abvField.setMaxWidth(60);

        ibuLabel = new Label("IBU score:");
        ibuError = new Label("IBU value must be between 0 and 100!");
        ibuError.setVisible(false);
        ibuField = new TextField();
        ibuField.setMaxWidth(60);

        imgLabel = new Label("Beer Image URL:");
        imgField = new TextField();
        imgField.setMinWidth(200);
    }

    public void display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add a Beer");
        window.setMinWidth(500);

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 20, 10));
        layout.setVgap(10);

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 0, 10, 0));
        pane.setVgap(4);
        pane.setHgap(10);

        Label instructions = new Label("Enter the following Beer information:");

        GridPane.setConstraints(beerNameLabel, 0, 0);
        GridPane.setConstraints(beerNameField, 1, 0);
        GridPane.setConstraints(beerNameError, 1, 1);
        GridPane.setConstraints(breweryNameLabel, 0, 2);
        GridPane.setConstraints(breweryField, 1, 2);
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
                breweryNameLabel, breweryField, breweryError,
                abvLabel, abvField, abvError,
                ibuLabel, ibuField, ibuError,
                imgLabel, imgField);

        pane.setAlignment(Pos.CENTER);

        HBox buttons = new HBox(10);
        //Create two buttons
        Button yesButton = new Button("Add Beer");
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
        window.setScene(scene);
        window.showAndWait();
    }

    private Beer validateFields() {
        Beer beer = null;
        String beerName = beerNameField.getCharacters().toString();
        String breweryName = breweryField.getCharacters().toString();
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

        if (!BeardyBee.breweryExists(breweryName)) {
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

        //if after all the checks, validated is still true, create Beer object
        if (validated) {
            beer = new Beer(beerName, breweryName, abv, ibu);
            beer.setBeerImgUrl(imgUrl);
        }

        return beer;
    }

    private boolean onSubmitClick(Stage window) {
        Beer toAdd = validateFields();
        if (toAdd != null) {
            BeardyBee.insertBeer(toAdd);
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

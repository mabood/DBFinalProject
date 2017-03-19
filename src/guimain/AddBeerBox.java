package guimain;

import BeerDB.Beer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddBeerBox {
    //Create variable
    static Beer created;

    public static boolean display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add a Beer to the Beers table");
        window.setMinWidth(500);

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 20, 10));
        layout.setVgap(10);

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setVgap(4);
        pane.setHgap(10);

        Label instructions = new Label("Enter the following Beer information:");

        //Attribute labels
        Label beerNameLabel = new Label("Beer Name:");
        Label beerNameError = new Label("Beer Name left blank.");
        beerNameError.setVisible(false);
        Label breweryNameLabel = new Label("Brewery Name: ");
        Label breweryError = new Label("Brewery not found! Add it to the Breweries table.");
        breweryError.setVisible(false);
        Label abvLabel = new Label("Alcohol By Volume:");
        Label abvError = new Label("ABV value must be between 0 and 100.");
        abvError.setVisible(false);
        Label ibuLabel = new Label("IBU score:");
        Label ibuError = new Label("IBU value must be between 0 and 100.");
        ibuError.setVisible(false);
        Label imgLabel = new Label("Beer Image URL:");

        TextField beerNameField = new TextField();
        beerNameField.setMinWidth(200);
        TextField breweryField = new TextField();
        breweryField.setMinWidth(200);
        TextField abvField = new TextField();
        abvField.setMaxWidth(60);
        TextField ibuField = new TextField();
        ibuField.setMaxWidth(60);
        TextField imgField = new TextField();
        imgField.setMinWidth(200);

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
            window.close();
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

        //Make sure to return answer
        return true;
    }
}

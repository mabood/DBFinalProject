package guimain;

import BeerDB.BeardyBee;
import BeerDB.Beer;
import BeerDB.BeerBuzz;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddRatingBox {
    private boolean changed;
    private StarRating ratingControl;
    private Label beerTitle;
    private Label blankError;
    private Button submitButton;
    private Button cancelButton;

    private Beer toRate;

    public AddRatingBox(Beer toRate) {
        this.toRate = toRate;
        changed = false;
        beerTitle = new Label("Add a rating for " + toRate.getBeerName());
        ratingControl = new StarRating(false);
        ratingControl.displaySelectRating();

        blankError = new Label("Enter a rating from 1-5");
        blankError.setVisible(false);

        submitButton = new Button("Submit Rating");
        cancelButton = new Button("Cancel");
    }

    public void display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Rate " + toRate.getBeerName());
        window.setMinWidth(500);

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 20, 10));
        layout.setVgap(10);

        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10, 0, 10,0));

        pane.getChildren().addAll(ratingControl.getNode(), blankError, beerTitle);
        pane.setAlignment(Pos.CENTER);

        HBox buttons = new HBox(10);
        //Create two buttons


        //Clicking will set answer and close window
        submitButton.setOnAction(e -> {
            if (onSubmitClick(window)) {
                window.close();
            }
        });
        cancelButton.setOnAction(e -> {
            window.close();
        });

        buttons.getChildren().addAll(submitButton, cancelButton);
        buttons.setAlignment(Pos.CENTER);

        GridPane.setConstraints(pane, 0, 2);
        GridPane.setConstraints(buttons, 0, 3);

        layout.getChildren().addAll(pane, buttons);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    private BeerBuzz validateFields() {
        boolean validated = true;
        BeerBuzz buzz = null;
        int ratingValue = (int)ratingControl.getRatingValue();

        if (ratingValue < 1 || ratingValue > 5) {
            validated = false;
            blankError.setVisible(true);
        }
        else {
            blankError.setVisible(false);
        }

        if (validated) {
            buzz = new BeerBuzz(toRate.getBeerId(), ratingValue);
        }

        return buzz;
    }

    private boolean onSubmitClick(Stage window) {
        BeerBuzz toAdd = validateFields();
        if (toAdd != null) {
            BeardyBee.insertRating(toAdd);
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

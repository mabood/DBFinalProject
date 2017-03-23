package guimain;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Rating;

public class StarRating {
    private Rating rating;
    private VBox container;
    private Label below;
    private double starsValue;

    public StarRating(boolean belowText) {
        rating = new Rating();
        rating.setMax(5);
        rating.setPartialRating(true);
        rating.setUpdateOnHover(true);
        starsValue = 0;

        container = new VBox(5);
        container.setAlignment(Pos.CENTER);
        container.getChildren().add(rating);

        below = new Label();
        below.setVisible(false);
        container.getChildren().add(below);

        if (belowText) {
            below.setVisible(true);
        }
    }

    public Node getNode() {
        return container;
    }

    public double getRatingValue() {
        return rating.getRating();
    }

    private void updateValue(double value) {
        if (value > 5) {
            starsValue = 5;
        }
        else if (value < 0) {
            starsValue = 0;
        }
        else {
            starsValue = value;
        }
        rating.setRating(starsValue);

        String belowText = "Avg Rating: ";
        belowText += String.format("%.1f", starsValue);
        belowText += " / 5";
        below.setText(belowText);
    }

    public void displayFixedRating(double stars) {
        rating.setUpdateOnHover(false);
        updateValue(stars);
        container.setVisible(true);
    }

    public void displaySelectRating() {
        rating.setPartialRating(false);
        updateValue(0);
    }


}

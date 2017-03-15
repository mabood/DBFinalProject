package guimain;

import BeerDB.Beer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class BeerTableView {

    //Need to add the labe values as variables here

    public BeerTableView() {}

    public void updateMeta(Beer selected) {}

    public void updateTable() {}

    public BorderPane CreateLayout() {

        VBox middleBox = new VBox(10);

        Label label1 = new Label("beer tuple 1");
        Label label2 = new Label("beer tuple 2");
        Label label3 = new Label("beer tuple 3");

        middleBox.getChildren().addAll(label1, label2, label3);

        GridPane metaBox = new GridPane();
        metaBox.setPadding(new Insets(10,10,10,10));
        metaBox.setVgap(10);
        metaBox.setHgap(10);

        Label imgLabel = new Label("<Image goes here>");
        Label beerTitle = new Label("Beer tittle");
        Label beerBrewery = new Label("Brewery Name");
        Label beerAbv = new Label("ABV");

        Button button1 = new Button("List all bars serving this beer");

        GridPane.setConstraints(imgLabel, 0, 0);
        GridPane.setConstraints(beerTitle, 0, 1);
        GridPane.setConstraints(beerBrewery, 0, 2);
        GridPane.setConstraints(beerAbv, 0, 3);
        GridPane.setConstraints(button1, 0, 4);

        metaBox.getChildren().addAll(imgLabel, beerTitle, beerBrewery, beerAbv,
                button1);

        BorderPane pane = new BorderPane();

        pane.setCenter(middleBox);
        pane.setRight(metaBox);

        return pane;


    }

}

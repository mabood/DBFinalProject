package guimain;

import BeerDB.Bar;
import BeerDB.BeardyBee;
import BeerDB.Beer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Comparator;
import java.util.PriorityQueue;

import static java.awt.Color.PINK;
import static java.awt.SystemColor.window;

/**
 * Created by chrisyerina on 3/22/17.
 */
public class AddInventoryBox {

    private Label barTitle;
    private Button submitButton;
    private Button cancelButton;

    private Bar shipmentClient;
    private ComboBox<Beer> beerDropdown;

    public AddInventoryBox(Bar selectedBar) {

        this.shipmentClient = selectedBar;
        barTitle = new Label("Add a beer to " + selectedBar.getBarName());
        buildDropdown();
        submitButton = new Button("Submit");
        cancelButton = new Button("Cancel");
        submitButton.setDisable(true);
    }

    public void display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add inventory to " + shipmentClient.getBarName());
        window.setMinWidth(500);

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10, 0, 10, 0));
        layout.setVgap(10);

        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10, 0, 10, 0));

        pane.getChildren().addAll(barTitle, beerDropdown);
        pane.setAlignment(Pos.CENTER);

        HBox buttons = new HBox(10);

        beerDropdown.setOnAction(e -> { submitButton.setDisable(false); });

        submitButton.setOnAction(e -> {
            onSubmitClick();
            window.close();
        });

        cancelButton.setOnAction(e -> {
            window.close();
        });

        buttons.getChildren().addAll(submitButton, cancelButton);
        buttons.setAlignment(Pos.CENTER);

        GridPane.setConstraints(pane, 0, 1);
        GridPane.setConstraints(buttons, 0, 2);

        layout.getChildren().addAll(pane, buttons);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

    }


    private void buildDropdown() {
        ObservableList<Beer> options = FXCollections.observableArrayList();
        PriorityQueue<Beer> bQueue = new PriorityQueue<>(new Comparator<Beer>() {
            @Override
            public int compare(Beer b1, Beer b2) {
                return b1.getBeerName().compareTo(b2.getBeerName());
            }
        });
        for (Beer value : BeardyBee.getBeerMap().values()) {
            bQueue.add(value);
        }
        while (!bQueue.isEmpty()) {
            options.add(bQueue.poll());
        }

        beerDropdown = new ComboBox<>();
        beerDropdown.setItems(options);
        beerDropdown.setCellFactory(new Callback<ListView<Beer>, ListCell<Beer>>() {
            @Override
            public ListCell<Beer> call(ListView<Beer> param) {

                return new ListCell<Beer>(){
                    @Override
                    public void updateItem(Beer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        }
                        else {
                            setText(item.getBeerName());
                        }
                    }
                };
            }
        });

    }

    private void onSubmitClick() {
        Beer item = beerDropdown.getValue();
        BeardyBee.insertInventory(item, shipmentClient);
    }
}

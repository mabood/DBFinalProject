package guimain;

import BeerDB.BeardyBee;
import BeerDB.Beer;
import BeerDB.Brewery;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BreweryTableView {
    private ImageView imgTile;
    private Label imgLabel;

    private Label nameLabel;
    private Label locationLabel;

    private Label breweryName;
    private Label breweryLocation;

    private Button allBeersQuery;
    private ObservableList<Brewery> breweries;
    private TableView<Brewery> breweryTable;


    public BreweryTableView() {
        nameLabel = new Label("Brewery:");
        locationLabel = new Label("Location:");

        imgLabel = new Label("loading image...");
        imgLabel.setVisible(false);
        imgTile = new ImageView();
        imgTile.setPreserveRatio(true);
        imgTile.setFitHeight(250);
        breweryName = new Label();
        breweryLocation = new Label();
        allBeersQuery = new Button("List all bars serving this beer");

        breweries = this.updateBreweries();
        breweryTable = this.CreateTableView();
    }

    public void updateMeta(Brewery selected) {
        Image breweryImg = BreweryImageCache.getImage(selected.getBreweryImgUrl());
        if (breweryImg == null) {
            imgTile.setVisible(false);
            imgLabel.setVisible(true);
        }
        else {
            imgTile.setVisible(true);
            imgLabel.setVisible(false);
        }
        imgTile.setImage(breweryImg);
        breweryName.setText(selected.getBreweryName());
        breweryLocation.setText(selected.getBreweryLocation());
    }

    private ObservableList<Brewery> updateBreweries() {
        return BeardyBee.queryBreweryTable();
    }

    public void updateTable() {
        breweries = updateBreweries();
        breweryTable.setItems(BeardyBee.queryBreweryTable());

        BreweryImageCache.updateBreweries(breweries);
        (new Thread(new BreweryImageCache())).start();
        //beerTable.getSelectionModel().selectFirst();
    }

    public BorderPane CreateLayout() {

        updateTable();

        BorderPane metaPane = new BorderPane();

        GridPane imgBox = new GridPane();
        imgBox.setPadding(new Insets(10,10,10,10));

        GridPane.setConstraints(imgLabel, 0, 0);
        GridPane.setConstraints(imgTile, 0, 0);

        imgBox.getChildren().addAll(imgLabel, imgTile);


        GridPane metaBox = new GridPane();
        metaBox.setPadding(new Insets(10,10,10,10));
        metaBox.setVgap(10);
        metaBox.setHgap(10);

        GridPane.setConstraints(nameLabel, 0, 0);
        GridPane.setConstraints(breweryName, 1, 0);
        GridPane.setConstraints(locationLabel, 0, 1);
        GridPane.setConstraints(breweryLocation, 1, 1);

        metaBox.getChildren().addAll(nameLabel, breweryName, locationLabel, breweryLocation);

        VBox beerButtons = new VBox(6);
        beerButtons.getChildren().addAll(allBeersQuery);

        HBox leftMargin = new HBox(10);
        HBox rightMargin = new HBox(10);

        metaPane.setTop(imgBox);
        metaPane.setCenter(metaBox);
        metaPane.setBottom(beerButtons);
        metaPane.setLeft(leftMargin);
        metaPane.setRight(rightMargin);

        BorderPane pane = new BorderPane();

        pane.setCenter(breweryTable);
        pane.setRight(metaPane);

        return pane;

    }

    private TableView<Brewery> CreateTableView() {
        TableView<Brewery> table = new TableView<>();

        TableColumn<Brewery, String> breweryNameColumn = new TableColumn<>("Brewery");
        breweryNameColumn.setMinWidth(200);
        breweryNameColumn.setCellValueFactory(new PropertyValueFactory<>("breweryName"));

        TableColumn<Brewery, String> breweryLocationColumn = new TableColumn<>("Location");
        breweryLocationColumn.setMinWidth(200);
        breweryLocationColumn.setCellValueFactory(new PropertyValueFactory<>("breweryLocation"));

        table.getColumns().add(breweryNameColumn);
        table.getColumns().add(breweryLocationColumn);

        table.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->
            updateMeta(newValue)
        );

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return table;
    }

}

package guimain;

import BeerDB.BeardyBee;
import BeerDB.Beer;
import BeerDB.Brewery;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

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
    private Node metaPane;

    public BreweryTableView() {
        nameLabel = new Label("Brewery:");
        locationLabel = new Label("Location:");

        imgLabel = new Label("loading image...");
        imgLabel.setVisible(false);
        imgTile = new ImageView();
        imgTile.setPreserveRatio(true);
        imgTile.setFitHeight(250);

        breweryName = new Label();
        breweryName.setWrapText(true);
        breweryLocation = new Label();
        breweryLocation.setWrapText(true);

        allBeersQuery = new Button("List all beers that this brewery makes");

        breweries = this.updateBreweries();
        breweryTable = this.CreateTableView();
        metaPane = this.buildMeta();
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

        metaPane.setVisible(true);
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

    private Node buildImageContainer() {
        StackPane constrainingPane = new StackPane();

        constrainingPane.setMinWidth(200);
        constrainingPane.setMaxWidth(200);
        constrainingPane.setMinHeight(250);
        constrainingPane.setMaxHeight(250);

        constrainingPane.getChildren().add(imgTile);
        imgTile.fitWidthProperty().bind(constrainingPane.widthProperty());
        constrainingPane.setAlignment(Pos.CENTER);

        return constrainingPane;
    }

    private Node buildMeta() {

        BorderPane metaPane = new BorderPane();

        GridPane imgBox = new GridPane();
        imgBox.setPadding(new Insets(10,10,10,10));

        Node imgContainer = buildImageContainer();

        GridPane.setConstraints(imgLabel, 0, 0);
        GridPane.setConstraints(imgContainer, 0, 0);

        imgBox.getChildren().addAll(imgLabel, imgContainer);
        imgBox.setAlignment(Pos.CENTER);

        GridPane metaBox = new GridPane();
        metaBox.setPadding(new Insets(10,10,10,10));
        metaBox.setVgap(10);
        metaBox.setHgap(10);

        GridPane.setConstraints(nameLabel, 0, 0);
        GridPane.setConstraints(breweryName, 1, 0);
        GridPane.setConstraints(locationLabel, 0, 1);
        GridPane.setConstraints(breweryLocation, 1, 1);

        metaBox.getChildren().addAll(nameLabel, breweryName, locationLabel, breweryLocation);

        VBox breweryButtons = new VBox(6);
        breweryButtons.getChildren().addAll(allBeersQuery);
        breweryButtons.setAlignment(Pos.CENTER);
        breweryButtons.setPadding(new Insets(10, 10, 0, 10));

        HBox leftMargin = new HBox(10);
        HBox rightMargin = new HBox(10);

        metaPane.setTop(imgBox);
        metaPane.setCenter(metaBox);
        metaPane.setBottom(breweryButtons);
        metaPane.setLeft(leftMargin);
        metaPane.setRight(rightMargin);

        metaPane.setMinWidth(300);
        metaPane.setMaxWidth(300);

        metaPane.setVisible(false);

        return metaPane;

    }

    private Node buildFooter() {
        Button addBreweryButton = new Button("Add Brewery");
        addBreweryButton.setOnAction(e -> {
            AddBreweryBox addBrewery = new AddBreweryBox();
            addBrewery.display();
        });

        HBox tableButtons = new HBox(10);
        tableButtons.getChildren().add(addBreweryButton);

        tableButtons.setPadding(new Insets(10,10,10,10));

        return tableButtons;
    }

    public BorderPane CreateLayout() {

        BorderPane pane = new BorderPane();
        updateTable();

        pane.setCenter(breweryTable);
        pane.setRight(metaPane);
        pane.setBottom(buildFooter());

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

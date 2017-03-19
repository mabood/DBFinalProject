package guimain;

import BeerDB.BeardyBee;
import BeerDB.Beer;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class BeerTableView {
    private ImageView imgTile;
    private Label imgLabel;

    private Label titleLabel;
    private Label breweryLabel;
    private Label abvLabel;
    private Label ibuLabel;

    private Label beerTitle;
    private Label beerBrewery;
    private Label beerAbv;
    private Label beerIbu;
    private Button allBarsQuery;
    private ObservableList<Beer> beers;
    private TableView<Beer> beerTable;
    private Node metaPane;


    public BeerTableView() {
        titleLabel = new Label("Beer:");
        breweryLabel = new Label("Brewery:");
        abvLabel = new Label("ABV:");
        ibuLabel = new Label("IBU:");

        imgLabel = new Label("loading image...");

        imgLabel.setVisible(false);
        imgTile = new ImageView();
        imgTile.setPreserveRatio(true);
        imgTile.setFitHeight(250);

        beerTitle = new Label();
        beerTitle.setWrapText(true);
        beerBrewery = new Label();
        beerBrewery.setWrapText(true);
        beerAbv = new Label();
        beerIbu = new Label();
        allBarsQuery = new Button("List all bars serving this beer");

        beerTable = this.CreateTableView();
        metaPane = this.buildMeta();
    }

    public void updateMeta(Beer selected) {
        if (selected != null) {
            Image beerImg = BeerImageCache.getImage(selected.getBeerImgUrl());
            if (selected.getBeerImgUrl() == null) {
                imgTile.setVisible(false);
                imgLabel.setText("No image");
                imgLabel.setVisible(true);
            }
            else if (beerImg == null) {
                imgTile.setVisible(false);
                imgLabel.setText("Loading image...");
                imgLabel.setVisible(true);
            }
            else {
                imgTile.setVisible(true);
                imgLabel.setVisible(false);
            }
            imgTile.setImage(beerImg);
            beerTitle.setText(selected.getBeerName());
            beerBrewery.setText(selected.getBreweryName());
            beerAbv.setText(Double.toString(selected.getBeerAbv()));
            beerIbu.setText(Integer.toString(selected.getBeerIbu()));

            metaPane.setVisible(true);
        }
        else {
            metaPane.setVisible(false);
        }
    }

    private ObservableList<Beer> updateBeers() {
        return BeardyBee.queryBeerTable();
    }

    public void updateTable() {
        beers = updateBeers();
        beerTable.setItems(beers);

        BeerImageCache.updateBeers(beers);

        if (beers.size() > 0) {
            BeerImageCache.fetchImageTimeout(beers.get(0).getBeerImgUrl(), 1500);
            beerTable.getSelectionModel().selectFirst();
        }

        (new Thread(new BeerImageCache())).start();
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

        GridPane.setConstraints(titleLabel, 0, 0);
        GridPane.setConstraints(beerTitle, 1, 0);
        GridPane.setConstraints(breweryLabel, 0, 1);
        GridPane.setConstraints(beerBrewery, 1, 1);
        GridPane.setConstraints(abvLabel, 0, 2);
        GridPane.setConstraints(beerAbv, 1, 2);
        GridPane.setConstraints(ibuLabel, 0, 3);
        GridPane.setConstraints(beerIbu, 1, 3);

        metaBox.getChildren().addAll(titleLabel, beerTitle, breweryLabel, beerBrewery,
                abvLabel, beerAbv, ibuLabel, beerIbu);

        VBox beerButtons = new VBox(6);
        beerButtons.getChildren().addAll(allBarsQuery);
        beerButtons.setAlignment(Pos.CENTER);
        beerButtons.setPadding(new Insets(10,10, 0,10));

        HBox leftMargin = new HBox(10);
        HBox rightMargin = new HBox(10);

        metaPane.setTop(imgBox);
        metaPane.setCenter(metaBox);
        metaPane.setBottom(beerButtons);
        metaPane.setLeft(leftMargin);
        metaPane.setRight(rightMargin);

        metaPane.setMinWidth(250);
        metaPane.setMaxWidth(250);

        metaPane.setVisible(false);

        return metaPane;
    }

    private Node buildFooter() {
        Button addBeerButton = new Button("Add Beer");
        addBeerButton.setOnAction(e -> {
            AddBeerBox addBeer = new AddBeerBox();
            addBeer.display();
            if (addBeer.changesMade()) {
                updateTable();
            }
        });

        HBox tableButtons = new HBox(10);
        tableButtons.getChildren().add(addBeerButton);

        tableButtons.setPadding(new Insets(10,10,10,10));

        return tableButtons;
    }


    public BorderPane CreateLayout() {
        BorderPane pane = new BorderPane();
        updateTable();

        pane.setCenter(beerTable);
        pane.setRight(metaPane);
        pane.setBottom(buildFooter());

        return pane;
    }

    private TableView<Beer> CreateTableView() {
        TableView<Beer> table = new TableView<>();

        TableColumn<Beer, String> beerNameColumn = new TableColumn<>("Name");
        beerNameColumn.setMinWidth(200);
        beerNameColumn.setCellValueFactory(new PropertyValueFactory<>("beerName"));

        TableColumn<Beer, String> breweryColumn = new TableColumn<>("Brewery");
        breweryColumn.setMinWidth(200);
        breweryColumn.setCellValueFactory(new PropertyValueFactory<>("breweryName"));

        TableColumn<Beer, Double> beerAbvColumn = new TableColumn<>("ABV");
        beerAbvColumn.setMinWidth(100);
        beerAbvColumn.setCellValueFactory(new PropertyValueFactory<>("beerAbv"));

        TableColumn<Beer, Integer> beerIbuColumn = new TableColumn<>("IBU");
        beerIbuColumn.setMinWidth(100);
        beerIbuColumn.setCellValueFactory(new PropertyValueFactory<>("beerIbu"));

        table.getColumns().add(beerNameColumn);
        table.getColumns().add(breweryColumn);
        table.getColumns().add(beerAbvColumn);
        table.getColumns().add(beerIbuColumn);

        table.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->
            updateMeta(newValue)
        );

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return table;
    }

}

package guimain;

import BeerDB.BeardyBee;
import BeerDB.Beer;
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

public class BarTableView {
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


    public BarTableView() {
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
        beerBrewery = new Label();
        beerAbv = new Label();
        beerIbu = new Label();
        allBarsQuery = new Button("List all bars serving this beer");

        beers = this.updateBeers();
        beerTable = this.CreateTableView();
    }

    public void updateMeta(Beer selected) {
        Image beerImg = BeerImageCache.getImage(selected.getBeerImgUrl());
        if (beerImg == null) {
            imgTile.setVisible(false);
            imgLabel.setVisible(true);
        }
        else {
            imgTile.setVisible(true);
            imgLabel.setVisible(false);
        }
        imgTile.setImage(beerImg);
        beerTitle.setText(selected.getBeerName());
        beerBrewery.setText(Integer.toString(selected.getBreweryId()));
        beerAbv.setText(Double.toString(selected.getBeerAbv()));
        beerIbu.setText(Integer.toString(selected.getBeerIbu()));
    }

    private ObservableList<Beer> updateBeers() {
        return BeardyBee.queryBeerTable();
    }

    public void updateTable() {
        beers = updateBeers();
        beerTable.setItems(BeardyBee.queryBeerTable());

        BeerImageCache.updateBeers(beers);
        (new Thread(new BeerImageCache())).start();
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

        HBox leftMargin = new HBox(10);
        HBox rightMargin = new HBox(10);

        metaPane.setTop(imgBox);
        metaPane.setCenter(metaBox);
        metaPane.setBottom(beerButtons);
        metaPane.setLeft(leftMargin);
        metaPane.setRight(rightMargin);

        BorderPane pane = new BorderPane();

        pane.setCenter(beerTable);
        pane.setRight(metaPane);

        return pane;

    }

    private TableView<Beer> CreateTableView() {
        TableView<Beer> table = new TableView<>();

        TableColumn<Beer, String> beerNameColumn = new TableColumn<>("Name");
        beerNameColumn.setMinWidth(200);
        beerNameColumn.setCellValueFactory(new PropertyValueFactory<>("beerName"));

        TableColumn<Beer, Integer> breweryColumn = new TableColumn<>("Brewery");
        breweryColumn.setMinWidth(200);
        breweryColumn.setCellValueFactory(new PropertyValueFactory<>("breweryId"));

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
package guimain;

import BeerDB.Bar;
import BeerDB.BeardyBee;
import BeerDB.Beer;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BarTableView {

    private Label nameLabel;
    private Label locationLabel;

    private Label barName;
    private Label barLocation;

    private Button allBeersQuery;
    private ObservableList<Bar> bars;
    private TableView<Bar> barsTable;
    private Node metaPane;

    public BarTableView() {
        nameLabel = new Label("Bar:");
        locationLabel = new Label("Location:");

        barName = new Label();
        barName.setWrapText(true);
        barLocation = new Label();
        barLocation.setWrapText(true);

        allBeersQuery = new Button("List all beers served at this bar");

        barsTable = this.CreateTableView();
        metaPane = this.buildMeta();
    }

    public void updateMeta(Bar selected) {
        barName.setText(selected.getBarName());
        barLocation.setText(selected.getBarLocation());

        metaPane.setVisible(true);
    }

    private ObservableList<Bar> updateBars() {
        return BeardyBee.queryBarsTable();
    }

    public void updateTable() {
        bars = updateBars();
        barsTable.setItems(bars);

        //beerTable.getSelectionModel().selectFirst();
    }

    private Node buildMeta() {

        BorderPane metaPane = new BorderPane();

        GridPane metaBox = new GridPane();
        metaBox.setPadding(new Insets(10,10,10,10));
        metaBox.setVgap(10);
        metaBox.setHgap(10);

        GridPane.setConstraints(nameLabel, 0, 0);
        GridPane.setConstraints(barName, 1, 0);
        GridPane.setConstraints(locationLabel, 0, 1);
        GridPane.setConstraints(barLocation, 1, 1);

        metaBox.getChildren().addAll(nameLabel, barName, locationLabel, barLocation);

        VBox barButtons = new VBox(6);
        barButtons.getChildren().addAll(allBeersQuery);
        barButtons.setAlignment(Pos.CENTER);
        barButtons.setPadding(new Insets(10, 10, 0, 10));

        HBox leftMargin = new HBox(10);
        HBox rightMargin = new HBox(10);

        metaPane.setCenter(metaBox);
        metaPane.setBottom(barButtons);
        metaPane.setLeft(leftMargin);
        metaPane.setRight(rightMargin);

        metaPane.setMinWidth(250);
        metaPane.setMaxWidth(250);

        metaPane.setVisible(false);

        return metaPane;

    }

    private Node buildFooter() {
        Button addBarButton = new Button("Add Bar");
        addBarButton.setOnAction(e -> {
            AddBarBox barBox = new AddBarBox();
            barBox.display();
            if (barBox.changesMade()) {
                updateTable();
            }
        });

        HBox tableButtons = new HBox(10);
        tableButtons.getChildren().add(addBarButton);

        tableButtons.setPadding(new Insets(10,10,10,10));

        return tableButtons;
    }

    public BorderPane CreateLayout() {

        BorderPane pane = new BorderPane();
        updateTable();

        pane.setCenter(barsTable);
        pane.setRight(metaPane);
        pane.setBottom(buildFooter());

        return pane;

    }

    private TableView<Bar> CreateTableView() {
        TableView<Bar> table = new TableView<>();

        TableColumn<Bar, String> barNameColumn = new TableColumn<>("Name");
        barNameColumn.setMinWidth(200);
        barNameColumn.setCellValueFactory(new PropertyValueFactory<>("barName"));

        TableColumn<Bar, String> barLocationColumn = new TableColumn<>("Location");
        barLocationColumn.setMinWidth(200);
        barLocationColumn.setCellValueFactory(new PropertyValueFactory<>("barLocation"));

        table.getColumns().add(barNameColumn);
        table.getColumns().add(barLocationColumn);

        table.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->
            updateMeta(newValue)
        );

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return table;
    }

}

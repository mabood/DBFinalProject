package guimain;

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
import javafx.scene.layout.*;

public class BeerTableView extends GenericTableView<Beer> {

    public BeerTableView() {
        super(true, true);
        metaPane = new BeerMeta(true, true);
    }

    public BeerTableView(boolean showButtons, boolean showImages) {
        super(showImages, showButtons);
        metaPane = new BeerMeta(showImages, showButtons);
    }

    @Override
    public ObservableList<Beer> updateItems() {
        return BeardyBee.queryBeerTable();
    }

    @Override
    public void updateTable() {
        items = updateItems();
        itemTable.setItems(items);

        if (images) {
            BeerImageCache.updateBeers(items);

            if (items.size() > 0) {
                BeerImageCache.fetchImageTimeout(items.get(0).getBeerImgUrl(), 1500);
            }

            (new Thread(new BeerImageCache())).start();
        }

        itemTable.getSelectionModel().selectFirst();
    }

    @Override
    public Node buildFooter() {
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

    @Override
    public TableView<Beer> CreateTableView() {
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
                metaPane.refreshMeta(newValue, this)
        );

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return table;
    }

}

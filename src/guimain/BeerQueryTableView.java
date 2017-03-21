package guimain;

import BeerDB.Beer;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class BeerQueryTableView extends GenericTableView<Beer> {

    public BeerQueryTableView(ObservableList<Beer> content) {
        super(true, false);
        items = content;
        metaPane = new BeerMeta(true, false);
    }

    @Override
    public ObservableList<Beer> updateItems() {
        return items;
    }

    @Override
    public TableView<Beer> buildTable() {
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

        return table;
    }

}

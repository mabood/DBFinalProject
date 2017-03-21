package guimain;

import BeerDB.Bar;
import BeerDB.Beer;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class BarQueryTableView extends GenericTableView<Bar> {

    public BarQueryTableView(ObservableList<Bar> content) {
        super(false, false);
        items = content;
        metaPane = new BarMeta(false);
    }

    @Override
    public ObservableList<Bar> updateItems() {
        return items;
    }

    @Override
    public TableView<Bar> buildTable() {
        TableView<Bar> table = new TableView<>();

        TableColumn<Bar, String> barNameColumn = new TableColumn<>("Name");
        barNameColumn.setMinWidth(200);
        barNameColumn.setCellValueFactory(new PropertyValueFactory<>("barName"));

        TableColumn<Bar, String> barLocationColumn = new TableColumn<>("Location");
        barLocationColumn.setMinWidth(200);
        barLocationColumn.setCellValueFactory(new PropertyValueFactory<>("barLocation"));

        table.getColumns().add(barNameColumn);
        table.getColumns().add(barLocationColumn);

        return table;
    }

}

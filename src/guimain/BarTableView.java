package guimain;

import BeerDB.Bar;
import BeerDB.BeardyBee;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class BarTableView extends GenericTableView<Bar>{

    public BarTableView() {
        super(false, true);
        metaPane = new BarMeta(true);
    }

    public BarTableView(boolean showButtons) {
        super(false, showButtons);
        metaPane = new BarMeta(showButtons);
    }

    @Override
    public ObservableList<Bar> updateItems() {
        return BeardyBee.queryBarsTable();
    }

    @Override
    public void updateTable() {
        items = updateItems();
        itemTable.setItems(items);

        itemTable.getSelectionModel().selectFirst();
    }

    @Override
    public Node buildFooter() {
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

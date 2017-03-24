package guimain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;

abstract class GenericTableView<T> {
    boolean buttons;
    boolean images;

    public GenericMeta<T> metaPane;
    public ObservableList<T> items;
    public TableView<T> itemTable;

    public GenericTableView(boolean showImages, boolean showButtons) {
        images = showImages;
        buttons = showButtons;
        itemTable = this.CreateTableView();
    }

    public ObservableList<T> updateItems() {
        return FXCollections.observableArrayList();
    }

    public void updateTable(Object lastSelection) {
        items = updateItems();
        itemTable.setItems(items);
        itemTable.getSelectionModel().selectFirst();
    }

    public Node buildFooter() {

        HBox tableButtons = new HBox(10);

        return tableButtons;
    }

    public TableView<T> buildTable() {
        TableView<T> table = new TableView<>();

        return table;
    }

    public BorderPane CreateLayout() {

        BorderPane pane = new BorderPane();
        updateTable(null);

        pane.setCenter(itemTable);
        pane.setRight(metaPane.pane);

        if (buttons) {
            pane.setBottom(buildFooter());
        }

        return pane;

    }

    public TableView<T> CreateTableView() {
        TableView<T> table = buildTable();

        table.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->
                metaPane.refreshMeta(newValue, this)
        );

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return table;
    }

}

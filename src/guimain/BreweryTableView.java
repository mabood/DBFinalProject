package guimain;

import BeerDB.BeardyBee;
import BeerDB.Brewery;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

public class BreweryTableView extends GenericTableView<Brewery>{

    public BreweryTableView() {
        super(true, true);
        metaPane = new BreweryMeta(true, true);
    }

    public BreweryTableView(boolean showButtons, boolean showImages) {
        super(showImages, showButtons);
        metaPane = new BreweryMeta(showImages, showButtons);
    }

    @Override
    public ObservableList<Brewery> updateItems() {
        return BeardyBee.queryBreweryTable();
    }

    @Override
    public void updateTable() {
        items = updateItems();
        itemTable.setItems(items);

        if (images) {
            BreweryImageCache.updateBreweries(items);

            if (items.size() > 0) {
                BreweryImageCache.fetchImageTimeout(items.get(0).getBreweryImgUrl(), 1500);
            }

            (new Thread(new BreweryImageCache())).start();
        }

        itemTable.getSelectionModel().selectFirst();
    }

    @Override
    public Node buildFooter() {
        Button addBreweryButton = new Button("Add Brewery");
        addBreweryButton.setOnAction(e -> {
            AddBreweryBox addBrewery = new AddBreweryBox();
            addBrewery.display();
            if (addBrewery.changesMade()) {
                updateTable();
            }
        });

        HBox tableButtons = new HBox(10);
        tableButtons.getChildren().add(addBreweryButton);

        tableButtons.setPadding(new Insets(10,10,10,10));

        return tableButtons;
    }

    @Override
    public TableView<Brewery> buildTable() {
        TableView<Brewery> table = new TableView<>();

        TableColumn<Brewery, String> breweryNameColumn = new TableColumn<>("Brewery");
        breweryNameColumn.setMinWidth(200);
        breweryNameColumn.setCellValueFactory(new PropertyValueFactory<>("breweryName"));

        TableColumn<Brewery, String> breweryLocationColumn = new TableColumn<>("Location");
        breweryLocationColumn.setMinWidth(200);
        breweryLocationColumn.setCellValueFactory(new PropertyValueFactory<>("breweryLocation"));

        table.getColumns().add(breweryNameColumn);
        table.getColumns().add(breweryLocationColumn);

        return table;
    }

}

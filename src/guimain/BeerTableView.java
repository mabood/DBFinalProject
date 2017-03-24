package guimain;

import BeerDB.BeardyBee;
import BeerDB.Beer;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.Iterator;
import java.util.ListIterator;

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
    public void updateTable(Object lastSelection) {
        items = updateItems();
        itemTable.setItems(items);
        itemTable.refresh();
        int lastIndex= -1;

        if (images) {
            BeerImageCache.updateBeers(items);

            if (items.size() > 0) {
                BeerImageCache.fetchImageTimeout(items.get(0).getBeerImgUrl(), 1500);
            }

            (new Thread(new BeerImageCache())).start();
        }

        if (lastSelection instanceof Beer) {
            final Beer cmp = (Beer) lastSelection;
            ListIterator<Beer> it = items.listIterator();
            while (it.hasNext()) {
                if (cmp.compareFields(it.next())) {
                    lastIndex = it.previousIndex();
                    break;
                }
            }
        }
        if (lastIndex > -1) {
            itemTable.getSelectionModel().select(lastIndex);
        }
        else {
            itemTable.getSelectionModel().selectFirst();
        }
    }

    @Override
    public Node buildFooter() {
        Button addBeerButton = new Button("Add Beer");
        addBeerButton.setOnAction(e -> {
            AddBeerBox addBeer = new AddBeerBox();
            addBeer.display();
            if (addBeer.changesMade()) {
                updateTable(null);
            }
        });

        HBox tableButtons = new HBox(10);
        tableButtons.getChildren().add(addBeerButton);

        tableButtons.setPadding(new Insets(10,10,10,10));

        return tableButtons;
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

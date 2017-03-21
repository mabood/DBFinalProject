package guimain;

import BeerDB.Bar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BarMeta extends GenericMeta<Bar> {
    private Label nameLabel;
    private Label locationLabel;

    private Label barName;
    private Label barLocation;

    private Button allBeersQuery;
    private Button editBar;
    private Button removeBar;

    public BarMeta(boolean showButtons) {
        super(false, showButtons);
    }

    @Override
    public void initMeta() {
        nameLabel = new Label("Bar:");
        locationLabel = new Label("Location:");

        barName = new Label();
        barName.setWrapText(true);
        barLocation = new Label();
        barLocation.setWrapText(true);
    }

    @Override
    public void initButtons(){
        allBeersQuery = new Button("List all beers served at this bar");
        editBar = new Button("Edit bar");
        removeBar = new Button("Remove bar");
    }

    private Node buildButtons() {
        VBox buttonBox = new VBox(10);
        if (buttons) {
            buttonBox.getChildren().addAll(editBar, removeBar, allBeersQuery);
            buttonBox.setAlignment(Pos.CENTER);
            buttonBox.setPadding(new Insets(10,10, 0,10));
        }
        return buttonBox;
    }

    private void updateButtons(Bar selected, GenericTableView parentPage) {

    }

    @Override
    public Node buildMeta(){
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

        HBox leftMargin = new HBox(10);
        HBox rightMargin = new HBox(10);

        metaPane.setCenter(metaBox);
        metaPane.setBottom(buildButtons());
        metaPane.setLeft(leftMargin);
        metaPane.setRight(rightMargin);

        metaPane.setMinWidth(250);
        metaPane.setMaxWidth(250);

        metaPane.setVisible(false);

        return metaPane;
    }
    @Override
    public void updateMeta(Bar selected, GenericTableView parentPage){
        barName.setText(selected.getBarName());
        barLocation.setText(selected.getBarLocation());

        if (buttons) {
            updateButtons(selected, parentPage);
        }
    };

}
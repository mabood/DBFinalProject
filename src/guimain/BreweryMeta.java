package guimain;

import BeerDB.BeardyBee;
import BeerDB.Beer;
import BeerDB.Brewery;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BreweryMeta extends GenericMeta<Brewery> {
    private ImageView imgTile;
    private Label imgLabel;

    private Label nameLabel;
    private Label locationLabel;

    private Label breweryName;
    private Label breweryLocation;

    private Button allBeersQuery;
    private Button editBrewery;
    private Button removeBrewery;

    public BreweryMeta(boolean showImages, boolean showButtons) {
        super(showImages, showButtons);
    }
    @Override
    public void initMeta() {
        nameLabel = new Label("Brewery:");
        locationLabel = new Label("Location:");

        breweryName = new Label();
        breweryName.setWrapText(true);
        breweryLocation = new Label();
        breweryLocation.setWrapText(true);
    }

    @Override
    public void initImg(){
        imgLabel = new Label("loading image...");
        imgLabel.setVisible(false);
        imgTile = new ImageView();
        imgTile.setPreserveRatio(true);
        imgTile.setFitHeight(250);
    }

    @Override
    public void initButtons(){
        allBeersQuery = new Button("List all beers from this brewery");
        editBrewery = new Button("Edit brewery");
        removeBrewery = new Button("Remove brewery");

    }

    private Node buildButtons() {
        VBox buttonBox = new VBox(10);
        if (buttons) {
            buttonBox.getChildren().addAll(editBrewery, removeBrewery, allBeersQuery);
            buttonBox.setAlignment(Pos.CENTER);
            buttonBox.setPadding(new Insets(10,10, 0,10));
        }
        return buttonBox;
    }

    private void updateButtons(Brewery selected, GenericTableView parentPage) {
        editBrewery.setOnAction(e -> {
            EditBreweryBox editBox = new EditBreweryBox(selected);
            editBox.display();
            if (editBox.changesMade()) {
                parentPage.updateTable();
                TabManager.refreshTab(TabManager.BEERS_INDEX);
            }
        });
        allBeersQuery.setOnAction(e -> {
            String tabTitle = "";
            ObservableList<Beer> result = BeardyBee.queryBeersFromBrewery(selected);
            BeerQueryTableView beerQTable = new BeerQueryTableView(result);
            if (result.isEmpty()) {
                AlertBox.display("No Beers Found", "There are currently no beers brewed at " + selected.getBreweryName());
            }
            else {
                if (result.size() == 1) {
                    tabTitle = "1 Beer Brewed by ";
                }
                else {
                    tabTitle = result.size() + " Beers Brewed by ";
                }
                int index = TabManager.addTab(tabTitle + selected.getBreweryName(), beerQTable, true);
                TabManager.setActiveTab(index);
            }
        });
    }

    private Node buildImg() {
        GridPane imgBox = new GridPane();

        if (images) {
            imgBox.setPadding(new Insets(10,10,10,10));

            Node imgContainer = buildImageContainer(imgTile, 250, 250);

            GridPane.setConstraints(imgLabel, 0, 0);
            GridPane.setConstraints(imgContainer, 0, 0);

            imgBox.getChildren().addAll(imgLabel, imgContainer);
            imgBox.setAlignment(Pos.CENTER);
        }
        return imgBox;
    }

    @Override
    public Node buildMeta(){
        BorderPane metaPane = new BorderPane();

        GridPane metaBox = new GridPane();
        metaBox.setPadding(new Insets(10,10,10,10));
        metaBox.setVgap(10);
        metaBox.setHgap(10);

        GridPane.setConstraints(nameLabel, 0, 0);
        GridPane.setConstraints(breweryName, 1, 0);
        GridPane.setConstraints(locationLabel, 0, 1);
        GridPane.setConstraints(breweryLocation, 1, 1);

        metaBox.getChildren().addAll(nameLabel, breweryName, locationLabel, breweryLocation);

        HBox leftMargin = new HBox(10);
        HBox rightMargin = new HBox(10);

        metaPane.setTop(buildImg());
        metaPane.setCenter(metaBox);
        metaPane.setBottom(buildButtons());
        metaPane.setLeft(leftMargin);
        metaPane.setRight(rightMargin);

        metaPane.setMinWidth(300);
        metaPane.setMaxWidth(300);

        metaPane.setVisible(false);

        return metaPane;
    }

    @Override
    public void updateMeta(Brewery selected, GenericTableView parentPage){
        if (images) {
            Image breweryImg = BreweryImageCache.getImage(selected.getBreweryImgUrl());
            if (selected.getBreweryImgUrl() == null) {
                imgTile.setVisible(false);
                imgLabel.setText("No image");
                imgLabel.setVisible(true);
            }
            else if (breweryImg == null) {
                imgTile.setVisible(false);
                imgLabel.setText("Loading image...");
                imgLabel.setVisible(true);
            }
            else {
                imgTile.setVisible(true);
                imgLabel.setVisible(false);
            }
            imgTile.setImage(breweryImg);

        }

        breweryName.setText(selected.getBreweryName());
        breweryLocation.setText(selected.getBreweryLocation());

        if (buttons) {
            updateButtons(selected, parentPage);
        }
    }

}

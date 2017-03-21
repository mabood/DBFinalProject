package guimain;

import BeerDB.Beer;
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

public class BeerMeta extends GenericMeta<Beer> {
    private ImageView imgTile;
    private Label imgLabel;

    private Label titleLabel;
    private Label breweryLabel;
    private Label abvLabel;
    private Label ibuLabel;

    private Label beerTitle;
    private Label beerBrewery;
    private Label beerAbv;
    private Label beerIbu;

    private Button allBarsQuery;
    private Button editBeer;
    private Button removeBeer;

    public BeerMeta(boolean showImages, boolean showButtons) {
        super(showImages, showButtons);
    }

    @Override
    public void initMeta() {
        titleLabel = new Label("Beer:");
        breweryLabel = new Label("Brewery:");
        abvLabel = new Label("ABV:");
        ibuLabel = new Label("IBU:");

        beerTitle = new Label();
        beerTitle.setWrapText(true);
        beerBrewery = new Label();
        beerBrewery.setWrapText(true);
        beerAbv = new Label();
        beerIbu = new Label();
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
        allBarsQuery = new Button("List all bars serving this beer");
        editBeer = new Button("Edit Beer");
        removeBeer = new Button("Remove Beer");
    }

    private Node buildButtons() {
        VBox buttonBox = new VBox(10);
        if (buttons) {
            buttonBox.getChildren().addAll(editBeer, removeBeer, allBarsQuery);
            buttonBox.setAlignment(Pos.CENTER);
            buttonBox.setPadding(new Insets(10,10, 0,10));
        }
        return buttonBox;
    }

    private void updateButtons(Beer selected, GenericTableView parentPage) {

    }

    private Node buildImg() {
        GridPane imgBox = new GridPane();

        if (images) {
            imgBox.setPadding(new Insets(10,10,10,10));

            Node imgContainer = buildImageContainer(imgTile, 200, 250);

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

        GridPane.setConstraints(titleLabel, 0, 0);
        GridPane.setConstraints(beerTitle, 1, 0);
        GridPane.setConstraints(breweryLabel, 0, 1);
        GridPane.setConstraints(beerBrewery, 1, 1);
        GridPane.setConstraints(abvLabel, 0, 2);
        GridPane.setConstraints(beerAbv, 1, 2);
        GridPane.setConstraints(ibuLabel, 0, 3);
        GridPane.setConstraints(beerIbu, 1, 3);

        metaBox.getChildren().addAll(titleLabel, beerTitle, breweryLabel, beerBrewery,
                abvLabel, beerAbv, ibuLabel, beerIbu);

        HBox leftMargin = new HBox(10);
        HBox rightMargin = new HBox(10);

        metaPane.setTop(buildImg());
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
    public void updateMeta(Beer selected, GenericTableView parentPage){
        if (images) {
            Image beerImg = BeerImageCache.getImage(selected.getBeerImgUrl());
            if (selected.getBeerImgUrl() == null) {
                imgTile.setVisible(false);
                imgLabel.setText("No image");
                imgLabel.setVisible(true);
            }
            else if (beerImg == null) {
                imgTile.setVisible(false);
                imgLabel.setText("Loading image...");
                imgLabel.setVisible(true);
            }
            else {
                imgTile.setVisible(true);
                imgLabel.setVisible(false);
            }
            imgTile.setImage(beerImg);
        }

        beerTitle.setText(selected.getBeerName());
        beerBrewery.setText(selected.getBreweryName());
        beerAbv.setText(Double.toString(selected.getBeerAbv()));

        if (selected.getBeerIbu() > 0) {
            beerIbu.setText(Integer.toString(selected.getBeerIbu()));
            ibuLabel.setVisible(true);
            beerIbu.setVisible(true);
        }
        else {
            ibuLabel.setVisible(false);
            beerIbu.setVisible(false);
        }

        if (buttons) {
            updateButtons(selected, parentPage);
        }
    };

}
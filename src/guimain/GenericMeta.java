package guimain;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

abstract class GenericMeta<T> {
    public boolean images;
    public boolean buttons;
    Node pane;

    public GenericMeta(boolean showImages, boolean showButtons) {
        images = showImages;
        buttons = showButtons;

        initMeta();

        if (showImages) {
            initImg();
        }

        if (showButtons) {
            initButtons();
        }

        pane = buildMeta();
    }

    public void initMeta(){}

    public void initImg(){}

    public void initButtons(){}

    public Node buildMeta() {
        return new BorderPane();
    }

    public void updateMeta(T selected, GenericTableView parentPage){};

    public void refreshMeta(T selected, GenericTableView parentPage) {
        if (selected != null) {
            updateMeta(selected, parentPage);
            pane.setVisible(true);
        }
        else {
            pane.setVisible(false);
        }
    }

    public Node buildImageContainer(ImageView imgTile, double width, double height) {
        StackPane constrainingPane = new StackPane();

        constrainingPane.setMinWidth(width);
        constrainingPane.setMaxWidth(width);
        constrainingPane.setMinHeight(height);
        constrainingPane.setMaxHeight(height);

        constrainingPane.getChildren().add(imgTile);
        imgTile.fitWidthProperty().bind(constrainingPane.widthProperty());
        constrainingPane.setAlignment(Pos.CENTER);

        return constrainingPane;
    }
}

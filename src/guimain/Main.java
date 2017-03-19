package guimain;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Main extends Application {

    Stage window;


    @Override
    public void start(Stage primaryStage) throws Exception{

        window = primaryStage;

        //Example
        window.setTitle("Beer DB");
        Group root = new Group();
        Scene scene = new Scene(root, 900, 600, Color.WHITE);
        TabPane tabPane = new TabPane();
        BorderPane borderPane = new BorderPane();

        //Beers tab
        Tab tab1 = new Tab();
        BeerTableView beersView = new BeerTableView();
        tab1.setText("Beers");
        tab1.setContent(beersView.CreateLayout());
        tab1.setClosable(false);
        tabPane.getTabs().add(tab1);

        //Breweries tab
        Tab tab2 = new Tab();
        BreweryTableView breweriesView = new BreweryTableView();
        tab2.setText("Breweries");
        tab2.setContent(breweriesView.CreateLayout());
        tab2.setClosable(false);
        tabPane.getTabs().add(tab2);

        //Bars tab
        Tab tab3 = new Tab();
        BarTableView barsView = new BarTableView();
        tab3.setText("Bars");
        tab3.setContent(barsView.CreateLayout());
        tab3.setClosable(false);
        tabPane.getTabs().add(tab3);

        // bind to take available space
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());

        borderPane.setCenter(tabPane);
        root.getChildren().add(borderPane);
        window.setScene(scene);
        window.show();
        //end

    }


    public static void main(String[] args) {
        launch(args);
    }
}

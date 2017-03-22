package guimain;

import BeerDB.DBConnector;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    Stage window;


    @Override
    public void start(Stage primaryStage) throws Exception{

        window = primaryStage;

        window.setTitle("Beer DB");
        Group root = new Group();
        Scene scene = new Scene(root, 900, 650, Color.WHITE);
        BorderPane borderPane = new BorderPane();


        //Beers tab
        TabManager.addTab("Beers", new BeerTableView(), false);

        //Breweries tab
        TabManager.addTab("Breweries", new BreweryTableView(), false);

        //Bars tab
        TabManager.addTab("Bars", new BarTableView(), false);

        // bind to take available space
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());

        borderPane.setCenter(TabManager.tabs);
        root.getChildren().add(borderPane);
        window.setScene(scene);
        window.setOnCloseRequest(e -> {
            System.out.println("Closing DB Connection...");
            DBConnector.closeConnection();
        });
        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

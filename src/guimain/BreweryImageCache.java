package guimain;

import BeerDB.Beer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.util.HashMap;


public class BreweryImageCache implements Runnable{
    static HashMap<String, Image> images = new HashMap<>();
    static ObservableList<Beer> beers = FXCollections.observableArrayList();

    static void updateBeers(ObservableList<Beer> updatedBeers) {
        beers = updatedBeers;
    }

    static Image fetchImage(String url) {
        if (images.containsKey(url)) {
            return images.get(url);
        }
        else {
            return images.put(url, new Image(url));
        }
    }

    static Image getImage(String url) {
        if (images.containsKey(url)) {
            return images.get(url);
        }
        else {
            return null;
        }
    }

    @Override
    public void run() {
        for (Beer br : beers) {
            if (!images.containsKey(br.getBeerImgUrl())) {
                images.put(br.getBeerImgUrl(), new Image(br.getBeerImgUrl()));
            }
        }
    }
}

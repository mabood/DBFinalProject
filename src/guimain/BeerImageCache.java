package guimain;

import BeerDB.Beer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import java.util.HashMap;


public class BeerImageCache extends ImageCache implements Runnable {
    static ObservableList<Beer> beers = FXCollections.observableArrayList();

    static void updateBeers(ObservableList<Beer> updatedBeers) {
        beers = updatedBeers;
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
            if (br.getBeerImgUrl() != null && !images.containsKey(br.getBeerImgUrl())) {
                images.put(br.getBeerImgUrl(), new Image(br.getBeerImgUrl()));
            }
        }
    }
}

package guimain;

import BeerDB.Beer;
import BeerDB.Brewery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.util.HashMap;


public class BreweryImageCache extends ImageCache implements Runnable{
    static ObservableList<Brewery> breweries = FXCollections.observableArrayList();

    static void updateBreweries(ObservableList<Brewery> updatedBreweries) {
        breweries = updatedBreweries;
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
        for (Brewery brew : breweries) {
            if (isValidUrl(brew.getBreweryImgUrl()) && !images.containsKey(brew.getBreweryImgUrl())) {
                images.put(brew.getBreweryImgUrl(), new Image(brew.getBreweryImgUrl()));
            }
        }
    }
}

package BeerDB;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

/** BeardyBee backend functionality for application
 *
 */
public class BeardyBee {

    public static ObservableList<Beer> queryBeerTable() {
        ObservableList<Beer> beers = FXCollections.observableArrayList();

        //will query beer table and parse result set to populate beers list

        Beer beer1 = new Beer(1, "Coors Light", 1, 5.5, 10);
        beer1.setBeerImgUrl("http://tmpcompany.com/tmpDash/can.png");

        Beer beer2 = new Beer(2, "Torpedo", 2, 6.2, 45);
        beer2.setBeerImgUrl("http://craftcans.com/craftcanspics/sntorpedo.jpg");

        Beer beer3 = new Beer(3, "Pipeline", 3, 7.1, 23);
        beer3.setBeerImgUrl("http://www.ohbeautifulbeer.com/wp-content/uploads/2013/09/pipeline-porter-bottle.jpg");

        beers.add(beer1);
        beers.add(beer2);
        beers.add(beer3);

        return beers;

    }

    public static ObservableList<Brewery> queryBreweryTable() {
        ObservableList<Brewery> breweries = FXCollections.observableArrayList();

        //will query table and parse result set to populate table

        Brewery brewery1 = new Brewery(1, "Firestone Walker", "Paso Robles, CA");
        brewery1.setBreweryImgUrl("http://brewbound.s3.amazonaws.com/BreweryLogos/Standard/219690562.fwbc.logo.png");

        Brewery brewery2 = new Brewery(2, "BarrelHouse", "Paso Robles, CA");
        brewery2.setBreweryImgUrl("https://www.craftbrewingbusiness.com/wp-content/uploads/2015/01/Barrelhouse-Brewing-logo.jpg");

        Brewery brewery3 = new Brewery(3, "Anheuser-Busch", "St. Louis, MO");
        brewery3.setBreweryImgUrl("https://pbs.twimg.com/profile_images/656966309498847232/b-a0OfKt.png");

        breweries.add(brewery1);
        breweries.add(brewery2);
        breweries.add(brewery3);

        return breweries;
    }

}

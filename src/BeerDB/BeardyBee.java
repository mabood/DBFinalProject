package BeerDB;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

import java.sql.ResultSet;
import java.util.HashSet;

/** BeardyBee backend functionality for application
 *
 */
public class BeardyBee {
    static HashSet<String> breweryNames = new HashSet<>();
    static HashSet<String> barNames = new HashSet<>();

    public static boolean breweryExists(String breweryName) {
        return breweryNames.contains(breweryName);
    }

    public static boolean barExists(String barNamePlusLocation) {
        return barNames.contains(barNamePlusLocation);
    }

    public static void insertBeer(Beer addingBeer) {

        DBConnector.insertTuple(addingBeer);

    }

    public static void insertBrewery(Brewery addingBrewery) {

        DBConnector.insertTuple(addingBrewery);

    }

    public static void insertBar(Bar addingBar) {

        DBConnector.insertTuple(addingBar);

    }

    public static ObservableList<Beer> queryBeerTable() {
        ObservableList<Beer> beers = FXCollections.observableArrayList();
        Beer queryBeer = new Beer(null, null, 0, 0);
        ResultSet beerTuples = DBConnector.queryTable(queryBeer);
        int currentColumn = 0;
        Beer insertingBeer;
        int beerId;
        String beerName;
        String breweryName;
        double beerAbv;
        int beerIbu;
        String beerImgUrl;

        //will query beer table and parse result set to populate beers list

        try {
            while (!beerTuples.isClosed()) {
                beerId = beerTuples.getInt(currentColumn++);
                beerName = beerTuples.getString(currentColumn++);
                breweryName = beerTuples.getString(currentColumn++);
                beerAbv = beerTuples.getDouble(currentColumn++);
                beerIbu = beerTuples.getInt(currentColumn++);
                beerImgUrl = beerTuples.getString(currentColumn++);

                insertingBeer = new Beer(beerName, breweryName, beerAbv, beerIbu);
                insertingBeer.setBeerId(beerId);
                insertingBeer.setBeerImgUrl(beerImgUrl);

                beers.add(insertingBeer);

                beerTuples.next();
                currentColumn = 0;
            }
        }
        catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

//        Beer beer1 = new Beer("Coors Light", "Coors Brewing Company", 5.5, 10);
//        beer1.setBeerImgUrl("http://tmpcompany.com/tmpDash/can.png");
//
//        Beer beer2 = new Beer("Torpedo", "Sierra Nevada", 6.2, 45);
//        beer2.setBeerImgUrl("http://craftcans.com/craftcanspics/sntorpedo.jpg");
//
//        Beer beer3 = new Beer("Pipeline Porter", "Kona Brewing Company", 7.1, 23);
//        beer3.setBeerImgUrl("http://www.ohbeautifulbeer.com/wp-content/uploads/2013/09/pipeline-porter-bottle.jpg");
//
//        beers.add(beer1);
//        beers.add(beer2);
//        beers.add(beer3);

        return beers;

    }

    public static ObservableList<Brewery> queryBreweryTable() {
        ObservableList<Brewery> breweries = FXCollections.observableArrayList();
        Brewery queryBrewery = new Brewery(null, null);
        ResultSet breweryTuples = DBConnector.queryTable(queryBrewery);
        int currentColumn = 0;
        Brewery insertingBrewery;
        String breweryName;
        String breweryLocation;
        String breweryImgUrl;

        try {
            while (!breweryTuples.isClosed()) {
                breweryName = breweryTuples.getString(currentColumn++);
                breweryLocation = breweryTuples.getString(currentColumn++);
                breweryImgUrl = breweryTuples.getString(currentColumn++);

                insertingBrewery = new Brewery(breweryName, breweryLocation);
                insertingBrewery.setBreweryImgUrl(breweryImgUrl);

                breweries.add(insertingBrewery);

                breweryNames.add(breweryName);

                breweryTuples.next();
                currentColumn = 0;
            }
        }
        catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        //will query table and parse result set to populate table

//        Brewery brewery1 = new Brewery("Firestone Walker", "Paso Robles, CA");
//        brewery1.setBreweryImgUrl("http://brewbound.s3.amazonaws.com/BreweryLogos/Standard/219690562.fwbc.logo.png");
//
//        Brewery brewery2 = new Brewery("BarrelHouse", "Paso Robles, CA");
//        brewery2.setBreweryImgUrl("http://brewbound-images.s3.amazonaws.com/wp-content/uploads/2015/01/barrelhouse-brew.png");
//
//        Brewery brewery3 = new Brewery("Anheuser-Busch", "St. Louis, MO");
//        brewery3.setBreweryImgUrl("https://pbs.twimg.com/profile_images/656966309498847232/b-a0OfKt.png");
//
//        breweries.add(brewery1);
//        breweries.add(brewery2);
//        breweries.add(brewery3);
//
//        breweryNames.add(brewery1.getBreweryName());
//        breweryNames.add(brewery2.getBreweryName());
//        breweryNames.add(brewery3.getBreweryName());

        return breweries;
    }

    public static ObservableList<Bar> queryBarsTable() {
        ObservableList<Bar> bars = FXCollections.observableArrayList();
        Bar queryBar = new Bar(null, null); //Change to static method
        ResultSet barTuples = DBConnector.queryTable(queryBar);
        int currentColumn = 0;
        Bar insertingBar;
        int barId;
        String barName;
        String barLocation;

        try {
            while (!barTuples.isClosed()) {
                barId = barTuples.getInt(currentColumn++);
                barName = barTuples.getString(currentColumn++);
                barLocation = barTuples.getString(currentColumn++);

                insertingBar = new Bar(barName, barLocation);
                insertingBar.setBarId(barId);

                bars.add(insertingBar);

                barNames.add(barName + barLocation);

                barTuples.next();
                currentColumn = 0;
            }
        }
        catch (java.sql.SQLException e) {
            e.printStackTrace();
        }


//        Bar bar1 = new Bar("Marstons", "Downtown SLO");
//        Bar bar2 = new Bar("Hop Yard", "Pleasanton CA");
//
//        bars.add(bar1);
//        bars.add(bar2);
//
//        barNames.add(bar1.getBarName() + bar1.getBarLocation());
//        barNames.add(bar2.getBarName() + bar2.getBarLocation());


        return bars;
    }

}

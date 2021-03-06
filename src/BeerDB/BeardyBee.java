package BeerDB;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

import java.sql.ResultSet;
import java.util.*;

/** BeardyBee backend functionality for application
 *
 */
public class BeardyBee {
    static HashSet<String> breweryNames = new HashSet<>();
    static HashMap<Integer, Beer> beerMap = new HashMap<>();
    static HashMap<Integer, Bar> barMap = new HashMap<>();

    public static boolean breweryExists(String breweryName) {
        return breweryNames.contains(breweryName);
    }

    public static boolean barExists(String barName, String barLocation) {
        for (Bar bar : barMap.values()) {
            if (bar.getBarName().equals(barName) && bar.getBarLocation().equals(barLocation)) {
                return true;
            }
        }
        return false;
    }

    public static HashSet<String> getBreweryNames() {
        return breweryNames;
    }

    public static HashMap<Integer, Beer> getBeerMap() {
        return beerMap;
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

    public static void insertInventory(Beer addingBeer, Bar addingBar) {
        Inventory inv = new Inventory(addingBar.getBarId(), addingBeer.getBeerId());
        DBConnector.insertTuple(inv);
    }

    public static void insertRating(BeerBuzz beerBuzz) {

        DBConnector.insertTuple(beerBuzz);
    }

    public static ObservableList<Beer> queryBeersFromBar(Bar bar) {
        ObservableList<Beer> beers = FXCollections.observableArrayList();
        String query = "SELECT beerID\nFROM Inventory\nWHERE barID = " +
                bar.getBarId() + ";\n";
        ResultSet beerTuples = DBConnector.queryTable(query);
        int currentColumn = 1;
        int beerID;

        try {
            while (!beerTuples.isClosed() && beerTuples.next()) {
                beerID = beerTuples.getInt(currentColumn);
                beers.add(beerMap.get(beerID));
            }
        }
        catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        catch (NullPointerException n) {
            System.out.println("Attempt to query table found. check that table exists in DB.");
        }
        finally {
            DBConnector.closeStatement(beerTuples);
        }

        return beers;
    }

    public static ObservableList<Bar> queryBarsFromBeer(Beer beer) {
        ObservableList<Bar> bars = FXCollections.observableArrayList();
        String query = "SELECT barID\nFROM Inventory\nWHERE beerID = " +
                beer.getBeerId() + ";\n";
        ResultSet barTuples = DBConnector.queryTable(query);
        int currentColumn = 1;
        int barID;

        try {
            while (!barTuples.isClosed() && barTuples.next()) {
                barID = barTuples.getInt(currentColumn);
                bars.add(barMap.get(barID));
            }
        }
        catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        catch (NullPointerException n) {
            System.out.println("Attempt to query table found. check that table exists in DB.");
        }
        finally {
            DBConnector.closeStatement(barTuples);
        }

        return bars;
    }

    public static ObservableList<Beer> queryBeersFromBrewery(Brewery brewery) {
        ObservableList<Beer> beers = FXCollections.observableArrayList();
        String query = "SELECT beerID\nFROM Beer\nWHERE breweryName = '" +
                brewery.getBreweryName() + "';\n";
        ResultSet beerTuples = DBConnector.queryTable(query);
        int currentColumn = 1;
        int beerID;

        try {
            while (!beerTuples.isClosed() && beerTuples.next()) {
                beerID = beerTuples.getInt(currentColumn);
                beers.add(beerMap.get(beerID));
            }
        }
        catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        catch (NullPointerException n) {
            System.out.println("Attempt to query table found. check that table exists in DB.");
        }
        finally {
            DBConnector.closeStatement(beerTuples);
        }

        return beers;
    }

    public static ObservableList<Beer> queryBeerTable() {
        ObservableList<Beer> beers = FXCollections.observableArrayList();
        Beer queryBeer = new Beer(null, null, 0, 0);
        ResultSet beerTuples = DBConnector.queryTable(queryBeer);
        int currentColumn = 1;
        Beer insertingBeer;
        int beerId;
        String beerName;
        String breweryName;
        double beerAbv;
        int beerIbu;
        String beerImgUrl;
        double beerRatingAVG;

        //will query beer table and parse result set to populate beers list

        try {
            while (!beerTuples.isClosed() && beerTuples.next()) {
                beerId = beerTuples.getInt(currentColumn++);
                beerName = beerTuples.getString(currentColumn++);
                breweryName = beerTuples.getString(currentColumn++);
                beerAbv = beerTuples.getDouble(currentColumn++);
                beerIbu = beerTuples.getInt(currentColumn++);
                beerImgUrl = beerTuples.getString(currentColumn++);
                beerRatingAVG = beerTuples.getDouble(currentColumn++);

                insertingBeer = new Beer(beerName, breweryName, beerAbv, beerIbu);
                insertingBeer.setBeerId(beerId);
                insertingBeer.setBeerImgUrl(beerImgUrl);
                insertingBeer.setBeerRatingAVG(beerRatingAVG);

                beerMap.put(beerId, insertingBeer);
                beers.add(insertingBeer);


                currentColumn = 1;
            }
        }
        catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        catch (NullPointerException n) {
            System.out.println("Beer table not defined.");
        }
        finally {
            DBConnector.closeStatement(beerTuples);
        }

        return beers;

    }

    public static ObservableList<Brewery> queryBreweryTable() {
        ObservableList<Brewery> breweries = FXCollections.observableArrayList();
        Brewery queryBrewery = new Brewery(null, null);
        ResultSet breweryTuples = DBConnector.queryTable(queryBrewery);
        int currentColumn = 1;
        Brewery insertingBrewery;
        String breweryName;
        String breweryLocation;
        String breweryImgUrl;

        try {
            while (!breweryTuples.isClosed() && breweryTuples.next()) {
                breweryName = breweryTuples.getString(currentColumn++);
                breweryLocation = breweryTuples.getString(currentColumn++);
                breweryImgUrl = breweryTuples.getString(currentColumn++);

                insertingBrewery = new Brewery(breweryName, breweryLocation);
                insertingBrewery.setBreweryImgUrl(breweryImgUrl);

                breweries.add(insertingBrewery);

                breweryNames.add(breweryName);

                currentColumn = 1;
            }
        }
        catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        catch (NullPointerException n) {
            System.out.println("Brewery table not defined.");
        }
        finally {
            DBConnector.closeStatement(breweryTuples);
        }

        return breweries;
    }

    public static ObservableList<Bar> queryBarsTable() {
        ObservableList<Bar> bars = FXCollections.observableArrayList();
        Bar queryBar = new Bar(null, null); //Change to static method
        ResultSet barTuples = DBConnector.queryTable(queryBar);
        int currentColumn = 1;
        Bar insertingBar;
        int barId;
        String barName;
        String barLocation;

        try {
            while (!barTuples.isClosed() && barTuples.next()) {
                barId = barTuples.getInt(currentColumn++);
                barName = barTuples.getString(currentColumn++);
                barLocation = barTuples.getString(currentColumn++);

                insertingBar = new Bar(barName, barLocation);
                insertingBar.setBarId(barId);

                barMap.put(barId, insertingBar);
                bars.add(insertingBar);

                currentColumn = 1;
            }
        }
        catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        catch (NullPointerException n) {
            System.out.println("Bar table not defined.");
        }
        finally {
            DBConnector.closeStatement(barTuples);
        }

        return bars;
    }

    public static void removeBeer(Beer removeBeer) {

        beerMap.remove(removeBeer.getBeerId());
        DBConnector.removeTuple(removeBeer);
    }

    public static void removeBrewery(Brewery removeBrewery) {

        breweryNames.remove(removeBrewery.getBreweryName());
        DBConnector.removeTuple(removeBrewery);
    }

    public static void removeBar(Bar removeBar) {

        barMap.remove(removeBar.getBarId());
        DBConnector.removeTuple(removeBar);
    }

    public static void updateBeer(Beer updatedBeer) {

        DBConnector.updateTuple(updatedBeer);

    }

    public static void updateBrewery(Brewery updatedBrewery) {

        DBConnector.updateTuple(updatedBrewery);

    }

    public static void updateBar(Bar updatedBar) {

        DBConnector.updateTuple(updatedBar);

    }

}

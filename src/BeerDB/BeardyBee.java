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

}

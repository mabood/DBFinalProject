package BeerDB;

import java.sql.*;
import com.mysql.jdbc.Driver;

public class DBConnector {

    static Connection connect;

    public static void connect() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(
                    "jdbc:mysql://beerdb-inst.cmkxc8yt6omz.us-west-2." +
                            "rds.amazonaws.com:3306/Beer_Database?user=beerDB" +
                            "&password=PEOPLEMUMBLE");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    //change return types to ResultSets
    public static ResultSet insertTuple(SQLGenerator obj) {
        String statement = obj.generateInsertStatement();
        //execute statement
    }

    // remove tuple

    // update tuple

    public static ResultSet queryTable(SQLGenerator obj) {
        String statement = obj.generateGetTableStatement();
        //execute statement
    }

    public static ResultSet queryTuple(SQLGenerator obj) {
        String statement = obj.generateGetTupleStatement();
    }

}

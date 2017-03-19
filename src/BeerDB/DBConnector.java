package BeerDB;

import java.sql.*;
import com.mysql.jdbc.Driver;

public class DBConnector {

    private static Connection getConnection() {

        Connection connect;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(
                    "jdbc:mysql://beerdb-inst.cmkxc8yt6omz.us-west-2." +
                            "rds.amazonaws.com:3306/Beer_Database?user=beerDB" +
                            "&password=PEOPLEMUMBLE");

            return connect;
        }
        catch (Exception e) {
            e.printStackTrace();

            // call error box method

            return null;
        }

    }

    private static void executeNoResult(String statement) {

        try {
            Connection connection = getConnection();
            Statement st = connection.createStatement();
            st.execute(statement);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ResultSet executeResult(String statement) {
        ResultSet result;

        try {
            Connection connection = getConnection();
            Statement st = connection.createStatement();
            result = st.executeQuery(statement);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void insertTuple(SQLGenerator obj) {
        String insertStatement = obj.generateInsertStatement();

        if (obj.generateRemoveStatement() != null) {
            executeNoResult(insertStatement);
        }
    }

    // remove tuple
    public static void removeTuple(SQLGenerator obj) {
        String removeStatement = obj.generateRemoveStatement();

        if (obj.generateRemoveStatement() != null) {
            executeNoResult(removeStatement);
        }
    }

    // update tuple
    public static void updateTuple(SQLGenerator obj) {
        String updateStatement = obj.generateInsertStatement();

        if (obj.generateUpdateStatement() != null) {
            executeNoResult(updateStatement);
        }
    }

    // query table
    public static ResultSet queryTable(SQLGenerator obj) {
        String queryStatement = obj.generateGetTableStatement();
        ResultSet rs = null;

        if (obj.generateGetTableStatement() != null) {
            rs = executeResult(queryStatement);
        }

        return rs;
    }


}

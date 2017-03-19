package BeerDB;

import java.sql.*;
import com.mysql.jdbc.Driver;
import guimain.AlertBox;

public class DBConnector {
    static Connection currentConnection = null;

    private static Connection getConnection() {

        try {
            if (currentConnection != null && !currentConnection.isClosed()) {
                return currentConnection;
            }
            else {
                Class.forName("com.mysql.jdbc.Driver");

                Connection connect;
                connect = DriverManager.getConnection(
                        "jdbc:mysql://beerdb-inst.cmkxc8yt6omz.us-west-2." +
                                "rds.amazonaws.com:3306/Beer_Database?user=beerDB" +
                                "&password=PEOPLEMUMBLE");


                return connect;
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection Failed :(");

            AlertBox.display("Database Connection Error", "Database is unreachable.");

            return null;
        }
    }

    private static void closeConnection() {
        try {
            if (currentConnection != null && !currentConnection.isClosed()) {
                currentConnection.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection already closed.");
        }
    }

    private static void executeNoResult(String statement) {

        try {
            Connection connection = getConnection();
            Statement st = connection.createStatement();
            st.execute(statement);
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection Failed :(");

        }
    }

    public static void insertTuple(SQLGenerator obj) {
        String insertStatement = obj.generateInsertStatement();

        if (obj.generateInsertStatement() != null) {
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
        String updateStatement = obj.generateUpdateStatement();

        if (obj.generateUpdateStatement() != null) {
            try {
                Connection connection = getConnection();
                Statement st = connection.createStatement();
                st.execute(updateStatement);
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // query table
    public static ResultSet queryTable(SQLGenerator obj) {
        String queryStatement = obj.generateGetTableStatement();
        ResultSet rs = null;

        if (queryStatement != null) {

            try {
                Connection connection = getConnection();
                Statement st = connection.createStatement();
                rs = st.executeQuery(queryStatement);
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("SQL Query Table statement is null");
        }

        return rs;
    }

}

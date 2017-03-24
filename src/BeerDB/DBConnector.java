package BeerDB;

import java.io.*;
import java.sql.*;
import guimain.AlertBox;
import guimain.CriticalAlertBox;
import java.util.Properties;

public class DBConnector {
    private static Connection currentConnection = null;
    private static String dbUrl = loadConfigs("DATABASE");
    private static String dbUser = loadConfigs("DBUSER");
    private static String dbPassword = loadConfigs("DBPASSWORD");

    private static String loadConfigs(String propertyKey) {
        Properties prop = new Properties();
        InputStream input = null;
        String propertyValue = "";

        try {

            input = new FileInputStream("conf/db.properties");
            prop.load(input);
            propertyValue = prop.getProperty(propertyKey);

        } catch (IOException io) {
            CriticalAlertBox.displayAndQuit("Error reading properties file", io.getMessage());
            io.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return propertyValue;
    }

    private static Connection getConnection() {

        try {
            if (currentConnection != null && !currentConnection.isClosed()) {
                return currentConnection;
            }
            else {
                Class.forName("com.mysql.jdbc.Driver");

                Connection connect;
                connect = DriverManager.getConnection(dbUrl + "?user=" + dbUser + "&password=" + dbPassword);

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

    public static void closeConnection() {
        try {
            if (currentConnection != null && !currentConnection.isClosed()) {
                currentConnection.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection already closed");
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

        System.out.println("Executing Insert:\n" + insertStatement);

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
                System.out.println("Executing Table Query: \n" + queryStatement);
                rs = st.executeQuery(queryStatement);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("SQL Query Table statement is null");
        }

        return rs;
    }

    // query table just with string
    public static ResultSet queryTable(String sqlQuery) {
        String queryStatement = sqlQuery;
        ResultSet rs = null;

        if (queryStatement != null) {

            try {
                Connection connection = getConnection();
                Statement st = connection.createStatement();
                System.out.println("Executing Table Query: " + queryStatement);
                rs = st.executeQuery(queryStatement);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("SQL Query Table statement is null");
        }

        return rs;
    }


    public static void closeStatement(ResultSet rs) {
        try {
            rs.getStatement().close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}

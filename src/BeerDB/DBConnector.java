package BeerDB;

public class DBConnector {
    public static void insertTuple(SQLGenerator obj) {
        String statement = obj.generateInsertStatement();
        //execute statement
    }

    public static void queryTable(SQLGenerator obj) {
        String statement = obj.generateGetTableStatement();
        //execute statement
    }

    public static void queryTuple(SQLGenerator obj) {
        String statement = obj.generateGetTupleStatement();
    }



}

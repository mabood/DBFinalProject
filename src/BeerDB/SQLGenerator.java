package BeerDB;

public interface SQLGenerator {
    String generateInsertStatement();
    String generateGetTableStatement();
    String generateGetTupleStatement();
    String generateUpdateStatement();
    String generateRemoveStatement();
}

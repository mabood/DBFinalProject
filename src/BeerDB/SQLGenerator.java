package BeerDB;

public interface SQLGenerator {
    String generateInsertStatement();
    String generateGetTableStatement();
    String generateUpdateStatement();
    String generateRemoveStatement();
}

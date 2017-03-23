package BeerDB;

public class BeerBuzz implements SQLGenerator{

    private int beerID;
    private int rating;

    public BeerBuzz(int beerID, int rating) {
        this.beerID = beerID;
        this.rating = rating;
    }

    public int getBeerID() {
        return beerID;
    }

    public int getRating() {
        return rating;
    }

    public void setBeerID(int beerID) {
        this.beerID = beerID;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String generateInsertStatement() {

        return "INSERT INTO BeerBuzz (beerID, rating)\nVALUES (" +
                getBeerID() + ", " + getRating() + ");\n";
    }

    @Override
    public String generateGetTableStatement() {

        return "SELECT * FROM BeerBuzz;\n";
    }

    @Override
    public String generateUpdateStatement() {
        return null;
    }

    @Override
    public String generateRemoveStatement() {
        return null;
    }
}

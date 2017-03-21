package BeerDB;

public class Inventory implements SQLGenerator{

    private int barID, beerID;

    public Inventory(int barID, int beerID) {
        this.barID = barID;
        this.beerID = beerID;
    }

    public int getBarID() {
        return barID;
    }

    public int getBeerID() {
        return beerID;
    }

    public void setBarID(int barID) {
        this.barID = barID;
    }

    public void setBeerID(int beerID) {
        this.beerID = beerID;
    }

    @Override
    public String generateInsertStatement() {
        return "INSERT INTO Inventory (barID, beerID)\nVALUES (" + getBarID() +
                ", " + getBeerID() + ");\n";
    }

    // dealt with in beardybee
    @Override
    public String generateGetTableStatement() { return null; }

    // don't need to update
    @Override
    public String generateUpdateStatement() {
        return null;
    }

    @Override
    public String generateRemoveStatement() {
        return "DELETE FROM Inventory\nWHERE beerID = " + getBeerID() + ";\n";
    }
}

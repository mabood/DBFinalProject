package BeerDB;

public class Bar implements SQLGenerator{

    private int barId;
    private String barName;
    private String barLocation;

    public Bar(String barName, String barLocation) {
        this.barName = barName;
        this.barLocation = barLocation;
    }

    public int getBarId() {
        return barId;
    }

    public void setBarId(int barId) {
        this.barId = barId;
    }

    public String getBarName() {
        return barName;
    }

    public void setBarName(String barName) {
        this.barName = barName;
    }

    public String getBarLocation() {
        return barLocation;
    }

    public void setBarLocation(String barLocation) {
        this.barLocation = barLocation;
    }

    @Override
    public String generateInsertStatement() {
        return null;
    }

    @Override
    public String generateGetTableStatement() {
        return null;
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

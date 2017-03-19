package BeerDB;

public class Brewery implements SQLGenerator{

    private int breweryId;
    private String breweryName;
    private String breweryLocation;
    private String breweryImgUrl;

    public Brewery(int breweryId, String breweryName, String breweryLocation) {
        this.breweryId = breweryId;
        this.breweryName = breweryName;
        this.breweryLocation = breweryLocation;
    }

    public int getBreweryId() {
        return breweryId;
    }

    public void setBreweryId(int breweryId) {
        this.breweryId = breweryId;
    }

    public String getBreweryName() {
        return breweryName;
    }

    public void setBreweryName(String breweryName) {
        this.breweryName = breweryName;
    }

    public String getBreweryLocation() {
        return breweryLocation;
    }

    public void setBreweryLocation(String breweryLocation) {
        this.breweryLocation = breweryLocation;
    }

    public String getBreweryImgUrl() {
        return breweryImgUrl;
    }

    public void setBreweryImgUrl(String breweryImgUrl) {
        this.breweryImgUrl = breweryImgUrl;
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
    public String generateGetTupleStatement() {
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

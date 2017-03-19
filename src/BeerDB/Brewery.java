package BeerDB;

public class Brewery implements SQLGenerator{

    private String breweryName;
    private String breweryLocation;
    private String breweryImgUrl;

    public Brewery(String breweryName, String breweryLocation) {
        this.breweryName = breweryName;
        this.breweryLocation = breweryLocation;
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

        String output;

        if (getBreweryImgUrl() == null) {
            output = "INSERT INTO Brewery (name, location)\nVALUES ('" +
                    getBreweryName() + "', '" + getBreweryLocation() + "');\n";
        }
        else {
            output = "INSERT INTO Brewery (name, location, imgURL)\nVALUES ('" +
                    getBreweryName() + "', '" + getBreweryLocation() + "', '" +
                    getBreweryImgUrl() + "');\n";
        }

        return output;
    }

    @Override
    public String generateGetTableStatement() {
        return "SELECT * FROM Brewery\nORDER BY name;\n";
    }


    // won't work for god knows what fuckin reason
    @Override
    public String generateUpdateStatement() {
        return "UPDATE Brewery\nSET name = '" + getBreweryName() + "', " +
                "location = '" + getBreweryLocation() + "', imgURL = '" +
                getBreweryImgUrl() + "'\nWHERE name = '" +
                getBreweryName() + "';\n";
    }


    // keep null, don't need to implement
    @Override
    public String generateRemoveStatement() {
        return null;
    }
}

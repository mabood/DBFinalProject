package BeerDB;

public class Brewery implements SQLGenerator{

    private String breweryName;
    private String breweryLocation;
    private String breweryImgUrl;
    private String formerBreweryName;

    public Brewery(String breweryName, String breweryLocation) {
        this.formerBreweryName = breweryName;
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

    public void updateFields(String breweryName, String breweryLocation, String breweryImgUrl) {
        formerBreweryName = this.breweryName;
        this.breweryName = breweryName;
        this.breweryLocation = breweryLocation;
        this.breweryImgUrl = breweryImgUrl;
    }

    @Override
    public String generateInsertStatement() {

        String output;

        if (getBreweryImgUrl() == null) {
            output = "INSERT INTO Brewery (breweryName, breweryLocation)\n" +
                    "VALUES ('" + getBreweryName() + "', '" +
                    getBreweryLocation() + "');\n";
        }
        else {
            output = "INSERT INTO Brewery (breweryName, breweryLocation," +
                    " imgURL)\nVALUES ('" + getBreweryName() + "', '" +
                    getBreweryLocation() + "', '" + getBreweryImgUrl() +
                    "');\n";
        }

        return output;
    }

    @Override
    public String generateGetTableStatement() {
        return "SELECT * FROM Brewery\nORDER BY breweryName;\n";
    }

    @Override
    public String generateUpdateStatement() {
        String output;

        if (getBreweryImgUrl() == null) {
            output = "UPDATE Brewery\nSET " +
                    "', breweryLocation = '" + getBreweryLocation() +
                    "'\nWHERE breweryName = '" + getBreweryName() + "'\n";

        }
        else {
            output = "UPDATE Brewery\nSET " +
                    "breweryLocation = '" + getBreweryLocation() + "', imgURL = '" +
                    getBreweryImgUrl() + "'\nWHERE breweryName = '" +
                    getBreweryName() + "'\n";
        }

        return output;
    }

    @Override
    public String generateRemoveStatement() {
        return "DELETE FROM Brewery\nWHERE breweryName = " + getBreweryName() +
                ";\n";
    }
}

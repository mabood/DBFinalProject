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
        String sqlStatement = "UPDATE Brewery\n" +
                "SET breweryName = '%s', breweryLocation = '%s', imgURL = %s\n" +
                "WHERE breweryName = '%s'";

        String imgURLupdate = getBreweryImgUrl() == null ? "null" : "'" + getBreweryImgUrl() + "'";

        String output = String.format(sqlStatement, breweryName, breweryLocation, imgURLupdate, formerBreweryName);

        return output;
    }

    @Override
    public String generateRemoveStatement() {
        return String.format("DELETE FROM Brewery\n" +
                "WHERE breweryName = '%s';\n", getBreweryName());
    }

    public boolean compareFields(Object other) {
        if (other == null) {
            return false;
        }
        if (!(other instanceof Brewery)) {
            return false;
        }
        final Brewery cmp = (Brewery)other;

        if (this.breweryName != null && !this.breweryName.equals(cmp.getBreweryName()) || this.breweryName == null && cmp.getBreweryName() != null) {
            return false;
        }
        if (this.breweryLocation != null && !this.breweryLocation.equals(cmp.getBreweryLocation()) || this.breweryLocation == null && cmp.getBreweryLocation() != null) {
            return false;
        }
        if (this.breweryImgUrl != null && !this.breweryImgUrl.equals(cmp.getBreweryImgUrl()) || this.breweryImgUrl == null && cmp.getBreweryImgUrl() != null) {
            return false;
        }
        return true;
    }
}

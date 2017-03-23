package BeerDB;

public class Beer implements SQLGenerator{
    private int beerId;
    private String beerName;
    private String breweryName;
    private double beerAbv;
    private int beerIbu;
    private String beerImgUrl;
    private double beerRatingAVG;

    public Beer(String beerName, String breweryName, double beerAbv, int beerIbu) {
        this.beerName = beerName;
        this.breweryName = breweryName;
        this.beerAbv = beerAbv;
        this.beerIbu = beerIbu;
    }

    public int getBeerId() {
        return beerId;
    }

    public void setBeerId(int beerId) {
        this.beerId = beerId;
    }

    public String getBeerName() {
        return beerName;
    }

    public void setBeerName(String beerName) {
        this.beerName = beerName;
    }

    public String getBreweryName() {
        return breweryName;
    }

    public void setBreweryName(String breweryName) {
        this.breweryName = breweryName;
    }

    public void setBeerRatingAVG(double beerRatingAVG) {
        this.beerRatingAVG = beerRatingAVG;
    }

    public double getBeerRatingAVG() {
        return beerRatingAVG;
    }

    public double getBeerAbv() {
        return beerAbv;
    }

    public void setBeerAbv(double beerAbv) {
        this.beerAbv = beerAbv;
    }

    public int getBeerIbu() {
        return beerIbu;
    }

    public void setBeerIbu(int beerIbu) {
        this.beerIbu = beerIbu;
    }

    public String getBeerImgUrl() {
        return beerImgUrl;
    }

    public void setBeerImgUrl(String beerImgUrl) {
        this.beerImgUrl = beerImgUrl;
    }

    @Override
    public String generateInsertStatement() {
        String output;

        if (getBeerImgUrl() == null) {
            output = "INSERT INTO Beer (beerName, breweryName, beerABV, beerIBU)\n" +
                    "VALUES ('" + getBeerName() + "', '" + getBreweryName() +
                    "', " + getBeerAbv() + ", " + getBeerIbu() + ");\n";
        }
        else {
            output = "INSERT INTO Beer (beerName, breweryName, beerABV, beerIBU," +
                    " imgURL)\n" + "VALUES ('" + getBeerName() + "', '"
                    + getBreweryName() + "', " + getBeerAbv() + ", "
                    + getBeerIbu() + ", '" + getBeerImgUrl() + "');\n";
        }

        return output;
    }

    @Override
    public String generateGetTableStatement() {

        return "SELECT b.*, AVG(COALESCE(bb.rating, 0))\nFROM Beer b LEFT " +
                "OUTER JOIN BeerBuzz bb ON b.beerID = bb.beerID\nGROUP BY " +
                "b.beerID, b.beerName, b.breweryName, b.beerABV, b.beerIBU," +
                "b.imgURL\nORDER BY b.beerName;\n";
    }

    @Override
    public String generateUpdateStatement() {
        String output;

        if (getBeerImgUrl() == null) {
            output = "UPDATE Beer\nSET beerName = '" + getBeerName() + "', beerABV = " +
                    getBeerAbv() + ", beerIBU = " + getBeerIbu() + "\nWHERE beerID = " +
                    getBeerId() + ";\n";
        }
        else {
            output = "UPDATE Beer\nSET beerName = '" + getBeerName() + "', beerABV = " +
                    getBeerAbv() + ", beerIBU = " + getBeerIbu() + ", imgURL = '" +
                    getBeerImgUrl() + "'\nWHERE beerID = " + getBeerId() + ";\n";
        }

        return output;
    }

    @Override
    public String generateRemoveStatement() {
        return "DELETE FROM Beer\nWHERE beerID = " + getBeerId() + ";\n";
    }

    public boolean compareFields(Object other) {
        if (other == null) {
            return false;
        }
        if (!(other instanceof Beer)) {
            return false;
        }
        final Beer cmp = (Beer)other;

        if (this.beerName != null && !this.beerName.equals(cmp.getBeerName()) || this.beerName == null && cmp.getBeerName() != null) {
            return false;
        }
        if (this.breweryName != null && !this.breweryName.equals(cmp.getBreweryName()) || this.breweryName == null && cmp.getBreweryName() != null) {
            return false;
        }
        if (this.beerImgUrl != null && !this.beerImgUrl.equals(cmp.getBeerImgUrl()) || this.beerImgUrl == null && cmp.getBeerImgUrl() != null) {
            return false;
        }
        if (this.beerAbv != cmp.getBeerAbv()) {
            return false;
        }
        if (this.beerIbu != cmp.getBeerIbu()) {
            return false;
        }
        return true;
    }
}

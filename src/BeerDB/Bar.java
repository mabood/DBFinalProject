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
        return "INSERT INTO Bar (barName, barLocation)\n" +
                "VALUES ('" + getBarName() + "', '" +
                getBarLocation() + "');\n";
    }

    @Override
    public String generateGetTableStatement() {
        return "SELECT * FROM Bar\nORDER BY barName;\n";
    }

    @Override
    public String generateUpdateStatement() {
        return "UPDATE Bar\nSET barName = '" + getBarName() + "', barLocation " +
                "= '" + getBarLocation() + "'\nWHERE barID = '" +
                getBarId() + "'\n";
    }

    @Override
    public String generateRemoveStatement() {
        return "DELETE FROM Bar\nWHERE barID = " + getBarId() + ";\n";
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (!(other instanceof Bar)) {
            return false;
        }
        final Bar cmp = (Bar) other;
        return this.barName.equals(cmp.getBarName())
                && this.barLocation.equals(cmp.getBarLocation());
    }
}

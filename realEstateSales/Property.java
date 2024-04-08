import java.time.LocalDate;

sealed abstract class Property permits Apartment, House {
    protected String streetName;
    protected int streetNumber;
    protected String town;
    protected String postcode;
    protected double areaM2;
    protected double price;
    protected LocalDate validTill;

    Property(String streetName, int streetNumber, String town, String postcode,
             double areaM2, double price, LocalDate validTill)
    {
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.town = town;
        this.postcode = postcode;
        this.areaM2 = areaM2;
        this.price = price;
        this.validTill = validTill;
    }

    @Override
    public abstract String toString();

    public int getStreetNumber() {
        return this.streetNumber;
    }

    public String getStreetName() {
        return this.streetName;
    }

    public String getTown() {
        return this.town;
    }

    public String getPostcode() {
        return this.postcode;
    }

    public double getAreaM2() {
        return this.areaM2;
    }

    public double getPrice() {
        return this.price;
    }

    public LocalDate getValidTill() {
        return this.validTill;
    }
}

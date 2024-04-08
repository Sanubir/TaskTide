import java.time.LocalDate;

public final class House extends Property {
    private double lotSizeM2;

    House(String streetName, int streetNumber, String town, String postcode,
          double areaM2, double price, LocalDate validTill, double lotSizeM2)
    {
        super(streetName, streetNumber, town, postcode, areaM2, price, validTill);
        this.lotSizeM2 = lotSizeM2;
    }

    @Override
    public String toString() {
        String string = "";
        String separation = ", ";
        string += "Valid till: " + validTill + separation;
        string += "Price: " + price + separation;
        string += "Town: " + town + separation;
        string += "Street name: " + streetName + separation;
        string += "Street number: " + streetNumber + separation;
        string += "Postcode: " + postcode + separation;
        string += "Area: " + areaM2 + "m^2" + separation;
        string += "Lot size: " + lotSizeM2 + "m^2";
        return string;
    }

    public double getLotSizeM2() {
        return this.lotSizeM2;
    }
}

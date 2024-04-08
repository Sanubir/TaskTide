import java.time.LocalDate;

public final class Apartment extends Property {
    private int apartmentNumber;
    private int floorNumber;

    Apartment(String streetName, int streetNumber, String town, String postcode,
              double areaM2, double price, LocalDate validTill, int apartmentNumber, int floorNumber)
    {
        super(streetName, streetNumber, town, postcode, areaM2, price, validTill);
        this.apartmentNumber = apartmentNumber;
        this.floorNumber = floorNumber;
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
        string += "Apartment number: " + apartmentNumber + separation;
        string += "Floor number: " + floorNumber;
        return string;
    }

    public int getApartmentNumber() {
        return this.apartmentNumber;
    }

    public int getFloorNumber() {
        return this.floorNumber;
    }
}

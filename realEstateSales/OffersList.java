import java.util.ArrayList;
import java.util.function.Predicate;

public class OffersList {
    private ArrayList<Property> offersList;

    public OffersList() {
        this.offersList = new ArrayList<>();
    }

    public void addOffer(House newHouse) {
        offersList.add(newHouse);
    }

    public void addOffer(Apartment newApartment) {
        offersList.add(newApartment);
    }

    public ArrayList<Property> getOffers(Predicate<Property> check) {
        ArrayList<Property> offers = new ArrayList<>();
        for (Property offer : offersList) {
            if (check.test(offer)) offers.add(offer);
        }
        return offers;
    }

    public int getOffersAmount() {
        try {
            return offersList.size();
        } catch (Exception e) {
            return 0;
        }
    }
}

package market;

import country.DayChanger;
import enumerationClasses.TypeProduction;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import service.ResourseProperties;

public class PriceList implements Observer {

    private int dayFromBegin;
    private EnumMap<TypeProduction, Double> priesList;
    private DayChanger dayChanger;
    private List<Offer> Offers;

    public PriceList(DayChanger dayChanger, List<Offer> Offers) {
        this.dayChanger = dayChanger;
        dayChanger.addObserver(this);
        dayFromBegin = 0;
        priesList = new EnumMap<>(TypeProduction.class);
        updatePrice();
    }

    public Double getPriceForOneTonn(TypeProduction type) {
        return priesList.get(type);
    }
// разбить на несколько методов

    private void updatePrice() {
        EnumMap<TypeProduction, Double> sumOfOffers = new EnumMap<>(TypeProduction.class);
        double allWeightOfMarket = 0;
        for (Offer o : Offers) {
            TypeProduction type = o.getTypeProduction();
            Double currentWeight = o.getWeight();
            allWeightOfMarket = +currentWeight;
            currentWeight += sumOfOffers.get(type);
            sumOfOffers.put(type, currentWeight);
        }
        Set<TypeProduction> allType = sumOfOffers.keySet();
        EnumMap<TypeProduction, Double> allRatio = new EnumMap<>(TypeProduction.class);
        for (TypeProduction type : allType) {
            double ratio = sumOfOffers.get(type) / allWeightOfMarket;
            allRatio.put(type, ratio);
        }
        for (TypeProduction type : allType) {
            double newprice = ResourseProperties.getBasicValue(type) / allRatio.get(type);
            priesList.put(type, newprice);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        updatePrice();
    }
}

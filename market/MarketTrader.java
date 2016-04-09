package market;

import building.Stock;
import country.DayChanger;
import enumerationClasses.TypeProduction;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MarketTrader implements ITrader, Observer {

    private IWallet wallet;
    private static int count = 0;
    private Stock stock;
    private int IDTrader;
    DayChanger dayChanger;
    private ITraderUI traderUI;
    List<Offer> listOfOffers;
    Market market;

    public MarketTrader(DayChanger dayChanger, List<Offer> list, Market market) {
        this.dayChanger = dayChanger;
        dayChanger.addObserver(this);
        traderUI = new SimpleMarketUI();
        wallet = new TraderWallet();
        this.market = market;
        IDTrader = count++;
    }

    public void receiveList(List<Offer> list) {
        listOfOffers = list;
    }

    public int getIDTrader() {
        return this.IDTrader;
    }

    public void findApropriateOffer() {
        int idOffer = traderUI.findApropriateOffer(listOfOffers, wallet.getBalance());
        market.pickUpOffer(idOffer, IDTrader);
    }

    public void findProductionForSell() {
        ProductPack pack = traderUI.findProductionForSell(listOfOffers, stock);
        market.applay(pack, IDTrader);
    }

    public void makeDailyOperation() {
        findProductionForSell();
        findApropriateOffer();
    }

    

    public void update(Observable o, Object arg) {
        makeDailyOperation();
    }
    public double getBalance()
    {
        return wallet.getBalance();
        
    }public void takeProductPack(ProductPack pack) {
        TypeProduction type = pack.getTypeProduction();
        double weight = pack.getWeight();
        stock.takeProduct(type, weight);

    }

    public void giveProductPack(ProductPack pack) {
        TypeProduction type = pack.getTypeProduction();
        double weight = pack.getWeight();
        stock.giveProduct(type, weight);
    }
    public void takeMoney(double money)
    {
        wallet.takeMoney(money);
    }
    public void giveMoney(double money)
    {
        wallet.giveMoney(money);
    }
}
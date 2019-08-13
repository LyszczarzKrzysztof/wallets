package wallets3;

import java.math.BigDecimal;
import java.util.List;

public class Offer {
    private String stuff;

    List<Money> prices;

    public Offer(String stuff, List<Money> prices) {
        this.stuff = stuff;
        this.prices = prices;
    }

    public String toString(){
        return "Towar: "+stuff+" ceny: "+ prices;
    }

    public String getStuff() {
        return stuff;
    }

    public List<Money> getPrices() {
        return prices;
    }

    public Money makeUSD( Money money) {

        Money money1= new Money(money.getAmount().multiply(money.getMapOfCurrencyRates().get(money.getCurrency())), Currency.USD);


        if (money.getCurrency().equals(Currency.USD)) {
            return money1;
        }

        return money;
    }


    public boolean hasLowerPrice(Offer offer){
        BigDecimal minPrice = makeUSD(offer.getPrices().get(0)).getAmount();
        int counter = 0;
        boolean condition=false;

        for (Money o: prices) {
            if(makeUSD(o).getAmount().subtract(minPrice).compareTo(new BigDecimal(0))<0){
                counter++;
            }
        }

        if(counter>0){
            condition = true;
        }
        return condition;
    }

    public Money howMuchWillYouPayForThis(Offer offer){
        BigDecimal minPrice = makeUSD(offer.getPrices().get(0)).getAmount();
        Currency minCurrency = offer.getPrices().get(0).getCurrency();
        BigDecimal minAmount = offer.getPrices().get(0).getAmount();

        if(hasLowerPrice(offer)==true){
            for(Money o: prices){
                if (makeUSD(o).getAmount().subtract(minPrice).compareTo(new BigDecimal(0))<0){
                    minCurrency = o.getCurrency();
                    minAmount = o.getAmount();
                }
            }
        }

        return new Money(minAmount,minCurrency);
    }
}

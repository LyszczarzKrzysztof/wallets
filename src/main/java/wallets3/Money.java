package wallets3;

import wallets3.exception.JestesBiednyException;
import wallets3.exception.NieTaWalutaException;

import java.math.BigDecimal;
import java.util.Map;

public class Money {

    private BigDecimal amount;
    private Currency currency;

    private BigDecimal USDtoPLN = new BigDecimal(1/3.70);
    private BigDecimal USDtoEUR = new BigDecimal(1/0.95);

    private Map<Currency, BigDecimal> mapOfCurrencyRates;

    public Map<Currency, BigDecimal> setMapOfCurrencyRates(){
        mapOfCurrencyRates.put(Currency.PLN, USDtoPLN);
        mapOfCurrencyRates.put(Currency.EUR, USDtoEUR);

        return mapOfCurrencyRates;
    }

    public Map<Currency, BigDecimal> getMapOfCurrencyRates() {
        return mapOfCurrencyRates;
    }

    @Override
    public String toString(){
        return "Kwota: "+amount+" "+currency;
    }


    public Money(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Money() {
        this.amount = amount;
        this.currency = currency;
    }

    public Money acceptMoney(Money money) {
        this.amount = this.amount.add(money.amount);
        return money;
    }

    public Money payMoney(Money money) throws JestesBiednyException, NieTaWalutaException {
        if(canYouAfford(money)) {
            this.amount = this.amount.subtract(money.amount);
            return money;
        }else{
            throw new JestesBiednyException();
        }
    }

    public boolean canYouAfford(Money money) throws NieTaWalutaException {
        if(this.currency.compareTo(money.currency)==0) {
            if (this.amount.compareTo(money.amount) >= 0) {
                return true;
            } else {
                return false;
            }
        }else{
            throw new NieTaWalutaException();
        }
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }
}

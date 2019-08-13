package wallets3;

import wallets3.exception.JestesBiednyException;
import wallets3.exception.NieTaWalutaException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Wallet {
    Map<Currency,Money> mapOfMoneyInWallet;


    @Override
    public String toString(){
        return ""+ mapOfMoneyInWallet;
    }

    public Wallet() {
        this.mapOfMoneyInWallet=new HashMap<>();
    }

    public void payIn(Money money){
        if(mapOfMoneyInWallet.containsKey(money.getCurrency())) {
            mapOfMoneyInWallet.get(money.getCurrency()).acceptMoney(money);
        }else{
            mapOfMoneyInWallet.put(money.getCurrency(),money);
        }
    }

    public void payOut(Money money) throws JestesBiednyException, NieTaWalutaException {
        mapOfMoneyInWallet.getOrDefault(money.getCurrency(),
                new Money(new BigDecimal(0),money.getCurrency())).payMoney(money);
    }

    public String balance(){
        return "Na koncie posiadasz: "+ mapOfMoneyInWallet;
    }


}

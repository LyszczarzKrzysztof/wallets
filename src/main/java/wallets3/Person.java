package wallets3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wallets3.exception.JestesBiednyException;
import wallets3.exception.NieTaWalutaException;

import java.util.ArrayList;
import java.util.List;

public class Person {
    Logger logger = LoggerFactory.getLogger(Person.class);

    private Wallet wallet;

    List<String> listOfStuff = new ArrayList<>();
    List<Offer> listForSale = new ArrayList<>();
    List<Offer> listToBuy = new ArrayList<>();

    public Person() {
    }

    public Person(Wallet wallet) {
        this.wallet = wallet;
    }

    public Person(Wallet wallet, List<String> listOfStuff, List<Offer> listForSale, List<Offer> listToBuy) {
        this.wallet = wallet;
        this.listOfStuff = listOfStuff;
        this.listForSale = listForSale;
        this.listToBuy = listToBuy;
    }

    @Override
    public String toString() {
        return "" + wallet + " " + listOfStuff + " " + listForSale + " " + listToBuy;
    }

    public boolean doYouHaveWallet(Wallet wallet) {
        if (wallet != null) {
            return true;
        } else {
            return false;
        }
    }

    public void payToPerson(Person person, Money money) {
        try {
            this.wallet.payOut(money);
            person.acceptMoneyFromAnyone(money);
        } catch (JestesBiednyException e) {
            logger.warn("Nie stać Cię!");
        } catch (NieTaWalutaException e) {
            logger.warn("Nie ta waluta");
        }
    }

    public void acceptMoneyFromAnyone(Money money) {

        this.wallet.payIn(money);

//        if(doYouHaveWallet(wallet)) {
//            wallet.payIn(money);
//        }else{
//            new Person(wallet);
//            wallet.payIn(money);
//    }
    }


    public Offer howMuchDoYouWantForThis(String stuff) {
        if (listOfStuff.contains(stuff)) {
            for (Offer o : listForSale) {
                if (stuff.equals(o.getStuff())) {
                    return o;
                }
            }
        } else {
            logger.warn("There is no such stuff");
        }
        return null;
    }


    public void giveStuffToPerson(Person buyer, Offer offer) {
        this.listForSale.remove(this.listForSale.indexOf(offer));
        buyer.getStuff(offer.getStuff());
    }

    public void getStuff(String stuff) {
        this.listOfStuff.add(stuff);
    }

    public void buy(Person seller, Offer offer, Money money) {
        if (this.listToBuy.contains(offer)) {
            this.listToBuy.remove(this.listToBuy.indexOf(offer));
            payToPerson(seller,money);
            seller.sell(this,offer,money);
        } else {
            logger.warn("You dont need this");
        }
    }

    public void sell(Person buyer, Offer offer, Money money) {
        if (this.listOfStuff.contains(offer.getStuff())) {
            this.listOfStuff.remove(this.listOfStuff.indexOf(offer.getStuff()));
            giveStuffToPerson(buyer,listForSale.get(listForSale.indexOf(offer)));
        } else {
            logger.warn("You dont have this kind of stuff to sell!");
        }
    }
}

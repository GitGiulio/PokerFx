package cp2.logic;

import cp2.data.Card;
import cp2.data.Deck;

import java.util.ArrayList;

public class Game {
    private Deck deck;
    private int bet;
    private ArrayList<Card> hand;
    private ArrayList<Card> dealer;
    private int winner;



    public Game(int bet){
        this.bet=bet;
        deck = new Deck();
        deck.shuffle();
        setHand(new ArrayList<>());
        setDealer(new ArrayList<>());
        getHand().add(deck.getDeck().getFirst());
        deck.getDeck().removeFirst();
        getDealer().add(deck.getDeck().getFirst());
        deck.getDeck().removeFirst();
    }

    public boolean hit(){
        getHand().add(deck.getDeck().getFirst());
        deck.getDeck().removeFirst();
        if(count(getHand())>21) return false;
        else return true;
    }
    public void doubleDown(){
        bet*=2;
        getHand().add(deck.getDeck().getFirst());
        deck.getDeck().removeFirst();
        setWinner(this.stand());
    }
    public int stand(){
        int handScore = count(getHand());
        int dealerScore = count(getDealer());
        if(handScore > 21) return -1;
        else while (dealerScore < 17){
            if (dealerScore > handScore && dealerScore <= 21) return -1;
            else if (dealerScore > 21) return 1;
            else{
                getDealer().add(deck.getDeck().getFirst());
                deck.getDeck().removeFirst();
            }
            dealerScore = count(getDealer());
        }
        if (dealerScore > handScore && dealerScore <= 21) return -1;
        else if(dealerScore == handScore) return 0;
        else return 1;
    }
    public int count(ArrayList<Card> arrayList){
        int counter = 0;
        int uni = 0;
        for (Card c:arrayList){
            if (c.getNumber()!=1) counter += c.getValue();
            else uni++;
        }
        if(counter <= 7 && uni == 4) counter += 14;
        else if (counter <= 8 && uni == 3) counter += 13;
        else if (counter <= 9 && uni == 2) counter = 12;
        else if(counter <= 10 && uni == 1) counter += 11;
        else counter += uni;

        return counter;
    }

    public int getBet() {
        return bet;
    }
    public void setBet(int bet) {
        this.bet = bet;
    }
    public ArrayList<Card> getHand() {
        return hand;
    }
    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }
    public ArrayList<Card> getDealer() {
        return dealer;
    }
    public void setDealer(ArrayList<Card> dealer) {
        this.dealer = dealer;
    }
    public int isWinner() {
        return winner;
    }
    public void setWinner(int winner) {
        this.winner = winner;
    }
}

package cp2.data;

import java.util.*;

public class Deck {
    public enum BackColor{
        RED,
        BLUE,
    }
    private LinkedList<Card> deck;
    private BackColor backColor;

    public Deck(){
        setDeck(new LinkedList<>());
        for(Card.Seme s: Card.Seme.values()){
            for (int i=1;i<14;i++){
                Card card = new Card(i,s);
                getDeck().add(card);
            }
        }
    }
    public void shuffle(){
        Collections.shuffle(getDeck(),new Random());
    }
    public LinkedList<Card> getDeck() {
        return deck;
    }
    public void setDeck(LinkedList<Card> deck) {
        this.deck = deck;
    }
    public BackColor getBackColor() {
        return backColor;
    }
    public void setBackColor(BackColor backColor) {
        this.backColor = backColor;
    }

}

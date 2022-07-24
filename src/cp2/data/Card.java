package cp2.data;

public class Card implements Comparable{
    public enum Seme{
        QUORI,
        QUADRI,
        FIORI,
        PICCHE,
    }
    private int number;
    private Seme seme;
    private int value;

    @Override
    public int compareTo(Object o) {
        if (o.getClass()!=Card.class) return -777;
        Card otherCard = (Card) o;
        if (this.getSeme() == otherCard.getSeme())return this.getNumber() - otherCard.getNumber();
        else{
            switch (this.getSeme()){
                case QUORI :return 1;
                case QUADRI :{
                    if (otherCard.getSeme() ==Seme.QUORI) return -1;
                    else return 1;
                }
                case FIORI :{
                    if(otherCard.getSeme() ==Seme.PICCHE) return  1;
                    else return -1;
                }
                case PICCHE :return -1;
            }
        }
        return -777;
    }

    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public Seme getSeme() {
        return seme;
    }
    public void setSeme(Seme seme) {
        this.seme = seme;
    }
    public void setValue() {
        if(this.number>=10) this.value=10;
        else this.value=this.number;
    }
    public int getValue() {
        return value;
    }


    public Card(int number,Seme seme){
        this.setNumber(number);
        this.setSeme(seme);
        this.setValue();
    }

    @Override
    public String toString() {
        return "Card{" +
                "number=" + number +
                ", seme=" + seme +
                '}';
    }
}

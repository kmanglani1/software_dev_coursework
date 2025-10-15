package cardgame;
public class Card {

    private final int cardValue;
    private final int cardId;
    public static int idCounter = 0;

    public Card(int value) {
        this.cardId = ++idCounter;
        this.cardValue = value;
    }

    public synchronized int getCardValue() {
        return cardValue;
    }
    
}
package cardgame;
import java.util.ArrayList;
import java.util.List;

public class CardDeck {

    private ArrayList<Card> deck;
    private int deckId;
    private int topCard;
    private int bottomCard;
    private int maxSize;

    private static int idCounter = 0;

    public CardDeck() {
        this.deck = new ArrayList<>();
        this.deckId = ++idCounter;
        this.bottomCard = -1;

        if (deck.isEmpty()) {
            this.topCard = -1;
            this.maxSize = 0;
        } else {
            this.topCard = 0;
            this.maxSize = deck.size();
        }
    }

    public int getDeckId() {
        return deckId;
    }

    public int getTopCard() {
        return topCard;
    }

    public int getBottomCard() {
        return bottomCard;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public ArrayList<Card> getDeck() {
        return new ArrayList<Card>(deck);
    }

    public static void resetIdCounter() {
        idCounter = 0;
    }

    public void setMaxSize(int newSize) {
        this.maxSize = newSize;
    }

    public void addCard(Card card) {
        deck.add(card);
    }

}
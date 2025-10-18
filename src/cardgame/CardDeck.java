package cardgame;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CardDeck {

    private List<Card> deck;
    private int deckId;
    private int topCard;
    private int bottomCard;
    private int maxSize;

    private static int idCounter = 0;

    public CardDeck() {
        this.deck = Collections.synchronizedList(new ArrayList<>());
        this.deckId = ++idCounter;


        if (deck.isEmpty()) {
            this.topCard = -1;
            this.maxSize = 0;
            this.bottomCard = -1;
        } else {
            this.topCard = 0;
            this.maxSize = deck.size();
            this.bottomCard = deck.size() - 1;
        }
    }

    public synchronized int getDeckId() {
        return deckId;
    }

    public synchronized int getTopCard() {
        return topCard;
    }

    public synchronized int getBottomCard() {
        return bottomCard;
    }

    public synchronized int getMaxSize() {
        return maxSize;
    }

    public synchronized ArrayList<Card> getDeckContents() {
        return new ArrayList<Card>(deck);
    }

    public synchronized static void resetIdCounter() {
        idCounter = 0;
    }

    public synchronized void setMaxSize(int newSize) {
        this.maxSize = newSize;
    }

    public synchronized void addCard(Card card) {
        deck.add(card);
    } // enqueue may be needed instead

    public synchronized void removeCard() {
        deck.remove(0);
    } // dequeue may be needed instead

}
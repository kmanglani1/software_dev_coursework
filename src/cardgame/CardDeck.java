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
    
    public synchronized Card getCardAtPosition(int Pos) {
        return deck.get(Pos);
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

    public void setTopCard(int cardValue) {
        this.topCard = cardValue;
    }

    public void setBottomCard(int cardValue) {
        this.bottomCard = cardValue;
    }

    public synchronized void addCard(Card card) {
        if (topCard == -1) {
            topCard = 0;
            deck.add(card);
        } else {
            deck.add(card);
        }
    } // enqueue may be needed instead

    public synchronized void removeCard() {
        if (deck.isEmpty()) {
            System.out.println("Error: removing card from empty deck");
            return;
        }
        deck.remove(0);
        // int start = getTopCard();
        // int end = getBottomCard();
        // if (start == -1) {
        //     break;
        // } else {
        //     Card oldTopCard = getDeckContents().get(start);
        //     int oldTop = oldTopCard.getCardValue();
        //     start = start + 1;
        //     setTopCard(start);
        //     if (start>end) {
        //         setTopCard(-1);
        //         setBottomCard(-1);
        //     }
        // }
    } // dequeue may be needed instead

    public synchronized String stringCardsHeld() {
        String stringOfCardsHeld = "";
        for (Card card : deck) {
            stringOfCardsHeld = stringOfCardsHeld + " " + card.getCardValue();
        }
        return stringOfCardsHeld;
    }

}
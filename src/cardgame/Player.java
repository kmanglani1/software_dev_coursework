package cardgame;
import java.util.ArrayList;

public class Player {

    private final int playerId;
    private List<Card> cardHeld;

    private static int idCounter = 0;

    public Player() {
        this.playerId = ++idCounter;
        this.cardHeld = Collections.synchronizedList(new ArrayList<>());
    }

    public synchronized int getPlayerId() {
        return playerId;
    }

    public synchronized static void resetIdCounter() {
        idCounter = 0;
    }

    public synchronized void addCardHeld(Card card) {
        cardHeld.add(card);
    }

    public synchronized void removeCardHeld(Card card) {
        cardHeld.remove(card.getCardValue());
    }
}
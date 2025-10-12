package cardgame;
import java.util.ArrayList;

public class Player {

    private final int playerId;
    private ArrayList<Card> cardsHeld;

    private static int idCounter = 0;

    public Player() {
        this.playerId = ++idCounter;
        this.cardsHeld = new ArrayList<>();
    }

    public int getPlayerId() {
        return playerId;
    }

    public static void resetIdCounter() {
        idCounter = 0;
    }

    public static void addCardHeld(Card card) {
        cardHeld.add(card);
    }

    public static void removeCardHeld(Card card) {
        cardHeld.remove(card.getCardValue());
    }
}
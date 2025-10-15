package cardgame;
import java.util.ArrayList;

public class Player {

    private final int playerId;
    private ArrayList<Card> cardHeld;

    private static int idCounter = 0;

    public Player() {
        this.playerId = ++idCounter;
        this.cardHeld = new ArrayList<>();
    }

    public int getPlayerId() {
        return playerId;
    }

    public static void resetIdCounter() {
        idCounter = 0;
    }

    public void addCardHeld(Card card) {
        cardHeld.add(card);
    }

    public void removeCardHeld(Card card) {
        cardHeld.remove(card.getCardValue());
    }
}
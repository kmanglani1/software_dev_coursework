package cardgame;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Player {

    private final int playerId;
    private List<Card> cardsHeld;

    private static int idCounter = 0;

    public Player() {
        this.playerId = ++idCounter;
        this.cardsHeld = Collections.synchronizedList(new ArrayList<>());
    }

    public synchronized int getPlayerId() {
        return playerId;
    }

    public synchronized static void resetIdCounter() {
        idCounter = 0;
    }

    public synchronized void addCardHeld(Card card) {
        cardsHeld.add(card);
    }

    public synchronized void removeCardHeld(Card card) {
        cardsHeld.remove(card.getCardValue());
    }

    public synchronized Boolean checkHand() {
        Boolean hasWon = true;
        Card card1 = cardsHeld.indexOf(0);
        for (Card card : cardsHeld) {
            if (card.getCardValue() != card1.getCardValue()) {
                hasWon = false;
                return hasWon;
            }
        }
        return hasWon;
    }

    public synchronized Card cardToDiscard() {
        for (Card card : cardsHeld) {
            if (card.getCardValue() != playerId) {
                return card;
            }
        }
        return null;
    }

    public synchronized String stringCardsHeld() {
        String stringOfCardsHeld = "";
        for (Card card : cardsHeld) {
            stringOfCardsHeld = stringOfCardsHeld + " " + card.getCardValue();
        }
        return stringOfCardsHeld;
    }

}
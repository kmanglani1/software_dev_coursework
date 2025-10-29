package cardgame;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

// Represents a player in the card game
public class Player {

    // Attributes
    private final int playerId; // Unique identifier for each player
    private List<Card> cardsHeld; // List of current cards held by the player
    private static int idCounter = 0; // Static counter to generate unique player IDs

    // Constructs a new player instance
    public Player() {
        this.playerId = ++idCounter; // Increment and assign a unique player ID
        this.cardsHeld = Collections.synchronizedList(new ArrayList<>()); // Creates a new thread-safe list for cards held by the player
    }

    // Thread-safe access to return the player's ID
    public synchronized int getPlayerId() {
        return playerId;
    }

    // Thread-safe method to reset the static player ID counter
    public synchronized static void resetIdCounter() {
        idCounter = 0;
    }

    // Thread-safe method to add a new card to the player's hand
    public synchronized void addCardHeld(Card card) {
        cardsHeld.add(card);
    }

    // Thread-safe method to remove a card from the player's hand
    public synchronized void removeCardHeld(Card card) {
        cardsHeld.remove(card);
    }

    // Thread-safe method to check if the player has a winning hand
    public synchronized Boolean checkHand() {
        Boolean hasWon = true; // Assume player has won and prove otherwise
        Card card1 = cardsHeld.get(0); // Get first card in hand
        for (Card card : cardsHeld) {
            if (card.getCardValue() != card1.getCardValue()) { // If any card has a different value to the first card held, it is not a winning hand
                hasWon = false;
                return hasWon;
            }
        }
        return hasWon;
    }

    // Thread-safe method to find a card that doesn't match the player's ID
    public synchronized Card cardToDiscard() {
        for (Card card : cardsHeld) {
            if (card.getCardValue() != playerId) { // Find the first card not matching player ID
                return card;
            }
        }
        return null;
    }

    // Thread-safe method to return a string representation of all cards held by the player
    public synchronized String stringCardsHeld() {
        String stringOfCardsHeld = ""; // String to hold card values
        for (Card card : cardsHeld) {
            stringOfCardsHeld = stringOfCardsHeld + " " + card.getCardValue(); // Append string with each card value
        }
        return stringOfCardsHeld; // Return formatted string
    }

}
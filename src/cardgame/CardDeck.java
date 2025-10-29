package cardgame;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

// Represents a deck of cards in the card game
public class CardDeck {

    // Attributes
    private List<Card> deck; // List of current cards in the deck
    private int deckId; // Unique identifier for each deck
    private int topCard; // Index of the top card in the deck
    private int bottomCard; // Index of the bottom card in the deck
    private int maxSize; // Maximum number of cards in the deck
    private static int idCounter = 0; // Static counter to generate unique player IDs

    // Constructs a new deck of cards instance
    public CardDeck() {
        this.deck = Collections.synchronizedList(new ArrayList<>()); // Creates a new thread-safe list for deck
        this.deckId = ++idCounter; // Increment and assign a unique deck ID

        // Establishes set-up for queue/FIFO structure of the card deck
        if (deck.isEmpty()) {
            this.topCard = -1; // Indicates there is no top card
            this.maxSize = 0; // Empty deck's size is 0
            this.bottomCard = -1; // Indicates there is no bottom card
        } else {
            this.topCard = 0; // Indicates top card is at position 0
            this.maxSize = deck.size(); // Sets maximum size to size of the deck
            this.bottomCard = deck.size() - 1; // Indicates bottom card is at the last position
        }
    }

    // Thread-safe access to return the deck's ID
    public synchronized int getDeckId() {
        return deckId;
    }

    // Thread-safe access to return the top card index
    public synchronized int getTopCard() {
        return topCard;
    }

    // Thread-safe access to return the bottom card index
    public synchronized int getBottomCard() {
        return bottomCard;
    }
    
    // Thread-safe method to return the card at a specific position
    public synchronized Card getCardAtPosition(int Pos) {
        return deck.get(Pos);
    }

    // Thread-safe access to return the maximum size of the deck
    public synchronized int getMaxSize() {
        return maxSize;
    }

    // Thread-safe method to return a copy of the deck's contents
    public synchronized ArrayList<Card> getDeckContents() {
        return new ArrayList<Card>(deck);
    }

    // Thread-safe method to reset the static deck ID counter
    public synchronized static void resetIdCounter() {
        idCounter = 0;
    }

    // Thread-safe method to set a new maximum deck size
    public synchronized void setMaxSize(int newSize) {
        this.maxSize = newSize;
    }

    public void setTopCard(int cardValue) {
        this.topCard = cardValue;
    }

    public void setBottomCard(int cardValue) {
        this.bottomCard = cardValue;
    }

    // Thread-safe method to add a card to the deck
    public synchronized void addCard(Card card) {
        if (topCard == -1) { // If deck is empty
            topCard = 0; // Set top card to 0
            deck.add(card); // Add card to list
        } else {
            deck.add(card); // Add card to list
        }
        this.maxSize = this.maxSize + 1; // Increase max size value by 1
    }

    // Thread-safe method to remove the top card from the deck
    public synchronized void removeCard() {
        if (deck.isEmpty()) { // Cannot remove card from an empty dck
            return;
        }
        deck.remove(0); // Remove card at index 0
        this.maxSize = this.maxSize - 1; // Decrease max size value by 1
    }

    // Thread-safe method to return a string representation of all cards in the deck
    public synchronized String stringCardsHeld() {
        String stringOfCardsHeld = ""; // String to hold card values
        for (Card card : deck) {
            stringOfCardsHeld = stringOfCardsHeld + " " + card.getCardValue(); // Append string with each card value
        }
        return stringOfCardsHeld; // Return formatted string
    }

}
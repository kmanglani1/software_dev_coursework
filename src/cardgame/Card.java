package cardgame;

// Represents a single playing card in the card game
public class Card {

    // Attributes
    private final int cardValue; // Card's fixed integer value that connot be changed

    // Constructs a new card instance with the given value
    public Card(int value) {
        this.cardValue = value; // Set card's value
    }

    // Thread-safe access to return the value of the card
    public synchronized int getCardValue() {
        return cardValue;
    }

}
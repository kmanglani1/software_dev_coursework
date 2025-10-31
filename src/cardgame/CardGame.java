package cardgame;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


// Represents main card game logic
public class CardGame extends Thread {

    // Attributes
    private List<Player> allPlayers; // List of all players in the game
    private List<CardDeck> allCardDecks; // List of all card decks used in the game
    private List<Thread> allThreads; // List of all threads for each player in the game
    private volatile boolean won = false; // Flag to indicate if a player has won
    private final Object lock; // Lock for synchronizing the threads

    // Constructs a new card game instance
    public CardGame() {
        this.allPlayers = Collections.synchronizedList(new ArrayList<Player>()); // Creates a new thread-safe list for all players in the game
        this.allCardDecks = Collections.synchronizedList(new ArrayList<CardDeck>()); // Creates a new thread-safe list for all card decks used in the game
        this.allThreads = Collections.synchronizedList(new ArrayList<Thread>()); // Creates a new thread-safe list for all threads in the game
        this.lock = new Object(); // Creates lock
    }


    // Method to check the validity of the input file
    public static boolean validInputFile(String fileLocation, int noOfPeople, CardDeck initialDeck) {
        try {
            File cardFile = new File(fileLocation);
            
            try (Scanner fileReader = new Scanner(cardFile)) {
                int noOfLines = 8*noOfPeople; // Total number of expected cards

                for (int i = 1; i <= noOfLines; i++) {
                    int cardValue = fileReader.nextInt(); // Read card value from file
                    Card card = new Card(cardValue); // Create new card instance
                    initialDeck.addCard(card); // Add card to an initial pre-dealt deck
                }

            } catch (Exception e) {
                return false; // Return false if file not valid
            }
        } catch (Exception e) {
            // System.out.println("Error occurred: Invalid File");
            return false; // Return false if file not valid
        }
        return true; // Return true if file is valid
    }

    public static void main(String[] args) {
        CardGame thisCardGame = new CardGame(); // Creates card game instance
        CardDeck initialDeck = new CardDeck(); // Creates first deck of card to be dealt to players and their decks

        Scanner input = new Scanner(System.in);

        // Request number of players from user
        System.out.println("Please enter the number of players:");
        int noOfPeople = input.nextInt();
        input.nextLine();

        // Request input file from user
        System.out.println("Please enter location of pack to load:");
        String fileLocation = input.nextLine();
        boolean validFile = validInputFile(fileLocation, noOfPeople, initialDeck);

        // Loop until valid input file is entered
        while (!validFile){
            // System.out.println("File not valid. Please enter location of pack to load:");
            System.out.println("Please enter location of pack to load:");
            fileLocation = input.nextLine();
            validFile = validInputFile(fileLocation, noOfPeople, initialDeck);
        }

        // Create a new player instance for each person
        for (int i = 1; i <= noOfPeople; i++) {
            Player player = new Player();
            thisCardGame.allPlayers.add(player); // Add to list of all players
        }

        // Deal 4 cards to each player from the initial deck in a round-robin fashion
        for (int i = 0; i < 4; i++) {
            for (Player player : thisCardGame.allPlayers) {
                Card tempCard = new Card(initialDeck.getCardAtPosition(initialDeck.getTopCard()).getCardValue());
                player.addCardHeld(tempCard);
                initialDeck.removeCard();
            }
        }

        // Create a new deck instance for each player
        for (int i = 1; i <= noOfPeople; i++) {
            CardDeck cardDeck = new CardDeck();
            thisCardGame.allCardDecks.add(cardDeck); // Add to list of all decks
        }

        // Deal 4 cards to each deck from the initial deck in a round-robin fashion
        for (int i = 0; i < 4; i++) {
            for (CardDeck cardDeck : thisCardGame.allCardDecks) {
                Card newTempCard = new Card(initialDeck.getCardAtPosition(initialDeck.getTopCard()).getCardValue());
                cardDeck.addCard(newTempCard);
                initialDeck.removeCard();
            }
        }

        // Create and start a new thread - one for each player
        for (Player player : thisCardGame.allPlayers) {
            String idNum = String.valueOf(player.getPlayerId());
            Thread t = new Thread(thisCardGame, idNum);
            thisCardGame.allThreads.add(t); // Add to list of all threads
            t.start();
        }

        // Wait for all threads to finish
        try {
            for (Thread t : thisCardGame.allThreads) {
                t.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    } // end of main

    // Adds a player to the game (used for testing)
    public void addPlayer(Player player) { 
        allPlayers.add(player);
    }

    // Adds a deck to the game (used for testing)
    public void addDeck(CardDeck cardDeck) { 
        allCardDecks.add(cardDeck);
    }

    // Returns a player instance from search by ID
    public Player getPlayer(int playerId) {
        for (Player player : allPlayers) {
            if (playerId == player.getPlayerId()) {
                return player;
            }
        }
        return null;
    }

    // Returns a deck instance from search by ID
    public CardDeck getCardDeck(int deckId) {
        for (CardDeck cardDeck : allCardDecks) {
            if (deckId == cardDeck.getDeckId()) {
                return cardDeck;
            }
        }
        return null;
    }

    // Check's if the player has a winning hand
    public Boolean checkPlayerHand(int playerId) {
        Player player = getPlayer(playerId);
        return player.checkHand(); // Returns true if won
    }
    
    // Method carry's out one atomic action by a player and writes the respective file lines
    public void takeTurn(int playerId) {
        // Get player instance
        Player player = getPlayer(playerId);

        // Get's "Deck n"'s contents to pick up 1 card
        int pickUpDeckId = playerId+1;
        ArrayList<Card> pickUpDeck = getCardDeck(pickUpDeckId).getDeckContents();

        // Thread-safe block to wait for a card
        synchronized (lock) {
            while (pickUpDeck.size() == 0) { // If deck is empty
                try {
                    lock.wait(); // Wait until notified
                    pickUpDeck = getCardDeck(pickUpDeckId).getDeckContents();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    
        // Draw top card
        Card newCard = pickUpDeck.get(getCardDeck(pickUpDeckId).getTopCard());
        player.addCardHeld(newCard);
        getCardDeck(pickUpDeckId).removeCard();

        // Write drawn action to file
        String playerFileName = "player" + playerId + "_output.txt";
        try {
            FileWriter writer = new FileWriter(playerFileName, true);
            String line = "player " + playerId + " draws a " + newCard.getCardValue() + " from deck " + pickUpDeckId + "\n";
            writer.write(line);
        } catch (IOException e) {
            System.out.println("error failure");
        }

        // Determine card to discard
        Card oldCard = player.cardToDiscard();

        // Get's "Deck n+1" to discard 1 card
        int discardDeckId = playerId + 2;
        if (player.getPlayerId() == allPlayers.size()) {
            discardDeckId = 2;
        } else {
            discardDeckId = playerId + 2;
        }
        CardDeck discardDeck = getCardDeck(discardDeckId);

        // Discard card from player's hand
        player.removeCardHeld(oldCard);

        // Thread-safe block to add discarded card to deck
        synchronized (lock) {
            if (discardDeck.getDeckContents().size() == 0) { // If deck was empty
                discardDeck.addCard(oldCard);
                lock.notifyAll(); // Notify player n+1 that there is a card in the deck
            } else {
                discardDeck.addCard(oldCard);
            }
        }

        // Write drawn action to file
        try {
            FileWriter writer = new FileWriter(playerFileName, true);

            String line1 = "player " + playerId + " discards a " + oldCard.getCardValue() + " from deck " + discardDeckId + "\n";
            writer.write(line1);
            String line2 = "player " + playerId + " current hand is" + player.stringCardsHeld() + "\n";
            writer.write(line2);
            writer.close();
        } catch (IOException e) {
            System.out.println("error failure");
        }
    }

    // Thread-safe method to end a thread that has won
    public void thisThreadWon(int threadId) {
        // Print winning message to terminal
        System.out.println("player " + threadId + " wins");

        // Write closing 3 line's to player output file
        String playerFileName = "player" + threadId + "_output.txt";
        try {
            FileWriter writerEnd = new FileWriter(playerFileName, true);

            String line1 = "player " + threadId + " wins\n";
            writerEnd.write(line1);
            String line2 = "player " + threadId + " exits\n";
            writerEnd.write(line2);
            String line3 = "player " + threadId + " final hand:" + getPlayer(threadId).stringCardsHeld() + "\n";
            writerEnd.write(line3);
            writerEnd.close();
        } catch (IOException e) {
            System.out.println("error failure");
        }

        // Write deck n's final contents to deck output file
        String deckFileName = "deck" + threadId + "_output.txt";
        try {
            FileWriter writer2 = new FileWriter(deckFileName);

            String line1 = "deck" + threadId + " contents:" + getCardDeck(threadId+1).stringCardsHeld() + "\n";
            writer2.write(line1);
            writer2.close();
        } catch (IOException e) {
            System.out.println("error failure");
        }
    }

    // Thread-safe method to end a thread that has not won
    public void otherThreadWon(int threadId, int winnerId) {
        // Write closing 3 line's to player output file
        String playerFileName = "player" + threadId + "_output.txt";
        try {
            FileWriter writerEnd = new FileWriter(playerFileName, true);
            String line1 = "player " + winnerId + " has informed player " + threadId + " that player "+ winnerId + " has won\n";
            writerEnd.write(line1);
            String line2 = "player " + threadId + " exits\n";
            writerEnd.write(line2);
            String line3 = "player " + threadId + " final hand:" + getPlayer(threadId).stringCardsHeld() + "\n";
            writerEnd.write(line3);
            writerEnd.close();
        } catch (IOException e) {
            System.out.println("error failure");
        }

        // Write deck n's final contents to deck output file
        String deckFileName = "deck" + threadId + "_output.txt";
        try {
            FileWriter writer = new FileWriter(deckFileName);

            String line1 = "deck" + threadId + " contents:" + getCardDeck(threadId+1).stringCardsHeld() + "\n";
            writer.write(line1);
            writer.close();
        } catch (IOException e) {
            System.out.println("error failure");
        }
    }

    public void run() {
        int turnCount = 0;
        int threadId = Integer.parseInt(Thread.currentThread().getName());

        while (!won) { // If no one has won

            if (turnCount == 0) { // If first turn
                // Write player's initial hand to player file
                String playerFileName = "player" + threadId + "_output.txt";
                try {
                    FileWriter writer = new FileWriter(playerFileName);
                    String line1 = "player " + threadId + " initial hand" + getPlayer(threadId).stringCardsHeld() + "\n";
                    writer.write(line1);
                    writer.close();
                } catch (IOException e) {
                    System.out.println("error failure");
                }

                // Check if player has been dealt a winning hand by chance
                Boolean result = checkPlayerHand(threadId);
                if (result == true) {
                    synchronized (lock) {
                        won = true;
                        lock.notifyAll();
                    }
                    break;
                } 
            }

            // Player takes one turn - one uninterrupted atomic action
            takeTurn(threadId);

            // If player has won notify other players
            Boolean result = checkPlayerHand(threadId);
            if (result == true) {
                synchronized (lock) {
                    won = true;
                    lock.notifyAll();
                }
            }

            // Increment turn count
            turnCount = turnCount + 1;
        }

        // After someone has declared a win

        // Check if this player won
        Boolean thisWon = checkPlayerHand(threadId);
        if (thisWon == true) { // If won
            thisThreadWon(threadId); // Handle win
        } else { // If not won
            // Find winning player
            int winnerId = -1;
            for (Player player : allPlayers) {
                if (player.checkHand()){
                    winnerId = player.getPlayerId();
                }
            }
            otherThreadWon(threadId, winnerId); // Handle loss
        }      
    }

}

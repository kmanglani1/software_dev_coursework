package cardgame;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;



public class CardGame extends Thread {

    private List<Player> allPlayers;
    private List<CardDeck> allCardDecks;
    private List<Thread> allThreads;
    private volatile boolean won = false;
    private final Object lock;
    // private int arrived = 0;


    public CardGame() {
        this.allPlayers = Collections.synchronizedList(new ArrayList<Player>());
        this.allCardDecks = Collections.synchronizedList(new ArrayList<CardDeck>());
        this.allThreads = Collections.synchronizedList(new ArrayList<Thread>());
        this.lock = new Object();
    }



    public static boolean validInputFile(String fileLocation, int noOfPeople, CardDeck initialDeck) {
        try {
            File cardFile = new File(fileLocation);
            
            try (Scanner fileReader = new Scanner(cardFile)) {
                int noOfLines = 8*noOfPeople;

                for (int i = 1; i <= noOfLines; i++) {
                    int cardValue = fileReader.nextInt();
                    Card card = new Card(cardValue);
                    initialDeck.addCard(card);
                    // System.out.println(initialDeck.stringCardsHeld());
                }

            } catch (Exception e) {
                System.out.println("Error occurred");
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error occurred");
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        CardGame thisCardGame = new CardGame();
        CardDeck initialDeck = new CardDeck();
        // Object lock = new Object();

        Scanner input = new Scanner(System.in);

        System.out.println("Enter the number of people:");
        int noOfPeople = input.nextInt();

        input.nextLine();

        System.out.println("Please enter location of pack to load:");
        String fileLocation = input.nextLine();
        boolean validFile = validInputFile(fileLocation, noOfPeople, initialDeck);

        while (!validFile){
            System.out.println("File not valid. Please enter location of pack to load:");
            fileLocation = input.nextLine();
            validFile = validInputFile(fileLocation, noOfPeople, initialDeck);
        }

        System.out.println("Gets here 1");
        System.out.println(initialDeck.stringCardsHeld());
        System.out.println("Gets here 2");
        // List<Player> allPlayers = Collections.synchronizedList();

        for (int i = 1; i <= noOfPeople; i++) {
            Player player = new Player();
            thisCardGame.allPlayers.add(player);
        }

        System.out.println(thisCardGame.allPlayers);

        for (int i = 0; i < 4; i++) {
            // System.out.println("card hand number: "+i);
            for (Player player : thisCardGame.allPlayers) {
                // System.out.println("Player being dealt: "+player.getPlayerId());
                Card tempCard = new Card(initialDeck.getCardAtPosition(initialDeck.getTopCard()).getCardValue());
                player.addCardHeld(tempCard);
                initialDeck.removeCard();
                System.out.println(player.stringCardsHeld());
                System.out.println(initialDeck.stringCardsHeld());
            }
        }

        // List<CardDeck> allCardDecks = Collections.synchronizedList(new ArrayList<CardDeck>());

        for (int i = 1; i <= noOfPeople; i++) {
            CardDeck cardDeck = new CardDeck();
            thisCardGame.allCardDecks.add(cardDeck);
        }

        // System.out.println(thisCardGame.allCardDecks);

        for (int i = 0; i < 4; i++) {
            // System.out.println("card deck number: "+i);
            for (CardDeck cardDeck : thisCardGame.allCardDecks) {
                // System.out.println("Deck being dealt: "+cardDeck.getDeckId());
                Card newTempCard = new Card(initialDeck.getCardAtPosition(initialDeck.getTopCard()).getCardValue());
                cardDeck.addCard(newTempCard);
                initialDeck.removeCard();
                // System.out.println(cardDeck.getDeckId() + cardDeck.stringCardsHeld());
                // System.out.println(initialDeck.stringCardsHeld());
            }
        }

        for (Player player : thisCardGame.allPlayers) {
            String idNum = String.valueOf(player.getPlayerId());
            Thread t = new Thread(thisCardGame, idNum);
            thisCardGame.allThreads.add(t);
            t.start();
        }

        // System.out.println(thisCardGame.allThreads);

        try {
            for (Thread t : thisCardGame.allThreads) {
                t.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        





    } 
    // end of main

    public Player getPlayer(int playerId) {
        for (Player player : allPlayers) {
            if (playerId == player.getPlayerId()) {
                return player;
            }
        }
        return null;
    }

    public CardDeck getCardDeck(int deckId) {
        for (CardDeck cardDeck : allCardDecks) {
            if (deckId == cardDeck.getDeckId()) {
                return cardDeck;
            }
        }
        return null;
    }



    public Boolean checkPlayerHand(int playerId) {
        Player player = getPlayer(playerId);
        return player.checkHand();
        // returns true if won
    }
    

    public void takeTurn(int playerId) {
        Player player = getPlayer(playerId);
        int pickUpDeckId = playerId+1;
        ArrayList<Card> pickUpDeck = getCardDeck(pickUpDeckId).getDeckContents();

        synchronized (lock) {
            while (pickUpDeck.size() == 0) {
                try {
                    Thread.currentThread().wait();
                    pickUpDeck = getCardDeck(pickUpDeckId).getDeckContents();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    
        Card newCard = pickUpDeck.get(getCardDeck(pickUpDeckId).getTopCard());
        player.addCardHeld(newCard);
        getCardDeck(pickUpDeckId).removeCard();

        String playerFileName = "player" + playerId + "_output.txt";
        try {
            FileWriter writer = new FileWriter(playerFileName, true);
            String line = "player " + playerId + " draws a " + newCard.getCardValue() + " from deck " + pickUpDeckId + "\n";
            writer.write(line);
        } catch (IOException e) {
            System.out.println("error failure");
        }

        Card oldCard = player.cardToDiscard();
        player.removeCardHeld(oldCard);
        int discardDeckId = playerId + 2;
        if (player.getPlayerId() == allPlayers.size()) {
            discardDeckId = 2;
        } else {
            discardDeckId = playerId + 2;
        }

        CardDeck discardDeck = getCardDeck(discardDeckId);
        synchronized (lock) {
            if (discardDeck.getDeckContents().size() == 0) {
                discardDeck.addCard(oldCard);
                notifyAll();
            }
        }
        discardDeck.addCard(oldCard);
        


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

    // this thread won
    public void thisThreadWon(int threadId) {
        System.out.println("player " + threadId + " wins");
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
        // print win to terminal
        // print last lines of player files
        // print final deck contents to deck_output


    public void otherThreadWon(int threadId, int winnerId) {
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

    // public synchronized void waitForAllPlayers() throws InterruptedException {
    //     arrived++;
    //     if (arrived < allPlayers.size()) {
    //         wait();
    //     } else {
    //         arrived = 0;
    //         notifyAll();
    //     }
    // }
    
    // other thread won
        // print final deck contents to deck_output


    public void run() { // public void run
        int turnCount = 0;
        int threadId = Integer.parseInt(Thread.currentThread().getName());
        while (!won) {
            // System.out.println("in loop");
            if (turnCount == 0) {

                String playerFileName = "player" + threadId + "_output.txt";
                try {
                    FileWriter writer = new FileWriter(playerFileName);
                    String line1 = "player " + threadId + " initial hand" + getPlayer(threadId).stringCardsHeld() + "\n";
                    writer.write(line1);
                    writer.close();
                } catch (IOException e) {
                    System.out.println("error failure");
                }

                Boolean result = checkPlayerHand(threadId);

                if (result == true) {
                    synchronized (lock) {
                        won = true;
                        lock.notifyAll();
                    }
                    break;
                }
                
            }

            takeTurn(threadId);
            Boolean result = checkPlayerHand(threadId);

            if (result == true) {
                synchronized (lock) {
                    won = true;
                    lock.notifyAll();
                }
            }
                // break;

            turnCount = turnCount + 1; 
            // try {
            //     waitForAllPlayers();
            // } catch (InterruptedException e) {
            //     e.printStackTrace();
            // }
             
        }


        // System.out.println("out of loop");
        Boolean thisWon = checkPlayerHand(threadId);
        if (thisWon == true) {
            thisThreadWon(threadId);
        } else {
            int winnerId = -1;
            for (Player player : allPlayers) {
                if (player.checkHand()){
                    winnerId = player.getPlayerId();
                }
            }
            otherThreadWon(threadId, winnerId);
        }

            
            
    }


}

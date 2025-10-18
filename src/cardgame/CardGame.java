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


    public CardGame() {
        this.allPlayers = Collections.synchronizedList(new ArrayList<Player>());
        this.allCardDecks = Collections.synchronizedList(new ArrayList<CardDeck>());
    }



    public static boolean validInputFile(String fileLocation, int noOfPeople) {
        try {
            File cardFile = new File(fileLocation);
            
            try (Scanner fileReader = new Scanner(cardFile)) {
                int noOfLines = 8*noOfPeople;
                CardDeck initialDeck = new CardDeck();

                for (int i = 1; i <= noOfLines; i++) {
                    int cardValue = fileReader.nextInt();
                    Card card = new Card(cardValue);
                    initialDeck.addCard(card);
                }

            } catch (Exception e) {
                System.out.println("Error occurred");
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

        Scanner input = new Scanner(System.in);

        System.out.println("Enter the number of people:");
        int noOfPeople = input.nextInt();

        input.nextLine();

        System.out.println("Please enter location of pack to load:");
        String fileLocation = input.nextLine();
        boolean validFile = validInputFile(fileLocation, noOfPeople);

        while (!validFile){
            System.out.println("File not valid. Please enter location of pack to load:");
            fileLocation = input.nextLine();
            validFile = validInputFile(fileLocation, noOfPeople);
        }

        // List<Player> allPlayers = Collections.synchronizedList();

        for (int i = 1; i <= noOfPeople; i++) {
            Player player = new Player();
            thisCardGame.allPlayers.add(player);
        }

        for (int i = 0; i < 4; i++) {
            System.out.println("card hand number: "+i);
            for (Player player : thisCardGame.allPlayers) {
                System.out.println("Player being dealt: "+player.getPlayerId());
                Card tempCard = new Card(initialDeck.getTopCard());
                player.addCardHeld(tempCard);
                initialDeck.removeCard();
            }
        }

        // List<CardDeck> allCardDecks = Collections.synchronizedList(new ArrayList<CardDeck>());

        for (int i = 1; i <= noOfPeople; i++) {
            CardDeck cardDeck = new CardDeck();
            thisCardGame.allCardDecks.add(cardDeck);
        }

        for (int i = 0; i < 4; i++) {
            System.out.println("card deck number: "+i);
            for (CardDeck cardDeck : thisCardGame.allCardDecks) {
                System.out.println("Deck being dealt: "+cardDeck.getDeckId());
                Card newTempCard = new Card(initialDeck.getTopCard());
                cardDeck.addCard(newTempCard);
                initialDeck.removeCard(); 
            }
        }


        

    }





    

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
        int pickUpDeckId = playerId;
        ArrayList<Card> pickUpDeck = getCardDeck(pickUpDeckId).getDeckContents();
        Card newCard = pickUpDeck.get(getCardDeck(pickUpDeckId).getTopCard());
        player.addCardHeld(newCard);
        getCardDeck(pickUpDeckId).removeCard();

        String playerFileName = "player" + playerId + "_output.txt";
        try {
            FileWriter writer = new FileWriter(playerFileName);

            String line = "player " + playerId + " draws a " + newCard.getCardValue() + " from deck " + pickUpDeckId;
            writer.write(line);
        } catch (IOException e) {
            System.out.println("error failure");
        }

        Card oldCard = player.cardToDiscard();
        player.removeCardHeld(oldCard);
        int discardDeckId = playerId + 1;
        getCardDeck(discardDeckId).addCard(oldCard);


        try {
            FileWriter writer = new FileWriter(playerFileName);

            String line1 = "player " + playerId + " discards a " + oldCard.getCardValue() + " from deck " + discardDeckId;
            writer.write(line1);
            String line2 = "player " + playerId + " current hand is" + player.stringCardsHeld();
            writer.write(line2);
            writer.close();
        } catch (IOException e) {
            System.out.println("error failure");
        }
    }

    // this thread won
    public void threadWon(int threadID) {
        
    }
        // print win to terminal
        // print last lines of player files
        // print final deck contents to deck_output
    
    // other thread won
        // print final deck contents to deck_output

    public void run() { //public void run
        int turnCount = 0;

        if (turnCount == 0) {
            // check no one has won
            // if yes
                // other thread won
            // if not
                // print initial hand
        }

        // check player hand
        // if won
            // notify
            // this thread won
        
        // play turn

        // if won
            // notify
            // this thread won
    }







}

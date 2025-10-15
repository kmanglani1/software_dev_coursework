package cardgame;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;



public class CardGame {
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

        ArrayList<Player> allPlayers = new ArrayList<Player>();

        for (int i = 1; i <= noOfPeople; i++) {
            Player player = new Player();
            allPlayers.add(player);
        }

        for (int i = 0; i < 4; i++) {
            System.out.println("card hand number: "+i);
            for (Player player : allPlayers) {
                System.out.println("Player being dealt: "+player);
                Card tempCard = new Card(initialDeck.getTopCard());
                player.addCardHeld(tempCard);
                initialDeck.removeCard();
            }
        }

        // for (Player player : allPlayers) {
        //     System.out.println(player.cardsHeld());
        // }
    }
}
    

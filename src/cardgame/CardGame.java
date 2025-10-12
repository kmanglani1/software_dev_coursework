package cardgame;
import java.util.Scanner;
import java.io.File;



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
    }
}
    

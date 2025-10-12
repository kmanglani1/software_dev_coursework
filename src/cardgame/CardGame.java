package cardgame;
import java.util.Scanner;



public class GenerateInputFile {
    public static void main(String[] args) {

        public boolean validInputFile(String fileLocation, int noOfPeople) {
            try {
                File cardFile = new File(fileLocation);
                
                try (Scanner fileReader = new Scanner(cardFile)) {
                    int noOfLines = 8*noOfPeople
                    CardDeck initialDeck = new CardDeck();

                    for (int i = 1; i <= noOfLines; i++) {
                        int cardValue = fileReader.nextInt();
                        String cardName = "card"+String.valueOf(i);
                        Card cardName = new Card(cardValue);
                        initialDeck.addCard(cardName);
                    }
                } catch (Exception e) {
                    System.out.println("Error occurred");
                }
            } catch (Exception e) {
                System.out.println("Error occurred");
            }
        }

        Scanner input = new Scanner(System.in);
        System.out.println("Enter the number of people");
        int noOfPeople = input.nextInt();
        System.out.println("Please enter location of pack to load");
        String fileLocation = input.nextLine();

    }
}
    

package cardgame;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

// Generates a random input file that can be used to play the card game
public class GenerateInputFile {
    public static void main(String[] args) {
        
        // Generate a random number of players between 2 and 10
        Random random = new Random();
        int numberOfPeople = random.nextInt(9) + 2;

        String fileName = "InputFile.txt"; // Create a string of the name of the input file
        
        try {
            FileWriter writer = new FileWriter(fileName);
            int numberOfLines = 8*numberOfPeople; // Total number of random card values to write (8 per player)
            for(int i = 1; i <= numberOfLines; i++) {
                int n = random.nextInt(50) + 1; // Generate a random card value between 1 and 50
                String num = String.valueOf(n); // Convert the card value to a String for file writing
                writer.write(num+"\n"); // Write each card value to a new line of the file
            }

            writer.close(); 
            System.out.println("success"); // Output confirmation message if input file is created
        } catch (IOException e) {
            System.out.println("error failure"); // Output error message if input file is not created
        }

        System.out.println(numberOfPeople); // Output the number of people the file is generated for
    }
}

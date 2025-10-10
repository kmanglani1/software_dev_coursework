package cardgame;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

public class GenerateInputFile {
    public static void main(String[] args) {
        Random random = new Random();

        int numberOfPeople = random.nextInt(9) + 2;

        String fileName = "InputFile.txt";
        
        try {
            FileWriter writer = new FileWriter(fileName);

            int numberOfLines = 8*numberOfPeople;
            for(int i = 1; i <= numberOfLines; i++) {
                int n = random.nextInt(50) + 1;
                String num = String.valueOf(n); 
                writer.write(num+"\n");
                // writer.write();
            }

            writer.close();
            System.out.println("success");
        } catch (IOException e) {
            System.out.println("error failure");
            e.printStackTrace();
        }

        System.out.println(numberOfPeople);
    }
}

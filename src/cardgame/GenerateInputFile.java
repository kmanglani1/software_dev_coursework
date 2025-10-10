package cardgame;
import java.util.Random;

public class GenerateInputFile {
    public static void main(String[] args) {
        Random random = new Random();

        int numberOfPeople = random.nextInt(8) + 2;

        System.out.println(numberOfPeople);
    }
}

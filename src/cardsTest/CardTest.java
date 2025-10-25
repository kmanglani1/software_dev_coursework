package cardgame;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class CardTest {
    @Test
    public void testGetCardValue(){
        Card c = new Card(4);
        assertEquals(4, c.getCardValue());
    }

}
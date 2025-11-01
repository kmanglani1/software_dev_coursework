package cardgame;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.AfterClass;


import java.util.List;
import java.util.ArrayList;


public class CardDeckTest {

    private CardDeck deck;

    @Before
    public void assign() {
        CardDeck.resetIdCounter();
        deck = new CardDeck();
    }

    @Test
    public void testEmptyDeck () {
        assertEquals(-1, deck.getTopCard());
        assertEquals(-1, deck.getBottomCard());
        assertEquals(0, deck.getMaxSize());
        assertEquals(1, deck.getDeckId());
        assertEquals("", deck.stringCardsHeld());
    }

    @Test
    public void testAddingCards() {
        
        deck.addCard(new Card(4));
        deck.addCard(new Card(7));
        deck.addCard(new Card(12));
        deck.addCard(new Card(10));
        deck.addCard(new Card(26));

        List<Card> tempDeck2 = deck.getDeckContents();

        assertEquals(4, deck.getCardAtPosition(0).getCardValue());
        assertEquals(7, deck.getCardAtPosition(1).getCardValue());
        assertEquals(12, deck.getCardAtPosition(2).getCardValue());
        assertEquals(10, deck.getCardAtPosition(3).getCardValue());
        assertEquals(26, deck.getCardAtPosition(4).getCardValue());

        assertEquals(0, deck.getTopCard());
        assertEquals(-1, deck.getBottomCard());
        assertEquals(5, deck.getMaxSize());

    }
    
    @Test
    public void testRemovingCards() {
        deck.addCard(new Card(4));
        deck.addCard(new Card(7));
        deck.addCard(new Card(12));
        deck.addCard(new Card(10));
        deck.addCard(new Card(26));

        deck.removeCard();
        
        List <Card> tempDeck3 = deck.getDeckContents();
        assertEquals(4, deck.getMaxSize());

        assertEquals(7, deck.getCardAtPosition(0).getCardValue());
        assertEquals(12, deck.getCardAtPosition(1).getCardValue());
        assertEquals(10, deck.getCardAtPosition(2).getCardValue());
        assertEquals(26, deck.getCardAtPosition(3).getCardValue());
    }

    @Test
    public void testGetDeckContents(){
        deck.addCard(new Card(4));
        deck.addCard(new Card(7));
        deck.addCard(new Card(12));
        deck.addCard(new Card(10));
        deck.addCard(new Card(26));

        ArrayList<Integer> tempDeck4 = new ArrayList<>();
        tempDeck4.add(4);
        tempDeck4.add(7);
        tempDeck4.add(12);
        tempDeck4.add(10);
        tempDeck4.add(26);

        ArrayList<Integer> tempDeck5 = new ArrayList<>();
        for (int i = 0; i < deck.getMaxSize(); i++) {
            tempDeck5.add(deck.getCardAtPosition(i).getCardValue());
        }
        assertEquals(tempDeck4, tempDeck5);
    }

    @Test
    public void testStringCardsHeld() {
        deck.addCard(new Card(4));
        deck.addCard(new Card(7));
        deck.addCard(new Card(12));
        deck.addCard(new Card(10));
        deck.addCard(new Card(26));

        assertEquals(" 4 7 12 10 26", deck.stringCardsHeld());
    }

    // @Test
    // public void testCardPositionSetters() {
    //     deck.addCard(new Card(4));
    //     deck.addCard(new Card(7));
    //     deck.addCard(new Card(12));
    //     deck.addCard(new Card(10));
    //     deck.addCard(new Card(26));

    //     deck.setMaxSize(10);
    //     assertEquals(10, deck.getMaxSize());
    //     deck.setTopCard(8);
    //     assertEquals(8, deck.getTopCard());
    //     deck.setBottomCard(20);
    //     assertEquals(20, deck.getBottomCard());

    // }

     @Test
    public void testDeckStringCardsHeld(){
       deck.addCard(new Card(4));
        deck.addCard(new Card(7));
        deck.addCard(new Card(12));
        deck.addCard(new Card(10));
        deck.addCard(new Card(26));
        assertEquals(" 4 7 12 10 26", deck.stringCardsHeld());
    }


}

package cardgame;
import static org.junit.Assert.*;
import org.junit.Test;
import java.io.IOException;

import cardgame.CardDeck;
import cardgame.CardGame;
import cardgame.Player;

import org.junit.Before;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;


import java.util.List;
import java.beans.Transient;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class CardGameTest {

    private CardGame testGame;

    @Before
    public void reset(){
        testGame = new CardGame();
        Player.resetIdCounter();
        CardDeck.resetIdCounter();
    }
    
    @Test
    public void testValidInputFileTrue(){
        String filename = "TestFile1";
        File testFile = new File(filename);
        try {
            FileWriter writer = new FileWriter(filename);
            for (int i = 1; i<=16; i++) {
                writer.write(i+"\n");
            }
            writer.close();
        } catch (IOException e){
        }

        CardDeck testInitialDeck = new CardDeck();
        boolean result = testGame.validInputFile(filename, 2, testInitialDeck);
        assertTrue(result);
        assertEquals(16, testInitialDeck.getDeckContents().size());

        testFile.delete();
    }

    @Test
    public void testValidInputFileFalse(){
        String filename = "TestFile1";
        File testFile = new File(filename);
        try {
            FileWriter writer = new FileWriter(filename);
            for (int i = 1; i<=10; i++) {
                writer.write(i+"\n");
            }
            writer.close();
        } catch (IOException e){
        }

        CardDeck testInitialDeck = new CardDeck();
        boolean result = testGame.validInputFile(filename, 2, testInitialDeck);
        assertFalse(result);

        testFile.delete();
    }

    @Test
    public void testGetPlayer(){
        testGame.addPlayer(new Player());
        testGame.addPlayer(new Player());
        testGame.addPlayer(new Player());

        Player player = testGame.getPlayer(2);
        assertNotNull(player);
        assertEquals(2, player.getPlayerId());
    }

    @Test
    public void testGetDeck(){
        testGame.addDeck(new CardDeck());
        testGame.addDeck(new CardDeck());
        testGame.addDeck(new CardDeck());

        CardDeck cardDeck = testGame.getCardDeck(2);
        assertNotNull(cardDeck);
        assertEquals(2, cardDeck.getDeckId());
    }

    @Test
    public void testCheckPlayerHandTrue(){
        Player player = new Player();
        testGame.addPlayer(player);
        player.addCardHeld(new Card(1));
        player.addCardHeld(new Card(1));
        player.addCardHeld(new Card(1));
        player.addCardHeld(new Card(1));

        assertTrue(testGame.checkPlayerHand(1));
    }

    @Test
    public void testCheckPlayerHandFalse(){
        Player player = new Player();
        testGame.addPlayer(player);
        player.addCardHeld(new Card(1));
        player.addCardHeld(new Card(2));
        player.addCardHeld(new Card(3));
        player.addCardHeld(new Card(4));

        assertFalse(testGame.checkPlayerHand(1));
    }

    @Test
    public void testThisThreadWon(){
        Player player = new Player();
        testGame.addPlayer(player);
        player.addCardHeld(new Card(1));
        player.addCardHeld(new Card(1));
        player.addCardHeld(new Card(1));
        player.addCardHeld(new Card(1));

        CardDeck initialDeck = new CardDeck();
        CardDeck deck = new CardDeck();
        testGame.addDeck(deck);
        deck.addCard(new Card(10));

        String filename1 = "player1_output.txt";
        File testPlayerFile = new File(filename1);

        String filename2 = "deck1_output.txt";
        File testDeckFile = new File(filename2);

        testGame.thisThreadWon(1);

        assertTrue(testPlayerFile.exists());
        assertTrue(testDeckFile.exists());

        testPlayerFile.delete();
        testDeckFile.delete();
    }

    @Test
    public void testOtherThreadWon(){
        Player player = new Player();
        testGame.addPlayer(player);
        player.addCardHeld(new Card(5));
        player.addCardHeld(new Card(7));
        player.addCardHeld(new Card(9));
        player.addCardHeld(new Card(11));

        CardDeck initialDeck = new CardDeck();
        CardDeck deck = new CardDeck();
        testGame.addDeck(deck);
        deck.addCard(new Card(10));

        String filename1 = "player1_output.txt";
        File testPlayerFile = new File(filename1);

        String filename2 = "deck1_output.txt";
        File testDeckFile = new File(filename2);

        testGame.otherThreadWon(1, 2);

        assertTrue(testPlayerFile.exists());
        assertTrue(testDeckFile.exists());

        testPlayerFile.delete();
        testDeckFile.delete();
    }

    @Test
    public void testTakeTurn(){
        Player p1 = new Player();
        testGame.addPlayer(p1);
        p1.addCardHeld(new Card(1));
        p1.addCardHeld(new Card(1));
        p1.addCardHeld(new Card(2));
        p1.addCardHeld(new Card(1));

        Player p2 = new Player();
        testGame.addPlayer(p2);

        CardDeck initialDeck = new CardDeck();

        CardDeck deck1 = new CardDeck();
        deck1.addCard(new Card(5));
        testGame.addDeck(deck1);

        CardDeck deck2 = new CardDeck();
        testGame.addDeck(deck2);

        testGame.takeTurn(1);

        assertTrue(testGame.getCardDeck(2).getDeckContents().size() == 0);
        assertTrue(testGame.getCardDeck(3).getDeckContents().size() == 1);

        assertEquals(" 1 1 1 5", testGame.getPlayer(1).stringCardsHeld());
        assertEquals(" 2", testGame.getCardDeck(3).stringCardsHeld());


    }


}
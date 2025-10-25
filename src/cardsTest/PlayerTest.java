package cardgame;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;

import cardgame.Player;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;

import java.beans.Transient;

public class PlayerTest {

    @Before
    public void reset(){
        Player.resetIdCounter();
    }

    @Test
    public void testGetPlayerId(){
        Player p1 = new Player();
        assertEquals(1, p1.getPlayerId());
    }

    @Test
    public void testResetIdCounter(){
        Player p1 = new Player();
        Player p2 = new Player();
        assertEquals(2, p2.getPlayerId());

        Player.resetIdCounter();
        Player p3 = new Player();
        assertEquals(1, p3.getPlayerId());
    }

    @Test
    public void testAddCardHeld(){
        Player p1 = new Player();
        Card c = new Card(11);
        p1.addCardHeld(c);
        assertEquals(" 11", p1.stringCardsHeld());
    }

    @Test
    public void testRemoveCardHeld(){
        Player p1 = new Player();
        Card c1 = new Card(11);
        Card c2 = new Card(12);
        p1.addCardHeld(c1);
        p1.addCardHeld(c2);
        assertEquals(" 11 12", p1.stringCardsHeld());
        p1.removeCardHeld(c1);
        assertEquals(" 12", p1.stringCardsHeld());
    }

    @Test
    public void testCheckHandTrue(){
        Player p1 = new Player();
        Card c1 = new Card(1);
        Card c2 = new Card(1);
        Card c3 = new Card(1);
        Card c4 = new Card(1);
        p1.addCardHeld(c1);
        p1.addCardHeld(c2);
        p1.addCardHeld(c3);
        p1.addCardHeld(c4);
        assertTrue(p1.checkHand());
    }

    @Test
    public void testCheckHandFalse(){
        Player p1 = new Player();
        Card c1 = new Card(11);
        Card c2 = new Card(12);
        Card c3 = new Card(13);
        Card c4 = new Card(14);
        p1.addCardHeld(c1);
        p1.addCardHeld(c2);
        p1.addCardHeld(c3);
        p1.addCardHeld(c4);
        assertFalse(p1.checkHand());
    }

    @Test
    public void testCardToDiscard(){
        Player p1 = new Player();
        Card c1 = new Card(11);
        Card c2 = new Card(12);
        Card c3 = new Card(13);
        Card c4 = new Card(14);
        p1.addCardHeld(c1);
        p1.addCardHeld(c2);
        p1.addCardHeld(c3);
        p1.addCardHeld(c4);
        Card toDiscard = p1.cardToDiscard();
        assertNotNull(toDiscard);
        assertEquals(11, toDiscard.getCardValue());
    }

    @Test
    public void testStringCardsHeld(){
        Player p1 = new Player();
        Card c1 = new Card(11);
        Card c2 = new Card(12);
        p1.addCardHeld(c1);
        p1.addCardHeld(c2);
        assertEquals(" 11 12", p1.stringCardsHeld());
    }

}
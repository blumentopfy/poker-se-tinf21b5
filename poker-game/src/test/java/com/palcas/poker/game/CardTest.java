package com.palcas.poker.game;

import com.palcas.poker.game.model.Rank;
import com.palcas.poker.game.model.Suit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {
    @Test
    public void testCardEquality() {
        Card card1 = new Card(Suit.HEARTS, Rank.ACE);
        Card card2 = new Card(Suit.HEARTS, Rank.ACE);
        Card card3 = new Card(Suit.CLUBS, Rank.ACE);

        assertTrue(card1.equals(card2));
        assertFalse(card1.equals(card3));
    }

    @Test
    public void testCardComparison() {
        Card card1 = new Card(Suit.HEARTS, Rank.ACE);
        Card card2 = new Card(Suit.HEARTS, Rank.TWO);
        Card card3 = new Card(Suit.DIAMONDS, Rank.ACE);
        Card card4 = new Card(Suit.HEARTS, Rank.KING);

        assertTrue(card1.compareTo(card2) > 0);
        assertTrue(card2.compareTo(card1) < 0);
        assertTrue(card1.compareTo(card3) == 0);
        assertTrue(card1.compareTo(card4) > 0);
        assertTrue(card2.compareTo(card4) < 0);
    }

    @Test
    public void testGetters() {
        Card card = new Card(Suit.DIAMONDS, Rank.FIVE);

        assertEquals(Suit.DIAMONDS, card.getSuit());
        assertEquals(Rank.FIVE, card.getRank());
    }

}

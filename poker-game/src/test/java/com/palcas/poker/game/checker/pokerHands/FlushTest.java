package com.palcas.poker.game.checker.pokerHands;

import com.palcas.poker.model.Rank;
import com.palcas.poker.model.Suit;
import com.palcas.poker.game.Card;
import com.palcas.poker.game.checker.CardsStatisticsService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FlushTest {
    private static Flush flush;

    @BeforeAll
    public static void setUp() {
        flush = new Flush(new CardsStatisticsService());
    }


    @Test
    public void testContainsFlush1() {
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.SPADES, Rank.FOUR);
        Card card3 = new Card(Suit.SPADES, Rank.SIX);
        Card card4 = new Card(Suit.SPADES, Rank.EIGHT);
        Card card5 = new Card(Suit.SPADES, Rank.TEN);
        Card card6 = new Card(Suit.HEARTS, Rank.ACE);
        Card card7 = new Card(Suit.DIAMONDS, Rank.KING);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        boolean result = flush.containsFlush(cards);
        assertTrue(result);
    }

    @Test
    public void testContainsFlush2() {
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.CLUBS, Rank.FOUR);
        Card card3 = new Card(Suit.HEARTS, Rank.SIX);
        Card card4 = new Card(Suit.DIAMONDS, Rank.EIGHT);
        Card card5 = new Card(Suit.SPADES, Rank.TEN);
        Card card6 = new Card(Suit.HEARTS, Rank.ACE);
        Card card7 = new Card(Suit.DIAMONDS, Rank.KING);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        boolean result = flush.containsFlush(cards);
        assertFalse(result);
    }

    @Test
    public void testContainsFlush3() {
        Card card1 = new Card(Suit.HEARTS, Rank.TWO);
        Card card2 = new Card(Suit.CLUBS, Rank.FOUR);
        Card card3 = new Card(Suit.HEARTS, Rank.SIX);
        Card card4 = new Card(Suit.HEARTS, Rank.EIGHT);
        Card card5 = new Card(Suit.SPADES, Rank.TEN);
        Card card6 = new Card(Suit.HEARTS, Rank.ACE);
        Card card7 = new Card(Suit.HEARTS, Rank.FOUR);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        boolean result = flush.containsFlush(cards);
        assertTrue(result);
    }

    @Test
    public void testSelectHandForFlush1() {
        Card twoOfSpades = new Card(Suit.SPADES, Rank.TWO);
        Card fourOfSpades = new Card(Suit.SPADES, Rank.FOUR);
        Card sixOfSpades = new Card(Suit.SPADES, Rank.SIX);
        Card eightOfSpades = new Card(Suit.SPADES, Rank.EIGHT);
        Card tenOfSpades = new Card(Suit.SPADES, Rank.TEN);
        Card aceOfHearts = new Card(Suit.HEARTS, Rank.ACE);
        Card kingOfDiamonds = new Card(Suit.DIAMONDS, Rank.KING);
        Card[] cards = {fourOfSpades, sixOfSpades, tenOfSpades, aceOfHearts, kingOfDiamonds, twoOfSpades, eightOfSpades};

        Card[] selectedCards = flush.selectHandForFlush(cards);
        assertEquals(selectedCards.length, 5);
        assertEquals(selectedCards[0], tenOfSpades);
        assertEquals(selectedCards[1], eightOfSpades);
        assertEquals(selectedCards[2], sixOfSpades);
        assertEquals(selectedCards[3], fourOfSpades);
        assertEquals(selectedCards[4], twoOfSpades);
    }

    @Test
    public void testSelectHandForFlush2() {
        //should return null since it doesn't have a Flush
        Card twoOfSpades = new Card(Suit.SPADES, Rank.TWO);
        Card fourOfSpades = new Card(Suit.SPADES, Rank.FOUR);
        Card sixOfSpades = new Card(Suit.SPADES, Rank.SIX);
        Card eightOfSpades = new Card(Suit.CLUBS, Rank.EIGHT);
        Card tenOfSpades = new Card(Suit.SPADES, Rank.TEN);
        Card aceOfHearts = new Card(Suit.HEARTS, Rank.ACE);
        Card kingOfDiamonds = new Card(Suit.DIAMONDS, Rank.KING);
        Card[] cards = {fourOfSpades, sixOfSpades, tenOfSpades, aceOfHearts, kingOfDiamonds, twoOfSpades, eightOfSpades};

        Card[] selectedCards = flush.selectHandForFlush(cards);
        assertEquals(selectedCards, null);
    }
}

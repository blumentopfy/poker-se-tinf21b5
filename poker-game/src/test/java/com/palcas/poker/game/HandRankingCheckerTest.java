package com.palcas.poker.game;

import com.palcas.poker.Rank;
import com.palcas.poker.Suit;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
public class HandRankingCheckerTest {






    @Test
    public void testContainsPair1() {
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.DIAMONDS, Rank.THREE);
        Card card3 = new Card(Suit.HEARTS, Rank.FOUR);
        Card card4 = new Card(Suit.CLUBS, Rank.FOUR);
        Card card5 = new Card(Suit.SPADES, Rank.SIX);
        Card card6 = new Card(Suit.HEARTS, Rank.SEVEN);
        Card card7 = new Card(Suit.HEARTS, Rank.EIGHT);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        boolean result = HandRankingChecker.containsPair(cards);
        assertTrue(result);
    }

    @Test
    public void testContainsPair2() {
        // 2 pairs even
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.DIAMONDS, Rank.THREE);
        Card card3 = new Card(Suit.HEARTS, Rank.FOUR);
        Card card4 = new Card(Suit.CLUBS, Rank.FIVE);
        Card card5 = new Card(Suit.SPADES, Rank.SIX);
        Card card6 = new Card(Suit.HEARTS, Rank.FOUR);
        Card card7 = new Card(Suit.HEARTS, Rank.FIVE);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        boolean result = HandRankingChecker.containsPair(cards);
        assertTrue(result);
    }

    @Test
    public void testContainsPair3() {
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.DIAMONDS, Rank.THREE);
        Card card3 = new Card(Suit.HEARTS, Rank.FOUR);
        Card card4 = new Card(Suit.CLUBS, Rank.FIVE);
        Card card5 = new Card(Suit.SPADES, Rank.SIX);
        Card card6 = new Card(Suit.HEARTS, Rank.SEVEN);
        Card card7 = new Card(Suit.HEARTS, Rank.EIGHT);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        boolean result = HandRankingChecker.containsPair(cards);
        assertFalse(result);
    }
}
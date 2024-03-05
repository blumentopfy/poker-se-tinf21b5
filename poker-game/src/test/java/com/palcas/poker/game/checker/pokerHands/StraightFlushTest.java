package com.palcas.poker.game.checker.pokerHands;

import com.palcas.poker.Rank;
import com.palcas.poker.Suit;
import com.palcas.poker.game.Card;
import com.palcas.poker.game.checker.CardsStatisticsService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StraightFlushTest {

    private static StraightFlush straightFlush;

    @BeforeAll
    public static void setUp() {
        straightFlush = new StraightFlush(new CardsStatisticsService());
    }

    @Test
    public void testContainsStraightFlush1() {
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.SPADES, Rank.THREE);
        Card card3 = new Card(Suit.SPADES, Rank.FOUR);
        Card card4 = new Card(Suit.SPADES, Rank.FIVE);
        Card card5 = new Card(Suit.SPADES, Rank.SIX);
        Card card6 = new Card(Suit.SPADES, Rank.QUEEN);
        Card card7 = new Card(Suit.HEARTS, Rank.EIGHT);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        boolean result = straightFlush.containsStraightFlush(cards);
        assertTrue(result);
    }


    @Test
    public void testContainsStraightFlush2() {
        Card card1 = new Card(Suit.HEARTS, Rank.TWO);
        Card card2 = new Card(Suit.HEARTS, Rank.THREE);
        Card card3 = new Card(Suit.HEARTS, Rank.FOUR);
        Card card4 = new Card(Suit.HEARTS, Rank.FIVE);
        Card card5 = new Card(Suit.DIAMONDS, Rank.KING);
        Card card6 = new Card(Suit.HEARTS, Rank.ACE);
        Card card7 = new Card(Suit.CLUBS, Rank.EIGHT);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        boolean result = straightFlush.containsStraightFlush(cards);
        assertTrue(result);
    }

    @Test
    public void testContainsStraightFlush3() {
        // not straight
        Card card1 = new Card(Suit.DIAMONDS, Rank.TEN);
        Card card2 = new Card(Suit.DIAMONDS, Rank.JACK);
        Card card3 = new Card(Suit.DIAMONDS, Rank.SIX);
        Card card4 = new Card(Suit.DIAMONDS, Rank.KING);
        Card card5 = new Card(Suit.DIAMONDS, Rank.ACE);
        Card card6 = new Card(Suit.SPADES, Rank.TWO);
        Card card7 = new Card(Suit.HEARTS, Rank.THREE);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        boolean result = straightFlush.containsStraightFlush(cards);
        assertFalse(result);
    }

    @Test
    public void testContainsStraightFlush4() {
        // not flush
        Card card1 = new Card(Suit.SPADES, Rank.FOUR);
        Card card2 = new Card(Suit.HEARTS, Rank.FIVE);
        Card card3 = new Card(Suit.SPADES, Rank.SIX);
        Card card4 = new Card(Suit.HEARTS, Rank.SEVEN);
        Card card5 = new Card(Suit.SPADES, Rank.EIGHT);
        Card card6 = new Card(Suit.DIAMONDS, Rank.NINE);
        Card card7 = new Card(Suit.CLUBS, Rank.TEN);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        boolean result = straightFlush.containsStraightFlush(cards);
        assertFalse(result);
    }
}

package com.palcas.poker.game.checker.pokerHands;

import com.palcas.poker.model.Rank;
import com.palcas.poker.model.Suit;
import com.palcas.poker.game.Card;
import com.palcas.poker.game.checker.CardsStatisticsService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TwoPairsTest {
    private static TwoPairs twoPairs;

    @BeforeAll
    public static void setUp() {
        twoPairs = new TwoPairs(new CardsStatisticsService());
    }

    @Test
    public void testContainsTwoPairs1() {
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.DIAMONDS, Rank.TWO);
        Card card3 = new Card(Suit.HEARTS, Rank.THREE);
        Card card4 = new Card(Suit.CLUBS, Rank.THREE);
        Card card5 = new Card(Suit.SPADES, Rank.FOUR);
        Card card6 = new Card(Suit.HEARTS, Rank.FIVE);
        Card card7 = new Card(Suit.HEARTS, Rank.KING);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        boolean result = twoPairs.containsTwoPairs(cards);
        assertTrue(result);
    }

    @Test
    public void testContainsTwoPairs2() {
        // 3 pairs even
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.DIAMONDS, Rank.TWO);
        Card card3 = new Card(Suit.HEARTS, Rank.THREE);
        Card card4 = new Card(Suit.CLUBS, Rank.FOUR);
        Card card5 = new Card(Suit.SPADES, Rank.FOUR);
        Card card6 = new Card(Suit.HEARTS, Rank.THREE);
        Card card7 = new Card(Suit.HEARTS, Rank.KING);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        boolean result = twoPairs.containsTwoPairs(cards);
        assertTrue(result);
    }

    @Test
    public void testContainsTwoPairs3() {
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.DIAMONDS, Rank.THREE);
        Card card3 = new Card(Suit.HEARTS, Rank.THREE);
        Card card4 = new Card(Suit.CLUBS, Rank.FIVE);
        Card card5 = new Card(Suit.SPADES, Rank.FOUR);
        Card card6 = new Card(Suit.HEARTS, Rank.TWO);
        Card card7 = new Card(Suit.HEARTS, Rank.KING);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        boolean result = twoPairs.containsTwoPairs(cards);
        assertTrue(result);
    }

    @Test
    public void testContainsTwoPairs4() {
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.DIAMONDS, Rank.THREE);
        Card card3 = new Card(Suit.HEARTS, Rank.FOUR);
        Card card4 = new Card(Suit.CLUBS, Rank.FIVE);
        Card card5 = new Card(Suit.SPADES, Rank.FIVE);
        Card card6 = new Card(Suit.HEARTS, Rank.SEVEN);
        Card card7 = new Card(Suit.HEARTS, Rank.EIGHT);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        boolean result = twoPairs.containsTwoPairs(cards);
        assertFalse(result);
    }
}

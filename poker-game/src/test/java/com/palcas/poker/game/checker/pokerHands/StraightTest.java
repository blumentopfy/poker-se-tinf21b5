package com.palcas.poker.game.checker.pokerHands;

import com.palcas.poker.model.Rank;
import com.palcas.poker.model.Suit;
import com.palcas.poker.game.Card;
import com.palcas.poker.game.checker.CardsStatisticsService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StraightTest {
    private static Straight straight;

    @BeforeAll
    public static void setUp() {
        straight = new Straight(new CardsStatisticsService());
    }

    @Test
    public void testContainsStraight1() {
        // contains straight with ace as lowest
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.DIAMONDS, Rank.THREE);
        Card card3 = new Card(Suit.CLUBS, Rank.FOUR);
        Card card4 = new Card(Suit.HEARTS, Rank.FIVE);
        Card card5 = new Card(Suit.SPADES, Rank.EIGHT);
        Card card6 = new Card(Suit.HEARTS, Rank.KING);
        Card card7 = new Card(Suit.HEARTS, Rank.ACE);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        boolean result = straight.containsStraight(cards);
        assertTrue(result);
    }

    @Test
    public void testContainsStraight2() {
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.DIAMONDS, Rank.THREE);
        Card card3 = new Card(Suit.CLUBS, Rank.FOUR);
        Card card4 = new Card(Suit.HEARTS, Rank.FIVE);
        Card card5 = new Card(Suit.SPADES, Rank.ACE);
        Card card6 = new Card(Suit.HEARTS, Rank.SIX);
        Card card7 = new Card(Suit.HEARTS, Rank.SEVEN);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        boolean result = straight.containsStraight(cards);
        assertTrue(result);
    }

    @Test
    public void testContainsStraight3() {
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.DIAMONDS, Rank.THREE);
        Card card3 = new Card(Suit.CLUBS, Rank.FOUR);
        Card card4 = new Card(Suit.HEARTS, Rank.SEVEN);
        Card card5 = new Card(Suit.SPADES, Rank.TEN);
        Card card6 = new Card(Suit.HEARTS, Rank.QUEEN);
        Card card7 = new Card(Suit.HEARTS, Rank.KING);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        boolean result = straight.containsStraight(cards);
        assertFalse(result);
    }
}

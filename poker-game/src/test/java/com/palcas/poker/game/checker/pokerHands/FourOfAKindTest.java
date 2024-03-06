package com.palcas.poker.game.checker.pokerHands;

import com.palcas.poker.model.Rank;
import com.palcas.poker.model.Suit;
import com.palcas.poker.game.Card;
import com.palcas.poker.game.checker.CardsStatisticsService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FourOfAKindTest {

    private static FourOfAKind fourOfAKind;

    @BeforeAll
    public static void setUp() {
        fourOfAKind = new FourOfAKind(new CardsStatisticsService());
    }

    @Test
    public void testContainsFourOfAKind1() {
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.CLUBS, Rank.TWO);
        Card card3 = new Card(Suit.HEARTS, Rank.TWO);
        Card card4 = new Card(Suit.DIAMONDS, Rank.TWO);
        Card card5 = new Card(Suit.SPADES, Rank.THREE);
        Card card6 = new Card(Suit.HEARTS, Rank.KING);
        Card card7 = new Card(Suit.HEARTS, Rank.QUEEN);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        boolean result = fourOfAKind.containsFourOfAKind(cards);
        assertTrue(result);
    }

    @Test
    public void testContainsFourOfAKind2() {
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.CLUBS, Rank.FOUR);
        Card card3 = new Card(Suit.HEARTS, Rank.FIVE);
        Card card4 = new Card(Suit.DIAMONDS, Rank.SEVEN);
        Card card5 = new Card(Suit.SPADES, Rank.THREE);
        Card card6 = new Card(Suit.HEARTS, Rank.ACE);
        Card card7 = new Card(Suit.HEARTS, Rank.KING);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        boolean result = fourOfAKind.containsFourOfAKind(cards);
        assertFalse(result);
    }

    @Test
    public void testContainsFourOfAKind3() {
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.CLUBS, Rank.TWO);
        Card card3 = new Card(Suit.HEARTS, Rank.FOUR);
        Card card4 = new Card(Suit.DIAMONDS, Rank.FOUR);
        Card card5 = new Card(Suit.SPADES, Rank.THREE);
        Card card6 = new Card(Suit.HEARTS, Rank.TWO);
        Card card7 = new Card(Suit.DIAMONDS, Rank.TWO);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        boolean result = fourOfAKind.containsFourOfAKind(cards);
        assertTrue(result);
    }
}

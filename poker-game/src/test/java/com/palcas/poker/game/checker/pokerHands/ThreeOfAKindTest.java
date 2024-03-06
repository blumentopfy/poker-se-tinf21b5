package com.palcas.poker.game.checker.pokerHands;

import com.palcas.poker.model.Rank;
import com.palcas.poker.model.Suit;
import com.palcas.poker.game.Card;
import com.palcas.poker.game.checker.CardsStatisticsService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ThreeOfAKindTest {

    private static ThreeOfAKind threeOfAKind;

    @BeforeAll
    public static void setUp() {
        threeOfAKind = new ThreeOfAKind(new CardsStatisticsService());
    }

    @Test
    public void testContainsThreeOfAKind1() {
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.DIAMONDS, Rank.TWO);
        Card card3 = new Card(Suit.CLUBS, Rank.TWO);
        Card card4 = new Card(Suit.HEARTS, Rank.THREE);
        Card card5 = new Card(Suit.SPADES, Rank.SIX);
        Card card6 = new Card(Suit.HEARTS, Rank.FIVE);
        Card card7 = new Card(Suit.HEARTS, Rank.KING);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        boolean result = threeOfAKind.containsThreeOfAKind(cards);
        assertTrue(result);
    }

    @Test
    public void testContainsThreeOfAKind2() {
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.DIAMONDS, Rank.THREE);
        Card card3 = new Card(Suit.CLUBS, Rank.KING);
        Card card4 = new Card(Suit.HEARTS, Rank.FIVE);
        Card card5 = new Card(Suit.SPADES, Rank.SIX);
        Card card6 = new Card(Suit.DIAMONDS, Rank.KING);
        Card card7 = new Card(Suit.HEARTS, Rank.KING);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        boolean result = threeOfAKind.containsThreeOfAKind(cards);
        assertTrue(result);
    }

    @Test
    public void testContainsThreeOfAKind3() {
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.DIAMONDS, Rank.THREE);
        Card card3 = new Card(Suit.CLUBS, Rank.FOUR);
        Card card4 = new Card(Suit.HEARTS, Rank.FIVE);
        Card card5 = new Card(Suit.SPADES, Rank.SIX);
        Card card6 = new Card(Suit.HEARTS, Rank.SEVEN);
        Card card7 = new Card(Suit.HEARTS, Rank.EIGHT);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        boolean result = threeOfAKind.containsThreeOfAKind(cards);
        assertFalse(result);
    }

    @Test
    public void testContainsThreeOfAKind4() {
        // Edge case: Four of a kind scenario
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.DIAMONDS, Rank.TWO);
        Card card3 = new Card(Suit.CLUBS, Rank.TWO);
        Card card4 = new Card(Suit.HEARTS, Rank.KING);
        Card card5 = new Card(Suit.SPADES, Rank.SIX);
        Card card6 = new Card(Suit.HEARTS, Rank.FIVE);
        Card card7 = new Card(Suit.HEARTS, Rank.TWO);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        boolean result = threeOfAKind.containsThreeOfAKind(cards);
        assertTrue(result);
    }

}

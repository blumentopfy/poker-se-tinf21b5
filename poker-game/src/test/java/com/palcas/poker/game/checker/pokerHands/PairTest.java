package com.palcas.poker.game.checker.pokerHands;

import com.palcas.poker.model.Rank;
import com.palcas.poker.model.Suit;
import com.palcas.poker.game.Card;
import com.palcas.poker.game.evaluation.CardsStatisticsService;
import com.palcas.poker.game.evaluation.pokerHands.Pair;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PairTest {
    private static Pair pair;

    @BeforeAll
    public static void setUp() {
        pair = new Pair(new CardsStatisticsService());
    }

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

        boolean result = pair.containsPair(cards);
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

        boolean result = pair.containsPair(cards);
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

        boolean result = pair.containsPair(cards);
        assertFalse(result);
    }

    @Test
    public void testSelectHandForPair1() {
        Card twoOfSpades = new Card(Suit.SPADES, Rank.TWO);
        Card threeOfDiamonds = new Card(Suit.DIAMONDS, Rank.THREE);
        Card fourOfHearts1 = new Card(Suit.HEARTS, Rank.FOUR);
        Card fourOfClubs = new Card(Suit.CLUBS, Rank.FOUR);
        Card sixOfSpades = new Card(Suit.SPADES, Rank.SIX);
        Card sevenOfHearts = new Card(Suit.HEARTS, Rank.SEVEN);
        Card eightOfHearts = new Card(Suit.HEARTS, Rank.EIGHT);
        Card[] cards = {fourOfHearts1, twoOfSpades, sevenOfHearts, threeOfDiamonds, eightOfHearts, sixOfSpades, fourOfClubs};

        Card[] selectedCards = pair.selectHandForPair(cards);
        assertEquals(5, selectedCards.length);
        assertEquals(selectedCards[0].getRank(), Rank.FOUR);
        assertEquals(selectedCards[1].getRank(), Rank.FOUR);
        assertEquals(selectedCards[2], eightOfHearts);
        assertEquals(selectedCards[3], sevenOfHearts);
        assertEquals(selectedCards[4], sixOfSpades);
    }

    @Test
    public void testSelectHandForPair2() {
        Card twoOfSpades = new Card(Suit.SPADES, Rank.TWO);
        Card threeOfDiamonds = new Card(Suit.DIAMONDS, Rank.THREE);
        Card fourOfHearts1 = new Card(Suit.HEARTS, Rank.FOUR);
        Card fiveOfClubs = new Card(Suit.CLUBS, Rank.FIVE);
        Card sixOfSpades = new Card(Suit.SPADES, Rank.SIX);
        Card sevenOfHearts = new Card(Suit.HEARTS, Rank.SEVEN);
        Card eightOfHearts = new Card(Suit.HEARTS, Rank.EIGHT);
        Card[] cards = {fourOfHearts1, twoOfSpades, sevenOfHearts, threeOfDiamonds, eightOfHearts, sixOfSpades, fiveOfClubs};

        Card[] selectedCards = pair.selectHandForPair(cards);
        assertNull(selectedCards);
    }
}

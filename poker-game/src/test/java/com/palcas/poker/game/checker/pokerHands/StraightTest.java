package com.palcas.poker.game.checker.pokerHands;

import com.palcas.poker.game.model.Rank;
import com.palcas.poker.game.model.Suit;
import com.palcas.poker.game.Card;
import com.palcas.poker.game.evaluation.CardsStatisticsService;
import com.palcas.poker.game.evaluation.pokerHands.Straight;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    public void testSelectHandForStraight1() {
        //should return straight from 7 to 3
        Card twoOfSpades = new Card(Suit.SPADES, Rank.TWO);
        Card threeOfSpades = new Card(Suit.SPADES, Rank.THREE);
        Card fourOfClubs = new Card(Suit.CLUBS, Rank.FOUR);
        Card fiveOfSpades = new Card(Suit.SPADES, Rank.FIVE);
        Card sixOfSpades = new Card(Suit.SPADES, Rank.SIX);
        Card sevenOfSpades = new Card(Suit.SPADES, Rank.SEVEN);
        Card nineOfSpades = new Card(Suit.HEARTS, Rank.NINE);
        Card[] cards = {fourOfClubs, threeOfSpades, sixOfSpades, fiveOfSpades, twoOfSpades, sevenOfSpades, nineOfSpades};

        Card[] selectedCards = straight.selectHandForStraight(cards);
        assertEquals(5, selectedCards.length);
        assertEquals(selectedCards[0], sevenOfSpades);
        assertEquals(selectedCards[1], sixOfSpades);
        assertEquals(selectedCards[2], fiveOfSpades);
        assertEquals(selectedCards[3], fourOfClubs);
        assertEquals(selectedCards[4], threeOfSpades);
    }

    @Test
    public void testSelectHandForStraight2() {
        //should return null, since there is no Straight
        Card twoOfSpades = new Card(Suit.SPADES, Rank.TWO);
        Card threeOfSpades = new Card(Suit.SPADES, Rank.THREE);
        Card fourOfClubs = new Card(Suit.CLUBS, Rank.ACE);
        Card fiveOfSpades = new Card(Suit.SPADES, Rank.FIVE);
        Card sixOfSpades = new Card(Suit.SPADES, Rank.SIX);
        Card sevenOfSpades = new Card(Suit.SPADES, Rank.SEVEN);
        Card nineOfSpades = new Card(Suit.HEARTS, Rank.NINE);
        Card[] cards = {fourOfClubs, threeOfSpades, sixOfSpades, fiveOfSpades, twoOfSpades, sevenOfSpades, nineOfSpades};

        Card[] selectedCards = straight.selectHandForStraight(cards);
        assertNull(selectedCards);
    }
}

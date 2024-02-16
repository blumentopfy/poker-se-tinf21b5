package com.palcas.poker;

import com.palcas.poker.game.Card;
import com.palcas.poker.display.CardDisplay.Suit;
import com.palcas.poker.display.CardDisplay.Rank;
import com.palcas.poker.game.HandRankingChecker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
public class HandRankingCheckerContainsTest {

    @Test
    public void testContainsRoyalFlush1() {
        Card card1 = new Card(Suit.SPADES, Rank.TEN);
        Card card2 = new Card(Suit.SPADES, Rank.JACK);
        Card card3 = new Card(Suit.SPADES, Rank.QUEEN);
        Card card4 = new Card(Suit.SPADES, Rank.KING);
        Card card5 = new Card(Suit.SPADES, Rank.ACE);
        Card card6 = new Card(Suit.HEARTS, Rank.QUEEN);
        Card card7 = new Card(Suit.HEARTS, Rank.KING);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        boolean result = HandRankingChecker.containsRoyalFlush(cards);
        assertTrue(result);
    }

    @Test
    public void testContainsRoyalFlush2() {
        // when it's not flush
        Card card1 = new Card(Suit.SPADES, Rank.TEN);
        Card card2 = new Card(Suit.SPADES, Rank.JACK);
        Card card3 = new Card(Suit.CLUBS, Rank.QUEEN);
        Card card4 = new Card(Suit.SPADES, Rank.KING);
        Card card5 = new Card(Suit.SPADES, Rank.ACE);
        Card card6 = new Card(Suit.HEARTS, Rank.QUEEN);
        Card card7 = new Card(Suit.HEARTS, Rank.KING);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        boolean result = HandRankingChecker.containsRoyalFlush(cards);
        assertFalse(result);
    }

    @Test
    public void testContainsRoyalFlush3() {
        // when it's not royal
        Card card1 = new Card(Suit.SPADES, Rank.TEN);
        Card card2 = new Card(Suit.SPADES, Rank.JACK);
        Card card3 = new Card(Suit.SPADES, Rank.QUEEN);
        Card card4 = new Card(Suit.SPADES, Rank.KING);
        Card card5 = new Card(Suit.SPADES, Rank.NINE);
        Card card6 = new Card(Suit.HEARTS, Rank.QUEEN);
        Card card7 = new Card(Suit.HEARTS, Rank.KING);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        boolean result = HandRankingChecker.containsRoyalFlush(cards);
        assertFalse(result);
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

        boolean result = HandRankingChecker.containsStraightFlush(cards);
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

        boolean result = HandRankingChecker.containsStraightFlush(cards);
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

        boolean result = HandRankingChecker.containsStraightFlush(cards);
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

        boolean result = HandRankingChecker.containsStraightFlush(cards);
        assertFalse(result);
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

        boolean result = HandRankingChecker.containsFourOfAKind(cards);
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

        boolean result = HandRankingChecker.containsFourOfAKind(cards);
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

        boolean result = HandRankingChecker.containsFourOfAKind(cards);
        assertTrue(result);
    }


    @Test
    public void testContainsFullHouse1() {
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.CLUBS, Rank.TWO);
        Card card3 = new Card(Suit.HEARTS, Rank.TWO);
        Card card4 = new Card(Suit.DIAMONDS, Rank.THREE);
        Card card5 = new Card(Suit.SPADES, Rank.THREE);
        Card card6 = new Card(Suit.HEARTS, Rank.FOUR);
        Card card7 = new Card(Suit.HEARTS, Rank.FIVE);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        boolean result = HandRankingChecker.containsFullHouse(cards);
        assertTrue(result);
    }

    @Test
    public void testContainsFullHouse2() {
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.CLUBS, Rank.TWO);
        Card card3 = new Card(Suit.HEARTS, Rank.THREE);
        Card card4 = new Card(Suit.DIAMONDS, Rank.THREE);
        Card card5 = new Card(Suit.SPADES, Rank.FOUR);
        Card card6 = new Card(Suit.HEARTS, Rank.FIVE);
        Card card7 = new Card(Suit.HEARTS, Rank.SIX);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        boolean result = HandRankingChecker.containsFullHouse(cards);
        assertFalse(result);
    }

    @Test
    public void testContainsFullHouse3() {
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.CLUBS, Rank.TWO);
        Card card3 = new Card(Suit.HEARTS, Rank.TWO);
        Card card4 = new Card(Suit.DIAMONDS, Rank.THREE);
        Card card5 = new Card(Suit.SPADES, Rank.FOUR);
        Card card6 = new Card(Suit.HEARTS, Rank.THREE);
        Card card7 = new Card(Suit.HEARTS, Rank.FOUR);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        boolean result = HandRankingChecker.containsFullHouse(cards);
        assertTrue(result);
    }

    @Test
    public void testContainsFullHouse4() {
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.CLUBS, Rank.TWO);
        Card card3 = new Card(Suit.HEARTS, Rank.THREE);
        Card card4 = new Card(Suit.DIAMONDS, Rank.FOUR);
        Card card5 = new Card(Suit.SPADES, Rank.FIVE);
        Card card6 = new Card(Suit.HEARTS, Rank.SIX);
        Card card7 = new Card(Suit.HEARTS, Rank.SEVEN);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        boolean result = HandRankingChecker.containsFullHouse(cards);
        assertFalse(result);
    }

    @Test
    public void testContainsFullHouse5() {
        // 2 sets of 3 are also a full house
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.CLUBS, Rank.TWO);
        Card card3 = new Card(Suit.HEARTS, Rank.TWO);
        Card card4 = new Card(Suit.DIAMONDS, Rank.THREE);
        Card card5 = new Card(Suit.SPADES, Rank.FOUR);
        Card card6 = new Card(Suit.HEARTS, Rank.THREE);
        Card card7 = new Card(Suit.HEARTS, Rank.THREE);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        boolean result = HandRankingChecker.containsFullHouse(cards);
        assertTrue(result);
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

        boolean result = HandRankingChecker.containsFlush(cards);
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

        boolean result = HandRankingChecker.containsFlush(cards);
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

        boolean result = HandRankingChecker.containsFlush(cards);
        assertTrue(result);
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

        boolean result = HandRankingChecker.containsStraight(cards);
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

        boolean result = HandRankingChecker.containsStraight(cards);
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

        boolean result = HandRankingChecker.containsStraight(cards);
        assertFalse(result);
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

        boolean result = HandRankingChecker.containsThreeOfAKind(cards);
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

        boolean result = HandRankingChecker.containsThreeOfAKind(cards);
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

        boolean result = HandRankingChecker.containsThreeOfAKind(cards);
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

        boolean result = HandRankingChecker.containsThreeOfAKind(cards);
        assertTrue(result);
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

        boolean result = HandRankingChecker.containsTwoPairs(cards);
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

        boolean result = HandRankingChecker.containsTwoPairs(cards);
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

        boolean result = HandRankingChecker.containsTwoPairs(cards);
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

        boolean result = HandRankingChecker.containsTwoPairs(cards);
        assertFalse(result);
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
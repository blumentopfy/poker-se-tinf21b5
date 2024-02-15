package com.palcas.poker;

import com.palcas.poker.game.Card;
import com.palcas.poker.display.CardDisplay.Suit;
import com.palcas.poker.display.CardDisplay.Rank;
import com.palcas.poker.game.HandEvaluator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
public class HandEvaluatorContainsTest {

    @Test
    public void testContainsRoyalFlush1() {
        Card card1 = new Card(Suit.SPADES, Rank.TEN);
        Card card2 = new Card(Suit.SPADES, Rank.JACK);
        Card card3 = new Card(Suit.SPADES, Rank.QUEEN);
        Card card4 = new Card(Suit.SPADES, Rank.KING);
        Card card5 = new Card(Suit.SPADES, Rank.ACE);
        Card[] board = {card1, card2, card3, card4, card5};
        Card handCard1 = new Card(Suit.HEARTS, Rank.QUEEN);
        Card handCard2 = new Card(Suit.HEARTS, Rank.KING);

        boolean result = HandEvaluator.containsRoyalFlush(board, handCard1, handCard2);
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
        Card[] board = {card1, card2, card3, card4, card5};
        Card handCard1 = new Card(Suit.HEARTS, Rank.QUEEN);
        Card handCard2 = new Card(Suit.HEARTS, Rank.KING);

        boolean result = HandEvaluator.containsRoyalFlush(board, handCard1, handCard2);
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
        Card[] board = {card1, card2, card3, card4, card5};
        Card handCard1 = new Card(Suit.HEARTS, Rank.QUEEN);
        Card handCard2 = new Card(Suit.HEARTS, Rank.KING);

        boolean result = HandEvaluator.containsRoyalFlush(board, handCard1, handCard2);
        assertFalse(result);
    }




    @Test
    public void testContainsStraightFlush1() {
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.SPADES, Rank.THREE);
        Card card3 = new Card(Suit.SPADES, Rank.FOUR);
        Card card4 = new Card(Suit.SPADES, Rank.FIVE);
        Card card5 = new Card(Suit.SPADES, Rank.SIX);
        Card[] board = {card1, card2, card3, card4, card5};
        Card handCard1 = new Card(Suit.SPADES, Rank.QUEEN);
        Card handCard2 = new Card(Suit.HEARTS, Rank.EIGHT);

        boolean result = HandEvaluator.containsStraightFlush(board, handCard1, handCard2);
        assertTrue(result);
    }

    @Test
    public void testContainsStraightFlush2() {
        Card card1 = new Card(Suit.HEARTS, Rank.TWO);
        Card card2 = new Card(Suit.HEARTS, Rank.THREE);
        Card card3 = new Card(Suit.HEARTS, Rank.FOUR);
        Card card4 = new Card(Suit.HEARTS, Rank.FIVE);
        Card card5 = new Card(Suit.DIAMONDS, Rank.KING);
        Card[] board = {card1, card2, card3, card4, card5};
        Card handCard1 = new Card(Suit.HEARTS, Rank.ACE);
        Card handCard2 = new Card(Suit.CLUBS, Rank.EIGHT);

        boolean result = HandEvaluator.containsStraightFlush(board, handCard1, handCard2);
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
        Card[] board = {card1, card2, card3, card4, card5};
        Card handCard1 = new Card(Suit.SPADES, Rank.TWO);
        Card handCard2 = new Card(Suit.HEARTS, Rank.THREE);

        boolean result = HandEvaluator.containsStraightFlush(board, handCard1, handCard2);
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
        Card[] board = {card1, card2, card3, card4, card5};
        Card handCard1 = new Card(Suit.DIAMONDS, Rank.NINE);
        Card handCard2 = new Card(Suit.CLUBS, Rank.TEN);

        boolean result = HandEvaluator.containsStraightFlush(board, handCard1, handCard2);
        assertFalse(result);
    }



    @Test
    public void testContainsFourOfAKind1() {
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.CLUBS, Rank.TWO);
        Card card3 = new Card(Suit.HEARTS, Rank.TWO);
        Card card4 = new Card(Suit.DIAMONDS, Rank.TWO);
        Card card5 = new Card(Suit.SPADES, Rank.THREE);
        Card[] board = {card1, card2, card3, card4, card5};
        Card handCard1 = new Card(Suit.HEARTS, Rank.KING);
        Card handCard2 = new Card(Suit.HEARTS, Rank.QUEEN);

        boolean result = HandEvaluator.containsFourOfAKind(board, handCard1, handCard2);
        assertTrue(result);
    }

    @Test
    public void testContainsFourOfAKind2() {
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.CLUBS, Rank.FOUR);
        Card card3 = new Card(Suit.HEARTS, Rank.FIVE);
        Card card4 = new Card(Suit.DIAMONDS, Rank.SEVEN);
        Card card5 = new Card(Suit.SPADES, Rank.THREE);
        Card[] board = {card1, card2, card3, card4, card5};
        Card handCard1 = new Card(Suit.HEARTS, Rank.ACE);
        Card handCard2 = new Card(Suit.HEARTS, Rank.KING);

        boolean result = HandEvaluator.containsFourOfAKind(board, handCard1, handCard2);
        assertFalse(result);
    }

    @Test
    public void testContainsFourOfAKind3() {
        // Board contains one pair, hand contains one pair of the same rank
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.CLUBS, Rank.TWO);
        Card card3 = new Card(Suit.HEARTS, Rank.FOUR);
        Card card4 = new Card(Suit.DIAMONDS, Rank.FOUR);
        Card card5 = new Card(Suit.SPADES, Rank.THREE);
        Card[] board = {card1, card2, card3, card4, card5};
        Card handCard1 = new Card(Suit.HEARTS, Rank.TWO);
        Card handCard2 = new Card(Suit.DIAMONDS, Rank.TWO);

        boolean result = HandEvaluator.containsFourOfAKind(board, handCard1, handCard2);
        assertTrue(result);
    }




    @Test
    public void testContainsFullHouse1() {
        // Board contains three of a kind and a pair
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.CLUBS, Rank.TWO);
        Card card3 = new Card(Suit.HEARTS, Rank.TWO);
        Card card4 = new Card(Suit.DIAMONDS, Rank.THREE);
        Card card5 = new Card(Suit.SPADES, Rank.THREE);
        Card[] board = {card1, card2, card3, card4, card5};
        Card handCard1 = new Card(Suit.HEARTS, Rank.FOUR);
        Card handCard2 = new Card(Suit.HEARTS, Rank.FIVE);

        boolean result = HandEvaluator.containsFullHouse(board, handCard1, handCard2);
        assertTrue(result);
    }

    @Test
    public void testContainsFullHouse2() {
        // Board contains two pairs of different ranks
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.CLUBS, Rank.TWO);
        Card card3 = new Card(Suit.HEARTS, Rank.THREE);
        Card card4 = new Card(Suit.DIAMONDS, Rank.THREE);
        Card card5 = new Card(Suit.SPADES, Rank.FOUR);
        Card[] board = {card1, card2, card3, card4, card5};
        Card handCard1 = new Card(Suit.HEARTS, Rank.FIVE);
        Card handCard2 = new Card(Suit.HEARTS, Rank.SIX);

        boolean result = HandEvaluator.containsFullHouse(board, handCard1, handCard2);
        assertFalse(result);
    }

    @Test
    public void testContainsFullHouse3() {
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.CLUBS, Rank.TWO);
        Card card3 = new Card(Suit.HEARTS, Rank.TWO);
        Card card4 = new Card(Suit.DIAMONDS, Rank.THREE);
        Card card5 = new Card(Suit.SPADES, Rank.FOUR);
        Card[] board = {card1, card2, card3, card4, card5};
        Card handCard1 = new Card(Suit.HEARTS, Rank.THREE);
        Card handCard2 = new Card(Suit.HEARTS, Rank.FOUR);

        boolean result = HandEvaluator.containsFullHouse(board, handCard1, handCard2);
        assertTrue(result);
    }

    @Test
    public void testContainsFullHouse4() {
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.CLUBS, Rank.TWO);
        Card card3 = new Card(Suit.HEARTS, Rank.THREE);
        Card card4 = new Card(Suit.DIAMONDS, Rank.FOUR);
        Card card5 = new Card(Suit.SPADES, Rank.FIVE);
        Card[] board = {card1, card2, card3, card4, card5};
        Card handCard1 = new Card(Suit.HEARTS, Rank.SIX);
        Card handCard2 = new Card(Suit.HEARTS, Rank.SEVEN);

        boolean result = HandEvaluator.containsFullHouse(board, handCard1, handCard2);
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
        Card[] board = {card1, card2, card3, card4, card5};
        Card handCard1 = new Card(Suit.HEARTS, Rank.THREE);
        Card handCard2 = new Card(Suit.HEARTS, Rank.THREE);

        boolean result = HandEvaluator.containsFullHouse(board, handCard1, handCard2);
        assertTrue(result);
    }


    @Test
    public void testContainsFlush1() {
        // Board contains a flush
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.SPADES, Rank.FOUR);
        Card card3 = new Card(Suit.SPADES, Rank.SIX);
        Card card4 = new Card(Suit.SPADES, Rank.EIGHT);
        Card card5 = new Card(Suit.SPADES, Rank.TEN);
        Card[] board = {card1, card2, card3, card4, card5};
        Card handCard1 = new Card(Suit.HEARTS, Rank.ACE);
        Card handCard2 = new Card(Suit.DIAMONDS, Rank.KING);

        boolean result = HandEvaluator.containsFlush(board, handCard1, handCard2);
        assertTrue(result);
    }

    @Test
    public void testContainsFlush2() {
        // No flush on the board
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.CLUBS, Rank.FOUR);
        Card card3 = new Card(Suit.HEARTS, Rank.SIX);
        Card card4 = new Card(Suit.DIAMONDS, Rank.EIGHT);
        Card card5 = new Card(Suit.SPADES, Rank.TEN);
        Card[] board = {card1, card2, card3, card4, card5};
        Card handCard1 = new Card(Suit.HEARTS, Rank.ACE);
        Card handCard2 = new Card(Suit.DIAMONDS, Rank.KING);

        boolean result = HandEvaluator.containsFlush(board, handCard1, handCard2);
        assertFalse(result);
    }

    @Test
    public void testContainsFlush3() {
        // Board doesn't contain a flush, but with player's hand it does
        Card card1 = new Card(Suit.HEARTS, Rank.TWO);
        Card card2 = new Card(Suit.CLUBS, Rank.FOUR);
        Card card3 = new Card(Suit.HEARTS, Rank.SIX);
        Card card4 = new Card(Suit.HEARTS, Rank.EIGHT);
        Card card5 = new Card(Suit.SPADES, Rank.TEN);
        Card[] board = {card1, card2, card3, card4, card5};
        Card handCard1 = new Card(Suit.HEARTS, Rank.ACE);
        Card handCard2 = new Card(Suit.HEARTS, Rank.FOUR);

        boolean result = HandEvaluator.containsFlush(board, handCard1, handCard2);
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
        Card handCard1 = new Card(Suit.HEARTS, Rank.KING);
        Card handCard2 = new Card(Suit.HEARTS, Rank.ACE);
        Card[] board = {card1, card2, card3, card4, card5};

        boolean result = HandEvaluator.containsStraight(board, handCard1, handCard2);
        assertTrue(result);
    }

    @Test
    public void testContainsStraight2() {
        // No straight on the board, but player's hand forms a straight
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.DIAMONDS, Rank.THREE);
        Card card3 = new Card(Suit.CLUBS, Rank.FOUR);
        Card card4 = new Card(Suit.HEARTS, Rank.FIVE);
        Card card5 = new Card(Suit.SPADES, Rank.ACE);
        Card handCard1 = new Card(Suit.HEARTS, Rank.SIX);
        Card handCard2 = new Card(Suit.HEARTS, Rank.SEVEN);
        Card[] board = {card1, card2, card3, card4, card5};

        boolean result = HandEvaluator.containsStraight(board, handCard1, handCard2);
        assertTrue(result);
    }

    @Test
    public void testContainsStraight3() {
        // Board doesn't contain a straight, neither does player's hand
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.DIAMONDS, Rank.THREE);
        Card card3 = new Card(Suit.CLUBS, Rank.FOUR);
        Card card4 = new Card(Suit.HEARTS, Rank.SEVEN);
        Card card5 = new Card(Suit.SPADES, Rank.TEN);
        Card handCard1 = new Card(Suit.HEARTS, Rank.QUEEN);
        Card handCard2 = new Card(Suit.HEARTS, Rank.KING);
        Card[] board = {card1, card2, card3, card4, card5};

        boolean result = HandEvaluator.containsStraight(board, handCard1, handCard2);
        assertFalse(result);
    }

    @Test
    public void testContainsThreeOfAKind1() {
        // Board contains three of a kind
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.DIAMONDS, Rank.TWO);
        Card card3 = new Card(Suit.CLUBS, Rank.TWO);
        Card card4 = new Card(Suit.HEARTS, Rank.THREE);
        Card card5 = new Card(Suit.SPADES, Rank.SIX);
        Card handCard1 = new Card(Suit.HEARTS, Rank.FIVE);
        Card handCard2 = new Card(Suit.HEARTS, Rank.KING);
        Card[] board = {card1, card2, card3, card4, card5};

        boolean result = HandEvaluator.containsThreeOfAKind(board, handCard1, handCard2);
        assertTrue(result);
    }

    @Test
    public void testContainsThreeOfAKind2() {
        // No three of a kind on the board, but player's hand contains three of a kind
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.DIAMONDS, Rank.THREE);
        Card card3 = new Card(Suit.CLUBS, Rank.KING);
        Card card4 = new Card(Suit.HEARTS, Rank.FIVE);
        Card card5 = new Card(Suit.SPADES, Rank.SIX);
        Card handCard1 = new Card(Suit.DIAMONDS, Rank.KING);
        Card handCard2 = new Card(Suit.HEARTS, Rank.KING);
        Card[] board = {card1, card2, card3, card4, card5};

        boolean result = HandEvaluator.containsThreeOfAKind(board, handCard1, handCard2);
        assertTrue(result);
    }

    @Test
    public void testContainsThreeOfAKind3() {
        // Board doesn't contain three of a kind, neither does player's hand
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.DIAMONDS, Rank.THREE);
        Card card3 = new Card(Suit.CLUBS, Rank.FOUR);
        Card card4 = new Card(Suit.HEARTS, Rank.FIVE);
        Card card5 = new Card(Suit.SPADES, Rank.SIX);
        Card handCard1 = new Card(Suit.HEARTS, Rank.SEVEN);
        Card handCard2 = new Card(Suit.HEARTS, Rank.EIGHT);
        Card[] board = {card1, card2, card3, card4, card5};

        boolean result = HandEvaluator.containsThreeOfAKind(board, handCard1, handCard2);
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
        Card handCard1 = new Card(Suit.HEARTS, Rank.FIVE);
        Card handCard2 = new Card(Suit.HEARTS, Rank.TWO);
        Card[] board = {card1, card2, card3, card4, card5};

        boolean result = HandEvaluator.containsThreeOfAKind(board, handCard1, handCard2);
        assertTrue(result);
    }


    @Test
    public void testContainsTwoPairs1() {
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.DIAMONDS, Rank.TWO);
        Card card3 = new Card(Suit.HEARTS, Rank.THREE);
        Card card4 = new Card(Suit.CLUBS, Rank.THREE);
        Card card5 = new Card(Suit.SPADES, Rank.FOUR);
        Card handCard1 = new Card(Suit.HEARTS, Rank.FIVE);
        Card handCard2 = new Card(Suit.HEARTS, Rank.KING);
        Card[] board = {card1, card2, card3, card4, card5};

        boolean result = HandEvaluator.containsTwoPairs(board, handCard1, handCard2);
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
        Card handCard1 = new Card(Suit.HEARTS, Rank.THREE);
        Card handCard2 = new Card(Suit.HEARTS, Rank.KING);
        Card[] board = {card1, card2, card3, card4, card5};

        boolean result = HandEvaluator.containsTwoPairs(board, handCard1, handCard2);
        assertTrue(result);
    }

    @Test
    public void testContainsTwoPairs3() {
        // Two pairs with one pair in hand and one on the board
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.DIAMONDS, Rank.THREE);
        Card card3 = new Card(Suit.HEARTS, Rank.THREE);
        Card card4 = new Card(Suit.CLUBS, Rank.FIVE);
        Card card5 = new Card(Suit.SPADES, Rank.FOUR);
        Card handCard1 = new Card(Suit.HEARTS, Rank.TWO);
        Card handCard2 = new Card(Suit.HEARTS, Rank.KING);
        Card[] board = {card1, card2, card3, card4, card5};

        boolean result = HandEvaluator.containsTwoPairs(board, handCard1, handCard2);
        assertTrue(result);
    }

    @Test
    public void testContainsTwoPairs4() {
        // No two pairs
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.DIAMONDS, Rank.THREE);
        Card card3 = new Card(Suit.HEARTS, Rank.FOUR);
        Card card4 = new Card(Suit.CLUBS, Rank.FIVE);
        Card card5 = new Card(Suit.SPADES, Rank.FIVE);
        Card handCard1 = new Card(Suit.HEARTS, Rank.SEVEN);
        Card handCard2 = new Card(Suit.HEARTS, Rank.EIGHT);
        Card[] board = {card1, card2, card3, card4, card5};

        boolean result = HandEvaluator.containsTwoPairs(board, handCard1, handCard2);
        assertFalse(result);
    }


    @Test
    public void testContainsPair1() {
        // Pair in the board
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.DIAMONDS, Rank.THREE);
        Card card3 = new Card(Suit.HEARTS, Rank.FOUR);
        Card card4 = new Card(Suit.CLUBS, Rank.FOUR);
        Card card5 = new Card(Suit.SPADES, Rank.SIX);
        Card handCard1 = new Card(Suit.HEARTS, Rank.SEVEN);
        Card handCard2 = new Card(Suit.HEARTS, Rank.EIGHT);
        Card[] board = {card1, card2, card3, card4, card5};

        boolean result = HandEvaluator.containsPair(board, handCard1, handCard2);
        assertTrue(result);
    }

    @Test
    public void testContainsPair2() {
        // 2 pairs
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.DIAMONDS, Rank.THREE);
        Card card3 = new Card(Suit.HEARTS, Rank.FOUR);
        Card card4 = new Card(Suit.CLUBS, Rank.FIVE);
        Card card5 = new Card(Suit.SPADES, Rank.SIX);
        Card handCard1 = new Card(Suit.HEARTS, Rank.FOUR);
        Card handCard2 = new Card(Suit.HEARTS, Rank.FIVE);
        Card[] board = {card1, card2, card3, card4, card5};

        boolean result = HandEvaluator.containsPair(board, handCard1, handCard2);
        assertTrue(result);
    }

    @Test
    public void testContainsPair3() {
        // No pair
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.DIAMONDS, Rank.THREE);
        Card card3 = new Card(Suit.HEARTS, Rank.FOUR);
        Card card4 = new Card(Suit.CLUBS, Rank.FIVE);
        Card card5 = new Card(Suit.SPADES, Rank.SIX);
        Card handCard1 = new Card(Suit.HEARTS, Rank.SEVEN);
        Card handCard2 = new Card(Suit.HEARTS, Rank.EIGHT);
        Card[] board = {card1, card2, card3, card4, card5};

        boolean result = HandEvaluator.containsPair(board, handCard1, handCard2);
        assertFalse(result);
    }



}

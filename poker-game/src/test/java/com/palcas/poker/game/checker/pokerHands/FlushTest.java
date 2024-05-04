package com.palcas.poker.game.checker.pokerHands;

import com.palcas.poker.game.model.Rank;
import com.palcas.poker.game.model.Suit;
import com.palcas.poker.game.Card;
import com.palcas.poker.game.evaluation.CardsStatisticsService;
import com.palcas.poker.game.evaluation.pokerHands.Flush;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FlushTest {
    private static Flush flush;

    @BeforeAll
    public static void setUp() {
        flush = new Flush(new CardsStatisticsService());
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

        boolean result = flush.containsFlush(cards);
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

        boolean result = flush.containsFlush(cards);
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

        boolean result = flush.containsFlush(cards);
        assertTrue(result);
    }

    @Test
    public void testSelectHandForFlush1() {
        Card twoOfSpades = new Card(Suit.SPADES, Rank.TWO);
        Card fourOfSpades = new Card(Suit.SPADES, Rank.FOUR);
        Card sixOfSpades = new Card(Suit.SPADES, Rank.SIX);
        Card eightOfSpades = new Card(Suit.SPADES, Rank.EIGHT);
        Card tenOfSpades = new Card(Suit.SPADES, Rank.TEN);
        Card aceOfHearts = new Card(Suit.HEARTS, Rank.ACE);
        Card kingOfDiamonds = new Card(Suit.DIAMONDS, Rank.KING);
        Card[] cards = {fourOfSpades, sixOfSpades, tenOfSpades, aceOfHearts, kingOfDiamonds, twoOfSpades, eightOfSpades};

        Card[] selectedCards = flush.selectHandForFlush(cards);
        assertEquals(selectedCards.length, 5);
        assertEquals(selectedCards[0], tenOfSpades);
        assertEquals(selectedCards[1], eightOfSpades);
        assertEquals(selectedCards[2], sixOfSpades);
        assertEquals(selectedCards[3], fourOfSpades);
        assertEquals(selectedCards[4], twoOfSpades);
    }

    @Test
    public void testSelectHandForFlush2() {
        //should return null since it doesn't have a Flush
        Card twoOfSpades = new Card(Suit.SPADES, Rank.TWO);
        Card fourOfSpades = new Card(Suit.SPADES, Rank.FOUR);
        Card sixOfSpades = new Card(Suit.SPADES, Rank.SIX);
        Card eightOfSpades = new Card(Suit.CLUBS, Rank.EIGHT);
        Card tenOfSpades = new Card(Suit.SPADES, Rank.TEN);
        Card aceOfHearts = new Card(Suit.HEARTS, Rank.ACE);
        Card kingOfDiamonds = new Card(Suit.DIAMONDS, Rank.KING);
        Card[] cards = {fourOfSpades, sixOfSpades, tenOfSpades, aceOfHearts, kingOfDiamonds, twoOfSpades, eightOfSpades};

        Card[] selectedCards = flush.selectHandForFlush(cards);
        assertNull(selectedCards);
    }

    @Test
    public void testCompareFlushHands_FirstHandBetter() {
        // First hand has higher-ranking cards
        Card jackOfSpades = new Card(Suit.SPADES, Rank.JACK);
        Card nineOfSpades = new Card(Suit.SPADES, Rank.NINE);
        Card sevenOfSpades = new Card(Suit.SPADES, Rank.SEVEN);
        Card fourOfSpades = new Card(Suit.SPADES, Rank.FOUR);
        Card twoOfSpades = new Card(Suit.SPADES, Rank.TWO);

        Card aceOfHearts = new Card(Suit.HEARTS, Rank.ACE);
        Card kingOfDiamonds = new Card(Suit.DIAMONDS, Rank.KING);
        Card[] setOf7CardsForHand1 = {twoOfSpades, fourOfSpades, sevenOfSpades, nineOfSpades, jackOfSpades, aceOfHearts, kingOfDiamonds};
        Card[] hand1 = flush.selectHandForFlush(setOf7CardsForHand1);

        // Second hand has lower-ranking cards
        Card jackOfHearts = new Card(Suit.HEARTS, Rank.JACK);
        Card nineOfHearts = new Card(Suit.HEARTS, Rank.NINE);
        Card sixOfHearts = new Card(Suit.HEARTS, Rank.SIX);
        Card fiveOfHearts = new Card(Suit.HEARTS, Rank.FIVE);
        Card threeOfHearts = new Card(Suit.HEARTS, Rank.THREE);

        Card queenOfDiamonds = new Card(Suit.DIAMONDS, Rank.QUEEN);
        Card kingOfClubs = new Card(Suit.CLUBS, Rank.KING);
        Card[] setOf7CardsForHand2 = {threeOfHearts, fiveOfHearts, sixOfHearts, nineOfHearts, jackOfHearts, queenOfDiamonds, kingOfClubs};
        Card[] hand2 = flush.selectHandForFlush(setOf7CardsForHand2);

        int result = flush.compareFlushHands(hand1, hand2);
        assertTrue(result > 0);
    }

    @Test
    public void testCompareFlushHands_EqualHands() {
        Card twoOfSpades = new Card(Suit.SPADES, Rank.TWO);
        Card fourOfSpades = new Card(Suit.SPADES, Rank.FOUR);
        Card sixOfSpades = new Card(Suit.SPADES, Rank.SIX);
        Card eightOfSpades = new Card(Suit.SPADES, Rank.EIGHT);
        Card tenOfSpades = new Card(Suit.SPADES, Rank.TEN);
        Card aceOfHearts = new Card(Suit.HEARTS, Rank.ACE);
        Card kingOfDiamonds = new Card(Suit.DIAMONDS, Rank.KING);
        Card[] setOf7CardsForHand1 = {twoOfSpades, fourOfSpades, sixOfSpades, eightOfSpades, tenOfSpades, aceOfHearts, kingOfDiamonds};
        Card[] hand1 = flush.selectHandForFlush(setOf7CardsForHand1);

        Card twoOfHearts = new Card(Suit.HEARTS, Rank.TWO);
        Card fourOfHearts = new Card(Suit.HEARTS, Rank.FOUR);
        Card sixOfHearts = new Card(Suit.HEARTS, Rank.SIX);
        Card eightOfHearts = new Card(Suit.HEARTS, Rank.EIGHT);
        Card tenOfHearts = new Card(Suit.HEARTS, Rank.TEN);
        Card aceOfDiamonds = new Card(Suit.DIAMONDS, Rank.ACE);
        Card kingOfClubs = new Card(Suit.CLUBS, Rank.KING);
        Card[] setOf7CardsForHand2 = {twoOfHearts, fourOfHearts, sixOfHearts, eightOfHearts, tenOfHearts, aceOfDiamonds, kingOfClubs};
        Card[] hand2 = flush.selectHandForFlush(setOf7CardsForHand2);

        int result = flush.compareFlushHands(hand1, hand2);
        assertEquals(0, result);
    }

    @Test
    public void testCompareFlushHands_SecondHandBetter() {
        // First hand has lower-ranking cards
        Card jackOfClubs = new Card(Suit.CLUBS, Rank.JACK);
        Card nineOfClubs = new Card(Suit.CLUBS, Rank.NINE);
        Card sevenOfClubs = new Card(Suit.CLUBS, Rank.SEVEN);
        Card fourOfClubs = new Card(Suit.CLUBS, Rank.FOUR);
        Card twoOfClubs = new Card(Suit.CLUBS, Rank.TWO);

        Card aceOfDiamonds = new Card(Suit.DIAMONDS, Rank.ACE);
        Card kingOfSpades = new Card(Suit.SPADES, Rank.KING);
        Card[] setOf7CardsForHand1 = {twoOfClubs, fourOfClubs, sevenOfClubs, nineOfClubs, jackOfClubs, aceOfDiamonds, kingOfSpades};
        Card[] hand1 = flush.selectHandForFlush(setOf7CardsForHand1);

        // Second hand has higher-ranking cards
        Card jackOfHearts = new Card(Suit.HEARTS, Rank.JACK);
        Card tenOfHearts = new Card(Suit.HEARTS, Rank.TEN);
        Card sixOfHearts = new Card(Suit.HEARTS, Rank.SIX);
        Card fiveOfHearts = new Card(Suit.HEARTS, Rank.FIVE);
        Card threeOfHearts = new Card(Suit.HEARTS, Rank.THREE);

        Card queenOfDiamonds = new Card(Suit.DIAMONDS, Rank.QUEEN);
        Card kingOfClubs = new Card(Suit.CLUBS, Rank.KING);
        Card[] setOf7CardsForHand2 = {threeOfHearts, fiveOfHearts, sixOfHearts, tenOfHearts, jackOfHearts, queenOfDiamonds, kingOfClubs};
        Card[] hand2 = flush.selectHandForFlush(setOf7CardsForHand2);

        int result = flush.compareFlushHands(hand1, hand2);
        assertTrue(result < 0);
    }


}

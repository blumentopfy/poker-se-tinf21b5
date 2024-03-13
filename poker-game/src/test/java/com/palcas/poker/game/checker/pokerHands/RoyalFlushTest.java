package com.palcas.poker.game.checker.pokerHands;

import com.palcas.poker.model.Rank;
import com.palcas.poker.model.Suit;
import com.palcas.poker.game.Card;
import com.palcas.poker.game.checker.CardsStatisticsService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RoyalFlushTest {

    private static RoyalFlush royalFlush;

    @BeforeAll
    public static void setUp() {
        royalFlush = new RoyalFlush(new CardsStatisticsService());
    }


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

        boolean result = royalFlush.containsRoyalFlush(cards);
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

        boolean result = royalFlush.containsRoyalFlush(cards);
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

        boolean result = royalFlush.containsRoyalFlush(cards);
        assertFalse(result);
    }

    @Test
    public void testSelectHandForRoyalFlush1() {
        //should return Royal Flush in this order: 10, J, Q, K, A
        Card tenOfSpades = new Card(Suit.SPADES, Rank.TEN);
        Card jackOfSpades = new Card(Suit.SPADES, Rank.JACK);
        Card queenOfSpades = new Card(Suit.SPADES, Rank.QUEEN);
        Card kingOfSpades = new Card(Suit.SPADES, Rank.KING);
        Card aceOfSpades = new Card(Suit.SPADES, Rank.ACE);
        Card otherCard1 = new Card(Suit.HEARTS, Rank.QUEEN);
        Card otherCard2 = new Card(Suit.HEARTS, Rank.KING);
        Card[] cards = {jackOfSpades, tenOfSpades, queenOfSpades, otherCard1, aceOfSpades, kingOfSpades, otherCard2};

        Card[] selectedCards = royalFlush.selectHandForRoyalFlush(cards);
        assertEquals(5, selectedCards.length);
        assertEquals(selectedCards[0], tenOfSpades);
        assertEquals(selectedCards[1], jackOfSpades);
        assertEquals(selectedCards[2], queenOfSpades);
        assertEquals(selectedCards[3], kingOfSpades);
        assertEquals(selectedCards[4], aceOfSpades);
    }

    @Test
    public void testSelectHandForRoyalFlush2() {
        //doesn't contain Royal Flush, so should return null
        Card tenOfSpades = new Card(Suit.SPADES, Rank.TEN);
        Card jackOfSpades = new Card(Suit.SPADES, Rank.JACK);
        Card queenOfSpades = new Card(Suit.DIAMONDS, Rank.QUEEN);
        Card kingOfSpades = new Card(Suit.SPADES, Rank.KING);
        Card aceOfSpades = new Card(Suit.SPADES, Rank.ACE);
        Card otherCard1 = new Card(Suit.HEARTS, Rank.QUEEN);
        Card otherCard2 = new Card(Suit.CLUBS, Rank.KING);
        Card[] cards = {tenOfSpades, jackOfSpades, queenOfSpades, kingOfSpades, aceOfSpades, otherCard1, otherCard2};

        Card[] selectedCards = royalFlush.selectHandForRoyalFlush(cards);
        assertNull(selectedCards);
    }
}

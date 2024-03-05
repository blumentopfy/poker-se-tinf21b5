package com.palcas.poker.game.checker.pokerHands;

import com.palcas.poker.Rank;
import com.palcas.poker.Suit;
import com.palcas.poker.game.Card;
import com.palcas.poker.game.HandRankingChecker;
import com.palcas.poker.game.checker.CardsStatisticsService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
}

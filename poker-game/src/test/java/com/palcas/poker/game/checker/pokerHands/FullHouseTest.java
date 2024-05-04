package com.palcas.poker.game.checker.pokerHands;

import com.palcas.poker.game.model.Rank;
import com.palcas.poker.game.model.Suit;
import com.palcas.poker.game.Card;
import com.palcas.poker.game.evaluation.CardsStatisticsService;
import com.palcas.poker.game.evaluation.pokerHands.FullHouse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FullHouseTest {
    private static FullHouse fullHouse;

    @BeforeAll
    public static void setUp() {
        fullHouse = new FullHouse(new CardsStatisticsService());
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

        boolean result = fullHouse.containsFullHouse(cards);
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

        boolean result = fullHouse.containsFullHouse(cards);
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

        boolean result = fullHouse.containsFullHouse(cards);
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

        boolean result = fullHouse.containsFullHouse(cards);
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

        boolean result = fullHouse.containsFullHouse(cards);
        assertTrue(result);
    }

    @Test
    public void testSelectHandForFullHouse1() {
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.CLUBS, Rank.TWO);
        Card card3 = new Card(Suit.HEARTS, Rank.TWO);
        Card card4 = new Card(Suit.DIAMONDS, Rank.THREE);
        Card card5 = new Card(Suit.SPADES, Rank.THREE);
        Card card6 = new Card(Suit.HEARTS, Rank.FOUR);
        Card card7 = new Card(Suit.HEARTS, Rank.FIVE);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        Card[] selectedCards = fullHouse.selectHandForFullHouse(cards);
        assertEquals(5, selectedCards.length);
        assertEquals(selectedCards[0].getRank(), Rank.TWO);
        assertEquals(selectedCards[1].getRank(), Rank.TWO);
        assertEquals(selectedCards[2].getRank(), Rank.TWO);
        assertEquals(selectedCards[3].getRank(), Rank.THREE);
        assertEquals(selectedCards[4].getRank(), Rank.THREE);
    }

    @Test
    public void testSelectHandForFullHouse2() {
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.CLUBS, Rank.TWO);
        Card card3 = new Card(Suit.HEARTS, Rank.THREE);
        Card card4 = new Card(Suit.DIAMONDS, Rank.THREE);
        Card card5 = new Card(Suit.SPADES, Rank.FOUR);
        Card card6 = new Card(Suit.HEARTS, Rank.FIVE);
        Card card7 = new Card(Suit.HEARTS, Rank.SIX);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        Card[] selectedCards = fullHouse.selectHandForFullHouse(cards);
        assertEquals(null, selectedCards);
    }

    @Test
    public void testSelectHandForFullHouse3() {
        // 2 sets of 3 is also a full house, higher valued triplet is then the triplet in full house
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.CLUBS, Rank.TWO);
        Card card3 = new Card(Suit.HEARTS, Rank.TWO);
        Card card4 = new Card(Suit.DIAMONDS, Rank.THREE);
        Card card5 = new Card(Suit.SPADES, Rank.FOUR);
        Card card6 = new Card(Suit.HEARTS, Rank.THREE);
        Card card7 = new Card(Suit.HEARTS, Rank.THREE);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        Card[] selectedCards = fullHouse.selectHandForFullHouse(cards);
        assertEquals(5, selectedCards.length);
        assertEquals(selectedCards[0].getRank(), Rank.THREE);
        assertEquals(selectedCards[1].getRank(), Rank.THREE);
        assertEquals(selectedCards[2].getRank(), Rank.THREE);
        assertEquals(selectedCards[3].getRank(), Rank.TWO);
        assertEquals(selectedCards[4].getRank(), Rank.TWO);
    }
}

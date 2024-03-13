package com.palcas.poker.game.checker.pokerHands;

import com.palcas.poker.model.Rank;
import com.palcas.poker.model.Suit;
import com.palcas.poker.game.Card;
import com.palcas.poker.game.checker.CardsStatisticsService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TwoPairsTest {
    private static TwoPairs twoPairs;

    @BeforeAll
    public static void setUp() {
        twoPairs = new TwoPairs(new CardsStatisticsService());
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

        boolean result = twoPairs.containsTwoPairs(cards);
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

        boolean result = twoPairs.containsTwoPairs(cards);
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

        boolean result = twoPairs.containsTwoPairs(cards);
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

        boolean result = twoPairs.containsTwoPairs(cards);
        assertFalse(result);
    }

    @Test
    public void testSelectHandForTwoPairs1() {
        Card twoOfSpades = new Card(Suit.SPADES, Rank.TWO);
        Card twoOfDiamonds = new Card(Suit.DIAMONDS, Rank.TWO);
        Card threeOfHearts = new Card(Suit.HEARTS, Rank.THREE);
        Card threeOfClubs = new Card(Suit.CLUBS, Rank.THREE);
        Card fourOfSpades = new Card(Suit.SPADES, Rank.FOUR);
        Card fiveOfHearts = new Card(Suit.HEARTS, Rank.FIVE);
        Card kingOfHearts = new Card(Suit.HEARTS, Rank.KING);
        Card[] cards = {threeOfClubs, twoOfSpades, kingOfHearts, fourOfSpades, fiveOfHearts, threeOfHearts, twoOfDiamonds};

        Card[] selectedCards = twoPairs.selectHandForTwoPairs(cards);
        assertEquals(5, selectedCards.length);
        assertEquals(selectedCards[0].getRank(), Rank.THREE);
        assertEquals(selectedCards[1].getRank(), Rank.THREE);
        assertEquals(selectedCards[2].getRank(), Rank.TWO);
        assertEquals(selectedCards[3].getRank(), Rank.TWO);
        assertEquals(selectedCards[4], kingOfHearts);
    }

    @Test
    public void testSelectHandForTwoPairs2() {
        Card twoOfSpades = new Card(Suit.SPADES, Rank.TWO);
        Card twoOfDiamonds = new Card(Suit.DIAMONDS, Rank.TWO);
        Card threeOfHearts = new Card(Suit.HEARTS, Rank.THREE);
        Card threeOfClubs = new Card(Suit.CLUBS, Rank.THREE);
        Card fiveOfSpades = new Card(Suit.SPADES, Rank.FIVE);
        Card fiveOfHearts = new Card(Suit.HEARTS, Rank.FIVE);
        Card kingOfHearts = new Card(Suit.HEARTS, Rank.KING);
        Card[] cards = {threeOfClubs, twoOfSpades, kingOfHearts, fiveOfSpades, fiveOfHearts, threeOfHearts, twoOfDiamonds};

        Card[] selectedCards = twoPairs.selectHandForTwoPairs(cards);
        assertEquals(5, selectedCards.length);
        assertEquals(selectedCards[0].getRank(), Rank.FIVE);
        assertEquals(selectedCards[1].getRank(), Rank.FIVE);
        assertEquals(selectedCards[2].getRank(), Rank.THREE);
        assertEquals(selectedCards[3].getRank(), Rank.THREE);
        assertEquals(selectedCards[4], kingOfHearts);
    }

    @Test
    public void testSelectHandForTwoPairs3() {
        Card twoOfSpades = new Card(Suit.SPADES, Rank.TWO);
        Card aceOfDiamonds = new Card(Suit.DIAMONDS, Rank.ACE);
        Card threeOfHearts = new Card(Suit.HEARTS, Rank.THREE);
        Card threeOfClubs = new Card(Suit.CLUBS, Rank.THREE);
        Card fourOfSpades = new Card(Suit.SPADES, Rank.FOUR);
        Card fiveOfHearts = new Card(Suit.HEARTS, Rank.FIVE);
        Card kingOfHearts = new Card(Suit.HEARTS, Rank.KING);
        Card[] cards = {threeOfClubs, twoOfSpades, kingOfHearts, fourOfSpades, fiveOfHearts, threeOfHearts, aceOfDiamonds};

        Card[] selectedCards = twoPairs.selectHandForTwoPairs(cards);
        assertEquals(null, selectedCards);
    }
}

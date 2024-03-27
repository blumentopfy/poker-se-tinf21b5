package com.palcas.poker.game.checker.pokerHands;

import com.palcas.poker.model.Rank;
import com.palcas.poker.model.Suit;
import com.palcas.poker.game.Card;
import com.palcas.poker.game.evaluation.CardsStatisticsService;
import com.palcas.poker.game.evaluation.pokerHands.FourOfAKind;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FourOfAKindTest {

    private static FourOfAKind fourOfAKind;

    @BeforeAll
    public static void setUp() {
        fourOfAKind = new FourOfAKind(new CardsStatisticsService());
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

        boolean result = fourOfAKind.containsFourOfAKind(cards);
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

        boolean result = fourOfAKind.containsFourOfAKind(cards);
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

        boolean result = fourOfAKind.containsFourOfAKind(cards);
        assertTrue(result);
    }

    @Test
    public void testSelectHandForFourOfAKind1() {
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.CLUBS, Rank.TWO);
        Card card3 = new Card(Suit.HEARTS, Rank.TWO);
        Card card4 = new Card(Suit.DIAMONDS, Rank.TWO);
        Card card5 = new Card(Suit.SPADES, Rank.THREE);
        Card kingOfHearts = new Card(Suit.HEARTS, Rank.KING);
        Card card7 = new Card(Suit.HEARTS, Rank.QUEEN);
        Card[] cards = {card1, card2, card7, card4, card5, kingOfHearts, card3};

        Card[] selectedCards = fourOfAKind.selectHandForFourOfAKind(cards);
        assertEquals(selectedCards[0].getRank(), Rank.TWO);
        assertEquals(selectedCards[1].getRank(), Rank.TWO);
        assertEquals(selectedCards[2].getRank(), Rank.TWO);
        assertEquals(selectedCards[3].getRank(), Rank.TWO);
        assertEquals(selectedCards[4], kingOfHearts);
    }

    @Test
    public void testSelectHandForFourOfAKind2() {
        //should return null since it doesn't have 4 of a kind
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.CLUBS, Rank.TWO);
        Card card3 = new Card(Suit.HEARTS, Rank.EIGHT);
        Card card4 = new Card(Suit.DIAMONDS, Rank.TWO);
        Card card5 = new Card(Suit.SPADES, Rank.THREE);
        Card kingOfHearts = new Card(Suit.HEARTS, Rank.KING);
        Card card7 = new Card(Suit.HEARTS, Rank.QUEEN);
        Card[] cards = {card1, card2, card3, card4, card5, kingOfHearts, card7};

        Card[] selectedCards = fourOfAKind.selectHandForFourOfAKind(cards);
        assertEquals(selectedCards, null);
    }
}

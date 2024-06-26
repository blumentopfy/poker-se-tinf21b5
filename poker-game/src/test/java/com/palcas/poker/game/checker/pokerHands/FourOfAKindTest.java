package com.palcas.poker.game.checker.pokerHands;

import com.palcas.poker.game.model.Rank;
import com.palcas.poker.game.model.Suit;
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

    @Test
    public void testCompareFourOfAKindHands(){
        Card commoncard1 = new Card(Suit.DIAMONDS, Rank.KING);
        Card commoncard2 = new Card(Suit.HEARTS, Rank.KING);
        Card commoncard3 = new Card(Suit.CLUBS, Rank.KING);
        Card commoncard4 = new Card(Suit.SPADES, Rank.KING);
        Card commoncard5 = new Card(Suit.DIAMONDS, Rank.TWO);


        Card card1g = new Card(Suit.SPADES, Rank.ACE);
        Card card2g = new Card(Suit.CLUBS, Rank.SEVEN);
        Card[] cardsA = {commoncard1, commoncard2, commoncard3, commoncard4, commoncard5, card1g, card2g};
        Card[] handA = fourOfAKind.selectHandForFourOfAKind(cardsA);

        Card card1h = new Card(Suit.DIAMONDS, Rank.QUEEN);
        Card card2h = new Card(Suit.CLUBS, Rank.THREE);
        Card[] cardsB = {commoncard1, commoncard2, commoncard3, commoncard4, commoncard5, card1h, card2h};
        Card[] handB = fourOfAKind.selectHandForFourOfAKind(cardsB);

        assertEquals(1, fourOfAKind.compareFourOfAKindHands(handA, handB));
    }
}

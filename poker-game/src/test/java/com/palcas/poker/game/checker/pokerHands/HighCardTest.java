package com.palcas.poker.game.checker.pokerHands;

import com.palcas.poker.game.Card;
import com.palcas.poker.model.Rank;
import com.palcas.poker.model.Suit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HighCardTest {
    private static HighCard highCard;

    @BeforeAll
    public static void setUp() {
        highCard = new HighCard();
    }

    @Test
    public void testSelectHandForHighCard1() {
        Card twoOfSpades = new Card(Suit.SPADES, Rank.TWO);
        Card twoOfDiamonds = new Card(Suit.DIAMONDS, Rank.TWO);
        Card twoOfClubs = new Card(Suit.CLUBS, Rank.TWO);
        Card threeOfHearts = new Card(Suit.HEARTS, Rank.THREE);
        Card fiveOfHearts = new Card(Suit.HEARTS, Rank.FIVE);
        Card sixOfSpades = new Card(Suit.SPADES, Rank.SIX);
        Card kingOfHearts = new Card(Suit.HEARTS, Rank.KING);
        Card[] cards = {twoOfClubs, threeOfHearts, sixOfSpades, kingOfHearts, fiveOfHearts, twoOfSpades, twoOfDiamonds};

        Card[] selectedCards = highCard.selectHandForHighCard(cards);
        assertEquals(5, selectedCards.length);
        assertEquals(selectedCards[0], kingOfHearts);
        assertEquals(selectedCards[1], sixOfSpades);
        assertEquals(selectedCards[2], fiveOfHearts);
        assertEquals(selectedCards[3], threeOfHearts);
        assertEquals(selectedCards[4].getRank(), Rank.TWO);
    }
}

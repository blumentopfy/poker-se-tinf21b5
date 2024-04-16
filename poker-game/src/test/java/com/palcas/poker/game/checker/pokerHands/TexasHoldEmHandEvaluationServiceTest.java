package com.palcas.poker.game.checker.pokerHands;

import com.palcas.poker.game.Card;
import com.palcas.poker.game.HandRanking;
import com.palcas.poker.game.Player;
import com.palcas.poker.game.evaluation.HandEvaluationService;
import com.palcas.poker.game.evaluation.TexasHoldEmHandEvaluationService;
import com.palcas.poker.model.Rank;
import com.palcas.poker.model.Suit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TexasHoldEmHandEvaluationServiceTest {

    private static HandEvaluationService handCheckerService;

    @BeforeAll
    public static void setUp() {
        handCheckerService = new TexasHoldEmHandEvaluationService();
    }

    @Test
    public void testFullHouse1() {
        Card card1a = new Card(Suit.SPADES, Rank.TWO);
        Card card2a = new Card(Suit.CLUBS, Rank.TWO);
        Card card3a = new Card(Suit.HEARTS, Rank.TWO);
        Card card4a = new Card(Suit.DIAMONDS, Rank.THREE);
        Card card5a = new Card(Suit.SPADES, Rank.THREE);
        Card card6a = new Card(Suit.HEARTS, Rank.FOUR);
        Card card7a = new Card(Suit.HEARTS, Rank.FIVE);
        Card[] cardsa = {card1a, card2a, card3a, card4a, card5a, card6a, card7a};

        Card card1b = new Card(Suit.SPADES, Rank.TWO);
        Card card2b = new Card(Suit.CLUBS, Rank.TWO);
        Card card3b = new Card(Suit.HEARTS, Rank.TWO);
        Card card4b = new Card(Suit.SPADES, Rank.THREE);
        Card card5b = new Card(Suit.DIAMONDS, Rank.FOUR);
        Card card6b = new Card(Suit.HEARTS, Rank.FOUR);
        Card card7b = new Card(Suit.HEARTS, Rank.FIVE);
        Card[] cardsb = {card1b, card2b, card3b, card4b, card5b, card6b, card7b};


        Card[] selectedCardsA = handCheckerService.select(cardsa);
        Card[] selectedCardsB = handCheckerService.select(cardsb);
        int AgreaterB = handCheckerService.compareHandsOfSameHandRanking(selectedCardsA, selectedCardsB);


        assertEquals(HandRanking.FULL_HOUSE, handCheckerService.check(cardsa));
        assertEquals(5, selectedCardsA.length);
        assertEquals(selectedCardsA[0].getRank(), Rank.TWO);
        assertEquals(selectedCardsA[1].getRank(), Rank.TWO);
        assertEquals(selectedCardsA[2].getRank(), Rank.TWO);
        assertEquals(selectedCardsA[3].getRank(), Rank.THREE);
        assertEquals(selectedCardsA[4].getRank(), Rank.THREE);

        assertEquals(HandRanking.FULL_HOUSE, handCheckerService.check(cardsb));
        assertEquals(5, selectedCardsB.length);
        assertEquals(selectedCardsB[0].getRank(), Rank.TWO);
        assertEquals(selectedCardsB[1].getRank(), Rank.TWO);
        assertEquals(selectedCardsB[2].getRank(), Rank.TWO);
        assertEquals(selectedCardsB[3].getRank(), Rank.FOUR);
        assertEquals(selectedCardsB[4].getRank(), Rank.FOUR);

        assertEquals(AgreaterB, -1);
    }

    @Test
    public void testDetermineWinner1() {
        // Player alice with Full House
        Player alice = new Player("Alice", 2000);
        Card card1a = new Card(Suit.SPADES, Rank.TWO);
        Card card2a = new Card(Suit.CLUBS, Rank.TWO);
        Card card3a = new Card(Suit.HEARTS, Rank.TWO);
        Card card4a = new Card(Suit.DIAMONDS, Rank.THREE);
        Card card5a = new Card(Suit.SPADES, Rank.THREE);
        Card card6a = new Card(Suit.HEARTS, Rank.FOUR);
        Card card7a = new Card(Suit.HEARTS, Rank.FIVE);
        Card[] cardsa = {card1a, card2a, card3a, card4a, card5a, card6a, card7a};

        // Player bob with a High Card
        Player bob = new Player("Bob", 2000);
        Card card1b = new Card(Suit.SPADES, Rank.ACE);
        Card card2b = new Card(Suit.CLUBS, Rank.KING);
        Card card3b = new Card(Suit.HEARTS, Rank.TWO);
        Card card4b = new Card(Suit.DIAMONDS, Rank.JACK);
        Card card5b = new Card(Suit.SPADES, Rank.TEN);
        Card card6b = new Card(Suit.HEARTS, Rank.EIGHT);
        Card card7b = new Card(Suit.HEARTS, Rank.SEVEN);
        Card[] cardsb = {card1b, card2b, card3b, card4b, card5b, card6b, card7b};

        HashMap<Player, Card[]> playerCardMap = new HashMap<>();
        playerCardMap.put(alice, cardsa);
        playerCardMap.put(bob, cardsb);


        Player[] winners = handCheckerService.determineWinner(playerCardMap);


        assertEquals(HandRanking.FULL_HOUSE, handCheckerService.check(cardsa));
        assertEquals(HandRanking.HIGH_CARD, handCheckerService.check(cardsb));
        assertEquals(1, winners.length);
        assertEquals(alice, winners[0]);
    }

}

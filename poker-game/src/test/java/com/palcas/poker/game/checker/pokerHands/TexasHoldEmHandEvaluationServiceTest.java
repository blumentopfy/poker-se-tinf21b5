package com.palcas.poker.game.checker.pokerHands;

import com.palcas.poker.game.Card;
import com.palcas.poker.game.Player;
import com.palcas.poker.game.evaluation.HandEvaluationService;
import com.palcas.poker.game.evaluation.HandRanking;
import com.palcas.poker.game.evaluation.TexasHoldEmHandEvaluationService;
import com.palcas.poker.game.model.Rank;
import com.palcas.poker.game.model.Suit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
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
    public void compareTwoSameHandsEvenThoughPocketsAreDifferent(){
        Card commoncard1 = new Card(Suit.DIAMONDS, Rank.KING);
        Card commoncard2 = new Card(Suit.HEARTS, Rank.KING);
        Card commoncard3 = new Card(Suit.CLUBS, Rank.KING);
        Card commoncard4 = new Card(Suit.SPADES, Rank.KING);
        Card commoncard5 = new Card(Suit.DIAMONDS, Rank.TWO);


        Card card1g = new Card(Suit.SPADES, Rank.ACE);
        Card card2g = new Card(Suit.CLUBS, Rank.SEVEN);
        Card[] cardsA = {commoncard1, commoncard2, commoncard3, commoncard4, commoncard5, card1g, card2g};

        Card card1h = new Card(Suit.DIAMONDS, Rank.ACE);
        Card card2h = new Card(Suit.CLUBS, Rank.THREE);
        Card[] cardsB = {commoncard1, commoncard2, commoncard3, commoncard4, commoncard5, card1h, card2h};


        assertEquals(0, handCheckerService.compare(cardsA, cardsB));
    }

    @Test
    public void compareTwoSlightlyDifferentHands(){
        Card commoncard1 = new Card(Suit.DIAMONDS, Rank.KING);
        Card commoncard2 = new Card(Suit.HEARTS, Rank.KING);
        Card commoncard3 = new Card(Suit.CLUBS, Rank.KING);
        Card commoncard4 = new Card(Suit.SPADES, Rank.KING);
        Card commoncard5 = new Card(Suit.DIAMONDS, Rank.TWO);


        Card card1g = new Card(Suit.SPADES, Rank.ACE);
        Card card2g = new Card(Suit.CLUBS, Rank.SEVEN);
        Card[] cardsA = {commoncard1, commoncard2, commoncard3, commoncard4, commoncard5, card1g, card2g};

        Card card1h = new Card(Suit.DIAMONDS, Rank.KING);
        Card card2h = new Card(Suit.CLUBS, Rank.THREE);
        Card[] cardsB = {commoncard1, commoncard2, commoncard3, commoncard4, commoncard5, card1h, card2h};

        assertEquals(1, handCheckerService.compare(cardsA, cardsB));
    }

    @Test
    public void compareTwoDifferentHands(){
        Card commoncard1 = new Card(Suit.DIAMONDS, Rank.KING);
        Card commoncard2 = new Card(Suit.HEARTS, Rank.KING);
        Card commoncard3 = new Card(Suit.CLUBS, Rank.KING);
        Card commoncard4 = new Card(Suit.SPADES, Rank.KING);
        Card commoncard5 = new Card(Suit.DIAMONDS, Rank.TWO);


        Card card1g = new Card(Suit.SPADES, Rank.ACE);
        Card card2g = new Card(Suit.CLUBS, Rank.SEVEN);
        Card[] cardsA = {commoncard1, commoncard2, commoncard3, commoncard4, commoncard5, card1g, card2g};

        Card card1h = new Card(Suit.DIAMONDS, Rank.QUEEN);
        Card card2h = new Card(Suit.CLUBS, Rank.THREE);
        Card[] cardsB = {commoncard1, commoncard2, commoncard3, commoncard4, commoncard5, card1h, card2h};

        assertEquals(1, handCheckerService.compare(cardsA, cardsB));
    }



    @Test
    public void compareTwoFullHouses() {
        Card communityCard1 = new Card(Suit.SPADES, Rank.TWO);
        Card communityCard2 = new Card(Suit.CLUBS, Rank.TWO);
        Card communityCard3 = new Card(Suit.HEARTS, Rank.TWO);
        Card communityCard4 = new Card(Suit.DIAMONDS, Rank.THREE);
        Card communityCard5 = new Card(Suit.DIAMONDS, Rank.FOUR);


        Card pocketCard1a = new Card(Suit.SPADES, Rank.THREE);
        Card pocketCard2a = new Card(Suit.HEARTS, Rank.FIVE);
        Card[] cardsa = {communityCard1, communityCard2, communityCard3, communityCard4, communityCard5, pocketCard1a, pocketCard2a};

        Card pocketCard1b = new Card(Suit.HEARTS, Rank.FOUR);
        Card pocketCard2b = new Card(Suit.HEARTS, Rank.FIVE);
        Card[] cardsb = {communityCard1, communityCard2, communityCard3, communityCard4, communityCard5, pocketCard1b, pocketCard2b};


        Card[] selectedCardsA = handCheckerService.select(cardsa);
        Card[] selectedCardsB = handCheckerService.select(cardsb);
        int AgreaterB = handCheckerService.compare(selectedCardsA, selectedCardsB);


        assertEquals(HandRanking.FULL_HOUSE, handCheckerService.evaluate(cardsa));
        assertEquals(5, selectedCardsA.length);
        assertEquals(selectedCardsA[0].getRank(), Rank.TWO);
        assertEquals(selectedCardsA[1].getRank(), Rank.TWO);
        assertEquals(selectedCardsA[2].getRank(), Rank.TWO);
        assertEquals(selectedCardsA[3].getRank(), Rank.THREE);
        assertEquals(selectedCardsA[4].getRank(), Rank.THREE);

        assertEquals(HandRanking.FULL_HOUSE, handCheckerService.evaluate(cardsb));
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

        // Player charlie with a Flush
        Player charlie = new Player("Charlie", 2000);
        Card card1c = new Card(Suit.HEARTS, Rank.SIX);
        Card card2c = new Card(Suit.HEARTS, Rank.EIGHT);
        Card card3c = new Card(Suit.HEARTS, Rank.TEN);
        Card card4c = new Card(Suit.HEARTS, Rank.JACK);
        Card card5c = new Card(Suit.HEARTS, Rank.QUEEN);
        Card card6c = new Card(Suit.HEARTS, Rank.THREE);
        Card card7c = new Card(Suit.HEARTS, Rank.ACE);
        Card[] cardsc = {card1c, card2c, card3c, card4c, card5c, card6c, card7c};

        HashMap<Player, Card[]> playerCardMap = new HashMap<>();
        playerCardMap.put(alice, cardsa);
        playerCardMap.put(bob, cardsb);
        playerCardMap.put(charlie, cardsc);


        Player[] winners = handCheckerService.determineWinner(playerCardMap);


        assertEquals(HandRanking.FULL_HOUSE, handCheckerService.evaluate(cardsa));
        assertEquals(HandRanking.HIGH_CARD, handCheckerService.evaluate(cardsb));
        assertEquals(HandRanking.FLUSH, handCheckerService.evaluate(cardsc));
        assertEquals(1, winners.length);
        assertEquals(alice, winners[0]);
    }

    @Test
    public void testDetermineWinner2() {
        // Player dave with Three of a Kind (Three Kings)
        Player dave = new Player("Dave", 2000);
        Card card1d = new Card(Suit.SPADES, Rank.KING);
        Card card2d = new Card(Suit.CLUBS, Rank.KING);
        Card card3d = new Card(Suit.HEARTS, Rank.KING);
        Card card4d = new Card(Suit.DIAMONDS, Rank.TWO);
        Card card5d = new Card(Suit.SPADES, Rank.THREE);
        Card card6d = new Card(Suit.HEARTS, Rank.FOUR);
        Card card7d = new Card(Suit.HEARTS, Rank.FIVE);
        Card[] cardsd = {card1d, card2d, card3d, card4d, card5d, card6d, card7d};

        // Player eve with Three of a Kind (Three Sevens)
        Player eve = new Player("Eve", 2000);
        Card card1e = new Card(Suit.SPADES, Rank.SEVEN);
        Card card2e = new Card(Suit.CLUBS, Rank.SEVEN);
        Card card3e = new Card(Suit.HEARTS, Rank.SEVEN);
        Card card4e = new Card(Suit.DIAMONDS, Rank.JACK);
        Card card5e = new Card(Suit.SPADES, Rank.TEN);
        Card card6e = new Card(Suit.HEARTS, Rank.EIGHT);
        Card card7e = new Card(Suit.HEARTS, Rank.SIX);
        Card[] cardse = {card1e, card2e, card3e, card4e, card5e, card6e, card7e};

        // Player frank with High Card
        Player frank = new Player("Frank", 2000);
        Card card1f = new Card(Suit.SPADES, Rank.ACE);
        Card card2f = new Card(Suit.CLUBS, Rank.KING);
        Card card3f = new Card(Suit.HEARTS, Rank.FOUR);
        Card card4f = new Card(Suit.DIAMONDS, Rank.JACK);
        Card card5f = new Card(Suit.SPADES, Rank.TEN);
        Card card6f = new Card(Suit.HEARTS, Rank.EIGHT);
        Card card7f = new Card(Suit.HEARTS, Rank.SIX);
        Card[] cardsf = {card1f, card2f, card3f, card4f, card5f, card6f, card7f};

        HashMap<Player, Card[]> playerCardMap = new HashMap<>();
        playerCardMap.put(dave, cardsd);
        playerCardMap.put(eve, cardse);
        playerCardMap.put(frank, cardsf);

        Player[] winners = handCheckerService.determineWinner(playerCardMap);

        assertEquals(HandRanking.THREE_OF_A_KIND, handCheckerService.evaluate(cardsd));
        assertEquals(HandRanking.THREE_OF_A_KIND, handCheckerService.evaluate(cardse));
        assertEquals(HandRanking.HIGH_CARD, handCheckerService.evaluate(cardsf));

        assertEquals(1, winners.length);
        assertEquals(dave, winners[0]);
    }


    @Test
    public void TwoWinnersIfBestHandsAreEqual() {
        Card commoncard1 = new Card(Suit.DIAMONDS, Rank.KING);
        Card commoncard2 = new Card(Suit.HEARTS, Rank.KING);
        Card commoncard3 = new Card(Suit.CLUBS, Rank.KING);
        Card commoncard4 = new Card(Suit.SPADES, Rank.KING);
        Card commoncard5 = new Card(Suit.DIAMONDS, Rank.TWO);

        // Player grace with a Straight and Pair of Aces
        Player grace = new Player("Grace", 2000);
        Card card1g = new Card(Suit.SPADES, Rank.ACE);
        Card card2g = new Card(Suit.CLUBS, Rank.SEVEN);
        Card[] cardsg = {commoncard1, commoncard2, commoncard3, commoncard4, commoncard5, card1g, card2g};

        // Player hannah with a Straight and Pair of Aces
        Player hannah = new Player("Hannah", 2000);
        Card card1h = new Card(Suit.DIAMONDS, Rank.ACE);
        Card card2h = new Card(Suit.CLUBS, Rank.EIGHT);
        Card[] cardsh = {commoncard1, commoncard2, commoncard3, commoncard4, commoncard5, card1h, card2h};

        // Player isabella with a Straight and a 7 and a 3
        Player isabella = new Player("Isabella", 2000);
        Card card1i = new Card(Suit.SPADES, Rank.FOUR);
        Card card2i = new Card(Suit.CLUBS, Rank.THREE);
        Card[] cardsi = {commoncard1, commoncard2, commoncard3, commoncard4, commoncard5, card1i, card2i};

        HashMap<Player, Card[]> playerCardMap = new HashMap<>();
        playerCardMap.put(grace, cardsg);
        playerCardMap.put(hannah, cardsh);
        playerCardMap.put(isabella, cardsi);

        Player[] winners = handCheckerService.determineWinner(playerCardMap);

        assertEquals(HandRanking.FOUR_OF_A_KIND, handCheckerService.evaluate(cardsg));
        assertEquals(HandRanking.FOUR_OF_A_KIND, handCheckerService.evaluate(cardsh));
        assertEquals(HandRanking.FOUR_OF_A_KIND, handCheckerService.evaluate(cardsi));

        assertEquals(2, winners.length);
        assertTrue(Arrays.asList(winners).contains(grace));
        assertTrue(Arrays.asList(winners).contains(hannah));
    }


}

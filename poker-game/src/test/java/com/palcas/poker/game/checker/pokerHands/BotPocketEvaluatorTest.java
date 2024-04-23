package com.palcas.poker.game.checker.pokerHands;

import com.palcas.poker.game.Card;
import com.palcas.poker.game.evaluation.BotPocketEvaluator;
import com.palcas.poker.model.Rank;
import com.palcas.poker.model.Suit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

public class BotPocketEvaluatorTest {
    BotPocketEvaluator botPocketEvaluator;

    public BotPocketEvaluatorTest() {
        this.botPocketEvaluator = new BotPocketEvaluator();
    }

    @Test
    public void returns100BecauseItHasTheBestPossibleHand() {
        ArrayList<Card> commonCards = new ArrayList<>();
        commonCards.add(new Card(Suit.DIAMONDS, Rank.TEN));
        commonCards.add(new Card(Suit.DIAMONDS, Rank.JACK));
        commonCards.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
        commonCards.add(new Card(Suit.SPADES, Rank.THREE));
        commonCards.add(new Card(Suit.CLUBS, Rank.TWO));

        ArrayList<Card> pocketCards = new ArrayList<>();
        pocketCards.add(new Card(Suit.DIAMONDS, Rank.KING));
        pocketCards.add(new Card(Suit.DIAMONDS, Rank.ACE));

        int timesWonAgainstOtherRandomPockets = botPocketEvaluator.evaluatePocketAgainst100RandomPockets(commonCards, pocketCards);

        assertEquals(100, timesWonAgainstOtherRandomPockets);
    }

    @Test
    public void returns0BecauseItHasTheWorstPossibleHand() {
        ArrayList<Card> commonCards = new ArrayList<>();
        commonCards.add(new Card(Suit.DIAMONDS, Rank.TEN));
        commonCards.add(new Card(Suit.DIAMONDS, Rank.JACK));
        commonCards.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
        commonCards.add(new Card(Suit.DIAMONDS, Rank.FOUR));
        commonCards.add(new Card(Suit.CLUBS, Rank.SEVEN));

        ArrayList<Card> pocketCards = new ArrayList<>();
        pocketCards.add(new Card(Suit.CLUBS, Rank.TWO));
        pocketCards.add(new Card(Suit.CLUBS, Rank.THREE));

        int timesWonAgainstOtherRandomPockets = botPocketEvaluator.evaluatePocketAgainst100RandomPockets(commonCards, pocketCards);

        assertEquals(0, timesWonAgainstOtherRandomPockets);
    }

    @Test
    public void regularTestWithOnePair() {
        ArrayList<Card> commonCards = new ArrayList<>();
        commonCards.add(new Card(Suit.DIAMONDS, Rank.TEN));
        commonCards.add(new Card(Suit.DIAMONDS, Rank.JACK));
        commonCards.add(new Card(Suit.CLUBS, Rank.SEVEN));
        commonCards.add(new Card(Suit.DIAMONDS, Rank.THREE));
        commonCards.add(new Card(Suit.CLUBS, Rank.FIVE));

        ArrayList<Card> pocketCards = new ArrayList<>();
        pocketCards.add(new Card(Suit.CLUBS, Rank.TWO));
        pocketCards.add(new Card(Suit.CLUBS, Rank.THREE));

        int timesWonAgainstOtherRandomPockets = botPocketEvaluator.evaluatePocketAgainst100RandomPockets(commonCards, pocketCards);

        assertTrue(timesWonAgainstOtherRandomPockets >= 20);
        assertTrue(timesWonAgainstOtherRandomPockets <= 80);
    }

    @Test
    public void worksEvenWithOnly3CommunityCards() {
        ArrayList<Card> commonCards = new ArrayList<>();
        commonCards.add(new Card(Suit.CLUBS, Rank.SEVEN));
        commonCards.add(new Card(Suit.DIAMONDS, Rank.THREE));
        commonCards.add(new Card(Suit.CLUBS, Rank.FIVE));

        ArrayList<Card> pocketCards = new ArrayList<>();
        pocketCards.add(new Card(Suit.CLUBS, Rank.TWO));
        pocketCards.add(new Card(Suit.CLUBS, Rank.THREE));

        int timesWonAgainstOtherRandomPockets = botPocketEvaluator.evaluatePocketAgainst100RandomPockets(commonCards, pocketCards);

        assertTrue(timesWonAgainstOtherRandomPockets >= 20);
        assertTrue(timesWonAgainstOtherRandomPockets <= 80);
    }
}

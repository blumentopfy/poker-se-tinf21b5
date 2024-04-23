package com.palcas.poker.game.evaluation;

import com.palcas.poker.game.Card;
import com.palcas.poker.game.Deck;

import java.util.ArrayList;
import java.util.Collections;

public class BotPocketEvaluator {
    HandEvaluationService handEvaluationService;

    public BotPocketEvaluator() {
        handEvaluationService = new TexasHoldEmHandEvaluationService();
    }

    public int evaluatePocketAgainstNRandomPockets(ArrayList<Card> communityCards, ArrayList<Card> pocketCards, int numberPockets) {
        // create deck with all cards except the known cards
        Deck deck = new Deck().shuffle();
        for (Card card : communityCards) {
            deck.removeCard(card);
        }
        deck.removeCard(pocketCards.get(0));
        deck.removeCard(pocketCards.get(1));
        ArrayList<Card> cardsWithoutKnownCards = deck.getCards();

        // create a list with 100 random pockets
        ArrayList<ArrayList<Card>> simulatedPockets = new ArrayList<>();
        for(int i = 0; i < 1000; i++) {
            ArrayList<Card> tempDeck = new ArrayList<>(cardsWithoutKnownCards);
            Collections.shuffle(tempDeck);
            ArrayList<Card> pocket = new ArrayList<>();
            pocket.add(tempDeck.get(0));
            pocket.add(tempDeck.get(1));
            simulatedPockets.add(pocket);
        }

        // create set of all 7 original Cards
        ArrayList<Card> all7cardsFromOriginalPocket = new ArrayList<>(communityCards);
        all7cardsFromOriginalPocket.add(pocketCards.get(0));
        all7cardsFromOriginalPocket.add(pocketCards.get(1));
        Card[] originalCards = all7cardsFromOriginalPocket.toArray(new Card[all7cardsFromOriginalPocket.size()]);

        // compare each simulated pocket against the original pocket and see who would win
        int counterTimesWonOrEqual = 0;
        for (ArrayList<Card> pocket : simulatedPockets) {
            ArrayList<Card> all7cards = new ArrayList<>(communityCards);
            all7cards.add(pocket.get(0));
            all7cards.add(pocket.get(1));
            Card[] simulatedCards = all7cards.toArray(new Card[all7cards.size()]);
            if(handEvaluationService.compare(originalCards, simulatedCards) >= 1) {
                counterTimesWonOrEqual++;
            }
        }
        return counterTimesWonOrEqual;
    }
}

package com.palcas.poker.game.evaluation;

import com.palcas.poker.game.Card;
import com.palcas.poker.game.Deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BotPocketEvaluator {
    HandEvaluationService handEvaluationService;

    public BotPocketEvaluator() {
        handEvaluationService = new TexasHoldEmHandEvaluationService();
    }

    /*
     * creates a given number of random virtual pockets. They form a combination
     * with the community cards.
     * those combinations get compared to the combination the original pocket can
     * pose. Then evaluates against how many
     * virtual pockets the original pocket would win.
     * 
     * @param communityCards community cards, communityCards.size() must be between
     * 3 and 5
     * 
     * @param pocketCards original pocket cards of bot
     * 
     * @param numberPockets number of virtual pockets to simulate and compare to
     * 
     * @return number of virtual pockets, the original pocket would win against or
     * would draw
     */
    public int evaluatePostFlopPocket(List<Card> communityCards, ArrayList<Card> pocketCards, int numberPockets) {
        // create deck with all cards except the known cards
        Deck deck = new Deck().shuffleFullDeck();
        for (Card card : communityCards) {
            deck.removeCard(card);
        }
        deck.removeCard(pocketCards.get(0));
        deck.removeCard(pocketCards.get(1));
        ArrayList<Card> cardsWithoutKnownCards = deck.getCards();

        // create a list with 100 random pockets
        ArrayList<ArrayList<Card>> simulatedPockets = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
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

        // compare each simulated pocket against the original pocket and see who would
        // win
        int counterTimesWonOrEqual = 0;
        for (ArrayList<Card> pocket : simulatedPockets) {
            ArrayList<Card> all7cards = new ArrayList<>(communityCards);
            all7cards.add(pocket.get(0));
            all7cards.add(pocket.get(1));
            Card[] simulatedCards = all7cards.toArray(new Card[all7cards.size()]);
            if (handEvaluationService.compare(originalCards, simulatedCards) >= 1) {
                counterTimesWonOrEqual++;
            }
        }
        return counterTimesWonOrEqual;
    }

    /*
     * creates a given number of random virtual pockets and community cards to form
     * combinations.
     * then evaluates against how many virtual pockets the original pocket would win
     * or draw against.
     * 
     * @param pocketCards original pocket cards of the bot
     * 
     * @param numberPockets number of scenarios the pocketCards shall be evaluated
     * in
     * 
     * @returns the number of wins + draws against the simulated pockets
     */
    public int evaluatePreFlopPocket(List<Card> pocketCards, int numberPockets) {
        // create deck with all cards except the known cards
        Deck deckWithKnownCardsRemoved = new Deck().shuffleFullDeck();
        deckWithKnownCardsRemoved.removeCard(pocketCards.get(0));
        deckWithKnownCardsRemoved.removeCard(pocketCards.get(1));

        int counterTimesWonOrEqual = 0;
        for (int scenarioCounter = 0; scenarioCounter < numberPockets; scenarioCounter++) {
            deckWithKnownCardsRemoved.shuffle();
            ArrayList<Card> deck = new ArrayList<>(deckWithKnownCardsRemoved.getCards());

            // this Array contains the community cards at index 0-4 and the simulated
            // pockets at index 5-6
            Card[] communityCardsWithSimulatedPocket = new Card[7];
            for (int i = 0; i < 7; i++) {
                communityCardsWithSimulatedPocket[i] = deck.get(i);
            }

            // this Array contains the community cards at index 0-4 (they are copied from
            // other array) and the 2 original pocket cards
            Card[] communityCardsWithOriginalPocket = new Card[7];
            for (int i = 0; i < 5; i++) {
                communityCardsWithOriginalPocket[i] = communityCardsWithSimulatedPocket[i];
            }
            communityCardsWithOriginalPocket[5] = pocketCards.get(0);
            communityCardsWithOriginalPocket[6] = pocketCards.get(1);

            if (handEvaluationService.compare(communityCardsWithOriginalPocket,
                    communityCardsWithSimulatedPocket) >= 1) {
                counterTimesWonOrEqual++;
            }
        }
        return counterTimesWonOrEqual;
    }

}

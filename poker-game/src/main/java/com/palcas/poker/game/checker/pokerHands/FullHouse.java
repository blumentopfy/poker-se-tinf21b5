package com.palcas.poker.game.checker.pokerHands;

import com.palcas.poker.model.Rank;
import com.palcas.poker.game.Card;
import com.palcas.poker.game.checker.CardsStatisticsService;

import java.util.HashMap;

public class FullHouse {

    CardsStatisticsService cardsStatistics;

    public FullHouse(CardsStatisticsService cardsStatistics) {
        this.cardsStatistics = cardsStatistics;
    }
    public boolean containsFullHouse(Card[] cards) {
        HashMap<Rank, Integer> countedRanks = cardsStatistics.countRanks(cards);
        boolean containsThreeOfAKind = false;
        boolean containsTwoOfAKind = false;
        for (int rankCount : countedRanks.values()) {
            if (rankCount == 3) {
                if(containsThreeOfAKind || containsTwoOfAKind) {
                    return true;
                } else {
                    containsThreeOfAKind = true;
                }
            }
            if (rankCount == 2) {
                if (containsThreeOfAKind) {
                    return true;
                } else {
                    containsTwoOfAKind = true;
                }
            }
        }
        return false;
    }

    public Card[] selectHandForFullHouse(Card[] all7cards) {
        if(!containsFullHouse(all7cards)) {return null;}
        HashMap<Rank, Integer> countedRanks = cardsStatistics.countRanks(all7cards);
        Rank[] descendingSortedRanks = cardsStatistics.getDescendingOrderedRanks();

        //determine, which rank do the pair and triplet have
        Rank pairOfThreeRank = null;
        Rank pairOfTwoRank = null;
        for (Rank rank : descendingSortedRanks) {
            if (countedRanks.get(rank) == 3) {
                if(pairOfThreeRank == null) {
                    pairOfThreeRank = rank;
                } else if (pairOfTwoRank == null) {
                    pairOfTwoRank = rank;
                }
            } else if (countedRanks.get(rank) == 2) {
                if (pairOfTwoRank == null) {
                    pairOfTwoRank = rank;
                }
            }
        }
        return createFullHouseHand(all7cards, pairOfThreeRank, pairOfTwoRank);
    }

    private Card[] createFullHouseHand(Card[] all7cards, Rank pairOfThreeRank, Rank pairOfTwoRank){
        Card[] selected5cards = new Card[5];
        int pairOfThreeCounter = 0;
        int pairOfTwoCounter = 3;
        for (Card card : all7cards) {
            if (card.getRank() == pairOfThreeRank && pairOfThreeCounter <= 2) {
                selected5cards[pairOfThreeCounter++] = card;
            } else if (card.getRank() == pairOfTwoRank && pairOfTwoCounter <= 4) {
                selected5cards[pairOfTwoCounter++] = card;
            }
        }
        return selected5cards;
    }
}

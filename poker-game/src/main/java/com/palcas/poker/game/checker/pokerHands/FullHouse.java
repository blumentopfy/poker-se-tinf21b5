package com.palcas.poker.game.checker.pokerHands;

import com.palcas.poker.Rank;
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
}

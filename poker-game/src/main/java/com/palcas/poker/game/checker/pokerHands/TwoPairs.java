package com.palcas.poker.game.checker.pokerHands;

import com.palcas.poker.Rank;
import com.palcas.poker.game.Card;
import com.palcas.poker.game.checker.CardsStatisticsService;

import java.util.HashMap;

public class TwoPairs {

    CardsStatisticsService cardsStatistics;

    public TwoPairs(CardsStatisticsService cardsStatistics) {
        this.cardsStatistics = cardsStatistics;
    }
    public boolean containsTwoPairs(Card[] cards) {
        HashMap<Rank, Integer> countedRanks = cardsStatistics.countRanks(cards);
        boolean containsTwoOfAKind = false;
        for (int rankCount : countedRanks.values()) {
            if (rankCount == 2) {
                if(containsTwoOfAKind) {
                    return true;
                } else {
                    containsTwoOfAKind = true;
                }
            }
        }
        return false;
    }
}

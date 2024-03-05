package com.palcas.poker.game.checker.pokerHands;

import com.palcas.poker.Rank;
import com.palcas.poker.game.Card;
import com.palcas.poker.game.checker.CardsStatisticsService;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class Straight {

    CardsStatisticsService cardsStatistics;

    public Straight(CardsStatisticsService cardsStatistics) {
        this.cardsStatistics = cardsStatistics;
    }
    public boolean containsStraight(Card[] cards) {
        HashMap<Rank, Integer> countedRanks = cardsStatistics.countRanks(cards);
        Rank[] ascendingOrderedRanks = cardsStatistics.getAscendingOrderedRanks();

        int streak = 0;
        // check for Ace at the beginning of the streak, since it can be the very lowest or very highest card
        if (countedRanks.get(Rank.ACE) >= 1) {
            streak++;
        }
        for (Rank rank : ascendingOrderedRanks) {
            if (countedRanks.get(rank) >= 1) {
                streak++;
                if(streak >= 5) {
                    return true;
                }
            } else {
                streak = 0;
            }
        }
        return false;
    }
}

package com.palcas.poker.game.checker.pokerHands;

import java.util.HashMap;

import com.palcas.poker.Rank;
import com.palcas.poker.Suit;
import com.palcas.poker.game.Card;
import com.palcas.poker.game.checker.CardsStatisticsService;

public class RoyalFlush {

    CardsStatisticsService cardsStatistics;

    public RoyalFlush(CardsStatisticsService cardsStatistics) {
        this.cardsStatistics = cardsStatistics;
    }

    public boolean containsRoyalFlush(Card[] cards) {
        Suit suitOfPotentialRoyalFlush = cardsStatistics.calculateSuitOfPotentialFlush(cards);
        if (suitOfPotentialRoyalFlush == null) {return false;}
        Card[] setOfCardsWithFlushSuite = cardsStatistics.filterForSuit(suitOfPotentialRoyalFlush, cards);

        //check manually for 10, Jack, Queen, King, Ace
        HashMap<Rank, Integer> countedRanks = cardsStatistics.countRanks(setOfCardsWithFlushSuite);
        return countedRanks.get(Rank.TEN) >= 1
                && countedRanks.get(Rank.JACK) >= 1
                && countedRanks.get(Rank.QUEEN) >= 1
                && countedRanks.get(Rank.KING) >= 1
                && countedRanks.get(Rank.ACE) >= 1;
    }


}

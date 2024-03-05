package com.palcas.poker.game.checker.pokerHands;

import com.palcas.poker.Suit;
import com.palcas.poker.game.Card;
import com.palcas.poker.game.checker.CardsStatisticsService;

public class StraightFlush {

    CardsStatisticsService cardsStatistics;

    public StraightFlush(CardsStatisticsService cardsStatistics) {
        this.cardsStatistics = cardsStatistics;
    }

    public boolean containsStraightFlush(Card[] cards) {
        Suit suitOfPotentialStraightFlush = cardsStatistics.calculateSuitOfPotentialFlush(cards);
        if (suitOfPotentialStraightFlush == null) {return false;}
        Card[] setOfCardsWithFlushSuite = cardsStatistics.filterForSuit(suitOfPotentialStraightFlush, cards);
        return cardsStatistics.containsStraight(setOfCardsWithFlushSuite);
    }
}

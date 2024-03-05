package com.palcas.poker.game.checker.pokerHands;

import com.palcas.poker.Rank;
import com.palcas.poker.Suit;
import com.palcas.poker.game.Card;
import com.palcas.poker.game.checker.CardsStatisticsService;

import java.util.HashMap;

public class Flush {
    CardsStatisticsService cardsStatistics;

    public Flush(CardsStatisticsService cardsStatistics) {
        this.cardsStatistics = cardsStatistics;
    }

    public boolean containsFlush(Card[] cards) {
        Suit suitOfPotentialRoyalFlush = cardsStatistics.calculateSuitOfPotentialFlush(cards);
        if (suitOfPotentialRoyalFlush == null) {return false;} else {
            return true;
        }
    }
}

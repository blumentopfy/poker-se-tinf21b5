package com.palcas.poker.game.checker.pokerHands;

import com.palcas.poker.model.Rank;
import com.palcas.poker.model.Suit;
import com.palcas.poker.game.Card;
import com.palcas.poker.game.checker.CardsStatisticsService;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

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

    //Structure of hand returned: cards ordered by descending value
    public Card[] selectHandForStraightFlush(Card[] all7cards) {
        if (!containsStraightFlush(all7cards)) { return null; }
        Suit suitOfPotentialStraightFlush = cardsStatistics.calculateSuitOfPotentialFlush(all7cards);
        Card[] setOfCardsWithFlushSuite = cardsStatistics.filterForSuit(suitOfPotentialStraightFlush, all7cards);
        HashMap<Rank, Integer> countedRanks = cardsStatistics.countRanks(setOfCardsWithFlushSuite);
        Rank[] sortedRanks = cardsStatistics.getDescendingOrderedRanks();
        int streak = 0;
        Card[] selected5cards = new Card[5];
        for (Rank rank : sortedRanks) {
            if (countedRanks.get(rank) >= 1) {
                selected5cards[streak++] = cardsStatistics.getCardByRank(setOfCardsWithFlushSuite, rank);
                if (streak == 5) {return selected5cards;}
            } else {
                streak = 0;
            }
        }
        // check for Ace also at bottom of the streak, since it can be the very lowest or very highest card
        if (countedRanks.get(Rank.ACE) >= 1) {
            selected5cards[streak] = cardsStatistics.getCardByRank(setOfCardsWithFlushSuite, Rank.ACE);
        }
        return selected5cards;
    }

    public int compareStraightFlushHands(Card[] hand1, Card[] hand2) {
        return hand1[0].compareTo(hand2[0]);
    }
}

package com.palcas.poker.game.checker.pokerHands;

import com.palcas.poker.Rank;
import com.palcas.poker.game.Card;
import com.palcas.poker.game.checker.CardsStatisticsService;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class Pair {
    CardsStatisticsService cardsStatistics;

    public Pair(CardsStatisticsService cardsStatistics) {
        this.cardsStatistics = cardsStatistics;
    }

    public boolean containsPair(Card[] cards) {
        HashMap<Rank, Integer> countedRanks = cardsStatistics.countRanks(cards);
        for (int rankCount : countedRanks.values()) {
            if (rankCount == 2) {
                return true;
            }
        }
        return false;
    }

    public Card[] selectHandForPair(Card[] all7cards) {
        Rank[] sortedRanks = cardsStatistics.getDescendingOrderedRanks();
        HashMap<Rank, Integer> countedRanks = cardsStatistics.countRanks(all7cards);
        // sort cards ascending, so we can easily also find the high card
        Arrays.sort(all7cards, Comparator.comparingInt(card -> ((Card) card).getRank().getValue()).reversed());
        int pairIndex = 0;
        int highCardIndex = 2;
        Card[] selected5cards = new Card[5];
        for (Card card : all7cards) {
            if(countedRanks.get(card.getRank()) == 2) {
                selected5cards[pairIndex] = card;
                pairIndex++;
            } else if (countedRanks.get(card.getRank()) != 2 && highCardIndex <= 4) {
                selected5cards[highCardIndex] = card;
                highCardIndex++;
            }
        }
        return selected5cards;
    }
}

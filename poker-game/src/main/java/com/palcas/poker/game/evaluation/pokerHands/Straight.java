package com.palcas.poker.game.evaluation.pokerHands;

import com.palcas.poker.game.model.Rank;
import com.palcas.poker.game.Card;
import com.palcas.poker.game.evaluation.CardsStatisticsService;

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
        // check for Ace at the beginning of the streak, since it can be the very lowest
        // or very highest card
        if (countedRanks.get(Rank.ACE) >= 1) {
            streak++;
        }
        for (Rank rank : ascendingOrderedRanks) {
            if (countedRanks.get(rank) >= 1) {
                streak++;
                if (streak >= 5) {
                    return true;
                }
            } else {
                streak = 0;
            }
        }
        return false;
    }

    // Structure of hand returned: cards ordered by descending value
    public Card[] selectHandForStraight(Card[] all7cards) {
        if (!containsStraight(all7cards)) {
            return null;
        }
        HashMap<Rank, Integer> countedRanks = cardsStatistics.countRanks(all7cards);
        Rank[] descendingSortedRanks = cardsStatistics.getDescendingOrderedRanks();
        int streak = 0;
        Card[] selected5cards = new Card[5];
        for (Rank rank : descendingSortedRanks) {
            if (streak == 5) {
                return selected5cards;
            }
            if (countedRanks.get(rank) >= 1) {
                selected5cards[streak++] = cardsStatistics.getCardByRank(all7cards, rank);
                if (streak == 5) {
                    return selected5cards;
                }
            } else {
                streak = 0;
            }
        }
        // check for Ace at both ends of the streak, since it can be the very lowest or
        // very highest card
        if (countedRanks.get(Rank.ACE) >= 1) {
            selected5cards[streak] = cardsStatistics.getCardByRank(all7cards, Rank.ACE);
        }
        return selected5cards;
    }

    public int compareStraightHands(Card[] hand1, Card[] hand2) {
        return hand1[0].compareTo(hand2[0]);
    }
}

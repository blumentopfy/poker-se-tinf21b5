package com.palcas.poker.game.evaluation.pokerHands;

import com.palcas.poker.model.Rank;
import com.palcas.poker.game.Card;
import com.palcas.poker.game.evaluation.CardsStatisticsService;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class FourOfAKind {

    CardsStatisticsService cardsStatistics;

    public FourOfAKind(CardsStatisticsService cardsStatistics) {
        this.cardsStatistics = cardsStatistics;
    }

    public boolean containsFourOfAKind(Card[] cards) {
        HashMap<Rank, Integer> countedRanks = cardsStatistics.countRanks(cards);
        for (int rankCount : countedRanks.values()) {
            if (rankCount == 4) {
                return true;
            }
        }
        return false;
    }

    // Stucture of hand returned: card 0-3 are 4 of a kind, card 4 is high card
    public Card[] selectHandForFourOfAKind(Card[] all7cards) {
        if (!containsFourOfAKind(all7cards)) {
            return null;
        }
        Rank rankOfFourOfAKind = calculateRankOfFourOfAKind(all7cards);
        // sort cards ascending so, we can easily also find the high card
        Arrays.sort(all7cards, Comparator.comparingInt(card -> card.getRank().getValue()));
        int i = 0;
        Card[] selected5cards = new Card[5];
        for (Card card : all7cards) {
            if (card.getRank() == rankOfFourOfAKind) {
                selected5cards[i] = card;
                i++;
            } else {
                selected5cards[4] = card;
            }
        }
        return selected5cards;
    }

    private Rank calculateRankOfFourOfAKind(Card[] cards) {
        HashMap<Rank, Integer> countedRanks = cardsStatistics.countRanks(cards);
        Rank rankOfFourOfAKind = null;
        for (Rank rank : countedRanks.keySet()) {
            if (countedRanks.get(rank) == 4) {
                rankOfFourOfAKind = rank;
            }
        }
        return rankOfFourOfAKind;
    }

    public int compareFourOfAKindHands(Card[] hand1, Card[] hand2) {
        if (hand1[0].compareTo(hand2[0]) != 0) {
            return hand1[0].compareTo(hand2[0]);
        } else {
            return hand1[4].compareTo(hand2[4]);
        }
    }
}

package com.palcas.poker.game.checker.pokerHands;

import com.palcas.poker.model.Rank;
import com.palcas.poker.game.Card;
import com.palcas.poker.game.checker.CardsStatisticsService;

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

    public Card[] selectHandForFourOfAKind(Card[] all7cards) {
        // calculate the rank, of which there are 4 of
        HashMap<Rank, Integer> countedRanks = cardsStatistics.countRanks(all7cards);
        Rank rankOfFourOfAKind = null;
        for (Rank rank : countedRanks.keySet()) {
            if (countedRanks.get(rank) == 4) {
                rankOfFourOfAKind = rank;
            }
        }
        // sort cards ascending so, we can easily also find the high card
        Arrays.sort(all7cards, Comparator.comparingInt(card -> card.getRank().getValue()));
        int i = 0;
        Card[] selected5cards = new Card[5];
        for (Card card : all7cards) {
            if(card.getRank() == rankOfFourOfAKind) {
                selected5cards[i] = card;
                i++;
            } else {
                selected5cards[4] = card;
            }
        }
        return selected5cards;
    }
}

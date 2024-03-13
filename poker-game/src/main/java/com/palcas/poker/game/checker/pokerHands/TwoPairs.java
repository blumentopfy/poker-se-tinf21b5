package com.palcas.poker.game.checker.pokerHands;

import com.palcas.poker.model.Rank;
import com.palcas.poker.game.Card;
import com.palcas.poker.game.checker.CardsStatisticsService;

import java.util.Arrays;
import java.util.Comparator;
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

    public Card[] selectHandForTwoPairs(Card[] all7cards) {
        if (!containsTwoPairs(all7cards)) {return null;}
        Rank[] descendingSortedRanks = cardsStatistics.getDescendingOrderedRanks();
        HashMap<Rank, Integer> countedRanks = cardsStatistics.countRanks(all7cards);

        Rank betterPair = null;
        Rank worsePair = null;
        Rank highCardRank = null;
        for (Rank rank : descendingSortedRanks) {
            if (countedRanks.get(rank) == 2) {
                if(betterPair == null) {
                    betterPair = rank;
                } else if (worsePair == null) {
                    worsePair = rank;
                }
            }
        }
        return createTwoPairsHand(all7cards, betterPair, worsePair);
    }

    private Card[] createTwoPairsHand(Card[] all7cards, Rank betterPair, Rank worsePair) {
        // sort cards ascending, so we can easily also find the high card
        Arrays.sort(all7cards, Comparator.comparingInt(card -> ((Card) card).getRank().getValue()).reversed());

        Card[] selected5cards = new Card[5];
        for (Card card : all7cards) {
            if(card.getRank() == betterPair) {
                if(selected5cards[0] == null) {
                    selected5cards[0] = card;
                } else {
                    selected5cards[1] = card;
                }
            } else if(card.getRank() == worsePair) {
                if(selected5cards[2] == null) {
                    selected5cards[2] = card;
                } else {
                    selected5cards[3] = card;
                }
            } else if (selected5cards[4] == null) {
                selected5cards[4] = card;
            }
        }
        return selected5cards;
    }
}

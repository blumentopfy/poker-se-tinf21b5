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
        Rank[] sortedRanks = cardsStatistics.getDescendingOrderedRanks();
        HashMap<Rank, Integer> countedRanks = cardsStatistics.countRanks(all7cards);
        // sort cards ascending, so we can easily also find the high card
        Arrays.sort(all7cards, Comparator.comparingInt(card -> ((Card) card).getRank().getValue()).reversed());
        int i = 0;
        Card[] selected5cards = new Card[5];
        //TODO this might need some more logic checking
        for (Card card : all7cards) {
            if(countedRanks.get(card.getRank()) == 2 && i <= 3) {
                selected5cards[i] = card;
                i++;
            } else {
                selected5cards[4] = card;
            }
            if (selected5cards[4]!=null && selected5cards[3]!=null) {
                break;
            }
        }
        return selected5cards;
    }
}

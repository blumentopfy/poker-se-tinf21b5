package com.palcas.poker.game.checker.pokerHands;

import com.palcas.poker.Rank;
import com.palcas.poker.game.Card;
import com.palcas.poker.game.checker.CardsStatisticsService;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class ThreeOfAKind {

    CardsStatisticsService cardsStatistics;

    public ThreeOfAKind(CardsStatisticsService cardsStatistics) {
        this.cardsStatistics = cardsStatistics;
    }
    public boolean containsThreeOfAKind(Card[] cards) {
        HashMap<Rank, Integer> countedRanks = cardsStatistics.countRanks(cards);
        for (int rankCount : countedRanks.values()) {
            if (rankCount >= 3) {
                return true;
            }
        }
        return false;
    }

    public Card[] selectHandForThreeOfAKind(Card[] all7cards) {
        Rank[] descendingSortedRanks = cardsStatistics.getDescendingOrderedRanks();
        HashMap<Rank, Integer> countedRanks = cardsStatistics.countRanks(all7cards);
        Rank rankOfThreeOfAKind = null;
        for (Rank rank : descendingSortedRanks) {
            if (countedRanks.get(rank) == 3) {
                rankOfThreeOfAKind = rank;
                break;
            }
        }
        // sort cards ascending, so we can easily also find the high card
        Arrays.sort(all7cards, Comparator.comparingInt(card -> ((Card) card).getRank().getValue()).reversed());
        int i = 0;
        Card[] selected5cards = new Card[5];
        for (Card card : all7cards) {
            if(card.getRank() == rankOfThreeOfAKind) {
                selected5cards[i] = card;
                i++;
            } else if (card.getRank() != rankOfThreeOfAKind && selected5cards[3] == null){
                selected5cards[3] = card;
            } else {
                selected5cards[4] = card;
                break;
            }
        }
        return selected5cards;
    }
}

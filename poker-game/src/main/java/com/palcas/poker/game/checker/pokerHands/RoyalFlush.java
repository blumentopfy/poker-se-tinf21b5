package com.palcas.poker.game.checker.pokerHands;

import java.util.HashMap;

import com.palcas.poker.model.Rank;
import com.palcas.poker.model.Suit;
import com.palcas.poker.game.Card;
import com.palcas.poker.game.checker.CardsStatisticsService;

public class RoyalFlush {

    CardsStatisticsService cardsStatistics;

    public RoyalFlush(CardsStatisticsService cardsStatistics) {
        this.cardsStatistics = cardsStatistics;
    }

    public boolean containsRoyalFlush(Card[] cards) {
        Suit suitOfPotentialRoyalFlush = cardsStatistics.calculateSuitOfPotentialFlush(cards);
        if (suitOfPotentialRoyalFlush == null) {return false;}
        Card[] setOfCardsWithFlushSuite = cardsStatistics.filterForSuit(suitOfPotentialRoyalFlush, cards);

        //check manually for 10, Jack, Queen, King, Ace
        HashMap<Rank, Integer> countedRanks = cardsStatistics.countRanks(setOfCardsWithFlushSuite);
        return countedRanks.get(Rank.TEN) >= 1
                && countedRanks.get(Rank.JACK) >= 1
                && countedRanks.get(Rank.QUEEN) >= 1
                && countedRanks.get(Rank.KING) >= 1
                && countedRanks.get(Rank.ACE) >= 1;
    }

    public Card[] selectHandForRoyalFlush(Card[] all7Cards) {
        if (!containsRoyalFlush(all7Cards)) {return null;}
        Suit suitOfRoyalFlush = cardsStatistics.calculateSuitOfPotentialFlush(all7Cards);

        Card[] selected5cards = new Card[5];
        for (Card card: all7Cards) {
            if(card.getSuit() == suitOfRoyalFlush) {
                if (card.getRank() == Rank.TEN) {
                    selected5cards[0] = card;
                } else if (card.getRank() == Rank.JACK) {
                    selected5cards[1] = card;
                } else if (card.getRank() == Rank.QUEEN) {
                    selected5cards[2] = card;
                } else if (card.getRank() == Rank.KING) {
                    selected5cards[3] = card;
                } else if (card.getRank() == Rank.ACE) {
                    selected5cards[4] = card;
                }
            }
        }
        return selected5cards;
    }

    public int compareRoyalFlushHands(Card[] hand1, Card[] hand2) {
        return 0;
    }
}

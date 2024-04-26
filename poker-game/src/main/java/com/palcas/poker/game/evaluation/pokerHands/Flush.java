package com.palcas.poker.game.evaluation.pokerHands;

import com.palcas.poker.model.Suit;
import com.palcas.poker.game.Card;
import com.palcas.poker.game.evaluation.CardsStatisticsService;

import java.util.Arrays;
import java.util.Comparator;

public class Flush {
    CardsStatisticsService cardsStatistics;

    public Flush(CardsStatisticsService cardsStatistics) {
        this.cardsStatistics = cardsStatistics;
    }

    public boolean containsFlush(Card[] cards) {
        Suit suitOfPotentialRoyalFlush = cardsStatistics.calculateSuitOfPotentialFlush(cards);
        if (suitOfPotentialRoyalFlush == null) {
            return false;
        } else {
            return true;
        }
    }

    // Structure of hand returned: cards are ordered by descending value
    public Card[] selectHandForFlush(Card[] all7cards) {
        if (!containsFlush(all7cards)) {
            return null;
        }
        Suit suitOfFlush = cardsStatistics.calculateSuitOfPotentialFlush(all7cards);
        Arrays.sort(all7cards, Comparator.comparingInt(card -> ((Card) card).getRank().getValue()).reversed());
        Card[] selected5cards = new Card[5];
        int arrayCounter = 0;
        for (Card card : all7cards) {
            if (card.getSuit() == suitOfFlush && arrayCounter <= 4) {
                selected5cards[arrayCounter++] = card;
            }
        }
        return selected5cards;
    }

    public int compareFlushHands(Card[] hand1, Card[] hand2) {
        for (int i = 0; i < 5; i++) {
            if (hand1[i].compareTo(hand2[i]) != 0) {
                return hand1[i].compareTo(hand2[i]);
            }
        }
        return 0;
    }
}

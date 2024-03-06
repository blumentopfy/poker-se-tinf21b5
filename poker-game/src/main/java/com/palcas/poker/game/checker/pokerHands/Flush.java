package com.palcas.poker.game.checker.pokerHands;

import com.palcas.poker.model.Rank;
import com.palcas.poker.model.Suit;
import com.palcas.poker.game.Card;
import com.palcas.poker.game.checker.CardsStatisticsService;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class Flush {
    CardsStatisticsService cardsStatistics;

    public Flush(CardsStatisticsService cardsStatistics) {
        this.cardsStatistics = cardsStatistics;
    }

    public boolean containsFlush(Card[] cards) {
        Suit suitOfPotentialRoyalFlush = cardsStatistics.calculateSuitOfPotentialFlush(cards);
        if (suitOfPotentialRoyalFlush == null) {return false;} else {
            return true;
        }
    }

    public Card[] selectHandForFlush(Card[] all7cards) {
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
}

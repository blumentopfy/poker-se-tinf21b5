package com.palcas.poker.game.evaluation;

import com.palcas.poker.game.model.Rank;
import com.palcas.poker.game.model.Suit;
import com.palcas.poker.game.Card;

import java.util.*;

public class CardsStatisticsService {

    /*
     * Counts how many cards of each rank there are
     * 
     * @param mergedHandAndBoard Card-Array of 7 cards, whose ranks will be counted
     * 
     * @return a HashMap<Rank, Integer> with the number of cards for each rank. This
     * should add up to 7 again
     */
    public HashMap<Rank, Integer> countRanks(Card[] mergedHandAndBoard) {
        HashMap<Rank, Integer> rankCounter = new HashMap<>();
        for (Rank rank : Rank.values()) {
            rankCounter.put(rank, 0);
            for (Card card : mergedHandAndBoard) {
                if (card.getRank() == rank) {
                    int currentValue = rankCounter.get(rank);
                    rankCounter.put(rank, currentValue + 1);
                }
            }
        }
        return rankCounter;
    }

    /*
     * Counts how many cards of each suit there are
     * 
     * @param mergedHandAndBoard Card-Array of 7 cards, whose suits will be counted
     * 
     * @return a HashMap<Suit, Integer> with the number of cards for each suit. This
     * should add up to 7 again
     */
    public HashMap<Suit, Integer> countSuits(Card[] mergedHandAndBoard) {
        HashMap<Suit, Integer> suitCounter = new HashMap<>();
        for (Suit suit : Suit.values()) {
            suitCounter.put(suit, 0);
            for (Card card : mergedHandAndBoard) {
                if (card.getSuit() == suit) {
                    int currentValue = suitCounter.get(suit);
                    suitCounter.put(suit, currentValue + 1);
                }
            }
        }
        return suitCounter;
    }

    public Suit calculateSuitOfPotentialFlush(Card[] cards) {
        HashMap<Suit, Integer> countedSuits = countSuits(cards);
        Suit suitOfPotentialFlush = null;
        for (Suit suit : countedSuits.keySet()) {
            if (countedSuits.get(suit) >= 5) {
                suitOfPotentialFlush = suit;
            }
        }
        return suitOfPotentialFlush;
    }

    public Card[] filterForSuit(Suit suit, Card[] cards) {
        List<Card> filteredCardsList = new ArrayList<Card>();
        for (Card card : cards) {
            if (card.getSuit() == suit) {
                filteredCardsList.add(card);
            }
        }
        Card[] filteredCardsArray = new Card[filteredCardsList.size()];
        filteredCardsArray = filteredCardsList.toArray(filteredCardsArray);
        return filteredCardsArray;
    }

    public boolean containsStraight(Card[] cards) {
        HashMap<Rank, Integer> countedRanks = countRanks(cards);
        Rank[] sortedRanks = Rank.values();
        Arrays.sort(Rank.values(), Comparator.comparingInt(Rank::getValue));
        int streak = 0;
        // check for Ace at the beginning of the streak, since it can be the very lowest
        // or very highest card
        if (countedRanks.get(Rank.ACE) >= 1) {
            streak++;
        }
        for (Rank rank : sortedRanks) {
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

    public Rank[] getAscendingOrderedRanks() {
        Rank[] ascendingSortedRanks = Rank.values();
        Arrays.sort(Rank.values(), Comparator.comparingInt(Rank::getValue));
        return ascendingSortedRanks;
    }

    public Rank[] getDescendingOrderedRanks() {
        Rank[] descendingSortedRanks = Rank.values();
        Arrays.sort(descendingSortedRanks, Comparator.comparingInt(Rank::getValue).reversed());
        return descendingSortedRanks;
    }

    public Card getCardByRank(Card[] cards, Rank rank) {
        for (Card card : cards) {
            if (card.getRank() == rank) {
                return card;
            }
        }
        return null;
    }
}

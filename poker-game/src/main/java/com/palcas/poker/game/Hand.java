package com.palcas.poker.game;

public class Hand {

    public enum HandRanking {
        ROYAL_FLUSH("Royal Flush", 9),
        STRAIGHT_FLUSH("Straight Flush", 8),
        FOUR_OF_A_KIND("Four of a Kind", 7),
        FULL_HOUSE("Full House", 6),
        FLUSH("Flush", 5),
        STRAIGHT("Straight", 4),
        THREE_OF_A_KIND("Three of a Kind", 3),
        TWO_PAIRS("Two Pairs", 2),
        PAIR("Pair", 1),
        HIGH_CARD("High Card", 0);

        private final String name;
        private final int value;

        HandRanking(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        public String getName() {
            return name;
        }
    }



    private Card[] cards; //here, only 5 cards get saved
    private HandRanking handRanking;



    public Hand(Card[] cardsOfCombination){
        this.handRanking = evaluateHandRanking(cards);
        //TODO select the 5 best out of the 7 and set them
    }


    public Card[] getCards() {
        return this.cards;
    }

    /*
     sets the hand rank for a fast, rough comparison of 2 Hands
     @param cards Array of 7 Cards which get examined to determine the hand rank
     @returns most valuable HandRanking possible from that 7 inputted cards
     */
    private HandRanking evaluateHandRanking(Card[] cards) {
        if (HandRankingChecker.containsRoyalFlush(cards)) {
            return HandRanking.ROYAL_FLUSH;
        } else if (HandRankingChecker.containsStraightFlush(cards)) {
            return HandRanking.STRAIGHT_FLUSH;
        } else if (HandRankingChecker.containsFourOfAKind(cards)) {
            return HandRanking.FOUR_OF_A_KIND;
        } else if (HandRankingChecker.containsFullHouse(cards)) {
            return HandRanking.FULL_HOUSE;
        } else if (HandRankingChecker.containsFlush(cards)) {
            return HandRanking.FLUSH;
        } else if (HandRankingChecker.containsStraight(cards)) {
            return HandRanking.STRAIGHT;
        } else if (HandRankingChecker.containsThreeOfAKind(cards)) {
            return HandRanking.THREE_OF_A_KIND;
        } else if (HandRankingChecker.containsTwoPairs(cards)) {
            return HandRanking.TWO_PAIRS;
        } else if (HandRankingChecker.containsPair(cards)) {
            return HandRanking.PAIR;
        } else {
            return HandRanking.HIGH_CARD;
        }
    }

}

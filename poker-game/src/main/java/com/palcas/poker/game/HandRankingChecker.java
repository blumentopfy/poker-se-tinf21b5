package com.palcas.poker.game;

import com.palcas.poker.display.CardDisplay.Rank;
import com.palcas.poker.display.CardDisplay.Suit;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class HandRankingChecker {

    //TODO if we need more classes to fit the conditions, we could split up this class and HandRankingCardsSelector into many seperate classes
    
    public static boolean containsRoyalFlush(Card[] cards) {
        HashMap<Suit, Integer> countedSuits = countSuits(cards);
        Suit suitOfPotentialStraightFlush = null;
        int numberOfCardsWithThisSuit = 0;

        // calculate the suit of the potential straight flush
        for (Suit suit : countedSuits.keySet()) {
            if (countedSuits.get(suit) >= 5) {
                suitOfPotentialStraightFlush = suit;
                numberOfCardsWithThisSuit = countedSuits.get(suit);
            }
        }
        if (suitOfPotentialStraightFlush == null) {
            // if it is not even a flush, it cant be a straight flush
            return false;
        }

        // create new Array with only the cards of the same suit
        Card[] setOfCardsWithFlushSuite = new Card[numberOfCardsWithThisSuit];
        int i = 0;
        for (Card card: cards) {
            if(card.getSuit() == suitOfPotentialStraightFlush) {
                setOfCardsWithFlushSuite[i] = card;
                i++;
            }
        }

        //check manually, if 10,J,Q,K,A are there
        HashMap<Rank, Integer> countedRanks = countRanks(setOfCardsWithFlushSuite);
        return countedRanks.get(Rank.TEN) >= 1
                && countedRanks.get(Rank.JACK) >= 1
                && countedRanks.get(Rank.QUEEN) >= 1
                && countedRanks.get(Rank.KING) >= 1
                && countedRanks.get(Rank.ACE) >= 1;
    }

    public static boolean containsStraightFlush(Card[] cards) {

        HashMap<Suit, Integer> countedSuits = countSuits(cards);
        Suit suitOfPotentialStraightFlush = null;
        int numberOfCardsWithThisSuit = 0;

        // calculate the suit of the potential straight flush
        for (Suit suit : countedSuits.keySet()) {
            if (countedSuits.get(suit) >= 5) {
                suitOfPotentialStraightFlush = suit;
                numberOfCardsWithThisSuit = countedSuits.get(suit);
            }
        }
        if (suitOfPotentialStraightFlush == null) {
            // if it is not even a flush, it cant be a straight flush
            return false;
        }

        // create new Array with only the cards of the same suit
        Card[] setOfCardsWithFlushSuite = new Card[numberOfCardsWithThisSuit];
        int i = 0;
        for (Card card: cards) {
            if(card.getSuit() == suitOfPotentialStraightFlush) {
                setOfCardsWithFlushSuite[i] = card;
                i++;
            }
        }

        // check for straight in only this new Array of Cards
        HashMap<Rank, Integer> countedRanks = countRanks(setOfCardsWithFlushSuite);
        Rank[] sortedRanks = Rank.values();
        Arrays.sort(Rank.values(), Comparator.comparingInt(Rank::getValue));
        int streak = 0;
        // check for Ace at the beginning of the streak, since it can be the very lowest or very highest card
        if (countedRanks.get(Rank.ACE) >= 1) {
            streak++;
        }
        for (Rank rank : sortedRanks) {
            if (countedRanks.get(rank) >= 1) {
                streak++;
                if(streak >= 5) {
                    return true;
                }
            } else {
                streak = 0;
            }
        }
        return false;
    }

    public static boolean containsFourOfAKind(Card[] cards) {
        HashMap<Rank, Integer> countedRanks = countRanks(cards);
        for (int rankCount : countedRanks.values()) {
            if (rankCount == 4) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean containsFullHouse(Card[] cards) {
        HashMap<Rank, Integer> countedRanks = countRanks(cards);
        boolean containsThreeOfAKind = false;
        boolean containsTwoOfAKind = false;
        for (int rankCount : countedRanks.values()) {
            if (rankCount == 3) {
                if(containsThreeOfAKind || containsTwoOfAKind) {
                    return true;
                } else {
                    containsThreeOfAKind = true;
                }
            }
            if (rankCount == 2) {
                if (containsThreeOfAKind) {
                    return true;
                } else {
                    containsTwoOfAKind = true;
                }
            }
        }
        return false;
    }
    
    public static boolean containsFlush(Card[] cards) {
        HashMap<Suit, Integer> countedSuits = countSuits(cards);
        for (int rankCount : countedSuits.values()) {
            if (rankCount >= 5) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean containsStraight(Card[] cards) {
        HashMap<Rank, Integer> countedRanks = countRanks(cards);
        Rank[] sortedRanks = Rank.values();
        Arrays.sort(Rank.values(), Comparator.comparingInt(Rank::getValue));

        int streak = 0;
        // check for Ace at the beginning of the streak, since it can be the very lowest or very highest card
        if (countedRanks.get(Rank.ACE) >= 1) {
            streak++;
        }
        for (Rank rank : sortedRanks) {
            if (countedRanks.get(rank) >= 1) {
                streak++;
                if(streak >= 5) {
                    return true;
                }
            } else {
                streak = 0;
            }
        }
        return false;
    }
    
    public static boolean containsThreeOfAKind(Card[] cards) {
        HashMap<Rank, Integer> countedRanks = countRanks(cards);
        for (int rankCount : countedRanks.values()) {
            if (rankCount >= 3) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean containsTwoPairs(Card[] cards) {
        HashMap<Rank, Integer> countedRanks = countRanks(cards);
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
    
    public static boolean containsPair(Card[] cards) {
        HashMap<Rank, Integer> countedRanks = countRanks(cards);
        for (int rankCount : countedRanks.values()) {
            if (rankCount == 2) {
                return true;
            }
        }
        return false;
    }



    /*
     * merges the cards from the hand and the board into a single array
     * @param an array of 5 cards (those are the public board cards)
     * @param handCard1 one card of the players hand
     * @param handCard2 the other card of the players hand
     * @return an array of 7 cards, which includes the 5 cards from the board and the 2 cards from the player hand
     */
    //TODO maybe move this method elsewhere, if we don't use it here...
    public static Card[] mergeHandAndBoard(Card[] board, Card handCard1, Card handCard2) {
        //
        Card[] mergedHandAndBoard = new Card[7];
        System.arraycopy(board, 0, mergedHandAndBoard, 0, board.length);
        mergedHandAndBoard[5] = handCard1;
        mergedHandAndBoard[6] = handCard2;
        return mergedHandAndBoard;
    }

    /*
     * Counts how many cards of each rank there are
     * @param mergedHandAndBoard Card-Array of 7 cards, whose ranks will be counted
     * @return a HashMap<Rank, Integer> with the number of cards for each rank. This should add up to 7 again
     */
    public static HashMap<Rank, Integer> countRanks(Card[] mergedHandAndBoard) {
        HashMap<Rank, Integer> rankCounter = new HashMap<>();
        for (Rank rank: Rank.values()) {
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
     * @param mergedHandAndBoard Card-Array of 7 cards, whose suits will be counted
     * @return a HashMap<Suit, Integer> with the number of cards for each suit. This should add up to 7 again
     */
    public static HashMap<Suit, Integer> countSuits(Card[] mergedHandAndBoard) {
        HashMap<Suit, Integer> suitCounter = new HashMap<>();
        for (Suit suit: Suit.values()) {
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

    public static void main(String[] args) {
        //just for testing purposes, main method can be deleted
        Card card1 = new Card(Suit.SPADES, Rank.TWO);
        Card card2 = new Card(Suit.CLUBS, Rank.FOUR);
        Card card3 = new Card(Suit.HEARTS, Rank.FIVE);
        Card card4 = new Card(Suit.HEARTS, Rank.TEN);
        Card card5 = new Card(Suit.HEARTS, Rank.JACK);
        Card card6 = new Card(Suit.HEARTS, Rank.QUEEN);
        Card card7 = new Card(Suit.HEARTS, Rank.KING);
        Card[] cards = {card1, card2, card3, card4, card5, card6, card7};

        System.out.println(containsRoyalFlush(cards));
    }
}
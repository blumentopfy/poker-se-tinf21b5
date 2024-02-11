package com.palcas.poker.game;

import java.util.HashMap;

import com.palcas.poker.display.CardDisplay.Rank;
import com.palcas.poker.display.CardDisplay.Suit;

public class HandEvaluator {
    
    public static boolean containsRoyalFlush(Card[] board, Card handCard1, Card handCard2) {
        Card[] setOfHandAndBoard = mergeHandAndBoard(board, handCard1, handCard2);
        // Logic to check if the hand contains royal flush
        // Return true if it does, false otherwise
        return false;
    }

    public static boolean containsStraightFlush(Card[] board, Card handCard1, Card handCard2) {
        Card[] setOfHandAndBoard = mergeHandAndBoard(board, handCard1, handCard2);
        // Logic to check if the hand contains straight flush
        // Return true if it does, false otherwise
        return false;
    }

    public static boolean containsFourOfAKind(Card[] board, Card handCard1, Card handCard2) {
        Card[] setOfHandAndBoard = mergeHandAndBoard(board, handCard1, handCard2);
        HashMap<Rank, Integer> countedRanks = countRanks(setOfHandAndBoard);
        for (int rankCount : countedRanks.values()) {
            if (rankCount == 4) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean containsFullHouse(Card[] board, Card handCard1, Card handCard2) {
        Card[] setOfHandAndBoard = mergeHandAndBoard(board, handCard1, handCard2);
        // Logic to check if the hand contains a full house
        // Return true if it does, false otherwise
        return false;
    }
    
    public static boolean containsFlush(Card[] board, Card handCard1, Card handCard2) {
        Card[] setOfHandAndBoard = mergeHandAndBoard(board, handCard1, handCard2);
        // Logic to check if the hand contains a flush
        // Return true if it does, false otherwise
        return false;
    }
    
    public static boolean containsStraight(Card[] board, Card handCard1, Card handCard2) {
        Card[] setOfHandAndBoard = mergeHandAndBoard(board, handCard1, handCard2);
        // Logic to check if the hand forms a straight
        // Return true if it does, false otherwise
        return false;
    }
    
    public static boolean containsThreeOfAKind(Card[] board, Card handCard1, Card handCard2) {
        Card[] setOfHandAndBoard = mergeHandAndBoard(board, handCard1, handCard2);
        HashMap<Rank, Integer> countedRanks = countRanks(setOfHandAndBoard);
        for (int rankCount : countedRanks.values()) {
            if (rankCount == 3) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean containsTwoPair(Card[] board, Card handCard1, Card handCard2) {
        Card[] setOfHandAndBoard = mergeHandAndBoard(board, handCard1, handCard2);
        // Logic to check if the hand contains two pairs
        // Return true if it does, false otherwise
        return false;
    }
    
    public static boolean containsPair(Card[] board, Card handCard1, Card handCard2) {
        Card[] setOfHandAndBoard = mergeHandAndBoard(board, handCard1, handCard2);
        HashMap<Rank, Integer> countedRanks = countRanks(setOfHandAndBoard);
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
    public static Card[] mergeHandAndBoard(Card[] board, Card handCard1, Card handCard2) {
        // 
        Card[] mergedHandAndBoard = new Card[7];
        for (int i = 0; i < board.length; i++) {
            mergedHandAndBoard[i] = board[i];
        }
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
            for (int i = 0; i < mergedHandAndBoard.length; i++) {
                if (mergedHandAndBoard[i].getRank() == rank) {
                    int currentValue = rankCounter.get(rank);
                    rankCounter.put(rank, currentValue + 1);
                }
            }
        }
        return rankCounter;
    }

    public static void main(String[] args) {
        //just for testing purposes, main can be deleted
        Card card1 = new Card(Suit.DIAMONDS, Rank.ACE);
        Card card2 = new Card(Suit.SPADES, Rank.QUEEN);
        Card card3 = new Card(Suit.HEARTS, Rank.ACE);
        Card card4 = new Card(Suit.CLUBS, Rank.THREE);
        Card card5 = new Card(Suit.SPADES, Rank.KING);
        Card[] board = {card1, card2, card3, card4, card5};

        Card handCard1 = new Card(Suit.CLUBS, Rank.TWO);
        Card handCard2 = new Card(Suit.SPADES, Rank.ACE);
        System.out.println(containsThreeOfAKind(board, handCard1, handCard2));
    }
}

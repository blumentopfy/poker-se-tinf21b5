package com.palcas.poker;

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
        // Logic to check if the hand contains four of a kind
        // Return true if it does, false otherwise
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
        // Logic to check if the hand contains three of a kind
        // Return true if it does, false otherwise
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
        // Logic to check if the hand contains a pair
        // Return true if it does, false otherwise
        return false;
    }
    
    


    public static Card[] mergeHandAndBoard(Card[] board, Card handCard1, Card handCard2) {
        // merges the cards from the hand and the board into a single array
        Card[] mergedHandAndBoard = new Card[7];
        for (int i = 0; i < board.length; i++) {
            mergedHandAndBoard[i] = board[i];
        }
        mergedHandAndBoard[5] = handCard1;
        mergedHandAndBoard[6] = handCard2;
        return mergedHandAndBoard;
    }
}

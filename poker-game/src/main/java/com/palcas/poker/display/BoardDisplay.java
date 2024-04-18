package com.palcas.poker.display;

import java.util.List;

import com.palcas.poker.game.Card;

public class BoardDisplay {

        public static void displayColoredPokerBoard(Card[] board) {
        String neutralColorCode = "\u001B[0m";
        System.out.println("   ┌─────────┐   ┌─────────┐   ┌─────────┐   ┌─────────┐   ┌─────────┐");
        System.out.println("   │         │   │         │   │         │   │         │   │         │");
        System.out.println(generateRankLineString(board));
        System.out.println("   │         │   │         │   │         │   │         │   │         │");
        System.out.println(generateSuiteLineString(board));
        System.out.println("   │         │   │         │   │         │   │         │   │         │");
        System.out.println("   └─────────┘   └─────────┘   └─────────┘   └─────────┘   └─────────┘");
    }

    public static String generateRankLineString(Card[] board) {
        String neutralColorCode = "\u001B[0m";
        String rankLineString = "";
        for (int i = 0; i < board.length; i++) {
            rankLineString += "   │  " + CardDisplay.getColorCode(board[i]) + CardDisplay.getFormattedRank(board[i]) + neutralColorCode + "  │";
        }
        return rankLineString;
    }

    public static String generateSuiteLineString(Card[] board) {
        String neutralColorCode = "\u001B[0m";
        String suiteLineString = "";
        for (int i = 0; i < board.length; i++) {
            suiteLineString += "   │ " + CardDisplay.getColorCode(board[i]) + CardDisplay.getFormattedSuit(board[i]) + neutralColorCode + " │";
        }
        return suiteLineString;
    }

    public static void printPreFlopBoard(String state, List<Card> cards) {
        DisplayElements.clearConsole();

        DisplayElements.printSeperator();
        System.out.println("Currently: " + state);
        DisplayElements.printSeperator();

        System.out.println("These are your pocket cards:");
        HandDisplay.displayColoredPokerHand(cards.get(0), cards.get(1));
        DisplayElements.printSeperator();
    }

    public static void printPostFlopBoard(String state, List<Card> mainPlayerCards, List<Card> communityCards) {
        DisplayElements.clearConsole();

        DisplayElements.printSeperator();
        System.out.println("Currently: " + state);
        DisplayElements.printSeperator();

        System.out.println("These are your pocket cards:");
        HandDisplay.displayColoredPokerHand(mainPlayerCards.get(0), mainPlayerCards.get(1));
        DisplayElements.printSeperator();

        System.out.println("These are the community cards:");
        displayColoredPokerBoard(communityCards.toArray(new Card[0]));
        DisplayElements.printSeperator();
    }

}

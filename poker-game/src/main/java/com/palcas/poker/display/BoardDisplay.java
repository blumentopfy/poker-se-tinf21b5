package com.palcas.poker.display;

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
        for (int i = 0; i < 5; i++) {
            rankLineString += "   │  " + CardDisplay.getColorCode(board[i]) + CardDisplay.getFormattedRank(board[i]) + neutralColorCode + "  │";
        }
        return rankLineString;
    }

    public static String generateSuiteLineString(Card[] board) {
        String neutralColorCode = "\u001B[0m";
        String suiteLineString = "";
        for (int i = 0; i < 5; i++) {
            suiteLineString += "   │ " + CardDisplay.getColorCode(board[i]) + CardDisplay.getFormattedSuit(board[i]) + neutralColorCode + " │";
        }
        return suiteLineString;
    }
}

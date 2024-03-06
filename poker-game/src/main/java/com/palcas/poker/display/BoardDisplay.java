package com.palcas.poker.display;



import com.palcas.poker.model.Rank;
import com.palcas.poker.model.Suit;
import com.palcas.poker.game.Card;

public class BoardDisplay {

    public static void main(String[] args) {
        //just for testing purposes, main can be deleted
        Card card1 = new Card(Suit.DIAMONDS, Rank.FIVE);
        Card card2 = new Card(Suit.SPADES, Rank.QUEEN);
        Card card3 = new Card(Suit.HEARTS, Rank.ACE);
        Card card4 = new Card(Suit.CLUBS, Rank.THREE);
        Card card5 = new Card(Suit.SPADES, Rank.KING);
        Card[] board = {card1, card2, card3, card4, card5};

        displayColoredPokerBoard(board);
    }

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

package com.palcas.poker.display;

import com.palcas.poker.game.Card;

public class HandDisplay {

    public static void displayColoredPokerHand(Card card1, Card card2) {
        String firstCardColorCode = CardDisplay.getColorCode(card1);
        String secondCardColorCode = CardDisplay.getColorCode(card2);

        String neutralColorCode = "\u001B[0m";
        System.out.println("   ┌─────────┐   ┌─────────┐");
        System.out.println("   │         │   │         │");
        System.out.println("   │  " + firstCardColorCode + CardDisplay.getFormattedRank(card1) + neutralColorCode + "  │   │  " + secondCardColorCode +  CardDisplay.getFormattedRank(card2) + neutralColorCode + "  │");
        System.out.println("   │         │   │         │");
        System.out.println("   │ " + secondCardColorCode + CardDisplay.getFormattedSuit(card1) + neutralColorCode + " │   │ " + secondCardColorCode + CardDisplay.getFormattedSuit(card2) + neutralColorCode + " │");
        System.out.println("   │         │   │         │");
        System.out.println("   └─────────┘   └─────────┘");
    }

}

package com.palcas.poker.display;

import com.palcas.poker.model.Rank;
import com.palcas.poker.model.Suit;
import com.palcas.poker.game.Card;

public class HandDisplay {

    public static void main(String[] args) {
        //just for testing purposes, main can be deleted
        Card card1 = new Card(Suit.DIAMONDS, Rank.FIVE);
        Card card2 = new Card(Suit.SPADES, Rank.QUEEN);

        displayColoredPokerHand(card1, card2);
    }

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

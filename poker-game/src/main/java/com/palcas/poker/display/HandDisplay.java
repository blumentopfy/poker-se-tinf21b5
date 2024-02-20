package com.palcas.poker.display;

import com.palcas.poker.Rank;
import com.palcas.poker.Suit;
import com.palcas.poker.game.Card;

public class HandDisplay {

    public static void main(String[] args) {
        //just for testing purposes, main can be deleted
        Card card1 = new Card(Suit.DIAMONDS, Rank.FIVE);
        Card card2 = new Card(Suit.SPADES, Rank.QUEEN);

        displayColoredPokerHand(card1, card2);
    }

    public static void displayColoredPokerHand(Card card1, Card card2) {
        String neutralColorCode = "\u001B[0m";
        System.out.println("   ┌─────────┐   ┌─────────┐");
        System.out.println("   │         │   │         │");
        System.out.println("   │  " + card1.getSuit().getColorCode() + card1.getRank().getFormattedName() + neutralColorCode + "  │   │  " + card2.getSuit().getColorCode() +  card2.getRank().getFormattedName() + neutralColorCode + "  │");
        System.out.println("   │         │   │         │");
        System.out.println("   │ " + card1.getSuit().getColorCode() + card1.getSuit().getFormattedName() + neutralColorCode + " │   │ " + card2.getSuit().getColorCode() + card2.getSuit().getFormattedName() + neutralColorCode + " │");
        System.out.println("   │         │   │         │");
        System.out.println("   └─────────┘   └─────────┘");
    }

}

package com.palcas.poker.display;

import com.palcas.poker.Card;
import com.palcas.poker.CardEnums.Rank;
import com.palcas.poker.CardEnums.Suit;

public class HandDisplay {
    CardDisplay cardDisplay;

    public HandDisplay() {
        cardDisplay = new CardDisplay();
    }

    public static void main(String[] args) {
        Card card1 = new Card(Suit.DIAMONDS, Rank.FIVE);
        Card card2 = new Card(Suit.SPADES, Rank.QUEEN);


        displayColoredPokerHand(card1, card2);
    }

    //TODO das ding muss noch variabel einsetzbar gemacht werden
    public static void displayColoredPokerHand(Card card1, Card card2) {
        String neutralColorCode = "\u001B[0m";
        System.out.println("  ┌─────────┐   ┌─────────┐");
        System.out.println("  │         │   │         │");
        System.out.println("  │  " + card1.getSuit().getColorCode() + card1.getRank().getFormattedName() + neutralColorCode + "  │   │  " + card2.getSuit().getColorCode() +  card2.getRank().getFormattedName() + neutralColorCode + "  │");
        System.out.println("  │         │   │         │");
        System.out.println("  │ " + card1.getSuit().getColorCode() + card1.getSuit().getFormattedName() + neutralColorCode + " │   │ " + card2.getSuit().getColorCode() + card2.getSuit().getFormattedName() + neutralColorCode + " │");
        System.out.println("  │         │   │         │");
        System.out.println("  └─────────┘   └─────────┘");
    }

}

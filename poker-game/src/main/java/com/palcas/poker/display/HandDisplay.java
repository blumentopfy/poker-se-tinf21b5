package com.palcas.poker.display;

public class HandDisplay {
    CardDisplay cardDisplay;

    public HandDisplay() {
        cardDisplay = new CardDisplay();
    }

    public static void main(String[] args) {
        displayColoredPokerHand("\u2660", "5", "♥", "K");
    }

    // erstes Konzept, wie die Darstellung einer Hand möglich wäre, genaue Details müssen besprochen werden 
    //TODO das ding muss noch variabel einsetzbar gemacht werden
    public static void displayColoredPokerHand(String suit1, String rank1, String suit2, String rank2) {
        System.out.println("  ┌─────────┐   ┌─────────┐");
        System.out.println("  │         │   │         │");
        System.out.println("  │    \u001B[31m" + suit1 + "\u001B[0m    │   │    " + suit2 + "    │");
        System.out.println("  │         │   │         │");
        System.out.println("  │    \u001B[31m" + rank1 + "\u001B[0m    │   │    " + rank2 + "    │");
        System.out.println("  │         │   │         │");
        System.out.println("  └─────────┘   └─────────┘");
    }

    public static void displayColoredPokerHand2(String suit1, String rank1, String suit2, String rank2) {
        System.out.println("  ┌─────────┐   ┌─────────┐");
        System.out.println("  │         │   │         │");
        System.out.println("  │         │   │         │");
        System.out.println("  │         │   │         │");
        System.out.println("  │  heart  │   │         │");
        System.out.println("  │         │   │         │");
        System.out.println("  └─────────┘   └─────────┘");
    }

}

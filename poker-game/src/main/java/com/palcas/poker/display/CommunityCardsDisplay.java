package com.palcas.poker.display;

import java.util.List;

import com.palcas.poker.game.Card;

public class CommunityCardsDisplay {

    public static void displayColoredCommunityCards(Card[] communityCards) {
        String neutralColorCode = "\u001B[0m";
        System.out.println("   ┌─────────┐   ┌─────────┐   ┌─────────┐   ┌─────────┐   ┌─────────┐");
        System.out.println("   │         │   │         │   │         │   │         │   │         │");
        System.out.println(generateRankLineString(communityCards));
        System.out.println("   │         │   │         │   │         │   │         │   │         │");
        System.out.println(generateSuiteLineString(communityCards));
        System.out.println("   │         │   │         │   │         │   │         │   │         │");
        System.out.println("   └─────────┘   └─────────┘   └─────────┘   └─────────┘   └─────────┘");
    }

    public static String generateRankLineString(Card[] communityCards) {
        String neutralColorCode = "\u001B[0m";
        String rankLineString = "";
        for (int i = 0; i < communityCards.length; i++) {
            rankLineString += "   │  " + CardDisplay.getColorCode(communityCards[i]) + CardDisplay.getFormattedRank(communityCards[i])
                    + neutralColorCode + "  │";
        }
        if(communityCards.length == 3) {
            rankLineString += "   │    ?    │   │    ?    │";
        }
        if(communityCards.length == 4) {
            rankLineString += "   │    ?    │";
        }
        return rankLineString;
    }

    public static String generateSuiteLineString(Card[] communityCards) {
        String neutralColorCode = "\u001B[0m";
        String suiteLineString = "";
        for (int i = 0; i < communityCards.length; i++) {
            suiteLineString += "   │ " + CardDisplay.getColorCode(communityCards[i]) + CardDisplay.getFormattedSuit(communityCards[i])
                    + neutralColorCode + " │";
        }
        if(communityCards.length == 3) {
            suiteLineString += "   │    ?    │   │    ?    │";
        }
        if(communityCards.length == 4) {
            suiteLineString += "   │    ?    │";
        }
        return suiteLineString;
    }

    public static void printPreFlopBoard(String state, List<Card> cards) {
        DisplayElements.printSeperator();
        System.out.println("Currently: " + state);
        DisplayElements.printSeperator();

        System.out.println("These are your pocket cards:");
        PocketDisplay.displayColoredPocket(cards.get(0), cards.get(1));
        DisplayElements.printSeperator();
    }

    public static void printPostFlopBoard(String state, List<Card> mainPlayerCards, List<Card> communityCards) {
        DisplayElements.clearConsole();

        DisplayElements.printSeperator();
        System.out.println("Currently: " + state);
        DisplayElements.printSeperator();

        System.out.println("These are your pocket cards:");
        PocketDisplay.displayColoredPocket(mainPlayerCards.get(0), mainPlayerCards.get(1));
        DisplayElements.printSeperator();

        System.out.println("These are the community cards:");
        displayColoredCommunityCards(communityCards.toArray(new Card[0]));
        DisplayElements.printSeperator();
    }
}

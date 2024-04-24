package com.palcas.poker.display;

import java.util.Scanner;

import com.palcas.poker.game.GameState;

public class TutorialDisplay {
    public static void tutorialGreeting(Scanner scanner) {
        DisplayElements.clearWithSeperator();
        System.out.println("Welcome to the Tutorial of PalCas Poker!");
        DisplayElements.printSeperator();
        System.out.println("In this tutorial, we will explore the game of Poker by showing you how to play \"Texas Hold'Em\"");
        DisplayElements.printSeperator();
        System.out.println("(press enter to continue the tutorial)");
        scanner.nextLine();
    }

    public static void printPlayerCountInfo(Scanner scanner) {
        DisplayElements.clearWithSeperator();
        System.out.println("Usually, you would be prompted for how many players you want to play with at this stage");
        System.out.println("In this tutorial however, we will play with 3 additional players.");
        DisplayElements.printSeperator();
        System.out.println("(press enter to continue the tutorial)");
        DisplayElements.printSeperator();
        scanner.nextLine();
    }

    public static void printStakesInfo(Scanner scanner) {
        DisplayElements.clearWithSeperator();
        System.out.println("BLINDS");
        DisplayElements.printSeperator();
        System.out.println("Your next choice would be to pick your preffered Blind-setup");
        System.out.println("Blinds in Poker serve a specific purpose.");
        System.out.println("They exist so every round of poker at least has some chips to play for.");
        System.out.println("Additionally, when a player wants to bet or raise (terms further explainer later), they at least have to bet the amount of the big blind");
        System.out.println("For starters, we reccomend playing a low-stakes game, meaning a low big blind");
        System.out.println("The big blind will also increase regularly");
        System.out.println("For the purpose of this tutorial, we have selected a low-stakes game for you.");
        DisplayElements.printSeperator();
        System.out.println("(press enter to continue the tutorial)");
        DisplayElements.printSeperator();
        scanner.nextLine();
    }

    public static void printPlayers(GameState gameState) {
        DisplayElements.clearWithSeperator();
        System.out.println("PLAYERS");
        DisplayElements.printSeperator();
        System.out.println("In this game, you will be playing against 3 other players.");
        System.out.println("The players in the game are:");
        gameState.players.forEach(player -> System.out.println(player.getName()));
        System.out.println("At the start of the game, every player will receive the same amount of chips (in this case 1000).");
        DisplayElements.printSeperator();
        System.out.println("(press enter to continue the tutorial)");
        DisplayElements.printSeperator();
    }
}

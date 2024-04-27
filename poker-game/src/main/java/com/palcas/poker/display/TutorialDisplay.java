package com.palcas.poker.display;

import java.util.List;
import java.util.Scanner;

import com.palcas.poker.game.Card;
import com.palcas.poker.game.GameState;

public class TutorialDisplay {
    public static void tutorialGreeting(Scanner scanner) {
        DisplayElements.clearWithSeperator();
        System.out.println("Welcome to the Tutorial of PalCas Poker!");
        DisplayElements.printSeperator();
        System.out.println(
                "In this tutorial, we will explore the game of Poker by showing you how to play \"Texas Hold'Em\"");
        DisplayElements.printSeperator();
        System.out.println("(press enter to continue the tutorial)");
        scanner.nextLine();
    }

    public static void playerCountInfo(Scanner scanner) {
        DisplayElements.clearWithSeperator();
        System.out.println("Usually, you would be prompted for how many players you want to play with at this stage\n"
                + "In this tutorial however, we will play with 3 additional players.");
        DisplayElements.printSeperator();
        System.out.println("(press enter to continue the tutorial)");
        DisplayElements.printSeperator();
        scanner.nextLine();
    }

    public static void stakesInfo(Scanner scanner) {
        DisplayElements.clearWithSeperator();
        System.out.println("BLINDS");
        DisplayElements.printSeperator();
        System.out.println("Your next choice would be to pick your preferred Blind-setup\n"
                + "Blinds in Poker serve a specific purpose.\n"
                + "They exist so every round of poker at least has some chips to play for.\n"
                + "Additionally, when a player wants to bet or raise (terms further explained later), they at least have to bet the amount of the big blind\n"
                + "For starters, we recommend playing a low-stakes game, meaning a low big blind\n"
                + "The big blind will also increase regularly\n"
                + "For the purpose of this tutorial, we have selected a low-stakes game for you.");
        DisplayElements.printSeperator();
        System.out.println("(press enter to continue the tutorial)");
        DisplayElements.printSeperator();
        scanner.nextLine();
    }

    public static void introducePlayers(GameState gameState, Scanner scanner) {
        DisplayElements.clearWithSeperator();
        System.out.println("PLAYERS");
        DisplayElements.printSeperator();
        System.out.println("In this game, you will be playing against 3 other players.");
        System.out.println("The players in the game are:");
        gameState.players.forEach(player -> {
            System.out.println(player.getName() + " with " + player.getChips() + " chips.");
        });
        System.out.println(
                "At the start of the game, every player will receive the same amount of chips (in this case 1000).");
        DisplayElements.printSeperator();
        System.out.println("(press enter to continue the tutorial)");
        DisplayElements.printSeperator();
        scanner.nextLine();
    }

    public static void deckInfo(Scanner scanner) {
        DisplayElements.clearWithSeperator();
        System.out.println("DECK");
        DisplayElements.printSeperator();

        System.out.println("In Poker, you play with a standard deck of 52 cards.\n"
                + "The deck consists of 4 suits: Hearts, Diamonds, Clubs and Spades.\n"
                + "Each suit has 13 cards: 2, 3, 4, 5, 6, 7, 8, 9, 10, Jack, Queen, King, Ace\n"
                + "The goal of the game is to make the best possible hand out of the cards available to you.\n"
                + "The hands are ranked from highest to lowest:\n"
                + "Royal Flush, Straight Flush, Four of a Kind, Full House, Flush, Straight, Three of a Kind, Two Pair, One Pair, High Card\n"
                + "This is what each of these terms mean:\n"
                + "Royal Flush: A, K, Q, J, 10 of the same suit\n"
                + "Straight flush: 5 cards in a row of the same suit\n"
                + "Four of a Kind: 4 cards of the same rank\n"
                + "Full House: 3 cards of the same rank and 2 cards of the same rank\n"
                + "Flush: 5 cards of the same suit\n"
                + "Straight: 5 cards in a row\n"
                + "Three of a Kind: 3 cards of the same rank\n"
                + "Two Pair: 2 cards of the same rank and 2 cards of the same rank\n"
                + "One Pair: 2 cards of the same rank\n"
                + "High Card: The highest card in your hand\n"
                + "The player with the best hand wins the round and takes the pot.");

        DisplayElements.printSeperator();
        System.out.println("(press enter to continue the tutorial)");
        DisplayElements.printSeperator();
        scanner.nextLine();
    }

    public static void roundInfo(Scanner scanner) {
        DisplayElements.clearWithSeperator();
        System.out.println("ROUNDS");
        DisplayElements.printSeperator();

        System.out.println("In Poker, a round consists of 4 phases:\n"
                + "1. Pre-flop: Each player is dealt 2 cards \"face down\". The player after the big blind starts the betting.\n"
                + "2. Flop: 3 cards are dealt face up in the middle of the table\n"
                + "3. Turn: 1 card is dealt face up next to the flop\n"
                + "4. River: 1 card is dealt face up next to the turn\n"
                + "After every player recieves their cards, they have the option to either fold, call or raise.\n"
                + "Raising means you increase the bet, calling means you match the bet and folding means you forfeit the round.\n"
                + "The first player to bet is the player after the big blind.\n"
                + "After the flop, turn and river, the player after the dealer starts the betting.\n"
                + "The player with the best hand wins the round and takes the pot (meaning all bets accumulated over the last rounds).");

        DisplayElements.printSeperator();
        System.out.println("(press enter to continue the tutorial)");
        DisplayElements.printSeperator();
        scanner.nextLine();
    }

    public static void startRound(GameState state, Scanner scanner) {
        DisplayElements.clearWithSeperator();
        System.out.println("Enough talk, let's start with your first round of Texas Hold'Em!");
        System.out.println("The big blind is 20 and the small blind is 10.");
        DisplayElements.printSeperator();
        System.out.println("(press enter to start the round)");
        DisplayElements.printSeperator();
        scanner.nextLine();
    }

    public static void printMainPlayerPocket(List<Card> cards, Scanner scanner) {
        System.out.println("These are your pocket cards:");
        HandDisplay.displayColoredPokerHand(cards.get(0), cards.get(1));
        System.out.println("That's a pretty decent pocket! Let's see how the round plays out.");
        DisplayElements.printSeperator();
        System.out.println("(press enter to continue the Tutorial)");
        DisplayElements.printSeperator();
        scanner.nextLine();
    }
}

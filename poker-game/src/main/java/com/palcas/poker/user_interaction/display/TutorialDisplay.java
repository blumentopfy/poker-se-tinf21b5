package com.palcas.poker.user_interaction.display;

import java.util.List;
import java.util.Scanner;

import com.palcas.poker.game.Card;
import com.palcas.poker.game.GameState;
import com.palcas.poker.game.Player;

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
        System.out.println("The order of players playing is:");
        for (int i = 0; i < state.players.size(); i++) {
            Player player = state.players.get(i);
            if (i == 0) {
                System.out.println(player.getName() + " sets the small blind.");
            } else if (i == 1) {
                System.out.println(player.getName() + " sets the big blind.");
            } else if (i == 2) {
                System.out.println(player.getName() + " will be the first to bet this round.");
            } else {
                System.out.println("Finally, you will be the last to bet this round.");
            }
        }
        DisplayElements.printSeperator();
        System.out.println("(press enter to start the round)");
        DisplayElements.printSeperator();
        scanner.nextLine();
    }

    public static void printMainPlayerPocket(List<Card> cards, Scanner scanner) {
        DisplayElements.clearWithSeperator();
        System.out.println("These are your pocket cards:");
        PocketDisplay.displayColoredPocket(cards.get(0), cards.get(1));
        System.out.println("That's a pretty decent pocket! Let's see how the round plays out.");
        DisplayElements.printSeperator();
        System.out.println("(press enter to continue the Tutorial)");
        DisplayElements.printSeperator();
        scanner.nextLine();
    }

    public static void explainFirstBet(GameState gamestate, Scanner scanner) {
        DisplayElements.printSeperator();
        System.out.println("The first player to bet is the player after the big blind.");
        String playerWhoBet = gamestate.players.get(2).getName();
        System.out.println("In this case, the player after the big blind is " + playerWhoBet + ".");
        System.out.println(playerWhoBet + " has decided to place a bet of 20 chips.");
        System.out.println("You can now either fold, call or raise.");
        DisplayElements.printSeperator();
        System.out.println("(press enter to continue the Tutorial)");
        DisplayElements.printSeperator();
        scanner.nextLine();
    }

    public static void printPreFlopBotReaction(GameState gameState, Scanner scanner) {
        DisplayElements.clearWithSeperator();
        System.out.println("Let's see how the other players react to your raise.");
        DisplayElements.printSeperator();
        Player firstBot = gameState.players.get(0);
        Player secondBot = gameState.players.get(1);
        Player thirdBot = gameState.players.get(2);
        System.out.println(firstBot.getName() + " has decided to fold.");
        System.out.println(secondBot.getName() + " has also decided to fold.");
        System.out.println(thirdBot.getName() + " has decided to call your raise.");
        System.out.println("(press enter to continue the Tutorial)");
        DisplayElements.printSeperator();
        scanner.nextLine();
    }

    public static void explainFlop(GameState gameState, Scanner scanner) {
        DisplayElements.printSeperator();
        System.out.println("Now that the first betting round is over, let's see the flop.");
        System.out.println("The flop consists of the 3 cards you can see above");
        System.out.println("The flop is a crucial part of the game, as it can make or break your hand.");
        System.out.println(
                "As you can see, you have already hit Two Pairs with great ranks. This is looking good for you!");
        DisplayElements.printSeperator();
        System.out.println("(press enter to continue the Tutorial)");
        DisplayElements.printSeperator();
        scanner.nextLine();
    }

    public static void explainFlopBotAction(GameState gameState, Scanner scanner) {
        DisplayElements.printSeperator();
        System.out.println("The last player in the game has decided to check.");
        System.out.println("You could now either Raise or also check");
        System.out.println("Since you would not want to scare the other player off, we recommend you to check.");
    }

    public static void explainTurn(GameState gameState, Scanner scanner) {
        DisplayElements.printSeperator();
        System.out.println("The last player in the game has again decided to check.");
        System.out.println("You could now either Raise or also check");
        System.out.println("This is essentially the same situation as last round.");
        System.out.println("You have not hit anything new yet.");
        System.out.println("However, most of the combinations of cards would lead to a worse hand than you have.");
        System.out.println("Since you would not want to scare the other player off, we recommend you to check.");
        DisplayElements.printSeperator();
        System.out.println("(press enter to continue the Tutorial)");
        DisplayElements.printSeperator();
        scanner.nextLine();
    }

    public static void explainRiver(GameState gameState, Scanner scanner) {
        DisplayElements.printSeperator();
        System.out.println("The last player in the game has again decided to check a final time.");
        System.out.println("This indicates that the he or she might not have a gread hand here.");
        System.out.println("You could now either Raise or also check");
        System.out.println("Since you have an amazing hand, we recommend you to raise.");
        System.out.println("Most of the time, the other player will fold, but you will have to take that risk.");
        DisplayElements.printSeperator();
        System.out.println("(press enter to continue the Tutorial)");
        DisplayElements.printSeperator();
        scanner.nextLine();
    }

    public static void showdownInfo(GameState gameState, Scanner scanner) {
        DisplayElements.clearWithSeperator();
        System.out.println(gameState.players.get(2).getName() + " has decided to call!");
        System.out.println("It's time for the showdown!");
        DisplayElements.printSeperator();
        System.out.println("These are the cards of " + gameState.players.get(2).getName() + ":");
        Card botFirstCard = gameState.players.get(2).getPocket().getCards().get(0);
        Card botSecondCard = gameState.players.get(2).getPocket().getCards().get(1);
        PocketDisplay.displayColoredPocket(botFirstCard, botSecondCard);
        DisplayElements.printSeperator();
        System.out.println("These are your cards:");
        Card playerFirstCard = gameState.players.get(3).getPocket().getCards().get(0);
        Card playerSecondCard = gameState.players.get(3).getPocket().getCards().get(1);
        PocketDisplay.displayColoredPocket(playerFirstCard, playerSecondCard);
        DisplayElements.printSeperator();
        System.out.println("This means with your Full House, you win this round and with it the pot of " + gameState.pot
                + " chips!");
        System.out.println("(press enter to finish the Tutorial)");
        DisplayElements.printSeperator();
        scanner.nextLine();
    }
}

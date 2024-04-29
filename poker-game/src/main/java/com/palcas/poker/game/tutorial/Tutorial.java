package com.palcas.poker.game.tutorial;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

import com.palcas.poker.constants.PlayerNames;
import com.palcas.poker.display.BoardDisplay;
import com.palcas.poker.display.DisplayElements;
import com.palcas.poker.display.HandDisplay;
import com.palcas.poker.display.TutorialDisplay;
import com.palcas.poker.game.Card;
import com.palcas.poker.game.Deck;
import com.palcas.poker.game.GameState;
import com.palcas.poker.game.Player;
import com.palcas.poker.game.Pocket;
import com.palcas.poker.game.variants.TexasHoldEm.TexasHoldEmPocket;
import com.palcas.poker.input.BetChoice;
import com.palcas.poker.model.PlayerState;
import com.palcas.poker.model.Rank;
import com.palcas.poker.model.Suit;

public class Tutorial {
    private static GameState gameState;
    private static Player mainPlayer;
    private static List<Card> communityCards;

    public static void playTutorial(Scanner scanner) {
        TutorialDisplay.tutorialGreeting(scanner);

        TutorialDisplay.playerCountInfo(scanner);

        TutorialDisplay.stakesInfo(scanner);

        // Initializes players, deck, pockets, blinds and pot
        initializeGameState();

        TutorialDisplay.introducePlayers(gameState, scanner);

        TutorialDisplay.deckInfo(scanner);

        TutorialDisplay.roundInfo(scanner);

        TutorialDisplay.startRound(gameState, scanner);

        TutorialDisplay.printMainPlayerPocket(gameState.players.get(3).getPocket().getCards(), scanner);

        // First round of betting: main player is last, bot before bets big blind
        gameState.players.get(2).setBet(20);
        TutorialDisplay.explainFirstBet(gameState, scanner);
        int raiseAmount = letPlayerDoFirstBetChoice(scanner, gameState);

        simulatePreFlopBotReaction(raiseAmount);
        TutorialDisplay.printPreFlopBotReaction(gameState, scanner);

        // Flop
        communityCards = initializeCommunityCards();
        BoardDisplay.printPostFlopBoard("FLOP",
                gameState.players.get(3).getPocket().getCards(),
                communityCards);
        TutorialDisplay.explainFlop(gameState, scanner);

        // Second round of betting: main player is last, only bot at index 2 is left
        simulateLastBotChecking();
        TutorialDisplay.explainTurn(gameState, scanner);
        handlePlayerChecking(scanner);

        // Turn
        communityCards.add(new Card(Suit.HEARTS, Rank.SEVEN));
        BoardDisplay.printPostFlopBoard("TURN",
                gameState.players.get(3).getPocket().getCards(),
                communityCards);

        simulateLastBotChecking();
        TutorialDisplay.explainTurn(gameState, scanner);
        handlePlayerChecking(scanner);

        // River
        communityCards.add(new Card(Suit.DIAMONDS, Rank.KING));
        BoardDisplay.printPostFlopBoard("RIVER",
                gameState.players.get(3).getPocket().getCards(),
                communityCards);
        simulateLastBotChecking();
        TutorialDisplay.explainRiver(gameState, scanner);
        raiseAmount = handlePlayerRaising(scanner);
        // simulate last bot calling
        gameState.pot += raiseAmount;

        // Showdown
        TutorialDisplay.showdownInfo(gameState, scanner);

        System.exit(0);
    }

    private static void initializeGameState() {
        Collections.shuffle(PlayerNames.NAMES);
        List<String> playerNames = PlayerNames.NAMES.subList(0, 3);
        ArrayList<Player> players = new ArrayList<>();
        mainPlayer = new Player("You", 1000);

        for (String playerName : playerNames) {
            players.add(new Player(playerName, 1000));
        }
        players.add(mainPlayer);

        Tutorial.gameState = new GameState(mainPlayer, players);
        resetStatesAndBets();

        Pocket pocketPlayerOne = new TexasHoldEmPocket(new Card(Suit.DIAMONDS, Rank.NINE),
                new Card(Suit.HEARTS, Rank.KING));
        Pocket pocketPlayerTwo = new TexasHoldEmPocket(new Card(Suit.SPADES, Rank.ACE),
                new Card(Suit.SPADES, Rank.KING));
        Pocket pocketPlayerThree = new TexasHoldEmPocket(new Card(Suit.DIAMONDS, Rank.ACE),
                new Card(Suit.SPADES, Rank.JACK));
        Pocket pocketMainPlayer = new TexasHoldEmPocket(new Card(Suit.SPADES, Rank.ACE),
                new Card(Suit.SPADES, Rank.KING));

        gameState.players.get(0).setPocket(pocketPlayerOne);
        gameState.players.get(1).setPocket(pocketPlayerTwo);
        gameState.players.get(2).setPocket(pocketPlayerThree);
        gameState.players.get(3).setPocket(pocketMainPlayer);

        gameState.bigBlind = 20;
        gameState.smallBlind = 10;
        setBlinds();

        gameState.setDeck(new Deck().shuffleFullDeck());
    }

    protected static void resetStatesAndBets() {
        for (Player player : gameState.players) {
            player.setState(PlayerState.WAITING_TO_BET);
        }

        gameState.players.stream().forEach(player -> player.setBet(0));
    }

    protected static void setBlinds() {
        DisplayElements.printSeperator();
        if (gameState.players.get(gameState.smallBlindIndex) == mainPlayer) {
            System.out.println("You set the small blind of " + gameState.smallBlind + ".");
        } else {
            System.out.println(gameState.players.get(gameState.smallBlindIndex).getName() + " sets the small blind of "
                    + gameState.smallBlind + ".");
        }
        gameState.players.get(gameState.smallBlindIndex).setBet(gameState.smallBlind);

        if (gameState.players.get(gameState.bigBlindIndex) == mainPlayer) {
            System.out.println("You set the big blind of " + gameState.bigBlind + ".");
        } else {
            System.out.println(gameState.players.get(gameState.bigBlindIndex).getName() + " sets the big blind of "
                    + gameState.bigBlind + ".");
        }
        gameState.players.get(gameState.bigBlindIndex).setBet(gameState.bigBlind);

        SimpleEntry<Player, Integer> playerToHighestBet = new SimpleEntry<>(gameState.players.get(2), 20);
        gameState.setPlayerToHighestBet(playerToHighestBet);
        gameState.pot = gameState.smallBlind + gameState.bigBlind;
    }

    private static int letPlayerDoFirstBetChoice(Scanner scanner, GameState gameState) {
        SimpleEntry<Player, Integer> playerToHighestBet = gameState.getPlayerToHighestBet();
        System.out.println("Please choose an action:");
        System.out.println("1. (C)heck");
        System.out.println("2. (CALL) " + playerToHighestBet.getKey().getName() + "'s bet of 20.");
        System.out.println("3. (R)aise");
        System.out.println("3. (F)old");
        System.out.println(
                "In this instance, we recommend you to Raise, since you have good pocket cards.");
        int raiseAmount = 0;

        while (true) {
            String userInput = scanner.nextLine();

            if (userInput.equals("R")) {
                raiseAmount = raiseChoice(scanner, playerToHighestBet);
                break;
            } else {
                System.out.println("Invalid option! Please choose a valid action:");
                System.out.println("1. (C)heck");
                System.out.println("2. (CALL) " + playerToHighestBet.getKey().getName() + "'s bet of 20.");
                System.out.println("3. (R)aise");
                System.out.println("4. (F)old");
                System.out.println("Hint: we recommend you to either Raise, since you have good pocket cards.");
            }
        }

        return raiseAmount;
    }

    private static int raiseChoice(Scanner scanner, SimpleEntry<Player, Integer> playerToHighestBet) {
        DisplayElements.printSeperator();
        System.out.println("You have decided to raise the bet.");
        System.out.println("Please type in the number of chips you want to raise by:");
        System.out.println("Remember: you have " + gameState.mainPlayer.getChips() + " chips left.");
        System.out.println("You need to raise by at least the big blind, which is 20 chips.");

        int raiseAmount;
        while (true) {
            String userInput = scanner.nextLine();
            try {
                raiseAmount = Integer.parseInt(userInput);
                if (raiseAmount <= 20 || raiseAmount > mainPlayer.getChips()) {
                    System.out.println("Invalid amount! Please type in a valid number of chips to raise by:");
                    System.out.println("Remember: you have " + mainPlayer.getChips() + " chips in total.");
                    System.out.println("You need to raise by at least the big blind, which is 20 chips.");
                } else {
                    gameState.players.get(3).setBet(raiseAmount);
                    gameState.setPot(gameState.getPot() + raiseAmount);
                    SimpleEntry<Player, Integer> newPlayerToHighestBet = new SimpleEntry<>(mainPlayer, raiseAmount);
                    gameState.setPlayerToHighestBet(newPlayerToHighestBet);
                    System.out.println("You have raised the bet by " + raiseAmount + " chips.");
                    System.out.println("The new pot is " + gameState.getPot() + " chips.");
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount! Please type in a valid number of chips to raise by:");
                System.out.println("Remember: you have 1000 chips in total.");
                System.out.println("You need to raise by at least the big blind, which is 20 chips.");
            }
        }
        return raiseAmount;
    }

    private static void simulatePreFlopBotReaction(int raiseAmount) {
        gameState.players.get(0).setState(PlayerState.FOLDED);
        gameState.players.get(1).setState(PlayerState.FOLDED);
        gameState.players.get(2).setBet(raiseAmount);
        gameState.players.get(2).setState(PlayerState.CALLED);
    }

    private static ArrayList<Card> initializeCommunityCards() {
        ArrayList<Card> communityCards = new ArrayList<Card>();
        communityCards.add(new Card(Suit.CLUBS, Rank.ACE));
        communityCards.add(new Card(Suit.HEARTS, Rank.EIGHT));
        communityCards.add(new Card(Suit.HEARTS, Rank.KING));
        return communityCards;
    }

    private static void simulateLastBotChecking() {
        Player botLeft = gameState.players.get(2);
        botLeft.setState(PlayerState.CHECKED);
        System.out.println(botLeft.getName() + " checks.");
    }

    private static void handlePlayerChecking(Scanner scanner) {
        System.out.println("Please choose an option:");
        System.out.println("1. (C)heck");
        System.out.println("2. (R)aise");
        System.out.println("3. (F)old");
        System.out.println("Hint: we recommend you to check in order to not scare the other player off.");
        while (true) {
            String userInput = scanner.nextLine();
            if (!userInput.equals("C")) {
                System.out.println("Invalid action! Please choose a valid option:");
                System.out.println("1. (C)heck");
                System.out.println("2. (R)aise");
                System.out.println("3. (F)old");
                System.out.println("Hint: we recommend you to check in order to not scare the other player off.");
            } else {
                System.out.println("You have checked.");
                break;
            }
        }
    }

    private static int handlePlayerRaising(Scanner scanner) {
        System.out.println("Please choose an option:");
        System.out.println("1. (C)heck");
        System.out.println("2. (R)aise");
        System.out.println("3. (F)old");
        System.out.println("Hint: we recommend you to raise in order to not scare the other player off.");
        int raiseAmount = 0;
        while (true) {
            String userInput = scanner.nextLine();
            if (!userInput.equals("R")) {
                System.out.println("Invalid action! Please type in a valid a valid action:");
                System.out.println("1. (C)heck");
                System.out.println("2. (R)aise");
                System.out.println("3. (F)old");
                System.out.println("Hint: we recommend you to raise because you have an amazing hand.");
            } else {
                raiseAmount = raiseChoice(scanner, gameState.getPlayerToHighestBet());
                break;
            }
        }
        return raiseAmount;
    }
}

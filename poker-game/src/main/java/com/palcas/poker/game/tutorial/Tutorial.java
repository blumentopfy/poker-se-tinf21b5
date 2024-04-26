package com.palcas.poker.game.tutorial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.palcas.poker.constants.PlayerNames;
import com.palcas.poker.display.TutorialDisplay;
import com.palcas.poker.game.Card;
import com.palcas.poker.game.Deck;
import com.palcas.poker.game.GameState;
import com.palcas.poker.game.Player;
import com.palcas.poker.game.Pocket;
import com.palcas.poker.game.variants.TexasHoldEm.TexasHoldEmPocket;
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

        initializeGameState();
        TutorialDisplay.introducePlayers(gameState, scanner);

        TutorialDisplay.deckInfo(scanner);

        TutorialDisplay.roundInfo(scanner);

        gameState.setDeck(new Deck().shuffleFullDeck());
        resetStatesAndBets();

        TutorialDisplay.startRound(gameState, scanner);
        setBlinds();

        TutorialDisplay.printMainPlayerPocket(gameState.players.get(3).getPocket().getCards(), scanner);

        communityCards = new ArrayList<Card>();

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
        Pocket pocketPlayerOne = new TexasHoldEmPocket(new Card(Suit.DIAMONDS, Rank.NINE),
                new Card(Suit.HEARTS, Rank.KING));
        Pocket pocketPlayerTwo = new TexasHoldEmPocket(new Card(Suit.SPADES, Rank.ACE),
                new Card(Suit.SPADES, Rank.KING));
        Pocket pocketPlayerThree = new TexasHoldEmPocket(new Card(Suit.SPADES, Rank.ACE),
                new Card(Suit.SPADES, Rank.KING));
        Pocket pocketMainPlayer = new TexasHoldEmPocket(new Card(Suit.SPADES, Rank.ACE),
                new Card(Suit.SPADES, Rank.KING));

        gameState.players.get(0).setPocket(pocketPlayerOne);
        gameState.players.get(1).setPocket(pocketPlayerTwo);
        gameState.players.get(2).setPocket(pocketPlayerThree);
        gameState.players.get(3).setPocket(pocketMainPlayer);

        gameState.bigBlind = 20;
        gameState.smallBlind = 10;
    }

    protected static void resetStatesAndBets() {
        for (Player player : gameState.players) {
            player.setState(PlayerState.WAITING_TO_BET);
        }

        gameState.players.stream().forEach(player -> player.setBet(0));
    }

    protected static void setBlinds() {
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

        gameState.pot = gameState.smallBlind + gameState.bigBlind;
    }
}

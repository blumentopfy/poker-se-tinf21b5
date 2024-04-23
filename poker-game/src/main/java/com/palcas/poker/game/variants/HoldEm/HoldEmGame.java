package com.palcas.poker.game.variants.HoldEm;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.stream.Collectors;

import com.palcas.poker.constants.PlayerNames;
import com.palcas.poker.display.DisplayElements;
import com.palcas.poker.game.Deck;
import com.palcas.poker.game.GameState;
import com.palcas.poker.game.Player;
import com.palcas.poker.game.poker_bot.TexasHoldEmBotActionService;
import com.palcas.poker.input.*;
import com.palcas.poker.model.PlayerState;

public class HoldEmGame {
    private final Scanner scanner;
    private final Player mainPlayer;
    private final TexasHoldEmBotActionService botActionService;
    private GameState gameState;

    public HoldEmGame(Player mainPlayer, ArrayList<Player> players) {
        this.gameState = new GameState(mainPlayer, players);
        this.mainPlayer = mainPlayer;
        this.scanner = new Scanner(System.in);
        this.botActionService = new TexasHoldEmBotActionService();
    }

    public void start() {
        DisplayElements.clearConsole();
        System.out.println("Starting a game of Texas Hold'em...");

        // Query player for number of players he wants to play with and how many chips everyone should have
        int playerCount = new PlayerCountChoice(scanner).executeChoice().get();
        DisplayElements.clearConsole();
        new StakeLevelChoice(scanner)
                .addOption("Low Stakes (Blinds: 5/10)").withAction(() -> initializeBlinds(5))
                .addOption("Medium Stakes (Blinds: 25/50)").withAction(() -> initializeBlinds(25))
                .addOption("High Stakes (Blinds: 100/200)").withAction(() -> initializeBlinds(100))
                .addOption("Very High Stakes (Blinds: 500/1000)").withAction(() -> initializeBlinds(500))
                .executeChoice();

        DisplayElements.clearConsole();

        // Populate player list with the main player and the number of players he wants to play with
        Collections.shuffle(PlayerNames.NAMES);
        List<String> playerNames = PlayerNames.NAMES.subList(0, playerCount);

        // Initialize players with random chips between 20x and 40x big blind
        this.gameState.players = playerNames.stream()
            .map(player -> new Player(player, (int) (gameState.bigBlind * ((Math.random() * 20) + 20))))
            .collect(Collectors.toList());
        this.gameState.players.add(this.mainPlayer);

        System.out.println(DisplayElements.SEPERATOR);
        System.out.println("Ready to play some Poker, " + mainPlayer.getName() + "? Let's meet the other players:");

        for (Player player : gameState.players) {
            if (player != mainPlayer) {
                System.out.println(player.getName() + " - Chips: " + player.getChips());
            }
        }
        System.out.println("(press enter to proceed)");
        scanner.nextLine();
        
        // Create a new deck and shuffle it
        gameState.setDeck(new Deck().shuffle());

        startPokerGameLoop();

        //TODO save progress of the player
    }

    private void startPokerGameLoop() {
        while (true) {
            DisplayElements.clearConsole();
            System.out.println("Starting round " + gameState.getRoundsPlayed() + ".");
            // reset player states to WAITING_TO_BET and bets to 0
            resetStatesAndBets();

            // shuffle here means creating a new deck and shuffling it, contrary to Collections.shuffle()
            gameState.getDeck().shuffle();

            setBlinds();

            new HoldEmRound(gameState, botActionService).executeRound();

            checkLosers();

            gameState.setRoundsPlayed(gameState.getRoundsPlayed() + 1);
            adjustBlinds(gameState.getRoundsPlayed());

            // rotate blinds
            gameState.smallBlindIndex = (gameState.smallBlindIndex + 1) % gameState.players.size();
            gameState.bigBlindIndex = (gameState.bigBlindIndex + 1) % gameState.players.size();
        }
    }

    private void resetStatesAndBets() {
        for (Player player : gameState.players) {
            player.setState(PlayerState.WAITING_TO_BET);
        }

        gameState.players.stream().forEach(player -> player.setBet(0));
    }

    private void setBlinds() {
        if (gameState.players.get(gameState.smallBlindIndex) == mainPlayer) {
            System.out.println("You set the small blind of " + gameState.smallBlind + ".");
        } else {
            System.out.println(gameState.players.get(gameState.smallBlindIndex).getName() + " sets the small blind of " + gameState.smallBlind + ".");
        }
        gameState.players.get(gameState.smallBlindIndex).setBet(gameState.smallBlind);


        if (gameState.players.get(gameState.bigBlindIndex) == mainPlayer) {
            System.out.println("You set the big blind of " + gameState.bigBlind + ".");
        } else {
            System.out.println(gameState.players.get(gameState.bigBlindIndex).getName() + " sets the big blind of " + gameState.bigBlind + ".");
        }
        gameState.players.get(gameState.bigBlindIndex).setBet(gameState.bigBlind);

        gameState.pot = gameState.smallBlind + gameState.bigBlind;
        gameState.setPlayerToHighestBet(new SimpleEntry<>(gameState.players.get(gameState.bigBlindIndex), gameState.bigBlind));
    }

    private void splitPot(List<Player> winners) {
        if (winners.isEmpty()) {
            System.out.println("No winners to split the pot.");
            return;
        }
        int chipsPerWinner = gameState.pot / winners.size();  // this automatically rounds down to the nearest whole number
        for (Player winner : winners) {
            winner.addChips(chipsPerWinner);
            System.out.println(winner.getName() + " wins " + chipsPerWinner + " chips!");
        }

        // If there's a remainder, keep them in the pot for next round
        int remainder = gameState.pot % winners.size();
        if (remainder > 0 && !winners.isEmpty()) {
            System.out.println("The remaining " + remainder + " chips can't be distributed equally, so they stay in the pot for the next round!");
        }
        gameState.pot = remainder;
    }

    private void adjustBlinds(int round) {
        if (round % 2 == 0) {
            gameState.bigBlind = (int) Math.ceil(gameState.bigBlind * 1.25);
            gameState.smallBlind = (int) Math.ceil(gameState.smallBlind * 1.25);
        }
    }

    private void checkLosers() {
        for (Player player : gameState.players) {
            if (player.getChips() == 0) {
                System.out.println(player.getName() + " is out of chips and has lost the game.");
                gameState.players.remove(player);
            }
        }
    }

    private void initializeBlinds(int smallBlindValue) {
        gameState.smallBlindIndex = 0;
        gameState.bigBlindIndex = 1;
        gameState.smallBlind = smallBlindValue;
        gameState.bigBlind = 2*smallBlindValue;
    }
}

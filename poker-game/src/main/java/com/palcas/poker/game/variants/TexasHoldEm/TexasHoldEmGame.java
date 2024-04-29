package com.palcas.poker.game.variants.TexasHoldEm;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.stream.Collectors;

import com.palcas.poker.constants.PlayerNames;
import com.palcas.poker.display.DisplayElements;
import com.palcas.poker.game.Deck;
import com.palcas.poker.game.GameResult;
import com.palcas.poker.game.GameState;
import com.palcas.poker.game.Player;
import com.palcas.poker.game.PokerGame;
import com.palcas.poker.game.poker_bot.BotActionService;
import com.palcas.poker.game.poker_bot.EmpiricalBotActionService;
import com.palcas.poker.game.poker_bot.StatisticalBotActionService;
import com.palcas.poker.input.*;
import com.palcas.poker.model.PlayerState;

public class TexasHoldEmGame extends PokerGame {
    private final Scanner scanner;
    private final Player mainPlayer;
    private final int initialMainPlayerChips;
    private BotActionService botActionService;
    private GameState gameState;
    private static boolean wantsToPlayOnHard;
    public static int chipsWon;
    public static int roundsWon;

    public TexasHoldEmGame(Player mainPlayer, ArrayList<Player> players) {
        this.gameState = new GameState(mainPlayer, players);
        this.mainPlayer = mainPlayer;
        this.initialMainPlayerChips = mainPlayer.getChips();
        this.scanner = new Scanner(System.in);
        chipsWon = 0;
    }

    public GameResult playGame() {
        DisplayElements.clearConsole();
        new DifficultyChoice(scanner)
                .addOption("Easy")
                .withAction(() -> wantsToPlayOnHard = false)
                .addOption("Hard")
                .withAction(() -> wantsToPlayOnHard = true)
                .executeChoice();

        // Query player for number of players he wants to play with and how many chips
        // everyone should have
        int playerCount = new PlayerCountChoice(scanner).executeChoice().get();
        new StakeLevelChoice(scanner)
                // TODO Once StatisticalBotActionService is debugged, set second param to
                // "wantsToPlayOnHard"
                .addOption("Low Stakes (Blinds: 5/10)")
                .withAction(() -> initializeBlindsAndBotActionService(5, true))
                .addOption("Medium Stakes (Blinds: 25/50)")
                .withAction(() -> initializeBlindsAndBotActionService(25, true))
                .addOption("High Stakes (Blinds: 100/200)")
                .withAction(() -> initializeBlindsAndBotActionService(100, true))
                .addOption("Very High Stakes (Blinds: 500/1000)")
                .withAction(() -> initializeBlindsAndBotActionService(500, true))
                .executeChoice();

        DisplayElements.clearConsole();

        // Populate player list with the main player and the number of players he wants
        // to play with
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
        DisplayElements.printSeperator();
        System.out.println("(press enter to proceed)");
        DisplayElements.printSeperator();
        scanner.nextLine();

        // Create a new deck and shuffle it
        gameState.setDeck(new Deck().shuffleFullDeck());

        startPokerGameLoop();

        int chipsChange = mainPlayer.getChips() - initialMainPlayerChips;
        GameResult gameResult = new GameResult(mainPlayer,
                chipsChange,
                chipsWon,
                roundsPlayed,
                roundsWon);

        return gameResult;
    }

    protected void startPokerGameLoop() {
        boolean gameRunning = true;
        while (gameRunning) {
            DisplayElements.clearWithSeperator();
            System.out.println("Starting round " + gameState.getRoundsPlayed() + ".");
            // reset player states to WAITING_TO_BET and bets to 0
            resetStatesAndBets();

            gameState.getDeck().shuffleFullDeck();

            setBlinds();

            gameState = new TexasHoldEmRound(gameState, botActionService).executeRound();

            gameRunning = checkLosers();

            gameState.setRoundsPlayed(gameState.getRoundsPlayed() + 1);
            adjustBlinds(gameState.getRoundsPlayed());

            // rotate blinds
            gameState.smallBlindIndex = (gameState.smallBlindIndex + 1) % gameState.players.size();
            gameState.bigBlindIndex = (gameState.bigBlindIndex + 1) % gameState.players.size();

            roundsPlayed++;
        }
    }

    protected void resetStatesAndBets() {
        for (Player player : gameState.players) {
            player.setState(PlayerState.WAITING_TO_BET);
            player.setBet(0);
        }
    }

    protected void setBlinds() {
        Player smallBlindPlayer = gameState.players.get(gameState.smallBlindIndex);
        Player bigBlindPlayer = gameState.players.get(gameState.bigBlindIndex);
        // Set the small blind for player or bot
        if (smallBlindPlayer == mainPlayer) {
            System.out.println("You set the small blind of " + gameState.smallBlind + ".");
        } else {
            System.out.println(smallBlindPlayer.getName() + " sets the small blind of "
                    + gameState.smallBlind + ".");
        }
        smallBlindPlayer.setBet(gameState.smallBlind);

        // Set the big blind for player or bot
        if (bigBlindPlayer == mainPlayer) {
            System.out.println("You set the big blind of " + gameState.bigBlind + ".");
        } else {
            System.out.println(bigBlindPlayer.getName() + " sets the big blind of "
                    + gameState.bigBlind + ".");
        }
        bigBlindPlayer.setBet(gameState.bigBlind);

        gameState.pot = gameState.smallBlind + gameState.bigBlind;
        gameState.setPlayerToHighestBet(
                new SimpleEntry<>(bigBlindPlayer, gameState.bigBlind));
        bigBlindPlayer.setState(PlayerState.CALLED);
    }

    // TODO move this to round
    protected void splitPot(List<Player> winners) {
        if (winners.isEmpty()) {
            System.out.println("No winners to split the pot.");
            return;
        }
        int chipsPerWinner = gameState.pot / winners.size(); // this automatically rounds down to the nearest whole
                                                             // number
        for (Player winner : winners) {
            winner.addChips(chipsPerWinner);
            System.out.println(winner.getName() + " wins " + chipsPerWinner + " chips!");
        }

        for (Player player : gameState.players) {
            if (player == mainPlayer) {
                chipsWon += chipsPerWinner;
                roundsWon++;
            }
        }

        // If there's a remainder, keep them in the pot for next round
        int remainder = gameState.pot % winners.size();
        if (remainder > 0 && !winners.isEmpty()) {
            System.out.println("The remaining " + remainder
                    + " chips can't be distributed equally, so they stay in the pot for the next round!");
        }
        gameState.pot = remainder;
    }

    protected void adjustBlinds(int round) {
        if (round % 2 == 0) {
            gameState.bigBlind = (int) Math.ceil(gameState.bigBlind * 1.25);
            gameState.smallBlind = (int) Math.ceil(gameState.smallBlind * 1.25);
        }
    }

    protected boolean checkLosers() {
        boolean gameRunning = true;
        for (Player player : gameState.players) {
            if (player.getChips() <= gameState.bigBlind) {
                System.out.println(player.getName() + " doesn't have enough chips and has lost the game.");
                gameState.players.remove(player);
            }
        }

        if (gameState.players.size() == 1) {
            System.out.println("Only one player left, " + gameState.players.get(0).getName() + ", who wins the game!");
            return false;
        } else if (!gameState.players.contains(mainPlayer)) {
            System.out.println("You have lost the game.");
            return false;
        } else {
            return true;
        }
    }

    protected void initializeBlindsAndBotActionService(int smallBlindValue, boolean useEmpiricalBotActionService) {
        gameState.smallBlindIndex = 0;
        gameState.bigBlindIndex = 1;
        gameState.smallBlind = smallBlindValue;
        gameState.bigBlind = 2 * smallBlindValue;
        if (useEmpiricalBotActionService) {
            // the EmpiricalBotActionService shall simulate smallBlindValue/5 other Pockets.
            // so the higher the small blind, the mor pockets get simulated and the better
            // the decision
            this.botActionService = new EmpiricalBotActionService(smallBlindValue / 5);
        } else {
            this.botActionService = new StatisticalBotActionService();
        }
    }

    protected void processWinners() {
        List<Player> winners = gameState.getWinners();
        int pot = gameState.getPot();

        if (winners.size() > 1) {
            System.out.println("Splitting pot of " + gameState.getPot() + " to " + winners.stream()
                    .map(Player::getName)
                    .collect(Collectors.joining(", ")));
            splitPot(winners);
        } else {
            Player winner = winners.get(0);
            System.out.println(winner.getName() + " wins the pot of " + pot + "!");
            winner.addChips(pot);
            if (winner == mainPlayer) {
                chipsWon += pot;
                roundsWon++;
            }
        }
    }
}

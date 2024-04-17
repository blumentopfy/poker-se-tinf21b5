package com.palcas.poker.game.variants.HoldEm;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.palcas.poker.constants.PlayerNames;
import com.palcas.poker.display.DisplayElements;
import com.palcas.poker.display.HandDisplay;
import com.palcas.poker.game.Card;
import com.palcas.poker.game.Deck;
import com.palcas.poker.game.Player;
import com.palcas.poker.input.BetChoice;
import com.palcas.poker.input.PlayerCountChoice;
import com.palcas.poker.input.RaiseChoice;
import com.palcas.poker.input.StartingChipsChoice;
import com.palcas.poker.model.PlayerState;

public class HoldEmGame {
    private final Scanner scanner;
    private final Player mainPlayer;
    private List<Player> players;

    private static int bigBlind;
    private static int smallBlind;
    private static int pot;
    private Entry<Player, Integer> playerToHighestBet;


    private Deck deck;

    public HoldEmGame(Player mainPlayer, ArrayList<Player> players) {
        this.mainPlayer = mainPlayer;
        this.players = players;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Starting a game of Texas Hold'em...");

        // Query player for number of players he wants to play with and how many chips everyone should have
        int playerCount = new PlayerCountChoice(scanner).executeChoice().get();
        int startingChips = new StartingChipsChoice(scanner).executeChoice().get();

        // Populate player list with the main player and the number of players he wants to play with
        Collections.shuffle(PlayerNames.NAMES);
        List<String> playerNames = PlayerNames.NAMES.subList(0, playerCount);
        
        this.players = playerNames.stream()
            .map(player -> new Player(player, startingChips))
            .collect(Collectors.toList());
        this.players.add(this.mainPlayer);

        System.out.println(DisplayElements.SEPERATOR);
        System.out.println("Ready to play some Poker, " + mainPlayer.getName() + "? Let's meet the other players:");

        for (Player player : players) {
            if (player != mainPlayer) {
                System.out.println(player.getName() + " - Chips: " + player.getChips());
            }
        }
        
        // Create a new deck and shuffle it
        this.deck = new Deck().shuffle();

        startPokerGameLoop();


        //TODO save progress of the player

    }

    private void startPokerGameLoop() {

        int smallBlindIndex = 0;
        int bigBlindIndex = 1;
        int round = 0;

        while (true) {
            // reset player states to WAITING_TO_BET and bets to 0
            resetStatesAndBets();

            players.get(smallBlindIndex).setBet(smallBlind);
            players.get(bigBlindIndex).setBet(bigBlind);
            this.playerToHighestBet = new SimpleEntry<>(players.get(bigBlindIndex), bigBlind);

            // round loop
            roundLoop(bigBlindIndex);

            // check for losers
            checkLosers();

            // adjust blinds
            adjustBlinds(round++);

            // rotate blinds
            smallBlindIndex = (smallBlindIndex + 1) % players.size();
            bigBlindIndex = (bigBlindIndex + 1) % players.size();
        }
    }

    private void resetStatesAndBets() {
        for (Player player : this.players) {
            player.setState(PlayerState.WAITING_TO_BET);
        }

        this.players.stream().forEach(player -> player.setBet(0));
    }

    private HashMap<Player, HoldEmPocket> distributePocketCards() {
        HashMap<Player, HoldEmPocket> playersWithPockets = new HashMap<>();
        for (Player player : this.players) {
            HoldEmPocket newPocket = new HoldEmPocket().populatePocket(deck);
            playersWithPockets.put(player, newPocket);
        }
        return playersWithPockets;
    }

    /**
     * One round in the game:
     * first round of bets
     *      flop
     *      second round of bets
     *      turn
     *      third round of bets
     *      river
     *      fourth round of bets
     * if players > 1
     *      check winner
     *
     *
     * Betting:
     *      First round betting starts after bigblind
     *      Bets have to be at least big blind
     *      Betting ends if a round is complete without a re-raise
     *
     */
    private void roundLoop(int bigBlindIndex) {
        int player_count = this.players.size();

        // Distribute pocket cards
        HashMap<Player, HoldEmPocket> playersWithPockets = distributePocketCards();
        List<Card> mainPlayerCards = playersWithPockets.get(this.mainPlayer).getCards();

        DisplayElements.printSeperator();
        //todo Check charset display problem
        HandDisplay.displayColoredPokerHand(mainPlayerCards.get(0), mainPlayerCards.get(1));
        
        doBetting(bigBlindIndex);
    }

    private void bet(Player player) {
        if (player == this.mainPlayer) {
            new BetChoice(scanner)
            .addOption("Check").withAction(() -> check(player))
            .addOption("Call").withAction(() -> call(player))
            .addOption("Raise").withAction(() -> raise(player))
            .addOption("Fold").withAction(() -> fold(player))
            .executeChoice();
        } else {
            //TODO implement AI
            player.setState(PlayerState.CHECK);
        }
    }

    private void check(Player player) {
        if (player.getBet() < playerToHighestBet.getValue()) {
            System.out.println("You have to call at least " + playerToHighestBet.getValue() + " to check.");
            bet(player);
            return;
        } else {
            player.setState(PlayerState.CHECK);
            System.out.println(player.getName() + " checks.");
        }
    }

    private void call(Player player) {
        int chipsToCall = playerToHighestBet.getValue() - player.getBet();
        player.setBet(player.getBet() + chipsToCall);
        player.setChips(player.getChips() - chipsToCall);
        System.out.println(player.getName() + " calls " + chipsToCall + ".");

        pot += chipsToCall;
        System.out.println("The pot is now at " + pot + ".");
    }

    private void raise(Player player) {
        Optional<Object> raiseAmountOptional = new RaiseChoice(scanner).executeChoice();
        int chipsToRaise = (int) raiseAmountOptional.get();
        // check if raise is higher than current highest bet
        if (chipsToRaise < playerToHighestBet.getValue()) {
            System.out.println("You have to raise at least " + playerToHighestBet.getValue() + " to raise.");
            bet(player);
            return;
        // check if player has enough chips
        } else if (chipsToRaise > player.getChips()) {
            System.out.println("You don't have enough chips to raise by " + chipsToRaise + ".");
            bet(player);
            return;
        } else {
            player.setBet(player.getBet() + chipsToRaise);
            player.setChips(player.getChips() - chipsToRaise);
            System.out.println(player.getName() + " raises by " + chipsToRaise + ".");

            playerToHighestBet.setValue(playerToHighestBet.getValue() + chipsToRaise);
            pot += chipsToRaise;
            System.out.println("The pot is now at " + pot + ".");
        }
    }

    private void fold(Player player) {
        player.setState(PlayerState.FOLD);
        System.out.println(player.getName() + " folds.");
    }

    private static void adjustBlinds(int round) {
        if (round % 2 == 0) {
            bigBlind = (int) Math.ceil(bigBlind * 1.25);
            smallBlind = (int) Math.ceil(smallBlind * 1.25);
        }
    }

    private void checkLosers() {
        for (Player player : players) {
            if (player.getChips() == 0) {
                System.out.println(player.getName() + " is out of chips and has lost the game.");
                players.remove(player);
            }
        }
    }

    private boolean checkIfBettingOver() {
        // Betting is not over if either...
        for (Player player : players) {
            // ... a player is still waiting to bet
            if (player.getState() == PlayerState.WAITING_TO_BET) {
                return false;
            // ... or a player has checked and has not called the highest bet/raise
            } else if (player.getState() == PlayerState.CHECK && player.getBet() != playerToHighestBet.getValue()) {
                return false;
            // ... or a player has raised but not to the currently highest bet
            } else if (player.getState() == PlayerState.RAISE && player.getBet() == playerToHighestBet.getValue()) {
                return false;
            }
        }

    // If none of the conditions above are met, betting is over
    return true;
    }

    private void doBetting(int bigBlindIndex) {

        // Start betting at index of big blind + 1
        // Since this will overflow, we will take the modulo of the player count
        int playerToBetIndex = bigBlindIndex + 1 % this.players.size();

        boolean bettingOver = false;
        while (!bettingOver) {
            bet(this.players.get(playerToBetIndex));
            bettingOver = checkIfBettingOver();
            playerToBetIndex = playerToBetIndex++ % this.players.size();
        }
    }
}
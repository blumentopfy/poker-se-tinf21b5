package com.palcas.poker.game.variants.HoldEm;

import java.util.*;
import java.util.stream.Collectors;

import com.palcas.poker.constants.PlayerNames;
import com.palcas.poker.display.DisplayElements;
import com.palcas.poker.game.Deck;
import com.palcas.poker.game.Player;
import com.palcas.poker.input.PlayerCountChoice;
import com.palcas.poker.input.StartingChipsChoice;
import com.palcas.poker.model.PlayerState;

public class HoldEmGame {
    private final Scanner scanner;
    private final Player mainPlayer;
    private List<Player> players;
    private int bigBlind;
    private int smallBlind;

    private int highest_bet;
    private Deck deck;

    public HoldEmGame(Player mainPlayer, ArrayList<Player> players) {
        this.mainPlayer = mainPlayer;
        this.players = players;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Starting a game of Texas Hold'em...");

        // Query player for number of players he wants to play with and how many chips everyone should have
        int playerCount = new PlayerCountChoice(scanner).executeChoice();
        int startingChips = new StartingChipsChoice(scanner).executeChoice();

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

    /**
     * Game Loop, consisting of multiple rounds, check for eliminating players between rounds, adjusts blinds etc.:
     *
     * clear player states
     * roundloop
     * checkLosers
     * AdjustBlinds
     * SwitchPlayerList
     *
     */
    private void startPokerGameLoop() {

        while (true) {

            for (Player player : players) {

            }
        }
    }

    /**
     * One round in the game:
     * set Blinds
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
    private void roundLoop() {
        int player_count = this.players.size();
        // small and big blind are the first players in the player list
        // todo if only 2 players remain there is an edge case
        if (player_count > 2) {
            this.players.get(0).setBet(smallBlind);
            this.players.get(0).setState(PlayerState.CHECK);
            this.players.get(1).setBet(bigBlind);
            this.players.get(1).setState(PlayerState.RAISE);
            this.highest_bet = bigBlind;

            int i = 1;
            boolean check = true;
            while (check) {
                // the next player is the last player (i) + 1
                // if this overflows the list, we need to start at the first index (% player_count)
                i = (i + 1) % player_count;
                // next player bets
                bet(this.players.get(i));



                // end: check if a player is in state RAISE
                check = false;
                for(Player p : this.players) {
                    if (p.getState() == PlayerState.RAISE) {
                        check = true;
                        break;
                    }
                }
            }
        }
        // set blind

    }
    private void bet(Player player) {

    }
}

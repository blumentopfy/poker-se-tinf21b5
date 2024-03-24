package com.palcas.poker.game.variants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.palcas.poker.constants.PlayerNames;
import com.palcas.poker.display.DisplayElements;
import com.palcas.poker.game.Deck;
import com.palcas.poker.game.Player;
import com.palcas.poker.input.PlayerCountChoice;

public class HoldEmGame {
    private Scanner scanner;
    private Player mainPlayer;
    private int playerCount;
    private List<Player> players;
    private Deck deck;

    public HoldEmGame(Player mainPlayer, ArrayList<Player> players) {
        this.mainPlayer = mainPlayer;
        this.players = players;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Starting a game of Texas Hold'em...");

        // Query player for number of players he wants to play with
        this.playerCount = new PlayerCountChoice(scanner).executeChoice();

        // Populate player list with the main player and the number of players he wants to play with
        Collections.shuffle(PlayerNames.NAMES);
        List<String> playerNames = PlayerNames.NAMES.subList(0, this.playerCount);
        
        this.players = playerNames.stream()
            .map(Player::new)
            .collect(Collectors.toList());
        this.players.add(this.mainPlayer);

        System.out.println(DisplayElements.SEPERATOR);
        System.out.println("Ready to play some Poker, " + mainPlayer.getName() + "? Let's meet the other players:");

        for (Player player : players) {
            if (player != mainPlayer)
                System.out.println(player.getName() + " has " + player.getChips() + " chips.");
        }
    }
}

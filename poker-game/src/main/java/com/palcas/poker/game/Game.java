package com.palcas.poker.game;

import java.util.ArrayList;

public class Game {
    private ArrayList<Player> players;
    private Player mainPlayer;
    private Deck deck;

    public Game(String mainPlayerName) {
        this.mainPlayer = new Player(mainPlayerName, 1000);
        this.players = new ArrayList<Player>();
        this.players.add(this.mainPlayer);
        this.deck = new Deck();
    }

    public void startGame() {
        System.out.println("Starting the game...");
        System.out.println("Dealing cards to " + this.mainPlayer.getName());
        System.out.println("Player " + this.mainPlayer + " has " + this.mainPlayer.getChips() + " chips left.");
        // Display hand, query next option
    }
}

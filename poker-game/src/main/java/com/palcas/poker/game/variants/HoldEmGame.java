package com.palcas.poker.game.variants;

import com.palcas.poker.game.Player;

public class HoldEmGame {
    private Player mainPlayer;

    public HoldEmGame(Player mainPlayer) {
        this.mainPlayer = mainPlayer;
    }

    public void start() {
        System.out.println("Starting a game of Texas Hold'em...");
        System.out.println("Dealing cards to " + this.mainPlayer.getName());
        System.out.println("Player " + this.mainPlayer.getName() + " has " + this.mainPlayer.getChips() + " chips left.");
        // Display hand, query next option
    }
}

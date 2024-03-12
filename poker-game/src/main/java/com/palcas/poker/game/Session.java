package com.palcas.poker.game;

import java.util.ArrayList;
import java.util.Scanner;

import com.palcas.poker.game.variants.HoldEmGame;
import com.palcas.poker.input.PokerVariantChoice;

public class Session {
    private ArrayList<Player> players;
    private Player mainPlayer;

    public Session(String mainPlayerName) {
        //TODO if Player has already played, retrieve their chips from database/file storage
        this.mainPlayer = new Player(mainPlayerName, 1000);
        this.players = new ArrayList<Player>();
        this.players.add(this.mainPlayer);
    }

    public void start() {

        new PokerVariantChoice(new Scanner(System.in))
            .addChoice("Exit to main menu").withAction(() -> System.out.println("Exiting to main menu..."))
            .addChoice("OmahaHoldEm").withAction(() -> startOmahaHoldEmGame())
            .addChoice("Texas Hold'em").withAction(() -> startHoldEmGame())
            .executeChoice();


        System.out.println("Dealing cards to " + this.mainPlayer.getName());
        System.out.println("Player " + this.mainPlayer.getName() + " has " + this.mainPlayer.getChips() + " chips left.");
        // Display hand, query next option
    }

    private void startHoldEmGame() {
        new HoldEmGame(mainPlayer).start();
    }

    private void startOmahaHoldEmGame() {
        //TODO
    }
}

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
        this.mainPlayer = new Player(mainPlayerName, 10000);
        this.players = new ArrayList<Player>();
        this.players.add(this.mainPlayer);
    }

    public void start() {
        new PokerVariantChoice(new Scanner(System.in))
            .addChoice("Exit to main menu").withAction(() -> System.out.println("Exiting to main menu..."))
            .addChoice("OmahaHoldEm").withAction(() -> startOmahaHoldEmGame())
            .addChoice("Texas Hold'em").withAction(() -> startHoldEmGame())
            .executeChoice();
    }

    private void startHoldEmGame() {
        new HoldEmGame(mainPlayer, players).start();
    }

    private void startOmahaHoldEmGame() {
        //TODO
    }
}

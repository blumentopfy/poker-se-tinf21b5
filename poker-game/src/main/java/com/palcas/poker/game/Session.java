package com.palcas.poker.game;

import java.util.ArrayList;
import java.util.Scanner;

import com.palcas.poker.display.DisplayElements;
import com.palcas.poker.game.variants.HoldEm.HoldEmGame;
import com.palcas.poker.input.PokerVariantChoice;

public class Session {
    private ArrayList<Player> players;
    private Player mainPlayer;

    public Session(Player mainPlayer) {
        this.mainPlayer = mainPlayer;
        this.players = new ArrayList<Player>();
        this.players.add(this.mainPlayer);
    }

    public void start() {
        DisplayElements.clearConsole();
        new PokerVariantChoice(new Scanner(System.in))
            .addOption("Exit to main menu").withAction(() -> System.out.println("Exiting to main menu..."))
            .addOption("OmahaHoldEm").withAction(this::startOmahaHoldEmGame)
            .addOption("Texas Hold'em").withAction(this::startHoldEmGame)
            .executeChoice();
    }

    private void startHoldEmGame() {
        new HoldEmGame(mainPlayer, players).playGame();
    }

    private void startOmahaHoldEmGame() {
        //TODO
    }
}

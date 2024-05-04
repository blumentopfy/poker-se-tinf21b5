package com.palcas.poker.game;

import java.util.ArrayList;
import java.util.Scanner;

import com.palcas.poker.user_interaction.display.DisplayElements;
import com.palcas.poker.game.variants.TexasHoldEm.TexasHoldEmGame;
import com.palcas.poker.user_interaction.choices.PokerVariantChoice;

public class Session {
    private ArrayList<Player> players;
    public Player mainPlayer;

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
        GameResult gameResult = new TexasHoldEmGame(mainPlayer, players).playGame();
        //TODO persist game result
    }

    private void startOmahaHoldEmGame() {
        //TODO
    }
}

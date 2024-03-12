package com.palcas.poker;
import java.util.Scanner;

import com.palcas.poker.game.Game;
import com.palcas.poker.input.SystemChoice;

// Entry point for the game, should really only include welcome & game loop
public class App {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Hello to Palcas Poker v3: Electric Bogaloo!");
            System.out.println("Please enter your name to start the game: ");

            String playerName = scanner.nextLine();
            Game game = new Game(playerName);
            System.out.println("Welcome to the game" + playerName + " !");

            // Game Loop
            while (true) {
                // Main menu, could set some options here like "Highscore", "Settings", etc.
                new SystemChoice(scanner)
                    .addChoice("Start a new game").withAction(() -> game.startGame())
                    .addChoice("Exit the game").withAction(() -> System.exit(0))
                    .executeSystemChoice();

            }
        }
    }
}
package com.palcas.poker;
import java.util.Scanner;

import com.palcas.poker.game.Session;
import com.palcas.poker.input.SystemChoice;

// Entry point for the game, should really only include welcome & game loop
public class App {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Hello to Palcas Poker v3: Electric Bogaloo!");
            System.out.println("Please enter your name to start the game session: ");

            String playerName = scanner.nextLine();
            Session session = new Session(playerName);

            // Game Loop
            while (true) {
                // Main menu, could set some options here like "Highscore", "Settings", etc.
                new SystemChoice(scanner)
                    .addChoice("Play a round of poker").withAction(session::start)
                    .addChoice("Highscore").withAction(() -> System.out.println("Highscore not yet implemented!"))
                    .addChoice("Settings").withAction(() -> System.out.println("Settings not yet implemented!"))
                    .addChoice("Exit the game").withAction(() -> System.exit(0))
                    .executeChoice();
            }
        }
    }
}
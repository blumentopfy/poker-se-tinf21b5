package com.palcas.poker;
import java.io.IOException;
import java.util.Scanner;

import com.palcas.poker.constants.JacksonPersistenceSettings;
import com.palcas.poker.display.DisplayElements;
import com.palcas.poker.display.LeaderboardDisplay;
import com.palcas.poker.game.Session;
import com.palcas.poker.input.SystemChoice;
import com.palcas.poker.persistance.JacksonLeaderboardRepository;
import com.palcas.poker.persistance.LeaderboardRepository;

// Entry point for the game, should really only include welcome & game loop
public class App {

    public static void main(String[] args) {
        DisplayElements.clearConsole();
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Hello to Palcas Poker v3: Electric Bogaloo!");
            System.out.println("Please enter your name to start the game session: ");

            String playerName = scanner.nextLine();
            Session session = new Session(playerName);
            LeaderboardRepository leaderboardRepository = new JacksonLeaderboardRepository(JacksonPersistenceSettings.leaderboardFilePath);

            // Game Loop
            while (true) {
                DisplayElements.clearConsole();
                // Main menu, could set some options here like "Highscore", "Settings", etc.
                new SystemChoice(scanner)
                    .addOption("Play a round of poker").withAction(session::start)
                    .addOption("Highscore").withAction(() -> {
                            try {
                                LeaderboardDisplay.displayLeaderboard(leaderboardRepository.getTopTen());
                            } catch (IOException e) {
                                System.out.println("There has been a problem loading the leaderboard, please contact your admin or the developers for support...");
                                e.printStackTrace();
                            }
                        })
                    .addOption("Settings").withAction(() -> System.out.println("Settings not yet implemented!"))
                    .addOption("Exit the game").withAction(() -> System.exit(0))
                    .executeChoice();
            }
        }
    }
}
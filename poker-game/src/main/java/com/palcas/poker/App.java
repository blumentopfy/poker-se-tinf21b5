package com.palcas.poker;
import java.io.IOException;
import java.util.Scanner;

import com.palcas.poker.account_management.CLILoginUserInteraction;
import com.palcas.poker.constants.JacksonPersistenceSettings;
import com.palcas.poker.display.DisplayElements;
import com.palcas.poker.display.LeaderboardDisplay;
import com.palcas.poker.game.Player;
import com.palcas.poker.game.Session;
import com.palcas.poker.input.StakeLevelChoice;
import com.palcas.poker.input.MainMenuChoice;
import com.palcas.poker.persistance.JacksonLeaderboardRepository;
import com.palcas.poker.persistance.LeaderboardRepository;

// Entry point for the game, should really only include welcome & game loop
public class App {

    private static Player mainPlayer;

    public static void main(String[] args) {
        DisplayElements.clearConsole();
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Hello to Palcas Poker v3: Electric Bogaloo!");

            new MainMenuChoice(scanner)
                    .addOption("Login").withAction(() -> mainPlayer = new CLILoginUserInteraction().login())
                    .addOption("Register").withAction(() -> mainPlayer = new CLILoginUserInteraction().register())
                    .addOption("Continue as guest").withAction(() -> mainPlayer = new CLILoginUserInteraction().loginAsGuest())
                    .executeChoice();

            Session session = new Session(mainPlayer);
            LeaderboardRepository leaderboardRepository = new JacksonLeaderboardRepository(JacksonPersistenceSettings.LEADERBOARD_FILE_PATH);

            while (true) {
                DisplayElements.clearConsole();
                // Main menu
                new MainMenuChoice(scanner)
                    .addOption("Play a round of poker").withAction(session::start)
                    .addOption("Highscore").withAction(() -> {
                            try {
                                LeaderboardDisplay.displayLeaderboard(leaderboardRepository.getTopTen(), scanner);
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
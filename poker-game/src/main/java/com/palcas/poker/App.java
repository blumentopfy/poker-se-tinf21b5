package com.palcas.poker;
import java.io.IOException;
import java.util.Scanner;

import com.palcas.poker.account_management.CLILoginUserInteraction;
import com.palcas.poker.constants.JacksonPersistenceSettings;
import com.palcas.poker.display.DisplayElements;
import com.palcas.poker.display.LeaderboardDisplay;
import com.palcas.poker.game.Player;
import com.palcas.poker.game.Session;
import com.palcas.poker.game.tutorial.Tutorial;
import com.palcas.poker.input.LoginChoice;
import com.palcas.poker.input.MainMenuChoice;
import com.palcas.poker.persistance.JacksonLeaderboardRepository;
import com.palcas.poker.persistance.LeaderboardRepository;

// Entry point for the game, should really only include welcome & game loop
public class App {

    private static Player mainPlayer;
    private static Session session;
    private static LeaderboardRepository leaderboardRepository;
    public static final Scanner globalScanner = new Scanner(System.in);

    public static void main(String[] args) {
        DisplayElements.clearConsole();
        try (globalScanner) {
            System.out.println("Hello to Palcas Poker v3: Electric Bogaloo!");

            executeLogin(globalScanner);

            session = new Session(mainPlayer);
            leaderboardRepository = new JacksonLeaderboardRepository(JacksonPersistenceSettings.LEADERBOARD_FILE_PATH);

            while (true) {
                mainMenu();
            }
        }
    }

    private static void executeLogin(Scanner scanner) {
        new LoginChoice(scanner)
                    .addOption("Login").withAction(() -> mainPlayer = new CLILoginUserInteraction().login())
                    .addOption("Register").withAction(() -> mainPlayer = new CLILoginUserInteraction().register())
                    .addOption("Continue as guest").withAction(() -> mainPlayer = new CLILoginUserInteraction().loginAsGuest())
                    .executeChoice();
    }

    private static void mainMenu() {
        DisplayElements.clearConsole();
                // Main menu
                new MainMenuChoice()
                    .addOption("Play a round of poker").withAction(session::start)
                    .addOption("Highscore").withAction(() -> {
                            try {
                                LeaderboardDisplay.displayLeaderboard(leaderboardRepository.getTopTen(), globalScanner);
                            } catch (IOException e) {
                                System.out.println("There has been a problem loading the leaderboard, please contact your admin or the developers for support...");
                                e.printStackTrace();
                            }
                        })
                    .addOption("Settings").withAction(() -> System.out.println("Settings not yet implemented!"))
                    .addOption("Play Tutorial (TexasHoldEm)").withAction(() -> Tutorial.playTutorial(globalScanner))
                    .addOption("Exit the game").withAction(() -> System.exit(0))
                    .executeChoice(globalScanner);
    }
}
package com.palcas.poker;

import java.io.IOException;
import java.util.Scanner;

import com.palcas.poker.user_interaction.CLILoginUserInteraction;
import com.palcas.poker.persistance.constants.JacksonPersistenceSettings;
import com.palcas.poker.user_interaction.display.DisplayElements;
import com.palcas.poker.user_interaction.display.LeaderboardDisplay;
import com.palcas.poker.game.Player;
import com.palcas.poker.game.Session;
import com.palcas.poker.game.tutorial.Tutorial;
import com.palcas.poker.user_interaction.choices.LoginChoice;
import com.palcas.poker.user_interaction.choices.MainMenuChoice;
import com.palcas.poker.persistance.leaderboard.JacksonLeaderboardRepository;
import com.palcas.poker.persistance.leaderboard.LeaderboardRepository;

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
                .addOption("Continue as guest")
                .withAction(() -> mainPlayer = new CLILoginUserInteraction().loginAsGuest())
                .executeChoice();
    }

    private static void mainMenu() {
        DisplayElements.clearConsole();
        // Main menu
        new MainMenuChoice()
                .addOption("Play a round of poker").withAction(session::start)
                .addOption("Leaderboard").withAction(() -> {
                    try {
                        LeaderboardDisplay.displayLeaderboard(leaderboardRepository.getTopTen(), globalScanner);
                    } catch (IOException e) {
                        System.out.println(
                                "There has been a problem loading the leaderboard, please contact your admin or the developers for support...");
                        e.printStackTrace();
                    }
                })
                .addOption("Settings").withAction(() -> System.out.println("Settings not yet implemented!"))
                .addOption("Play Tutorial (TexasHoldEm)").withAction(() -> Tutorial.playTutorial(globalScanner))
                .addOption("Exit the game").withAction(() -> System.exit(0))
                .executeChoice(globalScanner);
    }
}
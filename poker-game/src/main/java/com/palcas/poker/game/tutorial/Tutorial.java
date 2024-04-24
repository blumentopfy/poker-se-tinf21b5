package com.palcas.poker.game.tutorial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.palcas.poker.constants.PlayerNames;
import com.palcas.poker.display.TutorialDisplay;
import com.palcas.poker.game.GameState;
import com.palcas.poker.game.Player;

public class Tutorial {
    private static GameState gameState;

    public static void playTutorial(Scanner scanner) {
        TutorialDisplay.tutorialGreeting(scanner);

        TutorialDisplay.printPlayerCountInfo(scanner);

        TutorialDisplay.printStakesInfo(scanner);

        initializeGameState();
        TutorialDisplay.printPlayers(gameState);
    }

    private static void initializeGameState() {
        Collections.shuffle(PlayerNames.NAMES);
        List<String> playerNames = PlayerNames.NAMES.subList(0, 3);
        ArrayList<Player> players = new ArrayList<>();
        Player mainPlayer = new Player("Tutorial Player", 1000);


        for (String playerName : playerNames) {
            players.add(new Player(playerName, 1000));
        }

        Tutorial.gameState = new GameState(mainPlayer, players);
    }
}

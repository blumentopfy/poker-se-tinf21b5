package com.palcas.poker.user_interaction.display;

import java.util.List;

import com.palcas.poker.game.GameState;
import com.palcas.poker.game.Player;

public class GameStateDisplay {
    public static void display(GameState gameState) {
        System.out.printf("-----CURRENT GAME STATUS-----%n");
        System.out.printf(" %-20s | %-5s | %-15s | %-15s | %-15s %n", "PLAYER", "BLIND", "STATUS", "CHIPS IN POT",
                "CHIPS ON STACK");
        System.out.printf("-".repeat(84) + "%n");
        for (Player player : gameState.getPlayers()) {
            String blindStatus = getBlindRepresentationOf(player, gameState);
            String statusName = player.getState().getStateName();
            String statusColor = player.getState().getStateColor();
            System.out.printf(" %-20s | %-5s |" + statusColor + " %-15s \u001B[0m| %-15d | %-15d %n", player.getName(), blindStatus,
                        statusName, player.getBet(), player.getChips());
        }
        PauseDisplay.continueWithEnter();
        DisplayElements.printSeperator();
    }

    private static String getBlindRepresentationOf(Player player, GameState gameState) {
        if (gameState.getSmallBlindPlayer().equals(player)) {
            return "small";
        } else if (gameState.getBigBlindPlayer().equals(player)) {
            return "big";
        } else {
            return "---";
        }
    }

    public static void displayWinners(GameState gameState) {
        DisplayElements.printSeperator();
        System.out.println("The winners are: ");
        for (Player winner : gameState.getWinners()) {
            System.out.println(winner.getName());
        }
        System.out.println("They have won the pot containing " + gameState.getPot() + " chips.");
        System.out.println("This is the hand they have won with: ");
        for (Player winner : gameState.getWinners()) {
            PocketDisplay.displayColoredPocket(winner.getPocket().getCards().get(0), winner.getPocket().getCards().get(1));
        }
        DisplayElements.printSeperator();
        PauseDisplay.continueWithEnter();
    }

}

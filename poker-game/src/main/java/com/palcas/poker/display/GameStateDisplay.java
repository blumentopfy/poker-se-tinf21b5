package com.palcas.poker.display;

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

}

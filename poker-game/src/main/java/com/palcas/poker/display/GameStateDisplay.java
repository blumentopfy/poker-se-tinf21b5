package com.palcas.poker.display;

import com.palcas.poker.game.GameState;
import com.palcas.poker.game.Player;

public class GameStateDisplay {
    public static void display(GameState gameState) {
        System.out.printf("-----CURRENT GAME STATUS-----%n");
        System.out.printf(" %-20s | %-5s | %-15s | %-15s | %-15s %n", "PLAYER", "BLIND", "STATUS", "CHIPS IN POT", "CHIPS ON STACK");
        System.out.printf("-".repeat(84) + "%n");
        for (Player player : gameState.getPlayers()) {
            String blindStatus = getBlindRepresentationOf(player, gameState);
            System.out.printf(" %-20s | %-5s | %-15s | %-15d | %-15d %n", player.getName(), blindStatus, player.getState().getCoolString(), player.getBet(), player.getChips());
        }
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

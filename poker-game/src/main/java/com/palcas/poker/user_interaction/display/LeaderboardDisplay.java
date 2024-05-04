package com.palcas.poker.user_interaction.display;

import com.palcas.poker.persistance.leaderboard.LeaderboardEntry;

import java.util.List;
import java.util.Scanner;

public class LeaderboardDisplay {
    public static void displayLeaderboard(List<LeaderboardEntry> leaderboardTopTen, Scanner scanner) {
        DisplayElements.clearConsole();
        if (leaderboardTopTen.size() == 0) {
            System.out.println("there are not entries yet in the leaderboard, you can be the first one ;-)");
            System.out.println("(press enter to return to main menu)");
            scanner.nextLine();
            return;
        }
        int maxNameLength = maxNameLength(leaderboardTopTen);
        System.out.printf(DisplayElements.SEPERATOR + "%n");
        System.out.printf("-----CURRENT LEADERBOARD-----%n");
        System.out.printf(DisplayElements.SEPERATOR + "%n");
        System.out.printf(" %-4s | %-" + Math.min(maxNameLength, 20) + "s | %5s %n", "RANK", "NAME", "CHIPS");
        System.out.printf(DisplayElements.SEPERATOR + "%n");
        int rank = 1;
        for (LeaderboardEntry entry : leaderboardTopTen) {
            System.out.printf("  %02d  | %-" + Math.min(maxNameLength, 20) + "s | %5d %n", rank++, entry.getName(),
                    entry.getChips());
        }
        System.out.printf(DisplayElements.SEPERATOR + "%n");
        System.out.println("(press enter to return to main menu)");
        scanner.nextLine();
    }

    private static int maxNameLength(List<LeaderboardEntry> leaderboardTopTen) {
        int maxLength = 0;
        for (LeaderboardEntry entry : leaderboardTopTen) {
            int length = entry.getName().length();
            if (length > maxLength) {
                maxLength = length;
            }
        }
        return maxLength;
    }
}

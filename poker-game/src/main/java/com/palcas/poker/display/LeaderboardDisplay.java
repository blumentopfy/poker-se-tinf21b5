package com.palcas.poker.display;

import com.palcas.poker.persistance.LeaderboardEntry;

import java.util.List;

public class LeaderboardDisplay {
    public static void displayLeaderboard(List<LeaderboardEntry> leaderboardTopTen) {
        int maxNameLength = maxNameLenght(leaderboardTopTen);
        System.out.printf(DisplayElements.SEPERATOR + "%n");
        System.out.printf("   current leaderboard:   %n");
        System.out.printf(DisplayElements.SEPERATOR + "%n");
        System.out.printf(" %-4s | %-"+Math.min(maxNameLength, 20)+"s | %5s %n", "RANK", "NAME", "CHIPS");
        System.out.printf(DisplayElements.SEPERATOR + "%n");
        int rank = 1;
        for (LeaderboardEntry entry: leaderboardTopTen) {
            System.out.printf("  %02d  | %-"+Math.min(maxNameLength, 20)+"s | %05d %n", rank++, entry.getName(), entry.getChips());
        }
    }

    private static int maxNameLenght(List<LeaderboardEntry> leaderboardTopTen) {
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

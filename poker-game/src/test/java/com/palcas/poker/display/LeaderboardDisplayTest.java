package com.palcas.poker.display;

import com.palcas.poker.persistance.LeaderboardEntry;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class LeaderboardDisplayTest {
    @Test public void testPrintLeaderboard() {
        List<LeaderboardEntry> leaderboardTopTen = Arrays.asList(
                new LeaderboardEntry("Alice", 1500),
                new LeaderboardEntry("Bob", 2200),
                new LeaderboardEntry("Charlie", 1800),
                new LeaderboardEntry("David", 2100),
                new LeaderboardEntry("Eve", 1900)
        );
        LeaderboardDisplay.displayLeaderboard(leaderboardTopTen);
    }

}

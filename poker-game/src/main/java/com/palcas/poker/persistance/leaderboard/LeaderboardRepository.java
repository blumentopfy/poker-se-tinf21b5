package com.palcas.poker.persistance.leaderboard;

import java.io.IOException;
import java.util.ArrayList;

public interface LeaderboardRepository {
    ArrayList<LeaderboardEntry> getTopTen() throws IOException;

    void addToLeaderboard(String name, int chips) throws IOException;
}

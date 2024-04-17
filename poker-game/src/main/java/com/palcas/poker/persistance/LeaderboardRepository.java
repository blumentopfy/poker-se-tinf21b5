package com.palcas.poker.persistance;

import java.io.IOException;
import java.util.ArrayList;

public interface LeaderboardRepository {
    ArrayList<LeaderboardEntry> getTopTen() throws IOException;
    void addToLeaderboard(String name, int chips) throws IOException;
}

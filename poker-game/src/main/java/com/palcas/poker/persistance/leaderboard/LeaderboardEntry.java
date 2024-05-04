package com.palcas.poker.persistance.leaderboard;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LeaderboardEntry {
    private String name;
    private int chips;

    @JsonCreator
    public LeaderboardEntry(@JsonProperty("name") String name, @JsonProperty("chips") int chips) {
        this.name = name;
        this.chips = chips;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getChips() {
        return chips;
    }

    public void setChips(int chips) {
        this.chips = chips;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Chips: " + chips;
    }
}

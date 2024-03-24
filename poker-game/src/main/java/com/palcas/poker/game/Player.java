package com.palcas.poker.game;

public class Player {
    private String name;
    private int chips;

    public Player(String name, int chips) {
        this.name = name;
        this.chips = chips;
    }

    public Player (String name) {
        this.name = name;
        this.chips = 10000;
    }

    public String getName() {
        return name;
    }

    public int getChips() {
        return chips;
    }

    public void setChips(int chips) {
        this.chips = chips;
    }
}

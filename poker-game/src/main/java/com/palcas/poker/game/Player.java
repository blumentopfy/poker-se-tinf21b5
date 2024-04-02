package com.palcas.poker.game;

import com.palcas.poker.model.PlayerState;

public class Player {
    private String name;
    private int chips;

    private Pocket pocket;

    private int bet;



    private PlayerState state;

    public Player(String name, int chips) {
        this.name = name;
        this.chips = chips;
    }

    public Player (String name) {
        this.name = name;
        this.chips = 0;
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

    public Pocket getPocket() {
        return pocket;
    }

    public void setPocket(Pocket pocket) {
        this.pocket = pocket;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }
    public PlayerState getState() {
        return state;
    }

    public void setState(PlayerState state) {
        this.state = state;
    }
}

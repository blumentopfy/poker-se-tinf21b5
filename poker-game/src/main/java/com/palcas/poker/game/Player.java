package com.palcas.poker.game;

import java.util.Random;

import com.palcas.poker.model.PlayerState;

public class Player {
    protected String name;
    protected int chips;
    private Pocket pocket;
    private int bet;
    private PlayerState state;
    private int agressionLevel;
    private int bluffAffinity;

    public Player(String name, int chips) {
        this.name = name;
        this.chips = chips;
        this.agressionLevel = generateRandomIntBetween(1, 100);
        this.bluffAffinity = generateRandomIntBetween(1, 100);
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
        
    public void setName(String name) {
        this.name = name;
    }

    public int getAgressionLevel() {
        return this.agressionLevel;
    }

    public void setAgressionLevel(int agressionLevel) {
        this.agressionLevel = agressionLevel;
    }

    public int getBluffAffinity() {
        return this.bluffAffinity;
    }

    public void setBluffAffinity(int bluffAffinity) {
        this.bluffAffinity = bluffAffinity;
    }

    public int generateRandomIntBetween(int min, int max) {
        Random random = new Random();
        return random.ints(min, max).findFirst().getAsInt();
    }
}

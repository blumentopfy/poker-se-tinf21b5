package com.palcas.poker.persistance;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.palcas.poker.game.Player;

public class Account extends Player{
    private String passwordHash;

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Account(String name, String passwordHash) {
        super(name, 2000);
        this.passwordHash = passwordHash;
    }

    @JsonCreator
    public Account(@JsonProperty("name") String name,
                   @JsonProperty("passwordHash") String passwordHash,
                   @JsonProperty("chips") int chips) {
        super(name, chips);
        this.name = name;
        this.passwordHash = passwordHash;
        this.chips = chips;
    }
}

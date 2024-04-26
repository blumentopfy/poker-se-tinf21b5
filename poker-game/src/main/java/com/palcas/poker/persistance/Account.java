package com.palcas.poker.persistance;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {
    private String name;
    private String passwordHash;
    private String passwordSalt;
    private int chips;

    public Account(String name, String passwordHash, String passwordSalt) {
        this.name = name;
        this.chips = 2000;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
    }

    @JsonCreator
    public Account(@JsonProperty("name") String name,
            @JsonProperty("passwordHash") String passwordHash,
            @JsonProperty("passwordSalt") String passwordSalt,
            @JsonProperty("chips") int chips) {
        this.name = name;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
        this.chips = chips;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
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
}

package com.palcas.poker.model;

public enum PlayerState {
    FOLDED("folded"),
    CHECKED("checked"),
    RAISED("raised"),
    IS_ALL_IN("is all in"),
    CALLED("called"),
    WAITING_TO_BET("waiting to bet");

    private final String coolString;

    PlayerState(String coolString) {
        this.coolString = coolString;
    }

    public String getCoolString() {
        return coolString;
    }
}

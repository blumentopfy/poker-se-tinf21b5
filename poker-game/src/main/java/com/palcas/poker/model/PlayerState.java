package com.palcas.poker.model;

public enum PlayerState {
    FOLDED("folded", "\u001B[31m"), //red
    CHECKED("checked", "\u001B[32m"), //green
    RAISED("raised", "\u001B[33m"), //yellow
    IS_ALL_IN("is all in", "\u001B[34m"), //blue
    CALLED("called", "\u001B[32m"), //green
    WAITING_TO_BET("waiting to bet", "\u001B[0m"); // white

    private final String stateName;
    private final String stateColor;

    PlayerState(String stateName, String stateColor) {
        this.stateName = stateName;
        this.stateColor = stateColor;
    }

    public String getStateName() {
        return stateName;
    }

    public String getStateColor() {
        return stateColor;
    }
}

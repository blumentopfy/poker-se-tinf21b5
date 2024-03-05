package com.palcas.poker.game;

public enum HandRanking {
    ROYAL_FLUSH("Royal Flush", 9),
    STRAIGHT_FLUSH("Straight Flush", 8),
    FOUR_OF_A_KIND("Four of a Kind", 7),
    FULL_HOUSE("Full House", 6),
    FLUSH("Flush", 5),
    STRAIGHT("Straight", 4),
    THREE_OF_A_KIND("Three of a Kind", 3),
    TWO_PAIRS("Two Pairs", 2),
    PAIR("Pair", 1),
    HIGH_CARD("High Card", 0);

    private final String name;
    private final int value;

    HandRanking(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public String getName() {
        return name;
    }
}

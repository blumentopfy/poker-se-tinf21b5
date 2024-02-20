package com.palcas.poker;

public enum Rank {
    TWO("2", "  2  ", 2),
    THREE("3", "  3  ", 3),
    FOUR("4", "  4  ", 4),
    FIVE("5", "  5  ", 5),
    SIX("6", "  6  ", 6),
    SEVEN("7", "  7  ", 7),
    EIGHT("8", "  8  ", 8),
    NINE("9", "  9  ", 9),
    TEN("10", "  10 ", 10),
    JACK("Jack", " Jack", 11),
    QUEEN("Queen", "Queen", 12),
    KING("King", " King", 13),
    ACE("Ace", " Ace ", 14);

    private final String name;
    private final String formattedName;
    private final int value;

    Rank(String name, String formattedName, int value) {
        this.name = name;
        this.formattedName = formattedName;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getFormattedName() {
        return formattedName;
    }

    public int getValue() {
        return value;
    }
}


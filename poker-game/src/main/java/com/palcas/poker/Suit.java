package com.palcas.poker;

public enum Suit {

    DIAMONDS("Diamonds", "\u2666", Color.RED),
    SPADES("Spades", "\u2660", Color.BLACK),
    CLUBS("Clubs", "\u2663", Color.BLACK),
    HEARTS("Hearts", "\u2665", Color.RED);

    private static final String space = " ";

    private final String name;
    private final String formattedName;
    private final Color color;

    Suit(String name, String formattedName, Color color) {
        this.name = name;
        this.formattedName = space.repeat(3) + formattedName + space.repeat(3);
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getFormattedName() {
        return formattedName;
    }

    public Color getColor() {
        return color;
    }

    public String getColorCode() {
        if (color == Color.RED) {
            return "\u001B[31m";
        } else {
            return "\u001B[0m";
        }
    }
}

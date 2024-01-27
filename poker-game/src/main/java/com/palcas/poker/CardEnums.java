package com.palcas.poker;

public class CardEnums {

    // Enum for Suit of the card like Hearts or Diamonds
    public enum Suit {
        HEARTS("Hearts", " Hearts", Color.RED),
        DIAMONDS("Diamonds", " Dmnds ", Color.RED),
        CLUBS("Clubs", " Clubs ", Color.BLACK),
        SPADES("Spades", " Spades", Color.BLACK);

        private final String name;
        private final String formattedName;
        private final Color color;
        //Haben die verschiedenen Symbole auch einen Wert? Sowas wie Herz schlägt Kreuz?
        
        Suit(String name, String formattedName, Color color) {
            this.name = name;
            this.formattedName = formattedName;
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

    // Enum for the Rank of the card like 7 or King
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

    // Enum for the Color of the card like red or black
    public enum Color {
        RED,
        BLACK;
    }
}

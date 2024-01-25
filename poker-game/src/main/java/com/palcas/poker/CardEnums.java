package com.palcas.poker;

public class CardEnums {

    // Enum for Suit of the card like Hearts or Diamonds
    public enum Suit {
        HEARTS("Hearts", "H", Color.RED),
        DIAMONDS("Diamonds", "D", Color.RED),
        CLUBS("Clubs", "C", Color.BLACK),
        SPADES("Spades", "S", Color.BLACK);

        private final String name;
        private final String abbreviation;
        private final Color color;
        //Haben die verschiedenen Symbole auch einen Wert? Sowas wie Herz schlägt Kreuz?
        //irgendwo noch den colorANSICode;
        
        Suit(String name, String abbreviation, Color color) {
            this.name = name;
            this.abbreviation = abbreviation;
            this.color = color;
        }

        public String getName() {
            return name;
        }

        public String getAbbreviation() {
            return abbreviation;
        }

        public Color getColor() {
            return color;
        }
    }

    // Enum for the Rank of the card like 7 or King
    public enum Rank {
        TWO("2", 2),
        THREE("3", 3),
        FOUR("4", 4),
        FIVE("5", 5),
        SIX("6", 6),
        SEVEN("7", 7),
        EIGHT("8", 8),
        NINE("9", 9),
        TEN("10", 10),
        JACK("Jack", 11),
        QUEEN("Queen", 12),
        KING("King", 13),
        ACE("Ace", 14);

        private final String name;
        private final int value;

        Rank(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
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

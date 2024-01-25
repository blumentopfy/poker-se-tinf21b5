package com.palcas.poker;

public class CardEnums {
    public enum Suit {
        HEARTS("Hearts", "H"),
        DIAMONDS("Diamonds", "D"),
        CLUBS("Clubs", "C"),
        SPADES("Spades", "S");

        private final String name;
        private final String abbreviation;
        //Haben die verschiedenen Symbole auch einen Wert? Sowas wie Herz schlägt Kreuz?
        //Farbe an sich fehlt noch, vielleicht Farbe mit code doch außerhalb machen?
        //private final String colorANSICode;
        
        Suit(String name, String abbreviation) {
            this.name = name;
            this.abbreviation = abbreviation;
        }

        public String getName() {
            return name;
        }

        public String getAbbreviation() {
            return abbreviation;
        }
    }


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
}

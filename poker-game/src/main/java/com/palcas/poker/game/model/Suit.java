package com.palcas.poker.game.model;

    // Enum for Suit of the card like Hearts or Diamonds
    public enum Suit {

        DIAMONDS("Diamonds", Color.RED),
        SPADES("Spades", Color.BLACK),
        CLUBS("Clubs", Color.BLACK),
        HEARTS("Hearts", Color.RED);

        private final String name;
        private final Color color;

        Suit(String name, Color color) {
            this.name = name;
            this.color = color;
        }

        public String getName() {
            return name;
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
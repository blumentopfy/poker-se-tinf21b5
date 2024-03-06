package com.palcas.poker.game;

import java.util.ArrayList;

import com.palcas.poker.model.Rank;
import com.palcas.poker.model.Suit;

public class Deck {
    private final ArrayList<Card> cards;

    public Deck() {
        this.cards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
}

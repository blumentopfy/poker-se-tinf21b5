package com.palcas.poker.game;

import java.util.ArrayList;
import java.util.Collections;

import com.palcas.poker.game.model.Rank;
import com.palcas.poker.game.model.Suit;
import java.util.Objects;

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

    public Deck shuffleFullDeck() {
        cards.clear();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
        Collections.shuffle(cards);
        return this;
    }

    public Deck shuffle() {
        Collections.shuffle(cards);
        return this;
    }

    public Card drawCard() {
        return cards.remove(0);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Deck)) {
            return false;
        }
        Deck deck = (Deck) o;
        return Objects.equals(cards, deck.cards);
    }

    @Override
    public String toString() {
        return "{" +
                " cards='" + getCards() + "'" +
                "}";
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }
}

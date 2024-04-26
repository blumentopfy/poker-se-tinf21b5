package com.palcas.poker.game;

import java.util.List;
import java.util.Objects;

// Implementations of this class should represent a player's pocket, i.e. the 2 cards exclusive to him
public abstract class Pocket {
    List<Card> cards;

    public List<Card> getCards() {
        return this.cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Pocket)) {
            return false;
        }
        Pocket pocket = (Pocket) o;
        return Objects.equals(cards, pocket.cards);
    }

    @Override
    public String toString() {
        return "{" +
                " cards='" + getCards() + "'" +
                "}";
    }
}

package com.palcas.poker.game.variants.HoldEm;

import com.palcas.poker.game.Card;
import com.palcas.poker.game.Pocket;

import java.util.List;

//This class represents the unique cards each player get to play with in any round of the poker game
public class HoldEmPocket extends Pocket {
    List<Card> cards;

    public HoldEmPocket(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
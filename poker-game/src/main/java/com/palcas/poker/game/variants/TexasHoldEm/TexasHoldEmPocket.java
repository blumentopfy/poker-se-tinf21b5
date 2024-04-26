package com.palcas.poker.game.variants.TexasHoldEm;

import com.palcas.poker.game.Card;
import com.palcas.poker.game.Deck;
import com.palcas.poker.game.Pocket;

import java.util.ArrayList;
import java.util.List;

//This class represents the unique cards each player get to play with in any round of the poker game
public class TexasHoldEmPocket extends Pocket {
    List<Card> cards;

    public TexasHoldEmPocket() {
        this.cards = new ArrayList<Card>();
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public TexasHoldEmPocket populatePocket(Deck deck) {
        cards.add(deck.drawCard());
        cards.add(deck.drawCard());
        return this;
    }
}
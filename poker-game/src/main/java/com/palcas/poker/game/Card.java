package com.palcas.poker.game;

import com.palcas.poker.display.CardDisplay.Rank;
import com.palcas.poker.display.CardDisplay.Suit;


public class Card implements Comparable<Card>{
    private Suit suit;
    private Rank rank;
    
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        Card card = (Card) obj;

        return this.getSuit().equals(card.getSuit()) && this.getRank().equals(card.getRank());
    }

    @Override
    public int compareTo(Card otherCard) {
        return Integer.compare(this.getRank().getValue(), otherCard.getRank().getValue());
    }
}
package com.palcas.poker;

import com.palcas.poker.CardEnums.Rank;
import com.palcas.poker.CardEnums.Suit;


public class Card{
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
}
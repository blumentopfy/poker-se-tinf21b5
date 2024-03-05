package com.palcas.poker.game.checker.pokerHands;

import com.palcas.poker.game.Card;

import java.util.Arrays;

public class HighCard {
    public Card[] selectHandForHighCard(Card[] all7cards) {
        //TODO test this, not sure if this works
        Arrays.sort(all7cards, (a, b) -> b.compareTo(a));
        return Arrays.copyOfRange(all7cards, 0, 5);
    }
}

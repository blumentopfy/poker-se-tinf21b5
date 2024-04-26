package com.palcas.poker.game.evaluation.pokerHands;

import com.palcas.poker.game.Card;

import java.util.Arrays;
import java.util.Comparator;

public class HighCard {
    // Structure of hand returned: sorted by descending value
    public Card[] selectHandForHighCard(Card[] all7cards) {
        Arrays.sort(all7cards, Comparator.reverseOrder());
        return Arrays.copyOfRange(all7cards, 0, 5);
    }

    public int compareHighCardHands(Card[] hand1, Card[] hand2) {
        for (int i = 0; i < 5; i++) {
            if (hand1[i].compareTo(hand2[i]) != 0) {
                return hand1[i].compareTo(hand2[i]);
            }
        }
        return 0;
    }
}

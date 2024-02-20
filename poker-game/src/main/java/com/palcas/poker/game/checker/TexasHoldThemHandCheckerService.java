package com.palcas.poker.game.checker;

import com.palcas.poker.game.Card;
import com.palcas.poker.game.HandRanking;
import com.palcas.poker.game.checker.HandCheckerService;

public class TexasHoldThemHandCheckerService implements HandCheckerService {
    @Override
    public HandRanking check(Card[] all7cards) {
        return null;
    }

    @Override
    public Card[] select(Card[] all7cards) {
        return new Card[0];
    }

    @Override
    public int compare(Card[] handA, Card[] handB) {
        return 0;
    }
}

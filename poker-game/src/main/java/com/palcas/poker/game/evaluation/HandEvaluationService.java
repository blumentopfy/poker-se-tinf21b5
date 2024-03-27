package com.palcas.poker.game.evaluation;

import com.palcas.poker.game.Card;
import com.palcas.poker.game.HandRanking;

public interface HandEvaluationService {
    HandRanking check (Card[] all7cards);

    Card[] select (Card[] all7cards);

    int compare(Card[] handA, Card[] handB);
}

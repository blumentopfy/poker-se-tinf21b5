package com.palcas.poker.game.evaluation;

import com.palcas.poker.game.Card;
import com.palcas.poker.game.HandRanking;
import com.palcas.poker.game.Player;
import java.util.HashMap;

public interface HandEvaluationService {
    HandRanking check (Card[] all7cards);

    Card[] select (Card[] all7cards);

    int compareHandsOfSameHandRanking(Card[] handA, Card[] handB);

    Player[] determineWinner(HashMap<Player, Card[]> playerToHandList);

}

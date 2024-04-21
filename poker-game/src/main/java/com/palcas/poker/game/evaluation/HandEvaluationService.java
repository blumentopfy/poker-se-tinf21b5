package com.palcas.poker.game.evaluation;

import com.palcas.poker.game.Card;
import com.palcas.poker.game.HandRanking;
import com.palcas.poker.game.Player;
import java.util.HashMap;

public interface HandEvaluationService {
    HandRanking evaluate(Card[] all7cards);

    Card[] select(Card[] all7cards);

    int compare(Card[] all7cardsA, Card[] all7cardsB);

    Player[] determineWinner(HashMap<Player, Card[]> playerToHandList);

}

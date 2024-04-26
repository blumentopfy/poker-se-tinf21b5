package com.palcas.poker.game.evaluation;

import com.palcas.poker.game.Card;
import com.palcas.poker.game.Player;
import java.util.HashMap;

public interface HandEvaluationService {

    /*
     * @param all7cards an Array of 7 Cards, which are the 5 board cards and the 2
     * pocket cards
     * 
     * @return HandRanking (e.g. "Full House") which is the highest possible
     * combination with those 7 cards
     */
    HandRanking evaluate(Card[] all7cards);

    /*
     * @param all7cards an Array of 7 Cards, which are the 5 board cards and the 2
     * pocket cards
     * 
     * @return Card[] of size 5 with the 5 cards, that form the best possible
     * combination
     */
    Card[] select(Card[] all7cards);

    /*
     * @param all7cardsA an Array of 7 Cards of Player A, which are the 5 board
     * cards and the 2 pocket cards of Player A
     * 
     * @param all7cardsB an Array of 7 Cards of Player B, which are the 5 board
     * cards and the 2 pocket cards of Player B
     * 
     * @return 1 if Player A has the better combination, -1 if Player B has the
     * better combination, 0 if both combinations are equally good
     */
    int compare(Card[] all7cardsA, Card[] all7cardsB);

    /*
     * @param player7cards a Hashmap from Player to the 7 cards (board + pocket),
     * from which he can choose 5 for a combiantion
     * 
     * @return Player[] with those Players that have the highest combination out of
     * all Players in the provided Hashmap. Typically, this will only be one Player,
     * but it is possible for multiple people to have a comination exactly equally
     * good
     */
    Player[] determineWinner(HashMap<Player, Card[]> playerToHandList);
}

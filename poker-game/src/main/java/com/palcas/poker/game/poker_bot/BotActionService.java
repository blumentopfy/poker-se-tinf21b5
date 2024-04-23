package com.palcas.poker.game.poker_bot;

import java.util.List;

import com.palcas.poker.game.Card;
import com.palcas.poker.game.Player;
import com.palcas.poker.game.evaluation.HandEvaluationService;

public abstract class BotActionService {
    private HandEvaluationService handEvaluator;
    
    protected abstract BotAction decidePreFlopAction(Player bot, List<Player> players, int bigBlindAmount);
    protected abstract BotAction decideFlopAction(Player bot, List<Card> communityCards);
    protected abstract boolean foldRandomlyWithProbabilityOf(double foldProbability);
}

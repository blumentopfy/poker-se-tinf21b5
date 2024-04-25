package com.palcas.poker.game.poker_bot;

import java.util.List;

import com.palcas.poker.game.Card;
import com.palcas.poker.game.GameState;
import com.palcas.poker.game.Player;

public interface BotActionService {
    abstract BotAction decidePreFlopAction(Player bot, List<Player> players, GameState gameState);
    abstract BotAction decidePostFlopAction(Player bot, List<Card> communityCards, GameState gameState);
}

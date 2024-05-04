package com.palcas.poker.game.checker.pokerHands.mocks;

import com.palcas.poker.game.Card;
import com.palcas.poker.game.GameState;
import com.palcas.poker.game.Player;
import com.palcas.poker.game.poker_bot.BotAction;
import com.palcas.poker.game.poker_bot.BotActionService;

import java.util.List;

public class BotActionServiceMock implements BotActionService {
    @Override
    public BotAction decidePreFlopAction(Player bot, List<Player> players, GameState gameState) {
        return new BotAction(BotAction.ActionType.RAISE);
    }

    @Override
    public BotAction decidePostFlopAction(Player bot, List<Card> communityCards, GameState gameState) {
        return new BotAction(BotAction.ActionType.RAISE);
    }
}

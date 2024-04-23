package com.palcas.poker.game;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.palcas.poker.game.poker_bot.BotActionService;
import com.palcas.poker.game.variants.HoldEm.HoldEmPocket;

public abstract class Round {
    GameState gameState;
    BotActionService botActionService;
    List<Player> players;
    List<Card> mainPlayerCards;
    List<Card> communityCards;

    public abstract GameState executeRound();
    protected abstract LinkedHashMap<Player, HoldEmPocket> distributePocketCards();
    protected abstract void bettingLoop(int bigBlindIndex);
    protected abstract void flop();
    protected abstract void turn();
    protected abstract void river();
    protected abstract List<Player> determineWinners(HashMap<Player, HoldEmPocket> playersWithPockets, List<Card> communityCards);
    protected abstract void checkForWalk();
    protected abstract void bet(Player player);
    protected abstract void check(Player player);
    protected abstract void call(Player player);
    protected abstract void raise(Player player);
    protected abstract void fold(Player player);
    protected abstract boolean checkIfBettingOver();
    
    public Round(GameState gameState, BotActionService botActionService, List<Card> mainPlayerCards) {
        this.gameState = gameState;
        this.botActionService = botActionService;
        this.mainPlayerCards = mainPlayerCards;
    }
}

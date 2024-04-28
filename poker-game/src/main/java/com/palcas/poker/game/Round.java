package com.palcas.poker.game;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.palcas.poker.game.poker_bot.BotAction;
import com.palcas.poker.game.poker_bot.BotActionService;
import com.palcas.poker.game.poker_bot.IllegalBotActionException;

public abstract class Round {
    GameState gameState;
    BotActionService botActionService;
    List<Player> players;
    List<Card> mainPlayerCards;
    List<Card> communityCards;

    public abstract GameState executeRound();
    protected abstract LinkedHashMap<Player, ? extends Pocket> distributePocketCards();
    protected abstract void bettingLoop(int bigBlindIndex);
    protected abstract void flop();
    protected abstract void turn();
    protected abstract void river();
    protected abstract List<Player> determineWinners(HashMap<Player, ? extends Pocket> playersWithPockets, List<Card> communityCards);
    protected abstract void checkForWalk();
    protected abstract void bet(Player player);
    protected abstract void mainPlayerCheck(Player player);
    protected abstract void mainPlayerCall(Player player);
    protected abstract void mainPlayerRaise(Player player);
    protected abstract void mainPlayerFold(Player player);
    protected abstract void mainPlayerAllIn(Player player);
    protected abstract void botCheck(Player bot) throws IllegalBotActionException;
    protected abstract void botCall(Player bot) throws IllegalBotActionException;
    protected abstract void botRaise(Player bot, BotAction botAction) throws IllegalBotActionException;
    protected abstract void botFold(Player bot);
    protected abstract void botAllIn(Player bot);
    protected abstract boolean checkIfBettingOver();
    
    public Round(GameState gameState, BotActionService botActionService, List<Card> mainPlayerCards) {
        this.gameState = gameState;
        this.botActionService = botActionService;
        this.mainPlayerCards = mainPlayerCards;
    }
}

package com.palcas.poker.game;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.palcas.poker.game.poker_bot.BotAction;
import com.palcas.poker.game.poker_bot.IllegalBotActionException;

public interface Round {
    public GameState executeRound();

    LinkedHashMap<Player, ? extends Pocket> distributePocketCards();

    void bettingLoop(int bigBlindIndex);

    void flop();

    void turn();

    void river();

    List<Player> determineWinners(HashMap<Player, ? extends Pocket> playersWithPockets,
            List<Card> communityCards);

    void checkForWalk();

    void bet(Player player);

    void mainPlayerCheck(Player player);

    void mainPlayerCall(Player player);

    void mainPlayerRaise(Player player);

    void mainPlayerFold(Player player);

    void mainPlayerAllIn(Player player);

    void botCheck(Player bot) throws IllegalBotActionException;

    void botCall(Player bot) throws IllegalBotActionException;

    void botRaise(Player bot, BotAction botAction) throws IllegalBotActionException;

    void botFold(Player bot);

    void botAllIn(Player bot);

    boolean checkIfBettingOver();
}

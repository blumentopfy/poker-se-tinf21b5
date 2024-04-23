package com.palcas.poker.game;

import java.util.List;
import java.util.Scanner;

import com.palcas.poker.game.poker_bot.BotActionService;

public abstract class PokerGame {
    private Scanner scanner;
    private Player mainPlayer;
    private BotActionService botActionService;
    private GameState gameState;

    protected abstract void start();
    protected abstract void startPokerGameLoop();
    protected abstract void initializeBlinds(int smallBlindValue);
    protected abstract void resetStatesAndBets();
    protected abstract void setBlinds();
    protected abstract void adjustBlinds(int round);
    protected abstract void checkLosers();
    protected abstract void splitPot(List<Player> winners);
}

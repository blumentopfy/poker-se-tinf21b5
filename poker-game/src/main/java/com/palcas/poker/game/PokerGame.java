package com.palcas.poker.game;

import java.util.List;
import java.util.Scanner;

import com.palcas.poker.game.poker_bot.BotActionService;

public abstract class PokerGame {
    private Scanner scanner;
    private Player mainPlayer;
    private int initialMainPlayerChips;
    private BotActionService botActionService;
    private GameState gameState;
    private GameResult gameResult;
    public int chipsWon;
    public int roundsPlayed;

    protected abstract GameResult playGame();
    protected abstract void startPokerGameLoop();
    protected abstract void initializeBlinds(int smallBlindValue);
    protected abstract void resetStatesAndBets();
    protected abstract void setBlinds();
    protected abstract void adjustBlinds(int round);
    protected abstract boolean checkLosers();
    protected abstract void splitPot(List<Player> winners);
}

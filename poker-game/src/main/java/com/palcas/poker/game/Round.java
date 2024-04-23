package com.palcas.poker.game;

import java.util.LinkedHashMap;
import java.util.List;

import com.palcas.poker.game.poker_bot.TexasHoldEmBotActionService;
import com.palcas.poker.game.variants.HoldEm.HoldEmPocket;
import java.util.Objects;

public abstract class Round {
    GameState gameState;
    TexasHoldEmBotActionService botActionService;
    List<Card> mainPlayerCards;
    List<Card> communityCards;

    public abstract GameState executeRound();
    protected abstract LinkedHashMap<Player, HoldEmPocket> distributePocketCards();
    protected abstract void bettingLoop(int bigBlindIndex);
    protected abstract void flop();
    protected abstract void turn();
    protected abstract void river();
    protected abstract List<Player> determineWinners();
    protected abstract void checkForWalk();
    protected abstract void bet(Player player);
    protected abstract void check(Player player);
    protected abstract void call(Player player);
    protected abstract void raise(Player player);
    protected abstract void fold(Player player);
    protected abstract boolean checkIfBettingOver();
    
    public Round(GameState gameState, TexasHoldEmBotActionService botActionService, List<Card> mainPlayerCards, List<Card> communityCards) {
        this.gameState = gameState;
        this.botActionService = botActionService;
        this.mainPlayerCards = mainPlayerCards;
        this.communityCards = communityCards;
    }

    public GameState getGameState() {
        return this.gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public TexasHoldEmBotActionService getBotActionService() {
        return this.botActionService;
    }

    public void setBotActionService(TexasHoldEmBotActionService botActionService) {
        this.botActionService = botActionService;
    }

    public List<Card> getMainPlayerCards() {
        return this.mainPlayerCards;
    }

    public void setMainPlayerCards(List<Card> mainPlayerCards) {
        this.mainPlayerCards = mainPlayerCards;
    }

    public List<Card> getCommunityCards() {
        return this.communityCards;
    }

    public void setCommunityCards(List<Card> communityCards) {
        this.communityCards = communityCards;
    }

    public Round gameState(GameState gameState) {
        setGameState(gameState);
        return this;
    }

    public Round botActionService(TexasHoldEmBotActionService botActionService) {
        setBotActionService(botActionService);
        return this;
    }

    public Round mainPlayerCards(List<Card> mainPlayerCards) {
        setMainPlayerCards(mainPlayerCards);
        return this;
    }

    public Round communityCards(List<Card> communityCards) {
        setCommunityCards(communityCards);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Round)) {
            return false;
        }
        Round round = (Round) o;
        return Objects.equals(gameState, round.gameState) && Objects.equals(botActionService, round.botActionService) && Objects.equals(mainPlayerCards, round.mainPlayerCards) && Objects.equals(communityCards, round.communityCards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameState, botActionService, mainPlayerCards, communityCards);
    }

    @Override
    public String toString() {
        return "{" +
            " gameState='" + getGameState() + "'" +
            ", botActionService='" + getBotActionService() + "'" +
            ", mainPlayerCards='" + getMainPlayerCards() + "'" +
            ", communityCards='" + getCommunityCards() + "'" +
            "}";
    }
}

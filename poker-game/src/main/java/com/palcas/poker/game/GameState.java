package com.palcas.poker.game;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public abstract class GameState {
    Player mainPlayer;
    List<Player> players;
    HashMap<Player, Pocket> playersWithPockets;
    int bigBlindIndex;
    int smallBlindIndex;
    int pot;
    int roundsPlayed;
    int roundsWonByMainPlayer;

    public GameState(Player mainPlayer, List<Player> players, HashMap<Player,Pocket> playersWithPockets, int bigBlindIndex, int smallBlindIndex, int pot, int roundsPlayed, int roundsWonByMainPlayer) {
        this.mainPlayer = mainPlayer;
        this.players = players;
        this.playersWithPockets = playersWithPockets;
        this.bigBlindIndex = bigBlindIndex;
        this.smallBlindIndex = smallBlindIndex;
        this.pot = pot;
        this.roundsPlayed = roundsPlayed;
        this.roundsWonByMainPlayer = roundsWonByMainPlayer;
    }

    public Player getMainPlayer() {
        return this.mainPlayer;
    }

    public void setMainPlayer(Player mainPlayer) {
        this.mainPlayer = mainPlayer;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public HashMap<Player,Pocket> getPlayersWithPockets() {
        return this.playersWithPockets;
    }

    public void setPlayersWithPockets(HashMap<Player,Pocket> playersWithPockets) {
        this.playersWithPockets = playersWithPockets;
    }

    public int getBigBlindIndex() {
        return this.bigBlindIndex;
    }

    public void setBigBlindIndex(int bigBlindIndex) {
        this.bigBlindIndex = bigBlindIndex;
    }

    public int getSmallBlindIndex() {
        return this.smallBlindIndex;
    }

    public void setSmallBlindIndex(int smallBlindIndex) {
        this.smallBlindIndex = smallBlindIndex;
    }

    public int getPot() {
        return this.pot;
    }

    public void setPot(int pot) {
        this.pot = pot;
    }

    public int getRoundsPlayed() {
        return this.roundsPlayed;
    }

    public void setRoundsPlayed(int roundsPlayed) {
        this.roundsPlayed = roundsPlayed;
    }

    public int getRoundsWonByMainPlayer() {
        return this.roundsWonByMainPlayer;
    }

    public void setRoundsWonByMainPlayer(int roundsWonByMainPlayer) {
        this.roundsWonByMainPlayer = roundsWonByMainPlayer;
    }

    public GameState mainPlayer(Player mainPlayer) {
        setMainPlayer(mainPlayer);
        return this;
    }

    public GameState players(List<Player> players) {
        setPlayers(players);
        return this;
    }

    public GameState playersWithPockets(HashMap<Player,Pocket> playersWithPockets) {
        setPlayersWithPockets(playersWithPockets);
        return this;
    }

    public GameState bigBlindIndex(int bigBlindIndex) {
        setBigBlindIndex(bigBlindIndex);
        return this;
    }

    public GameState smallBlindIndex(int smallBlindIndex) {
        setSmallBlindIndex(smallBlindIndex);
        return this;
    }

    public GameState pot(int pot) {
        setPot(pot);
        return this;
    }

    public GameState roundsPlayed(int roundsPlayed) {
        setRoundsPlayed(roundsPlayed);
        return this;
    }

    public GameState roundsWonByMainPlayer(int roundsWonByMainPlayer) {
        setRoundsWonByMainPlayer(roundsWonByMainPlayer);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof GameState)) {
            return false;
        }
        GameState gameState = (GameState) o;
        return Objects.equals(mainPlayer, gameState.mainPlayer) && Objects.equals(players, gameState.players) && Objects.equals(playersWithPockets, gameState.playersWithPockets) && bigBlindIndex == gameState.bigBlindIndex && smallBlindIndex == gameState.smallBlindIndex && pot == gameState.pot && roundsPlayed == gameState.roundsPlayed && roundsWonByMainPlayer == gameState.roundsWonByMainPlayer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mainPlayer, players, playersWithPockets, bigBlindIndex, smallBlindIndex, pot, roundsPlayed, roundsWonByMainPlayer);
    }

    @Override
    public String toString() {
        return "{" +
            " mainPlayer='" + getMainPlayer() + "'" +
            ", players='" + getPlayers() + "'" +
            ", playersWithPockets='" + getPlayersWithPockets() + "'" +
            ", bigBlindIndex='" + getBigBlindIndex() + "'" +
            ", smallBlindIndex='" + getSmallBlindIndex() + "'" +
            ", pot='" + getPot() + "'" +
            ", roundsPlayed='" + getRoundsPlayed() + "'" +
            ", roundsWonByMainPlayer='" + getRoundsWonByMainPlayer() + "'" +
            "}";
    }
}

package com.palcas.poker.game;

import java.util.Objects;

// Represents the result of a game, should be returned by a game and used by session to persist progress
public class GameResult {
    private final Player player;
    private final int chipsChange;
    private final int chipsWon;
    private final int roundsPlayed;
    private final int roundsWonByMainPlayer;

    public GameResult(Player player, int chipsChange, int chipsWon, int roundsPlayed, int roundsWon) {
        this.player = player;
        this.chipsChange = chipsChange;
        this.chipsWon = chipsWon;
        this.roundsPlayed = roundsPlayed;
        this.roundsWonByMainPlayer = roundsWon;
    }

    public Player getPlayer() {
        return this.player;
    }

    public int getChipsChange() {
        return this.chipsChange;
    }

    public int getChipsWon() {
        return this.chipsWon;
    }

    public int getRoundsPlayed() {
        return this.roundsPlayed;
    }

    public int getRoundsWonByMainPlayer() {
        return this.roundsWonByMainPlayer;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof GameResult)) {
            return false;
        }
        GameResult gameResult = (GameResult) o;
        return Objects.equals(player, gameResult.player) && chipsChange == gameResult.chipsChange
                && chipsWon == gameResult.chipsWon && roundsPlayed == gameResult.roundsPlayed
                && roundsWonByMainPlayer == gameResult.roundsWonByMainPlayer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, chipsChange, chipsWon, roundsPlayed, roundsWonByMainPlayer);
    }

    @Override
    public String toString() {
        return "{" +
                " player='" + getPlayer() + "'" +
                ", chipsChange='" + getChipsChange() + "'" +
                ", chipsWon='" + getChipsWon() + "'" +
                ", roundsPlayed='" + getRoundsPlayed() + "'" +
                ", roundsWon='" + getRoundsWonByMainPlayer() + "'" +
                "}";
    }
}

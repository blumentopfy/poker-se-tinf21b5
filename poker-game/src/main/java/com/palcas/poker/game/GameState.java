package com.palcas.poker.game;

import com.palcas.poker.game.variants.RoundStatus;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;

public class GameState {
    public Player mainPlayer;
    public List<Player> players;
    public int bigBlindIndex;
    public int smallBlindIndex;
    public int bigBlind;
    public int smallBlind;
    public int pot;
    private RoundStatus roundStatus;

    public RoundStatus getRoundStatus() {
        return roundStatus;
    }

    public void setRoundStatus(RoundStatus roundStatus) {
        this.roundStatus = roundStatus;
    }

    private HashMap<Player, Pocket> playersWithPockets;
    private List<Card> communityCards;
    private Deck deck;
    private int roundsPlayed;
    private int roundsWonByMainPlayer;
    private SimpleEntry<Player, Integer> playerToHighestBet;
    private List<Player> winners;

    public GameState(Player mainPlayer, ArrayList<Player> players) {
        this.mainPlayer = mainPlayer;
        this.players = players;
        this.playersWithPockets = new HashMap<Player, Pocket>();
        this.bigBlindIndex = 1;
        this.smallBlindIndex = 0;
        this.pot = 0;
        this.roundsPlayed = 0;
        this.roundsWonByMainPlayer = 0;
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

    public HashMap<Player, Pocket> getPlayersWithPockets() {
        return this.playersWithPockets;
    }

    public void setPlayersWithPockets(HashMap<Player, Pocket> playersWithPockets) {
        this.playersWithPockets = playersWithPockets;
    }

    public int getBigBlindIndex() {
        return this.bigBlindIndex;
    }

    public void setBigBlindIndex(int bigBlindIndex) {
        this.bigBlindIndex = bigBlindIndex;
    }

    public Player getSmallBlindPlayer() {
        return players.get(smallBlindIndex);
    }

    public Player getBigBlindPlayer() {
        return players.get(bigBlindIndex);
    }

    public int getSmallBlind() {
        return smallBlind;
    }

    public int getBigBlind() {
        return bigBlind;
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

    public Deck getDeck() {
        return this.deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public SimpleEntry<Player, Integer> getPlayerToHighestBet() {
        return this.playerToHighestBet;
    }

    public void setPlayerToHighestBet(SimpleEntry<Player, Integer> playerToHighestBet) {
        this.playerToHighestBet = playerToHighestBet;
    }

    public List<Card> getCommunityCards() {
        return this.communityCards;
    }

    public void setCommunityCards(List<Card> communityCards) {
        this.communityCards = communityCards;
    }

    public List<Player> getWinners() {
        return this.winners;
    }

    public void setWinners(List<Player> winners) {
        this.winners = winners;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof GameState)) {
            return false;
        }
        GameState gameState = (GameState) o;
        return Objects.equals(mainPlayer, gameState.mainPlayer) && Objects.equals(players, gameState.players)
                && Objects.equals(playersWithPockets, gameState.playersWithPockets)
                && bigBlindIndex == gameState.bigBlindIndex && smallBlindIndex == gameState.smallBlindIndex
                && pot == gameState.pot && roundsPlayed == gameState.roundsPlayed
                && roundsWonByMainPlayer == gameState.roundsWonByMainPlayer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mainPlayer, players, playersWithPockets, bigBlindIndex, smallBlindIndex, pot, roundsPlayed,
                roundsWonByMainPlayer);
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

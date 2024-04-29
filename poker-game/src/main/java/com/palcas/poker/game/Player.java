package com.palcas.poker.game;

import java.io.IOException;
import java.util.Random;

import com.palcas.poker.model.PlayerState;
import com.palcas.poker.persistance.Account;
import com.palcas.poker.persistance.AccountRepository;

import java.util.Objects;

public class Player {
    protected String name;
    protected int chips;
    private Pocket pocket;
    private int bet;
    private PlayerState state;
    private int aggressionLevel;
    private int bluffAffinity;
    private boolean isPersistedAsAccount;

    private AccountRepository accountRepository;

    public Player(String name, int chips) {
        //this constructor is for a bot or guest.
        // It needs no Repository but maybe aggression level and bluffAffinity for the botActionService
        this.name = name;
        this.chips = chips;
        this.aggressionLevel = generateRandomIntBetween(1, 100);
        this.bluffAffinity = generateRandomIntBetween(1, 100);
        this.isPersistedAsAccount = false;
    }

    public Player(String name, int chips, AccountRepository accountRepository) {
        //this constructor is for a player that has an Account and is supposed to be persisted.
        // It needs a Repository for persistence but no aggression level or bluffAffinity
        this.accountRepository = accountRepository;
        this.name = name;
        this.chips = chips;
        this.isPersistedAsAccount = true;
    }

    public Player(String name) {
        this.name = name;
        this.chips = 0;
    }

    public String getName() {
        return name;
    }

    public int getChips() {
        return chips;
    }

    public void setChips(int chips) {
        this.chips = chips;
        if(isPersistedAsAccount) {
            updatePersistedAccountWithCurrentChips();
        }
    }

    public void addChips(int amountToAdd) {
        chips += amountToAdd;
        if(isPersistedAsAccount) {
            updatePersistedAccountWithCurrentChips();
        }
    }

    public void substractChips(int amountToSubstract) {
        chips -= amountToSubstract;
        if(isPersistedAsAccount) {
            updatePersistedAccountWithCurrentChips();
        }
    }

    public Pocket getPocket() {
        return pocket;
    }

    public void setPocket(Pocket pocket) {
        this.pocket = pocket;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public PlayerState getState() {
        return state;
    }

    public void setState(PlayerState state) {
        this.state = state;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAggressionLevel() {
        return this.aggressionLevel;
    }

    public void setAggressionLevel(int aggressionLevel) {
        this.aggressionLevel = aggressionLevel;
    }

    public int getBluffAffinity() {
        return this.bluffAffinity;
    }

    public void setBluffAffinity(int bluffAffinity) {
        this.bluffAffinity = bluffAffinity;
    }

    public int generateRandomIntBetween(int min, int max) {
        Random random = new Random();
        return random.ints(min, max).findFirst().getAsInt();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Player)) {
            return false;
        }
        Player player = (Player) o;
        return Objects.equals(name, player.name) && chips == player.chips && Objects.equals(pocket, player.pocket)
                && bet == player.bet && Objects.equals(state, player.state) && aggressionLevel == player.aggressionLevel
                && bluffAffinity == player.bluffAffinity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, chips, pocket, bet, state, aggressionLevel, bluffAffinity);
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", chips=" + chips +
                ", pocket=" + pocket +
                ", bet=" + bet +
                ", state=" + state +
                ", aggressionLevel=" + aggressionLevel +
                ", bluffAffinity=" + bluffAffinity +
                ", isPersistedAsAccount=" + isPersistedAsAccount +
                ", accountRepository=" + accountRepository +
                '}';
    }

    private void updatePersistedAccountWithCurrentChips() {
        try {
            Account mainPlayerAccount = accountRepository.loadAccount(name);
            mainPlayerAccount.setChips(chips);
            accountRepository.saveAccount(mainPlayerAccount);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

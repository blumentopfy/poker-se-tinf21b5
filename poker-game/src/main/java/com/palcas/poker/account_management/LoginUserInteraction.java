package com.palcas.poker.account_management;

import com.palcas.poker.game.Player;

public interface LoginUserInteraction {
    public Player login();
    public Player register();
    public Player loginAsGuest();
}

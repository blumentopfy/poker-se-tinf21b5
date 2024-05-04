package com.palcas.poker.persistance.login;

import com.palcas.poker.game.Player;

public interface LoginUserInteraction {
    public Player login();
    public Player register();
    public Player loginAsGuest();
}

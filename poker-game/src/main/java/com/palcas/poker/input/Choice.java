package com.palcas.poker.input;

//TODO Choice shouldn't include several choices, possibly rename to "option" or sth
public interface Choice {
    Choice addChoice(String option);
    Choice withAction(Runnable action);
    void executeChoice();
}
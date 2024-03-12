package com.palcas.poker.input;

public interface Choice {
    Choice addChoice(String option);
    Choice withAction(Runnable action);
    void executeChoice();
}
package com.palcas.poker.input;

// This interface is used to create a choice of limited options
public interface LimitedChoice {
    LimitedChoice addOption(String option);
    LimitedChoice withAction(Runnable action);
    void executeChoice();
}
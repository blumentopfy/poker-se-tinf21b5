package com.palcas.poker.input;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BetChoice implements LimitedChoice {
    private Scanner scanner;
    private Map<String, Runnable> optionsToRunnables = new HashMap<>();
    private String option;

    public BetChoice(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public BetChoice addOption(String option) {
        this.optionsToRunnables.put(option, null);
        return this;
    }

    @Override
    public BetChoice withAction(Runnable action) {
        this.optionsToRunnables.put(this.option, action);
        return this;
    }

    @Override
    public void executeChoice() {
        System.out.println("Please choose an action:");

        for (String option : this.optionsToRunnables.keySet()) {
            System.out.println(option);
        }

        while (true) {
            this.option = scanner.nextLine().toUpperCase();

            if (this.optionsToRunnables.containsKey(this.option)) {
                this.optionsToRunnables.get(this.option).run();
                break;
            } else {
                System.out.println("Invalid option! Please choose a valid action.");
            }
        }
    }
}
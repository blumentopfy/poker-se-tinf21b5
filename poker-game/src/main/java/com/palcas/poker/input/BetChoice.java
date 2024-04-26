package com.palcas.poker.input;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import com.palcas.poker.display.DisplayElements;
import com.palcas.poker.game.Player;

public class BetChoice implements LimitedChoice {
    private Scanner scanner;
    private Map<String, Runnable> optionsToRunnables = new LinkedHashMap<>();
    private String option;
    Entry<Player, Integer> playerToHighestBet;

    public BetChoice(Scanner scanner, Entry<Player, Integer> playerToHighestBet) {
        this.scanner = scanner;
        this.playerToHighestBet = playerToHighestBet;
    }

    @Override
    public BetChoice addOption(String option) {
        this.option = option;
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
        DisplayElements.printSeperator();
        System.out.println("Please choose an action:");

        for (String option : this.optionsToRunnables.keySet()) {
            if (option.equals("(CALL)")) {
                System.out.println("(CALL) " + playerToHighestBet.getKey().getName() + "'s bet of "
                        + playerToHighestBet.getValue() + ".");
            } else {
                System.out.println(option);
            }
        }

        while (true) {
            String userInput = scanner.nextLine();
            boolean actionExecuted = false;

            if ("CALL".equals(userInput)) {
                this.optionsToRunnables.get("(CALL)").run();
                actionExecuted = true;
                break;
            }

            for (String option : this.optionsToRunnables.keySet()) {
                // Look at 2nd character of option according to how options are defined, not the
                // best way I'm sure
                if (option.charAt(1) == userInput.charAt(0)) {
                    this.optionsToRunnables.get(option).run();
                    actionExecuted = true;
                }
            }

            if (actionExecuted) {
                break;
            } else {
                DisplayElements.printSeperator();
                System.out.println("Invalid option! Please choose a valid action:");
                System.out.println("\"C\" for checking");
                System.out.println("\"CALL\" for calling");
                System.out.println("\"R\" for raising");
                System.out.println("\"F\" for folding");

            }
        }
    }
}
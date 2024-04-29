package com.palcas.poker.input;

import com.palcas.poker.display.DisplayElements;

import java.util.*;

public class StakeLevelChoice implements LimitedChoice {
    private final Scanner scanner;
    private final Map<String, Runnable> choicesToRunnables;
    String option;
    int choice;

    public StakeLevelChoice(Scanner scanner) {
        this.scanner = scanner;
        this.choicesToRunnables = new LinkedHashMap<String, Runnable>();
    }

    @Override
    public LimitedChoice addOption(String option) {
        this.option = option;
        return this;
    }

    @Override
    public LimitedChoice withAction(Runnable action) {
        this.choicesToRunnables.put(this.option, action);
        return this;
    }

    @Override
    public void executeChoice() {
        System.out.println("------STAKE LEVEL CHOICE------");
        System.out.println("Please choose a stake level:");

        List<String> choices = new ArrayList<>(this.choicesToRunnables.keySet());

        for (int i = 0; i < choices.size(); i++) {
            System.out.println((i + 1) + ". " + choices.get(i));
        }

        System.out.println(DisplayElements.SEPERATOR);

        while (true) {
            try {
                this.choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                break; // Exit the loop if no exception is thrown
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice! Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
            }
        }

        if (choice >= 1 && choice <= choices.size()) {
            // Execute the choice that is stored at the index of the choice - 1
            choicesToRunnables.get(choices.get(choice - 1)).run();
        } else {
            System.out.println("Invalid choice, please try again.");
        }
    }
}

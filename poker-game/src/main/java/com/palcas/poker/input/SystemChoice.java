package com.palcas.poker.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SystemChoice implements Choice {
    private Scanner scanner;
    private Map<String, Runnable> choicesToRunnables;
    String option;
    int choice;

    public SystemChoice(Scanner scanner) {
        this.scanner = scanner;
        this.choicesToRunnables = new HashMap<String, Runnable>();
    }

    public SystemChoice addChoice(String option) {
        this.option = option;
        return this;
    }

    
    public SystemChoice withAction(Runnable action) {
        this.choicesToRunnables.put(this.option, action);
        return this;
    }

    public void executeSystemChoice() {
        System.out.println("Please choose an option:");
        List<String> choices = new ArrayList<>(this.choicesToRunnables.keySet());

        for (int i = 0; i < choices.size(); i++) {
            System.out.println((i + 1) + ". " + choices.get(i));
        }

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
            System.out.println("Invalid choice!");
        }
    }
}
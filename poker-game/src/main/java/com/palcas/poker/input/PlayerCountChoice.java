package com.palcas.poker.input;

import java.util.InputMismatchException;
import java.util.Scanner;

public class PlayerCountChoice {
    private Scanner scanner;
    private int playerCount;

    public PlayerCountChoice(Scanner scanner) {
        this.scanner = scanner;
    }

    public PlayerCountChoice addChoice(String option) {
        System.out.println(option);
        return this;
    }

    public PlayerCountChoice withAction(Runnable action) {
        // This method is not used in this class
        return this;
    }

    public int executeChoice() {
        System.out.println("Please enter the number of players (1-8):");

        while (true) {
            try {
                this.playerCount = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                if (playerCount >= 1 && playerCount <= 8) {
                    break; // Exit the loop if a valid number is entered
                } else {
                    System.out.println("Invalid number! Please enter a number between 1 and 8.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
            }
        }

        return this.playerCount;
    }
}
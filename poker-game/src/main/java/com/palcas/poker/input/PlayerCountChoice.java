package com.palcas.poker.input;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

public class PlayerCountChoice implements ChoiceWithOpenOption {
    private final Scanner scanner;

    public PlayerCountChoice(Scanner scanner) {
        this.scanner = scanner;
    }

    public Optional<Integer> executeChoice() {
        System.out.println("Please enter the number of players (1-8):");

        int playerCount;
        while (true) {
            try {
                playerCount = scanner.nextInt();
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

        return Optional.of(playerCount);
    }
}
package com.palcas.poker.input;

import java.util.InputMismatchException;
import java.util.Scanner;

public class StartingChipsChoice {
    private final Scanner scanner;

    public StartingChipsChoice(Scanner scanner) {
        this.scanner = scanner;
    }

    public int executeChoice() {
        System.out.println("Please enter the amount of chips every player shall have at the beginning (100-1.000.000):");

        int startingChips;
        while (true) {
            try {
                startingChips = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                if (startingChips >= 100 && startingChips <= 1000000) {
                    break; // Exit the loop if a valid number is entered
                } else {
                    System.out.println("Invalid number! Please enter a number between 100 and 1000000.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
            }
        }

        return startingChips;
    }
}

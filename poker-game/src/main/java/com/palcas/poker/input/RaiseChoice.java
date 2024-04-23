package com.palcas.poker.input;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

public class RaiseChoice implements ChoiceWithOpenOption {
    private Scanner scanner;
    private int raiseAmount;

    public RaiseChoice(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Optional<Object> executeChoice() {
        System.out.println("Please enter the amount you want to raise by:");

        while (true) {
            try {
                this.raiseAmount = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                if (raiseAmount > 0) {
                    System.out.println("You chose to raise by " + raiseAmount + " chips.");
                    return Optional.of(raiseAmount);
                } else {
                    System.out.println("Invalid amount! Please enter a positive number.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
    }
}
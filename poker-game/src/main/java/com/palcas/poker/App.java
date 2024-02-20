package com.palcas.poker;

import java.util.Scanner;

// Entry point for the game, should really only include game loop
public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello to Palcas Poker v3: Electric Bogaloo!");
        System.out.println("Please enter your name to start the game: ");
        String playerName = scanner.nextLine();

        // Game Logic in this class later
        // PokerGame game = new PokerGame();

        while (true) {
            System.out.println("Welcome " + playerName + " to the game!");
            System.out.println("Please choose an option: ");
            System.out.println("1. Start a new game");
            System.out.println("2. Exit the game");
            int option = scanner.nextInt();
            if (option == 1) {
                System.out.println("Starting a new game...");
                // game.startGame();
            } else if (option == 2) {
                System.out.println("Exiting the game...");
                break;
            } else {
                System.out.println("Invalid option! Please try again.");
            }

        }
    }
}
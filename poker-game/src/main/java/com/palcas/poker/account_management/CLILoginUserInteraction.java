package com.palcas.poker.account_management;

import com.palcas.poker.constants.JacksonPersistenceSettings;
import com.palcas.poker.game.Player;
import com.palcas.poker.persistance.AccountRepository;
import com.palcas.poker.persistance.JacksonAccountRepository;

import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

public class CLILoginUserInteraction implements LoginUserInteraction {
    Scanner scanner;
    LoginManager loginManager;
    AccountRepository accountRepository;

    public CLILoginUserInteraction() {
        scanner = new Scanner(System.in);
        this.accountRepository = new JacksonAccountRepository(
                JacksonPersistenceSettings.ACCOUNT_FILE_PATH
        );
        loginManager = new LoginManager(accountRepository);
    }

    @Override
    public Player login() {
        System.out.println("Please log in with your account credentials.");
        for (int i = 3; i > 0; i--) {
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();
            Optional<Player> optionalPlayer = loginManager.login(username, password);
            if (optionalPlayer.isPresent()) {
                Player player = optionalPlayer.get();
                System.out.println("Successfully logged in as " + player.getName());
                return player;
            } else if (i > 1) {
                System.out.println("Username or password do not match...");
                System.out.println("Please try again (" + (i - 1) + "/3 tries left)");
            }
        }
        System.out.println("Username or password do not match...");
        System.out.println("Exiting the program...");
        System.exit(0);
        return null;
    }

    @Override
    public Player register() {
        System.out.println("Welcome to Palcas Poker! Please provide the following information to create an account.");
        boolean registrationSuccessful = false;
        while (!registrationSuccessful) {
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            Optional<Player> optionalPlayer = null;
            try {
                optionalPlayer = loginManager.register(username, password);
                if (optionalPlayer.isPresent()) {
                    Player player = optionalPlayer.get();
                    System.out.println("Successfully registered and logged in as " + player.getName());
                    return player;
                } else {
                    System.out.println("Something unexpected happened during login. Please contact your Admin.");
                }
            } catch (AccountAlreadyExistsException e) {
                System.out.println(e.getMessage() + " Please choose another username");
            } catch (PasswordRequirementsException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(
                        "Something went wrong looking up existing users... show the following lines to your administrator: ");
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Player loginAsGuest() {
        Player player = loginManager.loginAsGuest();
        System.out.println("Successfully logged in as " + player.getName());
        return player;
    }
}

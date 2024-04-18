package com.palcas.poker.account_management;

import com.palcas.poker.game.Player;
import com.palcas.poker.persistance.Account;
import com.palcas.poker.persistance.AccountRepository;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import java.io.IOException;
import java.util.Optional;

public class LoginManager {
    private AccountRepository accountRepository;

    public LoginManager(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Optional<Player> login(String username, String password) {
        try {
            Account account = accountRepository.loadAccount(username);
            if (account != null && account.getPasswordHash().equals(hashPassword(password, account.getPasswordSalt()))) {
                return Optional.of(new Player(account.getName(), account.getChips()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Login failed
        return Optional.empty();
    }

    public Optional<Player> register(String username, String password) throws AccountAlreadyExistsException{
        //TODO add constraints for Names and Passwords
        try {
            if (username.startsWith("Guest-")) {
                throw new AccountAlreadyExistsException("Username is not allowed to start with \"Guest\"...");
            }
            if (accountRepository.loadAccount(username) == null) {
                String salt = getSalt();
                Account newAccount = new Account(username, hashPassword(password, salt), salt);
                accountRepository.saveAccount(newAccount);
                return Optional.of(new Player(newAccount.getName(), newAccount.getChips()));
            } else {
                throw new AccountAlreadyExistsException("An account with this name already exists...");
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // Login failed
        return Optional.empty();
    }

    public Player loginAsGuest() {
        int randNumber = (int)(Math.ceil(Math.random()*1000));
        return new Player("Guest-"+randNumber, 2000);
    }

    private String hashPassword(String passwordToHash, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    private static String getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt.toString();
    }
}

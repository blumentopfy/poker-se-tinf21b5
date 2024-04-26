package com.palcas.poker.persistance;

import java.io.IOException;
import java.io.File;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonAccountRepository implements AccountRepository {
    private final ObjectMapper objectMapper;
    private final String filePath;

    public JacksonAccountRepository(String filePath) {
        this.objectMapper = new ObjectMapper();
        this.filePath = filePath;
    }

    @Override
    public Account loadAccount(String name) throws IOException {
        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) {
            System.out.println("account file doesn't exist or is empty");
            throw new IOException("account file doesn't exist or is empty");
        }

        Account[] accounts = objectMapper.readValue(file, Account[].class);
        for (Account account : accounts) {
            if (account.getName().equals(name)) {
                return account;
            }
        }
        return null;
    }

    @Override
    public void saveAccount(Account account) throws IOException {
        Account[] accounts;
        File file = new File(filePath);

        if (file.exists() && file.length() != 0) {
            accounts = objectMapper.readValue(file, Account[].class);
            // Update existing or add new account
            boolean found = false;
            for (int i = 0; i < accounts.length; i++) {
                if (accounts[i].getName().equals(account.getName())) {
                    accounts[i] = account;
                    found = true;
                    break;
                }
            }

            if (!found) {
                Account[] newAccounts = new Account[accounts.length + 1];
                System.arraycopy(accounts, 0, newAccounts, 0, accounts.length);
                newAccounts[accounts.length] = account;
                accounts = newAccounts;
            }
        } else {
            accounts = new Account[] { account };
        }

        objectMapper.writeValue(file, accounts);
    }
}

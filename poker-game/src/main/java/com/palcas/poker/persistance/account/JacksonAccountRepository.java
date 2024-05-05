package com.palcas.poker.persistance.account;

import java.io.FileWriter;
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
            createNew(file);
            return null;
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
            createNew(file);
            accounts = new Account[] { account };
        }

        objectMapper.writeValue(file, accounts);
    }

    public static String extractLowestDirectoryPath(File file) {
        file = file.getParentFile();
        if (file == null) {
            return null;
        }
        return file.getPath();
    }

    private static void createNew(File file) throws IOException {
        String directoryPath = extractLowestDirectoryPath(file);
        File directory = new File(directoryPath);
        if (!file.exists()) {
            directory.mkdirs();
        }
        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write("[]");
            writer.close();
        } catch (IOException e) {
            throw new IOException("could not create and write new file");
        }
    }
}

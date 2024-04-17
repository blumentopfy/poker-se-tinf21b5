package com.palcas.poker.persistance;

import com.palcas.poker.constants.JacksonPersistenceSettings;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class JacksonAccountRepositoryTest {
    private static AccountRepository accountRepository;

    @BeforeAll
    public static void setUp() {
        accountRepository = new JacksonAccountRepository(JacksonPersistenceSettings.testAccountFilePath);
        String testData = "[{\"name\": \"Alice\", \"passwordHash\": \"hashedPassword1\", \"chips\": 1000}, "
                + "{\"name\": \"Bob\", \"passwordHash\": \"hashedPassword2\", \"chips\": 2000}]";

        // Testdaten in die Datei schreiben
        try (FileWriter writer = new FileWriter(JacksonPersistenceSettings.testAccountFilePath)) {
            System.out.println("set up the test data");
            writer.write(testData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test void testLoadAccount() {
        try {
            Account pascal = accountRepository.loadAccount("Bob");
            assertEquals(pascal.getPasswordHash(), "hashedPassword2");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void testAddAccount() {
        Account account = new Account("Georg", "13374206969lelele");
        Account loadedAccount;
        try {
            accountRepository.saveAccount(account);
            loadedAccount = accountRepository.loadAccount("Georg");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(account.getName(), loadedAccount.getName());
        assertEquals(account.getPasswordHash(), loadedAccount.getPasswordHash());
    }

    @Test
    public void testAddAccountUpdate() {
        Account account = new Account("Max", "EierMitSalat");
        Account loadedAccount;
        try {
            accountRepository.saveAccount(account);
            loadedAccount = accountRepository.loadAccount("Max");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals("Max", loadedAccount.getName());
        assertEquals("EierMitSalat", loadedAccount.getPasswordHash());

        try {
            account.setPasswordHash("EierOhneSalat");
            accountRepository.saveAccount(account);
            loadedAccount = accountRepository.loadAccount("Max");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertEquals("Max", loadedAccount.getName());
        assertEquals("EierOhneSalat", loadedAccount.getPasswordHash());
    }
}

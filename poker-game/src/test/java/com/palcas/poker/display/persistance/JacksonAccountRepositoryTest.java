package com.palcas.poker.display.persistance;

import com.palcas.poker.constants.JacksonPersistenceSettings;
import com.palcas.poker.persistance.Account;
import com.palcas.poker.persistance.AccountRepository;
import com.palcas.poker.persistance.JacksonAccountRepository;
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
        String testData = "["
                + "{\"name\": \"Alice\", \"passwordHash\": \"e55a9d387402d3e84953f3b584a62eb5c8d9f796f1e1313cb6c6dc395f260d37\", \"passwordSalt\": \"1337\", \"chips\": 1000}, "
                + "{\"name\": \"Bob\", \"passwordHash\": \"013bd4cdf01910a5a02bb51ad7b50de82553a1d31827ed5c1f6760bca326dcc2\", \"passwordSalt\": \"69\", \"chips\": 2000}"
                + "]";


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
            assertEquals(pascal.getPasswordHash(), "013bd4cdf01910a5a02bb51ad7b50de82553a1d31827ed5c1f6760bca326dcc2");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void testAddAccount() {
        Account account = new Account("Georg", "634aa687c3365f3d3ac110f61542a4fcf839b5efba21b6893d201a63aa8ecda6", "9572");
        Account loadedAccount;
        try {
            accountRepository.saveAccount(account);
            loadedAccount = accountRepository.loadAccount("Georg");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(account.getName(), loadedAccount.getName());
        assertEquals(account.getPasswordHash(), loadedAccount.getPasswordHash());
        assertEquals(account.getPasswordSalt(), loadedAccount.getPasswordSalt());
    }

    @Test
    public void testAddAccountUpdate() {
        Account account = new Account("Max", "858620d32abff03ff587891b193e1de0f218e389ea0b16615902530fedbe0eea", "420");
        Account loadedAccount;
        try {
            accountRepository.saveAccount(account);
            loadedAccount = accountRepository.loadAccount("Max");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals("Max", loadedAccount.getName());
        assertEquals("858620d32abff03ff587891b193e1de0f218e389ea0b16615902530fedbe0eea", loadedAccount.getPasswordHash());

        try {
            account.setPasswordHash("96a6602ef951499be98990c3acf28d434056eeb865c1186b2e3c5456babc62ba");
            accountRepository.saveAccount(account);
            loadedAccount = accountRepository.loadAccount("Max");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertEquals("Max", loadedAccount.getName());
        assertEquals("96a6602ef951499be98990c3acf28d434056eeb865c1186b2e3c5456babc62ba", loadedAccount.getPasswordHash());
    }
}

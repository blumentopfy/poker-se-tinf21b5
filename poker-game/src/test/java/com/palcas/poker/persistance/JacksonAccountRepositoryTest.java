package com.palcas.poker.persistance;

import com.fasterxml.jackson.core.JsonParseException;
import com.palcas.poker.persistance.account.Account;
import com.palcas.poker.persistance.account.AccountRepository;
import com.palcas.poker.persistance.account.JacksonAccountRepository;
import com.palcas.poker.persistance.constants.JacksonPersistenceSettings;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class JacksonAccountRepositoryTest {
    private static AccountRepository accountRepository;

    @BeforeAll
    public static void setUp() {
        accountRepository = new JacksonAccountRepository(JacksonPersistenceSettings.TEST_ACCOUNT_FILE_PATH);
    }

    @BeforeEach
    public void writeTestDataInFile(){
        String testData = "["
                + "{\"name\": \"Alice\", \"passwordHash\": \"e55a9d387402d3e84953f3b584a62eb5c8d9f796f1e1313cb6c6dc395f260d37\", \"passwordSalt\": \"1337\", \"chips\": 1000}, "
                + "{\"name\": \"Bob\", \"passwordHash\": \"013bd4cdf01910a5a02bb51ad7b50de82553a1d31827ed5c1f6760bca326dcc2\", \"passwordSalt\": \"69\", \"chips\": 2000}"
                + "]";
        try (FileWriter writer = new FileWriter(JacksonPersistenceSettings.TEST_ACCOUNT_FILE_PATH)) {
            writer.write(testData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test void testLoadAccount() {
        try {
            Account bob = accountRepository.loadAccount("Bob");
            assertEquals("Bob", bob.getName());
            assertEquals("69", bob.getPasswordSalt());
            assertEquals("013bd4cdf01910a5a02bb51ad7b50de82553a1d31827ed5c1f6760bca326dcc2", bob.getPasswordHash());
            assertEquals(2000, bob.getChips());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void testAddAccount() {
        Account accountToAdd = new Account("Georg", "634aa687c3365f3d3ac110f61542a4fcf839b5efba21b6893d201a63aa8ecda6", "9572");
        Account loadedAccount;
        try {
            accountRepository.saveAccount(accountToAdd);
            loadedAccount = accountRepository.loadAccount("Georg");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(accountToAdd.getName(), loadedAccount.getName());
        assertEquals(accountToAdd.getPasswordHash(), loadedAccount.getPasswordHash());
        assertEquals(accountToAdd.getPasswordSalt(), loadedAccount.getPasswordSalt());
    }

    @Test
    public void testUpdatingTheHashOfAnExistingAccount() {
        Account alice;
        try {
            alice = accountRepository.loadAccount("Alice");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals("Alice", alice.getName());
        assertEquals("e55a9d387402d3e84953f3b584a62eb5c8d9f796f1e1313cb6c6dc395f260d37", alice.getPasswordHash());

        Account updatedAlice;
        try {
            alice.setPasswordHash("96a6602ef951499be98990c3acf28d434056eeb865c1186b2e3c5456babc62ba");
            accountRepository.saveAccount(alice);
            updatedAlice = accountRepository.loadAccount("Alice");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertEquals("Alice", updatedAlice.getName());
        assertEquals("96a6602ef951499be98990c3acf28d434056eeb865c1186b2e3c5456babc62ba", alice.getPasswordHash());
    }

    @Test
    public void throwsExceptionBecauseJSONFileIsBroken() {
        try (FileWriter writer = new FileWriter(JacksonPersistenceSettings.TEST_ACCOUNT_FILE_PATH)) {
            writer.write("hellothere{this\"isa}}broken[]assf1le[[[[[[");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertThrows(JsonParseException.class, () -> accountRepository.loadAccount("Alice"));
    }

    @Test void returnsNullBecauseAccountIsNotFond() {
        try {
            Account nonexistentAccount = accountRepository.loadAccount("Ghost");
            assertEquals(null, nonexistentAccount);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // #Requirement
    @Test void returnsNullIfNameIsNull() {
        Account account;
        try {
            account = accountRepository.loadAccount(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(null, account);
    }

    // #LifeSaver #BugFix
    @Test void returnsNullAndCreatesEmptyFileIfFileDoesntExistYet() {
        //the non-existing-account-filepath must point to a non-existing account
        AccountRepository corruptAccountRepository = new JacksonAccountRepository(JacksonPersistenceSettings.NON_EXISTING_ACCOUNT_FILE_PATH);
        Account account;
        File file = new File(JacksonPersistenceSettings.NON_EXISTING_ACCOUNT_FILE_PATH);

        try {
            account = corruptAccountRepository.loadAccount("Alice");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertEquals(null, account);
        assert(file.exists());

        // Clean up: Delete the file after assertions
        deleteCreatedFile(file);
    }

    // #LifeSaver #BugFix
    @Test void createsFileWithAccountIfFileDoesntExistYet() {
        //the non-existing-account-filepath must point to a non-existing account
        AccountRepository corruptAccountRepository = new JacksonAccountRepository(JacksonPersistenceSettings.NON_EXISTING_ACCOUNT_FILE_PATH);
        File file = new File(JacksonPersistenceSettings.NON_EXISTING_ACCOUNT_FILE_PATH);
        Account accountToAdd = new Account("Georg", "634aa687c3365f3d3ac110f61542a4fcf839b5efba21b6893d201a63aa8ecda6", "9572");
        Account receivedAccount;

        try {
            corruptAccountRepository.saveAccount(accountToAdd);
            receivedAccount = corruptAccountRepository.loadAccount("Georg");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertEquals(accountToAdd, receivedAccount);
        assert(file.exists());

        // Clean up: Delete the file after assertions
        deleteCreatedFile(file);
    }

    public static void deleteCreatedFile(File file) {
        File direcotry = new File(extractLowestDirectoryPath(file));
        if (file.exists()) {
            file.delete();
        }
        if (direcotry.exists()) {
            direcotry.delete();
        }
    }

    public static String extractLowestDirectoryPath(File file) {
        file = file.getParentFile();
        if (file == null) {
            return null;
        }
        return file.getPath();
    }
}

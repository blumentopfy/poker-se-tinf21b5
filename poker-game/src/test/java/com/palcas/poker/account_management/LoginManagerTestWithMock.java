package com.palcas.poker.account_management;

import com.palcas.poker.account_management.mocks.MockAccountRepository;
import com.palcas.poker.game.Player;
import com.palcas.poker.persistance.account.Account;
import com.palcas.poker.persistance.login.AccountAlreadyExistsException;
import com.palcas.poker.persistance.login.LoginManager;
import com.palcas.poker.persistance.login.PasswordRequirementsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class LoginManagerTestWithMock  {
    static LoginManager loginManager;


    @BeforeEach
    void setUpTestData() {
        List<Account> accounts = new ArrayList<>();

        Account alice = new Account("Alice", "e55a9d387402d3e84953f3b584a62eb5c8d9f796f1e1313cb6c6dc395f260d37", "1337", 1000);
        Account bob = new Account("Bob", "013bd4cdf01910a5a02bb51ad7b50de82553a1d31827ed5c1f6760bca326dcc2", "69", 2000);
        Account max = new Account("Max", "858620d32abff03ff587891b193e1de0f218e389ea0b16615902530fedbe0eea", "420", 2000);
        Account georg = new Account("Georg", "634aa687c3365f3d3ac110f61542a4fcf839b5efba21b6893d201a63aa8ecda6", "9572", 2000);

        accounts.add(alice);
        accounts.add(bob);
        accounts.add(max);
        accounts.add(georg);

        loginManager = new LoginManager(new MockAccountRepository(accounts));
    }

    @Test
    void testLoginSuccessfulBecauseCredentialsMatch() {
        Player player = loginManager.login("Max", "EierMitSalat").get();
        assertNotNull(player);
        assertEquals("Max", player.getName());
    }

    @Test
    void testLoginNotSuccessfulBecauseWrongPassword() {
        Optional<Player> player = loginManager.login("Max", "EierOhneSalat");
        assertTrue(player.isEmpty());
    }

    @Test
    void registeringFailsWhenUserAlreadyExists() throws AccountAlreadyExistsException {
        assertThrows(AccountAlreadyExistsException.class, () -> {
            Player player = loginManager.register("Max", "ValidPassw0rd").get();
        });
    }

    @Test
    void registeringFailsWhenNameStartsWithGuest() throws AccountAlreadyExistsException {
        assertThrows(AccountAlreadyExistsException.class, () -> {
            Player player = loginManager.register("Guest-name", "ValidPassw0rd").get();
        });
    }

    @Test
    void testRegisterSuccessful() throws AccountAlreadyExistsException, PasswordRequirementsException, IOException {
        Player player = loginManager.register("Kevin", "iLoveD0nuts").get();
        Player playerLoggedInAgain = loginManager.login("Kevin", "iLoveD0nuts").get();

        assertNotNull(player);
        assertNotNull(playerLoggedInAgain);
        assertEquals(player.getName(), playerLoggedInAgain.getName());
    }

    @Test
    void registeringFailsBecausePasswordRequirementsAreNotMatched()
            throws AccountAlreadyExistsException, PasswordRequirementsException, IOException {
        assertThrows(PasswordRequirementsException.class, () -> {
            loginManager.register("Kevin", "nouppercase0").get();
        });
        assertThrows(PasswordRequirementsException.class, () -> {
            loginManager.register("Kevin", "NOLOWERCASE0").get();
        });
        assertThrows(PasswordRequirementsException.class, () -> {
            loginManager.register("Kevin", "NoDigits").get();
        });
        assertThrows(PasswordRequirementsException.class, () -> {
            loginManager.register("Kevin", "Shor7").get();
        });
        assertThrows(PasswordRequirementsException.class, () -> {
            loginManager.register("Kevin", "WayyyyyyTooooooLoooooooooooooooong0").get();
        });
        assertThrows(PasswordRequirementsException.class, () -> {
            // empty password
            loginManager.register("Kevin", "").get();
        });
        assertThrows(PasswordRequirementsException.class, () -> {
            // password is null
            loginManager.register("Kevin", null).get();
        });
        Player player = loginManager.register("Kevin", "ValidPassw0rd").get();
        assertNotNull(player);
    }

    @Test
    void testLoginAsGuest() {
        Player guest = loginManager.loginAsGuest();

        assertTrue(guest.getName().startsWith("Guest-"));
    }

}

package com.palcas.poker.account_management;

import com.palcas.poker.persistance.constants.JacksonPersistenceSettings;
import com.palcas.poker.game.Player;
import com.palcas.poker.persistance.JacksonAccountRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class LoginManagerTest {

    static LoginManager loginManager;

    @BeforeAll
    public static void setUp() {
        loginManager = new LoginManager(
                new JacksonAccountRepository(JacksonPersistenceSettings.TEST_ACCOUNT_FILE_PATH));
    }

    @BeforeEach
    void setUpTestData() {
        String accountTestData = "["
                + "{\"name\": \"Alice\", \"passwordHash\": \"e55a9d387402d3e84953f3b584a62eb5c8d9f796f1e1313cb6c6dc395f260d37\", \"passwordSalt\": \"1337\", \"chips\": 1000}, "
                + "{\"name\": \"Bob\", \"passwordHash\": \"013bd4cdf01910a5a02bb51ad7b50de82553a1d31827ed5c1f6760bca326dcc2\", \"passwordSalt\": \"69\", \"chips\": 2000}, "
                + "{\"name\": \"Max\", \"passwordHash\": \"858620d32abff03ff587891b193e1de0f218e389ea0b16615902530fedbe0eea\", \"passwordSalt\": \"420\", \"chips\": 2000}, " // pwd:
                                                                                                                                                                              // EierMitSalat
                + "{\"name\": \"Georg\", \"passwordHash\": \"634aa687c3365f3d3ac110f61542a4fcf839b5efba21b6893d201a63aa8ecda6\", \"passwordSalt\": \"9572\", \"chips\": 2000}"
                + "]";

        try (FileWriter writer = new FileWriter(JacksonPersistenceSettings.TEST_ACCOUNT_FILE_PATH)) {
            writer.write(accountTestData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

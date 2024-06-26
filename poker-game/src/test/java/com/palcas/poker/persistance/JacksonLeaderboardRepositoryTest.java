package com.palcas.poker.persistance;

import com.fasterxml.jackson.core.JsonParseException;
import com.palcas.poker.persistance.constants.JacksonPersistenceSettings;
import com.palcas.poker.persistance.leaderboard.JacksonLeaderboardRepository;
import com.palcas.poker.persistance.leaderboard.LeaderboardEntry;
import com.palcas.poker.persistance.leaderboard.LeaderboardRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JacksonLeaderboardRepositoryTest {
    private static LeaderboardRepository leaderboardRepository;

    @BeforeAll
    public static void setUp() {
        leaderboardRepository = new JacksonLeaderboardRepository(JacksonPersistenceSettings.TEST_LEADERBOARD_FILE_PATH);
    }

    @BeforeEach void setUpTestData() {
        String leaderboardTestData = "["
                + "{\"name\": \"Alice\", \"chips\": 1500}, "
                + "{\"name\": \"Bob\", \"chips\": 2200}, "
                + "{\"name\": \"Charlie\", \"chips\": 1800}, "
                + "{\"name\": \"David\", \"chips\": 2100}, "
                + "{\"name\": \"Eve\", \"chips\": 1900}"
                + "]";
        try (FileWriter writer = new FileWriter(JacksonPersistenceSettings.TEST_LEADERBOARD_FILE_PATH)) {
            writer.write(leaderboardTestData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test void testGetTopTen() {
        try {
            List<LeaderboardEntry> leaderboard = leaderboardRepository.getTopTen();

            assertEquals(leaderboard.size(), 5);
            assertEquals(leaderboard.get(0).getName(), "Bob");
            assertEquals(leaderboard.get(1).getName(), "David");
            assertEquals(leaderboard.get(2).getName(), "Eve");
            assertEquals(leaderboard.get(3).getName(), "Charlie");
            assertEquals(leaderboard.get(4).getName(), "Alice");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test void testAddToLeaderboard() {
        try {
            List<LeaderboardEntry> leaderboard = leaderboardRepository.getTopTen();

            assertEquals(leaderboard.size(), 5);

            leaderboardRepository.addToLeaderboard("Pascal", 1984);
            leaderboard = leaderboardRepository.getTopTen();

            assertEquals(leaderboard.size(), 6);
            assertEquals(leaderboard.get(0).getName(), "Bob");
            assertEquals(leaderboard.get(1).getName(), "David");
            assertEquals(leaderboard.get(2).getName(), "Pascal");
            assertEquals(leaderboard.get(3).getName(), "Eve");
            assertEquals(leaderboard.get(4).getName(), "Charlie");
            assertEquals(leaderboard.get(5).getName(), "Alice");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test void testNotUpdateLeaderboard() {
        try {
            List<LeaderboardEntry> leaderboard = leaderboardRepository.getTopTen();

            assertEquals(5, leaderboard.size());

            leaderboardRepository.addToLeaderboard("Eve", 1337);
            leaderboard = leaderboardRepository.getTopTen();

            assertEquals(leaderboard.size(), 5);
            assertEquals(leaderboard.get(0).getName(), "Bob");
            assertEquals(leaderboard.get(1).getName(), "David");
            assertEquals(leaderboard.get(2).getName(), "Eve");
            assertEquals(leaderboard.get(2).getChips(), 1900);
            assertEquals(leaderboard.get(3).getName(), "Charlie");
            assertEquals(leaderboard.get(4).getName(), "Alice");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test void testUpdateLeaderboard() {
        try {
            List<LeaderboardEntry> leaderboard = leaderboardRepository.getTopTen();

            assertEquals(leaderboard.size(), 5);

            leaderboardRepository.addToLeaderboard("Alice", 2148);
            leaderboard = leaderboardRepository.getTopTen();

            assertEquals(leaderboard.size(), 5);
            assertEquals(leaderboard.get(0).getName(), "Bob");
            assertEquals(leaderboard.get(1).getName(), "Alice");
            assertEquals(leaderboard.get(1).getChips(), 2148);
            assertEquals(leaderboard.get(2).getName(), "David");
            assertEquals(leaderboard.get(3).getName(), "Eve");
            assertEquals(leaderboard.get(4).getName(), "Charlie");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void throwsExceptionBecauseJSONFileIsBroken() {
        try (FileWriter writer = new FileWriter(JacksonPersistenceSettings.TEST_LEADERBOARD_FILE_PATH)) {
            writer.write("hellothere{this\"isa}}broken[]assf1le[[[[[[");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertThrows(JsonParseException.class, () -> leaderboardRepository.getTopTen());
    }

    // #BugFix
    @Test void createsFileAndReturnsEmptyListIfLeaderboardFileDoesntExist() {
        LeaderboardRepository corruptLeaderboardRepository = new JacksonLeaderboardRepository(JacksonPersistenceSettings.NON_EXISTING_LEADERBOARD_FILE_PATH);
        File file = new File(JacksonPersistenceSettings.NON_EXISTING_LEADERBOARD_FILE_PATH);

        List<LeaderboardEntry> topTen;
        try {
            topTen = corruptLeaderboardRepository.getTopTen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertEquals(topTen.size(), 0);
        assert(file.exists());

        // Clean up
        deleteCreatedFile(file);
    }

    // #BugFix
    @Test void createsAndWritesIntoNewFileIfLeaderboardFileDoesntExist() {
        LeaderboardRepository corruptLeaderboardRepository = new JacksonLeaderboardRepository(JacksonPersistenceSettings.NON_EXISTING_LEADERBOARD_FILE_PATH);
        File file = new File(JacksonPersistenceSettings.NON_EXISTING_LEADERBOARD_FILE_PATH);
        List<LeaderboardEntry> leaderboard;

        try {
            corruptLeaderboardRepository.addToLeaderboard("Pascal", 1984);
            leaderboard = corruptLeaderboardRepository.getTopTen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertEquals(1, leaderboard.size());
        assert(file.exists());

        // Clean up
        deleteCreatedFile(file);
    }


    public static void deleteCreatedFile(File file) {
        File directory = new File(extractLowestDirectoryPath(file));
        if (file.exists()) {
            file.delete();
        }
        if (directory.exists()) {
            directory.delete();
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

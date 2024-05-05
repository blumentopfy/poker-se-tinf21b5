package com.palcas.poker.persistance.leaderboard;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class JacksonLeaderboardRepository implements LeaderboardRepository {

    private final ObjectMapper objectMapper;
    private final String filePath;

    public JacksonLeaderboardRepository(String filePath) {
        this.filePath = filePath;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public ArrayList<LeaderboardEntry> getTopTen() throws IOException {
        List<LeaderboardEntry> entries = loadEntriesFromFile();
        entries.sort(Comparator.comparingInt(LeaderboardEntry::getChips).reversed());
        int size = Math.min(10, entries.size());
        return new ArrayList<>(entries.subList(0, size));
    }

    @Override
    public void addToLeaderboard(String name, int chips) throws IOException {
        try {
            List<LeaderboardEntry> entries = loadEntriesFromFile();
            boolean found = false;
            for (LeaderboardEntry entry : entries) {
                if (entry.getName().equals(name)) {
                    if (entry.getChips() < chips) {
                        entry.setChips(chips);
                        found = true;
                        break;
                    } else {
                        return;
                    }
                }
            }
            if (!found) {
                entries.add(new LeaderboardEntry(name, chips));
            }
            saveEntriesToFile(entries);
        } catch (IOException e) {
            throw new IOException();
        }
    }

    private List<LeaderboardEntry> loadEntriesFromFile() throws IOException {
        File file = new File(filePath);
        if (file.exists() && file.length() != 0) {
            return objectMapper.readValue(file, new TypeReference<List<LeaderboardEntry>>() {
            });
        } else {
            createNew(file, "[]");
            return new ArrayList<>();
        }
    }

    private void saveEntriesToFile(List<LeaderboardEntry> entries) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            createNew(file, "[]");
        }
        objectMapper.writeValue(file, entries);
    }

    public static String extractLowestDirectoryPath(File file) {
        file = file.getParentFile();
        if (file == null) {
            return null;
        }
        return file.getPath();
    }

    private static void createNew(File file, String defaultString) throws IOException {
        String directoryPath = extractLowestDirectoryPath(file);
        File directory = new File(directoryPath);
        if (!file.exists()) {
            directory.mkdirs();
        }
        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(defaultString);
            writer.close();
        } catch (IOException e) {
            throw new IOException("could not create and write new file");
        }
    }
}

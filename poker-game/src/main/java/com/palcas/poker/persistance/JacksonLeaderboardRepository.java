package com.palcas.poker.persistance;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class JacksonLeaderboardRepository implements LeaderboardRepository{

    private final ObjectMapper objectMapper;
    private final String filePath;

    public JacksonLeaderboardRepository(String filePath) {
        this.filePath = filePath;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public ArrayList<LeaderboardEntry> getTopTen()  throws IOException {
        List<LeaderboardEntry> entries = loadEntriesFromFile();
        entries.sort(Comparator.comparingInt(LeaderboardEntry::getChips).reversed());
        int size = Math.min(10, entries.size());
        return new ArrayList<>(entries.subList(0, size));
    }

    @Override
    public void addToLeaderboard(String name, int chips)  throws IOException{
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
            return objectMapper.readValue(file, new TypeReference<List<LeaderboardEntry>>() {});
        }
        return new ArrayList<>();
    }

    private void saveEntriesToFile(List<LeaderboardEntry> entries) throws IOException {
        File file = new File(filePath);
        objectMapper.writeValue(file, entries);
    }
}

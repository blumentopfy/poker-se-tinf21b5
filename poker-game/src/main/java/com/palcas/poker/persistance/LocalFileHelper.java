package com.palcas.poker.persistance;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LocalFileHelper {
    private static String extractLowestDirectoryPath(File file) {
        file = file.getParentFile();
        if (file == null) {
            return null;
        }
        return file.getPath();
    }

    public static void createNew(File file, String defaultString) throws IOException {
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

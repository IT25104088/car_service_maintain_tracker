package com.cartracker.repository;

import java.io.*;
import java.util.*;

/**
 * Abstract class for file-based data persistence.
 * Encapsulation: File I/O logic is encapsulated.
 */
public abstract class FileBasedRepository<T> {
    protected File file;

    public FileBasedRepository(String filePath) {
        this.file = new File(filePath);
        ensureFileExists();
    }

    private void ensureFileExists() {
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write(getHeader());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create file: " + file.getPath(), e);
        }
    }

    protected abstract String getHeader();
    protected abstract T parseRecord(String line);
    protected abstract String recordToString(T record);

    public List<T> findAll() {
        List<T> records = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header
                }
                if (!line.trim().isEmpty()) {
                    T record = parseRecord(line);
                    if (record != null) {
                        records.add(record);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read records", e);
        }
        return records;
    }

    public synchronized void save(T record) {
        List<T> records = findAll();
        records.add(record);
        saveAll(records);
    }

    public synchronized void saveAll(List<T> records) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(getHeader());
            writer.newLine();
            for (T record : records) {
                writer.write(recordToString(record));
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save records", e);
        }
    }

    public synchronized void delete(T record) {
        List<T> records = findAll();
        records.remove(record);
        saveAll(records);
    }
}

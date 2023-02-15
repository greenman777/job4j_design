package ru.job4j.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Config {

    private final String path;
    private final Map<String, String> values = new HashMap<>();

    public Config(final String path) {
        this.path = path;
    }

    private boolean checkLine(String line) {
        boolean result = true;
        if ("".equals(line) || line.charAt(0) == '#') {
            result = false;
        } else if (!line.matches("[^=].+=.+")) {
            throw new IllegalArgumentException();
        }
        return result;
    }

    public void load() {
        try (BufferedReader read = new BufferedReader(new FileReader(this.path))) {
            String line;
            while ((line = read.readLine()) != null) {
                if (checkLine(line)) {
                    String[] paramsStr = line.split("=", 2);
                    String key = paramsStr[0];
                    String value = paramsStr[1];
                    values.put(key, value);
                }
            }
            read.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String value(String key) {
        return values.get(key);
    }

    @Override
    public String toString() {
        StringJoiner out = new StringJoiner(System.lineSeparator());
        try (BufferedReader read = new BufferedReader(new FileReader(this.path))) {
            read.lines().forEach(out::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }

    public static void main(String[] args) {
        System.out.println(new Config("data/app.properties"));
    }
}
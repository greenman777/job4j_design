package ru.job4j.io;

import java.io.*;
import java.util.*;

public class ConsoleChat {
    private static final String OUT = "закончить";
    private static final String STOP = "стоп";
    private static final String CONTINUE = "продолжить";
    private final String path;
    private final String botAnswers;

    public ConsoleChat(String path, String botAnswers) {
        this.path = path;
        this.botAnswers = botAnswers;
    }

    public void run() {
        List<String> phrases = readPhrases();
        List<String> log = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String answer;
        boolean continueAnswer = true;
        String question = scanner.nextLine();
        while (!(OUT.equals(question))) {
            log.add(question + System.lineSeparator());
            if (STOP.equals(question)) {
                continueAnswer = false;
            }
            if (CONTINUE.equals(question)) {
                continueAnswer = true;
            }
            if (continueAnswer) {
                answer = phrases.get(new Random().nextInt(phrases.size()));
                System.out.print(answer);
                log.add(answer);
            }
            question = scanner.nextLine();
        }
        log.add(question + System.lineSeparator());
        saveLog(log);
    }

    private List<String> readPhrases() {
        List<String> strings = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.lines().map(s -> s + System.lineSeparator()).forEach(strings::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strings;
    }

    private void saveLog(List<String> log) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(botAnswers, true))) {
            log.forEach(pw::print);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ConsoleChat cc = new ConsoleChat("./data/answers.txt", "./data/answer_log.txt");
        cc.run();
    }
}
package ru.job4j.io;

import java.io.*;

public class Analysis {
    public void unavailable(String source, String target) {
        try (BufferedReader read = new BufferedReader(new FileReader(source));
             PrintWriter out = new PrintWriter(target)) {
            boolean workTime = true;
            while (read.ready()) {
                String[] line = read.readLine().split(" ");
                if (workTime == Integer.parseInt(line[0]) >= 400) {
                    out.append(line[1]).append(';').append(workTime ? "" : System.lineSeparator());
                    workTime = !workTime;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Analysis analysis = new Analysis();
        analysis.unavailable("data/server.log", "data/target.csv");
    }

}

package ru.job4j.io;

import java.io.*;

public class Analysis {
    public void unavailable(String source, String target) {
        try (BufferedReader read = new BufferedReader(new FileReader(source));
             PrintWriter out = new PrintWriter(new FileOutputStream(target))) {
            String line, start = null, stop;
            while ((line = read.readLine()) != null) {
                String[] split = line.split("\s");
                if ("400".equals(split[0]) || "500".equals(split[0]) && start == null) {
                    start = split[1];
                } else if (start != null) {
                    stop = split[1];
                    out.printf("%s;%s;\n", start, stop);
                    start = null;
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

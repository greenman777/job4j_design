package ru.job4j.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Analysis {
    public void unavailable(String source, String target) {
        try (BufferedReader read = new BufferedReader(new FileReader(source));
             PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(target)))) {
            List<String> downtime = new ArrayList<>();
            boolean newPeriod = true, foundPeriod = false;
            String line, status, time, start = "", stop;
            while ((line = read.readLine()) != null) {
                status = line.split("\s")[0];
                time = line.split("\s")[1];
                if ("400".equals(status) || "500".equals(status)) {
                    if (newPeriod) {
                        start = time;
                        newPeriod = false;
                        foundPeriod = true;
                    }
                } else if (foundPeriod) {
                    stop = time;
                    downtime.add(start + ";" + stop + ";");
                    newPeriod = true;
                    foundPeriod = false;
                }
            }
            downtime.forEach(out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Analysis analysis = new Analysis();
        analysis.unavailable("data/server.log", "data/target.csv");
    }

}

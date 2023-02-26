package ru.job4j.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static ru.job4j.io.Search.search;

public class Zip {

    private static String[] validate(String[] args) {
        ArgsName argsParam = ArgsName.of(args);
        String[] params = new String[3];
        params[0] = argsParam.get("d");
        if (!Files.isDirectory(Path.of(params[0]))) {
            throw new IllegalArgumentException();
        }
        params[1] = argsParam.get("e");
        params[2] = argsParam.get("o");
        return params;
    }

    public void packFiles(List<Path> sources, Path target) {
        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target.toFile())))) {
            for (Path source : sources) {
                zip.putNextEntry(new ZipEntry(source.toString()));
                try (BufferedInputStream out = new BufferedInputStream(new FileInputStream(source.toFile()))) {
                    zip.write(out.readAllBytes());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        try {
            String[] params = validate(args);
            Zip zip = new Zip();
            zip.packFiles(search(Path.of(params[0]), p -> !p.toFile().getName().endsWith(params[1])), Path.of(params[2]));
        } catch (IllegalArgumentException | IOException exception) {
            exception.printStackTrace();
        }
    }
}

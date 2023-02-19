package ru.job4j.io.duplicates;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.nio.file.FileVisitResult.CONTINUE;

public class DuplicatesVisitor extends SimpleFileVisitor<Path> {
    Map<FileProperty, Path> fileDoubleMap = new HashMap<>();
    Set<Path> fileDoubleSet = new HashSet<>();
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Path path = fileDoubleMap.put(new FileProperty(Files.size(file), file.getFileName().toString()), file);
        if (path != null) {
            if (fileDoubleSet.add(path)) {
                System.out.println(path.toAbsolutePath());
            }
            if (fileDoubleSet.add(file)) {
                System.out.println(file.toAbsolutePath());
            }
        }
        return CONTINUE;
    }
}
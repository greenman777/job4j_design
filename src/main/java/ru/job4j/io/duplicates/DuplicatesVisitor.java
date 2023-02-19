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
    private final Map<FileProperty, Path> fileDuplicateMap = new HashMap<>();
    private final Set<Path> fileDuplicateSet = new HashSet<>();
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Path path = fileDuplicateMap.put(new FileProperty(Files.size(file), file.getFileName().toString()), file);
        if (path != null) {
            fileDuplicateSet.add(path);
            fileDuplicateSet.add(file);
        }
        return CONTINUE;
    }

    public void printDuplicate() {
        fileDuplicateSet.forEach(path -> System.out.println(path.toAbsolutePath()));
    }
}
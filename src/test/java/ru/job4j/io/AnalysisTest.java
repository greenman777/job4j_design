package ru.job4j.io;

import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AnalysisTest {

    @ParameterizedTest
    @MethodSource("getArgumentsValidate")
    void whenVariousEvents(List<String> sourceData, List<String> targetData, @TempDir Path tempDir) throws IOException {
        File source = tempDir.resolve("server.log").toFile();
        try (PrintWriter out = new PrintWriter(source)) {
            sourceData.forEach(out::println);
        }
        File target = tempDir.resolve("target.csv").toFile();
        Analysis analysis = new Analysis();
        analysis.unavailable(source.getAbsolutePath(), target.getAbsolutePath());

        StringBuilder rsl = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(target))) {
            in.lines().forEach(rsl::append);
        }
        assertThat(String.join("", targetData)).isEqualTo(rsl.toString());
    }

    static Stream<Arguments> getArgumentsValidate() {
        return Stream.of(
                Arguments.of(List.of("200 10:56:01", "400 10:58:01", "300 10:59:01", "500 11:02:01", "200 11:13:01"),
                        List.of("10:58:01;10:59:01;", "11:02:01;11:13:01;")),
                Arguments.of(List.of("200 10:56:01", "400 10:58:01", "500 10:59:01", "500 11:02:01", "200 11:13:01"),
                        List.of("10:58:01;11:13:01;")),
                Arguments.of(List.of("400 10:56:01", "400 10:58:01", "300 10:59:01", "500 11:02:01", "400 11:13:01", "200 11:14:01"),
                        List.of("10:56:01;10:59:01;", "11:02:01;11:14:01;")),
                Arguments.of(List.of("200 10:56:01", "300 10:58:01", "300 10:59:01", "500 11:02:01", "400 11:13:01", "500 11:14:01"),
                        List.of("11:02:01;"))
        );
    }
}
package ru.job4j.assertj;

import org.assertj.core.data.Index;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleConvertTest {
    @Test
    void checkArray() {
        SimpleConvert simpleConvert = new SimpleConvert();
        String[] array = simpleConvert.toArray("first", "second", "three", "four", "five");
        assertThat(array).hasSize(5)
                .contains("second")
                .contains("first", Index.atIndex(0))
                .containsAnyOf("zero", "second", "six")
                .doesNotContain("first", Index.atIndex(1));
    }

    @Test
    void checkList() {
        SimpleConvert simpleConvert = new SimpleConvert();
        List<String> list = simpleConvert.toList("first", "second", "three", "four", "five", "six");
        assertThat(list).hasSize(6)
                .contains("six")
                .contains("five", Index.atIndex(4))
                .containsAnyOf("zero", "first", "seven")
                .doesNotContain("first", Index.atIndex(1))
                .endsWith("five", "six")
                .containsSequence("second", "three")
                .anyMatch(e -> e.startsWith("f"));
    }

    @Test
    void checkSet() {
        SimpleConvert simpleConvert = new SimpleConvert();
        Set<String> list = simpleConvert.toSet("first", "second", "three", "four", "five", "six");
        assertThat(list).hasSize(6)
                .contains("six")
                .containsExactlyInAnyOrder("six", "first", "second", "three", "four", "five")
                .filteredOn(e -> e.endsWith("e"))
                .containsAnyOf("three", "five");
    }

    @Test
    void checkMap() {
        SimpleConvert simpleConvert = new SimpleConvert();
        Map<String, Integer> map = simpleConvert.toMap("zero", "first", "second", "three", "four", "five");
        assertThat(map).hasSize(6)
                .containsKey("four")
                .containsValues(2, 4, 1)
                .doesNotContainKey("six")
                .doesNotContainValue(7)
                .containsEntry("three", 3);
    }
}
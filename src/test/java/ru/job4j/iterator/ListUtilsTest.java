package ru.job4j.iterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

class ListUtilsTest {

    private List<Integer> input;

    @BeforeEach
    void setUp() {
        input = new ArrayList<>(Arrays.asList(1, 3, 9, 4, 7, 8));
    }

    @Test
    void whenAddBefore() {
        ListUtils.addBefore(input, 1, 2);
        assertThat(input).hasSize(7).containsSequence(1, 2, 3, 9, 4, 7, 8);
    }

    @Test
    void whenAddBeforeWithInvalidIndex() {
        assertThatThrownBy(() -> ListUtils.addBefore(input, 7, 2))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void whenAddAfter() {
        ListUtils.addAfter(input, 2, 2);
        assertThat(input).hasSize(7).containsSequence(1, 3, 9, 2, 4, 7, 8);
    }

    @Test
    void whenAddAfterWithInvalidIndex() {
        assertThatThrownBy(() -> ListUtils.addBefore(input, 8, 1))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void whenRemoveIf() {
        ListUtils.removeIf(input, e -> e % 2 == 0);
        assertThat(input).hasSize(4).containsSequence(1, 3, 9, 7);
    }

    @Test
    void whenReplaceIf() {
        ListUtils.replaceIf(input, e -> e % 2 != 0, 0);
        assertThat(input).hasSize(6).containsSequence(0, 0, 0, 4, 0, 8);
    }

    @Test
    void whenRemoveAll() {
        List<Integer> checkList = new ArrayList<>(Arrays.asList(3, 8));
        ListUtils.removeAll(input, checkList);
        assertThat(input).hasSize(4).containsSequence(1, 9, 4, 7);
    }

}
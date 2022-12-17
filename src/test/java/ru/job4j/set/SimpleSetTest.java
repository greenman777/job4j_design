package ru.job4j.set;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleSetTest {

    @Test
    void whenAddNonNull() {
        Set<Integer> set = new SimpleSet<>();
        assertThat(set.add(1)).isTrue();
        assertThat(set.contains(1)).isTrue();
        assertThat(set.add(1)).isFalse();
    }

    @Test
    void whenAddNull() {
        Set<Integer> set = new SimpleSet<>();
        assertThat(set.add(null)).isTrue();
        assertThat(set.contains(null)).isTrue();
        assertThat(set.add(null)).isFalse();
    }

    @Test
    void whenAddSomeElements() {
        Set<Integer> set = new SimpleSet<>();
        assertThat(set.add(3)).isTrue();
        assertThat(set.add(5)).isTrue();
        assertThat(set.add(8)).isTrue();
        assertThat(set.add(5)).isFalse();
        assertThat(set.add(null)).isTrue();
        assertThat(set.add(4)).isTrue();
        assertThat(set.add(5)).isFalse();
        assertThat(set.add(null)).isFalse();
        assertThat(set.add(9)).isTrue();
        assertThat(set).hasSize(6).contains(3, 5, 8, null, 4, 9);
    }

    @Test
    void whenContains() {
        Set<Integer> set = new SimpleSet<>();
        assertThat(set.add(3)).isTrue();
        assertThat(set.add(5)).isTrue();
        assertThat(set.add(8)).isTrue();
        assertThat(set.add(null)).isTrue();
        assertThat(set.contains(2)).isFalse();
        assertThat(set.contains(8)).isTrue();
        assertThat(set.contains(null)).isTrue();
    }
}
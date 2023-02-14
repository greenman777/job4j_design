package ru.job4j.io;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class ConfigTest {

    @Test
    void whenPairWithoutComment() {
        String path = "./data/pair_without_comment.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("name")).isEqualTo("Khodyrev Dmitriy");
    }

    @Test
    void whenPairAndComment() {
        String path = "./data/pair_and_comment.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("data")).isNull();
    }

    @Test
    void whenMultiPairAndCommentAndEmptyLine() {
        String path = "./data/multi_pair_and_comment_and_empty_line.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("hibernate.dialect")).isEqualTo("org.hibernate.dialect.PostgreSQLDialect");
        assertThat(config.value("hibernate.connection.username")).isEqualTo("postgres");
    }

    @Test
    void whenPatternViolationWithoutKey() {
        String path = "./data/pattern_violation_1.properties";
        Config config = new Config(path);
        assertThatThrownBy(config::load).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenPatternViolationWithoutValue() {
        String path = "./data/pattern_violation_2.properties";
        Config config = new Config(path);
        assertThatThrownBy(config::load).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenPatternViolationWithoutSep() {
        String path = "./data/pattern_violation_3.properties";
        Config config = new Config(path);
        assertThatThrownBy(config::load).isInstanceOf(IllegalArgumentException.class);
    }
}
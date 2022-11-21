package ru.job4j.assertj;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NameLoadTest {
    @Test
    void checkEmpty() {
        NameLoad nameLoad = new NameLoad();
        assertThatThrownBy(nameLoad::getMap)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("no data");
    }

    @ParameterizedTest
    @MethodSource("getArgumentsValidate")
    void checkValidate(String name, String message) {
        NameLoad nameLoad = new NameLoad();
        assertThatThrownBy(() -> nameLoad.parse(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);
    }

    @Test
    void checkValidateEmptyNames() {
        NameLoad nameLoad = new NameLoad();
        assertThatThrownBy(nameLoad::parse)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Names array is empty");
    }

    static Stream<Arguments> getArgumentsValidate() {
        String name = "NameTest";
        return Stream.of(
                Arguments.of(name, String.format("this name: %s does not contain the symbol \"=\"", name)),
                Arguments.of("=" + name, String.format("this name: %s does not contain a key", "=" + name)),
                Arguments.of(name + "=", String.format("this name: %s does not contain a value", name + "="))
        );
    }
}
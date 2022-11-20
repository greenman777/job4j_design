package ru.job4j.assertj;

import org.assertj.core.data.Percentage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withPrecision;

class BoxTest {
    @ParameterizedTest
    @MethodSource("getArgumentsForShape")
    void isThisShape(int vertex, int edge, String shape) {
        Box box = new Box(vertex, edge);
        String name = box.whatsThis();
        assertThat(name).isNotNull().isNotEmpty().isEqualTo(shape);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForVertex")
    void checkingVertexFromShapeAndEdge(int vertex, int edge, int vertexResult) {
        Box box = new Box(vertex, edge);
        int vertexNew = box.getNumberOfVertices();
        assertThat(vertexNew).isNotNull().isEqualTo(vertexResult);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForCheckIsExist")
    void whenShapeCheckIsExist(int vertex, int edge, boolean exist) {
        Box box = new Box(vertex, edge);
        boolean resultExist = box.isExist();
        assertThat(resultExist).isNotNull().isEqualTo(exist);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForGetArea")
    void whenShapeCheckGetArea(int vertex, int edge, double area) {
        Box box = new Box(vertex, edge);
        double areaResult = box.getArea();
        assertThat(areaResult).isEqualTo(area, withPrecision(0.01d))
                .isCloseTo(area, Percentage.withPercentage(1.0d));
    }

    static Stream<Arguments> getArgumentsForShape() {
        return Stream.of(
                Arguments.of(0, 10, "Sphere"),
                Arguments.of(4, 20, "Tetrahedron"),
                Arguments.of(8, 30, "Cube"),
                Arguments.of(10, 40, "Unknown object"),
                Arguments.of(0, -5, "Unknown object")
        );
    }

    static Stream<Arguments> getArgumentsForVertex() {
        return Stream.of(
                Arguments.of(4, 20, 4),
                Arguments.of(10, 40, -1),
                Arguments.of(0, -5, -1)
        );
    }

    static Stream<Arguments> getArgumentsForCheckIsExist() {
        return Stream.of(
                Arguments.of(0, 10, true),
                Arguments.of(10, 40, false),
                Arguments.of(4, -20, false)
        );
    }

    static Stream<Arguments> getArgumentsForGetArea() {
        return Stream.of(
                Arguments.of(0, 10, 1256.64),
                Arguments.of(4, 20, 692.82),
                Arguments.of(8, 30, 5400),
                Arguments.of(10, 40, 0.0)
        );
    }

}
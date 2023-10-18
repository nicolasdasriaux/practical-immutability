package practicalimmutability.kata.gameoflife;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class AreaTest {
    @Test
    void width_height() {

        final Area area = Area.of(Position.of(-10, -20), Position.of(100, 200));
        assertThat(area.width()).isEqualTo(111);
        assertThat(area.height()).isEqualTo(221);
    }

    @ParameterizedTest(name = "{2}")
    @MethodSource("positionExamples")
    void contains(Position position, boolean expectedResult, String description) {
        final Area area = Area.of(Position.of(-10, -20), Position.of(10, 20));
        assertThat(area.contains(position)).isEqualTo(expectedResult);
    }

    public static Stream<Arguments> positionExamples() {
        return Stream.of(
                Arguments.of(Position.of(0, 0), true, "Inside, strictly"),
                Arguments.of(Position.of(-10, 0), true, "Inside, on left border"),
                Arguments.of(Position.of(10, 0), true, "Inside, on right border"),
                Arguments.of(Position.of(0, -20), true, "Inside, on top border"),
                Arguments.of(Position.of(0, 20), true, "Inside, on bottom border"),
                Arguments.of(Position.of(-10, -20), true, "Inside, on left top corner"),
                Arguments.of(Position.of(10, -20), true, "Inside, on right top corner"),
                Arguments.of(Position.of(-10, 20), true, "Inside, on left bottom corner"),
                Arguments.of(Position.of(10, 20), true, "Inside, on right bottom corner"),

                Arguments.of(Position.of(-100, 0), false, "Outside, left sector"),
                Arguments.of(Position.of(100, 0), false, "Outside, right sector"),
                Arguments.of(Position.of(0, -200), false, "Outside, top sector"),
                Arguments.of(Position.of(0, 200), false, "Outside, bottom sector"),
                Arguments.of(Position.of(-100, -200), false, "Outside, left top sector"),
                Arguments.of(Position.of(100, -200), false, "Outside, right top sector"),
                Arguments.of(Position.of(-100, 200), false, "Outside, left bottom sector"),
                Arguments.of(Position.of(100, 200), false, "Outside, right bottom sector")
        );
    }

    @Test
    void normalize() {
        final Area area = Area.of(Position.of(-100, -200), Position.of(100, 200));
        assertThat(area.normalize(Position.of(-100, -200))).isEqualTo(Position.of(0, 0));
        assertThat(area.normalize(Position.of(10, 20))).isEqualTo(Position.of(110, 220));
    }
}

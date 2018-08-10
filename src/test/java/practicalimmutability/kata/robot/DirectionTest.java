package practicalimmutability.kata.robot;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static practicalimmutability.kata.robot.Direction.*;

class DirectionTest {
    static Stream<Arguments> directionCodeExamples() {
        return Stream.of(
                Arguments.of(North, 'N'),
                Arguments.of(South, 'S'),
                Arguments.of(East, 'E'),
                Arguments.of(West, 'W')
        );
    }

    @ParameterizedTest
    @MethodSource("directionCodeExamples")
    void toCode(final Direction direction, final char code) {
        assertThat(direction.toCode()).isEqualTo(code);
    }

    @ParameterizedTest
    @MethodSource("directionCodeExamples")
    void fromCode(final Direction direction, final char code) {
        assertThat(Direction.fromCode(code)).isEqualTo(direction);
    }
}

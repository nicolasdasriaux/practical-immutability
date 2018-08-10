package practicalimmutability.kata.robot;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class DirectionTest {
    static Stream<Arguments> codeExamples() {
        return Stream.of(
                Arguments.of(Direction.North, 'N'),
                Arguments.of(Direction.South, 'S'),
                Arguments.of(Direction.East, 'E'),
                Arguments.of(Direction.West, 'W')
        );
    }

    @ParameterizedTest
    @MethodSource("codeExamples")
    void toCode(final Direction direction, final char code) {
        assertThat(direction.toCode()).isEqualTo(code);
    }

    @ParameterizedTest
    @MethodSource("codeExamples")
    void fromCode(final Direction direction, final char code) {
        assertThat(Direction.fromCode(code)).isEqualTo(direction);
    }
}

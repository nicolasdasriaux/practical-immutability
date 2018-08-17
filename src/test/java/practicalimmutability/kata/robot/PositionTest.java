package practicalimmutability.kata.robot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static practicalimmutability.kata.robot.Direction.*;

class PositionTest {
    @Test
    void of() {
        final Position position = Position.of(4, 1);
        assertThat(position.x()).isEqualTo(4);
        assertThat(position.y()).isEqualTo(1);
    }

    static Stream<Arguments> moveExamples() {
        return Stream.of(
                Arguments.of(Position.of(4, 1), North, Position.of(4, 0)),
                Arguments.of(Position.of(4, 1), South, Position.of(4, 2)),
                Arguments.of(Position.of(4, 1), West, Position.of(3, 1)),
                Arguments.of(Position.of(4, 1), East, Position.of(5, 1))
        );
    }

    @ParameterizedTest
    @MethodSource("moveExamples")
    void move(final Position origin, final Direction direction, final Position destination) {
        assertThat(origin.move(direction)).isEqualTo(destination);
    }
}

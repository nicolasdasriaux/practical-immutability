package practicalimmutability.kata.robot;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import io.vavr.collection.List;
import io.vavr.collection.Seq;

import static org.assertj.core.api.Assertions.*;
import static practicalimmutability.kata.robot.Direction.*;

@DisplayName("Position")
class PositionTest {
    @DisplayName("Should create position from x and y")
    @Test
    void of() {
        final Position position = Position.of(4, 1);
        assertThat(position.x()).isEqualTo(4);
        assertThat(position.y()).isEqualTo(1);
    }

    static Seq<Arguments> moveExamples() {
        return List.of(
                Arguments.of(Position.of(4, 1), NORTH, Position.of(4, 0)),
                Arguments.of(Position.of(4, 1), SOUTH, Position.of(4, 2)),
                Arguments.of(Position.of(4, 1), WEST, Position.of(3, 1)),
                Arguments.of(Position.of(4, 1), EAST, Position.of(5, 1))
        );
    }

    @DisplayName("Should return new position when moving to a direction")
    @ParameterizedTest(name = "Starting from {0} and moving to {1} should arrive at {2}")
    @MethodSource("moveExamples")
    void move(final Position origin, final Direction direction, final Position destination) {
        assertThat(origin.move(direction)).isEqualTo(destination);
    }
}

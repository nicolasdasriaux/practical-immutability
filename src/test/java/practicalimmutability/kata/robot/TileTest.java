package practicalimmutability.kata.robot;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import practicalimmutability.kata.robot.Tile.*;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static practicalimmutability.kata.robot.Direction.*;

class TileTest {
    static Stream<Arguments> tileCodeExamples() {
        return Stream.of(
                Arguments.of(Empty.of(), ' '),
                Arguments.of(Start.of(), '@'),
                Arguments.of(Booth.of(), '$'),
                Arguments.of(Obstacle.of(), '#'),
                Arguments.of(BreakableObstacle.of(), 'X'),
                Arguments.of(DirectionModifier.of(North), 'N'),
                Arguments.of(DirectionModifier.of(South), 'S'),
                Arguments.of(DirectionModifier.of(West), 'W'),
                Arguments.of(DirectionModifier.of(East), 'E'),
                Arguments.of(CircuitInverter.of(), 'I'),
                Arguments.of(Beer.of(), 'B'),
                Arguments.of(Teleporter.of(), 'T')
        );
    }

    @ParameterizedTest
    @MethodSource("tileCodeExamples")
    void toCode(final Tile tile, final char code) {
        assertThat(tile.toCode()).isEqualTo(code);
    }

    @ParameterizedTest
    @MethodSource("tileCodeExamples")
    void fromCode(final Tile tile, final char code) {
        assertThat(Tile.fromCode(code)).isEqualTo(tile);
    }
}

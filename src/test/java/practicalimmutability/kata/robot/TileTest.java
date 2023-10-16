package practicalimmutability.kata.robot;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import practicalimmutability.kata.robot.Tile.Beer;
import practicalimmutability.kata.robot.Tile.Booth;
import practicalimmutability.kata.robot.Tile.BreakableObstacle;
import practicalimmutability.kata.robot.Tile.CircuitInverter;
import practicalimmutability.kata.robot.Tile.DirectionModifier;
import practicalimmutability.kata.robot.Tile.Empty;
import practicalimmutability.kata.robot.Tile.Obstacle;
import practicalimmutability.kata.robot.Tile.Start;
import practicalimmutability.kata.robot.Tile.Teleporter;

import io.vavr.collection.List;
import io.vavr.collection.Seq;

import static org.assertj.core.api.Assertions.*;
import static practicalimmutability.kata.robot.Direction.*;

@DisplayName("Tile")
class TileTest {
    static Seq<Arguments> tileCodeExamples() {
        return List.of(
                Arguments.of(Empty.of(), ' '),
                Arguments.of(Start.of(), '@'),
                Arguments.of(Booth.of(), '$'),
                Arguments.of(Obstacle.of(), '#'),
                Arguments.of(BreakableObstacle.of(), 'X'),
                Arguments.of(DirectionModifier.of(NORTH), 'N'),
                Arguments.of(DirectionModifier.of(SOUTH), 'S'),
                Arguments.of(DirectionModifier.of(WEST), 'W'),
                Arguments.of(DirectionModifier.of(EAST), 'E'),
                Arguments.of(CircuitInverter.of(), 'I'),
                Arguments.of(Beer.of(), 'B'),
                Arguments.of(Teleporter.of(), 'T')
        );
    }

    @DisplayName("Should get code from tile")
    @ParameterizedTest(name = "{0} -> ''{1}''")
    @MethodSource("tileCodeExamples")
    void toCode(final Tile tile, final char code) {
        assertThat(tile.toCode()).isEqualTo(code);
    }

    @DisplayName("Should get tile from code")
    @ParameterizedTest(name = "''{1}'' -> {0}")
    @MethodSource("tileCodeExamples")
    void fromCode(final Tile tile, final char code) {
        assertThat(Tile.fromCode(code)).isEqualTo(tile);
    }
}

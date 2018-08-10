package practicalimmutability.kata.robot;

import io.vavr.collection.List;
import org.junit.jupiter.api.Test;
import practicalimmutability.kata.robot.Tile.Booth;
import practicalimmutability.kata.robot.Tile.Empty;
import practicalimmutability.kata.robot.Tile.Obstacle;
import practicalimmutability.kata.robot.Tile.Start;

import static org.assertj.core.api.Assertions.assertThat;

class CityMapTest {
    private static final CityMap cityMap = CityMap.fromLines(
            "##########",
            "#@      B#",
            "#   XX T #",
            "#  BN   X#",
            "# T   I $#",
            "##########"
    );

    @Test
    void rows() {
        final CityMap cityMap = CityMap.fromLines(
                "####",
                "#@ #",
                "# $#",
                "####"
        );

        final Obstacle O = Obstacle.of();
        final Start S = Start.of();
        final Booth B = Booth.of();
        final Empty e = Empty.of();

        final List<List<Tile>> expectedRows = List.of(
                List.of(O, O, O, O),
                List.of(O, S, e, O),
                List.of(O, e, B, O),
                List.of(O, O, O, O)
        );

        assertThat(cityMap.rows()).isEqualTo(expectedRows);
    }

    @Test
    void start() {
        assertThat(cityMap.start()).isEqualTo(Position.of(1, 1));
    }

    @Test
    void teleporters() {
        assertThat(cityMap.teleporters()).containsExactlyInAnyOrder(Position.of(2, 4), Position.of(7, 2));
    }

    @Test
    void tile() {
        assertThat(cityMap.tile(Position.of(1, 1))).isEqualTo(Start.of());
    }

    @Test
    void teleporterOutPosition() {
        assertThat(cityMap.teleporterOutPosition(Position.of(2, 4))).isEqualTo(Position.of(7, 2));
        assertThat(cityMap.teleporterOutPosition(Position.of(7, 2))).isEqualTo(Position.of(2, 4));
    }

    @Test
    void breakObstacle() {
        final CityMap expectedCityMap = CityMap.fromLines(
                "##########",
                "#@       #",
                "#   XX T #",
                "#  BN   X#",
                "# T   I $#",
                "##########"
        );

        assertThat(cityMap.breakObstacle(Position.of(8, 1))).isEqualTo(expectedCityMap);
    }
}

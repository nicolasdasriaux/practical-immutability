package practicalimmutability.kata.robot;

import io.vavr.collection.List;
import org.junit.jupiter.api.Test;
import practicalimmutability.kata.robot.Tile.*;

import static org.assertj.core.api.Assertions.assertThat;

class CityMapTest {
    private final CityMap cityMap = CityMap.fromLines(
            // @formatter:off
           //0123456789
            "##########", // 0
            "#@       #", // 1
            "# B I T  #", // 2
            "#        #", // 3
            "#  N E   #", // 4
            "#   X    #", // 5
            "#  W S   #", // 6
            "#        #", // 7
            "#   T   $#", // 8
            "##########"  // 9
            // @formatter:on
    );

    @Test
    void rows() {
        final Empty __ = Empty.of();
        final Start A_ = Start.of(); // The Alpha
        final Booth Z_ = Booth.of(); // The Omega
        final Obstacle X_ = Obstacle.of();
        final BreakableObstacle x_ = BreakableObstacle.of();
        final DirectionModifier N_ = DirectionModifier.of(Direction.North);
        final DirectionModifier S_ = DirectionModifier.of(Direction.South);
        final DirectionModifier W_ = DirectionModifier.of(Direction.West);
        final DirectionModifier E_ = DirectionModifier.of(Direction.East);
        final CircuitInverter I_ = CircuitInverter.of();
        final Beer B_ = Beer.of();
        final Teleporter T_ = Teleporter.of();

        final List<List<Tile>> expectedRows = List.of(
                List.of(X_, X_, X_, X_, X_, X_, X_, X_, X_, X_),
                List.of(X_, A_, __, __, __, __, __, __, __, X_),
                List.of(X_, __, B_, __, I_, __, T_, __, __, X_),
                List.of(X_, __, __, __, __, __, __, __, __, X_),
                List.of(X_, __, __, N_, __, E_, __, __, __, X_),
                List.of(X_, __, __, __, x_, __, __, __, __, X_),
                List.of(X_, __, __, W_, __, S_, __, __, __, X_),
                List.of(X_, __, __, __, __, __, __, __, __, X_),
                List.of(X_, __, __, __, T_, __, __, __, Z_, X_),
                List.of(X_, X_, X_, X_, X_, X_, X_, X_, X_, X_)
        );

        assertThat(cityMap.rows()).isEqualTo(expectedRows);
    }

    @Test
    void start() {
        assertThat(cityMap.start()).isEqualTo(Position.of(1, 1));
    }

    @Test
    void teleporters() {
        assertThat(cityMap.teleporters()).containsExactlyInAnyOrder(Position.of(6, 2), Position.of(4, 8));
    }

    @Test
    void tile() {
        assertThat(cityMap.tile(Position.of(8, 8))).isEqualTo(Booth.of());
    }

    @Test
    void teleporterOutPosition() {
        assertThat(cityMap.teleporterOutPosition(Position.of(6, 2))).isEqualTo(Position.of(4, 8));
        assertThat(cityMap.teleporterOutPosition(Position.of(4, 8))).isEqualTo(Position.of(6, 2));
    }

    @Test
    void breakObstacle() {
        final CityMap expectedCityMap = CityMap.fromLines(
                "##########",
                "#@       #",
                "# B I T  #",
                "#        #",
                "#  N E   #",
                "#        #",
                "#  W S   #",
                "#        #",
                "#   T   $#",
                "##########"
        );

        assertThat(cityMap.breakObstacle(Position.of(4, 5))).isEqualTo(expectedCityMap);
    }
}

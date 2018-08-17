package practicalimmutability.kata.robot;

import io.vavr.collection.IndexedSeq;
import io.vavr.collection.Vector;
import org.junit.jupiter.api.Test;
import practicalimmutability.kata.robot.Tile.*;

import static org.assertj.core.api.Assertions.assertThat;
import static practicalimmutability.kata.robot.Direction.*;

class CityMapTest {
    private final CityMap cityMap = CityMap.fromLines(
            // @formatter:off
           //0123456789 | @formatter:on
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
    );

    @Test
    void rows() {
        final Empty ___ = Empty.of();
        final Start _A_ = Start.of(); // The Alpha
        final Booth _Z_ = Booth.of(); // The Omega
        final Obstacle _X_ = Obstacle.of();
        final BreakableObstacle _x_ = BreakableObstacle.of();
        final DirectionModifier _N_ = DirectionModifier.of(North);
        final DirectionModifier _S_ = DirectionModifier.of(South);
        final DirectionModifier _W_ = DirectionModifier.of(West);
        final DirectionModifier _E_ = DirectionModifier.of(East);
        final CircuitInverter _I_ = CircuitInverter.of();
        final Beer _B_ = Beer.of();
        final Teleporter _T_ = Teleporter.of();

        final IndexedSeq<IndexedSeq<Tile>> expectedRows = Vector.of(
                Vector.of(_X_, _X_, _X_, _X_, _X_, _X_, _X_, _X_, _X_, _X_),
                Vector.of(_X_, _A_, ___, ___, ___, ___, ___, ___, ___, _X_),
                Vector.of(_X_, ___, _B_, ___, _I_, ___, _T_, ___, ___, _X_),
                Vector.of(_X_, ___, ___, ___, ___, ___, ___, ___, ___, _X_),
                Vector.of(_X_, ___, ___, _N_, ___, _E_, ___, ___, ___, _X_),
                Vector.of(_X_, ___, ___, ___, _x_, ___, ___, ___, ___, _X_),
                Vector.of(_X_, ___, ___, _W_, ___, _S_, ___, ___, ___, _X_),
                Vector.of(_X_, ___, ___, ___, ___, ___, ___, ___, ___, _X_),
                Vector.of(_X_, ___, ___, ___, _T_, ___, ___, ___, _Z_, _X_),
                Vector.of(_X_, _X_, _X_, _X_, _X_, _X_, _X_, _X_, _X_, _X_)
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
        assertThat(cityMap.tile(Position.of(4, 8))).isEqualTo(Teleporter.of());
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

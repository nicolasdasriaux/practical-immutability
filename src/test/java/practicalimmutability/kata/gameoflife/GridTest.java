package practicalimmutability.kata.gameoflife;

import org.junit.jupiter.api.Test;

import io.vavr.collection.HashSet;
import io.vavr.collection.Iterator;
import io.vavr.collection.List;

import static org.assertj.core.api.Assertions.*;

class GridTest {
    @Test
    void cellState() {
        final Grid grid = Grid.of(HashSet.of(
                Position.of(10, 20),
                Position.of(100, 200)
        ));

        assertThat(grid.cellState(Position.of(10, 20))).isEqualTo(CellState.ALIVE);
        assertThat(grid.cellState(Position.of(100, 200))).isEqualTo(CellState.ALIVE);
        assertThat(grid.cellState(Position.of(1, 2))).isEqualTo(CellState.DEAD);
    }

    @Test
    void positionsToReconsider() {
        final Grid grid = Grid.of(HashSet.of(
                Position.of(10, 20),
                Position.of(100, 200)
        ));

        assertThat(grid.positionsToReconsider()).containsExactlyInAnyOrder(
                Position.of(10, 20),
                Position.of(100, 200),

                Position.of(9, 19),
                Position.of(10, 19),
                Position.of(11, 19),
                Position.of(9, 20),
                Position.of(11, 20),
                Position.of(9, 21),
                Position.of(10, 21),
                Position.of(11, 21),

                Position.of(99, 199),
                Position.of(100, 199),
                Position.of(101, 199),
                Position.of(99, 200),
                Position.of(101, 200),
                Position.of(99, 201),
                Position.of(100, 201),
                Position.of(101, 201)
        );
    }

    @Test
    void aliveNeighbourCount() {
        final Grid grid = Grid.of(HashSet.of(
                Position.of(10, 20),

                Position.of(9, 19),
                Position.of(11, 19),
                Position.of(9, 20),
                Position.of(11, 20),
                Position.of(11, 21)
        ));

        assertThat(grid.aliveNeighbourCount(Position.of(10, 20))).isEqualTo(5);
    }

    @Test
    void block() {
        final Grid block = Grid.fromLines(List.of(
                "OO",
                "OO"
        ));

        assertThat(block.nextGridState()).isEqualTo(block);
        assertThat(block.nextGridState().nextGridState()).isEqualTo(block);
    }

    @Test
    void blinker() {
        final Grid vertical = Grid.fromLines(List.of(
                " O ",
                " O ",
                " O "
        ));

        final Grid horizontal = Grid.fromLines(List.of(
                "   ",
                "OOO",
                "   "
        ));

        assertThat(vertical.nextGridState()).isEqualTo(horizontal);
        assertThat(horizontal.nextGridState()).isEqualTo(vertical);
        assertThat(horizontal.nextGridState().nextGridState()).isEqualTo(horizontal);
    }

    @Test
    void glider() {
        final Grid glider = Grid.fromLines(List.of(
                " O ",
                "  O",
                "OOO"
        ));

        final Iterator<Grid> grids = Iterator.iterate(glider, Grid::nextGridState);

        assertThat(grids.take(5).toList()).containsExactly(
                Grid.fromLines(List.of(
                        " O ",
                        "  O",
                        "OOO"
                )),
                Grid.fromLines(List.of(
                        "   ",
                        "O O",
                        " OO",
                        " O "
                )),
                Grid.fromLines(List.of(
                        "   ",
                        "  O",
                        "O O",
                        " OO"
                )),
                Grid.fromLines(List.of(
                        "    ",
                        " O  ",
                        "  OO",
                        " OO "
                )),
                Grid.fromLines(List.of(
                         "    ",
                         "  O ",
                         "   O",
                         " OOO"
                ))
        );
    }
}

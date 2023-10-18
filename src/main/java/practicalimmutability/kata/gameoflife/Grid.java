package practicalimmutability.kata.gameoflife;

import io.vavr.collection.CharSeq;
import io.vavr.collection.IndexedSeq;
import io.vavr.collection.Seq;
import io.vavr.collection.Set;
import io.vavr.collection.Vector;
import io.vavr.control.Option;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

public record Grid(Set<Position> aliveCellPositions) {
    /**
     * Hints:
     * <ul>
     *     <li>Use {@link Set#contains(Object)}</li>
     * </ul>
     */
    public CellState cellState(Position position) {
        // IMPLEMENT FUNC {{{
        return aliveCellPositions.contains(position) ? CellState.ALIVE : CellState.DEAD;
        // }}}
    }

    /**
     * Hints:
     * <ul>
     *     <li>Positions to reconsider are alive positions and all neighbours of alive positions</li>
     *     <li>Use {@link Set#union(Set)}</li>
     *     <li>Use {@link Set#flatMap(Function)}</li>
     * </ul>
     * @return
     */
    public Set<Position> positionsToReconsider() {
        // IMPLEMENT FUNC {{{
        return aliveCellPositions.union(aliveCellPositions.flatMap(Position::neighbours));
        // }}}
    }

    /**
     * Hints:
     * <ul>
     *     <li>Use {@link Set#count(Predicate)}</li>
     * </ul>
     */
    public int aliveNeighbourCount(Position position) {
        // IMPLEMENT FUNC {{{
        return position.neighbours().count(p -> cellState(p) == CellState.ALIVE);
        // }}}
    }

    /**
     * Hints:
     * <ul>
     *     <li>Starting from {@link Grid#positionsToReconsider()},
     *     compute new alive positions using {@link Set#flatMap(Function)}</li>
     *     <li>For each position to reconsider, use {@link Rules#nextCellState(CellState, int)}</li>
     *     <li>For each reconsidered position, either return an alive position (using {@link Option#some(Object)})
     *     or no position for a dead position (using {@link Option#none()})</li>
     *     <li>Use {@link Grid#of(Set)}</li>
     * </ul>
     */
    public Grid nextGridState() {
        // IMPLEMENT FUNC {{{
        final Set<Position> newAlivePositions = positionsToReconsider()
                .flatMap(position -> {
                    final CellState nextCellState = Rules.nextCellState(cellState(position), aliveNeighbourCount(position));
                    return nextCellState == CellState.ALIVE ? Option.of(position) : Option.none();
                });

        return Grid.of(newAlivePositions);
        // }}}
    }

    public static Grid of(Set<Position> newAlivePositions) {
        return new Grid(newAlivePositions);
    }

    public static String toDisplayString(Grid grid, Area area) {
        final char[][] displayGrid = new char[area.height()][area.width()];

        for (int i = 0; i < displayGrid.length; i++) {
            Arrays.fill(displayGrid[i], '.');
        }

        grid.aliveCellPositions().filter(area::contains).map(area::normalize).forEach(position -> {
            displayGrid[position.y()][position.x()] = 'O';
        });

        final IndexedSeq<CharSeq> rows = Vector.of(displayGrid).map(CharSeq::of);
        return rows.map(chars -> chars.mkString(" ")).mkString("\n");
    }

    public static Grid fromLines(Seq<String> lines) {
        final Seq<Position> positions = lines.zipWithIndex().flatMap(rowWithIndex -> {
            final String row = rowWithIndex._1();
            final int y = rowWithIndex._2();

            return CharSeq.of(row).zipWithIndex().flatMap(cellWithIndex -> {
                final Character cell = cellWithIndex._1();
                final int x = cellWithIndex._2();
                return cell == 'O' ? Option.of(Position.of(x, y)) : Option.none();
            });
        });

        return Grid.of(positions.toSet());
    }
}

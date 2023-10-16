package practicalimmutability.kata.gameoflife;

import io.vavr.collection.CharSeq;
import io.vavr.collection.IndexedSeq;
import io.vavr.collection.Seq;
import io.vavr.collection.Set;
import io.vavr.collection.Vector;
import io.vavr.control.Option;

import java.util.Arrays;

public record Grid(Set<Position> aliveCellPositions) {
    public CellState cellState(Position position) {
        return aliveCellPositions.contains(position) ? CellState.ALIVE : CellState.DEAD;
    }

    public Set<Position> positionsToReconsider() {
        return aliveCellPositions.union(aliveCellPositions.flatMap(Position::neighbours));
    }

    public int aliveNeighbourCount(Position position) {
        return position.neighbours().count(p -> cellState(p) == CellState.ALIVE);
    }

    public Grid nextGridState() {
        final Set<Position> newAlivePositions = positionsToReconsider()
                .flatMap(position -> {
                    final CellState nextCellState = Rules.nextCellState(cellState(position), aliveNeighbourCount(position));
                    return nextCellState == CellState.ALIVE ? Option.of(position) : Option.none();
                });

        return Grid.of(newAlivePositions);
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

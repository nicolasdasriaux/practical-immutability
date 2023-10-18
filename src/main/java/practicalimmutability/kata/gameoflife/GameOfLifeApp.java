package practicalimmutability.kata.gameoflife;

import io.vavr.collection.Iterator;
import io.vavr.collection.List;

public class GameOfLifeApp {
    public static void main(String[] args) {
        final Grid grid = Grid.fromLines(List.of(
                " O ",
                "  O",
                "OOO"
        ));

        final Area area = Area.of(Position.of(0, 0), Position.of(15, 15));

        final Iterator<Grid> grids = Iterator.iterate(grid, Grid::nextGridState);

        grids.take(51).forEach(currentGrid -> {
            System.out.println("\u001b[H\u001b[2J");
            System.out.println(Grid.toDisplayString(currentGrid, area));
        });
    }
}

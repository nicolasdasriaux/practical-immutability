package practicalimmutability.kata.gameoflife;

public class Rules {
    public static CellState nextCellState(CellState cellState, int neighbourCount) {
        if (neighbourCount < 2) {
            return CellState.DEAD;
        } else if (neighbourCount > 3) {
            return CellState.DEAD;
        } else if (neighbourCount == 2) {
            return cellState;
        } else if (neighbourCount == 3) {
            return CellState.ALIVE;
        } else {
            throw new IllegalArgumentException("Invalid neighbour count (%d)".formatted(neighbourCount));
        }
    }
}

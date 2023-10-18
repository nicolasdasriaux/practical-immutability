package practicalimmutability.kata.gameoflife;

public class Rules {
    /**
     * Hints:
     * <ul>
     *     <li>Carefully look at unit tests</li>
     *     <li>Comment examples in unit tests if you wish to implement some rules in isolation</li>
     * </ul>
     */
    public static CellState nextCellState(CellState cellState, int neighbourCount) {
        // IMPLEMENT FUNC {{{
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
        // }}}
    }
}

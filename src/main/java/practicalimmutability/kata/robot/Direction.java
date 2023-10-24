package practicalimmutability.kata.robot;

/**
 * <b>Direction</b> for robot move
 */
public enum Direction {
    NORTH,
    SOUTH,
    WEST,
    EAST;

    /**
     * Get the code for this direction
     *
     * <p>Difficulty: *</p>
     */
    public char toCode() {
        return io.vavr.API.TODO();
    }

    /**
     * Get a direction from a code
     *
     * <p>Difficulty: *</p>
     */
    public static Direction fromCode(final char code) {
        return io.vavr.API.TODO();
    }
}

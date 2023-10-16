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
        // IMPLEMENT FUNC {{{
        return switch (this) {
            case NORTH -> 'N';
            case SOUTH -> 'S';
            case WEST -> 'W';
            case EAST -> 'E';
        };
        // }}}
    }

    /**
     * Get a direction from a code
     *
     * <p>Difficulty: *</p>
     */
    public static Direction fromCode(final char code) {
        // IMPLEMENT FUNC {{{
        return switch (code) {
            case 'N' -> NORTH;
            case 'S' -> SOUTH;
            case 'W' -> WEST;
            case 'E' -> EAST;
            default -> throw new IllegalArgumentException(String.format("Unknown Direction code (%s)", code));
        };
        // }}}
    }
}

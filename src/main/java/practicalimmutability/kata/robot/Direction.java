package practicalimmutability.kata.robot;

public enum Direction {
    North,
    South,
    West,
    East;

    /**
     * Get the code for this direction
     *
     * Difficulty: *
     */
    public char toCode() {
        switch (this) {
            case North: return 'N';
            case South: return 'S';
            case West: return 'W';
            case East: return 'E';
            default: throw new IllegalArgumentException(String.format("Unknown Direction (%s)", this));
        }
    }

    /**
     * Get a direction from a code
     *
     * Difficulty: *
     */
    public static Direction fromCode(final char code) {
        switch (code) {
            case 'N': return North;
            case 'S': return South;
            case 'W': return West;
            case 'E': return East;
            default: throw new IllegalArgumentException(String.format("Unknown Direction code (%s)", code));
        }
    }
}

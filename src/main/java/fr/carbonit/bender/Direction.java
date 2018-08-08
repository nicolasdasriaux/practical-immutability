package fr.carbonit.bender;

public enum Direction {
    North,
    South,
    West,
    East;

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

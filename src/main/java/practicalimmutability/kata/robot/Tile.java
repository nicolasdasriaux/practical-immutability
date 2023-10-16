package practicalimmutability.kata.robot;

/**
 * <b>Tile</b> of a city map
 */
public sealed interface Tile {
    record Empty() implements Tile {
        public static Empty of() {
            return new Empty();
        }
    }

    record Start() implements Tile {
        public static Start of() {
            return new Start();
        }
    }

    record Booth() implements Tile {
        public static Booth of() {
            return new Booth();
        }
    }

    record Obstacle() implements Tile {
        public static Obstacle of() {
            return new Obstacle();
        }
    }

    record BreakableObstacle() implements Tile {
        public static BreakableObstacle of() {
            return new BreakableObstacle();
        }
    }

    record DirectionModifier(Direction direction) implements Tile {
        public static DirectionModifier of(final Direction direction) {
            return new DirectionModifier(direction);
        }
    }

    record CircuitInverter() implements Tile {
        public static CircuitInverter of() {
            return new CircuitInverter();
        }
    }

    record Beer() implements Tile {
        public static Beer of() {
            return new Beer();
        }
    }

    record Teleporter() implements Tile {
        public static Teleporter of() {
            return new Teleporter();
        }
    }

    /**
     * Get the code for this tile
     * <p>Difficulty: *</p>
     */
    default char toCode() {
        // IMPLEMENT FUNC {{{
        return switch (this) {
            case Empty() -> ' ';
            case Start() -> '@';
            case Booth() -> '$';
            case Obstacle() -> '#';
            case BreakableObstacle() -> 'X';
            case DirectionModifier(Direction direction) -> direction.toCode();
            case CircuitInverter() -> 'I';
            case Beer() -> 'B';
            case Teleporter() -> 'T';
        };
        // }}}
    }

    /**
     * Get tile from a code
     * <p>Difficulty: *</p>
     */
    static Tile fromCode(final char code) {
        // IMPLEMENT FUNC {{{
        return switch (code) {
            case ' ' -> Empty.of();
            case '@' -> Start.of();
            case '$' -> Booth.of();
            case '#' -> Obstacle.of();
            case 'X' -> BreakableObstacle.of();
            case 'N', 'S', 'E', 'W' -> DirectionModifier.of(Direction.fromCode(code));
            case 'I' -> CircuitInverter.of();
            case 'B' -> Beer.of();
            case 'T' -> Teleporter.of();
            default -> throw new IllegalArgumentException(String.format("Unknown tile code (%s)", code));
        };
        // }}}
    }
}

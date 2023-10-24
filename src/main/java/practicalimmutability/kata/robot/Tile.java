package practicalimmutability.kata.robot;

/**
 * <b>Tile</b> of a city map
 */
public sealed interface Tile {
    /**
     * <b>Empty</b> tile
     */
    record Empty() implements Tile {
        public static Empty of() {
            return new Empty();
        }
    }

    /**
     * Tile where the robot should be located at <b>start</b>
     */
    record Start() implements Tile {
        public static Start of() {
            return new Start();
        }
    }

    /**
     * Suicide <b>booth</b> that the depressed robot needs to reach to commit suicide and <b>die</b>
     */
    record Booth() implements Tile {
        public static Booth of() {
            return new Booth();
        }
    }

    /**
     * <b>Obstacle</b> that causes the robot to <b>change direction</b> (rotating left or right) upon hitting it
     */
    record Obstacle() implements Tile {
        public static Obstacle of() {
            return new Obstacle();
        }
    }

    /**
     * <b>Breakable obstacle</b> that can be broken by the robot when in <b>breaker</b> mode,
     * otherwise the robot reacts the same as with typical <b>obstacle</b>
     */
    record BreakableObstacle() implements Tile {
        public static BreakableObstacle of() {
            return new BreakableObstacle();
        }
    }

    /**
     * <b>Direction modifier</b> that makes the robot <b>change direction</b> to given direction
     * @param direction Direction
     */
    record DirectionModifier(Direction direction) implements Tile {
        public static DirectionModifier of(final Direction direction) {
            return new DirectionModifier(direction);
        }
    }

    /**
     * <b>Circuit inverter</b> that inverts robot <b>direction for rotation</b> (clockwise, counterclockwise)
     */
    record CircuitInverter() implements Tile {
        public static CircuitInverter of() {
            return new CircuitInverter();
        }
    }

    /**
     * <b>Beer</b> that toggles robot <b>breaker mode</b> (off, on)
     */
    record Beer() implements Tile {
        public static Beer of() {
            return new Beer();
        }
    }

    /**
     * <b>Teleporter</b> that will cause robot to <b>teleport</b> to the other teleporter
     */
    record Teleporter() implements Tile {
        public static Teleporter of() {
            return new Teleporter();
        }
    }

    /**
     * Get the code for this tile
     * <p>Difficulty: *</p>
     * <p>Hints:</p>
     * <ul>
     *     <li>See cheatsheet in {@link RobotApp}</li>
     * </ul>
     */
    default char toCode() {
        return io.vavr.API.TODO();
    }

    /**
     * Get tile from a code
     * <p>Difficulty: *</p>
     * <p>Hints:</p>
     * <ul>
     *     <li>See cheatsheet in {@link RobotApp}</li>
     * </ul>
     */
    static Tile fromCode(final char code) {
        return io.vavr.API.TODO();
    }
}

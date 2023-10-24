package practicalimmutability.kata.robot;

import practicalimmutability.kata.robot.Tile.BreakableObstacle;
import practicalimmutability.kata.robot.Tile.Obstacle;

import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import lombok.Builder;
import lombok.With;

import java.util.function.Predicate;

import static practicalimmutability.kata.robot.Direction.*;

/**
 * State for <b>robot</b>
 *
 * @param position  Current position of robot
 * @param direction Current direction of robot
 * @param breaker   Breaker mode
 * @param inverted  Priority inversion mode
 * @param dead      Dead (or alive)
 */
@Builder(toBuilder = true)
@With
public record Robot(
        Position position,
        Direction direction,
        boolean breaker,
        boolean inverted,
        boolean dead) {

    /**
     * Change direction of robot
     * <p>Difficulty: *</p>
     */
    public Robot changeDirection(final Direction direction) {
        return io.vavr.API.TODO();
    }

    /**
     * Toggle breaker mode of robot
     * <p>Difficulty: *</p>
     */
    public Robot toggleBreaker() {
        return io.vavr.API.TODO();
    }

    /**
     * Toggle priority inversion of robot
     * <p>Difficulty: *</p>
     */
    public Robot invert() {
        return io.vavr.API.TODO();
    }

    /**
     * Turn robot to dead
     * <p>Difficulty: *</p>
     */
    public Robot die() {
        return io.vavr.API.TODO();
    }

    /**
     * Non-inverted priorities
     * <p>Difficulty: *</p>
     * <p>Hints:</p>
     * <ul>
     *     <li>Use {@link List#of(Object[])}</li>
     * </ul>
     */
    public static final Seq<Direction> PRIORITIES =
            io.vavr.API.TODO();

    /**
     * Inverted priorities
     * <p>Difficulty: *</p>
     * <p>Hints:</p>
     * <ul>
     *     <li>Use {@link #PRIORITIES}</li>
     *     <li>Use {@link Seq#reverse()}</li>
     * </ul>
     */
    public static final Seq<Direction> INVERTED_PRIORITIES =
            io.vavr.API.TODO();

    /**
     * Get current robot priorities
     * <p>Difficulty: *</p>
     * <p>Hints:</p>
     * <ul>
     *     <li>Use {@link #PRIORITIES}</li>
     *     <li>Use {@link #INVERTED_PRIORITIES}</li>
     * </ul>
     */
    public Seq<Direction> priorities() {
        return io.vavr.API.TODO();
    }

    /**
     * Move robot on a city map
     * Robot keeps the same direction and move if no obstacle in this direction.
     * Otherwise robot changes direction (following its current priorities) and move to where first there is no obstacle.
     * <p>Difficulty: **</p>
     * <p>Hints:</p>
     * <ul>
     *     <li>Use {@link Seq#find(Predicate)}</li>
     *     <li>Use {@link #obstacleInDirection(Direction, CityMap)}</li>
     *     <li>Use {@link Option#get()}</li>
     * </ul>
     */
    public Robot move(final CityMap cityMap) {
        return io.vavr.API.TODO();
    }

    /**
     * Determine whether there is an obstacle or not for the robot in a direction on a city map
     * <p>
     * <p>Difficulty: *</p>
     * <p>Hints:</p>
     * <ul>
     *     <li>Use {@link #obstacle(Tile)}</li>
     * </ul>
     */
    private boolean obstacleInDirection(final Direction direction, final CityMap cityMap) {
        return io.vavr.API.TODO();
    }

    /**
     * Determine whether or not a tile is currently an obstacle for the robot
     * Remind that a breakable obstacle ceases to be an obstacle when robot is in breaker mode.
     * <p>Difficulty: *</p>
     * <p>Hints:</p>
     * <ul>
     *     <li>Use {@link #breaker()}</li>
     * </ul>
     */
    private boolean obstacle(final Tile tile) {
        return io.vavr.API.TODO();
    }

    /**
     * Trigger teleporter at current position and teleport robot to destination
     */
    public Robot triggerTeleporter(final CityMap cityMap) {
        return io.vavr.API.TODO();
    }

    /**
     * Create a robot in initial state (at start position, south directed, no alteration, alive)
     * <p>Difficulty: *</p>
     */
    public static Robot fromStart(final Position position) {
        return io.vavr.API.TODO();
    }
}

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
        // IMPLEMENT FUNC {{{
        return this.withDirection(direction);
        // }}}
    }

    /**
     * Toggle breaker mode of robot
     * <p>Difficulty: *</p>
     */
    public Robot toggleBreaker() {
        // IMPLEMENT FUNC {{{
        return this.withBreaker(!breaker());
        // }}}
    }

    /**
     * Toggle priority inversion of robot
     * <p>Difficulty: *</p>
     */
    public Robot invert() {
        // IMPLEMENT FUNC {{{
        return this.withInverted(!inverted());
        // }}}
    }

    /**
     * Turn robot to dead
     * <p>Difficulty: *</p>
     */
    public Robot die() {
        // IMPLEMENT FUNC {{{
        return this.withDead(true);
        // }}}
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
            // IMPLEMENT CONST {{{
            List.of(SOUTH, EAST, NORTH, WEST);
            // }}}

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
            // IMPLEMENT CONST {{{
            PRIORITIES.reverse();
            // }}}

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
        // IMPLEMENT FUNC {{{
        return inverted() ? INVERTED_PRIORITIES : PRIORITIES;
        // }}}
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
        // IMPLEMENT FUNC {{{
        final Direction currentDirection = direction();

        final Direction updatedDirection;

        if (obstacleInDirection(currentDirection, cityMap)) {
            updatedDirection = priorities().find(direction -> !obstacleInDirection(direction, cityMap)).get();
        } else {
            updatedDirection = currentDirection;
        }

        return this.toBuilder()
                .position(position().move(updatedDirection))
                .direction(updatedDirection)
                .build();
        // }}}
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
        // IMPLEMENT FUNC {{{
        return obstacle(cityMap.tile(position().move(direction)));
        // }}}
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
        // IMPLEMENT FUNC {{{
        return switch (tile) {
            case Obstacle() -> true;
            case BreakableObstacle() -> !breaker();
            default -> false;
        };
        // }}}
    }

    /**
     * Trigger teleporter at current position and teleport robot to destination
     */
    public Robot triggerTeleporter(final CityMap cityMap) {
        // IMPLEMENT FUNC {{{
        final Position outPosition = cityMap.teleporterOutPosition(position());
        return this.withPosition(outPosition);
        // }}}
    }

    /**
     * Create a robot in initial state (at start position, south directed, no alteration, alive)
     * <p>Difficulty: *</p>
     */
    public static Robot fromStart(final Position position) {
        // IMPLEMENT FUNC {{{
        return Robot.builder()
                .position(position)
                .direction(SOUTH)
                .breaker(false)
                .inverted(false)
                .dead(false)
                .build();
        // }}}
    }
}

package practicalimmutability.kata.robot;

import io.vavr.API;
import io.vavr.Tuple2;
import io.vavr.collection.Iterator;
import org.immutables.value.Value;
import practicalimmutability.kata.robot.Tile.*;

import java.util.function.Function;
import java.util.function.Predicate;

@Value.Immutable
public abstract class Scene {
    /**
     * Current city map state (BEFORE robot acts)
     */
    public abstract CityMap cityMap();

    /**
     * Current robot state (BEFORE robot acts)
     * Robot has just arrived on position and has not reacted to the tile at current position yet.
     * Robot direction is the one when it just arrived at position, not the one it will take after having reacted to the tile at current position.
     * Robot might be in breaker mode on a breakable obstacle, obstacle will break as the consequence of robot reaction to tile.
     */
    public abstract Robot robot();

    /**
     * Determine whether or not this scene is completed
     *
     * Difficulty: *
     * Hints:
     * Use {@link Robot#dead()}
     */
    public boolean completed() {
        // IMPLEMENT FUNC {{{
        return robot().dead();
        // }}}
    }

    /**
     * Determine next scene after the robot has acted
     * Robot reacts to tile at current position and then moves.
     *
     * Difficulty: ***
     * Hints:
     * It is recommended to implement first WITHOUT using a visitor pattern nor Vavr pattern matching.
     *
     * Implement tiles one by one and fail with an exception when not implemented yet.
     * For this, use {@link API#TODO()}}
     *
     * Current tile cannot be an obstacle.
     * Current tile might be a breakable obstacle only when robot is in breaker mode.
     * Be sure to handle the case for breakable obstacle and impact city map.
     * A dead robot cannot move.
     *
     * Use {@link IllegalStateException} for unexpected cases
     *
     * @see <a href="https://www.baeldung.com/vavr-pattern-matching">Guide to Pattern Matching in Vavr</a>
     */
    public Scene next() {
        // IMPLEMENT FUNC {{{
        final CityMap currentCityMap = cityMap();
        final Robot currentRobot = robot();
        final Position currentPosition = currentRobot.position();
        final Tile currentTile = currentCityMap.tile(currentPosition);

        if (currentTile instanceof Empty || currentTile instanceof Start) {
            final Robot updatedRobot = currentRobot.move(currentCityMap);
            return ImmutableScene.copyOf(this).withRobot(updatedRobot);
        } else if (currentTile instanceof Booth) {
            final Robot updatedRobot = currentRobot.die();
            return ImmutableScene.copyOf(this).withRobot(updatedRobot);
        } else if (currentTile instanceof Obstacle) {
            throw new IllegalStateException("Position should never be on Obstacle tile");
        } else if (currentTile instanceof BreakableObstacle) {
            final CityMap updatedCityMap = currentCityMap.breakObstacle(currentPosition);
            final Robot updatedRobot = currentRobot.move(updatedCityMap);

            return ImmutableScene.builder().from(this)
                    .cityMap(updatedCityMap)
                    .robot(updatedRobot)
                    .build();
        } else if (currentTile instanceof DirectionModifier) {
            final DirectionModifier directionModifier = (DirectionModifier) currentTile;

            final Robot updatedRobot = currentRobot
                    .changeDirection(directionModifier.direction())
                    .move(currentCityMap);

            return ImmutableScene.copyOf(this).withRobot(updatedRobot);
        } else if (currentTile instanceof CircuitInverter) {
            final Robot updatedRobot = currentRobot
                    .invert()
                    .move(currentCityMap);

            return ImmutableScene.copyOf(this).withRobot(updatedRobot);
        } else if (currentTile instanceof Beer) {
            final Robot updatedRobot = currentRobot
                    .toggleBreaker()
                    .move(currentCityMap);

            return ImmutableScene.copyOf(this).withRobot(updatedRobot);
        } else if (currentTile instanceof Teleporter) {
            final Robot updatedRobot = currentRobot
                    .triggerTeleporter(currentCityMap)
                    .move(currentCityMap);

            return ImmutableScene.copyOf(this).withRobot(updatedRobot);
        } else {
            throw new IllegalStateException(String.format("Unexpected Tile (%s)", currentTile));
        }
        // }}}
    }

    /**
     * Get an iterator over the successive scenes including the completed scene
     *
     * Difficulty: ****
     * Hints:
     * Prepare to face reasoning on infinite iterators
     * Read Vavr Javadoc carefully especially for {@link Iterator#iterate(Object, Function)} and {@link Iterator#span(Predicate)}
     *
     * Use {@link Iterator#iterate(Object, Function)} to generate successive scenes starting from initial scene
     * Use {@link Iterator#span(Predicate)}} to get a pair of iterators (scenes before completion, scenes at and after completion)
     * Use {@link Tuple2#_1} and {@link Tuple2#_2} to access both iterators in the pair
     * Use {@link Iterator#take(int)} to get iterator for just the scene at completion
     * Use {@link Iterator#concat(Iterable[])} to concat both iterators (scenes before completion, scene at completion)
     */
    public Iterator<Scene> run() {
        // IMPLEMENT FUNC {{{
        final Tuple2<Iterator<Scene>, Iterator<Scene>> prefixAndReminder =
                Iterator.iterate(this, Scene::next).span(scene -> !scene.completed());

        return Iterator.concat(prefixAndReminder._1, prefixAndReminder._2.take(1));
        // }}}
    }

    /**
     * Create initial scene from a city map
     *
     * Difficulty: *
     */
    public static Scene fromCityMap(final CityMap cityMap) {
        // IMPLEMENT FUNC {{{
        final Robot robot = Robot.fromStart(cityMap.start());

        return ImmutableScene.builder()
                .cityMap(cityMap)
                .robot(robot)
                .build();
        // }}}
    }
}

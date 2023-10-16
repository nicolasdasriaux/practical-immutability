package practicalimmutability.kata.robot;

import practicalimmutability.kata.robot.Tile.Beer;
import practicalimmutability.kata.robot.Tile.Booth;
import practicalimmutability.kata.robot.Tile.BreakableObstacle;
import practicalimmutability.kata.robot.Tile.CircuitInverter;
import practicalimmutability.kata.robot.Tile.DirectionModifier;
import practicalimmutability.kata.robot.Tile.Empty;
import practicalimmutability.kata.robot.Tile.Obstacle;
import practicalimmutability.kata.robot.Tile.Start;
import practicalimmutability.kata.robot.Tile.Teleporter;

import io.vavr.API;
import io.vavr.Tuple2;
import io.vavr.collection.Iterator;
import lombok.Builder;
import lombok.With;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * State for <b>scene</b> comprising both the city map and the robot
 *
 * @param cityMap Current city map state (<u>before</u> robot acts)
 * @param robot   Current robot state (<u>before</u> robot acts)
 *                Robot has just arrived at position and has not reacted to the tile at current position yet.
 *                Robot direction is the one when it just arrived at position,
 *                not the one it will take after having reacted to the tile at current position.
 *                Robot might be in breaker mode on a breakable obstacle,
 *                obstacle will break as the consequence of robot reaction to tile.
 */
@Builder(toBuilder = true)
public record Scene(
        CityMap cityMap,
        @With Robot robot) {

    /**
     * Determine whether or not this scene is completed
     * <p>Difficulty: *</p>
     * <p>Hints:</p>
     * <ul>
     *     <li>Use {@link Robot#dead()}</li>
     * </ul>
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
     * <p>Difficulty: ***</p>
     * <p>Hints:</p>
     * <ul>
     *     <li>It is recommended to implement first WITHOUT using a visitor pattern nor Vavr pattern matching.</li>
     *     <li>Implement tiles one by one and fail with an exception when not implemented yet.
     *     For this, use {@link API#TODO()}</li>
     *     <li>Current tile cannot be an obstacle.</li>
     *     <li>Current tile might be a breakable obstacle only when robot is in breaker mode.</li>
     *     <li>Be sure to handle the case for breakable obstacle and impact city map.</li>
     *     <li>A dead robot cannot move.</li>
     *     <li>Use {@link IllegalStateException} for unexpected cases</li>
     * </ul>
     */
    public Scene next() {
        // IMPLEMENT FUNC {{{
        final CityMap currentCityMap = cityMap();
        final Robot currentRobot = robot();
        final Position currentPosition = currentRobot.position();
        final Tile currentTile = currentCityMap.tile(currentPosition);

        return switch (currentTile) {
            case Empty() -> {
                final Robot updatedRobot = currentRobot.move(currentCityMap);
                yield this.withRobot(updatedRobot);
            }
            case Start() -> {
                final Robot updatedRobot = currentRobot.move(currentCityMap);
                yield this.withRobot(updatedRobot);
            }
            case Booth() -> {
                final Robot updatedRobot = currentRobot.die();
                yield  this.withRobot(updatedRobot);
            }
            case Obstacle() -> throw new IllegalStateException("Position should never be on Obstacle tile");
            case BreakableObstacle() -> {
                final CityMap updatedCityMap = currentCityMap.breakObstacle(currentPosition);
                final Robot updatedRobot = currentRobot.move(updatedCityMap);

                yield this.toBuilder()
                        .cityMap(updatedCityMap)
                        .robot(updatedRobot)
                        .build();
            }
            case DirectionModifier(Direction direction) -> {
                final Robot updatedRobot = currentRobot
                        .changeDirection(direction)
                        .move(currentCityMap);

                yield this.withRobot(updatedRobot);
            }
            case CircuitInverter() -> {
                final Robot updatedRobot = currentRobot
                        .invert()
                        .move(currentCityMap);

                yield  this.withRobot(updatedRobot);
            }
            case Beer() -> {
                final Robot updatedRobot = currentRobot
                        .toggleBreaker()
                        .move(currentCityMap);

                yield  this.withRobot(updatedRobot);
            }
            case Teleporter() -> {
                final Robot updatedRobot = currentRobot
                        .triggerTeleporter(currentCityMap)
                        .move(currentCityMap);

                yield  this.withRobot(updatedRobot);
            }
        };
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

        return Scene.builder()
                .cityMap(cityMap)
                .robot(robot)
                .build();
        // }}}
    }
}

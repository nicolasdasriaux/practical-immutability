package practicalimmutability.kata.robot;

import io.vavr.Tuple2;
import io.vavr.collection.Iterator;
import org.immutables.value.Value;
import practicalimmutability.kata.common.TODO;
import practicalimmutability.kata.robot.Tile.*;

import java.util.function.Function;
import java.util.function.Predicate;

@Value.Immutable
public abstract class Scene {
    public abstract CityMap cityMap();
    public abstract Robot robot();

    /**
     * Determine whether or not this scene is completed
     *
     * Difficulty: *
     * Hints:
     * Use {@link Robot#dead()}
     */
    public boolean completed() {
        return robot().dead();
    }

    /**
     * Determine next scene after the robot has acted
     * Robot reacts to tile at current position and then moves.
     *
     * Difficulty: ***
     * Hints:
     * Implement tiles one by one and fail with an exception when not implemented yet
     * For this use {@link TODO#IMPLEMENT()}
     * Be sure to handle the case for breakable obstacle and impact city map.
     * Current tile cannot be an obstacle.
     * Current tile can be a breakable obstacle when robot is in breaker mode.
     * A dead robot cannot move.
     */
    public Scene next() {
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
    }

    /**
     * Get an iterator over the successive scenes including the completed scene
     *
     * Difficulty: ****
     * Hints:
     * {@link Iterator#iterate(Object, Function)}
     * {@link Iterator#span(Predicate)}}
     * {@link Tuple2#_1}
     * {@link Tuple2#_2}
     */
    public Iterator<Scene> run() {
        final Tuple2<Iterator<Scene>, Iterator<Scene>> prefixAndReminder =
                Iterator.iterate(this, Scene::next).span(scene -> !scene.completed());

        return Iterator.concat(prefixAndReminder._1, prefixAndReminder._2.take(1));
    }

    /**
     * Create initial scene from a city map
     *
     * Difficulty: *
     */
    public static Scene fromCityMap(final CityMap cityMap) {
        final Robot robot = Robot.fromStart(cityMap.start());

        return ImmutableScene.builder()
                .cityMap(cityMap)
                .robot(robot)
                .build();
    }
}

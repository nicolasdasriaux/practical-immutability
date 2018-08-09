package fr.carbonit.bender;

import fr.carbonit.bender.Tile.*;
import org.immutables.value.Value;

@Value.Immutable
public abstract class Scene {
    public abstract CityMap cityMap();
    public abstract Robot robot();

    public boolean completed() {
        return robot().dead();
    }

    public Scene next() {
        final CityMap currentCityMap = cityMap();
        final Robot currentRobot = robot();
        final Position currentPosition = currentRobot.position();
        final Tile currentTile = currentCityMap.tile(currentPosition);

        if (currentTile instanceof Empty || currentTile instanceof Start) {
            final Robot newRobot = currentRobot.move(currentCityMap);
            return ImmutableScene.copyOf(this).withRobot(newRobot);
        } else if (currentTile instanceof Booth) {
            final Robot newRobot = currentRobot.die();
            return ImmutableScene.copyOf(this).withRobot(newRobot);
        } else if (currentTile instanceof Obstacle) {
            throw new IllegalStateException("Position should never be on Obstacle tile");
        } else if (currentTile instanceof BreakableObstacle) {
            final CityMap newCityMap = currentCityMap.breakObstacle(currentPosition);
            final Robot newRobot = currentRobot.move(newCityMap);

            return ImmutableScene.builder().from(this)
                    .cityMap(newCityMap)
                    .robot(newRobot)
                    .build();
        } else if (currentTile instanceof PathModifier) {
            final PathModifier pathModifier = (PathModifier) currentTile;

            final Robot newRobot = currentRobot
                    .changeDirection(pathModifier.direction())
                    .move(currentCityMap);

            return ImmutableScene.copyOf(this).withRobot(newRobot);
        } else if (currentTile instanceof CircuitInverter) {
            final Robot newRobot = currentRobot
                    .invert()
                    .move(currentCityMap);

            return ImmutableScene.copyOf(this).withRobot(newRobot);
        } else if (currentTile instanceof Beer) {
            final Robot newRobot = currentRobot
                    .toggleBreaker()
                    .move(currentCityMap);

            return ImmutableScene.copyOf(this).withRobot(newRobot);
        } else if (currentTile instanceof Teleporter) {
            final Robot newRobot = currentRobot
                    .triggerTeleporter(currentCityMap)
                    .move(currentCityMap);

            return ImmutableScene.copyOf(this).withRobot(newRobot);
        } else {
            throw new IllegalStateException(String.format("Unexpected Tile (%s)", currentTile));
        }
    }

    public static Scene fromCityMap(final CityMap cityMap) {
        final Robot robot = Robot.fromStart(cityMap.start());

        return ImmutableScene.builder()
                .cityMap(cityMap)
                .robot(robot)
                .build();
    }
}

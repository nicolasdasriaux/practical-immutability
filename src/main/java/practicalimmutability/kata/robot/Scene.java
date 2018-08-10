package practicalimmutability.kata.robot;

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

        if (currentTile instanceof Tile.Empty || currentTile instanceof Tile.Start) {
            final Robot updatedRobot = currentRobot.move(currentCityMap);
            return ImmutableScene.copyOf(this).withRobot(updatedRobot);
        } else if (currentTile instanceof Tile.Booth) {
            final Robot updatedRobot = currentRobot.die();
            return ImmutableScene.copyOf(this).withRobot(updatedRobot);
        } else if (currentTile instanceof Tile.Obstacle) {
            throw new IllegalStateException("Position should never be on Obstacle tile");
        } else if (currentTile instanceof Tile.BreakableObstacle) {
            final CityMap updatedCityMap = currentCityMap.breakObstacle(currentPosition);
            final Robot updatedRobot = currentRobot.move(updatedCityMap);

            return ImmutableScene.builder().from(this)
                    .cityMap(updatedCityMap)
                    .robot(updatedRobot)
                    .build();
        } else if (currentTile instanceof Tile.DirectionModifier) {
            final Tile.DirectionModifier directionModifier = (Tile.DirectionModifier) currentTile;

            final Robot updatedRobot = currentRobot
                    .changeDirection(directionModifier.direction())
                    .move(currentCityMap);

            return ImmutableScene.copyOf(this).withRobot(updatedRobot);
        } else if (currentTile instanceof Tile.CircuitInverter) {
            final Robot updatedRobot = currentRobot
                    .invert()
                    .move(currentCityMap);

            return ImmutableScene.copyOf(this).withRobot(updatedRobot);
        } else if (currentTile instanceof Tile.Beer) {
            final Robot updatedRobot = currentRobot
                    .toggleBreaker()
                    .move(currentCityMap);

            return ImmutableScene.copyOf(this).withRobot(updatedRobot);
        } else if (currentTile instanceof Tile.Teleporter) {
            final Robot updatedRobot = currentRobot
                    .triggerTeleporter(currentCityMap)
                    .move(currentCityMap);

            return ImmutableScene.copyOf(this).withRobot(updatedRobot);
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

package fr.carbonit.bender;

import fr.carbonit.bender.Tile.*;
import org.immutables.value.Value;

@Value.Immutable
public abstract class Scene {
    public abstract CityMap cityMap();
    public abstract Robot robot();

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
                    .useTeleporter(currentCityMap)
                    .move(currentCityMap);

            return ImmutableScene.copyOf(this).withRobot(newRobot);
        } else {
            throw new IllegalArgumentException(String.format("Unexpected Tile (%s)", currentTile));
        }
    }
}

/*
case class Scene(cityMap: CityMap, robot: Robot, tracking: Set[Robot], loop: Boolean) {
    def completed = loop || robot.dead

    def next(): Scene  = {
        val currentPosition = robot.position
        val currentSquare = cityMap.square(currentPosition)

        val newScene = currentSquare match {
            case Empty | Start => copy(robot = robot.move()(cityMap))
            case Booth => copy(robot = robot.die())

            case BreakableObstacle =>
                val newCityMap = cityMap.breakObstacle(currentPosition)
                copy(cityMap = newCityMap, robot = robot.move()(newCityMap))

            case PathModifier(direction) => copy(robot = robot.changeDirection(direction).move()(cityMap))
            case CircuitInverter => copy(robot = robot.invertPriorities().move()(cityMap))
            case Beer => copy(robot = robot.drinkBeer().move()(cityMap))
            case Teleporter => copy(robot = robot.enterTeleporter()(cityMap).move()(cityMap))
        }


        if (tracking.contains(newScene.robot))
            newScene.copy(loop = true)
        else {
            val currentTracking = if (currentSquare == BreakableObstacle) Set(robot) else tracking
            newScene.copy(tracking = currentTracking + newScene.robot)
        }
    }

    def run(): Option[Seq[String]] = {
        def runFrom(scene: Scene): Seq[Scene] = if (scene.completed) IndexedSeq(scene) else scene +: runFrom(scene.next())
        val scenes = runFrom(this)
        if (scenes.last.loop) None else Some(scenes.tail.init.map(_.robot.direction.toInstruction))
    }
}

object Scene {
    def fromCityMap(cityMap: CityMap): Scene = {
        val robot = Robot.fromStart(cityMap.start)
        Scene(cityMap, robot, Set(robot), false)
    }
}
*/
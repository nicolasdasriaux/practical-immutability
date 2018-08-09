package fr.carbonit.bender;

import fr.carbonit.common.TODO;
import io.vavr.collection.Set;
import io.vavr.control.Option;
import org.immutables.value.Value;

@Value.Immutable
public abstract class SceneTracker {
    public abstract Option<CityMap> previousCityMap();
    public abstract Set<Robot> robots();

    public boolean willLoop(final Robot robot) {
        return TODO.IMPLEMENT();
    }

    public SceneTracker track(final Robot robot) {
        return TODO.IMPLEMENT();
    }
}

/*
case class Scene(cityMap: CityMap, robot: Robot, robots: Set[Robot], loop: Boolean) {
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

            case DirectionModifier(direction) => copy(robot = robot.changeDirection(direction).move()(cityMap))
            case CircuitInverter => copy(robot = robot.invertPriorities().move()(cityMap))
            case Beer => copy(robot = robot.drinkBeer().move()(cityMap))
            case Teleporter => copy(robot = robot.enterTeleporter()(cityMap).move()(cityMap))
        }


        if (robots.contains(newScene.robot))
            newScene.copy(loop = true)
        else {
            val currentTracking = if (currentSquare == BreakableObstacle) Set(robot) else robots
            newScene.copy(robots = currentTracking + newScene.robot)
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

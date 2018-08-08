```scala
sealed trait Direction {
    def toInstruction: String = toString.toUpperCase
}

case object North extends Direction
case object South extends Direction
case object West extends Direction
case object East extends Direction

object Direction {
    def fromCode(code: Char): Direction = code match {
        case 'N' => North
        case 'E' => East
        case 'S' => South
        case 'W' => West
    }
}

case class Position(x: Int, y: Int) {
    def move(direction: Direction): Position = direction match {
        case North => copy(y = y - 1)
        case South => copy(y = y + 1)
        case West => copy(x = x - 1)
        case East => copy(x = x + 1)
    }
}

sealed trait Square
case object Empty extends Square
case object Start extends Square
case object Booth extends Square
case object Obstacle extends Square
case object BreakableObstacle extends Square
case class PathModifier(direction: Direction) extends Square
case object CircuitInverter extends Square
case object Beer extends Square
case object Teleporter extends Square

object Square {
    def fromCode(code: Char): Square = code match {
        case ' ' => Empty
        case '@' => Start
        case '$' => Booth
        case '#' => Obstacle
        case 'X' => BreakableObstacle
        case code @ ('N' | 'S' | 'E' | 'W') => PathModifier(Direction.fromCode(code))
        case 'I' => CircuitInverter
        case 'B' => Beer
        case 'T' => Teleporter
    }
}

case class CityMap(rows: IndexedSeq[IndexedSeq[Square]], start: Position, teleporters: Seq[Position]) {
    def square(position: Position): Square = rows(position.y)(position.x)
    def teleporterOutPosition(inPosition: Position): Position = teleporters.filter(_ != inPosition).head

    def breakObstacle(position: Position): CityMap = {
        this.copy(rows = rows.updated(position.y, rows(position.y).updated(position.x, Empty)))
    }
}

object CityMap {
    def fromLines(lines: IndexedSeq[String]): CityMap = {
        val rows = lines.map(_.map(Square.fromCode))
        val start = findPositions(rows, Start).head
        val teleporters = findPositions(rows, Teleporter)
        CityMap(rows, start, teleporters)
    }
    
    private[this] def findPositions(rows: IndexedSeq[IndexedSeq[Square]], desiredSquare: Square): Seq[Position] = {
        for {
            y <- 0 until rows.size
            x <- 0 until rows(y).size
            if rows(y)(x) == desiredSquare
        } yield Position(x, y)
    }
}

case class Robot(position: Position, direction: Direction, breaker: Boolean, inverted: Boolean, dead: Boolean) {
    def priorities: Seq[Direction] = if (inverted) Robot.priorities.reverse else Robot.priorities

    def move()(implicit cityMap: CityMap): Robot = {
        val newDirection =
            if (!obstacleInDirection(direction)) direction
            else priorities.find(!obstacleInDirection(_)).get

        copy(position = position.move(newDirection), direction = newDirection)
    }

    def die(): Robot = copy(dead = true)
    def changeDirection(newDirection: Direction): Robot = copy(direction = newDirection)
    def invertPriorities(): Robot = copy(inverted = !inverted)
    def drinkBeer(): Robot  = copy(breaker = !breaker)
    
    def enterTeleporter()(implicit cityMap: CityMap): Robot = {
        val outPosition = cityMap.teleporterOutPosition(position)
        copy(position = outPosition)
    }

    private[this] def obstacleInDirection(direction: Direction)(implicit cityMap: CityMap): Boolean = {
        obstacle(cityMap.square(position.move(direction)))
    }

    private[this] def obstacle(square: Square)(implicit cityMap: CityMap): Boolean = square match {
        case Obstacle => true
        case BreakableObstacle => !breaker
        case _ => false
    }
}

object Robot {
    def fromStart(position: Position) = Robot(position, South, false, false, false)
    val priorities: Seq[Direction] = List(South, East, North, West)
}

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

object Solution extends App {
    val Array(rowCount, columnCount) = readLine.split(" ").map(_.toInt)
    val lines: IndexedSeq[String] = (0 until rowCount).map(_ => readLine)
    lines.foreach(Console.err.println)

    val cityMap = CityMap.fromLines(lines)
    val scene = Scene.fromCityMap(cityMap)
    val result = scene.run()
    
    result match {
        case None => println("LOOP")
        case Some(moves) => moves.foreach(println)
    }
}
```
package practicalimmutability.kata.robot;

import io.vavr.collection.Iterator;

/**
 * ASCII-art drawings
 */
public class Drawing {
    /**
     * Get city map drawing as ASCII-art
     * @param cityMap City map to draw
     * @return ASCII-art for city map
     */
    public static String cityMapDrawing(final CityMap cityMap) {
        return cityMap.rows().map(row ->
                row.map(Tile::toCode).mkString(" ")
        ).mkString("\n");
    }

    /**
     * Get scene drawing as ASCII-art
     * @param scene Scene to draw
     * @return ASCII-art for scene
     */
    public static String sceneDrawing(final Scene scene) {
        final String robotStatus = robotStatusDrawing(scene.robot());
        final String sceneGrid = sceneGridDrawing(scene);
        return robotStatus + "\n" + sceneGrid;
    }

    private static String robotStatusDrawing(final Robot robot) {
        final String directionStatus = Character.toString(robot.direction().toCode());
        final String breakerStatus = robot.breaker() ? "Breaker" : "-------";
        final String invertedStatus = robot.inverted() ? "Inverted" : "--------";
        final String priorities = robot.priorities().map(Direction::toCode).mkString();
        final String deadStatus = robot.dead() ? "Dead" : "----";
        return String.format("| %s | %s | %s (%s) | %s |", directionStatus, breakerStatus, invertedStatus, priorities, deadStatus);
    }

    private static String sceneGridDrawing(final Scene scene) {
        final CityMap cityMap = scene.cityMap();
        final Robot robot = scene.robot();

        return Iterator.range(0, cityMap.rows().size()).map(y ->
                Iterator.range(0, cityMap.rows().get(y).size()).map(x -> {
                    final Position position = Position.of(x, y);
                    return robot.position().equals(position) ? '*' : cityMap.tile(position).toCode();
                }).mkString(" ")).mkString("\n");
    }
}

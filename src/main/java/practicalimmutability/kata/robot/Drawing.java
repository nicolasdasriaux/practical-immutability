package practicalimmutability.kata.robot;

import io.vavr.collection.Iterator;

public class Drawing {
    public static String cityMapDrawing(final CityMap cityMap) {
        return cityMap.rows().map(row ->
                row.map(Tile::toCode).mkString()
        ).mkString("\n");
    }

    public static String sceneDrawing(final Scene scene) {
        final String robotStatus = robotStatusDrawing(scene.robot());
        final String sceneGrid = sceneGridDrawing(scene);
        return robotStatus + "\n" + sceneGrid;
    }

    private static String robotStatusDrawing(final Robot robot) {
        final String breakerStatus = robot.breaker() ? "B" : "-";
        final String invertedStatus = robot.inverted() ? "I" : "-";
        final String priorities = robot.priorities().map(Direction::toCode).mkString();
        final String deadStatus = robot.dead() ? "$" : "-";
        return String.format("| %s | %s (%s) | %s |", breakerStatus, invertedStatus, priorities, deadStatus);
    }

    private static String sceneGridDrawing(final Scene scene) {
        final CityMap cityMap = scene.cityMap();
        final Robot robot = scene.robot();

        return Iterator.range(0, cityMap.rows().size()).map(y ->
                Iterator.range(0, cityMap.rows().get(y).size()).map(x -> {
                    final Position position = Position.of(x, y);
                    return robot.position().equals(position) ? robotDrawing(robot) : cityMap.tile(position).toCode();
                }).mkString()).mkString("\n");
    }

    private static char robotDrawing(final Robot robot) {
        return Character.toLowerCase(robot.direction().toCode());
    }
}

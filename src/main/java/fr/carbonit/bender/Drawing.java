package fr.carbonit.bender;

import io.vavr.collection.Iterator;

public class Drawing {
    public static String cityMapDrawing(final CityMap cityMap) {
        return cityMap.rows().map(row ->
                row.map(Tile::toCode).mkString()
        ).mkString("\n");
    }

    public static String sceneDrawing(final Scene scene) {
        final CityMap cityMap = scene.cityMap();
        final Robot robot = scene.robot();

        return Iterator.range(0, cityMap.rows().size()).map(y ->
                Iterator.range(0, cityMap.rows().get(y).size()).map(x -> {
                    final Position position = Position.of(x, y);
                    return robot.position().equals(position) ? "*" : cityMap.tile(position).toCode();
                }).mkString()
        ).mkString("\n");
    }
}

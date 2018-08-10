package practicalimmutability.kata.robot;

import io.vavr.collection.Iterator;

/**
 * @           Start
 * $           Booth
 * #           Obstacle
 * X           BreakableObstacle
 * N, S, E, W  DirectionModifier
 * I           CircuitInverter
 * B           Beer
 * T           Teleporter
 */
public class RobotApp {
    public static void main(final String[] args) {
        final Scene initialScene = Scene.fromCityMap(
                CityMap.fromLines(
                        "##########",
                        "#@      B#",
                        "#   XX T #",
                        "#   N   X#",
                        "# T   I $#",
                        "##########"
                )
        );

        final Iterator<Scene> scenes = Iterator.iterate(initialScene, Scene::next).takeUntil(Scene::completed);

        System.out.println(Drawing.cityMapDrawing(initialScene.cityMap()));
        System.out.println();

        scenes.forEach(scene -> {
            System.out.println(Drawing.sceneDrawing(scene));
            System.out.println();
        });
    }
}

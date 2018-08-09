package fr.carbonit.bender;

import io.vavr.collection.Stream;
import io.vavr.collection.Vector;

/**
 * @           Start
 * $           Booth
 * #           Obstacle
 * X           BreakableObstacle
 * N, S, E, W  PathModifier
 * I           CircuitInverter
 * B           Beer
 * T           Teleporter
 */
public class BenderApp {
    public static void main(final String[] args) {
        final Scene initialScene = Scene.fromCityMap(
                CityMap.fromLines(Vector.of(
                        "##########",
                        "#@      B#",
                        "#   XX T #",
                        "#   N   X#",
                        "# T     $#",
                        "##########"
                ))
        );

        final Stream<Scene> scenes = Stream.iterate(initialScene, Scene::next).takeUntil(Scene::completed);

        System.out.println(Drawing.cityMapDrawing(initialScene.cityMap()));
        System.out.println();

        scenes.forEach(scene -> {
            System.out.println(Drawing.sceneDrawing(scene));
            System.out.println();
        });
    }
}

package practicalimmutability.kata.robot;

import io.vavr.collection.Iterator;

/**
 * Use this main class to help you visualize what happens when running a scene
 * Feel free to modify the code to experiment and improve visualization
 *
 * Cheat Sheet
 * @           Start
 * $           Booth
 * #           Obstacle
 * X           BreakableObstacle
 * N, S, E, W  DirectionModifier
 * I           CircuitInverter
 * B           Beer
 * T           Teleporter
 *
 * You may add your own {@link Drawing#trackedSceneDrawing(TrackedScene)} method inspiring from
 * {@link Drawing#cityMapDrawing(CityMap)} and {@link Drawing#sceneDrawing(Scene)}.
 *
 */
public class RobotApp {
    public static void main(final String[] args) {
        final CityMap cityMap1 = CityMap.fromLines(
                "##########",
                "#@      B#",
                "#   XX T #",
                "#   N   X#",
                "# T   I $#",
                "##########"
        );

        final CityMap cityMap2 = CityMap.fromLines(
                "##########",
                "#@       #",
                "#     ####",
                "#     #  #",
                "#     # $#",
                "##########"
        );

        final CityMap cityMap3 = CityMap.fromLines(
                // @formatter:off
                      // 0123456789
                        "##########", // 0
                        "#@   E   #", // 1
                        "#   E S  #", // 2
                        "# T NNW I#", // 3
                        "#        #", // 4
                        "#        #", // 5
                        "#   # I  #", // 6
                        "#       ##", // 7
                        "#  T B X$#", // 8
                        "##########"  // 9
                        // @formatter:on
        );

        runScene(cityMap1);
        runTrackedScene(cityMap3);
    }

    private static void runScene(final CityMap cityMap) {
        final Scene initialScene = Scene.fromCityMap(cityMap);
        final Iterator<Scene> scenes = initialScene.run();

        System.out.println(Drawing.cityMapDrawing(initialScene.cityMap()));
        System.out.println();

        scenes.forEach(scene ->  {
            System.out.println(Drawing.sceneDrawing(scene));
            System.out.println();
        });
    }

    private static void runTrackedScene(final CityMap cityMap) {
        final Scene initialScene = Scene.fromCityMap(cityMap);
        final TrackedScene initialTrackedScene = TrackedScene.fromInitialScene(initialScene);
        final Iterator<TrackedScene> trackedScenes = initialTrackedScene.run();

        System.out.println(Drawing.cityMapDrawing(initialScene.cityMap()));
        System.out.println();

        trackedScenes.forEach(trackedScene ->  {
            System.out.println(Drawing.sceneDrawing(trackedScene.scene()));
            System.out.println();
        });
    }
}

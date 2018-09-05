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
 * You can add your own {@link Drawing#trackedSceneDrawing(TrackedScene)} method inspiring from
 * {@link Drawing#cityMapDrawing(CityMap)} and {@link Drawing#sceneDrawing(Scene)}.
 *
 */
public class RobotApp {
    public static void main(final String[] args) {
        final Scene initialScene1 = Scene.fromCityMap(
                CityMap.fromLines(
                        "##########",
                        "#@      B#",
                        "#   XX T #",
                        "#   N   X#",
                        "# T   I $#",
                        "##########"
                )
        );

        final Scene initialScene2 = Scene.fromCityMap(
                CityMap.fromLines(
                        "##########",
                        "#@       #",
                        "#     ####",
                        "#     #  #",
                        "#     # $#",
                        "##########"
                )
        );

        final Scene initialScene3 = Scene.fromCityMap(
                CityMap.fromLines(
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
                )
        );

        runScene(initialScene3);
    }

    private static void runScene(final Scene initialScene) {
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

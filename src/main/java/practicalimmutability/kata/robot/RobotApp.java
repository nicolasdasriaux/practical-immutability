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

        final Scene initialScene = initialScene3;

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

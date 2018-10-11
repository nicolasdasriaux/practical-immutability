package practicalimmutability.kata.robot;

import io.vavr.collection.HashSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static practicalimmutability.kata.robot.Direction.*;

@DisplayName("Scene Tracking")
class SceneTrackingTest {
    @DisplayName("Tracking")
    @Nested
    class Track {
        @DisplayName("should track city map and all robot states when city map unchanged")
        @Test
        void trackRobotWhenCityMapUnchanged() {
            final CityMap previousCityMap = CityMap.fromLines(
                    // @formatter:off
                  // 01234
                    "#####", // 0
                    "#@  #", // 1
                    "#   #", // 2
                    "#   #", // 3
                    "#####"  // 4
                    // @formatter:on
            );

            final SceneTracking previousSceneTracking = ImmutableSceneTracking.builder()
                    .previousCityMap(previousCityMap)
                    .previousRobots(
                            HashSet.of(
                                    ImmutableRobot.builder()
                                            .position(Position.of(1, 1)).direction(South)
                                            .breaker(false).inverted(false).dead(false)
                                            .build(),
                                    ImmutableRobot.builder()
                                            .position(Position.of(1, 2)).direction(South)
                                            .breaker(false).inverted(false).dead(false)
                                            .build()
                            )
                    )
                    .build();

            final Scene scene = ImmutableScene.builder()
                    .cityMap(previousCityMap)
                    .robot(
                            ImmutableRobot.builder()
                                    .position(Position.of(1, 3)).direction(South)
                                    .breaker(false).inverted(false).dead(false)
                                    .build()
                    )
                    .build();

            final SceneTracking sceneTracking = previousSceneTracking.track(scene);

            final SceneTracking expectedSceneTracking = ImmutableSceneTracking.builder()
                    .previousCityMap(previousCityMap)
                    .previousRobots(
                            HashSet.of(
                                    ImmutableRobot.builder()
                                            .position(Position.of(1, 1)).direction(South)
                                            .breaker(false).inverted(false).dead(false)
                                            .build(),
                                    ImmutableRobot.builder()
                                            .position(Position.of(1, 2)).direction(South)
                                            .breaker(false).inverted(false).dead(false)
                                            .build(),
                                    ImmutableRobot.builder()
                                            .position(Position.of(1, 3)).direction(South)
                                            .breaker(false).inverted(false).dead(false)
                                            .build()
                            )
                    )
                    .build();

            assertThat(sceneTracking).isEqualTo(expectedSceneTracking);
        }

        @DisplayName("should track new city map, forget past robot states and track new robot state when city map changed")
        @Test
        void restartRobotTrackingWhenCityMapChanged() {
            final CityMap previousCityMap = CityMap.fromLines(
                    // @formatter:off
                  // 01234
                    "#####", // 0
                    "#@  #", // 1
                    "#B  #", // 2
                    "#X  #", // 3
                    "#####"  // 4
                    // @formatter:on
            );

            final SceneTracking previousSceneTracking = ImmutableSceneTracking.builder()
                    .previousCityMap(previousCityMap)
                    .previousRobots(
                            HashSet.of(
                                    ImmutableRobot.builder()
                                            .position(Position.of(1, 1)).direction(South)
                                            .breaker(false).inverted(false).dead(false)
                                            .build(),
                                    ImmutableRobot.builder()
                                            .position(Position.of(1, 2)).direction(South)
                                            .breaker(false).inverted(false).dead(false)
                                            .build(),
                                    ImmutableRobot.builder()
                                            .position(Position.of(1, 3)).direction(South)
                                            .breaker(true).inverted(false).dead(false)
                                            .build()
                            )
                    )
                    .build();

            final CityMap cityMap = previousCityMap.breakObstacle(Position.of(1, 3));

            final Scene scene = ImmutableScene.builder()
                    .cityMap(cityMap)
                    .robot(
                            ImmutableRobot.builder()
                                    .position(Position.of(2, 3)).direction(East)
                                    .breaker(true).inverted(false).dead(false)
                                    .build()
                    )
                    .build();

            final SceneTracking sceneTracking = previousSceneTracking.track(scene);

            final SceneTracking expectedSceneTracking = ImmutableSceneTracking.builder()
                    .previousCityMap(cityMap)
                    .previousRobots(
                            HashSet.of(
                                    ImmutableRobot.builder()
                                            .position(Position.of(2, 3)).direction(East)
                                            .breaker(true).inverted(false).dead(false)
                                            .build()
                            )
                    )
                    .build();

            assertThat(sceneTracking).isEqualTo(expectedSceneTracking);
        }

        @DisplayName("should identify a loop when robot reached same past state and city map did not change meanwhile")
        @Test
        void identifyLoopWhenCityMapUnchanged() {
            final CityMap initialCityMap = CityMap.fromLines(
                    // @formatter:off
                  // 0123
                    "####", // 0
                    "#@ #", // 1
                    "#  #", // 2
                    "####"  // 3
                    // @formatter:on
            );

            final SceneTracking sceneTracking = SceneTracking.fromInitialScene(Scene.fromCityMap(initialCityMap))
                    .track(
                            ImmutableScene.builder()
                                    .cityMap(initialCityMap)
                                    .robot(
                                            ImmutableRobot.builder()
                                                    .position(Position.of(1, 2)).direction(South)
                                                    .breaker(false).inverted(false).dead(false)
                                                    .build()
                                    )
                                    .build()
                    )
                    .track(
                            ImmutableScene.builder()
                                    .cityMap(initialCityMap)
                                    .robot(
                                            ImmutableRobot.builder()
                                                    .position(Position.of(2, 2)).direction(East)
                                                    .breaker(false).inverted(false).dead(false)
                                                    .build()
                                            )
                                    .build()
                    )
                    .track(
                            ImmutableScene.builder()
                                    .cityMap(initialCityMap)
                                    .robot(
                                            ImmutableRobot.builder()
                                                    .position(Position.of(2, 1)).direction(North)
                                                    .breaker(false).inverted(false).dead(false)
                                                    .build()
                                    )
                                    .build()
                    )
                    .track(
                            ImmutableScene.builder()
                                    .cityMap(initialCityMap)
                                    .robot(
                                            ImmutableRobot.builder()
                                                    .position(Position.of(2, 2)).direction(South)
                                                    .breaker(false).inverted(false).dead(false)
                                                    .build()
                                    )
                                    .build()
                    )
                    .track(
                            ImmutableScene.builder()
                                    .cityMap(initialCityMap)
                                    .robot(
                                            ImmutableRobot.builder()
                                                    .position(Position.of(2, 1)).direction(North)
                                                    .breaker(false).inverted(false).dead(false)
                                                    .build()
                                    )
                                    .build()
                    );

            assertThat(sceneTracking.loop()).isTrue();
        }

        @DisplayName("should not identify a loop when robot reached same past state and city map changed meanwhile")
        @Test
        void identifyNoLoopWhenCityMapChanged() {
            final CityMap initialCityMap = CityMap.fromLines(
                    // @formatter:off
                  // 0123
                    "####", // 0
                    "#@X#", // 1
                    "#B #", // 2
                    "####"  // 3
                    // @formatter:on
            );

            final CityMap brokenObstacleCityMap = initialCityMap.breakObstacle(Position.of(2, 1));

            final SceneTracking sceneTracking = SceneTracking.fromInitialScene(Scene.fromCityMap(initialCityMap))
                    .track(
                            ImmutableScene.builder()
                                    .cityMap(initialCityMap)
                                    .robot(
                                            ImmutableRobot.builder()
                                                    .position(Position.of(1, 2)).direction(South)
                                                    .breaker(false).inverted(false).dead(false)
                                                    .build()
                                    )
                                    .build()
                    )
                    .track(
                            ImmutableScene.builder()
                                    .cityMap(initialCityMap)
                                    .robot(
                                            ImmutableRobot.builder()
                                                    .position(Position.of(2, 2)).direction(East)
                                                    .breaker(true).inverted(false).dead(false)
                                                    .build()
                                    )
                                    .build()
                    )
                    .track(
                            ImmutableScene.builder()
                                    .cityMap(initialCityMap)
                                    .robot(
                                            ImmutableRobot.builder()
                                                    .position(Position.of(2, 1)).direction(North)
                                                    .breaker(true).inverted(false).dead(false)
                                                    .build()
                                    )
                                    .build()
                    )
                    .track(
                            ImmutableScene.builder()
                                    .cityMap(brokenObstacleCityMap)
                                    .robot(
                                            ImmutableRobot.builder()
                                                    .position(Position.of(2, 2)).direction(South)
                                                    .breaker(true).inverted(false).dead(false)
                                                    .build()
                                    )
                                    .build()
                    )
                    .track(
                            ImmutableScene.builder()
                                    .cityMap(brokenObstacleCityMap)
                                    .robot(
                                            ImmutableRobot.builder()
                                                    .position(Position.of(2, 1)).direction(North)
                                                    .breaker(true).inverted(false).dead(false)
                                                    .build()
                                    )
                                    .build()
                    );

            assertThat(sceneTracking.loop()).isFalse();
        }
    }

    @DisplayName("should create initial scene tracking from initial scene")
    @Test
    void fromInitialScene() {
        final CityMap cityMap = CityMap.fromLines(
                // @formatter:off
              // 01234
                "#####", // 0
                "#@  #", // 1
                "#   #", // 2
                "#   #", // 3
                "#####"  // 4
                // @formatter:on
        );

        final Scene initialScene = Scene.fromCityMap(cityMap);
        final SceneTracking initialSceneTracking = SceneTracking.fromInitialScene(initialScene);

        final SceneTracking expectedInitialSceneTracking = ImmutableSceneTracking.builder()
                .loop(false)
                .previousCityMap(initialScene.cityMap())
                .previousRobots(HashSet.of(initialScene.robot()))
                .build();

        assertThat(initialSceneTracking).isEqualTo(expectedInitialSceneTracking);
    }
}

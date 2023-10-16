package practicalimmutability.kata.robot;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import io.vavr.collection.Iterator;
import io.vavr.collection.List;
import io.vavr.collection.Seq;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Tracked Scene")
class TrackedSceneTest {
    static Seq<Arguments> loopExamples() {
        return List.of(
                Arguments.of(
                        CityMap.fromLines(
                                // @formatter:off
                              // 01234
                                "#####", // 0
                                "#@  #", // 1
                                "#   #", // 2
                                "#   #", // 3
                                "#####"  // 4
                                // @formatter:on
                        )
                ),
                Arguments.of(
                        CityMap.fromLines(
                                // @formatter:off
                              // 01234
                                "#####", // 0
                                "#@ X#", // 1
                                "#   #", // 2
                                "#B  #", // 3
                                "#####"  // 4
                                // @formatter:on
                        )
                ),
                Arguments.of(
                        CityMap.fromLines(
                                // @formatter:off
                              // 01234
                                "#####", // 0
                                "#@  #", // 1
                                "#ES #", // 2
                                "#NW #", // 3
                                "#####"  // 4
                                // @formatter:on
                        )
                )
        );
    }

    @DisplayName("Should run until loop")
    @ParameterizedTest(name = "Example #{index}")
    @MethodSource("loopExamples")
    void loop(final CityMap initialCityMap) {
        final Scene scene = Scene.fromCityMap(initialCityMap);
        final Seq<TrackedScene> trackedScenes = TrackedScene.fromInitialScene(scene).run().toList();

        assertThat(trackedScenes.init().forAll(trackedScene -> ! trackedScene.loop())).isTrue();
        assertThat(trackedScenes.last().loop()).isTrue();
    }

    @DisplayName("Should hold successive scenes")
    @ParameterizedTest(name = "Example #{index}")
    @MethodSource("loopExamples")
    void scenes(final CityMap initialCityMap) {
        final Scene scene = Scene.fromCityMap(initialCityMap);
        final Seq<TrackedScene> trackedScenes = TrackedScene.fromInitialScene(scene).run().toList();

        final Seq<Scene> expectedScenes = Iterator.iterate(scene, Scene::next).take(trackedScenes.length()).toList();
        assertThat(trackedScenes.map(TrackedScene::scene)).isEqualTo(expectedScenes);
    }

    @DisplayName("Should create a tracked scene from an initial scene")
    @Test
    void fromInitialScene() {
        final CityMap cityMap = CityMap.fromLines(
                // @formatter:off
                  // 01234
                    "#####", // 0
                    "#@  #", // 1
                    "#   #", // 2
                    "#  $#", // 3
                    "#####"  // 4
                    // @formatter:on
        );

        final Scene initialScene = Scene.fromCityMap(cityMap);
        final TrackedScene initialTrackedScene = TrackedScene.fromInitialScene(initialScene);
        final SceneTracking initialSceneTracking = SceneTracking.fromInitialScene(initialScene);

        final TrackedScene trackedScene = TrackedScene.builder()
                .scene(initialScene)
                .tracking(initialSceneTracking)
                .build();

        assertThat(initialTrackedScene).isEqualTo(trackedScene);
    }
}

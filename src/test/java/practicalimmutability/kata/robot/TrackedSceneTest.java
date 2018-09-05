package practicalimmutability.kata.robot;

import io.vavr.collection.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class TrackedSceneTest {
    static Stream<Arguments> loopExamples() {
        return Stream.of(
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

    @ParameterizedTest
    @MethodSource("loopExamples")
    void loop(final CityMap initialCityMap) {
        final List<TrackedScene> trackedScenes = TrackedScene.fromInitialScene(Scene.fromCityMap(initialCityMap)).run().toList();
        final TrackedScene trackedScene = trackedScenes.last();
        assertThat(trackedScene.loop()).isTrue();
    }

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

        final TrackedScene trackedScene = ImmutableTrackedScene.builder()
                .scene(initialScene)
                .tracking(initialSceneTracking)
                .build();

        assertThat(initialTrackedScene).isEqualTo(trackedScene);
    }
}

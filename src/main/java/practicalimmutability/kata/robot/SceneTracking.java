package practicalimmutability.kata.robot;

import io.vavr.collection.Set;
import org.immutables.value.Value;

@Value.Immutable
public abstract class SceneTracking {
    /**
     * Previous city map state
     */
    public abstract CityMap previousCityMap();


    /**
     * Previous robot states
     */
    public abstract Set<Robot> previousRobots();

    /**
     * Looped scene
     */
    @Value.Default
    public boolean loop() {
        return  false;
    }

    /**
     * Consider a new scene and previous tracking state to detect a potential infinite loop
     *
     * Difficulty: ***
     * Hints:
     * {@link Set#contains(Object)}
     * Use equals methods as provided by Immutables
     * Be sure to look at unit tests for more details
     * Occurrence of previously observed robot state is not enough to detect loop, city map should also not have change meanwhile.
     * Somehow, you should reset robot tracking when city map changes.
     * A looped tracked scene should be kept unchanged.
     *
     * @param scene new scene to be considered
     * @return new tracking state
     */
    public SceneTracking track(final Scene scene) {
        // IMPLEMENT FUNC {{{
        final CityMap previousCityMap = previousCityMap();
        final CityMap cityMap = scene.cityMap();
        final Robot robot = scene.robot();

        if (loop()) {
            return this;
        } else {
            if (previousCityMap.equals(cityMap)) {
                if (previousRobots().contains(robot)) {
                    return ImmutableSceneTracking.copyOf(this).withLoop(true);
                } else {
                    return ImmutableSceneTracking.builder().from(this)
                            .addPreviousRobot(robot)
                            .build();
                }
            } else {
                return ImmutableSceneTracking.builder()
                        .previousCityMap(cityMap)
                        .addPreviousRobot(robot)
                        .build();
            }
        }
        // }}}
    }

    /**
     * Create initial scene tracking
     *
     * Difficulty: *
     * Hints:
     * Be sure to look at unit tests
     */
    public static SceneTracking fromInitialScene(final Scene scene) {
        // IMPLEMENT FUNC {{{
        return ImmutableSceneTracking.builder()
                .previousCityMap(scene.cityMap())
                .addPreviousRobot(scene.robot())
                .build();
        // }}}
    }
}

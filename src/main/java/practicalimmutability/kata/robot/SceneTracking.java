package practicalimmutability.kata.robot;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.With;

@Builder(toBuilder = true)
@With(AccessLevel.PRIVATE)
/**
 * State for <b>tracking of scene</b>
 *
 * @param previousCityMap Previous city map state
 * @param previousRobots Previous robot states
 * @param loop Looped scene
 */
public record SceneTracking(
        CityMap previousCityMap,
        Set<Robot> previousRobots,
        boolean loop) {

    /**
     * Consider a new scene and previous tracking state to detect a potential infinite loop
     *
     * <p>Difficulty: ***</p>
     * <p>Hints:</p>
     * <ul>
     *     <li>{@link Set#contains(Object)}</li>
     *     <li>Use equals methods as provided by Immutables</li>
     *     <li>Be sure to look at unit tests for more details</li>
     *     <li>Occurrence of previously observed robot state is not enough to detect loop,
     *     city map should also not have change meanwhile.</li>
     *     <li>Somehow, you should reset robot tracking when city map changes.</li>
     *     <li>A looped tracked scene should be kept unchanged.</li>
     * </ul>
     *
     * @param scene New scene to be considered
     * @return New tracking state
     */
    public SceneTracking track(final Scene scene) {
        return io.vavr.API.TODO();
    }

    /**
     * Create initial scene tracking
     *
     * Difficulty: *
     * Hints:
     * Be sure to look at unit tests
     */
    public static SceneTracking fromInitialScene(final Scene scene) {
        return io.vavr.API.TODO();
    }
}

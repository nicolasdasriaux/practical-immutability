package practicalimmutability.kata.robot;

import io.vavr.Tuple;
import io.vavr.collection.Iterator;
import io.vavr.control.Option;
import lombok.Builder;

import java.util.function.Function;

/**
 * <b>Tracked scene</b> holding the scene itself and its tracking
 * @param scene Current scene
 * @param tracking Current tracking of the scene
 */
@Builder
public record TrackedScene(
        Scene scene,
        SceneTracking tracking) {

    /**
     * Determine whether tracked scene is completed or not (dead robot, loop detected)
     * <p>Difficulty: *</p>
     */
    public boolean completed() {
        return io.vavr.API.TODO();
    }

    /**
     * Determine whether a loop was detected or not using tracking of this scene
     * <p>Difficulty: *</p>
     */
    public boolean loop() {
        return io.vavr.API.TODO();
    }

    /**
     * Determine next scene after the robot has acted and loop detection was performed
     * <p>Difficulty: **</p>
     * <p>Hints:</p>
     * <ul>
     *     <li>Use {@link Scene#next()}</li>
     *     <li>Use {@link SceneTracking#track(Scene)}</li>
     * </ul>
     */
    public TrackedScene next() {
        return io.vavr.API.TODO();
    }

    /**
     * Get an iterator over the successive tracked scenes including the completed scene
     *
     * <p>Difficulty: *</p>
     * <p>Hints:</p>
     * <ul>
     *     <li>Totally similar to {@link Scene#run()}</li>
     * </ul>
     *
     * <p>OR</p>
     *
     * <p>Difficulty: ***** (not for the faint of heart)</p>
     * <p>Hints:</p>
     *
     * <ul>
     *     <li>Read Vavr Javadoc carefully especially for {@link Iterator#unfoldRight(Object, Function)}</li>
     *     <li>
     *         Use {@link Iterator#unfoldRight(Object, Function)} to generate successive scenes starting from initial scene
     *         with {@code Option<TrackedScene>} as {@code T} (type for generator state)
     *         and {@code TrackedScene} as {@code U} (type for generated value)
     *     </li>
     *     <li>Use {@link Option#map(Function)}</li>
     *     <li>Use {@link Tuple#of(Object, Object)}</li>
     *     <li>Use {@link Option#of(Object)} and {@link Option#none()}</li>
     * </ul>
     */
    public Iterator<TrackedScene> run() {
        return io.vavr.API.TODO();
    }

    /**
     * Create tracked scene from a scene
     *
     * <p>Difficulty: *</p>
     * <p>Hints:</p>
     * <ul>
     *     <li>Be sure to initialize tracking</li>
     * </ul>
     */
    public static TrackedScene fromInitialScene(final Scene scene) {
        return io.vavr.API.TODO();
    }

    private static TrackedScene of(Scene scene, SceneTracking tracking) {
        return new TrackedScene(scene, tracking);
    }
}

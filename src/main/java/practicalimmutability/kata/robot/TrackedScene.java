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
        // IMPLEMENT FUNC {{{
        return scene().completed() || tracking().loop();
        // }}}
    }

    /**
     * Determine whether a loop was detected or not using tracking of this scene
     * <p>Difficulty: *</p>
     */
    public boolean loop() {
        // IMPLEMENT FUNC {{{
        return tracking().loop();
        // }}}
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
        // IMPLEMENT FUNC {{{
        final Scene currentScene = scene();
        final SceneTracking currentTracking = tracking();
        final Scene updatedScene = currentScene.next();
        final SceneTracking updatedTracking = currentTracking.track(updatedScene);
        return TrackedScene.of(updatedScene, updatedTracking);
        // }}}
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
        // IMPLEMENT FUNC {{{
        return Iterator.unfoldRight(Option.of(this), maybeCurrentScene -> {
            return maybeCurrentScene.map(scene -> {
                if (scene.completed()) {
                    return Tuple.of(scene, Option.none());
                } else {
                    return Tuple.of(scene, Option.of(scene.next()));
                }
            });
        });
        // }}}
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
        // IMPLEMENT FUNC {{{
        return TrackedScene.of(scene, SceneTracking.fromInitialScene(scene));
        // }}}
    }

    private static TrackedScene of(Scene scene, SceneTracking tracking) {
        return new TrackedScene(scene, tracking);
    }
}

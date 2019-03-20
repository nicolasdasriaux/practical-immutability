package practicalimmutability.kata.robot;

import io.vavr.Tuple;
import io.vavr.collection.Iterator;
import io.vavr.control.Option;
import org.immutables.value.Value;

import java.util.function.Function;

@Value.Immutable
public abstract class TrackedScene {
    /**
     * Current scene
     */
    @Value.Parameter
    public abstract Scene scene();

    /**
     * Current tracking of the scene
     */
    @Value.Parameter
    public abstract SceneTracking tracking();

    /**
     * Determine whether tracked scene is completed or not (dead robot, loop detected)
     *
     * Difficulty: *
     */
    public boolean completed() {
        return scene().completed() || tracking().loop();
    }

    /**
     * Determine whether a loop was detected or not using tracking of this scene
     *
     * Difficulty: *
     */
    public boolean loop() {
        return tracking().loop();
    }

    /**
     * Determine next scene after the robot has acted and loop detection was performed
     *
     * Difficulty: **
     * Hints:
     * Use {@link Scene#next()}
     * Use {@link SceneTracking#track(Scene)}
     */
    public TrackedScene next() {
        final Scene currentScene = scene();
        final SceneTracking currentTracking = tracking();
        final Scene updatedScene = currentScene.next();
        final SceneTracking updatedTracking = currentTracking.track(updatedScene);
        return ImmutableTrackedScene.of(updatedScene, updatedTracking);
    }

    /**
     * Get an iterator over the successive tracked scenes including the completed scene
     *
     * Difficulty: *
     * Hints:
     * Totally similar to {@link Scene#run()}
     *
     * Or
     *
     * Difficulty: ***** (not for the faint of heart)
     * Hints:
     * Read Vavr Javadoc carefully especially for {@link Iterator#unfoldRight(Object, Function)}
     *
     * Use {@link Iterator#unfoldRight(Object, Function)} to generate successive scenes starting from initial scene
     * with Option<TrackedScene> as T (type for generator state)
     * and TrackedScene a U (type for generated value)
     * Use {@link Option#map(Function)}
     * Use {@link Tuple#of(Object, Object)
     * Use {@link Option#of(Object)} and {@link Option#none()}
     */
    public Iterator<TrackedScene> run() {
        return Iterator.unfoldRight(Option.of(this), maybeCurrentScene -> {
            return maybeCurrentScene.map(scene -> {
                if (scene.completed()) {
                    return Tuple.of(scene, Option.none());
                } else {
                    return Tuple.of(scene, Option.of(scene.next()));
                }
            });
        });
    }

    /**
     * Create tracked scene from a scene
     *
     * Difficulty: *
     * Hints:
     * Be sure to initialize tracking
     */
    public static TrackedScene fromInitialScene(final Scene scene) {
        return ImmutableTrackedScene.of(scene, SceneTracking.fromInitialScene(scene));
    }
}

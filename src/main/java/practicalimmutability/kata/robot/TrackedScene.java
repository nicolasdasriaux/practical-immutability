package practicalimmutability.kata.robot;

import io.vavr.Tuple2;
import io.vavr.collection.Iterator;
import org.immutables.value.Value;

import java.util.function.Function;
import java.util.function.Predicate;

@Value.Immutable
public abstract class TrackedScene {
    @Value.Parameter
    public abstract Scene scene();

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
     * Get an iterator over the successive scenes including the completed scene
     *
     * Difficulty: ****
     * Hints:
     * {@link Iterator#iterate(Object, Function)}
     * {@link Iterator#span(Predicate)}}
     * {@link Tuple2#_1}
     * {@link Tuple2#_2}
     */
    public Iterator<TrackedScene> run() {
        final Tuple2<Iterator<TrackedScene>, Iterator<TrackedScene>> prefixAndReminder =
                Iterator.iterate(this, TrackedScene::next).span(trackedScene -> !trackedScene.completed());

        return Iterator.concat(prefixAndReminder._1, prefixAndReminder._2.take(1));
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

package practicalimmutability.presentation.adt.matcher;

import org.immutables.value.Value;
import practicalimmutability.presentation.adt.Direction;
import practicalimmutability.presentation.adt.Position;

public interface Action {
    <T, R> R match(ActionMatcher<T, R> matcher, T target);

    @Value.Immutable(singleton = true)
    abstract class Sleep implements Action {
        public static Sleep of() { return ImmutableSleep.of(); }

        @Override
        public <T, R> R match(final ActionMatcher<T, R> matcher, final T target) {
            return matcher.onSleep().apply(this, target);
        }
    }

    @Value.Immutable
    abstract class Walk implements Action {
        @Value.Parameter public abstract Direction direction();
        public static Walk of(final Direction direction) { return ImmutableWalk.of(direction); }

        @Override
        public <T, R> R match(final ActionMatcher<T, R> matcher, final T target) {
            return matcher.onWalk().apply(this, target);
        }
    }

    @Value.Immutable
    abstract class Jump implements Action {
        @Value.Parameter public abstract Position position();
        public static Jump of(final Position position) { return ImmutableJump.of(position); }

        @Override
        public <T, R> R match(final ActionMatcher<T, R> matcher, final T target) {
            return matcher.onJump().apply(this, target);
        }
    }
}

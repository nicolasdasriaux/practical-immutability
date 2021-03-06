package practicalimmutability.presentation.adt.matcher;

import org.immutables.value.Value;
import practicalimmutability.presentation.adt.Direction;
import practicalimmutability.presentation.adt.Position;

public interface Action {
    <R> R match(ActionMatcher<R> matcher);

    @Value.Immutable(singleton = true)
    abstract class Sleep implements Action {
        @Override
        public <R> R match(final ActionMatcher<R> matcher) {
            return matcher.onSleep().apply(this);
        }

        public static Sleep of() { return ImmutableSleep.of(); }
    }

    @Value.Immutable
    abstract class Walk implements Action {
        @Override
        public <R> R match(final ActionMatcher<R> matcher) {
            return matcher.onWalk().apply(this);
        }

        @Value.Parameter public abstract Direction direction();
        public static Walk of(final Direction direction) { return ImmutableWalk.of(direction); }
    }

    @Value.Immutable
    abstract class Jump implements Action {
        @Override
        public <R> R match(final ActionMatcher<R> matcher) {
            return matcher.onJump().apply(this);
        }

        @Value.Parameter public abstract Position position();
        public static Jump of(final Position position) { return ImmutableJump.of(position); }
    }
}

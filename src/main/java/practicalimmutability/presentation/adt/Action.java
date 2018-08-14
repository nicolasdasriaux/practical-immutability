package practicalimmutability.presentation.adt;

import org.immutables.value.Value;

public interface Action {
    @Value.Immutable(singleton = true)
    abstract class Sleep implements Action {
        public static Sleep of() { return ImmutableSleep.of(); }
    }
    
    @Value.Immutable
    abstract class Walk implements Action {
        @Value.Parameter public abstract Direction direction();
        public static Walk of(final Direction direction) { return ImmutableWalk.of(direction); }
    }
    
    @Value.Immutable
    abstract class Jump implements Action {
        @Value.Parameter public abstract Position position();
        public static Jump of(final Position position) { return ImmutableJump.of(position); }
    }
}

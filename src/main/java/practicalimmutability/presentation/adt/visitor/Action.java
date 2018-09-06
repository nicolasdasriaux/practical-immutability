package practicalimmutability.presentation.adt.visitor;

import org.immutables.value.Value;
import practicalimmutability.presentation.adt.Direction;
import practicalimmutability.presentation.adt.Position;

public interface Action {
    <R, T> R accept(ActionVisitor<T, R> visitor, T t);

    @Value.Immutable(singleton = true)
    abstract class Sleep implements Action {
        @Override
        public <R, T> R accept(final ActionVisitor<T, R> visitor, final T t) {
            return visitor.visitSleep(this, t);
        }

        public static Sleep of() { return ImmutableSleep.of(); }
    }

    @Value.Immutable
    abstract class Walk implements Action {
        @Override
        public <R, T> R accept(final ActionVisitor<T, R> visitor, final T t) {
            return visitor.visitWalk(this, t);
        }

        @Value.Parameter public abstract Direction direction();
        public static Walk of(final Direction direction) { return ImmutableWalk.of(direction); }
    }

    @Value.Immutable
    abstract class Jump implements Action {
        @Override
        public <R, T> R accept(final ActionVisitor<T, R> visitor, final T t) {
            return visitor.visitJump(this, t);
        }

        @Value.Parameter public abstract Position position();
        public static Jump of(final Position position) { return ImmutableJump.of(position); }
    }
}

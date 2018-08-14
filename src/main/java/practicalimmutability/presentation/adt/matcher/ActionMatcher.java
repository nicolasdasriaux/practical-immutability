package practicalimmutability.presentation.adt.matcher;

import org.immutables.value.Value;
import practicalimmutability.presentation.adt.matcher.Action.Jump;
import practicalimmutability.presentation.adt.matcher.Action.Sleep;
import practicalimmutability.presentation.adt.matcher.Action.Walk;

import java.util.function.BiFunction;

@Value.Immutable()
@Value.Style(stagedBuilder = true)
public abstract class ActionMatcher<T, R> {
    public abstract BiFunction<Sleep, T, R> onSleep();
    public abstract BiFunction<Walk, T, R> onWalk();
    public abstract BiFunction<Jump, T, R> onJump();
}

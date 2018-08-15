package practicalimmutability.presentation.adt.matcher;

import org.immutables.value.Value;
import practicalimmutability.presentation.adt.matcher.Action.Jump;
import practicalimmutability.presentation.adt.matcher.Action.Sleep;
import practicalimmutability.presentation.adt.matcher.Action.Walk;

import java.util.function.Function;

@Value.Immutable
@Value.Style(stagedBuilder = true)
public abstract class ActionMatcher<R> {
    public abstract Function<Sleep, R> onSleep();
    public abstract Function<Walk, R> onWalk();
    public abstract Function<Jump, R> onJump();
}

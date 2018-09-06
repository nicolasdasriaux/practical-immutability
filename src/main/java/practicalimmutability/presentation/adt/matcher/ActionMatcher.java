package practicalimmutability.presentation.adt.matcher;

import org.immutables.value.Value;
import practicalimmutability.presentation.adt.matcher.Action.Jump;
import practicalimmutability.presentation.adt.matcher.Action.Sleep;
import practicalimmutability.presentation.adt.matcher.Action.Walk;

import java.util.function.Function;

@Value.Immutable
@Value.Style(stagedBuilder = true)
public interface ActionMatcher<R> {
    Function<Sleep, R> onSleep();
    Function<Walk, R> onWalk();
    Function<Jump, R> onJump();
}

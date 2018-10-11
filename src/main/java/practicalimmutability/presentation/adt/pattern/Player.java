package practicalimmutability.presentation.adt.pattern;

import org.immutables.value.Value;
import practicalimmutability.presentation.adt.Action;
import practicalimmutability.presentation.adt.Position;

import static io.vavr.API.*;
import static practicalimmutability.presentation.adt.ActionPatterns.*;

@Value.Immutable
public abstract class Player {
    @Value.Parameter
    public abstract Position position();

    public Player act(final Action action) {
        return Match(action).of(
                Case($Sleep, () -> this),
                Case($Walk($()), direction -> ImmutablePlayer.of(position().move(direction))),
                Case($Jump($()), position -> ImmutablePlayer.of(position))
        );
    }

    public static Player of(final Position position) {
        return ImmutablePlayer.of(position);
    }
}

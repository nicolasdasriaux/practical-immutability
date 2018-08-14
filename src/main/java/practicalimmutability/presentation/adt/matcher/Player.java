package practicalimmutability.presentation.adt.matcher;

import org.immutables.value.Value;
import practicalimmutability.presentation.adt.Position;

import static practicalimmutability.presentation.adt.matcher.Action.*;

@Value.Immutable
public abstract class Player {
    @Value.Parameter
    public abstract Position position();

    public Player act(final Action action) {
        if (action instanceof Sleep) {
            return this;
        } else if (action instanceof Walk) {
            final Walk walk = (Walk) action;
            return ImmutablePlayer.of(position().move(walk.direction()));
        } else if (action instanceof Jump) {
            final Jump jump = (Jump) action;
            return ImmutablePlayer.of(jump.position());
        } else {
            throw new IllegalArgumentException(String.format("Unknown Action (%s)", action));
        }
    }

    public static Player of(final Position position) {
        return ImmutablePlayer.of(position);
    }
}

package practicalimmutability.presentation.adt;

import org.immutables.value.Value;

import static practicalimmutability.presentation.adt.Action.*;

@Value.Immutable
public abstract class Player {
    @Value.Parameter
    public abstract Position position();

    public Player act(final Action action) {
        if (action instanceof Sleep) {
            return this;
        } else if (action instanceof Walk) {
            final Walk walk = (Walk) action;
            return Player.of(this.position().move(walk.direction()));
        } else if (action instanceof Jump) {
            final Jump jump = (Jump) action;
            return Player.of(jump.position());
        } else {
            throw new IllegalArgumentException(String.format("Unknown Action (%s)", action));
        }
    }

    public static Player of(final Position position) {
        return ImmutablePlayer.of(position);
    }
}

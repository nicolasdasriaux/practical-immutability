package practicalimmutability.presentation.adt.matcher;

import org.immutables.value.Value;
import practicalimmutability.presentation.adt.Position;

import static practicalimmutability.presentation.adt.matcher.Action.*;

@Value.Immutable
public abstract class Player {
    @Value.Parameter
    public abstract Position position();

    private final ActionMatcher<Player, Player> actionMatcher = ImmutableActionMatcher.<Player, Player>builder()
            .onSleep((sleep, player) -> player)
            .onWalk((walk, player) -> ImmutablePlayer.of(player.position().move(walk.direction())))
            .onJump((jump, player) -> ImmutablePlayer.of(jump.position()))
            .build();

    public Player act(final Action action) {
        return action.match(actionMatcher, this);
    }

    public static Player of(final Position position) {
        return ImmutablePlayer.of(position);
    }
}

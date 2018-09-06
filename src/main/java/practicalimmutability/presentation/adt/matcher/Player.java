package practicalimmutability.presentation.adt.matcher;

import org.immutables.value.Value;
import practicalimmutability.presentation.adt.Position;

import java.util.function.Function;

@Value.Immutable
public abstract class Player {
    @Value.Parameter
    public abstract Position position();

    private static final ActionMatcher<Function<Player, Player>> ACT_MATCHER =
            ImmutableActionMatcher.<Function<Player, Player>>builder()
                    .onSleep(sleep -> player -> player)
                    .onWalk(walk -> player ->
                            Player.of(player.position().move(walk.direction()))
                    )
                    .onJump(jump -> player ->
                            Player.of(jump.position())
                    )
                    .build();

    public Player act(final Action action) {
        return action.match(ACT_MATCHER).apply(this);
    }

    public static Player of(final Position position) {
        return ImmutablePlayer.of(position);
    }
}

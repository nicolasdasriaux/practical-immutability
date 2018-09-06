package practicalimmutability.presentation.adt.visitor;

import org.immutables.value.Value;
import practicalimmutability.presentation.adt.Position;
import practicalimmutability.presentation.adt.visitor.Action.Jump;
import practicalimmutability.presentation.adt.visitor.Action.Sleep;
import practicalimmutability.presentation.adt.visitor.Action.Walk;

@Value.Immutable
public abstract class Player {
    @Value.Parameter
    public abstract Position position();

    private static final ActionVisitor<Player, Player> ACT_VISITOR = new ActionVisitor<Player, Player>() {
        @Override
        public Player visitSleep(final Sleep sleep, final Player player) {
            return player;
        }

        @Override
        public Player visitWalk(final Walk walk, final Player player) {
            return Player.of(player.position().move(walk.direction()));
        }

        @Override
        public Player visitJump(final Jump jump, final Player player) {
            return Player.of(jump.position());
        }
    };

    public Player act(final Action action) {
        return action.accept(ACT_VISITOR, this);
    }

    public static Player of(final Position position) {
        return ImmutablePlayer.of(position);
    }
}

package practicalimmutability.presentation.adt.pattern;

import io.vavr.collection.List;
import io.vavr.collection.Seq;
import practicalimmutability.presentation.adt.Action;
import practicalimmutability.presentation.adt.Action.Jump;
import practicalimmutability.presentation.adt.Action.Sleep;
import practicalimmutability.presentation.adt.Action.Walk;
import practicalimmutability.presentation.adt.Position;

import static practicalimmutability.presentation.adt.Direction.Right;
import static practicalimmutability.presentation.adt.Direction.Up;

public class GameApp {
    public static void main(final String[] args) {
        final Player initialPlayer = Player.of(Position.of(1, 1));

        final Seq<Action> actions = List.of(
                Jump.of(Position.of(5, 8)),
                Walk.of(Up),
                Sleep.of(),
                Walk.of(Right)
        );

        final Seq<Player> players = actions.scanLeft(initialPlayer, Player::act);
        System.out.println(players);
    }
}

package practicalimmutability.presentation.adt.matcher;

import io.vavr.collection.List;
import practicalimmutability.presentation.adt.Position;
import practicalimmutability.presentation.adt.matcher.Action.Jump;
import practicalimmutability.presentation.adt.matcher.Action.Sleep;
import practicalimmutability.presentation.adt.matcher.Action.Walk;

import static practicalimmutability.presentation.adt.Direction.Right;
import static practicalimmutability.presentation.adt.Direction.Up;

public class GameApp {
    public static void main(final String[] args) {
        final Player initialPlayer = Player.of(Position.of(1, 1));

        final List<Action> actions = List.of(
                Jump.of(Position.of(5, 8)),
                Walk.of(Up),
                Sleep.of(),
                Walk.of(Right)
        );

        final Player finalPLayer = actions.foldLeft(initialPlayer, Player::act);
        System.out.println(finalPLayer);
    }
}

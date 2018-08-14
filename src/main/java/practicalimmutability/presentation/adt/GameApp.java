package practicalimmutability.presentation.adt;

import io.vavr.collection.List;
import practicalimmutability.presentation.adt.Action.Jump;

import static practicalimmutability.presentation.adt.Action.Sleep;
import static practicalimmutability.presentation.adt.Action.Walk;
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

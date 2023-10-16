package practicalimmutability.presentation.adt;

import io.vavr.collection.List;
import io.vavr.collection.Seq;

import static practicalimmutability.presentation.adt.Action.*;
import static practicalimmutability.presentation.adt.Direction.*;

public class GameApp {
    public static void main(String[] args) {
        final Player initialPlayer = Player.of(Position.of(1, 1));

        final Seq<Action> actions = List.of(
                Jump.of(Position.of(5, 8)),
                Walk.of(NORTH),
                Sleep.of(),
                Walk.of(EAST)
        );

        final Player finalPLayer = actions.foldLeft(initialPlayer, Player::act);
        System.out.println(finalPLayer);

        final Seq<Player> players = actions.scanLeft(initialPlayer, Player::act);
        System.out.println(players);
    }
}

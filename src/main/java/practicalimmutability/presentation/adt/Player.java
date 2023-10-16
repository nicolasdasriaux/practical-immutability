package practicalimmutability.presentation.adt;

import static practicalimmutability.presentation.adt.Action.*;

public record Player(Position position) {
    public Player act(Action action) {
        return switch (action) {
            case Sleep() -> this;
            case Walk(Direction direction) -> Player.of(position.move(direction));
            case Jump(Position position) -> Player.of(position);
        };
    }

    public static Player of(Position position) {
        return new Player(position);
    }
}

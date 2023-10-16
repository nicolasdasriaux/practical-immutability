package practicalimmutability.presentation.adt;

import lombok.With;

@With
public record Position(int x, int y) {
    public Position move(Direction direction) {
        return switch (direction) {
            case NORTH -> this.withY(y() - 1);
            case SOUTH -> this.withY(y() + 1);
            case WEST -> this.withX(x() - 1);
            case EAST -> this.withX(x() + 1);
        };
    }

    public static Position of(int x, int y) {
        return new Position(x, y);
    }
}

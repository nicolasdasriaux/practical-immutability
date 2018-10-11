package practicalimmutability.kata.robot;

import org.immutables.value.Value;

@Value.Immutable
public abstract class Position {
    /**
     * Column coordinate (x)
     */
    @Value.Parameter
    public abstract int x();

    /**
     * Row coordinate (y)
     */
    @Value.Parameter
    public abstract int y();

    /**
     * Get position when moving to a direction
     *
     * Difficulty: *
     */
    public Position move(final Direction direction) {
        switch(direction) {
            case North: return ImmutablePosition.copyOf(this).withY(y() - 1);
            case South: return ImmutablePosition.copyOf(this).withY(y() + 1);
            case West: return ImmutablePosition.copyOf(this).withX(x() - 1);
            case East: return ImmutablePosition.copyOf(this).withX(x() + 1);
            default: throw new IllegalArgumentException(String.format("Unknown Direction (%s)", direction));
        }
    }

    /**
     * Create a position
     *
     * Difficulty: *
     */
    public static Position of(final int x, final  int y) {
        return ImmutablePosition.of(x, y);
    }
}

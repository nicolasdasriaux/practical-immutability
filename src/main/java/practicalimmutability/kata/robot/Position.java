package practicalimmutability.kata.robot;

import lombok.AccessLevel;
import lombok.With;

/**
 * <b>Position</b> on the map
 *
 * @param x Column coordinate (x)
 * @param y Row coordinate (y)
 */
@With(AccessLevel.PRIVATE)
public record Position(int x, int y) {
    /**
     * Get position when moving to a direction
     * <p>Difficulty: *</p>
     */
    public Position move(final Direction direction) {
        return io.vavr.API.TODO();
    }

    /**
     * Create a position
     * <p>Difficulty: *</p>
     */
    public static Position of(final int x, final int y) {
        return io.vavr.API.TODO();
    }
}

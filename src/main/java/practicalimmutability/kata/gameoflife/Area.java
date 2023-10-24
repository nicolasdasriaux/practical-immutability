package practicalimmutability.kata.gameoflife;

import practicalimmutability.presentation.Preconditions;

/**
 * Rectangular area defined by a top left corner and a bottom right corner.
 * Both <b>corners</b> are considered to be <b>inside the area</b>.
 *
 * @param p1 Top left corner
 * @param p2 Bottom right corner
 */
public record Area(Position p1, Position p2) {
    public Area {
        Preconditions.require(() -> p1.x() <= p2.x() && p1.y() <= p2.y(), () -> "Invalid area");
    }

    /**
     * Width of the area
     * @return Width
     */
    public int width() {
        return io.vavr.API.TODO();
    }

    /**
     * Height of the area
     * @return Height
     */
    public int height() {
        return io.vavr.API.TODO();
    }

    /**
     * Determine if given position is inside the area
     * @param position Position
     * @return {@code true} if position is inside the area
     */
    public boolean contains(Position position) {
        return io.vavr.API.TODO();
    }

    /**
     * Normalize given position relatively to top left corner of the area
     * @param position Position
     * @return Normalized position
     */
    public Position normalize(Position position) {
        return io.vavr.API.TODO();
    }

    public static Area of(Position p1, Position p2) {
        return new Area(p1, p2);
    }
}

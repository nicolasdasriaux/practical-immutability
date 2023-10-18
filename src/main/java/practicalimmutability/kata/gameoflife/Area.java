package practicalimmutability.kata.gameoflife;

import practicalimmutability.presentation.Preconditions;

public record Area(Position p1, Position p2) {
    public Area {
        Preconditions.require(() -> p1.x() <= p2.x() && p1.y() <= p2.y(), () -> "Invalid area");
    }

    public int width() {
        // IMPLEMENT FUNC {{{
        return p2.x() - p1.x() + 1;
        // }}}
    }

    public int height() {
        // IMPLEMENT FUNC {{{
        return p2.y() - p1.y() + 1;
        // }}}
    }

    public boolean contains(Position position) {
        // IMPLEMENT FUNC {{{
        return p1().x() <= position.x() && position.x() <= p2().x() &&
                p1().y() <= position.y() && position.y() <= p2().y();
        // }}}
    }

    public Position normalize(Position p) {
        // IMPLEMENT FUNC {{{
        return Position.of(p.x() - p1.x(), p.y() - p1.y());
        // }}}
    }

    public static Area of(Position p1, Position p2) {
        // IMPLEMENT FUNC {{{
        return new Area(p1, p2);
        // }}}
    }
}

package practicalimmutability.kata.gameoflife;

import practicalimmutability.presentation.Preconditions;

public record Area(Position p1, Position p2) {
    public Area {
        if (!(p1.x() <= p2.x() && p1.y() <= p2.y())) {
            throw new IllegalStateException("Invalid area");
        }
    }

    public  int width() {
        return p2.x() - p1.x() + 1;
    }

    public int height() {
        return p2.y() - p1.y() + 1;
    }

    public boolean contains(Position position) {
       return p1().x() <= position.x() && position.x() <= p2().x() &&
               p1().y() <= position.y() && position.y() <= p2().y();
    }

    public Position normalize(Position p) {
        return Position.of(p.x() - p1.x(), p.y() - p1.y());
    }

    public static Area of(Position p1, Position p2) {
        return new Area(p1, p2);
    }
}

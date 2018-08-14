package practicalimmutability.kata.robot;

import org.immutables.value.Value;

public interface Tile {
    @Value.Immutable(singleton = true)
    abstract class Empty implements Tile {
        public static Empty of() {
            return ImmutableEmpty.of();
        }
    }

    @Value.Immutable(singleton = true)
    abstract class Start implements Tile {
        public static Start of() {
            return ImmutableStart.of();
        }
    }

    @Value.Immutable(singleton = true)
    abstract class Booth implements Tile {
        public static Booth of() {
            return ImmutableBooth.of();
        }
    }

    @Value.Immutable(singleton = true)
    abstract class Obstacle implements Tile {
        public static Obstacle of() {
            return ImmutableObstacle.of();
        }
    }

    @Value.Immutable(singleton = true)
    abstract class BreakableObstacle implements Tile {
        public static BreakableObstacle of() {
            return ImmutableBreakableObstacle.of();
        }
    }

    @Value.Immutable
    abstract class DirectionModifier implements Tile {
        @Value.Parameter
        public abstract Direction direction();

        public static DirectionModifier of(final Direction direction) {
            return ImmutableDirectionModifier.of(direction);
        }
    }

    @Value.Immutable(singleton = true)
    abstract class CircuitInverter implements Tile {
        public static CircuitInverter of() {
            return ImmutableCircuitInverter.of();
        }
    }

    @Value.Immutable(singleton = true)
    abstract class Beer implements Tile {
        public static Beer of() {
            return ImmutableBeer.of();
        }
    }

    @Value.Immutable(singleton = true)
    abstract class Teleporter implements Tile {
        public static Teleporter of() {
            return ImmutableTeleporter.of();
        }
    }

    default char toCode() {
        if (this instanceof Empty) {
            return ' ';
        } else if (this instanceof Start) {
            return '@';
        } else if (this instanceof Booth) {
            return '$';
        } else if (this instanceof Obstacle) {
            return '#';
        } else if (this instanceof BreakableObstacle) {
            return 'X';
        } else if (this instanceof DirectionModifier) {
            final DirectionModifier directionModifier = (DirectionModifier) this;
            return directionModifier.direction().toCode();
        } else if (this instanceof CircuitInverter) {
            return 'I';
        } else if (this instanceof Beer) {
            return 'B';
        } else if (this instanceof Teleporter) {
            return 'T';
        } else {
            throw new IllegalArgumentException(String.format("Unknown tile (%s)", this));
        }
    }

    static Tile fromCode(final char code) {
        switch (code) {
            case ' ': return Empty.of();
            case '@': return Start.of();
            case '$': return Booth.of();
            case '#': return Obstacle.of();
            case 'X': return BreakableObstacle.of();
            case 'N': case 'S': case 'E': case 'W': return DirectionModifier.of(Direction.fromCode(code));
            case 'I': return CircuitInverter.of();
            case 'B': return Beer.of();
            case 'T': return Teleporter.of();
            default: throw new IllegalArgumentException(String.format("Unknown tile code (%s)", code));
        }
    }
}

package fr.carbonit.bender;

import org.immutables.value.Value;

public interface Tile {
    @Value.Immutable(singleton = true)
    class Empty implements Tile {
        public static Empty of() {
            return ImmutableEmpty.of();
        }
    }

    @Value.Immutable(singleton = true)
    class Start implements Tile {
        public static Start of() {
            return ImmutableStart.of();
        }
    }

    @Value.Immutable(singleton = true)
    class Booth implements Tile {
        public static Booth of() {
            return ImmutableBooth.of();
        }
    }

    @Value.Immutable(singleton = true)
    class Obstacle implements Tile {
        public static Obstacle of() {
            return ImmutableObstacle.of();
        }
    }

    @Value.Immutable(singleton = true)
    class BreakableObstacle implements Tile {
        public static BreakableObstacle of() {
            return ImmutableBreakableObstacle.of();
        }
    }

    @Value.Immutable
    abstract class PathModifier implements Tile {
        @Value.Parameter
        public abstract Direction direction();

        public static PathModifier of(final Direction direction) {
            return ImmutablePathModifier.of(direction);
        }
    }

    @Value.Immutable(singleton = true)
    class CircuitInverter implements Tile {
        public static CircuitInverter of() {
            return ImmutableCircuitInverter.of();
        }
    }

    @Value.Immutable(singleton = true)
    class Beer implements Tile {
        public static Beer of() {
            return ImmutableBeer.of();
        }
    }

    @Value.Immutable(singleton = true)
    class Teleporter implements Tile {
        public static Teleporter of() {
            return ImmutableTeleporter.of();
        }
    }

    static Tile fromCode(final char code) {
        switch (code) {
            case ' ': return Empty.of();
            case '@': return Start.of();
            case '$': return Booth.of();
            case '#': return Obstacle.of();
            case 'X': return BreakableObstacle.of();
            case 'N': case 'S': case 'E': case 'W': return PathModifier.of(Direction.fromCode(code));
            case 'I': return CircuitInverter.of();
            case 'B': return Beer.of();
            case 'T': return Teleporter.of();
            default: throw new AssertionError(String.format("Unknown tile code (%s)", code));
        }
    }
}

package fr.carbonit.bender;

import fr.carbonit.bender.Tile.BreakableObstacle;
import fr.carbonit.bender.Tile.Obstacle;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import org.immutables.value.Value;

import static fr.carbonit.bender.Direction.*;

@Value.Immutable
public abstract class Robot {
    public abstract Position position();
    public abstract Direction direction();
    public abstract boolean breaker();
    public abstract boolean inverted();
    public abstract boolean dead();

    public Robot changeDirection(final Direction direction) {
        return ImmutableRobot.copyOf(this).withDirection(direction);
    }

    public Robot toggleBreaker() {
        return ImmutableRobot.copyOf(this).withBreaker(!breaker());
    }

    public Robot invert() {
        return ImmutableRobot.copyOf(this).withInverted(!inverted());
    }

    public Robot die() {
        return ImmutableRobot.copyOf(this).withDead(true);
    }

    private boolean obstacleInDirection(final Direction direction, final CityMap cityMap) {
        return obstacle(cityMap.tile(position().move(direction)));
    }

    private boolean obstacle(final Tile tile) {
        if (tile instanceof Obstacle) {
            return true;
        } else if (tile instanceof BreakableObstacle) {
            return !breaker();
        } else {
            return false;
        }
    }

    public Robot move(final CityMap cityMap) {
        final Direction newDirection;

        if (!obstacleInDirection(direction(), cityMap)) {
            newDirection = direction();
        } else {
            newDirection = priorities().find(direction -> !obstacleInDirection(direction, cityMap)).get();
        }

        return ImmutableRobot.builder().from(this)
                .position(position().move(newDirection))
                .direction(newDirection)
                .build();
    }

    public Seq<Direction> priorities() {
        if(inverted()) {
            return PRIORITIES.reverse();
        } else {
            return PRIORITIES;
        }
    }
    public Robot useTeleporter(final CityMap cityMap) {
        final Position outPosition = cityMap.teleporterOutPosition(position());
        return ImmutableRobot.copyOf(this).withPosition(outPosition);
    }

    public static Robot fromStart(final Position position) {
        return ImmutableRobot.builder()
                .position(position)
                .direction(South)
                .breaker(false)
                .inverted(false)
                .dead(false)
                .build();
    }

    public static final Seq<Direction> PRIORITIES = List.of(South, East, North, West);
}

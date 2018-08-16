package practicalimmutability.kata.robot;

import io.vavr.collection.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import practicalimmutability.kata.common.TODO;

import static org.assertj.core.api.Assertions.assertThat;
import static practicalimmutability.kata.robot.Direction.*;

class RobotTest {
    @ParameterizedTest
    @EnumSource(Direction.class)
    void changeDirection(final Direction direction) {
        final Robot southDirectedRobot = ImmutableRobot.builder()
                .position(Position.of(2, 4))
                .direction(South)
                .breaker(false)
                .inverted(false)
                .dead(false)
                .build();

        final Robot redirectedRobot = ImmutableRobot.copyOf(southDirectedRobot).withDirection(direction);

        assertThat(southDirectedRobot.changeDirection(direction)).isEqualTo(redirectedRobot);
    }

    @Test
    void toggleBreaker() {
        final Robot nonBreakerRobot = ImmutableRobot.builder()
                .position(Position.of(2, 4))
                .direction(South)
                .breaker(false)
                .inverted(false)
                .dead(false)
                .build();

        final Robot breakerRobot = ImmutableRobot.copyOf(nonBreakerRobot).withBreaker(true);

        assertThat(nonBreakerRobot.toggleBreaker()).isEqualTo(breakerRobot);
        assertThat(nonBreakerRobot.toggleBreaker().toggleBreaker()).isEqualTo(nonBreakerRobot);
    }

    @Test
    void invert() {
        final Robot nonInvertedRobot = ImmutableRobot.builder()
                .position(Position.of(2, 4))
                .direction(South)
                .breaker(false)
                .inverted(false)
                .dead(false)
                .build();

        final Robot invertedRobot = ImmutableRobot.copyOf(nonInvertedRobot).withInverted(true);

        assertThat(nonInvertedRobot.invert()).isEqualTo(invertedRobot);
        assertThat(nonInvertedRobot.invert().invert()).isEqualTo(nonInvertedRobot);
    }

    @Test
    void die() {
        final Robot livingRobot = ImmutableRobot.builder()
                .position(Position.of(2, 4))
                .direction(South)
                .breaker(false)
                .inverted(false)
                .dead(false)
                .build();

        final Robot deadRobot = ImmutableRobot.copyOf(livingRobot).withDead(true);

        assertThat(livingRobot.die()).isEqualTo(deadRobot);
    }

    @Test
    @Disabled
    void move() {
        TODO.IMPLEMENT();
    }

    @Test
    void priorities() {
        final Robot nonInvertedRobot = ImmutableRobot.builder()
                .position(Position.of(2, 4))
                .direction(South)
                .breaker(false)
                .inverted(false)
                .dead(false)
                .build();

        assertThat(nonInvertedRobot.priorities()).isEqualTo(List.of(South, East, North, West));
        assertThat(nonInvertedRobot.invert().priorities()).isEqualTo(List.of(South, East, North, West).reverse());
    }

    @Test
    void triggerTeleporter() {
        final CityMap cityMap = CityMap.fromLines(
                // @formatter:off
               //01234567
                "########", // 0
                "#      #", // 1
                "# @    #", // 2
                "#      #", // 3
                "# T    #", // 4
                "#      #", // 5
                "#    T #", // 6
                "#      #", // 7
                "#    $ #", // 8
                "#      #", // 9
                "########"  // 10
                // @formatter:on
        );

        final Robot inTeleporterRobot = ImmutableRobot.builder()
                .position(Position.of(2, 4))
                .direction(South)
                .breaker(false)
                .inverted(false)
                .dead(false)
                .build();

        final ImmutableRobot outTeleporterRobot = ImmutableRobot.builder()
                .position(Position.of(5, 6))
                .direction(South)
                .breaker(false)
                .inverted(false)
                .dead(false)
                .build();

        assertThat(inTeleporterRobot.triggerTeleporter(cityMap)).isEqualTo(outTeleporterRobot);
    }

    @Test
    void fromStart() {
        final Robot robot = Robot.fromStart(Position.of(3, 4));

        final ImmutableRobot expectedRobot = ImmutableRobot.builder()
                .position(Position.of(3, 4))
                .direction(South)
                .breaker(false)
                .inverted(false)
                .dead(false)
                .build();

        assertThat(robot).isEqualTo(expectedRobot);
    }
}

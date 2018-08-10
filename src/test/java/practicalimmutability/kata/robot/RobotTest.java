package practicalimmutability.kata.robot;

import io.vavr.collection.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import practicalimmutability.kata.common.TODO;

import static org.assertj.core.api.Assertions.assertThat;
import static practicalimmutability.kata.robot.Direction.*;

class RobotTest {
    @Test
    void changeDirection() {
        final Robot northDirectRobot = Robot.fromStart(Position.of(1, 1));
        assertThat(northDirectRobot.direction()).isEqualTo(South);
        assertThat(northDirectRobot.changeDirection(East).direction()).isEqualTo(East);
    }

    @Test
    void toggleBreaker() {
        final Robot nonBreakerRobot = Robot.fromStart(Position.of(1, 1));
        assertThat(nonBreakerRobot.breaker()).isFalse();
        assertThat(nonBreakerRobot.toggleBreaker().breaker()).isTrue();
        assertThat(nonBreakerRobot.toggleBreaker().toggleBreaker().breaker()).isFalse();
    }

    @Test
    void invert() {
        final Robot nonInvertedRobot = Robot.fromStart(Position.of(1, 1));
        assertThat(nonInvertedRobot.inverted()).isFalse();
        assertThat(nonInvertedRobot.invert().inverted()).isTrue();
        assertThat(nonInvertedRobot.invert().invert().inverted()).isFalse();
    }

    @Test
    void die() {
        final Robot livingRobot = Robot.fromStart(Position.of(1, 1));
        assertThat(livingRobot.dead()).isFalse();
        assertThat(livingRobot.die().dead()).isTrue();
    }

    @Test
    @Disabled
    void move() {
        TODO.IMPLEMENT();
    }

    @Test
    void priorities() {
        final Robot nonInvertedRobot = Robot.fromStart(Position.of(1, 1));
        assertThat(nonInvertedRobot.priorities()).isEqualTo(List.of(South, East, North, West));
        assertThat(nonInvertedRobot.invert().priorities()).isEqualTo(List.of(South, East, North, West).reverse());
    }

    @Test
    @Disabled
    void triggerTeleporter() {
        TODO.IMPLEMENT();
    }

    @Test
    @Disabled
    void fromStart() {
        TODO.IMPLEMENT();
    }
}

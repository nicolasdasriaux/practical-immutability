package practicalimmutability.kata.robot;

import io.vavr.collection.List;
import io.vavr.collection.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

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

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class Move {
        @ParameterizedTest
        @EnumSource(Direction.class)
        void robotWithoutObstacle(final Direction direction) {
            final CityMap cityMap = CityMap.fromLines(
                    // @formatter:off
                   //0123456 | @formatter:on
                    "#######", // 0
                    "#@    #", // 1
                    "#     #", // 2
                    "#     #", // 3
                    "#     #", // 4
                    "#    $#", // 5
                    "#######"  // 6
            );

            final Position position = Position.of(3, 3);

            final Robot nonBreakerRobot = ImmutableRobot.builder()
                    .position(position)
                    .direction(direction)
                    .breaker(false)
                    .inverted(false)
                    .dead(false)
                    .build();

            final Robot movedRobot = ImmutableRobot.copyOf(nonBreakerRobot).withPosition(position.move(direction));

            assertThat(nonBreakerRobot.move(cityMap)).isEqualTo(movedRobot);
        }

        @ParameterizedTest
        @EnumSource(Direction.class)
        void breakerRobotWithBreakableObstacle(final Direction direction) {
            final CityMap cityMap = CityMap.fromLines(
                    // @formatter:off
                   //0123456 | @formatter:on
                    "#######", // 0
                    "#@    #", // 1
                    "#  X  #", // 2
                    "# X X #", // 3
                    "#  X  #", // 4
                    "#    $#", // 5
                    "#######"  // 6
                   //
            );

            final Position position = Position.of(3, 3);

            final Robot breakerRobot = ImmutableRobot.builder()
                    .position(position)
                    .direction(direction)
                    .breaker(true)
                    .inverted(false)
                    .dead(false)
                    .build();

            final Robot movedRobot = ImmutableRobot.copyOf(breakerRobot).withPosition(position.move(direction));

            assertThat(breakerRobot.move(cityMap)).isEqualTo(movedRobot);
        }

        Stream<Arguments> nonInvertedRobotWithObstacleExamples() {
            return Stream.of(
                    Arguments.of(
                            CityMap.fromLines(
                                    // @formatter:off
                                   //0123456 | @formatter:on
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#  #  #", // 2
                                    "#     #", // 3
                                    "#     #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                            ),
                            North, South
                    ),
                    Arguments.of(
                            CityMap.fromLines(
                                    // @formatter:off
                                   //0123456 | @formatter:on
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#     #", // 2
                                    "#     #", // 3
                                    "#  #  #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                            ),
                            South, East
                    ),
                    Arguments.of(
                            CityMap.fromLines(
                                    // @formatter:off
                                   //0123456 | @formatter:on
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#     #", // 2
                                    "# #   #", // 3
                                    "#     #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                            ),
                            West, South
                    ),
                    Arguments.of(
                            CityMap.fromLines(
                                    // @formatter:off
                                   //0123456 | @formatter:on
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#     #", // 2
                                    "#   # #", // 3
                                    "#     #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                            ),
                            East, South
                    ),
                    Arguments.of(
                            CityMap.fromLines(
                                    // @formatter:off
                                   //0123456 | @formatter:on
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#  #  #", // 2
                                    "#     #", // 3
                                    "#  #  #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                            ),
                            North, East
                    ),
                    Arguments.of(
                            CityMap.fromLines(
                                    // @formatter:off
                                   //0123456 | @formatter:on
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#     #", // 2
                                    "#   # #", // 3
                                    "#  #  #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                            ),
                            South, North
                    ),
                    Arguments.of(
                            CityMap.fromLines(
                                    // @formatter:off
                                   //0123456 | @formatter:on
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#     #", // 2
                                    "# #   #", // 3
                                    "#  #  #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                            ),
                            West, East
                    ),
                    Arguments.of(
                            CityMap.fromLines(
                                    // @formatter:off
                                   //0123456 | @formatter:on
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#     #", // 2
                                    "#   # #", // 3
                                    "#  #  #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                            ),
                            East, North
                    ),
                    Arguments.of(
                            CityMap.fromLines(
                                    // @formatter:off
                                   //0123456 | @formatter:on
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#  #  #", // 2
                                    "#   # #", // 3
                                    "#  #  #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                            ),
                            North, West
                    ),
                    Arguments.of(
                            CityMap.fromLines(
                                    // @formatter:off
                                   //0123456 | @formatter:on
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#  #  #", // 2
                                    "#   # #", // 3
                                    "#  #  #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                            ),
                            South, West
                    ),
                    Arguments.of(
                            CityMap.fromLines(
                                    // @formatter:off
                                   //0123456 | @formatter:on
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#     #", // 2
                                    "# # # #", // 3
                                    "#  #  #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                            ),
                            West, North
                    ),
                    Arguments.of(
                            CityMap.fromLines(
                                    // @formatter:off
                                   //0123456 | @formatter:on
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#  #  #", // 2
                                    "#   # #", // 3
                                    "#  #  #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                            ),
                            East, West
                    )
            );
        }

        @ParameterizedTest
        @MethodSource("nonInvertedRobotWithObstacleExamples")
        void nonInvertedRobotWithObstacle(final CityMap cityMap, final Direction initialDirection, final Direction finalDirection) {
            final Position initialPosition = Position.of(3, 3);

            final Robot robot = ImmutableRobot.builder()
                    .position(initialPosition)
                    .direction(initialDirection)
                    .breaker(false)
                    .inverted(false)
                    .dead(false)
                    .build();

            final Position finalPosition = initialPosition.move(finalDirection);

            final Robot movedRobot = ImmutableRobot.builder().from(robot)
                    .position(finalPosition)
                    .direction(finalDirection)
                    .build();

            assertThat(robot.move(cityMap)).isEqualTo(movedRobot);
        }

        Stream<Arguments> invertedRobotWithObstacleExamples() {
            return Stream.of(
                    Arguments.of(
                            CityMap.fromLines(
                                    // @formatter:off
                                   //0123456 | @formatter:on
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#  #  #", // 2
                                    "#     #", // 3
                                    "#     #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                            ),
                            North, West
                    ),
                    Arguments.of(
                            CityMap.fromLines(
                                    // @formatter:off
                                   //0123456 | @formatter:on
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#     #", // 2
                                    "#     #", // 3
                                    "#  #  #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                            ),
                            South, West
                    ),
                    Arguments.of(
                            CityMap.fromLines(
                                    // @formatter:off
                                   //0123456 | @formatter:on
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#     #", // 2
                                    "# #   #", // 3
                                    "#     #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                            ),
                            West, North
                    ),
                    Arguments.of(
                            CityMap.fromLines(
                                    // @formatter:off
                                   //0123456 | @formatter:on
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#     #", // 2
                                    "#   # #", // 3
                                    "#     #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                            ),
                            East, West
                    ),
                    Arguments.of(
                            CityMap.fromLines(
                                    // @formatter:off
                                   //0123456 | @formatter:on
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#  #  #", // 2
                                    "# #   #", // 3
                                    "#     #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                            ),
                            North, East
                    ),
                    Arguments.of(
                            CityMap.fromLines(
                                    // @formatter:off
                                   //0123456 | @formatter:on
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#     #", // 2
                                    "# #   #", // 3
                                    "#  #  #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                            ),
                            South, North
                    ),
                    Arguments.of(
                            CityMap.fromLines(
                                    // @formatter:off
                                   //0123456 | @formatter:on
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#  #  #", // 2
                                    "# #   #", // 3
                                    "#     #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                            ),
                            West, East
                    ),
                    Arguments.of(
                            CityMap.fromLines(
                                    // @formatter:off
                                   //0123456 | @formatter:on
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#     #", // 2
                                    "# # # #", // 3
                                    "#     #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                            ),
                            East, North
                    ),
                    Arguments.of(
                            CityMap.fromLines(
                                    // @formatter:off
                                   //0123456 | @formatter:on
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#  #  #", // 2
                                    "# # # #", // 3
                                    "#     #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                            ),
                            North, South
                    ),
                    Arguments.of(
                            CityMap.fromLines(
                                    // @formatter:off
                                   //0123456 | @formatter:on
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#  #  #", // 2
                                    "# #   #", // 3
                                    "#  #  #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                            ),
                            South, East
                    ),
                    Arguments.of(
                            CityMap.fromLines(
                                    // @formatter:off
                                   //0123456 | @formatter:on
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#  #  #", // 2
                                    "# # # #", // 3
                                    "#     #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                            ),
                            West, South
                    ),
                    Arguments.of(
                            CityMap.fromLines(
                                    // @formatter:off
                                   //0123456 | @formatter:on
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#  #  #", // 2
                                    "# # # #", // 3
                                    "#     #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                            ),
                            East, South
                    )
            );
        }

        @ParameterizedTest
        @MethodSource("invertedRobotWithObstacleExamples")
        void invertedRobotWithObstacle(final CityMap cityMap, final Direction initialDirection, final Direction finalDirection) {
            final Position initialPosition = Position.of(3, 3);

            final Robot robot = ImmutableRobot.builder()
                    .position(initialPosition)
                    .direction(initialDirection)
                    .breaker(false)
                    .inverted(true)
                    .dead(false)
                    .build();

            final Position finalPosition = initialPosition.move(finalDirection);

            final Robot movedRobot = ImmutableRobot.builder().from(robot)
                    .position(finalPosition)
                    .direction(finalDirection)
                    .build();

            assertThat(robot.move(cityMap)).isEqualTo(movedRobot);
        }
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
        assertThat(nonInvertedRobot.invert().priorities()).isEqualTo(List.of(West, North, East, South));
    }

    @Test
    void triggerTeleporter() {
        final CityMap cityMap = CityMap.fromLines(
                // @formatter:off
               //01234567 | @formatter:on
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
        );

        final Robot inTeleporterRobot = ImmutableRobot.builder()
                .position(Position.of(2, 4))
                .direction(South)
                .breaker(false)
                .inverted(false)
                .dead(false)
                .build();

        final Robot outTeleporterRobot = ImmutableRobot.copyOf(inTeleporterRobot).withPosition(Position.of(5, 6));

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

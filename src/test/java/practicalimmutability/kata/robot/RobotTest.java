package practicalimmutability.kata.robot;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import io.vavr.Tuple;
import io.vavr.Tuple3;
import io.vavr.collection.IndexedSeq;
import io.vavr.collection.List;
import io.vavr.collection.Seq;

import static org.assertj.core.api.Assertions.*;
import static practicalimmutability.kata.robot.Direction.*;

@DisplayName("Robot")
class RobotTest {
    @DisplayName("Should change direction to new given direction")
    @ParameterizedTest(name = "{0}")
    @EnumSource(Direction.class)
    void changeDirection(final Direction direction) {
        final Robot southDirectedRobot = Robot.builder()
                .position(Position.of(2, 4))
                .direction(SOUTH)
                .breaker(false)
                .inverted(false)
                .dead(false)
                .build();

        final Robot redirectedRobot = southDirectedRobot.withDirection(direction);

        assertThat(southDirectedRobot.changeDirection(direction)).isEqualTo(redirectedRobot);
    }

    @DisplayName("Should toggle breaker mode")
    @Test
    void toggleBreaker() {
        final Robot nonBreakerRobot = Robot.builder()
                .position(Position.of(2, 4))
                .direction(SOUTH)
                .breaker(false)
                .inverted(false)
                .dead(false)
                .build();

        final Robot breakerRobot = nonBreakerRobot.withBreaker(true);

        assertThat(nonBreakerRobot.toggleBreaker()).isEqualTo(breakerRobot);
        assertThat(nonBreakerRobot.toggleBreaker().toggleBreaker()).isEqualTo(nonBreakerRobot);
    }

    @DisplayName("Should invert priorities")
    @Test
    void invert() {
        final Robot nonInvertedRobot = Robot.builder()
                .position(Position.of(2, 4))
                .direction(SOUTH)
                .breaker(false)
                .inverted(false)
                .dead(false)
                .build();

        final Robot invertedRobot = nonInvertedRobot.withInverted(true);

        assertThat(nonInvertedRobot.invert()).isEqualTo(invertedRobot);
        assertThat(nonInvertedRobot.invert().invert()).isEqualTo(nonInvertedRobot);
    }

    @DisplayName("Should die")
    @Test
    void die() {
        final Robot livingRobot = Robot.builder()
                .position(Position.of(2, 4))
                .direction(SOUTH)
                .breaker(false)
                .inverted(false)
                .dead(false)
                .build();

        final Robot deadRobot = livingRobot.withDead(true);

        assertThat(livingRobot.die()).isEqualTo(deadRobot);
    }

    @DisplayName("Should have priorities accordingly to inversion mode")
    @Test
    void priorities() {
        final Robot nonInvertedRobot = Robot.builder()
                .position(Position.of(2, 4))
                .direction(SOUTH)
                .breaker(false)
                .inverted(false)
                .dead(false)
                .build();

        assertThat(nonInvertedRobot.priorities()).isEqualTo(List.of(SOUTH, EAST, NORTH, WEST));
        assertThat(nonInvertedRobot.invert().priorities()).isEqualTo(List.of(WEST, NORTH, EAST, SOUTH));
    }

    @DisplayName("When moving")
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class Move {
        @DisplayName("Should move keeping same direction when no obstacle")
        @ParameterizedTest(name="{0}")
        @EnumSource(Direction.class)
        void robotWithoutObstacle(final Direction direction) {
            final CityMap cityMap = CityMap.fromLines(
                    // @formatter:off
                  // 0123456
                    "#######", // 0
                    "#@    #", // 1
                    "#     #", // 2
                    "#     #", // 3
                    "#     #", // 4
                    "#    $#", // 5
                    "#######"  // 6
                    // @formatter:on
            );

            final Position position = Position.of(3, 3);

            final Robot nonBreakerRobot = Robot.builder()
                    .position(position)
                    .direction(direction)
                    .breaker(false)
                    .inverted(false)
                    .dead(false)
                    .build();

            final Robot movedRobot = nonBreakerRobot.withPosition(position.move(direction));

            assertThat(nonBreakerRobot.move(cityMap)).isEqualTo(movedRobot);
        }

        @DisplayName("Should move through breakable obstacle keeping same direction when in breaker mode")
        @ParameterizedTest(name="{0}")
        @EnumSource(Direction.class)
        void breakerRobotWithBreakableObstacle(final Direction direction) {
            final CityMap cityMap = CityMap.fromLines(
                    // @formatter:off
                  // 0123456
                    "#######", // 0
                    "#@    #", // 1
                    "#  X  #", // 2
                    "# X X #", // 3
                    "#  X  #", // 4
                    "#    $#", // 5
                    "#######"  // 6
                    // @formatter:on
            );

            final Position position = Position.of(3, 3);

            final Robot breakerRobot = Robot.builder()
                    .position(position)
                    .direction(direction)
                    .breaker(true)
                    .inverted(false)
                    .dead(false)
                    .build();

            final Robot movedRobot = breakerRobot.withPosition(position.move(direction));

            assertThat(breakerRobot.move(cityMap)).isEqualTo(movedRobot);
        }

        Seq<Tuple3<Seq<String>, Direction, Direction>> nonInvertedRobotWithObstacleExampleTemplates() {
            return List.of(
                    Tuple.of(
                            List.of(
                                    // @formatter:off
                                    // 0123456
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#  ?  #", // 2
                                    "#     #", // 3
                                    "#     #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                                    // @formatter:on
                            ),
                            NORTH, SOUTH
                    ),
                    Tuple.of(
                            List.of(
                                    // @formatter:off
                                    // 0123456
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#     #", // 2
                                    "#     #", // 3
                                    "#  ?  #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                                    // @formatter:on
                            ),
                            SOUTH, EAST
                    ),
                    Tuple.of(
                            List.of(
                                    // @formatter:off
                                    // 0123456
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#     #", // 2
                                    "# ?   #", // 3
                                    "#     #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                                    // @formatter:on
                            ),
                            WEST, SOUTH
                    ),
                    Tuple.of(
                            List.of(
                                    // @formatter:off
                                    // 0123456
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#     #", // 2
                                    "#   ? #", // 3
                                    "#     #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                                    // @formatter:on
                            ),
                            EAST, SOUTH
                    ),
                    Tuple.of(
                            List.of(
                                    // @formatter:off
                                    // 0123456
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#  ?  #", // 2
                                    "#     #", // 3
                                    "#  ?  #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                                    // @formatter:on
                            ),
                            NORTH, EAST
                    ),
                    Tuple.of(
                            List.of(
                                    // @formatter:off
                                    // 0123456
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#     #", // 2
                                    "#   ? #", // 3
                                    "#  ?  #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                                    // @formatter:on
                            ),
                            SOUTH, NORTH
                    ),
                    Tuple.of(
                            List.of(
                                    // @formatter:off
                                    // 0123456
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#     #", // 2
                                    "# ?   #", // 3
                                    "#  ?  #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                                    // @formatter:on
                            ),
                            WEST, EAST
                    ),
                    Tuple.of(
                            List.of(
                                    // @formatter:off
                                    // 0123456
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#     #", // 2
                                    "#   ? #", // 3
                                    "#  ?  #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                                    // @formatter:on
                            ),
                            EAST, NORTH
                    ),
                    Tuple.of(
                            List.of(
                                    // @formatter:off
                                    // 0123456
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#  ?  #", // 2
                                    "#   ? #", // 3
                                    "#  ?  #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                                    // @formatter:on
                            ),
                            NORTH, WEST
                    ),
                    Tuple.of(
                            List.of(
                                    // @formatter:off
                                    // 0123456
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#  ?  #", // 2
                                    "#   ? #", // 3
                                    "#  ?  #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                                    // @formatter:on
                            ),
                            SOUTH, WEST
                    ),
                    Tuple.of(
                            List.of(
                                    // @formatter:off
                                    // 0123456
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#     #", // 2
                                    "# ? ? #", // 3
                                    "#  ?  #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                                    // @formatter:on
                            ),
                            WEST, NORTH
                    ),
                    Tuple.of(
                            List.of(
                                    // @formatter:off
                                    // 0123456
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#  ?  #", // 2
                                    "#   ? #", // 3
                                    "#  ?  #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                                    // @formatter:on
                            ),
                            EAST, WEST
                    )
            );
        }

        Seq<Arguments> nonInvertedRobotWithObstacleExamples() {
            return substituteObstacles(nonInvertedRobotWithObstacleExampleTemplates(), '#');
        }

        @DisplayName("Should move and change direction taking obstacles into account when non-inverted priorities")
        @ParameterizedTest(name = "Case #{index} directing to {1} but bumped to {2}")
        @MethodSource("nonInvertedRobotWithObstacleExamples")
        void nonInvertedRobotWithObstacle(final CityMap cityMap, final Direction initialDirection, final Direction finalDirection) {
            final Position initialPosition = Position.of(3, 3);

            final Robot robot = Robot.builder()
                    .position(initialPosition)
                    .direction(initialDirection)
                    .breaker(false)
                    .inverted(false)
                    .dead(false)
                    .build();

            final Position finalPosition = initialPosition.move(finalDirection);

            final Robot movedRobot = robot.toBuilder()
                    .position(finalPosition)
                    .direction(finalDirection)
                    .build();

            assertThat(robot.move(cityMap)).isEqualTo(movedRobot);
        }

        Seq<Arguments> nonInvertedRobotWithBreakableObstacleExamples() {
            return substituteObstacles(nonInvertedRobotWithObstacleExampleTemplates(), 'X');
        }

        @DisplayName("Should move and change direction taking breakable obstacles into account when non-inverted priorities")
        @ParameterizedTest(name = "Case #{index} directing to {1} but bumped to {2}")
        @MethodSource("nonInvertedRobotWithBreakableObstacleExamples")
        void nonInvertedRobotWithBreakableObstacle(final CityMap cityMap, final Direction initialDirection, final Direction finalDirection) {
            final Position initialPosition = Position.of(3, 3);

            final Robot robot = Robot.builder()
                    .position(initialPosition)
                    .direction(initialDirection)
                    .breaker(false)
                    .inverted(false)
                    .dead(false)
                    .build();

            final Position finalPosition = initialPosition.move(finalDirection);

            final Robot movedRobot = robot.toBuilder()
                    .position(finalPosition)
                    .direction(finalDirection)
                    .build();

            assertThat(robot.move(cityMap)).isEqualTo(movedRobot);
        }

        Seq<Tuple3<Seq<String>, Direction, Direction>> invertedRobotWithObstacleExampleTemplates() {
            return List.of(
                    Tuple.of(
                            List.of(
                                    // @formatter:off
                                    // 0123456
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#  ?  #", // 2
                                    "#     #", // 3
                                    "#     #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                                    // @formatter:on
                            ),
                            NORTH, WEST
                    ),
                    Tuple.of(
                            List.of(
                                    // @formatter:off
                                    // 0123456
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#     #", // 2
                                    "#     #", // 3
                                    "#  ?  #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                                    // @formatter:on
                            ),
                            SOUTH, WEST
                    ),
                    Tuple.of(
                            List.of(
                                    // @formatter:off
                                    // 0123456
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#     #", // 2
                                    "# ?   #", // 3
                                    "#     #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                                    // @formatter:on
                            ),
                            WEST, NORTH
                    ),
                    Tuple.of(
                            List.of(
                                    // @formatter:off
                                    // 0123456
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#     #", // 2
                                    "#   ? #", // 3
                                    "#     #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                                    // @formatter:on
                            ),
                            EAST, WEST
                    ),
                    Tuple.of(
                            List.of(
                                    // @formatter:off
                                    // 0123456
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#  ?  #", // 2
                                    "# ?   #", // 3
                                    "#     #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                                    // @formatter:on
                            ),
                            NORTH, EAST
                    ),
                    Tuple.of(
                            List.of(
                                    // @formatter:off
                                    // 0123456
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#     #", // 2
                                    "# ?   #", // 3
                                    "#  ?  #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                                    // @formatter:on
                            ),
                            SOUTH, NORTH
                    ),
                    Tuple.of(
                            List.of(
                                    // @formatter:off
                                    // 0123456
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#  ?  #", // 2
                                    "# ?   #", // 3
                                    "#     #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                                    // @formatter:on
                            ),
                            WEST, EAST
                    ),
                    Tuple.of(
                            List.of(
                                    // @formatter:off
                                    // 0123456
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#     #", // 2
                                    "# ? ? #", // 3
                                    "#     #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                                    // @formatter:on
                            ),
                            EAST, NORTH
                    ),
                    Tuple.of(
                            List.of(
                                    // @formatter:off
                                    // 0123456
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#  ?  #", // 2
                                    "# ? ? #", // 3
                                    "#     #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                                    // @formatter:on
                            ),
                            NORTH, SOUTH
                    ),
                    Tuple.of(
                            List.of(
                                    // @formatter:off
                                    // 0123456
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#  ?  #", // 2
                                    "# ?   #", // 3
                                    "#  ?  #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                                    // @formatter:on
                            ),
                            SOUTH, EAST
                    ),
                    Tuple.of(
                            List.of(
                                    // @formatter:off
                                    // 0123456
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#  ?  #", // 2
                                    "# ? ? #", // 3
                                    "#     #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                                    // @formatter:on
                            ),
                            WEST, SOUTH
                    ),
                    Tuple.of(
                            List.of(
                                    // @formatter:off
                                    // 0123456
                                    "#######", // 0
                                    "#@    #", // 1
                                    "#  ?  #", // 2
                                    "# ? ? #", // 3
                                    "#     #", // 4
                                    "#    $#", // 5
                                    "#######"  // 6
                                    // @formatter:on
                            ),
                            EAST, SOUTH
                    )
            );
        }

        Seq<Arguments> invertedRobotWithObstacleExamples() {
            return substituteObstacles(invertedRobotWithObstacleExampleTemplates(), '#');
        }

        @DisplayName("Should move taking obstacles into account when inverted priorities")
        @ParameterizedTest(name = "Case #{index} directing to {1} but bumped to {2}")
        @MethodSource("invertedRobotWithObstacleExamples")
        void invertedRobotWithObstacle(final CityMap cityMap, final Direction initialDirection, final Direction finalDirection) {
            final Position initialPosition = Position.of(3, 3);

            final Robot robot = Robot.builder()
                    .position(initialPosition)
                    .direction(initialDirection)
                    .breaker(false)
                    .inverted(true)
                    .dead(false)
                    .build();

            final Position finalPosition = initialPosition.move(finalDirection);

            final Robot movedRobot = robot.toBuilder()
                    .position(finalPosition)
                    .direction(finalDirection)
                    .build();

            assertThat(robot.move(cityMap)).isEqualTo(movedRobot);
        }

        Seq<Arguments> invertedRobotWithBreakableObstacleExamples() {
            return substituteObstacles(invertedRobotWithObstacleExampleTemplates(), 'X');
        }

        @DisplayName("Should move taking breakable obstacles into account when inverted priorities")
        @ParameterizedTest(name = "Case #{index} directing to {1} but bumped to {2}")
        @MethodSource("invertedRobotWithBreakableObstacleExamples")
        void invertedRobotWithBreakableObstacle(final CityMap cityMap, final Direction initialDirection, final Direction finalDirection) {
            final Position initialPosition = Position.of(3, 3);

            final Robot robot = Robot.builder()
                    .position(initialPosition)
                    .direction(initialDirection)
                    .breaker(false)
                    .inverted(true)
                    .dead(false)
                    .build();

            final Position finalPosition = initialPosition.move(finalDirection);

            final Robot movedRobot = robot.toBuilder()
                    .position(finalPosition)
                    .direction(finalDirection)
                    .build();

            assertThat(robot.move(cityMap)).isEqualTo(movedRobot);
        }

        private Seq<Arguments> substituteObstacles(final Seq<Tuple3<Seq<String>, Direction, Direction>> exampleTemplates, final char obstacle) {
            return exampleTemplates.map(t -> {
                final IndexedSeq<String> lines = t._1.map(line -> line.replace('?', obstacle)).toVector();
                final Direction initialDirection = t._2;
                final Direction finalDirection = t._3;

                return Arguments.of(
                        CityMap.fromLines(lines),
                        initialDirection,
                        finalDirection
                );
            });
        }
    }

    @DisplayName("Should teleport to out-teleporter when triggering in-teleporter")
    @Test
    void triggerTeleporter() {
        final CityMap cityMap = CityMap.fromLines(
                // @formatter:off
              // 01234567
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

        final Robot inTeleporterRobot = Robot.builder()
                .position(Position.of(2, 4))
                .direction(SOUTH)
                .breaker(false)
                .inverted(false)
                .dead(false)
                .build();

        final Robot outTeleporterRobot = inTeleporterRobot.withPosition(Position.of(5, 6));

        assertThat(inTeleporterRobot.triggerTeleporter(cityMap)).isEqualTo(outTeleporterRobot);
    }

    @DisplayName("Should create robot from start position (south directed, no alteration, alive)")
    @Test
    void fromStart() {
        final Robot robot = Robot.fromStart(Position.of(3, 4));

        final Robot expectedRobot = Robot.builder()
                .position(Position.of(3, 4))
                .direction(SOUTH)
                .breaker(false)
                .inverted(false)
                .dead(false)
                .build();

        assertThat(robot).isEqualTo(expectedRobot);
    }
}

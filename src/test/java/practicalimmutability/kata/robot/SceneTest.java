package practicalimmutability.kata.robot;

import io.vavr.Tuple;
import io.vavr.Tuple3;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static practicalimmutability.kata.robot.Direction.*;

class SceneTest {
    @Test
    void completed() {
        final CityMap currentCityMap = CityMap.fromLines(
                // @formatter:off
                  // 012345
                    "######", // 0
                    "#@   #", // 1
                    "#    #", // 2
                    "#    #", // 3
                    "#   $#", // 4
                    "######"  // 5
                    // @formatter:on
        );

        final Robot currentRobot = ImmutableRobot.builder()
                .position(Position.of(4, 4))
                .direction(East)
                .breaker(false)
                .inverted(false)
                .dead(true)
                .build();

        final Scene currentScene = ImmutableScene.builder().cityMap(currentCityMap).robot(currentRobot).build();

        assertThat(currentScene.completed()).isTrue();
    }

    @Nested
    class Next {
        @Test
        void reactToEmptyAndMove() {
            final CityMap currentCityMap = CityMap.fromLines(
                    // @formatter:off
                  // 012345
                    "######", // 0
                    "#@   #", // 1
                    "#    #", // 2
                    "#    #", // 3
                    "#   $#", // 4
                    "######"  // 5
                    // @formatter:on
            );

            final Robot currentRobot = ImmutableRobot.builder()
                    .position(Position.of(1, 3))
                    .direction(South)
                    .breaker(false)
                    .inverted(false)
                    .dead(false)
                    .build();

            final Scene currentScene = ImmutableScene.builder()
                    .cityMap(currentCityMap)
                    .robot(currentRobot)
                    .build();

            final Robot updatedRobot = ImmutableRobot.copyOf(currentRobot).withPosition(Position.of(1, 4));
            final Scene updatedScene = ImmutableScene.copyOf(currentScene).withRobot(updatedRobot);

            assertThat(currentScene.next()).isEqualTo(updatedScene);
        }

        @Test
        void reactToBooth() {
            final CityMap currentCityMap = CityMap.fromLines(
                    // @formatter:off
                  // 012345
                    "######", // 0
                    "#@   #", // 1
                    "#    #", // 2
                    "#    #", // 3
                    "#   $#", // 4
                    "######"  // 5
                    // @formatter:on
            );

            final Robot currentRobot = ImmutableRobot.builder()
                    .position(Position.of(4, 4))
                    .direction(South)
                    .breaker(false)
                    .inverted(false)
                    .dead(false)
                    .build();

            final Scene currentScene = ImmutableScene.builder()
                    .cityMap(currentCityMap)
                    .robot(currentRobot)
                    .build();

            final Robot updatedRobot = ImmutableRobot.copyOf(currentRobot).withDead(true);
            final Scene updatedScene = ImmutableScene.copyOf(currentScene).withRobot(updatedRobot);

            assertThat(currentScene.next()).isEqualTo(updatedScene);
        }

        @Test
        void reactToBreakableObstacleWhenBreakerAndMove() {
            final CityMap currentCityMap = CityMap.fromLines(
                    // @formatter:off
                  // 012345
                    "######", // 0
                    "#@   #", // 1
                    "#    #", // 2
                    "#B   #", // 3
                    "# X $#", // 4
                    "######"  // 5
                    // @formatter:on
            );

            final Robot currentRobot = ImmutableRobot.builder()
                    .position(Position.of(2, 4))
                    .direction(East)
                    .breaker(true)
                    .inverted(false)
                    .dead(false)
                    .build();

            final Scene currentScene = ImmutableScene.builder()
                    .cityMap(currentCityMap)
                    .robot(currentRobot)
                    .build();

            final CityMap updatedCityMap = currentCityMap.breakObstacle(Position.of(2, 4));
            final Robot updatedRobot = ImmutableRobot.copyOf(currentRobot).withPosition(Position.of(3, 4));

            final Scene updatedScene = ImmutableScene.builder()
                    .cityMap(updatedCityMap)
                    .robot(updatedRobot)
                    .build();

            assertThat(currentScene.next()).isEqualTo(updatedScene);
        }

        @Test
        void reactToDirectionModifierAndMove() {
            final CityMap currentCityMap = CityMap.fromLines(
                    // @formatter:off
                  // 012345
                    "######", // 0
                    "#@   #", // 1
                    "#    #", // 2
                    "#    #", // 3
                    "# N $#", // 4
                    "######"  // 5
                    // @formatter:on
            );

            final Robot currentRobot = ImmutableRobot.builder()
                    .position(Position.of(2, 4))
                    .direction(East)
                    .breaker(true)
                    .inverted(false)
                    .dead(false)
                    .build();

            final Scene currentScene = ImmutableScene.builder()
                    .cityMap(currentCityMap)
                    .robot(currentRobot)
                    .build();

            final Robot updatedRobot = ImmutableRobot.builder().from(currentRobot)
                    .position(Position.of(2, 3))
                    .direction(North)
                    .build();

            final Scene updatedScene = ImmutableScene.copyOf(currentScene).withRobot(updatedRobot);

            assertThat(currentScene.next()).isEqualTo(updatedScene);
        }

        @Test
        void reactToCircuitInverterAndMove() {
            final CityMap currentCityMap = CityMap.fromLines(
                    // @formatter:off
                  // 012345
                    "######", // 0
                    "#@   #", // 1
                    "#    #", // 2
                    "#I   #", // 3
                    "#   $#", // 4
                    "######"  // 5
                    // @formatter:on
            );

            final Robot currentRobot = ImmutableRobot.builder()
                    .position(Position.of(1, 3))
                    .direction(South)
                    .breaker(false)
                    .inverted(false)
                    .dead(false)
                    .build();

            final Scene currentScene = ImmutableScene.builder()
                    .cityMap(currentCityMap)
                    .robot(currentRobot)
                    .build();

            final Robot updatedRobot = ImmutableRobot.builder().from(currentRobot)
                    .position(Position.of(1, 4))
                    .inverted(true)
                    .build();

            final Scene updatedScene = ImmutableScene.copyOf(currentScene).withRobot(updatedRobot);

            assertThat(currentScene.next()).isEqualTo(updatedScene);
        }

        @Test
        void reactToBeerAndMove() {
            final CityMap currentCityMap = CityMap.fromLines(
                    // @formatter:off
                  // 012345
                    "######", // 0
                    "#@   #", // 1
                    "#    #", // 2
                    "#B   #", // 3
                    "#   $#", // 4
                    "######"  // 5
                    // @formatter:on
            );

            final Robot currentRobot = ImmutableRobot.builder()
                    .position(Position.of(1, 3))
                    .direction(South)
                    .breaker(false)
                    .inverted(false)
                    .dead(false)
                    .build();

            final Scene currentScene = ImmutableScene.builder()
                    .cityMap(currentCityMap)
                    .robot(currentRobot)
                    .build();

            final Robot updatedRobot = ImmutableRobot.builder().from(currentRobot)
                    .position(Position.of(1, 4))
                    .breaker(true)
                    .build();

            final Scene updatedScene = ImmutableScene.copyOf(currentScene).withRobot(updatedRobot);

            assertThat(currentScene.next()).isEqualTo(updatedScene);
        }

        @Test
        void reactToTeleporterAndMove() {
            final CityMap currentCityMap = CityMap.fromLines(
                    // @formatter:off
                  // 012345
                    "######", // 0
                    "#@  T#", // 1
                    "#    #", // 2
                    "#    #", // 3
                    "# T $#", // 4
                    "######"  // 5
                    // @formatter:on
            );

            final Robot currentRobot = ImmutableRobot.builder()
                    .position(Position.of(2, 4))
                    .direction(South)
                    .breaker(false)
                    .inverted(false)
                    .dead(false)
                    .build();

            final Scene currentScene = ImmutableScene.builder()
                    .cityMap(currentCityMap)
                    .robot(currentRobot)
                    .build();

            final Robot updatedRobot = ImmutableRobot.copyOf(currentRobot).withPosition(Position.of(4, 2));
            final Scene updatedScene = ImmutableScene.copyOf(currentScene).withRobot(updatedRobot);

            assertThat(currentScene.next()).isEqualTo(updatedScene);
        }
    }

    @Nested
    class Run {
        @Test
        void simpleRun() {
            final CityMap initialCityMap = CityMap.fromLines(
                    // @formatter:off
                  // 012345
                    "######", // 0
                    "#@   #", // 1
                    "#    #", // 2
                    "#    #", // 3
                    "#   $#", // 4
                    "######"  // 5
                    // @formatter:on
            );

            final Scene initialScene = Scene.fromCityMap(initialCityMap);
            final Seq<Scene> scenes = initialScene.run().toList();

            final Seq<Tuple3<Position, Direction, Boolean>> positionsAndDirections = scenes.map(scene -> {
                final Robot robot = scene.robot();
                return Tuple.of(robot.position(), robot.direction(), robot.dead());
            });

            assertThat(positionsAndDirections).isEqualTo(List.of(
                    Tuple.of(Position.of(1, 1), South, false),
                    Tuple.of(Position.of(1, 2), South, false),
                    Tuple.of(Position.of(1, 3), South, false),
                    Tuple.of(Position.of(1, 4), South, false),
                    Tuple.of(Position.of(2, 4), East, false),
                    Tuple.of(Position.of(3, 4), East, false),
                    Tuple.of(Position.of(4, 4), East, false),
                    Tuple.of(Position.of(4, 4), East, true)
            ));
        }

        @Test
        void complexRun() {
            final CityMap initialCityMap = CityMap.fromLines(
                    // @formatter:off
                  // 0123456789
                    "##########", // 0
                    "#@   E   #", // 1
                    "#   E S  #", // 2
                    "# T NNW I#", // 3
                    "#        #", // 4
                    "#        #", // 5
                    "#   # I  #", // 6
                    "#       ##", // 7
                    "#  T B X$#", // 8
                    "##########"  // 9
                  // 0123456789
                    // @formatter:on
            );

            final Scene initialScene = Scene.fromCityMap(initialCityMap);
            final Seq<Scene> scenes = initialScene.run().toList();

            final Seq<Tuple3<Position, Direction, Boolean>> positionsAndDirections = scenes.map(scene -> {
                final Robot robot = scene.robot();
                return Tuple.of(robot.position(), robot.direction(), robot.dead());
            });

            assertThat(positionsAndDirections).isEqualTo(List.of(
                    Tuple.of(Position.of(1, 1), South, false),
                    Tuple.of(Position.of(1, 2), South, false),
                    Tuple.of(Position.of(1, 3), South, false),
                    Tuple.of(Position.of(1, 4), South, false),
                    Tuple.of(Position.of(1, 5), South, false),
                    Tuple.of(Position.of(1, 6), South, false),
                    Tuple.of(Position.of(1, 7), South, false),
                    Tuple.of(Position.of(1, 8), South, false),
                    Tuple.of(Position.of(2, 8), East, false),
                    Tuple.of(Position.of(3, 8), East, false),
                    Tuple.of(Position.of(3, 3), East, false),
                    Tuple.of(Position.of(4, 3), East, false),
                    Tuple.of(Position.of(4, 2), North, false),
                    Tuple.of(Position.of(5, 2), East, false),
                    Tuple.of(Position.of(6, 2), East, false),
                    Tuple.of(Position.of(6, 3), South, false),
                    Tuple.of(Position.of(5, 3), West, false),
                    Tuple.of(Position.of(5, 2), North, false),
                    Tuple.of(Position.of(5, 1), North, false),
                    Tuple.of(Position.of(6, 1), East, false),
                    Tuple.of(Position.of(7, 1), East, false),
                    Tuple.of(Position.of(8, 1), East, false),
                    Tuple.of(Position.of(8, 2), South, false),
                    Tuple.of(Position.of(8, 3), South, false),
                    Tuple.of(Position.of(8, 4), South, false),
                    Tuple.of(Position.of(8, 5), South, false),
                    Tuple.of(Position.of(8, 6), South, false),
                    Tuple.of(Position.of(7, 6), West, false),
                    Tuple.of(Position.of(6, 6), West, false),
                    Tuple.of(Position.of(5, 6), West, false),
                    Tuple.of(Position.of(5, 7), South, false),
                    Tuple.of(Position.of(5, 8), South, false),
                    Tuple.of(Position.of(6, 8), East, false),
                    Tuple.of(Position.of(7, 8), East, false),
                    Tuple.of(Position.of(8, 8), East, false),
                    Tuple.of(Position.of(8, 8), East, true)
            ));
        }

        @Test
        void complexObstaclesRun() {
            final CityMap initialCityMap = CityMap.fromLines(
                    // @formatter:off
                  // 0123456789
                    "##########", // 0
                    "#@  T S N#", // 1
                    "#   # X  #", // 2
                    "#  #    E#", // 3
                    "##       #", // 4
                    "# T    XW#", // 5
                    "#        #", // 6
                    "#        #", // 7
                    "#       $#", // 8
                    "##########"  // 9
                  // 0123456789
                    // @formatter:on
            );

            final Scene initialScene = Scene.fromCityMap(initialCityMap);
            final Seq<Scene> scenes = initialScene.run().take(100).toList();

            final Seq<Tuple3<Position, Direction, Boolean>> positionsAndDirections = scenes.map(scene -> {
                final Robot robot = scene.robot();
                return Tuple.of(robot.position(), robot.direction(), robot.dead());
            });

            assertThat(positionsAndDirections).isEqualTo(List.of(
                    Tuple.of(Position.of(1, 1), South, false),
                    Tuple.of(Position.of(1, 2), South, false),
                    Tuple.of(Position.of(1, 3), South, false),
                    Tuple.of(Position.of(2, 3), East, false),
                    Tuple.of(Position.of(2, 4), South, false),
                    Tuple.of(Position.of(2, 5), South, false),
                    Tuple.of(Position.of(5, 1), East, false),
                    Tuple.of(Position.of(6, 1), East, false),
                    Tuple.of(Position.of(7, 1), East, false),
                    Tuple.of(Position.of(8, 1), East, false),
                    Tuple.of(Position.of(8, 2), South, false),
                    Tuple.of(Position.of(8, 3), South, false),
                    Tuple.of(Position.of(8, 4), South, false),
                    Tuple.of(Position.of(8, 5), South, false),
                    Tuple.of(Position.of(8, 6), South, false),
                    Tuple.of(Position.of(8, 7), South, false),
                    Tuple.of(Position.of(8, 8), South, false),
                    Tuple.of(Position.of(8, 8), South, true)
            ));
        }
    }
}

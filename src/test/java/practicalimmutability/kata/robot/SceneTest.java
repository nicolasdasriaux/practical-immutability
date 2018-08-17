package practicalimmutability.kata.robot;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static practicalimmutability.kata.robot.Direction.*;

class SceneTest {
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
    void reactToBeerInverterAndMove() {
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

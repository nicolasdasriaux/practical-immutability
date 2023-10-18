package practicalimmutability.kata.gameoflife;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class PositionTest {
    @Test
    void neighbours() {
        assertThat(Position.of(10, 20).neighbours()).containsExactlyInAnyOrder(
            Position.of(9, 19),
            Position.of(9, 20),
            Position.of(9, 21),
            Position.of(10, 19),
            Position.of(10, 21),
            Position.of(11, 19),
            Position.of(11, 20),
            Position.of(11, 21)
        );
    }
}

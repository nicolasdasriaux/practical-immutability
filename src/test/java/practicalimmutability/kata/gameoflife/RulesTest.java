package practicalimmutability.kata.gameoflife;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class RulesTest {
    @ParameterizedTest(name="{3} ({0} with {1} neighbours ----> {2})")
    @MethodSource("ruleExamples")
    void nextCellState(CellState cellState, int neighbourCount, CellState expectedNextCellState, String description) {
        assertThat(Rules.nextCellState(cellState, neighbourCount)).isEqualTo(expectedNextCellState);
    }

    public static Stream<Arguments> ruleExamples() {
        return Stream.of(
                Arguments.of(CellState.ALIVE, 0, CellState.DEAD, "Die of loneliness"),
                Arguments.of(CellState.ALIVE, 1, CellState.DEAD, "Die of not enough company"),
                Arguments.of(CellState.ALIVE, 2, CellState.ALIVE, "<<<Unchanged>>>"),
                Arguments.of(CellState.ALIVE, 3, CellState.ALIVE, "<<<Unchanged>>>"),
                Arguments.of(CellState.ALIVE, 4, CellState.DEAD, "Die of too much company"),
                Arguments.of(CellState.ALIVE, 5, CellState.DEAD, "Die of too much company"),
                Arguments.of(CellState.ALIVE, 6, CellState.DEAD, "Die of too much company"),
                Arguments.of(CellState.ALIVE, 7, CellState.DEAD, "Die of too much company"),
                Arguments.of(CellState.ALIVE, 8, CellState.DEAD, "Die of too much company"),

                Arguments.of(CellState.DEAD, 0, CellState.DEAD, "<<<Unchanged>>>"),
                Arguments.of(CellState.DEAD, 1, CellState.DEAD, "<<<Unchanged>>>"),
                Arguments.of(CellState.DEAD, 2, CellState.DEAD, "<<<Unchanged>>>"),
                Arguments.of(CellState.DEAD, 3, CellState.ALIVE, "Give birth to a new life"),
                Arguments.of(CellState.DEAD, 4, CellState.DEAD, "<<<Unchanged>>>"),
                Arguments.of(CellState.DEAD, 5, CellState.DEAD, "<<<Unchanged>>>"),
                Arguments.of(CellState.DEAD, 6, CellState.DEAD, "<<<Unchanged>>>"),
                Arguments.of(CellState.DEAD, 7, CellState.DEAD, "<<<Unchanged>>>"),
                Arguments.of(CellState.DEAD, 8, CellState.DEAD, "<<<Unchanged>>>")
        );
    }
}

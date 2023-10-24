package practicalimmutability.kata.gameoflife;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.Iterator;
import io.vavr.collection.Seq;
import io.vavr.collection.Set;
import io.vavr.control.Option;

import java.util.function.Function;

/**
 * Position in an infinite space
 *
 * <p>Hints:</p>
 * <ul>
 *     <li>X axis is oriented from left (negative) to right (positive)</li>
 *     <li>Y axis is oriented from top (negative) to bottom (positive)</li>
 * </ul>
 *
 * @param x X coordinate
 * @param y Y coordinate
 */
public record Position(int x, int y) {
    public static final Seq<Tuple2<Integer, Integer>> DELTAS =
            Iterator.rangeClosed(-1, 1).flatMap(dx -> {
                return Iterator.rangeClosed(-1, 1).flatMap(dy -> {
                    return dx == 0 && dy == 0 ? Option.none() : Option.of(Tuple.of(dx, dy));
                });
            })
            .toList();

    /**
     * Neighbour positions of given position
     *
     * <p>Hints:</p>
     * <ul>
     *     <li>Use {@link Seq#map(Function)}</li>
     *     <li>Use {@link Tuple2#_1()} and {@link Tuple2#_2()}</li>
     *     <li>Use {@link Seq#toSet()}</li>
     * </ul>
     *
     * @return Set of neighbour position
     */
    public Set<Position> neighbours() {
        return io.vavr.API.TODO();
    }

    public static Position of(int x, int y) {
        return new Position(x, y);
    }
}

package practicalimmutability.kata.gameoflife;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.Iterator;
import io.vavr.collection.Seq;
import io.vavr.collection.Set;
import io.vavr.control.Option;

public record Position(int x, int y) {
    public static final Seq<Tuple2<Integer, Integer>> DELTAS = Iterator.rangeClosed(-1, 1)
            .flatMap(dx -> {
                return Iterator.rangeClosed(-1, 1).flatMap(dy -> {
                    return dx == 0 && dy == 0 ? Option.none() : Option.of(Tuple.of(dx, dy));
                });
            })
            .toList();

    public Set<Position> neighbours() {
        return DELTAS
                .map(delta -> Position.of(x + delta._1(), y + delta._2()))
                .toSet();
    }

    public static Position of(int x, int y) {
        return new Position(x, y);
    }
}

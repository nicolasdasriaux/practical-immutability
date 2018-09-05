package practicalimmutability.kata.robot;

import io.vavr.collection.*;
import io.vavr.control.Option;
import org.immutables.value.Value;
import practicalimmutability.kata.robot.Tile.Empty;
import practicalimmutability.kata.robot.Tile.Start;
import practicalimmutability.kata.robot.Tile.Teleporter;

import java.util.function.Function;
import java.util.function.Predicate;

@Value.Immutable(prehash = true)
public abstract class CityMap {
    public abstract IndexedSeq<IndexedSeq<Tile>> rows();
    public abstract Position start();
    public abstract Seq<Position> teleporters();

    /**
     * Get tile at position
     *
     * Difficulty: *
     * Hints
     * Use {@link IndexedSeq#get(int)}
     */
    public Tile tile(final Position position) {
        return rows().get(position.y()).get(position.x());
    }

    /**
     * Get out-teleporter position from in-teleporter position
     *
     * Difficulty: *
     * Hints
     * Use {@link Seq#find(Predicate)}
     * Use {@link Option#get()}
     */
    public Position teleporterOutPosition(final Position inPosition) {
        return teleporters().find(position -> !position.equals(inPosition)).get();
    }

    /**
     * Break obstacle at position
     *
     * Difficulty: **
     * Hints
     * Use {@link IndexedSeq#update(int, Function)}
     */
    public CityMap breakObstacle(final Position position) {
        final IndexedSeq<IndexedSeq<Tile>> updatedRows = rows().update(
                position.y(),
                row -> row.update(position.x(), Empty.of())
        );

        return ImmutableCityMap.copyOf(this).withRows(updatedRows);
    }

    /**
     * Create a city map from a seq of lines
     *
     * Difficulty: **
     * Hints
     * Use {@link CharSeq#of(CharSequence)}
     * Use {@link CharSeq#map(Function)}
     * Use {@link IndexedSeq#map(Function)}
     */
    public static CityMap fromLines(final IndexedSeq<String> lines) {
        final IndexedSeq<IndexedSeq<Tile>> rows = lines.map(line -> CharSeq.of(line).map(Tile::fromCode));
        final Position start = findPosition(rows, Start.of()).head();
        final Seq<Position> teleporters = findPosition(rows, Teleporter.of());
        return ImmutableCityMap.builder().rows(rows).start(start).teleporters(teleporters).build();
    }

    /**
     * Create a city map from lines
     *
     * Difficulty: *
     * Hints
     * Use {@link Array#of(Object[])}
     */
    public static CityMap fromLines(final String... lines) {
        return CityMap.fromLines(Array.of(lines));
    }

    /**
     * Get all positions of a tile
     *
     * Difficulty: ****
     * Hints
     * Use {@link Iterator#range(int, int)}
     * Use {@link Iterator#filter(Predicate)}
     * Use {@link Iterator#map(Function)}
     * Use {@link Iterator#flatMap(Function)}
     * Use {@link IndexedSeq#get(int)}
     */
    private static Seq<Position> findPosition(final IndexedSeq<IndexedSeq<Tile>> rows, final Tile tile) {
        return Iterator.range(0, rows.size()).flatMap(y ->
                Iterator.range(0, rows.get(y).size())
                        .filter(x -> rows.get(y).get(x).equals(tile))
                        .map(x -> Position.of(x, y))
        ).toList();
    }
}

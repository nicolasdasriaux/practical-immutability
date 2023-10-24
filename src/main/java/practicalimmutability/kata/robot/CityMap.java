package practicalimmutability.kata.robot;

import practicalimmutability.kata.robot.Tile.Empty;
import practicalimmutability.kata.robot.Tile.Start;
import practicalimmutability.kata.robot.Tile.Teleporter;

import io.vavr.collection.Array;
import io.vavr.collection.CharSeq;
import io.vavr.collection.IndexedSeq;
import io.vavr.collection.Iterator;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.With;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * State for <b>city map</b>
 *
 * @param rows        Seq of rows, each row is a seq of tiles.
 *                    Coordinate y is the row index.
 *                    Coordinate x is the column index.
 * @param start       Start position of robot
 * @param teleporters Teleporter positions (exactly 2)
 */
@Builder(toBuilder = true, access = AccessLevel.PRIVATE)
public record CityMap(
        @With(AccessLevel.PRIVATE)
        IndexedSeq<IndexedSeq<Tile>> rows,
        Position start,
        Seq<Position> teleporters) {

    /**
     * Get tile at position
     * <p>Difficulty: *</p>
     * <p>Hints:</p>
     * <ul>
     *     <li>Use {@link IndexedSeq#get(int)}</li>
     * </ul>
     */
    public Tile tile(final Position position) {
        return io.vavr.API.TODO();
    }

    /**
     * Get out-teleporter position from in-teleporter position
     * <p>Difficulty: *</p>
     * <p>Hints:</p>
     * <ul>
     *     <li>Use {@link Seq#find(Predicate)}</li>
     *     <li>Use {@link Option#get()}</li>
     * </ul>
     */
    public Position teleporterOutPosition(final Position inPosition) {
        return io.vavr.API.TODO();
    }

    /**
     * Break obstacle at position
     * <p>Difficulty: **</p>
     * <p>Hints:</p>
     * <ul>
     *     <li>Use {@link IndexedSeq#update(int, Function)}</li>
     * </ul>
     */
    public CityMap breakObstacle(final Position position) {
        return io.vavr.API.TODO();
    }

    /**
     * Create a city map from a seq of lines
     *
     * <p>Difficulty: **</p>
     * <p>Hints:</p>
     * <ul>
     *     <li>Use {@link CharSeq#of(CharSequence)} to wrap a {@code String} into a {@code CharSeq}</li>
     *     <li>Use {@link CharSeq#map(Function)}</li>
     *     <li>Use {@link IndexedSeq#map(Function)}</li>
     *     <li>Use {@link #findPosition(IndexedSeq, Tile)} and {@link IndexedSeq#head()} to find start position</li>
     *     <li>Use {@link #findPosition(IndexedSeq, Tile)} to find teleporter positions</li>
     * </ul>
     */
    public static CityMap fromLines(final IndexedSeq<String> lines) {
        return io.vavr.API.TODO();
    }

    /**
     * Create a city map from lines
     *
     * <p>Difficulty: *</p>
     * <p>Hints:</p>
     * <ul>
     *     <li>Use {@link Array#of(Object[])}</li>
     *     <li>Use {@link #fromLines(IndexedSeq)}</li>
     * </ul>
     */
    public static CityMap fromLines(final String... lines) {
        return io.vavr.API.TODO();
    }

    /**
     * Get all positions of a tile
     *
     * <p>Difficulty: ****</p>
     * <p>Hints:</p>
     * <ul>
     *     <li>Use {@link Iterator#range(int, int)} to get an iterator over y coordinate (rows) and an iterator over x coordinate (columns)</li>
     *     <li>Use {@link Iterator#flatMap(Function)} to combine both iterators over y and x coordinates into a single iterator</li>
     *     <li>Use {@link Iterator#filter(Predicate)} to identify matching tiles</li>
     *     <li>Use {@link Iterator#map(Function)} to transform x an y coordinates to positions</li>
     *     <li>Use {@link IndexedSeq#get(int)} to access rows and tiles</li>
     *     <li>Use {@link Iterator#toList()} to run iterator and get a list of positions</li>
     * </ul>
     */
    private static Seq<Position> findPosition(final IndexedSeq<IndexedSeq<Tile>> rows, final Tile tile) {
        return io.vavr.API.TODO();
    }
}

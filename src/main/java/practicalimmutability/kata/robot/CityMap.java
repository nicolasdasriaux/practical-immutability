package practicalimmutability.kata.robot;

import io.vavr.collection.*;
import practicalimmutability.kata.robot.Tile.Empty;
import practicalimmutability.kata.robot.Tile.Start;
import practicalimmutability.kata.robot.Tile.Teleporter;
import org.immutables.value.Value;

@Value.Immutable(prehash = true)
public abstract class CityMap {
    public abstract IndexedSeq<IndexedSeq<Tile>> rows();
    public abstract Position start();
    public abstract Seq<Position> teleporters();

    public Tile tile(final Position position) {
        return rows().get(position.y()).get(position.x());
    }

    public Position teleporterOutPosition(final Position inPosition) {
        return teleporters().find(position -> !position.equals(inPosition)).get();
    }

    public CityMap breakObstacle(final Position position) {
        final IndexedSeq<IndexedSeq<Tile>> updatedRows = rows().update(
                position.y(),
                row -> row.update(position.x(), Empty.of())
        );

        return ImmutableCityMap.copyOf(this).withRows(updatedRows);
    }

    public static CityMap fromLines(final IndexedSeq<String> lines) {
        final IndexedSeq<IndexedSeq<Tile>> rows = lines.map(line -> CharSeq.of(line).map(Tile::fromCode));
        final Position start = findPosition(rows, Start.of()).head();
        final Seq<Position> teleporters = findPosition(rows, Teleporter.of());
        return ImmutableCityMap.builder().rows(rows).start(start).teleporters(teleporters).build();
    }

    public static CityMap fromLines(final String... lines) {
        return CityMap.fromLines(Array.of(lines));
    }

    private static Seq<Position> findPosition(final IndexedSeq<IndexedSeq<Tile>> rows, final Tile tile) {
        return Iterator.range(0, rows.size()).flatMap(y ->
                Iterator.range(0, rows.get(y).size())
                        .filter(x -> rows.get(y).get(x).equals(tile))
                        .map(x -> Position.of(x, y))
        ).toList();
    }
}

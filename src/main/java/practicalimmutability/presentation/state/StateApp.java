package practicalimmutability.presentation.state;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.collection.Vector;
import practicalimmutability.presentation.Unit;

public class StateApp {
    public static void main(String[] args) {
        System.out.println(nextId.run(1));
        System.out.println(nextName.run(1));

        final State<Integer, Tuple2> program1 =
                setCounter(10).flatMap(unit1 ->
                        incCounter(20).flatMap(unit2 ->
                                nextAddition.flatMap(addition1 ->
                                        incCounter(10).flatMap(unit3 ->
                                                nextAddition.map(addition2 ->
                                                        Tuple.of(addition1, addition2)
                                                )
                                        )
                                )
                        )
                );

        System.out.println(program1.run(1));

        final State<Integer, Seq<String>> program2 =
                List.range(1, 10).foldLeft(
                        State.point(Vector.empty()),
                        (state, i) ->
                                state.flatMap(additions ->
                                        nextAddition.map(element -> additions.append(element))
                                )
                );

        System.out.println(program2.run(10));
    }

    private final static State<Integer, Integer> nextId = State.of(i -> ResultAndState.of(i, i + 1));
    private final static State<Integer, String> nextName = nextId.map(i -> String.format("Name %d", i));

    private final static State<Integer, String> nextAddition = nextId.flatMap(i ->
            nextId.map(j -> String.format("%d + %d = %d", i, j, i + j))
    );

    private static State<Integer, Unit> setCounter(final int value) {
        return State.put(value);
    }

    private static State<Integer, Unit> incCounter(final int value) {
        return State.modify(i -> i + value);
    }
}

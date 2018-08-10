package kata.state;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.collection.Vector;

public class StateApp {
    public static void main(String[] args) {
        final State<Integer, Integer> nextId = State.of(i -> StateAndResult.of(i + 1, i));
        final State<Integer, String> nextName = nextId.map(i -> String.format("Name %d", i));

        final State<Integer, String> nextAddition = nextId.flatMap(i ->
                nextId.map(j -> String.format("%d + %d = %d", i, j, i + j))
        );

        System.out.println(nextId.run(1));
        System.out.println(nextName.run(1));

        final State<Integer, Tuple2> program1 =
                State.put(10).flatMap(unit1 ->
                        State.<Integer>modify(i -> i + 20).flatMap(unit2 ->
                                nextAddition.flatMap(addition1 ->
                                        State.<Integer>modify(i -> i + 10).flatMap(unit3 ->
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
                        State.pure(Vector.empty()),
                        (state, i) ->
                                state.flatMap(additions ->
                                        nextAddition.map(element -> additions.append(element))
                                )
                );

        System.out.println(program2.run(10));
    }
}

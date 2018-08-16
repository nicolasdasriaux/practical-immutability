package practicalimmutability.kata.state;

import practicalimmutability.kata.common.Unit;
import org.immutables.value.Value;

import java.util.function.Function;

@Value.Immutable
public abstract class State<S, A> {
    @Value.Parameter
    public abstract Function<S, ResultAndState<A, S>> transition();

    public <B> State<S, B> map(final Function<? super A, ? extends B> f) {
        return State.of(s1 -> {
            final ResultAndState<A, S> sa = transition().apply(s1);
            final A a = sa.result();
            final S s2 = sa.state();

            final B b = f.apply(a);
            return ResultAndState.of(b, s2);
        });
    }

    public <B> State<S, B> flatMap(final Function<? super A, ? extends State<S, B>> f) {
        return State.of(s1 -> {
            final ResultAndState<A, S> sa = transition().apply(s1);
            final A a = sa.result();
            final S s2 = sa.state();

            final State<S, B> state = f.apply(sa.result());
            return state.transition().apply(s2);
        });
    }

    public ResultAndState<A, S> run(final S s) {
        return transition().apply(s);
    }

    public static <S, A> State<S, A> of(final Function<S, ResultAndState<A, S>> transition) {
        return ImmutableState.of(transition);
    }

    public static <S, A> State<S, A> point(final A a) {
        return State.of(s -> ResultAndState.of(a, s));
    }

    public static <S, A> State<S, S> get() {
        return State.of(s -> ResultAndState.of(s, s));
    }

    public static <S, A> State<S, Unit> put(final S s) {
        return State.of(s1 -> ResultAndState.of(Unit.of(), s));
    }

    public static <S> State<S, Unit> modify(final Function<? super S, ? extends S> f) {
        return State.of(s1 -> {
            final S s2 = f.apply(s1);
            return ResultAndState.of(Unit.of(), s2);
        });
    }
}

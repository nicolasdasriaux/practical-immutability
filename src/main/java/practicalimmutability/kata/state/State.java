package practicalimmutability.kata.state;

import practicalimmutability.kata.common.Unit;
import org.immutables.value.Value;

import java.util.function.Function;

@Value.Immutable
public abstract class State<S, A> {
    @Value.Parameter
    public abstract Function<S, StateAndResult<S, A>> transition();

    public <B> State<S, B> map(final Function<? super A, ? extends B> f) {
        return State.of(s1 -> {
            final StateAndResult<S, A> sa = transition().apply(s1);
            final S s2 = sa.state();
            final A a = sa.result();

            final B b = f.apply(a);
            return StateAndResult.of(s2, b);
        });
    }

    public <B> State<S, B> flatMap(final Function<? super A, ? extends State<S, B>> f) {
        return State.of(s1 -> {
            final StateAndResult<S, A> sa = transition().apply(s1);
            final S s2 = sa.state();
            final A a = sa.result();

            final State<S, B> state = f.apply(sa.result());
            return state.transition().apply(s2);
        });
    }

    public StateAndResult<S, A> run(final S s) {
        return transition().apply(s);
    }

    public static <S, A> State<S, A> of(final Function<S, StateAndResult<S, A>> transition) {
        return ImmutableState.of(transition);
    }

    public static <S, A> State<S, A> pure(final A a) {
        return State.of(s1 -> StateAndResult.of(s1, a));
    }

    public static <S, A> State<S, S> get() {
        return State.of(s1 -> StateAndResult.of(s1, s1));
    }

    public static <S, A> State<S, Unit> put(final S s) {
        return State.of(s1 -> StateAndResult.of(s, Unit.of()));
    }

    public static <S> State<S, Unit> modify(final Function<? super S, ? extends S> f) {
        return State.of(s1 -> {
            final S s2 = f.apply(s1);
            return StateAndResult.of(s2, Unit.of());
        });
    }
}

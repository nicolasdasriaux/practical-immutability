package practicalimmutability.kata.state;

import org.immutables.value.Value;

@Value.Immutable
public abstract class ResultAndState<R, S> {
    @Value.Parameter
    public abstract R result();

    @Value.Parameter
    public abstract S state();

    public static <R, S> ResultAndState<R, S> of(final R result, final S state) {
        return ImmutableResultAndState.of(result, state);
    }
}

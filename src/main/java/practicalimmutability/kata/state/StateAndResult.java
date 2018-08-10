package practicalimmutability.kata.state;

import org.immutables.value.Value;

@Value.Immutable
public abstract class StateAndResult<S, R> {
    @Value.Parameter
    public abstract S state();

    @Value.Parameter
    public abstract R result();

    public static <S, R> StateAndResult<S, R> of(final S state, final R result) {
        return ImmutableStateAndResult.of(state, result);
    }
}

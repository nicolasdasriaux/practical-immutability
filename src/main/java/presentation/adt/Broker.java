package presentation.adt;

import org.immutables.value.Value;

@Value.Immutable
public abstract class Broker implements Intermediary {
    public abstract String firstName();
    public abstract String lastName();

    @Override
    public <R> R match(final IntermediaryMatcher<R> matcher) {
        return matcher.onBroker().apply(this);
    }
}

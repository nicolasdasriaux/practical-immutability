package fr.carbonit.model.sample;

import org.immutables.value.Value;

@Value.Immutable
public abstract class Broker implements Intermediary {
    public abstract String firstName();
    public abstract String lastName();

    @Override
    public void match(IntermediaryMatcher matcher) {
        matcher.onBroker().accept(this);
    }
}

package fr.carbonit.model.sample;

import org.immutables.value.Value;

@Value.Immutable
public abstract class Agency implements Intermediary {
    public abstract String name();

    @Override
    public void match(IntermediaryMatcher matcher) {
        matcher.onAgency().accept(this);
    }
}

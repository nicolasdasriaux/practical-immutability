package fr.carbonit.model.sample;

import io.vavr.collection.Seq;
import io.vavr.control.Option;
import org.immutables.value.Value;

@Value.Immutable
public abstract class Agency implements Intermediary {
    public abstract Option<String> name();
    public abstract Seq<Agent> agents();

    @Override
    public <R> R match(final IntermediaryMatcher<R> matcher) {
        return matcher.onAgency().apply(this);
    }
}

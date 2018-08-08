package fr.carbonit.model.sample;

public interface Intermediary {
    <R> R match(IntermediaryMatcher<R> matcher);
}

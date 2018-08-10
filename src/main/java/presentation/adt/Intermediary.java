package presentation.adt;

public interface Intermediary {
    <R> R match(IntermediaryMatcher<R> matcher);
}

package practicalimmutability.presentation.intermediary;

public interface Intermediary {
    <R> R match(IntermediaryMatcher<R> matcher);
}

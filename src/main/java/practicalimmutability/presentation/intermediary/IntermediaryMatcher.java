package practicalimmutability.presentation.intermediary;

import org.immutables.value.Value;

import java.util.function.Function;

@Value.Immutable
@Value.Style(stagedBuilder = true)
public interface IntermediaryMatcher<R> {
    Function<Agency, R> onAgency();
    Function<Broker, R> onBroker();
}

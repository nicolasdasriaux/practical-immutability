package fr.carbonit.model.sample;

import org.immutables.value.Value;

import java.util.function.Consumer;

@Value.Immutable()
@Value.Style(stagedBuilder = true)
public interface IntermediaryMatcher {
    Consumer<Agency> onAgency();
    Consumer<Broker> onBroker();
}

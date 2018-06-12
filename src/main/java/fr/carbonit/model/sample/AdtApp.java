package fr.carbonit.model.sample;

import io.vavr.collection.List;
import io.vavr.collection.Seq;

import java.util.function.Consumer;

public class AdtApp {
    public static void main(String[] args) {

        final Seq<Intermediary> intermediaries = List.of(
                ImmutableAgency.builder().name("Super Agence").build(),
                ImmutableBroker.builder().firstName("Paul").lastName("Simpson").build()
        );

        intermediaries.forEach(intermediary -> {
            if (intermediary instanceof Agency) {
                final Agency agency = (Agency) intermediary;
                System.out.println(agency.name());
            } else if (intermediary instanceof Broker) {
                final Broker broker = (Broker) intermediary;
                System.out.println(broker.firstName() + " " + broker.lastName());
            }
        });

        intermediaries.forEach(intermediary -> intermediary.match(DISPLAY_VISITOR()));

        intermediaries.forEach(intermediary ->
                intermediary.match(
                        intermediaryVisitor(
                                agency -> System.out.println(agency.name()),
                                broker -> System.out.println(broker.firstName() + " " + broker.lastName())
                        )
                )
        );

        intermediaries.forEach(intermediary ->
                intermediary.match(
                        ImmutableIntermediaryMatcher.builder()
                                .onAgency(agency -> System.out.println(agency.name()))
                                .onBroker(broker -> System.out.println(broker.firstName() + " " + broker.lastName()))
                                .build()
                )
        );
    }

    public static final IntermediaryMatcher DISPLAY_VISITOR() {
        return new IntermediaryMatcher() {
            @Override
            public Consumer<Agency> onAgency() {
                return agency -> System.out.println(agency.name());
            }

            @Override
            public Consumer<Broker> onBroker() {
                return broker -> System.out.println(broker.firstName() + " " + broker.lastName());
            }
        };
    }

    public static IntermediaryMatcher intermediaryVisitor(Consumer<Agency> agencyConsumer, Consumer<Broker> brokerConsumer) {
        return new IntermediaryMatcher() {
            @Override
            public Consumer<Agency> onAgency() {
                return agencyConsumer;
            }

            @Override
            public Consumer<Broker> onBroker() {
                return brokerConsumer;
            }
        };
    }
}

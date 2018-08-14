package practicalimmutability.presentation.intermediary;

import io.vavr.collection.List;
import io.vavr.collection.Seq;

import java.util.function.Function;

public class IntermediaryApp {
    public static void main(final String[] args) {
        final Seq<Intermediary> intermediaries = List.of(
                ImmutableAgency.builder()
                        .name("Agence")
                        .agents(List.of(
                                ImmutableAgent.builder().firstName("Paul").lastName("Vierzon").build(),
                                ImmutableAgent.builder().firstName("Marie").lastName("Caton").build()
                        ))
                        .build(),
                ImmutableAgency.builder()
                        .agents(List.of(
                                ImmutableAgent.builder().firstName("Paul").lastName("Vierzon").build(),
                                ImmutableAgent.builder().firstName("Marie").lastName("Caton").build()
                        ))
                        .build(),
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
                                agency -> {
                                    System.out.println(agency.name());
                                    return null;
                                },
                                broker -> {
                                    System.out.println(broker.firstName() + " " + broker.lastName());
                                    return null;
                                }
                        )
                )
        );

        final Function<Agency, String> AGENCY_LABEL = agency ->
                agency.name()
                        .getOrElse(() ->
                                agency.agents()
                                        .map(agent -> agent.firstName() + " " + agent.lastName())
                                        .mkString(", ")
                        );

        final Function<Broker, String> BROKER_LABEL = broker -> broker.firstName() + " " + broker.lastName();

        intermediaries.forEach(intermediary -> {
            final String name = intermediary.match(
                    ImmutableIntermediaryMatcher.<String>builder()
                            .onAgency(AGENCY_LABEL)
                            .onBroker(BROKER_LABEL)
                            .build()
            );

            System.out.println(name);
        });
    }

    public static IntermediaryMatcher DISPLAY_VISITOR() {
        return new IntermediaryMatcher() {
            @Override
            public Function<Agency, Void> onAgency() {
                return agency -> {
                    System.out.println(agency.name());
                    return null;
                };
            }

            @Override
            public Function<Broker, Void> onBroker() {
                return broker -> {
                    System.out.println(broker.firstName() + " " + broker.lastName());
                    return null;
                };
            }
        };
    }

    public static <R> IntermediaryMatcher<R> intermediaryVisitor(final Function<Agency, R> agencyConsumer, final Function<Broker, R> brokerConsumer) {
        return new IntermediaryMatcher<R>() {
            @Override
            public Function<Agency, R> onAgency() {
                return agencyConsumer;
            }

            @Override
            public Function<Broker, R> onBroker() {
                return brokerConsumer;
            }
        };
    }
}

package fr.carbonit.model;

import org.immutables.value.Value;

@Value.Immutable
public interface Item {
    @Value.Parameter
    int id();

    @Value.Parameter
    String name();
}

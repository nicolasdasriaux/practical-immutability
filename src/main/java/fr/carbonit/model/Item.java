package fr.carbonit.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableItem.class)
@JsonDeserialize(as = ImmutableItem.class)
public interface Item {
    @Value.Parameter
    int id();
    @Value.Parameter
    String name();
}

package fr.carbonit.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableCustomer.class)
@JsonDeserialize(as = ImmutableCustomer.class)
public interface Customer {
    int id();
    Option<String> title();
    String firstName();
    String lastName();
    Seq<Order> orders();

    default String fullName() {
        return firstName() + " " + lastName();
    }
}

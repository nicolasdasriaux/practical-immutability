package fr.carbonit.model;

import io.vavr.collection.Seq;
import io.vavr.control.Option;
import org.immutables.value.Value;

@Value.Immutable
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

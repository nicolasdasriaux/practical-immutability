package fr.carbonit.model;

import io.vavr.collection.Seq;
import org.immutables.value.Value;

@Value.Immutable
public interface Order {
    int id();
    Seq<Item> items();
}

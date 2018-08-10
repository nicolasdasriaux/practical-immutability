package practicalimmutability.presentation.json;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.vavr.collection.Seq;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableOrder.class)
@JsonDeserialize(as = ImmutableOrder.class)
public interface Order {
    int id();
    Seq<Item> items();
}

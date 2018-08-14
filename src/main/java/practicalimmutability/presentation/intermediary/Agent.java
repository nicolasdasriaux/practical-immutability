package practicalimmutability.presentation.intermediary;

import org.immutables.value.Value;

@Value.Immutable
public abstract class Agent {
    public abstract String firstName();
    public abstract String lastName();
}

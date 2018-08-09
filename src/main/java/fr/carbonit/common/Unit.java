package fr.carbonit.common;

import fr.carbonit.state.ImmutableUnit;
import org.immutables.value.Value;

@Value.Immutable(singleton = true)
public abstract class Unit {
    public static Unit of() {
        return ImmutableUnit.of();
    }
}

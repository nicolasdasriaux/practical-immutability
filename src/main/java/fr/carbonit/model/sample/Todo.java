package fr.carbonit.model.sample;

import com.google.common.base.Preconditions;
import fr.carbonit.common.StringValidation;
import org.immutables.value.Value;

@Value.Immutable
public abstract class Todo {
    @Value.Parameter public abstract int id();
    @Value.Parameter public abstract String name();
    @Value.Default public boolean isDone() { return false; };

    public static Todo of(final int id, final String name) {
        return ImmutableTodo.of(id, name);
    }

    @Value.Check
    public void check() {
        Preconditions.checkState(
                id() >= 1,
                "ID should be a least 1 (" + id() + ")");

        Preconditions.checkState(
                StringValidation.isTrimmedAndNonEmpty(name()),
                "Name should be trimmed and non empty (" + name() + ")");
    }
}

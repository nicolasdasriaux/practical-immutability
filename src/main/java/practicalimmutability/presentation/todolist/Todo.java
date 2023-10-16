package practicalimmutability.presentation.todolist;

import practicalimmutability.presentation.Preconditions;
import practicalimmutability.presentation.StringValidation;

import lombok.AccessLevel;
import lombok.With;

public record Todo(int id, String name, @With(AccessLevel.PRIVATE) boolean done) {
    public Todo {
        Preconditions.requireNonNull(name, "name");

        Preconditions.require(id, "id", i -> i > 0,
                "'%s' should be greater than 0 (%d)"::formatted);

        Preconditions.require(name, "name", StringValidation::isTrimmedAndNonEmpty,
                "'%s' should be trimmed and non-empty (\"%s\")"::formatted);
    }

    public Todo markAsDone() { return this.withDone(true); }

    public static Todo of(int id, String name) {
        return new Todo(id, name, false);
    }
}

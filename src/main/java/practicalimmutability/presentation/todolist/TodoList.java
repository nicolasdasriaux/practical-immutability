package practicalimmutability.presentation.todolist;

import practicalimmutability.presentation.Preconditions;
import practicalimmutability.presentation.StringValidation;

import io.vavr.collection.List;
import io.vavr.collection.Seq;
import lombok.AccessLevel;
import lombok.With;

import java.util.Objects;
import java.util.function.Predicate;

public record TodoList(String name, @With(AccessLevel.PRIVATE) Seq<Todo> todos) {
    public TodoList {
        Preconditions.requireNonNull(name, "name");
        Preconditions.requireNonNull(todos, "todos");

        Preconditions.require(name, "name", StringValidation::isTrimmedAndNonEmpty,
                "'%s' should be trimmed and non-empty (\"%s\")"::formatted);

        Preconditions.require(todos, "todos", l -> l.forAll(Objects::nonNull),
                "'%s' elements should all be non null (%s)"::formatted);
    }

    public static TodoList of(String name) {
        return new TodoList(name, List.empty());
    }

    public TodoList addTodo(Todo todo) {
        Seq<Todo> modifiedTodos = this.todos().append(todo);
        return this.withTodos(modifiedTodos);
    }

    public TodoList removeTodo(int todoId) {
        final Seq<Todo> modifiedTodos = this.todos().removeFirst(todo -> todo.id() == todoId);
        return this.withTodos(modifiedTodos);
    }

    public TodoList markTodoAsDone(int todoId) {
        final int todoIndex = this.todos().indexWhere(todo -> todo.id() == todoId);

        if (todoIndex >= 0) {
            final Seq<Todo> modifiedTodos = this.todos().update(todoIndex, Todo::markAsDone);
            return this.withTodos(modifiedTodos);
        } else {
            return this;
        }
    }

    public int pendingCount() {
        return todos().count(Predicate.not(Todo::done));
    }

    public int doneCount() {
        return todos().count(Todo::done);
    }
}

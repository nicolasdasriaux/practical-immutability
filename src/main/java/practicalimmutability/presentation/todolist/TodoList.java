package practicalimmutability.presentation.todolist;

import com.google.common.base.Preconditions;
import io.vavr.collection.Seq;
import practicalimmutability.presentation.StringValidation;
import org.immutables.value.Value;

import java.util.Objects;

@Value.Immutable
public abstract class TodoList {
    @Value.Parameter public abstract String name();
    public abstract Seq<Todo> todos();

    public static TodoList of(final String name) {
        return ImmutableTodoList.of(name);
    }

    @Value.Check
    protected void check() {
        Preconditions.checkState(
                StringValidation.isTrimmedAndNonEmpty(name()),
                "Name should be trimmed and non empty (" + name() + ")");

        Preconditions.checkState(
                todos().forAll(Objects::nonNull),
                "Todos should all be non-null");
    }

    public TodoList addTodo(final Todo todo) {
        return ImmutableTodoList.builder().from(this).addTodo(todo).build();
    }

    public TodoList removeTodo(final int todoId) {
        final Seq<Todo> modifiedTodos = this.todos().removeFirst(todo -> todo.id() == todoId);
        return ImmutableTodoList.copyOf(this).withTodos(modifiedTodos);
    }

    public TodoList markTodoAsDone(final int todoId) {
        final int todoIndex = this.todos().indexWhere(todo -> todo.id() == todoId);

        if (todoIndex >= 0) {
            final Seq<Todo> modifiedTodos = this.todos().update(todoIndex, Todo::markAsDone);
            return ImmutableTodoList.copyOf(this).withTodos(modifiedTodos);
        } else {
            return this;
        }
    }

    public int pendingCount() {
        return todos().count(todo -> !todo.isDone());
    }

    public int doneCount() {
        return todos().count(todo -> todo.isDone());
    }
}

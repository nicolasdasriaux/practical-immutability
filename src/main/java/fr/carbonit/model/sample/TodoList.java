package fr.carbonit.model.sample;


import com.google.common.base.Preconditions;
import fr.carbonit.model.StringValidation;
import io.vavr.collection.Seq;
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
        final int todoIndex = todos().indexWhere(todo -> todo.id() == todoId);

        if (todoIndex >= 0) {
            final Seq<Todo> modifiedTodos = todos().update(todoIndex,
                    todo -> ImmutableTodo.copyOf(todo).withIsDone(true));

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

    public static void main(final String[] args) {
        final TodoList todoList = TodoList.of("Food")
                .addTodo(Todo.of(1, "Leek"))
                .addTodo(Todo.of(2, "Turnip"))
                .addTodo(Todo.of(3, "Cabbage"));

        final TodoList modifiedTodoList = todoList
                .markTodoAsDone(3)
                .removeTodo(2);

        System.out.println(todoList);
        System.out.println(modifiedTodoList);
    }
}

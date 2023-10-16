package practicalimmutability.presentation.todolist;

public class TodoListApp {
    public static void main(String[] args) {
        final TodoList todoList = TodoList.of("Food")
                .addTodo(Todo.of(1, "Leek"))
                .addTodo(Todo.of(2, "Turnip"))
                .addTodo(Todo.of(3, "Cabbage"));

        System.out.println(todoList);

        final TodoList modifiedTodoList = todoList
                .markTodoAsDone(3)
                .removeTodo(2);

        System.out.println(modifiedTodoList);
        System.out.println(modifiedTodoList.doneCount());
        System.out.println(modifiedTodoList.pendingCount());
    }
}

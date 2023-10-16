package practicalimmutability.presentation.adt;

public sealed interface Action {
    record Sleep() implements Action {
        public static Sleep of() { return new Sleep(); }
    }

    record Walk(Direction direction) implements Action {
        public static Walk of(Direction direction) { return new Walk(direction); }
    }

    record Jump(Position position) implements Action {
        public static Jump of(Position position) { return new Jump(position); }
    }
}

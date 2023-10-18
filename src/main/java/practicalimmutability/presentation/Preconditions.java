package practicalimmutability.presentation;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Preconditions {
    public static void requireNonNull(Object value, String name) {
        Objects.requireNonNull(value, () -> "'%s' should be non-null".formatted(name));
    }

    public static void require(int value, String name, IntPredicate predicate, BiFunction<String, Integer, String> message) {
        if (!predicate.test(value)) {
            throw new IllegalArgumentException(message.apply(name, value));
        }
    }

    public static <T> void require(T value, String name, Predicate<T> predicate, BiFunction<String, T, String> message) {
        if (!predicate.test(value)) {
            throw new IllegalArgumentException(message.apply(name, value));
        }
    }

    public static <T> void require(Supplier<Boolean> predicate, Supplier<String> message) {
        if (!predicate.get()) {
            throw new IllegalStateException(message.get());
        }
    }
}

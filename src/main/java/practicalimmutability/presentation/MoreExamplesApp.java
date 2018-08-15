package practicalimmutability.presentation;

import io.vavr.collection.List;
import practicalimmutability.presentation.adt.Action;
import practicalimmutability.presentation.adt.Position;

import java.util.Random;

import static practicalimmutability.presentation.Examples.example;
import static practicalimmutability.presentation.Examples.part;
import static practicalimmutability.presentation.adt.Action.*;
import static practicalimmutability.presentation.adt.Direction.Right;
import static practicalimmutability.presentation.adt.Direction.Up;

public class MoreExamplesApp {
    public static void main(final String[] args) {
        part("Expressions", () -> {
            example("?:", () -> {
                final boolean enabled = false;
                final String status = enabled ? "Enabled" : "Disabled";
                System.out.println(status);
            });

            example("if", () -> {
                final int mark = new Random().nextInt(5) + 1;

                final String mood; // No default value

                // Every branch either assigns value or fails
                // Compiler is happy
                if (1 <= mark && mark <= 3) {
                    mood = "Bad";
                } else if (mark == 4) {
                    mood = "OK";
                } else if (5 <= mark && mark <= 7) {
                    mood ="Good";
                } else {
                    throw new AssertionError("Unexpected mark (" + mark + ")");
                }

                System.out.println(mood);
            });

            example("switch", () -> {
                final Color color = Color.values()[new Random().nextInt(Color.values().length)];

                final int mark;

                switch (color) {
                    case RED: mark = 1; break;
                    case YELLOW: mark = 4; break;
                    case GREEN: mark = 7; break;

                    default:
                        throw new AssertionError("Unexpected color (" + color + ")");
                }

                System.out.println(mark);
            });
        });

        part("ADT", () -> {
            example("Instantiating ADT", () -> {
                final List<Action> actions = List.of(
                        Jump.of(Position.of(5, 8)),
                        Walk.of(Up),
                        Sleep.of(),
                        Walk.of(Right)
                );
            });
        });
    }
}

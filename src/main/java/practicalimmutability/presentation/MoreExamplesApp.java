package practicalimmutability.presentation;

import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import io.vavr.control.Try;
import practicalimmutability.presentation.adt.Position;
import practicalimmutability.presentation.adt.matcher.Action;
import practicalimmutability.presentation.adt.matcher.Action.Jump;
import practicalimmutability.presentation.adt.matcher.Action.Sleep;
import practicalimmutability.presentation.adt.matcher.Action.Walk;
import practicalimmutability.presentation.adt.matcher.Player;

import java.util.Random;
import java.util.function.Consumer;

import static io.vavr.API.*;
import static io.vavr.Predicates.*;
import static io.vavr.Patterns.*;
import static practicalimmutability.presentation.Examples.example;
import static practicalimmutability.presentation.Examples.part;
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

            example("Try", () -> {
                final Consumer<String> parse = input -> {
                    final Try<Integer> triedNumber = Try.of(() -> Integer.parseInt(input))
                            .filter(i -> i > 0)
                            .map(i -> i * 10);

                    System.out.println(triedNumber);
                };

                parse.accept("3");
                parse.accept("-10");
                parse.accept("WRONG");
            });

            example("Try to Option", () -> {
                final Consumer<String> parse = input -> {
                    final Try<Integer> triedNumber = Try.of(() -> Integer.parseInt(input))
                            .filter(i -> i > 0)
                            .map(i -> i * 10);

                    final Integer defaultedNumber = triedNumber.getOrElse(0);
                    final Option<Integer> maybeNumber = triedNumber.toOption();

                    System.out.println(triedNumber);
                    System.out.println(defaultedNumber);
                    System.out.println(maybeNumber);
                };

                parse.accept("3");
                parse.accept("-10");
                parse.accept("WRONG");
            });
        });

        part("ADT", () -> {
            example("Instantiating ADT", () -> {
                final Seq<Action> actions = List.of(
                        Jump.of(Position.of(5, 8)),
                        Walk.of(Up),
                        Sleep.of(),
                        Walk.of(Right)
                );
            });

            example("Applying successive actions", () -> {
                final Player initialPlayer = Player.of(Position.of(1, 1));

                final Seq<Action> actions = List.of(
                        Jump.of(Position.of(5, 8)), Walk.of(Up), Sleep.of(), Walk.of(Right));

                final Player finalPLayer = actions.foldLeft(initialPlayer, Player::act);
                final Seq<Player> players = actions.scanLeft(initialPlayer, Player::act);

                System.out.println(finalPLayer);
                System.out.println(players);
            });
        });

        part("Pattern Matching", () -> {
            example("From switch to Match expression", () -> {
                // import static io.vavr.API.*;

                List.of(0, 1, 2, 3).forEach(number -> {
                    final String label = Match(number).of(
                            Case($(0), "Zero"),
                            Case($(1), "One"),
                            Case($(2), "Two"),
                            Case($(), "More")
                    );

                    System.out.println(String.format("number=%d, label=%s", number, label));
                });

            });

            example("Matching by Condition", () -> {
                // import static io.vavr.Predicates.*;

                List.of(0, -1, 23, 2, 3).forEach(number -> {
                    final String label = Match(number).of(
                            Case($(0), "Zero"),
                            Case($(n -> n < 0), "Negative"),
                            Case($(isIn(19, 23, 29)), "Chosen Prime"),
                            Case($(i -> i % 2 == 0), i -> String.format("Even (%d)", i)),
                            Case($(), i-> String.format("Odd (%d)", i))
                    );

                    System.out.println(String.format("number=%d, label=%s", number, label));
                });
            });

            example("Matching by Pattern", () -> {
                // import static io.vavr.Patterns.*;

                List.of(Option.of(-1), Option.of(1), Option.of(0)).forEach(maybeNumber -> {
                    final String label = Match(maybeNumber).of(
                            Case($Some($(0)), "Zero"),
                            Case($Some($(i -> i < 0)), i -> String.format("Negative (%d)", i)),
                            Case($Some($(i -> i > 0)), i -> String.format("Positive (%d)", i)),
                            Case($None(), "Absent")
                    );

                    System.out.println(String.format("maybeNumber=%s label=%s", maybeNumber, label));
                });
            });
        });
    }
}

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
                final List<Action> actions = List.of(
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
    }
}

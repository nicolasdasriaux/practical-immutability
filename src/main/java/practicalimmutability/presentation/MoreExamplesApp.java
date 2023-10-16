package practicalimmutability.presentation;

import practicalimmutability.presentation.adt.Action;
import practicalimmutability.presentation.adt.Action.Jump;
import practicalimmutability.presentation.adt.Action.Sleep;
import practicalimmutability.presentation.adt.Action.Walk;
import practicalimmutability.presentation.adt.Player;
import practicalimmutability.presentation.adt.Position;

import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import io.vavr.control.Try;

import java.util.Random;
import java.util.function.Consumer;

import static practicalimmutability.presentation.Examples.*;
import static practicalimmutability.presentation.adt.Direction.*;

public class MoreExamplesApp {
    public static void main(String[] args) {
        part("Expressions", () -> {
            example("?:", () -> {
                final boolean enabled = false;
                final String status = enabled ? "Enabled" : "Disabled";
                System.out.println(status);
            });

            example("if", () -> {
                final int mark = new Random().nextInt(7) + 1;

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

                final int mark = switch (color) {
                    case RED -> 1;
                    case YELLOW -> 4;
                    case GREEN -> 7;
                };

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
                        Walk.of(NORTH),
                        Sleep.of(),
                        Walk.of(EAST)
                );
            });

            example("Applying successive actions", () -> {
                final Player initialPlayer = Player.of(Position.of(1, 1));

                final Seq<Action> actions = List.of(
                        Jump.of(Position.of(5, 8)), Action.Walk.of(NORTH), Action.Sleep.of(), Action.Walk.of(EAST));

                final Player finalPLayer = actions.foldLeft(initialPlayer, Player::act);
                final Seq<Player> players = actions.scanLeft(initialPlayer, Player::act);

                System.out.println(finalPLayer);
                System.out.println(players);
            });
        });
    }
}

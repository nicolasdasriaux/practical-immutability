package fr.carbonit.model.sample;

import io.vavr.collection.*;
import io.vavr.control.Option;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Random;

public class SampleApp {
    public static void main(String[] args) {
        part("Building proven software by assembling proven parts", () -> {
        });

        part("Encapsulation and class invariant", () -> {
            /*

             * Principle and induction
             * Invariant, preconditions
             * Setters are not encapsulation (setters are creatures from hell)

             * From mutable invariant-based to immutable invariant-based
             * Forms and views / Models / Entities have different characteristics
             * A form conforms with no invariant until it's sufficiently validated but it's too late.
             *
             *
             * A view

             */
        });

        part("Building an Modifying", () -> {
            example("Build", () -> {
                final Customer customer =
                        ImmutableCustomer.builder()
                                .id(1)
                                .firstName("John")
                                .lastName("Doe")
                                .build();

                System.out.println("customer=" + customer);
            });

            example("With", () -> {
                final Customer customer =
                        ImmutableCustomer.builder()
                                .id(1)
                                .firstName("John")
                                .lastName("Doe")
                                .build();

                System.out.println("customer=" + customer);

                final Customer modifiedCustomer =
                        ImmutableCustomer.copyOf(customer).withLastName("Martin");

                System.out.println("modifiedCustomer=" + modifiedCustomer);
            });

            example("Build from", () -> {
                final Customer customer =
                        ImmutableCustomer.builder()
                                .id(1)
                                .firstName("John")
                                .lastName("Doe")
                                .build();

                System.out.println("customer=" + customer);

                final Customer modifiedCustomer =
                        ImmutableCustomer.builder().from(customer)
                                .firstName("Paul")
                                .lastName("Martin")
                                .build();

                System.out.println("modifiedCustomer=" + modifiedCustomer);
            });
        });

        part("Calculating attributes from other attributes", () -> {
            example("Calculated attribute and uniform access principle", () -> {
                final Customer customer =
                        ImmutableCustomer.builder()
                                .id(1)
                                .firstName("John")
                                .lastName("Doe")
                                .build();

                System.out.println("customer=" + customer);
                System.out.println("customer.fullName=" + customer.fullName());
            });
        });

        part("Comparing", () -> {
            /*
            Simplifies tests
             */
            example("Equal", () -> {
                final Customer customer1 = ImmutableCustomer.builder()
                        .id(1).firstName("John").lastName("Doe").build();

                final Customer customer2 = ImmutableCustomer.builder()
                        .id(1).firstName("John").lastName("Doe").build();

                assert customer1.equals(customer2); // Same attributes
                assert customer1.hashCode() == customer2.hashCode();
            });

            example("Non Equal", () -> {
                final Customer customer1 = ImmutableCustomer.builder()
                        .id(1).firstName("John").lastName("Doe").build();

                final Customer customer3 = ImmutableCustomer.builder()
                        .id(1).firstName("Paul").lastName("Martin").build();

                assert !customer1.equals(customer3); // Different attributes
                assert customer1.hashCode() != customer3.hashCode();
            });
        });

        part("Displaying", () -> {
            example("To String", () -> {
                final Customer customer =
                        ImmutableCustomer.builder()
                                .id(1)
                                .firstName("John")
                                .lastName("Doe")
                                .build();

                System.out.println(customer.toString());
            });

            /* @Redacted */
        });

        part("Preventing null and incompleteness", () -> {
            example("Build incomplete", () -> {
                final Customer customer = ImmutableCustomer.builder()
                        .id(1)
                        .build();

                System.out.println("customer=" + customer);
            });

            example("Build null", () -> {
                final Customer customer = ImmutableCustomer.builder()
                        .id(1)
                        .firstName(null)
                        .lastName("Martin")
                        .build();

                System.out.println("customer=" + customer);
            });

            example("With null", () -> {
                final Customer customer =
                        ImmutableCustomer.builder()
                                .id(1)
                                .firstName("Paul")
                                .lastName("Simpson")
                                .build();

                System.out.println("customer=" + customer);

                final Customer modifiedCustomer =
                        ImmutableCustomer.copyOf(customer).withFirstName(null);

                System.out.println("modifiedCustomer=" + modifiedCustomer);
            });

            example("Build from null", () -> {
                final Customer customer =
                        ImmutableCustomer.builder()
                                .id(1)
                                .firstName("Paul")
                                .lastName("Simpson")
                                .build();

                System.out.println("customer=" + customer);

                final Customer modifiedCustomer =
                        ImmutableCustomer.builder()
                                .from(customer)
                                .firstName(null)
                                .lastName("Martin")
                                .build();

                System.out.println("modifiedCustomer=" + modifiedCustomer);
            });
        });

        part("Optionality instead of null", () -> {
        });

        part("Ensuring invariants", () -> {
            example("Build inconsistent", () -> {
                final Customer customer =
                        ImmutableCustomer.builder()
                                .id(-1)
                                .firstName("Paul")
                                .lastName("Simpson")
                                .build();

                System.out.println("customer=" + customer);
            });

            example("With inconsistent", () -> {
                final Customer customer =
                        ImmutableCustomer.builder()
                                .id(1)
                                .firstName("Paul")
                                .lastName("Simpson")
                                .build();

                System.out.println("customer=" + customer);

                final Customer modifiedCustomer =
                        ImmutableCustomer.copyOf(customer).withFirstName(" Paul ");

                System.out.println("modifiedCustomer=" + modifiedCustomer);
            });

            example("Build from inconsistent", () -> {
                final Customer customer =
                        ImmutableCustomer.builder()
                                .id(1)
                                .firstName("Paul")
                                .lastName("Simpson")
                                .build();

                System.out.println("customer=" + customer);

                final Customer modifiedCustomer =
                        ImmutableCustomer.builder()
                                .from(customer)
                                .lastName("")
                                .build();

                System.out.println("modifiedCustomer=" + modifiedCustomer);
            });
        });

        part("Immutable Collections", () -> {
            example("Seq", () -> {
                final Seq<Integer> ids = List.of(1, 2, 3, 4, 5);

                final Seq<String> availableIds = ids
                        .prepend(0) // Add 0 at head of list
                        .append(6) // Add 6 as last element of list
                        .filter(i -> i % 2 == 0) // Keep only even numbers
                        .map(i -> "#" + i); // Transform to rank

                System.out.println(availableIds);
            });

            example("IndexedSeq", () -> {
                final IndexedSeq<String> commands = Vector.of(
                        "command", "ls", "pwd", "cd", "man");

                final IndexedSeq<String> availableCommands = commands
                        .tail()  // Drop head of list keeping only tail
                        .remove("man"); // Remove man command

                System.out.println(availableCommands);
            });

            example("Set", () -> {
                final Set<String> greetings = HashSet.of("hello", "goodbye");

                final Set<String> availableGreetings = greetings
                        .addAll(List.of("hi", "bye", "hello")); // Add more greetings

                System.out.println(availableGreetings);
            });

            example("Map", () -> {
                final Map<Integer, String> idToName = HashMap.ofEntries(
                        Map.entry(1, "Peter"),
                        Map.entry(2, "John"),
                        Map.entry(3, "Mary"),
                        Map.entry(4, "Kate"));

                final Map<Integer, String> updatedIdToName = idToName
                        .remove(1) // Remove entry with key 1
                        .put(5, "Bart") // Add entry
                        .mapValues(String::toUpperCase);

                System.out.println(updatedIdToName);
            });

            example("Compare by value", () -> {
                System.out.println(HashSet.of(1, 2, 3).equals(HashSet.of(3, 2, 1)));

                assert HashSet.of(1, 2, 3).equals(HashSet.of(3, 2, 11));
            });
        });

        part("Option", () -> {
            example("Present", () -> {
                final Option<String> maybeTitle = Option.some("Mister");

                final String displayedTitle = maybeTitle
                        .map(String::toUpperCase) // Transform value, as present
                        .getOrElse("<No Title>"); // Get value, as present

                System.out.println(displayedTitle);
            });

            example("Absent", () -> {
                final Option<String> maybeTitle = Option.none();

                final String displayedTitle = maybeTitle
                        .map(String::toUpperCase) // Does nothing, as absent
                        .getOrElse("<No Title>"); // Return parameter, as absent

                System.out.println(displayedTitle);
            });

            example("From Nullable (null)", () -> {
                final String nullableTitle = null;
                final Option<String> maybeTitle = Option.of(nullableTitle);
                System.out.println(maybeTitle);
            });

            example("From Nullable (non null)", () -> {
                final Option<String> maybeTitle = Option.none();
                final String nullableTitle = maybeTitle.getOrNull();
                System.out.println(nullableTitle);
            });
        });

        part("Class with Option Attribute", () -> {
            example("Create Without", () -> {
                final ImmutableCustomer customer = ImmutableCustomer.builder()
                        .id(1)
                        // Do no set optional attribute
                        .firstName("Paul")
                        .lastName("Simpson")
                        .build();

                System.out.println(customer);
            });

            example("Create With", () -> {
                final ImmutableCustomer customer = ImmutableCustomer.builder()
                        .id(1)
                        .title("Mister") // Set optional attribute
                        .firstName("Paul")
                        .lastName("Simpson")
                        .build();

                System.out.println(customer);
            });

            example("With Unset", () -> {
                final ImmutableCustomer customer = ImmutableCustomer.builder()
                        .id(1)
                        .title("Mister") // Set optional attribute
                        .firstName("Paul")
                        .lastName("Simpson")
                        .build();

                final ImmutableCustomer modifiedCustomer =
                        ImmutableCustomer.copyOf(customer).withTitle(Option.none());

                System.out.println(modifiedCustomer);
            });

            example("With Set", () -> {
                final ImmutableCustomer customer =
                        ImmutableCustomer.builder()
                                .id(1)
                                .firstName("Paul")
                                .lastName("Simpson")
                                .build();

                final ImmutableCustomer modifiedCustomer =
                        ImmutableCustomer.copyOf(customer).withTitle("Mister");

                System.out.println(modifiedCustomer);
            });

            example("Build from Unset", () -> {
                final ImmutableCustomer customer =
                        ImmutableCustomer.builder()
                                .id(1)
                                .title("Mister")
                                .firstName("Paul")
                                .lastName("Simpson")
                                .build();

                final ImmutableCustomer modifiedCustomer =
                        ImmutableCustomer.builder().from(customer)
                                .unsetTitle()
                                .build();

                System.out.println(modifiedCustomer);
            });

            example("Build Set", () -> {
                final ImmutableCustomer customer =
                        ImmutableCustomer.builder()
                                .id(1)
                                .firstName("Paul")
                                .lastName("Simpson")
                                .build();

                final ImmutableCustomer modifiedCustomer =
                        ImmutableCustomer.builder().from(customer)
                                .title("Miss")
                                .firstName("Paula")
                                .build();

                System.out.println(modifiedCustomer);
            });
        });


        part("Expressions", () -> {
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
                final Color color = Color.values()[new Random().nextInt(Color.values().length) + 1];

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

    }

    private static void part(final String name, final Runnable runnable) {
        System.out.println("************************************************************");
        System.out.println("*** " + name.toUpperCase());
        System.out.println("************************************************************");
        System.out.println();

        runnable.run();
    }

    private static void example(final String name, final Runnable runnable) {
        System.out.println("============================================================");
        System.out.println(name);
        System.out.println("------------------------------------------------------------");

        try {
            runnable.run();
        } catch (final Exception exc) {
            final StringWriter writer = new StringWriter();
            final PrintWriter out = new PrintWriter(writer);
            exc.printStackTrace(out);
            System.out.println(writer.toString());
        }

        System.out.println("============================================================");
        System.out.println();
    }
}

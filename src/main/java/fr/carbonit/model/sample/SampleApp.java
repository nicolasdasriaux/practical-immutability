package fr.carbonit.model.sample;

import java.io.PrintWriter;
import java.io.StringWriter;

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
                        Customer.builder()
                                .id(1)
                                .firstName("John")
                                .lastName("Doe")
                                .build();

                System.out.println("customer=" + customer);
            });

            example("With", () -> {
                final Customer customer =
                        Customer.builder()
                                .id(1)
                                .firstName("John")
                                .lastName("Doe")
                                .build();

                System.out.println("customer=" + customer);

                final Customer modifiedCustomer =
                        customer.withLastName("Martin");

                System.out.println("modifiedCustomer=" + modifiedCustomer);
            });

            example("Build from", () -> {
                final Customer customer =
                        Customer.builder()
                                .id(1)
                                .firstName("John")
                                .lastName("Doe")
                                .build();

                System.out.println("customer=" + customer);

                final Customer modifiedCustomer =
                        Customer.builder().from(customer)
                                .firstName("Paul")
                                .lastName("Martin")
                                .build();

                System.out.println("modifiedCustomer=" + modifiedCustomer);
            });
        });

        part("Calculating attributes from other attributes", () -> {
            example("Calculated attribute and uniform access principle", () -> {
                final Customer customer =
                        Customer.builder()
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
                final Customer customer1 = Customer.builder()
                        .id(1).firstName("John").lastName("Doe").build();

                final Customer customer2 = Customer.builder()
                        .id(1).firstName("John").lastName("Doe").build();

                assert customer1.equals(customer2); // Same attributes
                assert customer1.hashCode() == customer2.hashCode();
            });

            example("Non Equal", () -> {
                final Customer customer1 = Customer.builder()
                        .id(1).firstName("John").lastName("Doe").build();

                final Customer customer3 = Customer.builder()
                        .id(1).firstName("Paul").lastName("Martin").build();

                assert !customer1.equals(customer3); // Different attributes
                assert customer1.hashCode() != customer3.hashCode();
            });
        });

        part("Displaying", () -> {
            example("To String", () -> {
                final Customer customer =
                        Customer.builder()
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
                Customer customer = Customer.builder()
                        .id(1)
                        .build();

                System.out.println("customer=" + customer);
            });

            example("Build null", () -> {
                Customer customer = Customer.builder()
                        .id(1)
                        .firstName(null)
                        .lastName("Martin")
                        .build();

                System.out.println("customer=" + customer);
            });

            example("With null", () -> {
                final Customer customer =
                        Customer.builder()
                                .id(1)
                                .firstName("Paul")
                                .lastName("Simpson")
                                .build();

                System.out.println("customer=" + customer);

                final Customer modifiedCustomer =
                        customer.withFirstName(null);

                System.out.println("modifiedCustomer=" + modifiedCustomer);
            });

            example("Build from null", () -> {
                final Customer customer =
                        Customer.builder()
                                .id(1)
                                .firstName("Paul")
                                .lastName("Simpson")
                                .build();

                System.out.println("customer=" + customer);

                final Customer modifiedCustomer =
                        Customer.builder()
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
                        Customer.builder()
                                .id(-1)
                                .firstName("Paul")
                                .lastName("Simpson")
                                .build();

                System.out.println("customer=" + customer);
            });

            example("With inconsistent", () -> {
                final Customer customer =
                        Customer.builder()
                                .id(1)
                                .firstName("Paul")
                                .lastName("Simpson")
                                .build();

                System.out.println("customer=" + customer);

                final Customer modifiedCustomer =
                        customer.withFirstName(" Paul ");

                System.out.println("modifiedCustomer=" + modifiedCustomer);
            });

            example("Build from inconsistent", () -> {
                final Customer customer =
                        Customer.builder()
                                .id(1)
                                .firstName("Paul")
                                .lastName("Simpson")
                                .build();

                System.out.println("customer=" + customer);

                final Customer modifiedCustomer =
                        Customer.builder()
                                .from(customer)
                                .lastName("")
                                .build();

                System.out.println("modifiedCustomer=" + modifiedCustomer);
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

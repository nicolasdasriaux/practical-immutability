package fr.carbonit;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.carbonit.model.*;
import io.vavr.collection.*;
import io.vavr.control.Try;
import io.vavr.jackson.datatype.VavrModule;

import java.io.IOException;

public class App {
    public static void main(final String[] args) throws IOException {
        {
            final Customer customer = ImmutableCustomer.builder().id(1).firstName("Paul").lastName("Newman").build();

            Try.of(() ->
                    ImmutableCustomer.builder().id(1).build()
            ).failed().forEach(System.out::println);

            Try.of(() ->
                    ImmutableCustomer.builder().id(1).firstName(null).lastName("Newman").build()
            ).failed().forEach(System.out::println);


            Try.of(() ->
                    ImmutableCustomer.copyOf(customer).withFirstName(null)
            ).failed().forEach(System.out::println);

            Try.of(() ->
                    ImmutableCustomer.builder().from(customer).firstName(null).lastName("Johnson").build()
            ).failed().forEach(System.out::println);

        }

        // ------ Seq = Collection

        final Seq<Integer> ids = List.of(1, 2, 3, 4, 6, 7, 8, 9);

        final Seq<Integer> updatedIds = ids
                .prepend(0)
                .append(10)
                .filter(i -> i % 2 == 0)
                .removeFirst(i -> i == 2);

        dump("ids", ids);
        dump("updatedIds", updatedIds);

        final Seq<String> names = ids.map(i -> "Name " + i);
        dump("names", names);

        // ------ IndexedSeq = List

        final IndexedSeq<String> commands = Vector.of("ls", "pwd", "cd");
        final IndexedSeq<String> manCommands = commands.map(cmd -> "man " + cmd);
        dump("commands", commands);
        dump("manCommands", manCommands);

        // ------ Set = Set

        final Set<String> greetings = HashSet.of("hello", "goodbye");
        dump("greetings.contains(\"hi\")", greetings.contains("hi"));

        // ------ Map = Map

        final Map<Integer, String> idToName = HashMap.ofEntries(
                Map.entry(1, "Peter"),
                Map.entry(2, "John"),
                Map.entry(3, "Mary"),
                Map.entry(4, "Kate")
        );

        final Map<Integer, String> updatedIdToName = idToName
                .remove(1)
                .put(5, "Bart")
                .mapValues(String::toUpperCase);

        dump("idToName", idToName);
        dump("updatedIdToName", updatedIdToName);

        final Seq<Item> items = Vector.of(
                ImmutableItem.builder().id(3).name("Table").build(),
                ImmutableItem.builder().id(4).name("Fork").build(),
                ImmutableItem.builder().id(5).name("Knife").build()
        );

        final Seq<Item> updatedItems = items
                .removeFirst(item -> item.id() == 3)
                .append(ImmutableItem.builder().id(6).name("Plate").build());

        dump("items", items);
        dump("updatedItems", updatedItems);

        final Customer customer1 = ImmutableCustomer.builder()
                .id(1)
                .setValueTitle("Mr.")
                .firstName("John")
                .lastName("Doe")
                .addOrder(ImmutableOrder.builder()
                        .id(1)
                        .addItem(ImmutableItem.of(1, "Ball"))
                        .addItem(ImmutableItem.of(2, "Pen"))
                        .build()
                )
                .addOrder(ImmutableOrder.builder()
                        .id(2)
                        .items(items)
                        .build()
                )
                .build();

        final Customer customer2 = ImmutableCustomer.copyOf(customer1).withLastName("Martin");

        final Customer customer3 = ImmutableCustomer.builder().from(customer1)
                .firstName("Paula")
                .lastName("Martin")
                .build();

        dump("customer1", customer1);
        dump("customer1.fullName", customer1.fullName());
        dump("customer2", customer2);
        dump("customer3", customer3);

        final ObjectMapper mapper = new ObjectMapper().registerModule(new VavrModule());
        final String json = mapper.writerFor(Customer.class).writeValueAsString(customer1);
        final Customer customer4 = mapper.readerFor(Customer.class).readValue(json);

        dump("json", json);
        dump("customer4", customer4);
    }

    private static void dump(final String name, final Object value) {
        System.out.println(name + "=" + value);
    }
}

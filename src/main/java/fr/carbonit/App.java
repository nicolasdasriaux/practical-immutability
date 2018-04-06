package fr.carbonit;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.carbonit.model.Customer;
import fr.carbonit.model.Item;
import fr.carbonit.model.Order;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.jackson.datatype.VavrModule;

import java.io.IOException;

public class App {
    public static void main(final String[] args) throws IOException {
        final Seq<Integer> ids1 = List.of(1, 2, 3);
        final Seq<Integer> ids2 = ids1.prepend(0);
        final Seq<String> names = ids1.map(i -> "Name " + i);

        dump("ids1", ids1);
        dump("ids2", ids2);
        dump("names", names);

        final Seq<Item> items1 = List.of(
                Item.builder().id(3).name("Table").build(),
                Item.builder().id(4).name("Fork").build(),
                Item.builder().id(5).name("Knife").build()
        );

        final Seq<Item> items2 = items1.removeFirst(item -> item.id() == 3);

        dump("items1", items1);
        dump("items2", items2);

        final Customer customer1 = Customer.builder()
                .id(1)
                .setValueTitle("Mr.")
                .firstName("John")
                .lastName("Doe")
                .addOrder(Order.builder()
                        .id(1)
                        .addItem(Item.of(1, "Ball"))
                        .addItem(Item.of(2, "Pen"))
                        .build()
                )
                .addOrder(Order.builder()
                        .id(2)
                        .items(items1)
                        .build()
                )
                .build();

        final Customer customer2 = customer1.withLastName("Martin");

        final Customer customer3 = Customer.builder().from(customer1)
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

package fr.carbonit;

import fr.carbonit.model.*;
import io.vavr.collection.List;
import io.vavr.collection.Seq;

public class App {
    public static void main(final String[] args) {
        final Seq<Integer> ids1 = List.of(1, 2, 3);
        final Seq<Integer> ids2 = ids1.prepend(0);
        final Seq<String> names = ids1.map(i -> "Name " + i);

        dump("ids1", ids1);
        dump("ids2", ids2);
        dump("names", names);

        final Seq<Item> items1 = List.of(
                ImmutableItem.of(3, "Table"),
                ImmutableItem.of(4, "Fork"),
                ImmutableItem.of(5, "Knife")
        );

        final Seq<Item> items2 = items1.removeFirst(item -> item.id() == 3);

        dump("items1", items1);
        dump("items2", items2);

        final Customer customer1 = ImmutableCustomer.builder()
                .id(1)
                .setValueTitle("Mr.")
                .firstName("Paul")
                .lastName("Dupond")
                .addOrder(ImmutableOrder.builder()
                        .id(1)
                        .addItem(ImmutableItem.of(1, "Ball"))
                        .addItem(ImmutableItem.of(2, "Pen"))
                        .build()
                )
                .addOrder(ImmutableOrder.builder()
                        .id(2)
                        .items(items1)
                        .build()
                )
                .build();

        final Customer customer2 = ImmutableCustomer.copyOf(customer1).withLastName("Martin");

        final Customer customer3 = ImmutableCustomer.builder()
                .from(customer1)
                .firstName("Paula")
                .lastName("Martin")
                .build();

        dump("customer1", customer1);
        dump("customer1.fullName", customer1.fullName());
        dump("customer2", customer2);
        dump("customer3", customer3);
    }

    private static void dump(final String name, final Object value) {
        System.out.println(name + "=" + value);
    }
}

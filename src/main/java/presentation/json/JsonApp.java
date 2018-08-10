package presentation.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.jackson.datatype.VavrModule;

import java.io.IOException;

public class JsonApp {
    public static void main(final String[] args) throws IOException {
        final Customer customer = ImmutableCustomer.builder()
                .id(1)
                .title("Mr.")
                .firstName("John")
                .lastName("Doe")
                .addOrder(ImmutableOrder.builder()
                        .id(1)
                        .addItem(ImmutableItem.builder().id(1).name("Ball").build())
                        .addItem(ImmutableItem.builder().id(2).name("Pen").build())
                        .build()
                )
                .addOrder(ImmutableOrder.builder()
                        .id(2)
                        .addItem(ImmutableItem.builder().id(3).name("Table").build())
                        .addItem(ImmutableItem.builder().id(4).name("Fork").build())
                        .addItem(ImmutableItem.builder().id(5).name("Knife").build())
                        .build()
                )
                .build();


        final ObjectMapper mapper = new ObjectMapper().registerModule(new VavrModule());
        final String serializedCustomer = mapper.writerFor(Customer.class).writeValueAsString(customer);
        System.out.println(serializedCustomer);
        final Customer deserializedCustomer = mapper.readerFor(Customer.class).readValue(serializedCustomer);
        System.out.println(deserializedCustomer);
    }
}

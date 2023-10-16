package practicalimmutability.presentation.option;


import io.vavr.control.Option;

public class CustomerApp {
    public static void main(String[] args) {
        final Customer customer = Customer.builder()
                .id(1)
                .title(Option.some("Mr"))
                .firstName("Peter")
                .lastName("Marble")
                .build();

        System.out.println(customer);
    }
}

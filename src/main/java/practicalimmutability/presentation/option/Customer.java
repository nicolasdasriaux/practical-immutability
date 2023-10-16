package practicalimmutability.presentation.option;

import practicalimmutability.presentation.Preconditions;
import practicalimmutability.presentation.StringValidation;

import io.vavr.control.Option;
import lombok.Builder;
import lombok.With;

import java.util.Objects;

@Builder(toBuilder = true)
@With
public record Customer(Option<String> title, int id, String firstName, String lastName) {
    public Customer {
        Preconditions.requireNonNull(title, "title");
        Preconditions.requireNonNull(firstName, "firstName");
        Preconditions.requireNonNull(lastName, "lastName");

        Preconditions.require(title, "title", o -> o.forAll(Objects::nonNull),
                "'%s' should not contain null"::formatted);

        Preconditions.require(id, "id", i -> i > 0,
                "'%s' should be greater than 0 (%d)"::formatted);

        Preconditions.require(firstName, "firstName", StringValidation::isTrimmedAndNonEmpty,
                "'%s' should be trimmed and non-empty (\"%s\")"::formatted);

        Preconditions.require(lastName, "lastName", StringValidation::isTrimmedAndNonEmpty,
                "'%s' should be trimmed and non-empty (\"%s\")"::formatted);
    }

    public String fullName() {
        return "%s %s".formatted(firstName(), lastName());
    }
}

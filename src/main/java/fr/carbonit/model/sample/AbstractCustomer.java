package fr.carbonit.model.sample;

import com.google.common.base.Preconditions;
import fr.carbonit.model.StringValidation;
import io.vavr.control.Option;
import org.immutables.value.Value;

@Value.Immutable
public abstract class AbstractCustomer {
    abstract Option<String> title();
    abstract int id();
    abstract String firstName();
    abstract String lastName();

    public String fullName() {
        return firstName() + " " + lastName();
    }

    @Value.Check
    protected void check() {
        Preconditions.checkState(
                id() >= 1,
                "ID should be a least 1 (" + id() + ")");

        Preconditions.checkState(
                StringValidation.isTrimmedAndNonEmpty(firstName()),
                "First Name should be trimmed and non empty (" + firstName() + ")");

        Preconditions.checkState(
                StringValidation.isTrimmedAndNonEmpty(lastName()),
                "Last Name should be trimmed and non empty (" + lastName() + ")");
    }
}

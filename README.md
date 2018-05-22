
---

# Immutable Classes
## with _Immutables_

---

# Declaring an Immutable Class

``` java
@Value.Immutable
public abstract class AbstractCustomer {
    abstract int id();
    abstract String firstName();
    abstract String lastName();
}
```

---

# Creating an Instance

``` java
final Customer customer =
    Customer.builder()
        .id(1)
        .firstName("John")
        .lastName("Doe")
        .build();
```

---

# Modifying an Instance (one attribute)

``` java
final Customer modifiedCustomer =
    customer.withLastName("Martin");
```

* Returns a **new instance** that is modified
* Previous instance remains unchanged
* Only one field modified

---

# Modifying an Instance (multiple attributes)

``` java
final Customer modifiedCustomer =
    Customer.builder().from(customer)
        .firstName("Paul")
        .lastName("Martin")
        .build();
```

* Several attributes modified with no intermediary instances

---

# Calculating an Attribute from Other Attributes

```java 
@Value.Immutable
public abstract class AbstractCustomer {
    ...
    public String fullName() {
        return firstName() + " " + lastName();
    }
}
```

* From the outside, calculated attribute looks exactly the same as other attributes
* Uniform access principle

---

# Reminder on Comparing

* By **value**, comparing **attributes** of object
* By **reference**, comparing **object identity** (pointer, address, reference ...)

---

# Comparing Immutable Instances

* Immutable class implies comparison by value
* _Immutables_ generates consistent
  * `.equals(other)`
  * `.hashCode()`
* Can ultimately be customized by code
* Greatly simplifies unit test assertions :thumbsup:

---

# Comparing Immutable Instances

``` java
final Customer customer1 = Customer.builder()
        .id(1).firstName("John").lastName("Doe").build();

final Customer customer2 = Customer.builder()
        .id(1).firstName("John").lastName("Doe").build();

assert customer1.equals(customer2); // Same attributes
assert customer1.hashCode() == customer2.hashCode();

final Customer customer3 = Customer.builder()
        .id(1).firstName("Paul").lastName("Martin").build();

assert !customer1.equals(customer3); // Different attributes
assert customer1.hashCode() != customer3.hashCode();
```
---

# Printing Immutable Instance

* _Immutables_ generates useful `.toString()` automatically 
* Confidential attributes can be hidden from `.toString()` using `@Redacted`
* Can ultimately be overridden by code
* Simplifies logging :thumbsup:
* Simplifies unit test debugging :thumbsup:
  * Compare with clipboard trick

---

# Printing Immutable Instance

``` java
System.out.println(customer.toString());
```

Will output something like

`Customer{id=1, firstName=John, lastName=Doe}`

---

# Attributes should never be `null`

* `null` is evil! :smiling_imp:
* _Immutabled_ rejects `null` by default.
* Optionality of field should be explicit using an **option type**
  * _Vavr_ `Option` is a good option :wink:
  * Java `Optional` is supported, but is not so good
  * More later

---

# Immutables ensures presence of values when creating an instance

``` java
Customer.builder().id(1).build()
```

Will fail with an exception

`java.lang.IllegalStateException: Cannot build Customer, some of required attributes are not set [firstName, lastName]`

---

# Immutables prevents introduction of `null` values

``` java
Customer.builder()
    .id(1).firstName(null).lastName("Martin")
    .build()

customer.withFirstName(null)

Customer.builder().from(customer)
    .firstName(null).lastName("Martin")
    .build()
````

Will all fail with an exception

`java.lang.NullPointerException: firstName`

---

---





---
# final (non-final) vs immutable (mutable)

# Immutable Collections (with Vavr)


### Mutable Collections

### Unmodifiable Collections

### Immutable Collections

---

Vavr Immutable Collections
==========================

* Immutable `Seq` is similar to mutable `java.util.Collection`.
* Immutable `IndexedSeq` is similar to mutable `java.util.List`.
* Immutable `Set` is similar to mutable `java.util.Set`.
* Immutable `Map` is similar to mutable `java.util.Map`.

Adaptateur

Différ

---

Using `Seq`
===========

Examples

---

Using `IndexedSeq`
==================

Examples

---

Using `Set`
===========

Examples

---

Using `Map`
===========

Examples

---

Optional Values (with Vavr)
===========================

### `null` vs `Option

### Using `Option` (examples)

---

Immutable Classes (with Immutables)
-----------------------------------

---

Implementing Immutable Class
============================

* `@Value.Immutable`
* Computed attribute example
* `@Value.Parameter`


# Enforcing Invariants

---

# Emulating Expressions

* `final` everywhere (local variable, parameter, enhanced `for` loop, `catch`)
* `if` and `? :`
* `switch`
* No non-terminal `return` (equivalent to `goto`)



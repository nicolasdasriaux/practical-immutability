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

---

# Creating Instance

``` java
final Customer customer =
    Customer.builder()
        .id(1)
        .firstName("John")
        .lastName("Doe")
        .build();
```

---

# Modifying Instance

``` java
final Customer modifiedCustomer =
    customer.withLastName("Martin");
```

* Returns a new instance that is modified
* Previous instance remains unchanged
* Only one field modified

---

# Modifying Instance

``` java
final Customer modifiedCustomer =
    Customer.builder().from(customer)
        .firstName("Paul")
        .lastName("Martin")
        .build();
```

* Several field modified with no intermediary instances

---

# Fields should never be `null`

* Optionality of field should be explicit using `Option` for the field

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

# Comparing by value (not by reference)

* Comparison
  - by value, comparing **fields** of object
  - by reference, comparing **address** of object 
* Immutables implements comparison by value consistently
  * `.equals(other)`
  * `.hashCode()`
* Simplifies unit tests

---

# Printing Instances

* `.toString()` is implemented automatically by Immutables
* Can be overridden by code.
* Simplifies logging and debugging
* Confidential fields can be hidden using 

---

# Enforcing Invariants

---

# Emulating Expressions

* `final` everywhere (local variable, parameter, enhanced `for` loop, `catch`)
* `if` and `? :`
* `switch`
* No non-terminal `return` (equivalent to `goto`)



autoscale: true

# _Immutables_

* zzzzz
* zzzzz
* zzzzz

---

# _Vavr_

* zzzzz
* zzzzz
* zzzzz

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
  * `.equals(other)` :thumbsup:
  * `.hashCode()` :thumbsup:
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

* _Immutables_ generates useful `.toString()` automatically :thumbsup:
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

```
Customer{id=1, firstName=John, lastName=Doe}
```


---

# Preventing `null` attributes

* Attributes should never be `null`
  * `null` is evil! :smiling_imp:
* _Immutables_ will rejects `null` by default :thumbsup:
* Optional attribute should be explicit using an **option type**
  * _Vavr_ `Option` is a good option :wink:
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

# Ensuring Consistency

* Proper encapsulation requires explicit **class invariant**
  * A set of rules that applies to attributes of class
  * and with which all instances must comply
* _Immutables_ allows to write a class invariant and will enforce it automatically :thumbsup:
* _Guava_ also provides `Preconditions` to help

---

# Expressing Class Invariant

``` java
@Value.Immutable
public abstract class AbstractCustomer {
    ...
    
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
```

---

# Ensuring Invariant at Creation

``` java
final Customer customer =
        Customer.builder()
                .id(-1)
                .firstName("Paul")
                .lastName("Simpson")
                .build();
```

Will fail with an exception

`java.lang.IllegalStateException: ID should be a least 1 (-1)`

---

# Ensuring Invariant at Modification

``` java
final Customer modifiedCustomer =
        customer.withFirstName(" Paul ");
```

Will fail with an exception

`java.lang.IllegalStateException: First Name should be trimmed and non empty ( Paul )`

---

# Ensuring Invariant at Modification

``` java
final Customer modifiedCustomer =
        Customer.builder()
                .from(customer)
                .lastName("")
                .build();
```

Will fail with an exception

`java.lang.IllegalStateException: Last Name should be trimmed and non empty ()`

---

# Immutable Collections
## with _Vavr_

---

# Immutable Collections

* A method that transforms an immutable collection
  * always return a **new collection** with the transformation applied
  * and keep the **original collection unchanged**
* Immutable collections **compare by value**
  * `.equals(other)` and `.hashCode()` are consistently implemented
* In principle, they should not accept `null` as element
  * but Vavr does :imp:
* Immutable Collection are special efficient data structures called _persistent data structures_

---

# _Vavr_ Immutable Collections

| Mutable (Java) | Mutable (_Vavr_) |
|----------------|------------------|
| `Collection`   | `Seq`            |
| `List`         | `IndexedSeq`     |
| `Set`          | `Set`            |
| `Map`          | `Map`            |

_Vavr_ collections can easily be wrapped back to Java collections using `.toJavaXXX()` methods

---

# Immutable Sequence

``` java
final Seq<Integer> ids = List.of(1, 2, 3, 4, 5);

final Seq<String> availableIds = ids
        .prepend(0) // Add 0 at head of list
        .append(6) // Add 6 as last element of list
        .filter(i -> i % 2 == 0) // Keep only even numbers
        .map(i -> "#" + i); // Transform to rank
```

Will output

```
List(#0, #2, #4, #6)
```

---

# Immutable Indexed Sequence

``` java
final IndexedSeq<String> commands = Vector.of(
        "command", "ls", "pwd", "cd", "man");

final IndexedSeq<String> availableCommands = commands
        .tail()  // Drop head of list keeping only tail
        .remove("man"); // Remove man command
```

Will output

```
Vector(ls, pwd, cd)
```

---

# Immutable Set

``` java
final Set<String> greetings = HashSet.of("hello", "goodbye");

final Set<String> availableGreetings = greetings
        .addAll(List.of("hi", "bye", "hello")); // Add more greetings
```

Will output

```
HashSet(hi, bye, goodbye, hello)
```
 
---

# Immutable Map

``` java
final Map<Integer, String> idToName = HashMap.ofEntries(
        Map.entry(1, "Peter"),
        Map.entry(2, "John"),
        Map.entry(3, "Mary"),
        Map.entry(4, "Kate"));

final Map<Integer, String> updatedIdToName = idToName
        .remove(1) // Remove entry with key 1
        .put(5, "Bart") // Add entry
        .mapValues(String::toUpperCase);
```

Result will output as

```
HashMap((2, JOHN), (3, MARY), (4, KATE), (5, BART))
```

---

# Immutable Option Type
### with _Vavr_

---

Optional Values (with Vavr)
===========================

### `null` vs `Option

### Using `Option` (examples)

---

# Emulating Expressions

* `final` everywhere (local variable, parameter, enhanced `for` loop, `catch`)
* `if` and `? :`
* `switch`
* No non-terminal `return` (equivalent to `goto`)

---

# Immutable All the Way
## with _Immutables_ and _Vavr_

---

# final (non-final) vs immutable (mutable)

# Immutable Collections (with Vavr)

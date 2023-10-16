autoscale: true
footer: Practical Immutability
slidenumbers: true

# Practical
# [fit] **Immutability**
## [fit] in Java with _Lombok_ and _Vavr_

---

# What Object Oriented Programming is

* **Object Identity**
  - Uniquely identify an instance (pointer, reference, address ...)
* **Inheritance** and **polymorphism**
  - Classify and specialize behavior in classifications
* **Encapsulation**
  - Ensure integrity of object :thumbsup:
  - Essence of OOP

---

# What Encapsulation is

* A **constructor** should either
  - :thumbsup: construct a **consistent** instance from its parameters
  - :bomb: or just fail if it cannot
* Applied on a consistent instance, a **method** should  either
  - :thumbsup: modify the object to another **consistent** state
  - :bomb: or just fail if it cannot
* Protection of consistency by constructors and methods ensures integrity of object
* Consistency can be described by a set of integrity rules called **class invariant**

---

# [fit] Setters `==` No Encapsulation at All `==` No OOP

```java
public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    public Customer() {}
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
}
```

* What are the integrity rules? How are they protected?
* This is structured programming, it works, but this is not OOP

---

# OOP Revisited

* **Encapsulation is not optional in OOP**
* If you cannot describe (and protect) class invariant, there is no class encapsulation
* Sure, there exists **classes with very weak invariant**:
  - _Forms_ which are never guaranteed to be consistent except after validation
  - JPA entity annotated with `@Entity` :broken_heart:
  - Or anything similar coming from an external system
* OOP does not require mutability and it works very well with immutability

---

# _Lombok_

...

---

# _Vavr_

> Vavr core is a functional library for Java.
-- From [http://www.vavr.io](http://www.vavr.io)

* Formerly known as _JavaSlang_
* Provides **immutable collections**
* Also provides functions and control structures (such as `Option`)
* Fully interoperable with Java collections and `Optional`
* Requires Java 8 or higher

---

# Immutable Classes
## with _Java_ `record` 

---

# Immutable Class

* **Constructor** returns a new object
* **Methods** do not modify the object but return a **new object** with the modifications applied instead
* For an immutable class, _Lombok_ may be used to generate
  - a `Builder` to create and modify instances :thumbsup:
  - a set of `.withXXX(xxx)` methods to modify instances :thumbsup:

---

# Declaring an Immutable Class

```java
@Builder(toBuilder = true)
@With
public record Customer(int id, String firstName, String lastName) {
    // ...
}
```

---

# Creating an Instance

```java
final Customer customer =
        Customer.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .build();
```

---

# Modifying an Instance (one attribute)

```java
final Customer modifiedCustomer =
        customer.withLastName("Martin");
```

* Returns a **new instance** that is modified
* Previous instance remains unchanged
* Only **one attribute** modified

---

# Modifying an Instance (multiple attributes)

```java
final Customer modifiedCustomer =
        customer.toBuilder()
                .firstName("Paul")
                .lastName("Martin")
                .build();
```

* Several attributes modified with no intermediary instances
* Also allows modifying **multiple attributes** that should remain **consistent** with each other

---

# Calculating an Attribute from Other Attributes

```java
@Builder(toBuilder = true)
@With
public record Customer(int id, String firstName, String lastName) { // ...
    public String fullName() {
        return "%s %s".formatted(firstName(), lastName());
    }
}
```

* From the outside, calculated attribute looks exactly the same as other attributes :thumbsup:
* **Uniform access principle**

---

# Reminder on Comparing

* By **value**, comparing **attributes** of object
* By **reference**, comparing **object identity** (pointer, address, reference ...)

---

# Comparing Immutable Instances

* Immutable class implies **comparison by value**
* `record` generates consistent
  - `.equals(other)` :thumbsup:
  - `.hashCode()` :thumbsup:
* Can ultimately be customized by code
* Greatly simplifies unit test assertions :thumbsup:

---

# Comparing Immutable Instances

```java
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

* `record` generates useful `.toString()` automatically :thumbsup:
* Can ultimately be overridden by code
* Simplifies logging :thumbsup:
* Simplifies unit test debugging :thumbsup:
  - Compare with clipboard trick

---

# Printing Immutable Instance

```java
System.out.println(customer.toString());
```

Will output something like

```
Customer[id=1, firstName=John, lastName=Doe]
```

---

# Preventing `null` attributes

* Attributes should never be `null`
  - `null` is evil! :smiling_imp:
* Check against `null` in `record` compact constructor
* Optional attribute should be explicit using an **option type**
  - _Vavr_ `Option` is a good ... option :wink:
  - More later

---

# Checking against `null`

```java
@Builder(toBuilder = true)
@With
public record Customer(int id, String firstName, String lastName) {
    public Customer {
        Preconditions.requireNonNull(firstName, "firstName");
        Preconditions.requireNonNull(lastName, "lastName");
        // ...
    } // ...
}
```

---

# Ensuring Consistency

* Proper encapsulation requires explicit **class invariant**
  - A set of rules that applies to attributes of class
  - and with which all instances must comply
* Check class invariant in `record` compact constructor

---

# Checking Class Invariant

```java
@Builder(toBuilder = true)
@With
public record Customer(int id, String firstName, String lastName) {
    public Customer {
        // ...
        Preconditions.require(id, "id", i -> i > 0,
                "'%s' should be greater than 0 (%d)"::formatted);

        Preconditions.require(firstName, "firstName", StringValidation::isTrimmedAndNonEmpty,
                "'%s' should be trimmed and non-empty (\"%s\")"::formatted);

        Preconditions.require(lastName, "lastName", StringValidation::isTrimmedAndNonEmpty, 
                "'%s' should be trimmed and non-empty (\"%s\")"::formatted);
  } // ...
}
```

---

# Immutable Collections
## with _Vavr_

---

# Immutable Collections

* A method that transforms an immutable collection
  - always return a **new collection** with the transformation applied
  - and keep the **original collection unchanged**
* Immutable collections **compare by value**
  - _Vavr_ implements `.equals(other)` and `.hashCode()` consistently :thumbsup:
* In principle, they **should not accept `null`** as element
  - but _Vavr_ does :imp:
* Immutable collections are special efficient data structures called **persistent data structures**

---

# _Vavr_ Immutable Collections

| Mutable (Java) | Immutable (_Vavr_) |
|----------------|--------------------|
| `Collection`   | `Seq`              |
| `List`         | `IndexedSeq`       |
| `Set`          | `Set`              |
| `Map`          | `Map`              |

* Collections can be wrapped
  - from Java to _Vavr_ using `.ofAll(...)` methods
  - and from _Vavr_ to Java using `.toJavaXXX()` methods

---

# Immutable Sequence

```java
final Seq<Integer> ids = List.of(1, 2, 3, 4, 5);

final Seq<String> availableIds = ids
        .prepend(0) // Add 0 at head of list
        .append(6) // Add 6 as last element of list
        .filter(i -> i % 2 == 0) // Keep only even numbers
        .map(i -> "#" + i); // Transform to rank
```

`availableIds` will print as

```
List(#0, #2, #4, #6)
```

---

# Immutable Indexed Sequence

```java
final IndexedSeq<String> commands = Vector.of(
        "command", "ls", "pwd", "cd", "man");

final IndexedSeq<String> availableCommands = commands
        .tail() // Drop head of list keeping only tail
        .remove("man"); // Remove man command
```

`availableCommands` will print as

```
Vector(ls, pwd, cd)
```

---

# Immutable Set

```java
final Set<String> greetings = HashSet.of("hello", "goodbye");

final Set<String> availableGreetings = greetings
        .addAll(List.of("hi", "bye", "hello")); // Add more greetings
```

`availableGreetings` will print as

```
HashSet(hi, bye, goodbye, hello)
```
 
---

# Immutable Map

```java
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

`updatedIdToName` will print as

```
HashMap((2, JOHN), (3, MARY), (4, KATE), (5, BART))
```

---

# Immutable Option Type
## with _Vavr_

---

# Option Type

* An option type is a generic type such as _Vavr_ `Option<T>` that models the **presence** or the **absence** of a value of type `T`.
* Options **compare by value** :thumbsup:
* In principle, options **should not accept `null`** as present value
  - but _Vavr_ does :imp:

___

# Present Value (`some`)

```java
final Option<String> maybeTitle = Option.some("Mister");

final String displayedTitle = maybeTitle
        .map(String::toUpperCase) // Transform value, as present
        .getOrElse("<No Title>"); // Get value, as present
```

`displayedTitle` will print as

```
MISTER
```

---

# Absent Value (`none`)

```java
final Option<String> maybeTitle = Option.none();

final String displayedTitle = maybeTitle
        .map(String::toUpperCase) // Does nothing, as absent
        .getOrElse("<No Title>"); // Return parameter, as absent

```

`displayedTitle` will print as

```
<No Title>
```

---

# Bridging with Nullable

From nullable to `Option`

```java
final Option<String> maybeTitle =
        Option.of(nullableTitle);
```

From `Option` to nullable

```java
final String nullableTitle =
        maybeTitle.getOrNull();
```

---

# Immutable<br>from Classes to Collections
## with _Immutables_ and _Vavr_

---

# `Customer` with an Optional Title

```java
@Builder(toBuilder = true)
@With
public record Customer(Option<String> title, int id, String firstName, String lastName) {
    // ...
}
```

---

# Preventing `null` in Title `Option`

```java
@Builder(toBuilder = true)
@With
public record Customer(Option<String> title, int id, String firstName, String lastName) {
    public Customer {
        // ...
        Preconditions.require(title, "title", o -> o.forAll(Objects::nonNull),
                "'%s' should not contain null"::formatted);
        // ...
    }
}
```

---

# `TodoList` class

```java
public record TodoList(String name, @With(AccessLevel.PRIVATE) Seq<Todo> todos) {
    // ...
    public static TodoList of(String name) {
        return new TodoList(name, List.empty());
    }
    // ...
}
```

---

# `TodoList` Invariant

```java
public record TodoList(String name, @With(AccessLevel.PRIVATE) Seq<Todo> todos) {
    public TodoList {
        Preconditions.requireNonNull(name, "name");
        Preconditions.requireNonNull(todos, "todos");

        Preconditions.require(name, "name", StringValidation::isTrimmedAndNonEmpty,
                "'%s' should be trimmed and non-empty (\"%s\")"::formatted);

        Preconditions.require(todos, "todos", l -> l.forAll(Objects::nonNull),
                "'%s' elements should all be non null (%s)"::formatted);
    }
    // ...
}
```

---

# `Todo` class

```java
public record Todo(int id, String name, @With(AccessLevel.PRIVATE) boolean done) {
    // ...
    public Todo markAsDone() {
        return this.withDone(true);
    }

    public static Todo of(int id, String name) {
        return new Todo(id, name, false);
    }
}
```

---

# `Todo` Invariant

```java
public record Todo(int id, String name, @With(AccessLevel.PRIVATE) boolean done) {
    public Todo {
        Preconditions.requireNonNull(name, "name");

        Preconditions.require(id, "id", i -> i > 0,
                "'%s' should be greater than 0 (%d)"::formatted);

        Preconditions.require(name, "name", StringValidation::isTrimmedAndNonEmpty,
                "'%s' should be trimmed and non-empty (\"%s\")"::formatted);
    }
    // ...
}
```
---

# Adding and Removing `Todo`


```java
public record TodoList(String name, @With(AccessLevel.PRIVATE) Seq<Todo> todos) {
    // ...
    public TodoList addTodo(Todo todo) {
        Seq<Todo> modifiedTodos = this.todos().append(todo);
        return this.withTodos(modifiedTodos);
    }

    public TodoList removeTodo(int todoId) {
        final Seq<Todo> modifiedTodos = this.todos().removeFirst(todo -> todo.id() == todoId);
        return this.withTodos(modifiedTodos);
    }
    // ...
}
```

---

# Marking `Todo` as Done

```java
public record TodoList(String name, @With(AccessLevel.PRIVATE) Seq<Todo> todos) {
    // ...
    public TodoList markTodoAsDone(int todoId) {
        final int todoIndex = this.todos().indexWhere(todo -> todo.id() == todoId);

        if (todoIndex >= 0) {
            final Seq<Todo> modifiedTodos = this.todos().update(todoIndex, Todo::markAsDone);
            return this.withTodos(modifiedTodos);
        } else {
            return this;
        }
    }
    // ...
}
```

---

# Counting Pending and Done `Todo`s

```java
public record TodoList(String name, @With(AccessLevel.PRIVATE) Seq<Todo> todos) {
    // ...
    public int pendingCount() {
        return todos().count(Predicate.not(Todo::done));
    }
  
    public int doneCount() {
        return todos().count(Todo::done);
    }
}
```

---

# Creating and Manipulating `TodoList`

```java
final TodoList todoList = TodoList.of("Food")
        .addTodo(Todo.of(1, "Leek"))
        .addTodo(Todo.of(2, "Turnip"))
        .addTodo(Todo.of(3, "Cabbage"));

final TodoList modifiedTodoList = todoList
        .markTodoAsDone(3)
        .removeTodo(2);
```

---

# But there is more to immutability than objects, collections and options

---

# Immutability of Variables

* Mutability of **variables** `!=` Mutability of **objects**
* Immutability of **objects**
  - Cannot mutate the fields of the object or collection
  - As seen so far
* Immutability of **variables** (local variable, parameter)
  - Cannot change the value (or reference) contained in the variable
  - `final` vs. ~~`final`~~

---

# Mutability Combinations

|                          | Immutable Object             | Mutable Object                                          |
|--------------------------|------------------------------|---------------------------------------------------------|
| **`final` Variable**     | :smile:                      | :neutral_face: if stricly local<br/>:fearful: otherwise |
| **non-`final` Variable** | :neutral_face: stricly local | :imp:                                                   |

---

# Expressions
## in Java

---

# Expressions vs. Instructions

* An **expression** evaluates to a value
  - Value can be directly assigned to a `final` variable
  - Expressions, when _pure_ :innocent:, do not cause any side-effect
* An **instruction** does something and has no value
  - Instructions always cause side-effects

---

# `final`, `final` Everywhere

* As many `final` as possible to **reduce moving parts**
* Somewhat controversial for other than local variables

| Type of variable             | Benefit of `final`                                                      |
|------------------------------|-------------------------------------------------------------------------|
| Local variable               | Emulates **expressions** :thumbsup:<br/>Prevents confusing reassignment |
| Parameter                    | Prevents rare reassignment                                              |
| `for` enhanced loop variable | Prevents rare reassignment                                              |
| `catch` clause variable      | Prevents rare reassignment                                              |

---

# ... `?` ... `:` ... Expression

```java
final String status = enabled ? "On" : "Off";
```

* An actual conditional expression!
* Only one of that kind in Java
* Only for very simple one-liners

---

# Emulating `if` Expression

````java
final String mood; // No default value

// Every branch either assigns value or fails
// Compiler is happy
if (1 <= mark && mark <= 3) {
    mood = "Bad";
} else if (mark == 4) {
    mood = "OK";
} else if (5 <= mark && mark <= 7) {
    mood ="Good";
} else {
    throw new AssertionError("Unexpected mark (" + mark + ")");
}
````

---

# `switch` Expression

```java
final int mark = switch (color) {
    case RED -> 1;
    case YELLOW -> 4;
    case GREEN -> 7;
};
```

---

# Algebraic Data Types
## with `sealed interface` and `record`

---

# [fit] WTF is that? :open_mouth:

---

# Algebraic Data Type

* **ADT** in short
* Somehow, **`enum` on steroids**
  - Some alternatives might hold one or more **attributes**
  - Attributes may vary in number and in type from one alternative to another

---

# `Direction` enumeration

```java
public enum Direction {
    NORTH,
    SOUTH,
    WEST,
    EAST
}
```

---

# `Position` class

```java
@With
public record Position(int x, int y) {
    // ...
    public static Position of(int x, int y) {
        return new Position(x, y);
    }
}
```

---

# Updating `Position` with `Direction`

```java
@With
public record Position(int x, int y) {
    public Position move(Direction direction) {
        return switch (direction) {
            case NORTH -> this.withY(y() - 1);
            case SOUTH -> this.withY(y() + 1);
            case WEST -> this.withX(x() - 1);
            case EAST -> this.withX(x() + 1);
        };
    }
    // ...
}
```

---

# Encoding `Action` ADT

```java
public sealed interface Action {
    record Sleep() implements Action {
        public static Sleep of() { return new Sleep(); }
    }

    record Walk(Direction direction) implements Action {
        public static Walk of(Direction direction) { return new Walk(direction); }
    }

    record Jump(Position position) implements Action {
        public static Jump of(Position position) { return new Jump(position); }
    }
}
```

---

# Instantiating `Action` ADT

```java
final Seq<Action> actions = List.of(
        Jump.of(Position.of(5, 8)),
        Walk.of(Up),
        Sleep.of(),
        Walk.of(Right)
);
```
---

# `Player` class

```java
public record Player(Position position) {
    // ...
    public static Player of(Position position) {
        return new Player(position);
    }
}
```

---

# Updating `Player` with `Action`

```java
public record Player(Position position) {
    public Player act(Action action) {
        return switch (action) {
            case Sleep() -> this;
            case Walk(Direction direction) -> Player.of(position.move(direction));
            case Jump(Position position) -> Player.of(position);
        };
    }
    // ...
}
```

---

# Applying Successive `Action`s

```java
final Player initialPlayer = Player.of(Position.of(1, 1));

final Seq<Action> actions = List.of(
        Jump.of(Position.of(5, 8)),
        Walk.of(NORTH),
        Sleep.of(),
        Walk.of(EAST)
);

final Player finalPLayer = actions.foldLeft(initialPlayer, Player::act);
final Seq<Player> players = actions.scanLeft(initialPlayer, Player::act);
```

* `finalPlayer` prints **final player state** as: `Player[position=Position[x=6, y=7]]`
* `players` prints **successive player states** as: `List(Player[position=Position[x=1, y=1]], Player[position=Position[x=5, y=8]], Player[position=Position[x=5, y=7]], Player[position=Position[x=5, y=7]], Player[position=Position[x=6, y=7]])`

---

> To immutability... and beyond!
-- Buzz Lightyear

---

# There is no Silver Bullet

* **Immutability pays off** even at small scale
  * Many no-brainers. If it's never mutated, make it immutable!
  * `record` and _Vavr_ collections are cool!
  * Code will be really more concise (more but simpler classes).
  * Concurrency and immutability is a match made in heaven!
* **Do not force-feed your code** with immutability
  * Immutability is very **intolerant of entangled design**, it will bite really hard
  * Immutability makes **working with associations more difficult** (bidirectional one-to-many and many-to-many) and odd for many people

---

# Gateway to Functional Programming

* With immutability, **extracting** or **inlining** an expression **will** (most often) **not change the meaning** of the program
  * This is a consequence of **referential transparency** :open_mouth:
  * Fundamental property of **functional programming**
* FP is programming with **pure functions** :innocent:
  * **Deterministic**: same arguments implies same result
  * **Total**: result always available for  arguments
  * **Pure**: no side-effects
* But how do we do with **I/O**?
  * Season finale cliffhanger... :anguished:

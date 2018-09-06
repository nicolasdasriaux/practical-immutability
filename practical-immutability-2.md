autoscale: true
footer: More ... Practical Immutability
slidenumbers: true

#  More ... Practical
# [fit] **Immutability**
## [fit] in Java with _Immutables_ and _Vavr_

---

# There is more to immutability than objects, collections and options

---

# Immutability of Variables

* Mutability of **variables** `!=` Mutability of **objects**
* Immutability of **objects**
  * Cannot mutate the object or collection
  * As seen so far
* Immutability of **variables** (local variable, parameter)
  * Cannot change the value (or reference) contained in the variable
  * `final` vs. ~~`final`~~

---

# Mutability Combinations

|                          | Immutable Object             | Mutable Object                                          |
|--------------------------|------------------------------|---------------------------------------------------------|
| **`final` Variable**     | :smile:                      | :neutral_face: if stricly local<br/>:fearful: otherwise |
| **non-`final` Variable** | :neutral_face: stricly local | :imp:                                                   |

---

# Expressions
## in Java ... and with _Vavr_

---

# Expressions vs. Instructions

* An **expression** evaluates to a value
  * Value can be directly assigned to a `final` variable
  * Expressions, when _pure_, do not cause any side-effect
* An **instruction** does something and has no value
  * Instructions always cause side-effects

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

# Emulating `switch` Expression

```java
final int mark;

switch (color) {
    case RED: mark = 1; break;
    case YELLOW: mark = 3; break;
    case GREEN: mark = 5; break;

    default:
        throw new AssertionError("Unexpected color (" + color + ")");
}
```

---

# Another `Try` with _Vavr_

```java
final Try<Integer> triedNumber = Try.of(() -> Integer.parseInt(input))
        .filter(i -> i > 0)
        .map(i -> i * 10);

```

| `input`   | `triedNumber` prints as                                                      |
|-----------|------------------------------------------------------------------------------|
| `"3"`     | `Success(30)`                                                                |
| `"-10"`   | `Failure(java.util.NoSuchElementException: Predicate does not hold for -10)` |
| `"WRONG"` | `Failure(java.lang.NumberFormatException: For input string: "WRONG")`        |

---

# `Try` to `Option`

```java
final Try<Integer> triedNumber = Try.of(() -> Integer.parseInt(input))
        .filter(i -> i > 0)
        .map(i -> i * 10);

final Integer defaultedNumber = triedNumber.getOrElse(0);
final Option<Integer> maybeNumber = triedNumber.toOption();
```

| `input`   | `defaultedNumber` prints as | `maybeNumber` prints as |
|-----------|-----------------------------|-------------------------|
| `"3"`     | `30`                        | `Some(30)`              |
| `"-10"`   | `0`                         | `None`                  |
| `"WRONG"` | `0`                         | `None`                  |

---

# Algebraic Data Types
## with _Immutables_

---

# [fit] What is that :shit:?

---

# Algebraic Data Type

* **ADT** in short
* Also called **discriminated union** in some other world
* **`enum` on steroids**
  * Some alternatives might hold one or more **attributes**
  * Attributes may vary in number and in type from one alternative to another

---

# `Direction` enumeration

```java
public enum Direction {
    Up,
    Down,
    Left,
    Right
}
```

---

# `Position` class

```java
@Value.Immutable
public abstract class Position {
    @Value.Parameter
    public abstract int x();

    @Value.Parameter
    public abstract int y();

    public static Position of(final int x, final  int y) {
        return ImmutablePosition.of(x, y);
    }
}

```

---

# Updating `Position` with `Direction`

```java
@Value.Immutable
public abstract class Position { // ...
    public Position move(final Direction direction) {
        switch (direction) {
            case Up: return ImmutablePosition.copyOf(this).withY(y() - 1);
            case Down: return ImmutablePosition.copyOf(this).withY(y() + 1);
            case Left: return ImmutablePosition.copyOf(this).withX(x() - 1);
            case Right: return ImmutablePosition.copyOf(this).withX(x() + 1);
            default: throw new IllegalArgumentException(
                        String.format("Unknown Direction (%s)", direction));
        }
    } // ...
}
```

---

# Encoding `Action` ADT

```java
public interface Action {
    @Value.Immutable(singleton = true)
    abstract class Sleep implements Action {
        public static Sleep of() { return ImmutableSleep.of(); }
    }
    @Value.Immutable
    abstract class Walk implements Action {
        @Value.Parameter public abstract Direction direction();
        public static Walk of(final Direction direction) { return ImmutableWalk.of(direction); }
    }
    @Value.Immutable
    abstract class Jump implements Action {
        @Value.Parameter public abstract Position position();
        public static Jump of(final Position position) { return ImmutableJump.of(position); }
    }
}
```

---

# Instantiating `Action` ADT

```java
final List<Action> actions = List.of(
        Jump.of(Position.of(5, 8)),
        Walk.of(Up),
        Sleep.of(),
        Walk.of(Right)
);
```
---

# `Player` class

```java
@Value.Immutable
public abstract class Player {
    @Value.Parameter
    public abstract Position position();    
        
    public static Player of(final Position position) {
        return ImmutablePlayer.of(position);
    }
}
```
---

# Updating `Player` with `Action`

```java
@Value.Immutable
public abstract class Player { // ...
    public Player act(final Action action) {
        if (action instanceof Sleep) {
            return this;
        } else if (action instanceof Walk) {
            final Walk walk = (Walk) action;
            return Player.of(position().move(walk.direction()));
        } else if (action instanceof Jump) {
            final Jump jump = (Jump) action;
            return Player.of(jump.position());
        } else {
            throw new IllegalArgumentException(String.format("Unknown Action (%s)", action));
        }
    } // ...
}
```

---

# Applying Successive `Action`s

```java
final Player initialPlayer = Player.of(Position.of(1, 1));

final Seq<Action> actions = List.of(
        Jump.of(Position.of(5, 8)), Walk.of(Up), Sleep.of(), Walk.of(Right));

final Player finalPLayer = actions.foldLeft(initialPlayer, Player::act);
final Seq<Player> players = actions.scanLeft(initialPlayer, Player::act);
```

* `finalPlayer` prints as: `Player{position=Position{x=6, y=7}}`
* `players` prints as: `List(Player{position=Position{x=1, y=1}}, Player{position=Position{x=5, y=8}}, Player{position=Position{x=5, y=7}}, Player{position=Position{x=5, y=7}}, Player{position=Position{x=6, y=7}})`

---

# Traditional `ActionVisitor`

```java
public interface ActionVisitor<T, R> {
    R visitSleep(Sleep sleep, T t);
    R visitWalk(Walk walk, T t);
    R visitJump(Jump jump, T t);
}
```

---

# `Action` Made Visitable

```java
public interface Action {
    <R, T> R accept(ActionVisitor<T, R> visitor, T t); // ...
    abstract class Sleep implements Action { // ...
        public <R, T> R accept(final ActionVisitor<T, R> visitor, final T t) {
            return visitor.visitSleep(this, t);
        } // ...
    } // ...
    abstract class Walk implements Action { // ...
        public <R, T> R accept(final ActionVisitor<T, R> visitor, final T t) {
            return visitor.visitWalk(this, t);
        } // ...
    } // ...
    abstract class Jump implements Action { // ...
        public <R, T> R accept(final ActionVisitor<T, R> visitor, final T t) {
            return visitor.visitJump(this, t);
        } // ...
    }
}
```

---

# Updating `Player` with `Action`... Visited

```java
@Value.Immutable
public abstract class Player { // ...
    private static final ActionVisitor<Player, Player> ACT_VISITOR = new ActionVisitor<Player, Player>() { // ...
        public Player visitSleep(final Sleep sleep, final Player player) {
            return player;
        } // ...
        public Player visitWalk(final Walk walk, final Player player) {
            return Player.of(player.position().move(walk.direction()));
        } // ...
        public Player visitJump(final Jump jump, final Player player) {
            return Player.of(jump.position());
        }
    };
    public Player act(final Action action) {
        return action.accept(ACT_VISITOR, this);
    } // ...
}
```

---

# Revisited :wink: `ActionMatcher`

```java
@Value.Immutable
@Value.Style(stagedBuilder = true)
public interface ActionMatcher<R> {
    Function<Sleep, R> onSleep();
    Function<Walk, R> onWalk();
    Function<Jump, R> onJump();
}
```

---

# `Action` Made Revisitable

```java
public interface Action {
    <R> R match(ActionMatcher<R> matcher); // ...
    abstract class Sleep implements Action {
        public <R> R match(final ActionMatcher<R> matcher) {
            return matcher.onSleep().apply(this);
        } // ...
    } // ...
    abstract class Walk implements Action {
        public <R> R match(final ActionMatcher<R> matcher) {
            return matcher.onWalk().apply(this);
        } // ...
    } // ...
    abstract class Jump implements Action {
       public <R> R match(final ActionMatcher<R> matcher) {
            return matcher.onJump().apply(this);
       } // ...
    }
}
```

---

# Updating `Player` with `Action`... Revisited :wink:

```java
@Value.Immutable
public abstract class Player { // ...
    private static final ActionMatcher<Function<Player, Player>> ACT_MATCHER =
            ImmutableActionMatcher.<Function<Player, Player>>builder()
                    .onSleep(sleep -> player -> player)
                    .onWalk(walk -> player ->
                            Player.of(player.position().move(walk.direction()))
                    )
                    .onJump(jump -> player ->
                            Player.of(jump.position())
                    )
                    .build();

    public Player act(final Action action) {
        return action.match(ACT_MATCHER).apply(this);
    } // ...
}
```

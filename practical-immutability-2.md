autoscale: true
footer: Practical Immutability
slidenumbers: true

#  More :wink: Practical
# [fit] **Immutability**
## [fit] in Java with _Immutables_ and _Vavr_

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

---

# Expressions vs. Instructions

* ...

---

# `final` Everywhere

```java
```

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

# Emulating `try` `catch` Expression

```java
```

---

# Algebraic Data Types :astonished:

---

# [fit] What?

---

# Algebraic Data Type

* **ADT** in short
* Also called **discriminated union**
* `enum` on steroids
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
public abstract class Position {
    // ...
    public Position move(final Direction direction) {
        switch (direction) {
            case Up: return ImmutablePosition.copyOf(this).withY(y() - 1);
            case Down: return ImmutablePosition.copyOf(this).withY(y() + 1);
            case Left: return ImmutablePosition.copyOf(this).withX(x() - 1);
            case Right: return ImmutablePosition.copyOf(this).withX(x() + 1);
            default: throw new IllegalArgumentException(
                        String.format("Unknown Direction (%s)", direction));
        }
    }
    // ...
}
```

---

# `Action` ADT

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
public abstract class Player {
    // ...
    public Player act(final Action action) {
        if (action instanceof Sleep) {
            return this;
        } else if (action instanceof Walk) {
            final Walk walk = (Walk) action;
            return ImmutablePlayer.of(position().move(walk.direction()));
        } else if (action instanceof Jump) {
            final Jump jump = (Jump) action;
            return ImmutablePlayer.of(jump.position());
        } else {
            throw new IllegalArgumentException(String.format("Unknown Action (%s)", action));
        }
    }
    // ...
}

```
---
 
# `instanceof` is not as evil as you think

```java
```

---

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

* Discriminated Union
* `enum` on steroids
* ADT for friends

---

# `Direction` enumeration

```java
enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}
```

---

# Action ADT

```java
interface Action {
    @Value.Immutable(singleton = true)
    class Sleep extends Action {}
    
    @Value.Immutable
    class Walk extends Action {
        public abstract Direction direction();
    }
    
    @Value.Immutable
    class Jump extends Action {
        public abstract Position position();
    }
}
```

 ---
 
# `instanceof` is not as evil as you think

````java
````

---

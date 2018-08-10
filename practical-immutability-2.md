autoscale: true
footer: Practical Immutability
slidenumbers: true

# Immutability of Variables

* Mutability of **variables** `!=` Mutability of **objects**
* Immutability of **variables** (local variable, parameter)
  * Cannot change the variable
  * `final` vs. ~~`final`~~
* Immutability of **objects**
  * Cannot mutate the object or collection
  * As seen so far

---

# Mutability Combinations

|                          | Immutable Object                     | Mutable Object                                     |
|--------------------------|--------------------------------------|----------------------------------------------------|
| **`final` Variable**     | :smile:                              | :fearful:<br/>Mitigate using mutable class locally |
| **non-`final` Variable** | :neutral_face:<br/>Local effect only | :imp:                                              |

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

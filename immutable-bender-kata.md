# Immutable Bender, Episode 1

This kata is about implementing a CodinGame exercise called **Bender, Episode 1**

## Instructions

The instructions can be found at the following location and are the sole property of CodinGame:
https://www.codingame.com/ide/puzzle/bender-episode-1

## Setup

* **IntelliJ** (Community edition is enough) is recommended as it features much more reliable **Annotation Processor** support compared to **Eclipse**.
* **JDK** should be at least JDK 8.

Once project opened, you will have to enable annotation processor support: 
https://immutables.github.io/apt.html#intellij-idea

## IntelliJ Pro Tips

* Ensuring that code and Javadoc for dependencies are downloaded will help for quick reference in hints.
* Check **Build project automatically** box in **Preferences**
* Enable **Toggle auto-test** icon in any test tab of **Run** Tool Window

## Recommended Implementation Steps

### Guided Steps

Here tests will already be available and you'll mostly have to implement the body of the methods.
There's a number of hints and links pointing to _Vavr_ code to help you (Ctrl + Click or Cmd + Click).

Satisfy existing tests to complete implementation.

1) Complete `Direction` implementation
2) Complete `Position` implementation
3) Complete `Tile` implementation
4) Complete `CityMap` implementation
5) Complete `Robot` implementation
6) Complete `Scene` implementation
   * Use `instanceof` or **pattern matching**
   * Keep **visitor pattern** implementation for later
7) Optionally start playing with `RobotApp` application to help visualize what happens
8) Complete `SceneTracking` implementation
9) Complete `TrackedScene` implementation
10) Play with `RobotApp` application to help visualize what happens

### Bonus Steps

Now implement the following features with your own tests.

1) Display either the **directions** or a **loop diagnosis** following the last steps of CodingGame instructions
2) Try adding class invariant on `CityMap` such as
   * Presence of exactly one **start**
   * Presence of exactly one **booth**
   * Absence of **teleporters** or presence of exactly 2 teleporters
3) Allow presence of 2 or more teleporters
   * Each teleporter will chain to the next teleporter.
   * Last teleporter will chain to the first one.
   * Order of teleporter should be determined by `y` then `x` coordinate.
   * This semantic will preserve the behavior of scenarios involving 2 teleporters.
4) Modify `Tile` and `Scene` implementation to use **visitor pattern**

# Robot Kata

## Recommended Implementation Steps

Switch to `java-record-kata` branch to work on **code without implementation**.
You are free to look at proposed **solution** in `java-record` branch if you feel stuck. 

* `src/main/java/practicalimmutability/kata/robot` contains the **kata**.
* `src/test/java/practicalimmutability/kata/robot` contains the **tests for the kata**.

### Guided Steps

Here tests will already be available and you'll mostly have to implement the body of the methods.
There's a number of hints and links to help you (Ctrl + Click or Cmd + Click).

Satisfy existing tests to complete implementation.

1) Complete `Direction` implementation
2) Complete `Position` implementation
3) Complete `Tile` implementation
4) Complete `CityMap` implementation
5) Complete `Robot` implementation
6) Complete `Scene` implementation
7) Optionally start playing with `RobotApp` application to help visualize what happens
8) Complete `SceneTracking` implementation
9) Complete `TrackedScene` implementation
10) Play with `RobotApp` application to help visualize what happens

### Bonus Steps

Now implement the following features with your own tests.

1) Display either the **directions** to booth or a **loop diagnosis**
2) Try adding class invariant on `CityMap` such as
   * Presence of exactly one **start**
   * Presence of exactly one **booth**
   * Absence of **teleporters** or presence of exactly 2 teleporters
3) Allow presence of 2 or more teleporters
   * Each teleporter will chain to the next teleporter.
   * Last teleporter will chain to the first one.
   * Order of teleporter should be determined by `y` then `x` coordinate.
   * This semantic will preserve the behavior of scenarios involving 2 teleporters.

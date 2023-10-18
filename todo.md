# Todo

##  Updating `kata` branch

Rebuild `kata` branch by removing solution code from method bodies starting from latest versions

* Replace section

  ```regexp
  // IMPLEMENT FUNC \{\{\{\n(.*\n)*?.*// \}\}\}
  ```

  By code

  ```java
  return io.vavr.API.TODO();
  ```

* Replace section

  ```regexp
  // IMPLEMENT CONST \{\{\{\n(.*\n)*?.*// \}\}\}
  ```

  By code

  ```java
  io.vavr.API.TODO();
  ```

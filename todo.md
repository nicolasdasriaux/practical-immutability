# Todo

## Kata

* Rebuild `kata` branch by removing solution code from method bodies starting from latest versions
* Update kata instructions to get to switch to `kata` branch

# Notes

# Substitution to publish new `kata` branch

Replace section

```regexp
// IMPLEMENT FUNC \{\{\{\n(.*\n)*?.*// \}\}\}
```

By code

```java
return API.TODO();
```

Replace section 
```regexp
// IMPLEMENT CONST \{\{\{\n(.*\n)*?.*// }}}
```

By code 
```java
API.TODO();
```

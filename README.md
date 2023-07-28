# Hayaku

A library for efficient integer lists
and a laboratory for collections of primitive types

## Design goals

* Offer a developer friendly API
* Offer Immutable design as first intent
* Resort to mutability only for performance and memory footprint
* Get the performance of Java arrays without the hassle

## Design Principles

### Immutability for correctness

```java
final IntList numbers = IntList.of(9, 5, 4);
// Immutable list of numbers

final IntList modifiedNumbers = numbers.swap(0, 2);
// A new immutable list of numbers where 1st and 3rd items have been swapped
// Initial list remains unchanged.
 ```

### Local mutability for performance

Immutability generates a lot of intermediate results as immutable objects.

```java
final  IntList numbers = IntList.of(6, 5, 4);

final IntList modifiedNumbers = numbers
        .swap(0, 2) // Returns a new immutable instance
        .append(7) // Returns a new immutable instance
        .prependAll(IntList.of(1, 2, 3)) // Returns a new immutable instance
        .map(i -> i * 10); // Returns a new immutable instance
```

Using builder pattern, it becomes possible to perform all successive operations on the same mutable builder
and eventually generate a single immutable object.

```java
final IntList numbers = IntList.of(6, 5, 4);

final IntList modifiedNumbers = numbers.toBuilder() // Returns a new mutable builder
        .swap(0, 2) // Performs changes in-place on the mutable builder
        .append(7) // Performs changes in-place on the mutable builder
        .prependAll(IntList.of(1, 2, 3)) // Performs changes in-place on the mutable builder
        .map(i -> i * 10) // Performs changes in-place on the mutable builder
        .build(); // Returns a new immutable instance
```

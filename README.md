# IntList

A library for efficient integer lists
and a laboratory for collections of primitive types

## Design Goals

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

### Mirrored immutable and mutable API

General principle is that the implementations for methods reside in `IntList.Builder`.
Most methods in `IntList` only delegate to the corresponding methods on `IntList.Builder`.

#### Transformation methods

A **transformation** method applies a transformation on an immutable `IntList`
and returns the result of this transformation as another immutable `IntList`.

The mirror method on `IntList.Builder` performs the same transformation
by mutating the targeted mutable builder and returns the exact same builder it has just mutated.

```java 
public class IntList {
    public IntList set(int index, int value) {
        return toBuilder() // Create a mutable builder from this immutable IntList
                .set(index, value) // Let the builder perform the transformation on itself
                .unsafeBuild(); // Create an immutable IntList from this mutable builder
    }

    public static class Builder {
        public Builder set(int index, int value) {
            // Actual implementation
            // This is where the transformation is actually performed by mutating this mutable builder.
            return this; // Return this builder
        }
    }
}
```

#### Query methods

A **query** method computes a value after an immutable `IntList`
and returns this result as an immutable value.

The mirror method on `IntList.Builder` performs the same computation but after the mutable builder
and returns this result as an immutable value.
Though mutable, the builder is never mutated by a query method.

```java
public class IntList {
    public boolean contains(int value) {
        return unsafeToBuilder() // Create a mutable builder from this immutable IntList
                .contains(value); // Let the builder perform the computation and return the result
    }
    
    public static class Builder {
        public boolean contains(int value) {
            // Actual implementation
            // This is where the computation is actually performed 
        }
    }
}
```

## Testing Strategy

Using **jqwik**, a property-based testing library

### Immutable `IntList`

* Example-based testing 
* Property-based testing

### Mutable `IntList.Builder`

* Statefull property-based testing to check consistency of mirrored method chains on both immutable and mutable implementations

## Performance Testing Strategy

Using **JMH** (Java Microbenchmark Harness)

* Benchmark for features
* Benchmark against low level array libraries such as **Apache Commons**

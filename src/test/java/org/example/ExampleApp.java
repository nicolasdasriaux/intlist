package org.example;

public class ExampleApp {
    public static void immutability() {
        final IntList numbers = IntList.of(9, 5, 4);
        // Immutable list of numbers

        final IntList modifiedNumbers = numbers.swap(0, 2);
        // A new immutable list of numbers where 1st and 3rd items have been swapped
        // Initial list remains unchanged.
    }

    public static void immutable_chain() {
        final  IntList numbers = IntList.of(6, 5, 4);

        final IntList modifiedNumbers = numbers
                .swap(0, 2) // Returns a new immutable instance
                .append(7) // Returns a new immutable instance
                .prependAll(IntList.of(1, 2, 3)) // Returns a new immutable instance
                .map(i -> i * 10); // Returns a new immutable instance
    }

    public static void mutable_chain() {
        final IntList numbers = IntList.of(6, 5, 4);

        final IntList modifiedNumbers = numbers.toBuilder() // Returns a new mutable builder
                .swap(0, 2) // Performs changes in-place on the mutable builder
                .append(7) // Performs changes in-place on the mutable builder
                .prependAll(IntList.of(1, 2, 3)) // Performs changes in-place on the mutable builder
                .map(i -> i * 10) /// Performs changes in-place on the mutable builder
                .build(); // Returns a new immutable instance
    }

    public static void main(String[] args) {
        immutability();
        immutable_chain();
        mutable_chain();
    }
}

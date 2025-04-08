package fastprimcol.intlist.example;

import fastprimcol.intlist.IntList;

public class IntListExamples {
    public static void immutability() {
        final IntList numbers = IntList.of(9, 5, 4);
        // Immutable list of numbers

        final IntList modifiedNumbers = numbers.swap(0, 2);
        // A new immutable list of numbers where 1st and 3rd items have been swapped
        // Initial list remains unchanged.

        System.out.printf("numbers=%s\n", numbers);
        System.out.printf("modifiedNumbers=%s\n", modifiedNumbers);
    }

    public static void immutable_chain() {
        final IntList numbers = IntList.of(6, 5, 4);

        final IntList modifiedNumbers = numbers
                .swap(0, 2) // Returns a new immutable instance
                .append(7) // Returns a new immutable instance
                .prependAll(IntList.of(1, 2, 3)) // Returns a new immutable instance
                .map(i -> i * 10); // Returns a new immutable instance

        System.out.printf("numbers=%s\n", numbers);
        System.out.printf("modifiedNumbers=%s\n", modifiedNumbers);
    }

    public static void mutable_chain() {
        final IntList numbers = IntList.of(6, 5, 4);

        final IntList modifiedNumbers = numbers.toBuilder() // Returns a new mutable builder
                .swap(0, 2) // Performs changes in-place on the mutable builder
                .append(7) // Performs changes in-place on the mutable builder
                .prependAll(IntList.of(1, 2, 3)) // Performs changes in-place on the mutable builder
                .map(i -> i * 10) /// Performs changes in-place on the mutable builder
                .build(); // Returns a new immutable instance

        System.out.printf("numbers=%s\n", numbers);
        System.out.printf("modifiedNumbers=%s\n", modifiedNumbers);
    }

    public static void mutable_chain_optimized() {
        final IntList numbers = IntList.of(6, 5, 4);

        final IntList modifiedNumbers = numbers.toBuilder(3, 1) // Leading capacity 3, trailing capacity 1
                .swap(0, 2)
                .append(7) // Trailing buffer is used, no reallocation nor move is performed
                .prependAll(IntList.of(1, 2, 3)) // Leading buffer is used, no reallocation nor move is performed
                .map(i -> i * 10)
                .build();

        System.out.printf("numbers=%s\n", numbers);
        System.out.printf("modifiedNumbers=%s\n", modifiedNumbers);
    }

    public static void build_lambda() {
        final IntList numbers = IntList.build(1, 3, builder -> builder
                .append(1)
                .append(2)
                .append(3)
                .prepend(0)
        );

        System.out.printf("numbers=%s\n", numbers);
    }

    public static void modify_lambda() {
        final IntList numbers = IntList.of(6, 5, 4);

        final IntList modifiedNumbers = numbers.modify(3, 1, builder -> builder
                .swap(0, 2)
                .append(7)
                .prependAll(IntList.of(1, 2, 3))
                .map(i -> i * 10)
        );

        System.out.printf("numbers=%s\n", numbers);
        System.out.printf("modifiedNumbers=%s\n", modifiedNumbers);
    }

    public static void main(String[] args) {
        immutability();
        immutable_chain();
        mutable_chain();
        mutable_chain_optimized();
        build_lambda();
        modify_lambda();
    }
}

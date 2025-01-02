package fastprimcol.intlist;

public class ExampleApp {
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
        final  IntList numbers = IntList.of(6, 5, 4);

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

    public static void build_initial() {
        final IntList numbers = IntList.build(1, 3, builder -> {
            builder.append(1);
            builder.append(2);
            builder.append(3);
            builder.prepend(0);
        });

        System.out.printf("numbers=%s\n", numbers);
    }


    public static void main(String[] args) {
        immutability();
        immutable_chain();
        mutable_chain();
        build_initial();
    }
}

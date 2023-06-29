package org.example;

import java.util.List;

public class Main {
    public static void timed(int iterations, Runnable runnable) {
        final long start = System.nanoTime();

        for (int i = 0; i < iterations; i++) {
            runnable.run();
        }

        final long end = System.nanoTime();
        long total = end - start;
        System.out.println(">>> %d ms".formatted(total / 1_000_000));
        System.out.println(">>> %d ns / operation".formatted(total / iterations));
    }

    public static void main(String[] args) {

        IntList.of(1, 2, 3).toBuilder(1, 1).prepend(0).append(4).build();
        System.out.println(IntList.of(4, 6, 8, 9, 10).arrangements(3));
        System.out.println(IntList.rangeClosed(1, 5));
        System.out.println(IntList.of(1, 2, 3, 4, 5, 7, 8, 9, 10).arrangements(3).size());

        System.out.println(IntList.rangeClosed(1, 5).combinations(3));

        timed(1000, () -> {
            final List<IntList> list = IntList.of(1, 2, 3, 4, 5, 7, 8, 9, 10).arrangements(3);
        });

        timed(1000, () -> {
            final List<IntList> list = IntList.of(1, 2, 3, 4, 5, 7, 8, 9, 10).combinations(3).stream()
                    .flatMap(combination -> combination.permutations().stream())
                    .toList();

        });

        final List<IntList> list = IntList.of(1, 2, 3, 4, 5, 6).combinations(3).stream()
                .flatMap(c -> c.permutations().stream())
                .toList();

        System.out.println(list);
        System.out.println(IntList.of(1, 2, 3, 4, 5).permutations());

        {
            IntList intList = IntList.of(1, 2, 3).toBuilder().debug("init")
                    .prependAll(IntList.of(-2, -1, 0)).debug("addAllFirst")
                    .prependAll(IntList.of(-5, -4, -3)).debug("addAllFirst")
                    .prependAll(IntList.of(-11, -10, -9, -8, -7, -6)).debug("addAllFirst")
                    .appendAll(IntList.of(4, 5, 6, 7, 8, 9, 10, 11, 12)).debug("addAll")
                    .build();
        }

        {
            final IntList intList = IntList.Builder.empty(25).debug(".empty(25)")
                    .append(5).debug(".add(5)")
                    .prepend(4).debug("addFirst")
                    .appendAll(IntList.of(6, 7, 8)).debug(".addFirst(4)")
                    .prependAll(IntList.of(2, 3)).debug("addAllFirst(IntList.of(2, 3))")
                    .set(0, -2).debug(".set(0, -2)")
                    .set(6, -8).debug(".set(6, -8)")
                    .swap(0, 6).debug(".swap(0, 6)")
                    .sort().debug(".sort()")
                    .reverse().debug(".reverse()")
                    .retainAll(IntList.of(3, 4, 5, 6, 7, -10)).debug(".retainAll(IntList.of(3, 4, 5, 6, 7, -10))")
                    .remove(5).debug(".remove(5)")
                    .removeAll(IntList.of(4, 7)).debug(".removeAll(IntList.of(4, 7))")
                    .insert(1, 10).debug(".insert(1, 10)")
                    .insertAll(2, IntList.of(11, -1, 12, 13)).debug(".insertAll(2, IntList.of(11, -1, 12, 13))")
                    .removeAt(3).debug(".delete(3)")
                    .filter(i -> i > 10).debug(".filter(i -> i > 10)")
                    .map(i -> i * 10).debug(".map(i -> i * 10)")
                    .replace(120, 220).debug(".replace(120, 220)")
                    .build();

            System.out.println(intList);
        }
        final IntList intList = IntList.of(1, 2, 3);

        final IntList.Builder builder = intList.toBuilder(20, 20);
        System.out.println(builder);
        builder.set(0, 5);
        System.out.println(builder);
        builder.set(2, 18);
        System.out.println(builder);
        builder.sort();
        System.out.println(builder);
        builder.append(92);
        System.out.println(builder);
        builder.append(91);
        System.out.println(builder);
        builder.append(90);
        System.out.println(builder);
        builder.appendAll(IntList.of(105, 104, 103));
        System.out.println(builder);
        builder.sort();
        System.out.println(builder);
        builder.insert(0, 222);
        System.out.println(builder);
        builder.insertAll(1, IntList.of(333, 444, 555));
        System.out.println(builder);
        builder.removeAt(3);
        System.out.println(builder);
        final IntList updatedIntList = builder.build();
        System.out.printf("updatedIntList=%s%n", updatedIntList);

        {
            final IntList newIntList = IntList.of(1, 2, 3).toBuilder()
                    .set(0, 5)
                    .set(2, 18)
                    .sort()
                    .append(92)
                    .append(91)
                    .append(90)
                    .appendAll(IntList.of(105, 104, 103))
                    .sort()
                    .insert(0, 222)
                    .insertAll(1, IntList.of(333, 444, 555))
                    .removeAt(3)
                    .map(i -> i * 1000)
                    .swap(0, 1)
                    .prepend(0)
                    .prependAll(IntList.of(1, 1, 1))
                    .build();

            System.out.printf("newIntList=%s%n", newIntList);
        }

        {
            final IntList newIntList = intList.build(20, 20, b -> b
                    .set(0, 5)
                    .set(2, 18)
                    .sort()
                    .append(92)
                    .append(91)
                    .append(90)
                    .appendAll(IntList.of(105, 104, 103))
                    .sort()
                    .insert(0, 222)
                    .insertAll(1, IntList.of(333, 444, 555))
                    .removeAt(3)
                    .map(i -> i * 1000)
                    .swap(0, 1));

            System.out.printf("newIntList=%s%n", newIntList);
        }

        {
            final IntList poussette = IntList.of(1, 2, 3, 4).toBuilder()
                    .remove(2)
                    .append(10)
                    .build();

            System.out.println("poussette = " + poussette);
        }

        System.out.println(IntList.of(2, 1, 3).compareTo(IntList.of(2, 1, 5)));
        System.out.println(IntList.of(5, 6, 7).contains(5));
        System.out.println(IntList.of(5, 6, 7).containsAll(IntList.of(6, 3)));
        System.out.println(IntList.of(5, 5, 6, 7).retainAll(IntList.of(6, 3, 5, 1)));
        System.out.println(IntList.of(5, 6, 1, 2, 5).removeAll(IntList.of(6, 3, 5, 8)));

        System.out.println(
                IntList.of(6, 7, 8)
                        .appendAll(IntList.of(9, 10, 11))
                        .append(12)
                        .prepend(4)
                        .prependAll(IntList.of(1, 2, 3))
                        .insert(4, 5)
                        .insertAll(4, IntList.of(97, 98, 99))
                        .remove(97)
                        .removeAll(IntList.of(98, 99))
                        .retainAll(IntList.of(1, 2, 3, 7, 8, 9, 13))
                        .set(0, 1)
                        .swap(0, 1)
                        .swap(1, 0)
                        .map(i -> i * 10)
        );

        System.out.println(IntList.of(5, -1, 2, 45, -12).filter(i -> i > 0));
    }
}

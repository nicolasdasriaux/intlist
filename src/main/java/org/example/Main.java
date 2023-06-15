package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println(IntList.of(1, 2, 3, 4, 5, 6).arrangements(3));

        final List<IntList> l2 = IntList.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).combinations(5).stream()
                .flatMap(c -> c.permutations().stream())
                .toList();

        System.out.println(l2);
        System.out.println(l2.size());

        final List<IntList> list = IntList.of(1, 2, 3, 4, 5, 6).combinations(3).stream()
                .flatMap(c -> c.permutations().stream())
                .toList();

        System.out.println(list);
        System.out.println(IntList.of(1, 2, 3, 4, 5).permutations());

        {
            IntList intList = IntList.Builder.of(new int[] {1, 2, 3}).debug("init")
                    .addAllFirst(IntList.of(-2, -1, 0)).debug("addAllFirst")
                    .addAllFirst(IntList.of(-5, -4, -3)).debug("addAllFirst")
                    .addAllFirst(IntList.of(-11, -10, -9, -8, -7, -6)).debug("addAllFirst")
                    .addAll(IntList.of( 4, 5, 6, 7, 8, 9, 10, 11, 12)).debug("addAll")
                    .build();
        }

        {
            final IntList intList = IntList.Builder.empty(25).debug(".empty(25)")
                    .add(5).debug(".add(5)")
                    .addFirst(4).debug("addFirst")
                    .addAll(IntList.of(6, 7, 8)).debug(".addFirst(4)")
                    .addAllFirst(IntList.of(2, 3)).debug("addAllFirst(IntList.of(2, 3))")
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
                    .delete(3).debug(".delete(3)")
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
        builder.add(92);
        System.out.println(builder);
        builder.add(91);
        System.out.println(builder);
        builder.add(90);
        System.out.println(builder);
        builder.addAll(IntList.of(105, 104, 103));
        System.out.println(builder);
        builder.sort();
        System.out.println(builder);
        builder.insert(0, 222);
        System.out.println(builder);
        builder.insertAll(1, IntList.of(333, 444, 555));
        System.out.println(builder);
        builder.delete(3);
        System.out.println(builder);
        final IntList updatedIntList = builder.build();
        System.out.printf("updatedIntList=%s%n", updatedIntList);

        {
            final IntList newIntList = IntList.of(1, 2, 3).toBuilder()
                    .set(0, 5)
                    .set(2, 18)
                    .sort()
                    .add(92)
                    .add(91)
                    .add(90)
                    .addAll(IntList.of(105, 104, 103))
                    .sort()
                    .insert(0, 222)
                    .insertAll(1, IntList.of(333, 444, 555))
                    .delete(3)
                    .map(i -> i * 1000)
                    .swap(0, 1)
                    .addFirst(0)
                    .addAllFirst(IntList.of(1, 1, 1))
                    .build();

            System.out.printf("newIntList=%s%n", newIntList);
        }

        {
            final IntList newIntList = intList.build(20, 20, b -> b
                    .set(0, 5)
                    .set(2, 18)
                    .sort()
                    .add(92)
                    .add(91)
                    .add(90)
                    .addAll(IntList.of(105, 104, 103))
                    .sort()
                    .insert(0, 222)
                    .insertAll(1, IntList.of(333, 444, 555))
                    .delete(3)
                    .map(i -> i * 1000)
                    .swap(0, 1));

            System.out.printf("newIntList=%s%n", newIntList);
        }

        {
            final IntList poussette = IntList.of(1, 2, 3, 4).toBuilder()
                    .remove(2)
                    .add(10)
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
                        .addAll(IntList.of(9, 10, 11))
                        .add(12)
                        .addFirst(4)
                        .addAllFirst(IntList.of(1, 2, 3))
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

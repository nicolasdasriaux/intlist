package org.example;

public class Main {
    public static void main(String[] args) {
        final IntList intList = IntList.of(1, 2, 3);

        final IntList.Builder builder = intList.toBuilder();
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
                    .replaceAll(i -> i * 1000)
                    .swap(0, 1)
                    .addFirst(0)
                    .addAllFirst(intList.of(1, 1, 1))
                    .build();

            System.out.printf("newIntList=%s%n", newIntList);
        }

        {
            final IntList newIntList = intList.build(b -> {
                b
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
                        .replaceAll(i -> i * 1000)
                        .swap(0, 1);
            });

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
    }
}

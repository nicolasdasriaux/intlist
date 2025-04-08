package fastprimcol.intlist.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListExamples {
    public static void boxing_unboxing() {
        final ArrayList<Integer> list = new ArrayList<>();
        list.add(1); // Boxing
        list.add(2); // Boxing
        list.add(3); // Boxing

        final int item = list.get(0); // Unboxing
    }

    public static void accepts_nulls() {
        final ArrayList<Integer> list = new ArrayList<>();
        list.add(null);

        try {
            final int item = list.get(0); // BOOM!
        } catch (final NullPointerException e) {
            assert true : "Should fail";
        }
    }

    public static void mutable_unmodifiable() {
        final List<Integer> mutableList = new ArrayList<>();
        mutableList.add(1); // Mutation
        mutableList.add(2); // Mutation

        final List<Integer> unmodifiableList = Collections.unmodifiableList(mutableList);

        try {
            unmodifiableList.add(3); // BOOM! Mutation fails at runtime
        } catch (UnsupportedOperationException e) {
            assert true : "Should fail";
        }
    }

    public static void cumbersome_api() {
        final List<Integer> list1 = List.of(1, 2, 3);
        final List<Integer> list2 = List.of(4, 5, 6);
        final List<Integer> list3 = List.of(7, 8, 9);
        final List<Integer> result = Lists.concat(list1, list2, list3);
        System.out.println(result);
    }

    public static class Lists {
        public static List<Integer> concat(
                List<Integer> list1,
                List<Integer> list2,
                List<Integer> list3) {

            final ArrayList<Integer> buffer = new ArrayList<>(list1);
            // Defensive copy

            buffer.addAll(list2); // Does not chain
            buffer.addAll(list3); // Does not chain

            return Collections.unmodifiableList(buffer);
            // Wrap to make unmodifiable
        }
    }

    public static void builder_pattern() {
        final List<Integer> list1 = ListBuilder.<Integer>builder()
                .add(1)
                .add(2)
                .addAll(List.of(3, 4, 5, 6))
                .build();

        System.out.println(list1);

        final List<Integer> list2 = ListBuilder.toBuilder(List.of(1, 2, 3))
                .add(4)
                .addAll(List.of(5, 6))
                .build();

        System.out.println(list2);
    }

    public static class ListBuilder<T> {
        private final List<T> buffer; // Working buffer

        private ListBuilder(List<T> buffer) {
            this.buffer = buffer;
        }

        public ListBuilder<T> add(T element) {
            buffer.add(element);
            return this; // Allows chaining
        }

        public ListBuilder<T> addAll(List<T> elements) {
            buffer.addAll(elements);
            return this; // Allows chaining
        }

        public List<T> build() {
            final ArrayList<T> result = new ArrayList<>(buffer); // Defensive copy
            return Collections.unmodifiableList(result); // Wrap to make unmodifiable
        }

        public static <T> ListBuilder<T> builder() {
            final ArrayList<T> buffer = new ArrayList<>();
            return new ListBuilder<>(buffer);
        }

        public static <T> ListBuilder<T> toBuilder(List<T> list) {
            final ArrayList<T> buffer = new ArrayList<>(list);
            return new ListBuilder<>(buffer);
        }
    }

    public static void main(String[] args) {
        boxing_unboxing();
        accepts_nulls();
        mutable_unmodifiable();
        cumbersome_api();
        builder_pattern();
    }
}

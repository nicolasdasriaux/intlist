package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntPredicate;

public class IntList implements Comparable<IntList> {
    private final int[] array;

    private IntList(int[] array) {
        this.array = array;
    }

    public int size() {
        return array.length;
    }

    public int get(int index) {
        return array[index];
    }

    public List<IntList> combinations(int k) {
        final int[] item = new int[k];
        final List<IntList> items = new ArrayList<>();
        combinationsLoop(k, k, 0, item, items);
        return items;
    }

    private void combinationsLoop(int k, int kk, int ii, int[] item, List<IntList> items) {
        if (kk == 0) {
            items.add(IntList.of(item.clone()));
        } else {
            for (int i = ii; i < array.length; i++) {
                item[k - kk] = array[i];
                combinationsLoop(k, kk - 1, i + 1, item, items);
            }
        }
    }

    public List<IntList> arrangements(int k) {
        final int[] item = new int[k];
        final List<IntList> items = new ArrayList<>();
        arrangementsLoop(k, k, 0, item, items);
        return items;
    }

    private void arrangementsLoop(int k, int kk, long indices, int[] item, List<IntList> items) {
        if (kk == 0) {
            items.add(IntList.of(item.clone()));
        } else {
            for (int i = 0; i < array.length; i++) {
                item[k - kk] = array[i];

                if ((indices & 1L << i) == 0) {
                    arrangementsLoop(k, kk - 1, indices | 1L << i, item, items);
                }
            }
        }
    }

    public List<IntList> permutations() {
        final int[] item = new int[array.length];
        final List<IntList> items = new ArrayList<>();
        permutationsLoop(array.length, array.length, 0, item, items);
        return items;
    }

    private void permutationsLoop(int n, int nn, long indices, int[] item, List<IntList> items) {
        if (nn == 0) {
            items.add(IntList.of(item.clone()));
        } else {
            for (int i = 0; i < array.length; i++) {
                item[n - nn] = array[i];

                if ((indices & 1L << i) == 0) {
                    permutationsLoop(n, nn - 1, indices | 1L << i, item, items);
                }
            }
        }
    }

    public boolean contains(int value) {
        return toBuilderNonLeaking().contains(value);
    }

    public boolean containsAll(IntList values) {
        return toBuilderNonLeaking().containsAll(values);
    }

    public boolean containsAny(IntList values) {
        return toBuilderNonLeaking().containsAny(values);
    }

    public IntList set(int index, int value) {
        return toBuilder()
                .set(index, value)
                .buildNonLeaking();
    }

    public IntList swap(int index1, int index2) {
        return toBuilder()
                .swap(index1, index2)
                .buildNonLeaking();
    }

    public IntList replace(int value, int newValue) {
        return toBuilder()
                .replace(value, newValue)
                .buildNonLeaking();
    }

    public IntList reverse() {
        return toBuilder()
                .reverse()
                .buildNonLeaking();
    }

    public IntList sort() {
        return toBuilder()
                .sort()
                .buildNonLeaking();
    }

    public IntList sortDesc() {
        return toBuilder()
                .sortDesc()
                .buildNonLeaking();
    }

    public IntList add(int value) {
        return toBuilder(0, 1)
                .add(value)
                .buildNonLeaking();
    }

    public IntList addAll(IntList values) {
        return toBuilder(0, values.array.length)
                .addAll(values)
                .build();
    }

    public IntList addFirst(int value) {
        return toBuilder(1, 0)
                .addFirst(value)
                .build();
    }

    public IntList addAllFirst(IntList values) {
        return toBuilder(values.array.length, 0)
                .addAllFirst(values)
                .buildNonLeaking();
    }

    public IntList retainAll(IntList values) {
        return toBuilder()
                .retainAll(values)
                .buildNonLeaking();
    }

    public IntList remove(int value) {
        return toBuilder()
                .remove(value)
                .buildNonLeaking();
    }

    public IntList removeAll(IntList values) {
        return toBuilder()
                .removeAll(values)
                .buildNonLeaking();
    }

    public IntList map(IntToIntFunction function) {
        return toBuilder()
                .map(function)
                .buildNonLeaking();
    }

    public IntList filter(IntPredicate predicate) {
        return toBuilder()
                .filter(predicate)
                .buildNonLeaking();
    }

    public IntList insert(int index, int value) {
        return toBuilder(0, 1)
                .insert(index, value)
                .buildNonLeaking();
    }

    public IntList insertAll(int index, IntList values) {
        return this.toBuilder(0, values.array.length)
                .insertAll(index, values)
                .buildNonLeaking();
    }

    public IntList delete(int index) {
        return this.toBuilder()
                .delete(index)
                .buildNonLeaking();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (o instanceof IntList other) {
            return Arrays.equals(array, other.array);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(array);
    }

    @Override
    public int compareTo(IntList o) {
        return Arrays.compare(array, o.array);
    }

    public static IntList of(int... array) {
        return new IntList(array);
    }

    public Builder toBuilder() {
        return Builder.of(array);
    }

    private Builder toBuilderNonLeaking() {
        return Builder.ofNonLeaking(array);
    }

    public Builder toBuilder(int lead, int trail) {
        return Builder.of(array, lead, trail);
    }

    public IntList build(Consumer<Builder> consumer) {
        final Builder builder = toBuilder();
        consumer.accept(builder);
        return builder.build();
    }

    public IntList build(int lead, int trail, Consumer<Builder> consumer) {
        final Builder builder = toBuilder(lead, trail);
        consumer.accept(builder);
        return builder.build();
    }

    public static class Builder {
        private int[] buffer;
        private int start;
        private int end;

        private Builder(int[] buffer, int start, int end) {
            this.buffer = buffer;
            this.start = start;
            this.end = end;
        }

        public int size() {
            return end - start;
        }

        public int capacity() {
            return buffer.length;
        }

        public int leadingCapacity() {
            return start;
        }

        public int trailingCapacity() {
            return buffer.length - end;
        }

        public int freeCapacity() {
            return buffer.length - end + start;
        }

        public int get(int index) {
            return buffer[start + index];
        }

        public Builder set(int index, int value) {
            buffer[start + index] = value;
            return this;
        }

        public Builder replace(int value, int newValue) {
            for (int i = start; i < end; i++) {
                if (buffer[i] == value) {
                    buffer[i] = newValue;
                }
            }

            return this;
        }

        public int indexOf(int value) {
            for (int i = start; i < end; i++) {
                if (buffer[i] == value) {
                    return i;
                }
            }

            return -1;
        }

        public int lastIndexOf(int value) {
            for (int i = end - 1; i >= start; i--) {
                if (buffer[i] == value) {
                    return i;
                }
            }

            return -1;
        }

        public boolean contains(int value) {
            for (int i = start; i < end; i++) {
                if (buffer[i] == value) {
                    return true;
                }
            }

            return false;
        }

        public boolean containsAll(IntList values) {
            for (int i = 0; i < values.array.length; i++) {
                if (!contains(values.array[i])) {
                    return false;
                }
            }

            return true;
        }

        public boolean containsAny(IntList values) {
            final Builder valuesBuilder = values.toBuilderNonLeaking();

            for (int i = start; i < end; i++) {
                if (valuesBuilder.contains(buffer[i])) {
                    return true;
                }
            }

            return false;
        }

        public boolean hasDuplicates() {
            for (int i = start; i < end; i++) {
                for (int j = i + 1; j < end; j++) {
                    if (buffer[i] == buffer[j]) {
                        return true;
                    }
                }
            }

            return false;
        }

        public Builder swap(int index1, int index2) {
            final int value = buffer[start + index1];
            buffer[start + index1] = buffer[start + index2];
            buffer[start + index2] = value;
            return this;
        }

        public Builder reverse() {
            for (int i = 0; i < (end - start) / 2; i++) {
                swap(i, end - start - 1 - i);
            }

            return this;
        }

        public Builder sort() {
            Arrays.sort(buffer, start, end);
            return this;
        }

        public Builder sortDesc() {
            return sort().reverse();
        }

        public Builder add(int value) {
            ensureTrailingCapacity(1);
            buffer[end] = value;
            end++;
            return this;
        }

        public Builder addAll(IntList values) {
            ensureTrailingCapacity(values.array.length);
            System.arraycopy(values.array, 0, buffer, end, values.array.length);
            end += values.array.length;
            return this;
        }

        public Builder addFirst(int value) {
            ensureLeadingCapacity(1);
            buffer[start - 1] = value;
            start--;
            return this;
        }

        public Builder addAllFirst(IntList values) {
            ensureLeadingCapacity(values.array.length);
            System.arraycopy(values.array, 0, buffer, start - values.array.length, values.array.length);
            start -= values.array.length;
            return this;
        }

        public Builder retainAll(IntList values) {
            final Builder valuesBuilder = values.toBuilderNonLeaking();
            int j = start;

            for (int i = start; i < end; i++) {
                if (valuesBuilder.contains(buffer[i])) {
                    if (j < i) {
                        buffer[j] = buffer[i];
                    }

                    j++;
                }
            }

            end = j;
            return this;
        }

        public Builder remove(int value) {
            int j = start;

            for (int i = start; i < end; i++) {
                if (buffer[i] != value) {
                    if (j < i) {
                        buffer[j] = buffer[i];
                    }

                    j++;
                }
            }

            end = j;
            return this;
        }

        public Builder removeAll(IntList values) {
            final Builder valuesBuilder = values.toBuilderNonLeaking();
            int j = start;

            for (int i = start; i < end; i++) {
                if (!valuesBuilder.contains(buffer[i])) {
                    if (j < i) {
                        buffer[j] = buffer[i];
                    }

                    j++;
                }
            }

            end = j;
            return this;
        }

        public Builder map(IntToIntFunction function) {
            for (int i = start; i < end; i++) {
                buffer[i] = function.apply(buffer[i]);
            }

            return this;
        }

        public Builder filter(IntPredicate predicate) {
            int j = start;

            for (int i = start; i < end; i++) {
                if (predicate.test(buffer[i])) {
                    if (j < i) {
                        buffer[j] = buffer[i];
                    }

                    j++;
                }
            }

            end = j;
            return this;
        }

        public Builder insert(int index, int value) {
            ensureTrailingCapacity(1);
            System.arraycopy(buffer, start + index, buffer, start + index + 1, end - start - index);
            buffer[start + index] = value;
            end++;
            return this;
        }

        public Builder insertAll(int index, IntList values) {
            ensureTrailingCapacity(values.array.length);
            System.arraycopy(buffer, start + index, buffer, start + index + values.array.length, end - start - index);
            System.arraycopy(values.array, 0, buffer, start + index, values.array.length);
            end += values.array.length;
            return this;
        }

        public Builder delete(int index) {
            System.arraycopy(buffer, start + index + 1, buffer, start + index, end - start - index);
            this.end--;
            return this;
        }

        private void ensureLeadingCapacity(int required) {
            final int capacity = capacity();
            final int size = size();

            if (required > leadingCapacity()) {
                if (required <= freeCapacity()) {
                    // Enough free capacity, push to the right
                    System.arraycopy(buffer, start, buffer, required, size);
                    start = required;
                    end = required + size;
                } else {
                    // Acquire enough leading capacity while balancing leading and trailing capacity
                    int newCapacity = capacity;
                    int newLeadingCapacity;

                    do {
                        newCapacity *= 2;
                        newLeadingCapacity = (newCapacity - size) / 2;
                    } while (required > newLeadingCapacity);

                    int[] newBuffer = new int[newCapacity];
                    System.arraycopy(buffer, start, newBuffer, newLeadingCapacity, size);
                    buffer = newBuffer;
                    start = newLeadingCapacity;
                    end = newLeadingCapacity + size;
                }
            }
        }

        private void ensureTrailingCapacity(int required) {
            final int capacity = capacity();
            final int size = size();

            if (required > trailingCapacity()) {
                if (required <= freeCapacity()) {
                    // Enough free capacity, push to the left
                    System.arraycopy(buffer, start, buffer, capacity - size - required, size);
                    start = capacity - size - required;
                    end = capacity - required;
                } else {
                    // Acquire enough trailing capacity while balancing leading and trailing capacity
                    int newCapacity = capacity;
                    int newLeadingCapacity;
                    int newTrailingCapacity;

                    do {
                        newCapacity *= 2;
                        newLeadingCapacity = (newCapacity - size) / 2;
                        newTrailingCapacity = newCapacity - size - newLeadingCapacity;
                    } while (required > newTrailingCapacity);

                    int[] newBuffer = new int[newCapacity];
                    System.arraycopy(buffer, start, newBuffer, newLeadingCapacity, size);
                    buffer = newBuffer;
                    start = newLeadingCapacity;
                    end = newLeadingCapacity + size;
                }
            }
        }

        public Builder debug(String msg) {
            Arrays.fill(buffer, 0, start, 0);
            Arrays.fill(buffer, end, buffer.length, 0);
            System.out.println("[%s] ---> %s".formatted(msg, this));
            return this;
        }

        @Override
        public String toString() {
            return "IntList.Builder[array=%s%s%s, start=%d, end=%d, capacity=%d, leadingCapacity=%d, trailingCapacity=%d, size=%d, freeCapacity=%d]"
                    .formatted(
                            Arrays.toString(Arrays.copyOfRange(buffer, 0, start)),
                            Arrays.toString(Arrays.copyOfRange(buffer, start, end)),
                            Arrays.toString(Arrays.copyOfRange(buffer, end, buffer.length)),
                            start,
                            end,
                            capacity(),
                            leadingCapacity(),
                            trailingCapacity(),
                            size(),
                            freeCapacity()
                    );
        }

        public IntList build() {
            if (start == 0 && end == buffer.length) {
                return IntList.of(buffer.clone());
            } else {
                return IntList.of(Arrays.copyOfRange(buffer, start, end));
            }
        }

        private IntList buildNonLeaking() {
            if (start == 0 && end == buffer.length) {
                return IntList.of(buffer);
            } else {
                return IntList.of(Arrays.copyOfRange(buffer, start, end));
            }
        }

        public static Builder of(int[] array) {
            return new Builder(array.clone(), 0, array.length);
        }

        private static Builder ofNonLeaking(int[] array) {
            return new Builder(array, 0, array.length);
        }

        public static Builder of(int[] array, int capacity) {
            final int[] buffer = new int[capacity];
            System.arraycopy(array, 0, buffer, (capacity - array.length) / 2, array.length);
            return new Builder(buffer, 0, array.length);
        }

        public static Builder of(int[] array, int lead, int trail) {
            final int[] buffer = new int[lead + array.length + trail];
            System.arraycopy(array, 0, buffer, lead, array.length);
            return new Builder(buffer, lead, lead + array.length);
        }

        public static Builder empty(int initialCapacity) {
            return new Builder(new int[initialCapacity], initialCapacity / 2, initialCapacity / 2);
        }

        public static Builder empty(int lead, int trail) {
            return new Builder(new int[lead + trail], lead, trail);
        }
    }

    @FunctionalInterface
    public interface IntToIntFunction {
        int apply(int value);
    }
}

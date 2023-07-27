package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class IntList implements Comparable<IntList> {
    private final int[] array;

    private IntList(int[] array) {
        this.array = array;
    }

    public List<IntList> permutations() {
        final int n = array.length;

        if (n <= 1) {
            return List.of(this);
        } else {
            final int[] item = array.clone();
            final List<IntList> items = new ArrayList<>();
            permutationsLoop(n, item, items);
            return items;
        }
    }

    private void permutationsLoop(int nn, int[] item, List<IntList> items) {
        if (nn <= 1) {
            items.add(IntList.of(item.clone()));
        } else {
            for (int i = 0; i < nn - 1; i++) {
                permutationsLoop(nn - 1, item, items);
                final int j = nn % 2 == 0 ? i : 0;
                final int temp = item[j];
                item[j] = item[nn - 1];
                item[nn - 1] = temp;
            }

            permutationsLoop(nn - 1, item, items);
        }
    }

    public List<IntList> combinations(int k) {
        final int n = array.length;

        if (k == 0) {
            return List.of(IntList.of());
        } else if (k == n) {
            return List.of(this);
        } else {
            final int[] item = new int[k];
            final List<IntList> items = new ArrayList<>();
            combinationsLoop(k, 0, 0, item, items);
            return items;
        }
    }

    private void combinationsLoop(int k, int kk, int nn, int[] item, List<IntList> items) {
        if (kk == k) {
            items.add(IntList.of(item.clone()));
        } else {
            final int n = array.length;

            for (int i = nn; i < n; i++) {
                item[kk] = array[i];
                combinationsLoop(k, kk + 1, i + 1, item, items);
            }
        }
    }

    public List<IntList> arrangements(int k) {
        final List<IntList> combinations = combinations(k);
        final IntList[] permutations = computePermutationTable(k);

        final List<IntList> items = new ArrayList<>();
        final int[] item = new int[k];

        for (int c = 0; c < combinations.size(); c++) {
            int[] combination = combinations.get(c).array;

            for (int p = 0; p < permutations.length; p++) {
                int[] permutation = permutations[p].array;

                for (int i = 0; i < permutation.length; i++) {
                    item[i] = combination[permutation[i]];
                }

                items.add(IntList.of(item.clone()));
            }
        }

        return items;
    }

    private static IntList[] computePermutationTable(int n) {
        return IntList.range(0, n).permutations().toArray(new IntList[0]);
    }

    public int size() {
        return array.length;
    }

    public int get(int index) {
        return array[index];
    }

    public IntList subList(int start, int end) {
        return toBuilder()
                .subList(start, end)
                .unsafeBuild();
    }

    public IntList shuffle() {
        return toBuilder()
                .shuffle()
                .unsafeBuild();
    }

    public IntList shuffle(Random random) {
        return toBuilder()
                .shuffle(random)
                .unsafeBuild();
    }

    public boolean contains(int value) {
        return unsafeToBuilder().contains(value);
    }

    public boolean containsAll(IntList values) {
        return unsafeToBuilder().containsAll(values);
    }

    public boolean containsAny(IntList values) {
        return unsafeToBuilder().containsAny(values);
    }

    public IntList set(int index, int value) {
        return toBuilder()
                .set(index, value)
                .unsafeBuild();
    }

    public IntList swap(int index1, int index2) {
        return toBuilder()
                .swap(index1, index2)
                .unsafeBuild();
    }

    public IntList replace(int value, int newValue) {
        return toBuilder()
                .replace(value, newValue)
                .unsafeBuild();
    }

    public IntList reverse() {
        return toBuilder()
                .reverse()
                .unsafeBuild();
    }

    public IntList sort() {
        return toBuilder()
                .sort()
                .unsafeBuild();
    }

    public IntList sortDesc() {
        return toBuilder()
                .sortDesc()
                .unsafeBuild();
    }

    public IntList append(int value) {
        return toBuilder(0, 1)
                .append(value)
                .unsafeBuild();
    }

    public IntList appendAll(IntList values) {
        return toBuilder(0, values.array.length)
                .appendAll(values)
                .build();
    }

    public IntList prepend(int value) {
        return toBuilder(1, 0)
                .prepend(value)
                .build();
    }

    public IntList prependAll(IntList values) {
        return toBuilder(values.array.length, 0)
                .prependAll(values)
                .unsafeBuild();
    }

    public IntList retainAll(IntList values) {
        return toBuilder()
                .retainAll(values)
                .unsafeBuild();
    }

    public IntList insert(int index, int value) {
        return toBuilder(0, 1)
                .insert(index, value)
                .unsafeBuild();
    }

    public IntList insertAll(int index, IntList values) {
        return this.toBuilder(0, values.array.length)
                .insertAll(index, values)
                .unsafeBuild();
    }

    public IntList remove(int value) {
        return toBuilder()
                .remove(value)
                .unsafeBuild();
    }

    public IntList removeAll(IntList values) {
        return toBuilder()
                .removeAll(values)
                .unsafeBuild();
    }

    public IntList removeAt(int index) {
        return toBuilder()
                .removeAt(index)
                .unsafeBuild();
    }

    public IntList take(int count) {
        return toBuilder()
                .take(count)
                .unsafeBuild();
    }

    public IntList drop(int count) {
        return toBuilder()
                .drop(count)
                .unsafeBuild();
    }

    public IntList takeRight(int count) {
        return toBuilder()
                .takeRight(count)
                .unsafeBuild();
    }

    public IntList dropRight(int count) {
        return toBuilder()
                .dropRight(count)
                .unsafeBuild();
    }

    public IntList takeWhile(IntPredicate predicate) {
        return toBuilder()
                .takeWhile(predicate)
                .unsafeBuild();
    }

    public IntList dropWhile(IntPredicate predicate) {
        return toBuilder()
                .dropWhile(predicate)
                .unsafeBuild();
    }

    public IntList map(IntToIntFunction function) {
        return toBuilder()
                .map(function)
                .unsafeBuild();
    }

    public IntList flatMap(IntFunction<IntList> function) {
        return toBuilder()
                .flatMap(function)
                .unsafeBuild();
    }

    public IntList filter(IntPredicate predicate) {
        return toBuilder()
                .filter(predicate)
                .unsafeBuild();
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
    public int hashCode() {
        return Arrays.hashCode(array);
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }

    @Override
    public int compareTo(IntList o) {
        return Arrays.compare(array, o.array);
    }

    public static IntList of(int... array) {
        return new IntList(array);
    }

    public static IntList range(int startInclusive, int endExclusive) {
        return IntList.of(IntStream.range(startInclusive, endExclusive).toArray());
    }

    public static IntList rangeClosed(int startInclusive, int endInclusive) {
        return IntList.of(IntStream.rangeClosed(startInclusive, endInclusive).toArray());
    }

    public Builder toBuilder() {
        return Builder.of(array);
    }

    private Builder unsafeToBuilder() {
        return Builder.unsafeOf(array);
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

        protected int[] getBuffer() {
            return buffer;
        }

        protected int getStart() {
            return start;
        }

        protected int getEnd() {
            return end;
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

        public Builder subList(int start, int end) {
            final int currentStart = this.start;
            this.start = currentStart + start;
            this.end = currentStart + end;
            return this;
        }

        public Builder shuffle() {
            return shuffle(new Random());
        }

        public Builder shuffle(Random random) {
            for (int i = start; i < end; i++) {
                final int j = i + random.nextInt(end - i);
                swap(i, j);
            }

            return this;
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
            final Builder valuesBuilder = values.unsafeToBuilder();

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

        public Builder append(int value) {
            ensureTrailingCapacity(1);
            buffer[end] = value;
            end++;
            return this;
        }

        public Builder appendAll(IntList values) {
            ensureTrailingCapacity(values.array.length);
            System.arraycopy(values.array, 0, buffer, end, values.array.length);
            end += values.array.length;
            return this;
        }

        public Builder prepend(int value) {
            ensureLeadingCapacity(1);
            buffer[start - 1] = value;
            start--;
            return this;
        }

        public Builder prependAll(IntList values) {
            ensureLeadingCapacity(values.array.length);
            System.arraycopy(values.array, 0, buffer, start - values.array.length, values.array.length);
            start -= values.array.length;
            return this;
        }

        public Builder retainAll(IntList values) {
            final Builder valuesBuilder = values.unsafeToBuilder();
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
            final Builder valuesBuilder = values.unsafeToBuilder();
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

        public Builder removeAt(int index) {
            if (index == 0) {
                start++;
            } else if (index == size() -1) {
                end--;
            } else {
                System.arraycopy(buffer, start + index + 1, buffer, start + index, end - start - index - 1);
                this.end--;
            }
            return this;
        }

        public Builder take(int count) {
            if (count < end - start) {
                end = start + count;
            }

            return this;
        }

        public Builder drop(int count) {
            if (count < end - start) {
                start += count;
            } else {
                start = end;
            }

            return this;
        }

        public Builder takeRight(int count) {
            if (count < end - start) {
                start = end - count;
            }

            return this;
        }

        public Builder dropRight(int count) {
            if (count < end - start) {
                end -= count;
            } else {
                end = start;
            }

            return this;
        }

        public Builder takeWhile(IntPredicate predicate) {
            int i = start;

            while (i < end && predicate.test(buffer[i])) {
                i++;
            }

            end = i;
            return this;
        }

        public Builder dropWhile(IntPredicate predicate) {
            int i = start;

            while (i < end && predicate.test(buffer[i])) {
                i++;
            }

            start = i;
            return this;
        }

        public Builder map(IntToIntFunction function) {
            for (int i = start; i < end; i++) {
                buffer[i] = function.apply(buffer[i]);
            }

            return this;
        }

        public Builder flatMap(IntFunction<IntList> function) {
            final Builder result = Builder.empty(0, this.capacity());

            for (int i = start; i < end; i++) {
                result.appendAll(function.apply(buffer[i]));
            }

            return reset(result);
        }

        public Builder filter(IntPredicate predicate) {
            int j = start;

            for (int i = start; i < end; i++) {
                if (predicate.test(buffer[i])) {
                    buffer[j] = buffer[i];
                    j++;
                }
            }

            end = j;
            return this;
        }

        private Builder reset(Builder builder) {
            buffer = builder.buffer;
            start = builder.start;
            end = builder.end;
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
                    int newCapacity = capacity == 0 ? 1 : capacity;
                    int newLeadingCapacity;

                    do {
                        newCapacity *= 2;
                        newLeadingCapacity = (newCapacity - size) / 2;
                    } while (required > newLeadingCapacity);

                    final int[] newBuffer = new int[newCapacity];
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
                    int newCapacity = capacity == 0 ? 1 : capacity;
                    int newLeadingCapacity;
                    int newTrailingCapacity;

                    do {
                        newCapacity *= 2;
                        newLeadingCapacity = (newCapacity - size) / 2;
                        newTrailingCapacity = newCapacity - size - newLeadingCapacity;
                    } while (required > newTrailingCapacity);

                    final int[] newBuffer = new int[newCapacity];
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

        private IntList unsafeBuild() {
            if (start == 0 && end == buffer.length) {
                return IntList.of(buffer);
            } else {
                return IntList.of(Arrays.copyOfRange(buffer, start, end));
            }
        }

        public static Builder of(int[] array) {
            return new Builder(array.clone(), 0, array.length);
        }

        private static Builder unsafeOf(int[] array) {
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
            return new Builder(new int[lead + trail], lead, lead);
        }
    }

    @FunctionalInterface
    public interface IntToIntFunction {
        int apply(int value);
    }
}

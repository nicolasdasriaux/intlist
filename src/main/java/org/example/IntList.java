package org.example;

import java.util.Arrays;
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
        return toBuilderGrowing(1)
                .add(value)
                .buildNonLeaking();
    }

    public IntList addAll(IntList values) {
        return toBuilderGrowing(values.array.length)
                .addAll(values)
                .build();
    }

    public IntList addFirst(int value) {
        return toBuilderGrowing(1)
                .addFirst(value)
                .build();
    }

    public IntList addAllFirst(IntList values) {
        return toBuilderGrowing(values.array.length)
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
                .replaceAll(function)
                .buildNonLeaking();
    }

    public IntList filter(IntPredicate predicate) {
        return toBuilder()
                .filter(predicate)
                .buildNonLeaking();
    }

    public IntList insert(int index, int value) {
        return toBuilderGrowing(1)
                .insert(index, value)
                .buildNonLeaking();
    }

    public IntList insertAll(int index, IntList values) {
        return this.toBuilderGrowing(values.array.length)
                .insertAll(index, values)
                .buildNonLeaking();
    }

    public IntList delete(int index) {
        return this.toBuilder()
                .delete(index)
                .build();
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

    public IntList build(Consumer<Builder> consumer) {
        final Builder builder = toBuilder();
        consumer.accept(builder);
        return builder.build();
    }

    private Builder toBuilderGrowing(int growth) {
        return Builder.ofGrowing(array, growth);
    }

    public static class Builder {
        private int[] array;
        private int length;
        private int capacity;

        private Builder(int[] array, int length, int capacity) {
            this.array = array;
            this.length = length;
            this.capacity = capacity;
        }

        public int size() {
            return length;
        }

        public int capacity() {
            return capacity;
        }

        public int get(int index) {
            return array[index];
        }


        public Builder set(int index, int value) {
            array[index] = value;
            return this;
        }


        public int indexOf(int value) {
            for (int i = 0; i < length; i++) {
                if (array[i] == value) {
                    return i;
                }
            }

            return -1;
        }

        public int lastIndexOf(int value) {
            for (int i = length - 1; i >= 0; i--) {
                if (array[i] == value) {
                    return i;
                }
            }

            return -1;
        }

        public boolean contains(int value) {
            for (int i = 0; i < length; i++) {
                if (array[i] == value) {
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
            for (int i = 0; i < length; i++) {
                if (values.contains(array[i])) {
                    return true;
                }
            }

            return false;
        }

        public boolean hasDuplicates() {
            for (int i = 0; i < length; i++) {
                for (int j = i + 1; j < length; j++) {
                    if (array[i] == array[j]) {
                        return true;
                    }
                }
            }

            return false;
        }

        public Builder swap(int index1, int index2) {
            final int value = array[index1];
            array[index1] = array[index2];
            array[index2] = value;
            return this;
        }

        public Builder reverse() {
            for (int i = 0; i < length / 2; i++) {
                swap(i, length - 1 - i);
            }

            return this;
        }

        public Builder sort() {
            Arrays.sort(array, 0, length);
            return this;
        }

        public Builder sortDesc() {
            return sort().reverse();
        }

        public Builder add(int value) {
            ensureCapacity(length + 1);
            array[length] = value;
            length++;
            return this;
        }

        public Builder addAll(IntList values) {
            ensureCapacity(length + values.array.length);
            System.arraycopy(values.array, 0, array, length, values.array.length);
            length += values.array.length;
            return this;
        }

        public Builder addFirst(int value) {
            ensureCapacity(length + 1);
            System.arraycopy(array, 0, array, 1, length);
            array[0] = value;
            length++;
            return this;
        }

        public Builder addAllFirst(IntList values) {
            ensureCapacity(length + values.array.length);
            System.arraycopy(array, 0, array, values.array.length, length);
            System.arraycopy(values.array, 0, array, 0, values.array.length);
            length += values.array.length;
            return this;
        }

        public Builder retainAll(IntList values) {
            final Builder builder = Builder.empty(this.length);

            for (int i = 0; i < length; i++) {
                if (values.contains(array[i])) {
                    builder.add(array[i]);
                }
            }

            return reset(builder);
        }

        public Builder remove(int value) {
            final Builder builder = Builder.empty(length);

            for (int i = 0; i < length; i++) {
                if (array[i] != value) {
                    builder.add(array[i]);
                }
            }

            return reset(builder);
        }

        public Builder removeAll(IntList values) {
            final Builder builder = Builder.empty(length);

            for (int i = 0; i < length; i++) {
                if (!values.contains(array[i])) {
                    builder.add(array[i]);
                }
            }

            return reset(builder);
        }

        public Builder replaceAll(IntToIntFunction function) {
            for (int i = 0; i < length; i++) {
                array[i] = function.apply(array[i]);
            }

            return this;
        }

        public Builder filter(IntPredicate predicate) {
            final Builder builder = Builder.empty(length);

            for (int i = 0; i < length; i++) {
                if(predicate.test(array[i])) {
                    builder.add(array[i]);
                }
            }

            reset(builder);
            return this;
        }

        public Builder insert(int index, int value) {
            ensureCapacity(length + 1);
            System.arraycopy(array, index, array, index + 1, length - index);
            array[index] = value;
            length++;
            return this;
        }

        public Builder insertAll(int index, IntList values) {
            ensureCapacity(length + values.array.length);
            System.arraycopy(array, index, array, index + values.array.length, length - index);
            System.arraycopy(values.array, 0, array, index, values.array.length);
            length += values.array.length;
            return this;
        }

        public Builder delete(int index) {
            System.arraycopy(array, index + 1, array, index, length - 1 - index);
            this.length--;
            return this;
        }

        private Builder reset(Builder builder) {
            array = builder.array;
            length = builder.length;
            capacity = builder.capacity;
            return this;
        }

        private void ensureCapacity(int required) {
            int newCapacity = this.capacity;

            while (required > newCapacity) {
                newCapacity *= 2;
            }

            if (required > this.capacity) {
                int[] newArray = new int[newCapacity];
                System.arraycopy(this.array, 0, newArray, 0, this.length);
                this.array = newArray;
                this.capacity = newCapacity;
            }
        }

        @Override
        public String toString() {
            return "IntList.Builder[" +
                    "array=" +
                    Arrays.toString(Arrays.copyOf(array, length)) +
                    Arrays.toString(Arrays.copyOfRange(array, length, capacity)) +
                    ", length=" +
                    length +
                    ", capacity=" +
                    capacity + "]";
        }

        public IntList build() {
            return IntList.of(length == capacity ? array.clone() : Arrays.copyOf(array, length));
        }

        public static Builder of(int[] array) {
            return new Builder(array.clone(), array.length, array.length);
        }

        private static Builder ofNonLeaking(int[] array) {
            return new Builder(array, array.length, array.length);
        }

        public static Builder empty(int initialCapacity) {
            return new Builder(new int[initialCapacity], 0, initialCapacity);
        }

        private static Builder ofGrowing(int[] array, int growth) {
            final int[] newArray = new int[array.length + growth];
            System.arraycopy(array, 0, newArray, 0, array.length);
            return new Builder(newArray, array.length, newArray.length);
        }

        private IntList buildNonLeaking() {
            return IntList.of(length == capacity ? array : Arrays.copyOf(array, length));
        }
    }

    @FunctionalInterface
    public interface IntToIntFunction {
        int apply(int value);
    }
}

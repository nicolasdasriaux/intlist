package fastprimcol.intlist.example;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

public class ArrayExamples {
    public static void only_mutable_but_not_extensible() {
        final int[] array = {1, 2, 3};

        array[0] = 10; // Non uniform syntax ([])

        // Appending an element at the end of an array
        final int[] modifiedArray = new int[array.length + 1]; // Non uniform syntax (length)
        System.arraycopy(array, 0, modifiedArray, 0, array.length); // Not a method of array
        modifiedArray[modifiedArray.length - 1] = 40;

        System.out.println(Arrays.toString(modifiedArray));
    }

    public static void broken_to_string() {
        final int[] array = {1, 2, 3};

        final String wrong = array.toString();
        System.out.println(wrong);
        // [I@615db445

        final String result = Arrays.toString(array);
        System.out.println(result);
        // [1, 2, 3]
    }

    public static void broken_equals() {
        final int[] array1 = {1, 2, 3};
        final int[] array2 = {1, 2, 3};

        boolean wrong = array1.equals(array2);
        assert !wrong : "Broken";

        final boolean result = Arrays.equals(array1, array2);
        assert result : "OK";
    }

    public static void broken_hash_code() {
        final int[] array1 = {1, 2, 3};
        final int[] array2 = {1, 2, 3};

        boolean wrong = array1.hashCode() == array2.hashCode();
        assert !wrong : "Broken";

        final boolean result = Arrays.hashCode(array1) == Arrays.hashCode(array2);
        assert result : "OK";
    }

    public static void poor_api() {
        final int[] array1 = {1, 2, 3};
        final int[] array2 = {4, 5, 6};
        final int[] array3 = {7, 8, 9};

        final int[] result = MoreArrays.concat(array1, array2, array3);
        assert Arrays.equals(result, new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9});
    }

    public static class MoreArrays {
        public static int[] concat(int[] array1, int[] array2, int[] array3) {
            int[] result = new int[array1.length + array2.length + array3.length];
            System.arraycopy(array1, 0, result, 0, array1.length);
            System.arraycopy(array2, 0, result, array1.length, array2.length);
            System.arraycopy(array3, 0, result, array1.length + array2.length, array3.length);
            return result;
        }
    }

    public static void apache_commons() {
        final int[] list1 = {1, 2, 3};
        final int[] array2 = {5, 6};
        final int[] array3 = {7, 8, 9};

        final int[] step1 = ArrayUtils.add(list1, 4); // Allocates an array and copy
        final int[] step2 = ArrayUtils.addAll(step1, array2); // Allocates an array and copy
        final int[] step3 = ArrayUtils.addAll(step2, array3); // Allocates an array and copy
        final int[] result = step3;

        // Not very efficient (redundant allocations and copies)
        // Poorly legible (parenthesis nesting)
        // Very underfeatured

        assert Arrays.equals(result, new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9});
    }

    public static void main(String[] args) {
        only_mutable_but_not_extensible();
        broken_to_string();
        broken_equals();
        broken_hash_code();
        poor_api();
        apache_commons();
    }
}

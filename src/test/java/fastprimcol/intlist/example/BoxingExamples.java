package fastprimcol.intlist.example;

public class BoxingExamples {
    public static void implicit_boxing_unboxing() {
        final Integer boxed = 1;
        // Implicit boxing

        int unboxed = boxed;
        // Implicit unboxing
    }

    public static void boxing() {
        final Integer a1 = Integer.valueOf(10_000);
        final Integer a2 = Integer.valueOf(10_000);

        assert a1.equals(a2); // Same value
        assert a1 != a2; // Different object reference

        // New instance every time!
    }

    public static void boxing_optimized() {
        final Integer a1 = Integer.valueOf(5);
        final Integer a2 = Integer.valueOf(5);

        assert a1.equals(a2); // Same value
        assert a1 == a2; // Same object reference

        // Preallocated for values between -128 to 127
    }

    public static void unboxing() {
        final Integer boxed = Integer.valueOf(5);

        final int unboxed = boxed.intValue();
        // Dereferencing every time!
    }

    public static void null_unboxing()  {
        final Integer boxed = null;

        try {
            final int unboxed = boxed.intValue(); // BOOM!
        } catch (NullPointerException e) {
            assert true : "Unboxing null should fail";
        }
    }

    public static void main(String[] args) {
        implicit_boxing_unboxing();
        boxing();
        boxing_optimized();
        unboxing();
        null_unboxing();
    }
}
